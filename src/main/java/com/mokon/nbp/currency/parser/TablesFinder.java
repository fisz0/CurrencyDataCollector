package com.mokon.nbp.currency.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mokon.nbp.currency.parser.Messages.URL_DATA_FOR_SPECIFIED_DATE;

public class TablesFinder {

    static Logger LOGGER = LoggerFactory.getLogger(TablesFinder.class);

    private List<String> currencyTables;
    private int year;

    public TablesFinder() {
        currencyTables = new ArrayList<>();
    }

    public void lookForTables() {
        getCurrentDateFormated();
        for (int i = 2002; i <= year; i++) {
            URL dir = getUrl(i);
            BufferedReader in = getBufferedReader(dir);
            readFromXml(in);
            closeBufferedReader(in);
        }
    }

    private URL getUrl(int i) {
        LOGGER.info("Connecting with bank service.");
        URL dir = null;
        try {
            if (i != year) {
                dir = new URL(URL_DATA_FOR_SPECIFIED_DATE + i + ".txt");
            } else if (i == year) {
                dir = new URL(URL_DATA_FOR_SPECIFIED_DATE + ".txt");

            }
        } catch (MalformedURLException e) {
            LOGGER.error("Can not connect.", e);
        }
        return dir;
    }

    private BufferedReader getBufferedReader(URL dir) {
        LOGGER.info("Buffering stream.");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(dir.openStream()));
        } catch (IOException e) {
            LOGGER.error("Can not create BufferedReader with given URL.", e);
        }
        return in;
    }

    private void readFromXml(BufferedReader in) {
        LOGGER.info("Reading from XML file.");
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.charAt(0) == 'c') {
                    currencyTables.add(inputLine);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Can not read from XML file.", e);
        }
    }


    private void closeBufferedReader(BufferedReader in) {
        LOGGER.info("Closing stream.");
        try {
            in.close();
        } catch (IOException e) {
            LOGGER.error("Can not close stream.", e);
        }
    }


    private void getCurrentDateFormated() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.year = localDate.getYear();
    }

    public List<String> getCurrencyTables() {
        return currencyTables;
    }

}
