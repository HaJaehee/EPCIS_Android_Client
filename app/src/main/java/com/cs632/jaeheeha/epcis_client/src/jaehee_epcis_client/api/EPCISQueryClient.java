package com.cs632.jaeheeha.epcis_client.src.jaehee_epcis_client.api;

import android.content.Context;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.util.Log;
import android.widget.TextView;

import com.cs632.jaeheeha.epcis_client.src.jaehee_epcis_client.configuration.EPCISConfiguration;
import com.cs632.jaeheeha.epcis_client.src.jaehee_epcis_client.util.FlashLightUtilForL;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
* The EPCIS Client API is the program for Java programmed devices using EPCIS.
* First of all, please configure EPCISConfiguration.java file.
*
* @author  Jaehee Ha
* @version 2.0
* @since   2016.11.14 
*/

public class EPCISQueryClient extends EPCISClient{
	
	private String m_uri = "";
	private String m_EPCIS_AC_Server_URL = "";
	private String m_EPCISname = "";
	private String m_Username = "";
	private String m_EPCIS_Clienttoken = "";
	private String m_Query = "";
	private String m_strUrl;
	private String m_strResult;
	private String m_strCookie;
	private TextView m_tvEvent;
	private FlashLightUtilForL m_fl;


	public EPCISQueryClient (){
		m_EPCIS_AC_Server_URL = EPCISConfiguration.EPCIS_AC_Server_URL;
		m_EPCISname = EPCISConfiguration.EPCISname;
		m_Username = EPCISConfiguration.Username;
		m_EPCIS_Clienttoken = EPCISConfiguration.EPCIS_Clienttoken;
		m_Query = "";
		setURI();
	}
	
	public EPCISQueryClient (String a_EPCIS_AC_Server_URL, String a_EPCISname, String a_Username, String a_EPCIS_Clienttoken){
		m_EPCIS_AC_Server_URL = a_EPCIS_AC_Server_URL;
		m_EPCISname = a_EPCISname;
		m_Username = a_Username;
		m_EPCIS_Clienttoken = a_EPCIS_Clienttoken;
		m_Query = "";
		setURI();
	}
	
	public EPCISQueryClient (String a_EPCIS_AC_Server_URL, String a_EPCISname, String a_Username, String a_EPCIS_Clienttoken, String a_Query){
		m_EPCIS_AC_Server_URL = a_EPCIS_AC_Server_URL;
		m_EPCISname = a_EPCISname;
		m_Username = a_Username;
		m_EPCIS_Clienttoken = a_EPCIS_Clienttoken;
		m_Query = a_Query;
		setURI();
	}
	
	public void setQuery (String a_Query)
	{
		m_Query = a_Query;
		setURI();
	}
	
	public String getQuery ()
	{
		return m_Query;
	}
	
	public void addQuery (String a_Query)
	{
		m_Query = m_Query + "&" + a_Query;
		setURI();
	}
	
	public void doQuery()
	{
		this.execute();
	}
	
	private void setURI()
	{
		this.m_uri = "http://"+m_EPCIS_AC_Server_URL+"/qryepcis/"+m_EPCISname+"/user/"+m_Username+"/token/"+m_EPCIS_Clienttoken+"/apiquery?"+m_Query;
	}

	public void setEventTextView(TextView a_tv){
		this.m_tvEvent = a_tv;
	}

	public void setCamera (FlashLightUtilForL a_fl) { m_fl = a_fl; }

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		m_strUrl = m_uri; //탐색하고 싶은 URL이다.
		m_strResult = "";
	}

	@Override
	protected Void doInBackground(Void... voids) {
		try{
			URL Url = new URL(m_strUrl);
			HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);

			m_strCookie = conn.getHeaderField("Set-Cookie");

			InputStream is = conn.getInputStream();

			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String line;

			while ((line = reader.readLine()) != null) {
				builder.append(line+ "\n");
			}

			m_strResult = builder.toString();

		}catch(MalformedURLException | ProtocolException exception) {
			exception.printStackTrace();
		}catch(IOException io){
			io.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
		Log.e("onPostExecute:","wtf1");
		m_tvEvent.append(m_strResult);
		Log.e("onPostExecute:","wtf2");

		XPathFactory xpathfactory = XPathFactory.newInstance();
		XPath xpath = xpathfactory.newXPath();
		InputSource source = new InputSource(new StringReader(m_strResult));
		String power = null;
		Log.e("onPostExecute:","wtf3");
		try {
			power = xpath.evaluate("/EPCISQueryDocumentType/EPCISBody/*[local-name()='QueryResults']/resultsBody/EventList/ObjectEvent/*[local-name()='rampPower']", source);
			Log.e("rampPower:","wtf4"+power);
			if (power.equals("ON")) {
				m_fl.turnOnFlashLight();
			}
			else if (power.equals("OFF")) {
				m_fl.turnOffFlashLight();
			}


		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}