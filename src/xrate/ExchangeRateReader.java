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

	public String baseURLcurrency;


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
	   this.baseURLcurrency = baseURL;

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
    public float getExchangeRate(String currencyCode, int year, int month, int day) 
		    
		    throws IOException, ParserConfigurationException, SAXException {

	    String Year = "" + year;
	    String Month = "" + month;
	    String Day = "" + day;
		
	    	
	    if(month < 10) {
		    Month = "0" + month;
	    }

	    if (day < 10) {
		    Day = "0" + day;
	    
	    }

	    
	    
	    String exchangerate = baseURLcurrency + Year + "/" + Month + "/" + Day + ".xml";
	    System.out.println(exchangerate);
	   //



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

    */

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
    public float getExchangeRate(String fromCurrency, String toCurrency, int year, int month, int day) 
		    throws ParserConfigurationException, SAXException, IOException {
        float here = this.getExchangeRate(fromCurrency, year, month, day);
	float destination  = this.getExchangeRate(toCurrency, year, month, day);
	
	return here/destination;



    }
}

