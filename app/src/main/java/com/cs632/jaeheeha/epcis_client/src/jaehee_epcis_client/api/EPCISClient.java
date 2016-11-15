package com.cs632.jaeheeha.epcis_client.src.jaehee_epcis_client.api;

import android.net.http.HttpResponseCache;
import android.os.AsyncTask;

/**
* The EPCIS Client API is the program for Java programmed devices using EPCIS.
* First of all, please configure EPCISConfiguration.java file.
*
* @author  Jaehee Ha
* @version 2.0
* @since   2016.11.14 
*/
public class EPCISClient extends AsyncTask<Void, Void, Void> {
	private String m_uri = "";
	private String m_EPCIS_AC_Server_URL = "";
	private String m_EPCISname = "";
	private String m_Username = "";
	private String m_EPCIS_Clienttoken = "";

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	@Override
	protected Void doInBackground(Void... params) {
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
	}
}