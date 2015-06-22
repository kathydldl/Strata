/*
 * *
 *  * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *  *
 *  * Please see distribution for license.
 *
 *
 */

package com.opengamma.strata.examples.finance;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.opengamma.analytics.util.ArrayUtils;
import com.opengamma.strata.collect.tuple.Pair;
import com.opengamma.strata.engine.config.Measure;
import com.opengamma.strata.examples.finance.credit.ExampleCalculator;
import com.opengamma.strata.examples.finance.credit.ExampleReporter;
import com.opengamma.strata.examples.finance.credit.ExampleTradeSource;
import com.opengamma.strata.examples.finance.credit.api.Calculator;
import com.opengamma.strata.examples.finance.credit.api.Reporter;
import com.opengamma.strata.examples.finance.credit.api.TradeSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CdsPricingExample {

  private static Logger logger = LoggerFactory.getLogger(CdsPricingExample.class);

  static final TradeSource trades = ExampleTradeSource.of();
  static final LocalDate valuationDate = LocalDate.of(2014, 10, 16);
  static final Measure pv = Measure.PRESENT_VALUE;
  static final Measure ir01ParallelPar = Measure.IR01_PARALLEL_PAR;
  static final Measure ir01BucketedPar = Measure.IR01_BUCKETED_PAR;
  static final Measure cs01ParallelPar = Measure.CS01_PARALLEL_PAR;
  static final Measure cs01BucketedPar = Measure.CS01_BUCKETED_PAR;
  static final ImmutableList<Measure> measures = ImmutableList.of(
      pv,
      ir01ParallelPar,
      cs01ParallelPar,
      ir01BucketedPar,
      cs01BucketedPar
  );
  static final Calculator calc = ExampleCalculator.of();
  static final Reporter reporter = ExampleReporter.of("cds-report-template");

  public static void main(String[] args) {
    logger.info("PV is " + calcPv());
    logger.info("IR01 parallel par is " + calcIr01ParallelPar());
    logger.info("IR01 bucketed par is " + join(calcIr01BucketedParSens()));
    logger.info("CS01 parallel par is " + calcCs01ParallelPar());
    logger.info("CS01 bucketed par is " + join(calcCs01BucketedParSens()));
    logger.info("=== CSV Report ===");
    calcMeasuresAndReportToCsvToLogger();
    logger.info("=== ASCII Report ===");
    calcMeasuresAndReportToAsciiToLogger();
  }

  // --------------------------------------------------------------------
  // package private helper functions for implementation and unit testing

  static double calcPv() {
    return calc.calculateScalarValue(valuationDate, trades, pv);
  }

  static double calcIr01ParallelPar() {
    return calc.calculateScalarValue(valuationDate, trades, ir01ParallelPar);
  }

  static double[] calcIr01BucketedPar() {
    return calc.calculateVectorValue(valuationDate, trades, ir01BucketedPar);
  }

  static List<Pair<String, Double>> calcIr01BucketedParSens() {
    return calc.calculateSensitivityValue(valuationDate, trades, ir01BucketedPar);
  }

  static double calcCs01ParallelPar() {
    return calc.calculateScalarValue(valuationDate, trades, cs01ParallelPar);
  }

  static double[] calcCs01BucketedPar() {
    return calc.calculateVectorValue(valuationDate, trades, cs01BucketedPar);
  }

  static List<Pair<String, Double>> calcCs01BucketedParSens() {
    return calc.calculateSensitivityValue(valuationDate, trades, cs01BucketedPar);
  }

  static void calcMeasuresAndReportToAsciiToLogger() {
    reporter.reportAsciiToLogger(calc.calculateReportingResults(valuationDate, trades, measures));
  }

  static void calcMeasuresAndReportToCsvToLogger() {
    reporter.reportCsvToLogger(calc.calculateReportingResults(valuationDate, trades, measures));
  }

  private static String join(double[] d) {
    return Lists.newArrayList(ArrayUtils.toObject(d))
        .stream()
        .map(s -> String.valueOf(s))
        .collect(Collectors.joining(", "));
  }

  private static String join(List<Pair<String, Double>> p) {
    return p
        .stream()
        .map(
            s -> String.format(
                "%s -> %.16f",
                s.getFirst(),
                s.getSecond()
            )
        )
        .collect(Collectors.joining(", "));
  }

}