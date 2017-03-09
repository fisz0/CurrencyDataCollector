package com.mokon.nbp.currency.parser;

/**
 * Created by mokon on 06.03.2017.
 */
public class Messages {
    public static final String WRONG_FORMAT = "Wrong date format, default format is: yyyy-MM-dd";

    public static final String WRONG_INPUT_ARGUMENTS = "To few input arguments. " +
        "Required number is 3: " +
        "Currency StartDate FinishDate. " +
        "Example: EUR 2013-01-28 2013-01-31";

    public static final String CURRENCY_PARSING_ERROR = "Wrong currency.";
    public static final String CURRENCY_CODE = "- currency code.";
    public static final String FROM_DATE = "- start date.";
    public static final String END_DATE = "- finish date.";
    public static final String AVG_BUY_COURSE = "- average buy course.";
    public static final String STANDARD_DEVIATION = "- standard deviation of sell course.";
    public static final String STANDARD_DEVIATION_ERROR = "- to few position to calculate standard deviation.";
    public static final String DATES_ERROR = "Start date must be before finish date.";

    public static final String URL_GENERAL = "http://www.nbp.pl/kursy/xml/";
    public static final String URL_DATA_FOR_SPECIFIED_DATE = "http://www.nbp.pl/kursy/xml/dir";
}
