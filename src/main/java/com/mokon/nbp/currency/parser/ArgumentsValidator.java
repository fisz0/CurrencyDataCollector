package com.mokon.nbp.currency.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.mokon.nbp.currency.parser.Messages.*;

/**
 * Created by mokon on 09.03.2017.
 */
public final class ArgumentsValidator {

    static Logger LOGGER = LoggerFactory.getLogger(ArgumentsValidator.class);

    public static LocalDate validateDate(String date) {
        LOGGER.info("Validating date.");
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(date);
            localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (java.time.format.DateTimeParseException e) {
            LOGGER.error(WRONG_FORMAT, e);
            System.exit(1);
        }
        return localDate;
    }

    public static void checkDates(LocalDate fromDay, LocalDate untilDay) {
        LOGGER.info("Comparing dates.");
        if (fromDay.compareTo(untilDay) > 0) {
            throw new IllegalArgumentException(DATES_ERROR);
        }
    }

    public static Currency validateCurrency(String argCurrency) {
        LOGGER.info("Validating currency.");
        Currency currency = null;
        try {
            currency = Currency.valueOf(argCurrency);
        } catch (IllegalArgumentException e) {
            LOGGER.error(CURRENCY_PARSING_ERROR);
        }
        return currency;
    }
}
