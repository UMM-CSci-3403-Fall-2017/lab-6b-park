package xrate;

import java.net.*;
import java.io.*;

import java.net.URL;
import java.net.MalformedURLException;
import java.net.URLConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * Provide access to basic currency exchange rate services.
 * 
 * @author Sungjae Park
 */
public class ExchangeRateReader {

	public String baseURL;


    /**
     * Construct an exchange rate reader using the given base URL. All requests
     * will then be relative to that URL. If, for example, your source is Xavier
     * Finance, the base URL is http://api.finance.xaviermedia.com/api/ Rates
     * for specific days will be constructed from that URL by appending the
     * year, month, and day; the URL for 25 June 2010, for example, would be
     * http://api.finance.xaviermedia.com/api/2010/06/25.xml
     * 
     * @param baseURL
     *            the base URL for requests
     */
    public ExchangeRateReader(String baseURL) {
	   this.baseURL = baseURL;

    }

    /**
     * Get the exchange rate for the specified currency against the base
     * currency (the Euro) on the specified date.
     * 
     * @param currencyCode
     *            the currency code for the desired currency
     * @param year
     *            the year as a four digit integer
     * @param month
     *            the month as an integer (1=Jan, 12=Dec)
     * @param day
     *            the day of the month as an integer
     * @return the desired exchange rate
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public float getExchangeRate(String currencyCode, int year, int month, int day) throws IOException, ParserConfigurationException, SAXException {
	    
	    float rate = 0;
	    String Year = baseURL + Integer.toString(year) + "/";
	    String Month = Integer.toString(month) + "/";
	    String Day = Integer.toString(day);

	    if(month < 10) {
		    Month = "0" + Integer.toString(month) + "/";
	    }

	    if (day < 10) {
		    Day = "0" + Integer.toString(day);
	    
	    }

	    
	    //create URL


	    String url = url + Year + Month + Day + ".xml";
	    URL newURL = new URL(url);
	    InputStream xmlStream = newURL.openStream();

	   // String exchangerate = baseURLcurrency + Year + "/" + Month + "/" + Day + ".xml";
	   // System.out.println(exchangerate);

	    DocumentBuilderFactory dbf = DocumnetBuilderFactory.newInstance();
	    DocumentBuilderFacotry db = dbf.newDocumentBuilder();
	    Document doc = db.parse(xmlStream);
	    doc.getDocumentElement().normalize();

	    //create nodeList

	    NodeList exchange = doc.getElementsByTagName("currency_code");
	    NodeList rates = doc.getElementsByTagName("rate");

	    float returns = Float.MIN_VALUE;
	    for(int i = 0; i < codes.getLength(); i++) {
		    if(codes.item(i).getNodeType() == codes.item(i).ELEMENT_NODE) {
			    if(currencycode.equals(code.item(i).getTextContent())) {
				    returns = new Float(rates.item(i).getTextContent());
			    }
		    }
	    }
	    return returns
    }


/*


	    float Errors = -1;
	    
	    URL exchangerateURL = new URL(exchangerate); 
	    InputStream xmlStream = exchangerateURL.openStream();   
	    
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document doc = db.parse(xmlStream);  
	    doc.getDocumentElement().normalize();

	    NodeList lists = doc.getElementsByTagName("fx");
	   // Node ExchangeCurrency = doc.getElementByTagName("rate");
	    
	 /**   for(int i = 0; i < nl.getLength(); i++) {
		    ExchangeCurrency = lists.item(i);
		    NodeList Subgroups = ExchangeCurrency.getChildNodes();
		    Node CC = ExchangeCurrency.item(1);

		    if(currencyCode.equals(CC.getTextContent())) {
			    Node RATES = Subgroups.item(3);
			    Errors = new Float(RATES.getTextContent());
			    break;
		    }
	    }
	    return Errors;
    } 

    

	    for(int i = 0; i < lists.getLength(); i++) {
		    Node ExchangeCurrency = lists.item(i);
		    NodeList Subgroups = ExchangeCurrency.getChildNodes();
		    Node CountryCode = Subgroups.item(i);

		    if(currencyCode.equals(CountryCode.getTextContent())){
			    Node rateExchange = Subgroups.item(3);
			    Errors = new Float(rateExchange.getTextContext());
			    break;
		    }
	    }

	    return Errors;
}


*/

    /**
     * Get the exchange rate of the first specified currency against the second
     * on the specified date.
     * 
     * @param currencyCode
     *            the currency code for the desired currency
     * @param year
     *            the year as a four digit integer
     * @param month
     *            the month as an integer (1=Jan, 12=Dec)
     * @param day
     *            the day of the month as an integer
     * @return the desired exchange rate
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public float getExchangeRate(String fromCurrency, String toCurrency, int year, int month, int day) throws ParserConfigurationException, SAXException, IOException {

	    String Year = baseURL + Integer.toString(year) + "/";
	    String Month = Integer.toString(month) + "/";
	    String Day = Integer.toString(day);

	    if(month < 10) {
		    Month = "0" + Integer.toString(month) + "/";
	    }

	    if(day < 10) {
		    Day = "0" + Integer.toString(day);
	    }

	    String url = url + Month + Day + ".xml";

	    URL newURL = new URL(url);
	    InputStream xmlStream = newURL.openStream();

	    //make documents
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = docBuilderFactory.newDocumentBuilder();
	    Document doc = docBuilder.parse(xmlStream);
	    doc.getDocumentElement().normalize();

	    float returns = Float.MIN_VALUE;
	    float rates = Float.MIN_VALUE;
	    float others = Float.MIN_VALUE;

	    // make nodelists
	    NodeList currency = doc.getElementsByTagName("currency_code");
	    NodeList rates = doc.getElementsByTagName("rate");

	    // for loop to find currency
	    //
	    
	    for(int i = 0; i < currency.getLength(); i++) {
		    if(currency.item(i).getNodeType() == currency.item(i).ELEMENT_NODE) {
			    if(fromCurrency.equals(currency.item(i).getTextContent())){
				    rates = new Float(rates.item(i).getTextContent());
			    }
			    if(toCurrency.equals(codes.item(i).getTextContenct())){
				    others = new Float(rates.item(i).getTextContent());
			    }
		    }
	    }
	    returns = rates/others;

	    return returns;



    }
}

