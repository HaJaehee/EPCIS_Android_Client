package com.cs632.jaeheeha.epcis_client.src.jaehee_epcis_client.api;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.cs632.jaeheeha.epcis_client.src.jaehee_epcis_client.configuration.EPCISConfiguration;

import org.json.JSONException;
import org.json.JSONObject;


/**
* The EPCIS Client API is the program for Java programmed devices using EPCIS.
* First of all, please configure EPCISConfiguration.java file.
*
* @author  Jaehee Ha
* @version 2.0
* @since   2016.11.14 
*/

public class EPCISCaptureClient extends EPCISClient{
	
	
	private String m_uri = "";
	private String m_EPCIS_AC_Server_URL = "";
	private String m_EPCISname = "";
	private String m_Username = "";
	private String m_EPCIS_Clienttoken = "";
	private JSONObject m_httpBody;
	private String m_Event;
	private String m_strUrl;
	private String m_strResult;
	private String m_strCookie;
	private TextView m_tvEvent;
	
	public EPCISCaptureClient () {
		m_EPCIS_AC_Server_URL = EPCISConfiguration.EPCIS_AC_Server_URL;
		m_EPCISname = EPCISConfiguration.EPCISname;
		m_Username = EPCISConfiguration.Username;
		m_EPCIS_Clienttoken = EPCISConfiguration.EPCIS_Clienttoken;
		m_httpBody = new JSONObject();
		try {
			m_httpBody.put("token", m_EPCIS_Clienttoken);
			m_httpBody.put("epcisevent", "");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		setURI();
	}
	
	public EPCISCaptureClient (String a_EPCIS_AC_Server_URL, String a_EPCISname, String a_Username, String a_EPCIS_Clienttoken){
		m_EPCIS_AC_Server_URL = a_EPCIS_AC_Server_URL;
		m_EPCISname = a_EPCISname;
		m_Username = a_Username;
		m_EPCIS_Clienttoken = a_EPCIS_Clienttoken;
		m_httpBody = new JSONObject();
		try {
			m_httpBody.put("token", m_EPCIS_Clienttoken);
			m_httpBody.put("epcisevent", "");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		setURI();
	}
	
	public EPCISCaptureClient (String a_EPCIS_AC_Server_URL, String a_EPCISname, String a_Username, String a_EPCIS_Clienttoken, String a_Event){
		m_EPCIS_AC_Server_URL = a_EPCIS_AC_Server_URL;
		m_EPCISname = a_EPCISname;
		m_Username = a_Username;
		m_EPCIS_Clienttoken = a_EPCIS_Clienttoken;
		m_Event = a_Event;
		m_httpBody = new JSONObject();

		try {
			m_httpBody.put("token", m_EPCIS_Clienttoken);
			m_httpBody.put("epcisevent", m_Event);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setURI();
	}
	
	public void setEvent (String a_Event)
	{
		if (a_Event == null)
			a_Event = "";
		m_Event = a_Event;
		m_httpBody.remove("epcisevent");
		try {
			m_httpBody.put("epcisevent", m_Event);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getEvent ()
	{
		return m_Event;
	}
	
	public void addEvent (String a_Event)
	{
		if (a_Event == null)
			a_Event = "";
		m_Event = m_Event + a_Event;
		m_httpBody.remove("epcisevent");
		try {
			m_httpBody.put("epcisevent", m_Event);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void doCapture()
	{
		this.execute();
	}
	
	private void setURI()
	{
		this.m_uri = "http://"+m_EPCIS_AC_Server_URL+"/captureepcis/"+m_EPCISname+"/user/"+m_Username+"/apicapture";
	}

	public void setEventTextView(TextView a_tv){
		this.m_tvEvent = a_tv;
	}


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		m_strUrl = m_uri;
		m_strResult = "";
	}
	@Override
	protected Void doInBackground(Void... voids) {
		try{
			URL Url = new URL(m_strUrl);
			HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");

			OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
			os.write(m_httpBody.toString());
			os.flush();

			m_strCookie = conn.getHeaderField("Set-Cookie");

			StringBuilder sb = new StringBuilder();

			int HttpResult = conn.getResponseCode();

			if(HttpResult == HttpURLConnection.HTTP_OK){

				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));

				String line = null;

				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}

				br.close();

				m_strResult = sb.toString();

			}else{
				m_strResult = conn.getResponseMessage();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
		m_tvEvent.append(m_strResult);
	}
}
