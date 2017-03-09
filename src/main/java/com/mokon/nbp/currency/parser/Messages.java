package com.mokon.nbp.currency.parser;

/**
 * Created by mokon on 06.03.2017.
 */
public class Messages {
    public static final String WRONG_FORMAT = "Niepoprawnie podana data, domyślny format daty to: yyyy-MM-dd";
    public static final String ASK_FOR_DATA = "Podaj dane do przeliczeń." +
        "- Waluta: USD, EUR, CHF, GBP" +
        "- Zakres dat: data początkowa i data końcowa" +
        "  ->Przykład: EUR 2013-01-28 2013-01-31";

    public static final String WRONG_INPUT_ARGUMENTS = "Nie podano wymaganej liczby parametrów wejściowych." +
        "Potrzebna ilość to 3:" +
        "Waluta Data_Początkowa Data_Końcowa." +
        "Przykład: EUR 2013-01-28 2013-01-31";

    public static final String CURRENCY_PARSING_ERROR = "Błędna waluta.";
    public static final String CURRENCY_CODE = "kod waluty";
    public static final String FROM_DATE = "data początkowa";
    public static final String END_DATE = "data końcowa";
    public static final String AVG_BUY_COURSE = "średni kurs kupna";
    public static final String STANDARD_DEVIATION = "Odchylenie standardowe kursów sprzedaży";
    public static final String STANDARD_DEVIATION_ERROR = "Zbyt mało danych, odchylenie standardowe kursów sprzedaży";
    public static final String DATES_ERROR = "BŁĄD: Data początkowa jest większa od daty końcowej!";

    public static final String URL_GENERAL = "http://www.nbp.pl/kursy/xml/";
    public static final String URL_DATA_FOR_SPECIFIED_DATE = "http://www.nbp.pl/kursy/xml/dir";
}
