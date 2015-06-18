/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 * <p>
 * Please see distribution for license.
 */
package com.opengamma.strata.function.credit;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.market.MarketDataKey;
import com.opengamma.strata.engine.calculations.DefaultSingleCalculationMarketData;
import com.opengamma.strata.engine.calculations.function.result.ScenarioResult;
import com.opengamma.strata.engine.marketdata.CalculationMarketData;
import com.opengamma.strata.engine.marketdata.CalculationRequirements;
import com.opengamma.strata.finance.credit.Cds;
import com.opengamma.strata.finance.credit.CdsTrade;
import com.opengamma.strata.finance.credit.ExpandedCds;
import com.opengamma.strata.finance.credit.reference.IndexReferenceInformation;
import com.opengamma.strata.finance.credit.reference.ReferenceInformation;
import com.opengamma.strata.finance.credit.reference.ReferenceInformationType;
import com.opengamma.strata.finance.credit.reference.SingleNameReferenceInformation;
import com.opengamma.strata.function.calculation.AbstractCalculationFunction;
import com.opengamma.strata.market.curve.IsdaCreditCurveParRates;
import com.opengamma.strata.market.curve.IsdaYieldCurveParRates;
import com.opengamma.strata.market.key.IsdaIndexCreditCurveParRatesKey;
import com.opengamma.strata.market.key.IsdaSingleNameCreditCurveParRatesKey;
import com.opengamma.strata.market.key.IsdaYieldCurveParRatesKey;

import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static com.opengamma.strata.engine.calculations.function.FunctionUtils.toScenarioResult;

/**
 * Calculates a result of a {@code CdsTrade} for each of a set of scenarios.
 *
 * @param <T> the return type
 */
public abstract class AbstractCdsFunction<T>
    extends AbstractCalculationFunction<CdsTrade, ScenarioResult<T>> {

  /**
   * Creates a new instance which will return results from the {@code execute} method that support automatic
   * currency conversion if the underlying results support it.
   */
  protected AbstractCdsFunction() {
    super();
  }

  /**
   * Creates a new instance.
   *
   * @param convertCurrencies if this is true the value returned by the {@code execute} method will support
   *                          automatic currency conversion if the underlying results support it
   */
  protected AbstractCdsFunction(boolean convertCurrencies) {
    super(convertCurrencies);
  }

  /**
   * Returns the Cds pricer.
   *
   * @return the pricer
   */
  protected CdsPricer pricer() {
    return CdsPricer.DEFAULT;
  }

  //-------------------------------------------------------------------------
  @Override
  public ScenarioResult<T> execute(CdsTrade trade, CalculationMarketData marketData) {
    return IntStream.range(0, marketData.getScenarioCount())
        .mapToObj(index -> new DefaultSingleCalculationMarketData(marketData, index))
        .map(provider -> execute(trade, provider))
        .collect(toScenarioResult(isConvertCurrencies()));
  }

  @Override
  public CalculationRequirements requirements(CdsTrade trade) {
    Cds cds = trade.getProduct();

    Currency notionalCurrency = cds.getFeeLeg().getPeriodicPayments().getNotional().getCurrency();
    Currency feeCurrency = cds.getFeeLeg().getUpfrontFee().getFixedAmount().getCurrency();

    Set<MarketDataKey<?>> rateCurveKeys = ImmutableSet.of(IsdaYieldCurveParRatesKey.of(notionalCurrency), IsdaYieldCurveParRatesKey.of(feeCurrency));

    ReferenceInformation referenceInformation = cds.getReferenceInformation();
    ReferenceInformationType cdsType = referenceInformation.getType();
    Set<MarketDataKey<?>> spreadCurveKey;
    switch (cdsType) {
      case SINGLE_NAME:
        SingleNameReferenceInformation singleNameReferenceInformation = (SingleNameReferenceInformation) referenceInformation;
        spreadCurveKey = ImmutableSet.of(IsdaSingleNameCreditCurveParRatesKey.of(singleNameReferenceInformation));
        break;
      case INDEX:
        IndexReferenceInformation indexReferenceInformation = (IndexReferenceInformation) referenceInformation;
        spreadCurveKey = ImmutableSet.of(IsdaIndexCreditCurveParRatesKey.of(indexReferenceInformation));
        // TODO Index Factor?
        break;
      default:
        throw new IllegalStateException("unknown reference information type: " + cdsType);
    }
    // TODO recovery rate and index factor
    return CalculationRequirements.builder()
        .singleValueRequirements(Sets.union(rateCurveKeys, spreadCurveKey))
        .outputCurrencies(ImmutableSet.of(notionalCurrency, feeCurrency))
        .build();
  }

  /**
   * Returns the currency of the trade.
   *
   * @param target the swap that is the target of the calculation
   * @return the currency of the cds
   */
  @Override
  public Optional<Currency> defaultReportingCurrency(CdsTrade target) {
    return Optional.of(target.getProduct().getFeeLeg().getPeriodicPayments().getNotional().getCurrency());
  }

  // execute for a single product
  protected T execute(CdsTrade trade, DefaultSingleCalculationMarketData provider) {

    IsdaYieldCurveParRatesKey yieldCurveParRatesKey = IsdaYieldCurveParRatesKey.of(
        trade.getProduct().getFeeLeg().getPeriodicPayments().getNotional().getCurrency());
    IsdaYieldCurveParRates yieldCurveParRates = provider.getValue(yieldCurveParRatesKey);

    ReferenceInformation referenceInformation = trade.getProduct().getReferenceInformation();
    ReferenceInformationType cdsType = referenceInformation.getType();
    IsdaCreditCurveParRates creditCurveParRates;
    switch (cdsType) {
      case SINGLE_NAME:
        SingleNameReferenceInformation singleNameReferenceInformation = (SingleNameReferenceInformation) referenceInformation;
        IsdaSingleNameCreditCurveParRatesKey singleNameCreditCurveParRatesKey = IsdaSingleNameCreditCurveParRatesKey.of(singleNameReferenceInformation);
        creditCurveParRates = provider.getValue(singleNameCreditCurveParRatesKey);
        break;
      case INDEX:
        IndexReferenceInformation indexReferenceInformation = (IndexReferenceInformation) referenceInformation;
        IsdaIndexCreditCurveParRatesKey indexCreditCurveParRatesKey = IsdaIndexCreditCurveParRatesKey.of(indexReferenceInformation);
        creditCurveParRates = provider.getValue(indexCreditCurveParRatesKey);
        break;
      default:
        throw new IllegalStateException("unknown reference information type: " + cdsType);
    }
    return execute(trade.getProduct().expand(), yieldCurveParRates, creditCurveParRates, provider);
  }

  // execute for a single product
  protected abstract T execute(
      ExpandedCds product,
      IsdaYieldCurveParRates yieldCurveParRates,
      IsdaCreditCurveParRates creditCurveParRates,
      DefaultSingleCalculationMarketData provider
  );


}
