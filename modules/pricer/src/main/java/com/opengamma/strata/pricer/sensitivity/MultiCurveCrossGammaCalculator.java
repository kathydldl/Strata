/**
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.pricer.sensitivity;

import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;

import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.collect.array.DoubleArray;
import com.opengamma.strata.collect.array.DoubleMatrix;
import com.opengamma.strata.market.curve.Curve;
import com.opengamma.strata.market.curve.NodalCurve;
import com.opengamma.strata.market.param.CrossGammaParameterSensitivities;
import com.opengamma.strata.market.param.CrossGammaParameterSensitivity;
import com.opengamma.strata.market.param.CurrencyParameterSensitivities;
import com.opengamma.strata.market.param.CurrencyParameterSensitivity;
import com.opengamma.strata.math.impl.differentiation.FiniteDifferenceType;
import com.opengamma.strata.math.impl.differentiation.VectorFieldFirstOrderDifferentiator;
import com.opengamma.strata.pricer.rate.ImmutableRatesProvider;

/**
 * Computes the cross-gamma to the curve parameters for multi-curve with all the curves in the same currency and instruments.
 * <p>
 * The curves should be represented by a YieldCurve with an InterpolatedDoublesCurve on the zero-coupon rates.
 * By default the gamma is computed using a one basis-point shift. This default can be change in a constructor.
 * The results themselves are not scaled (the represent the second order derivative).
 * <p>
 * <p> Reference: Interest Rate Cross-gamma for Single and Multiple Curves. OpenGamma Analysis 1, August 14
 */
public class MultiCurveCrossGammaCalculator {

  /**
   * Default implementation. Finite difference is forward and the shift is one basis point (0.0001).
   */
  public static final MultiCurveCrossGammaCalculator DEFAULT =
      new MultiCurveCrossGammaCalculator(FiniteDifferenceType.FORWARD, 1.0E-4);

  /**
   * The first order finite difference calculator.
   */
  private final VectorFieldFirstOrderDifferentiator fd;

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance of the finite difference calculator using forward differencing.
   * 
   * @param shift  the shift to be applied to the curves
   * @return the calculator
   */
  public static MultiCurveCrossGammaCalculator ofForwardDifference(double shift) {
    return new MultiCurveCrossGammaCalculator(FiniteDifferenceType.FORWARD, shift);
  }

  /**
   * Obtains an instance of the finite difference calculator using central differencing.
   * 
   * @param shift  the shift to be applied to the curves
   * @return the calculator
   */
  public static MultiCurveCrossGammaCalculator ofCentralDifference(double shift) {
    return new MultiCurveCrossGammaCalculator(FiniteDifferenceType.CENTRAL, shift);
  }

  /**
   * Obtains an instance of the finite difference calculator using backward differencing.
   * 
   * @param shift  the shift to be applied to the curves
   * @return the calculator
   */
  public static MultiCurveCrossGammaCalculator ofBackwardDifference(double shift) {
    return new MultiCurveCrossGammaCalculator(FiniteDifferenceType.BACKWARD, shift);
  }

  //-------------------------------------------------------------------------
  /**
   * Create an instance of the finite difference calculator.
   * 
   * @param fdType  the finite difference type
   * @param shift  the shift to be applied to the curves
   */
  private MultiCurveCrossGammaCalculator(FiniteDifferenceType fdType, double shift) {
    this.fd = new VectorFieldFirstOrderDifferentiator(fdType, shift);
  }

  //-------------------------------------------------------------------------
  public CrossGammaParameterSensitivities calculateCrossGammaIntraCurve(
      ImmutableRatesProvider ratesProvider,
      Function<ImmutableRatesProvider, CurrencyParameterSensitivities> sensitivitiesFn) {
    
    CrossGammaParameterSensitivities result = CrossGammaParameterSensitivities.empty();
    for (Entry<Currency, Curve> entry : ratesProvider.getDiscountCurves().entrySet()) {
      NodalCurve nodalCurve = (NodalCurve) entry.getValue(); // TODO 
      DoubleArray parameters = nodalCurve.getYValues();
      Function<DoubleArray, DoubleArray> function = new Function<DoubleArray, DoubleArray>() {
        @Override
        public DoubleArray apply(DoubleArray t) {
          NodalCurve newCurve = nodalCurve.withYValues(t);
          ImmutableRatesProvider newRates = ratesProvider.toBuilder().discountCurve(entry.getKey(), newCurve).build();
          CurrencyParameterSensitivities sensiMulti = sensitivitiesFn.apply(newRates);
          Optional<CurrencyParameterSensitivity> sensiSingle = sensiMulti.findSensitivity(newCurve.getName(), entry.getKey());
          return sensiSingle.isPresent() ? sensiSingle.get().getSensitivity() : DoubleArray.of();
        }
      };
      DoubleMatrix sensi = fd.differentiate(function).apply(parameters);
      result = result.combinedWith(CrossGammaParameterSensitivity.of(nodalCurve.getName(),
          nodalCurve.getMetadata().getParameterMetadata().get(), entry.getKey(), sensi));
    }

    return result;
  }


//  private <T> CrossGammaParameterSensitivities sensitivity(ImmutableRatesProvider ratesProvider, Map<T, Curve> baseCurves,
//      Function<Curve, ImmutableRatesProvider> ratesProviderFn,
//      Currency sensitivityCurrency,
//      Function<ImmutableRatesProvider, CurrencyParameterSensitivities> sensitivitiesFn) {
//
//    CrossGammaParameterSensitivities result = CrossGammaParameterSensitivities.empty();
//    for (Entry<T, Curve> entry : baseCurves.entrySet()) {
//      NodalCurve nodalCurve = (NodalCurve) entry.getValue(); // TODO 
//      DoubleArray parameters = nodalCurve.getYValues();
//      Function<DoubleArray, DoubleArray> function = new Function<DoubleArray, DoubleArray>() {
//        @Override
//        public DoubleArray apply(DoubleArray t) {
//          NodalCurve newCurve = nodalCurve.withYValues(t);
//          ImmutableRatesProvider newRates = ratesProviderFn.apply(newCurve);
//          CurrencyParameterSensitivities sensi = sensitivitiesFn.apply(newRates);
//          try {
//            sensi.getSensitivities().
//            return sensi.getSensitivity(nodalCurve.getName(), sensitivityCurrency).getSensitivity();
//          } catch (Exception e) {
//            return DoubleArray.of(); // TODO
//          }
//        }
//      };
//      DoubleMatrix sensi = fd.differentiate(function).apply(parameters);
//      result = result.combinedWith(CrossGammaParameterSensitivity.of(nodalCurve.getName(),
//          nodalCurve.getMetadata().getParameterMetadata().get(), sensitivityCurrency, sensi));
//    }
//
//    return result;
//  }
}
