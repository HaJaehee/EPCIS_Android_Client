package com.cs632.jaeheeha.epcis_client.src.jaehee_epcis_client;

import android.widget.TextView;

import com.cs632.jaeheeha.epcis_client.src.jaehee_epcis_client.api.EPCISCaptureClient;
import com.cs632.jaeheeha.epcis_client.src.jaehee_epcis_client.api.EPCISQueryClient;

/**
* The EPCIS Client API is the program for Java programmed devices using EPCIS.
* First of all, please configure EPCISConfiguration.java file.
*
* @author  Jaehee Ha
* @version 2.0
* @since   2016.11.14 
*/

public class EPCISEventHandler {

	private TextView m_tv;

	public EPCISEventHandler (TextView a_tv){
		setEventTextView(a_tv);
	}

	public void query() {

		//EPCIS Query Client Example
		EPCISQueryClient queryClient = new EPCISQueryClient();
		//= new EPCISQueryClient(EPCISConfiguration.EPCIS_AC_Server_URL,EPCISConfiguration.EPCISname,EPCISConfiguration.Username,EPCISConfiguration.EPCIS_Clienttoken);

		String query = "eventCountLimit=1&orderBy=recordTime";
		queryClient.setEventTextView(m_tv);
		queryClient.setQuery(query);
		queryClient.doQuery();

	}

	public void caputre(String a_strRampPower) {
		//EPCIS Capture Client Example
		EPCISCaptureClient captureClient = new EPCISCaptureClient();
		//= new EPCISCaptureClient(EPCISConfiguration.EPCIS_AC_Server_URL,EPCISConfiguration.EPCISname,EPCISConfiguration.Username,EPCISConfiguration.EPCIS_Clienttoken);


		String epcisevent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> "
				+ " <!DOCTYPE project> "
				+ " <epcis:EPCISDocument xmlns:epcis=\"urn:epcglobal:epcis:xsd:1\" "
				+ " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+ " xmlns:example=\"http://ns.example.com/epcis\" creationDate=\"2005-07-11T11:30:47.0Z\" "
				+ " schemaVersion=\"1.2\"> "
				+ " <EPCISBody> "
				+ " <EventList> "
				+ " <ObjectEvent> "
				+ " <eventTime>2005-04-04T20:33:31.116-06:00</eventTime> "
				+ " <eventTimeZoneOffset>-09:00</eventTimeZoneOffset> "
				+ " <epcList> "
				+ " <epc>urn:epc:id:sgtin:0614141.107346.2018</epc> "
				+ " </epcList> "
				+ " <action>OBSERVE</action> "
				+ " <example:rampPower>" + a_strRampPower + "</example:rampPower> "
				+ " </ObjectEvent> "
				+ " </EventList> "
				+ " </EPCISBody> "
				+ " </epcis:EPCISDocument> ";
		captureClient.setEventTextView(m_tv);
		captureClient.setEvent(epcisevent);
		captureClient.doCapture();
	}

	public void setEventTextView (TextView a_tv){
		m_tv=a_tv;
	}
}
