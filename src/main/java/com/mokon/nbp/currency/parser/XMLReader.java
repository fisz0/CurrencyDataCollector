package com.mokon.nbp.currency.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.mokon.nbp.currency.parser.Messages.URL_GENERAL;

public class XMLReader {

    static Logger LOGGER = LoggerFactory.getLogger(XMLReader.class);

    private TablesFinder tablesFinder;
    private List<BigDecimal> buy;
    private List<BigDecimal> sell;
    private Currency currency;
    private LocalDate fromDay;
    private LocalDate untilDay;

    public XMLReader(Currency currency, LocalDate fromDay, LocalDate untilDay) {
        this.fromDay = fromDay;
        this.untilDay = untilDay;
        this.currency = currency;
        buy = new ArrayList<>();
        sell = new ArrayList<>();
        tablesFinder = new TablesFinder();
    }

    public void getDataFromBank() {
        LOGGER.info("Getting desired data from bank.");
        tablesFinder.lookForTables();

        LocalDate startChecking = fromDay.minusDays(5);
        LocalDate stopChecking = untilDay.plusDays(5);

        DocumentBuilder documentBuilder = getDocumentBuilder();

        LOGGER.info("Iterating over received currency tables.");
        for (String currencyTable : tablesFinder.getCurrencyTables()) {
            readCurrencyData(startChecking, stopChecking, documentBuilder, currencyTable);
        }
    }

    private void readCurrencyData(LocalDate startChecking, LocalDate stopChecking, DocumentBuilder documentBuilder, String currencyTable) {
        if (compareDates(startChecking, stopChecking, getFileCreationDate(currencyTable))) {
            Document document = parseDocument(documentBuilder, currencyTable);
            document.getDocumentElement().normalize();
            readDataBetweenGivenDates(document);
        }
    }

    private LocalDate getFileCreationDate(String currencyTable) {
        return LocalDate.parse("20" + currencyTable.substring(currencyTable.length() - 6, currencyTable.length() - 4) + "-" + currencyTable.substring(currencyTable.length() - 4, currencyTable.length() - 2) + "-" + currencyTable.substring(currencyTable.length() - 2, currencyTable.length()));
    }


    private Document parseDocument(DocumentBuilder b, String currencyTableName) {
        Document document = null;
        try {
            document = b.parse(URL_GENERAL + currencyTableName + ".xml");
        } catch (SAXException e) {
            LOGGER.error("Can not parse desired XML file", e);
        } catch (IOException e) {
            LOGGER.error("Can not get desired XML file", e);
        }
        return document;
    }

    private DocumentBuilder getDocumentBuilder() {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = null;
        try {
            b = f.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.error("Parsing Exception!", e);
        }
        return b;
    }

    private void readDataBetweenGivenDates(Document doc) {
        LocalDate dataPublic = LocalDate.parse(doc.getElementsByTagName("data_publikacji").item(0).getTextContent());

        if (compareDates(fromDay, untilDay, dataPublic)) {
            NodeList nList = doc.getElementsByTagName("pozycja");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (getSectionByCurrencyCode(eElement)) break;
                }
            }
        }
    }

    private boolean getSectionByCurrencyCode(Element eElement) {
        if (currency.name().equals(eElement.getElementsByTagName("kod_waluty").item(0).getTextContent())) {
            NumberFormat df = NumberFormat.getInstance(Locale.GERMAN);
            getSellAndBuyCoursesFromXML(eElement, df);
            return true;
        }
        return false;
    }

    private void getSellAndBuyCoursesFromXML(Element eElement, NumberFormat df) {
        try {
            buy.add(BigDecimal.valueOf(df.parse(eElement.getElementsByTagName("kurs_kupna").item(0).getTextContent())
                .doubleValue()).round(CurrencyMathContext.getNewContext()));
            sell.add(BigDecimal.valueOf(df.parse(eElement.getElementsByTagName("kurs_sprzedazy").item(0).getTextContent())
                .doubleValue()).round(CurrencyMathContext.getNewContext()));
        } catch (ParseException e) {
            LOGGER.error("Can not parse number.", e);
        }
    }

    private boolean compareDates(LocalDate startChecking, LocalDate stopChecking, LocalDate dateFile) {
        return startChecking.compareTo(dateFile) <= 0 && stopChecking.compareTo(dateFile) >= 0;
    }


    public List<BigDecimal> getBuy() {
        return buy;
    }

    public List<BigDecimal> getSell() {
        return sell;
    }
}
