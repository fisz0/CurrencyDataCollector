package com.mokon.nbp.currency.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.mokon.nbp.currency.parser.Messages.*;
import static java.math.BigDecimal.valueOf;

/**
 * Created by mokon on 09.03.2017.
 */
public class StatisticCalculator {

    static Logger LOGGER = LoggerFactory.getLogger(StatisticCalculator.class);


    public void calculateStatistics(List<BigDecimal> buy, List<BigDecimal> sell) {
        LOGGER.info(String.format("%.4f " + AVG_BUY_COURSE + "\n", averageCurrencyBuyCourse(buy)));

        BigDecimal standardDeviation = standardDeviationSell(sell);

        if (!standardDeviation.equals(valueOf(0.0))) {
            LOGGER.info(String.format("%.4f " + STANDARD_DEVIATION, standardDeviation));
        } else {
            LOGGER.error(STANDARD_DEVIATION_ERROR);
        }
    }

    private BigDecimal averageCurrencyBuyCourse(List<BigDecimal> buy) {
        LOGGER.info("Calculating average currency buy course.");
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal e : buy) {
            sum = sum.add(e);
        }
        return sum.divide(valueOf(buy.size()), 5, RoundingMode.HALF_EVEN);
    }

    private BigDecimal standardDeviationSell(List<BigDecimal> sell) {
        LOGGER.info("Calculating standard deviation.");
        if (sell.size() == 1) return BigDecimal.ZERO;
        BigDecimal sum = valueOf(0);
        for (BigDecimal e : sell) {
            sum = sum.add(e);
        }
        sum = sum.divide(valueOf(sell.size()), 5, RoundingMode.HALF_EVEN);
        BigDecimal standardDeviation = BigDecimal.ZERO;
        for (BigDecimal e : sell) {
            standardDeviation = e.subtract(sum).pow(2).add(standardDeviation);
        }
        standardDeviation = sqrt(standardDeviation.divide(valueOf(sell.size()), 5, RoundingMode.HALF_EVEN));
        return standardDeviation;
    }

    private BigDecimal sqrt(BigDecimal x) {
        return BigDecimal.valueOf(StrictMath.sqrt(x.doubleValue())).round(CurrencyMathContext.getNewContext());
    }
}
