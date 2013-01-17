package org.hamisto.filmista;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

public class TopParsingImdb {

	List<String> Top;

	static TopParsingImdb instance;

	public static TopParsingImdb getInstance() {
		if (instance == null) {
			instance = new TopParsingImdb();
		}
		return instance;
	}

	TopParsingImdb() {

		Top = new ArrayList<String>();
		URI url = null;

		{

			try {
				url = new URI(
						"http://www.imdb.com/search/title?num_votes=5000,&sort=moviemeter&title_type=tv_series");
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
			
				final int CONNECTION_TIMEOUT = 20000;

				HttpParams httpparams = new BasicHttpParams();

				// Connection timeouts help reduce the amount of memory
				// resources that are consumed by idle connections
				HttpConnectionParams.setConnectionTimeout(httpparams,
						CONNECTION_TIMEOUT);

				HttpConnectionParams.setSoTimeout(httpparams,
						CONNECTION_TIMEOUT);
				DefaultHttpClient httpclient = new DefaultHttpClient(httpparams);

				// Spoof Firefox user agent to force a result from The Pirate
				// Bay
				httpclient
						.getParams()
						.setParameter(
								"http.useragent",
								"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

				HttpGet httpget = new HttpGet(url);

				// Make request
				HttpResponse response = httpclient.execute(httpget);

				// Read HTML response
				InputStream instream = response.getEntity().getContent();

				Tidy tidy = new Tidy();
				tidy.setQuiet(true);
				tidy.setShowWarnings(false);
				Document response1 = tidy.parseDOM(instream, null);

				// "wlb_wrapper btn2_wrapper btn2_active wlb_lite"
				XPathFactory factory = XPathFactory.newInstance();

				XPath xPath = factory.newXPath();
				String pattern = "//tr[position()< 9]/td[@class='title']/a/@href[1]";

				NodeList nodes = null;
				try {
					nodes = (NodeList) xPath.evaluate(pattern, response1,
							XPathConstants.NODESET);
				} catch (XPathExpressionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(nodes.getLength());
				for (int i = 0; i < nodes.getLength(); i++) {

					// add element to Top List
					Top.add(nodes
							.item(i)
							.getNodeValue()
							.toString()
							.substring(
									7,
									nodes.item(i).getNodeValue().toString()
											.length() - 1));
					
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<String> getTop() {
		return Top;
	}

	public void setTop(List<String> top) {
		Top = top;
	}
}
