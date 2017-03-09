package com.mokon.nbp.currency.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static com.mokon.nbp.currency.parser.Messages.*;

public class CurrencyDataCollector {
    static Logger LOGGER = LoggerFactory.getLogger(CurrencyDataCollector.class);

    private static XMLReader reader;
    private static StatisticCalculator statisticCalculator;
    private static LocalDate fromDay;
    private static LocalDate untilDay;
    private static Currency currency;

    public static void main(String[] args) {
        if (args.length != 3) {
            LOGGER.error(WRONG_INPUT_ARGUMENTS);
            System.exit(1);
        }

        currency = ArgumentsValidator.validateCurrency(args[0]);
        fromDay = ArgumentsValidator.validateDate(args[1]);
        untilDay = ArgumentsValidator.validateDate(args[2]);

        ArgumentsValidator.checkDates(fromDay, untilDay);

        reader = new XMLReader(currency, fromDay, untilDay);
        reader.getDataFromBank();
        statisticCalculator = new StatisticCalculator();

        LOGGER.info(currency + " " + CURRENCY_CODE);
        LOGGER.info(fromDay + " " + FROM_DATE);
        LOGGER.info(untilDay + " " + END_DATE);

        statisticCalculator.calculateStatistics(reader.getBuy(), reader.getSell());
    }
}
