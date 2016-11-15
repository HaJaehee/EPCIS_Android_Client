package com.cs632.jaeheeha.epcis_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cs632.jaeheeha.epcis_client.src.jaehee_epcis_client.EPCISEventHandler;
import com.cs632.jaeheeha.epcis_client.src.jaehee_epcis_client.configuration.EPCISConfiguration;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText m_etUserName;
    private EditText m_etClientToken;
    private EditText m_etEPCISName;
    private TextView m_tvEvent;
    private ToggleButton m_tbRampPower;
    private Button m_btnCapture;
    private Button m_btnQuery;
    private String m_strRampPower = "OFF";
    private EPCISEventHandler m_eh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_etEPCISName = (EditText) findViewById(R.id.etEPCISName);
        m_etEPCISName.setText(EPCISConfiguration.EPCISname);
        m_etClientToken = (EditText) findViewById(R.id.etClientToken);
        m_etClientToken.setText(EPCISConfiguration.EPCIS_Clienttoken);
        m_etUserName = (EditText) findViewById(R.id.etUserName);
        m_etUserName.setText(EPCISConfiguration.Username);
        m_tvEvent = (TextView) findViewById(R.id.tvEvent);
        m_tbRampPower = (ToggleButton) findViewById(R.id.tbRampPower);
        m_tbRampPower.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    m_strRampPower = "ON";
                else
                    m_strRampPower = "OFF";
            }
        });
        m_btnCapture = (Button) findViewById(R.id.btnCapture);
        m_btnCapture.setOnClickListener(this);
        m_btnQuery = (Button) findViewById(R.id.btnQuery);
        m_btnQuery.setOnClickListener(this);

        m_eh = new EPCISEventHandler(m_tvEvent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnCapture:
                setEPCISConfigruation();
                m_eh.caputre(m_strRampPower);
                break;
            case R.id.btnQuery:
                setEPCISConfigruation();
                m_eh.query();
                break;
        }
    }
    
    public void setEPCISConfigruation()
    {
        EPCISConfiguration.EPCIS_Clienttoken=m_etClientToken.getText().toString();
        EPCISConfiguration.EPCISname=m_etEPCISName.getText().toString();
        EPCISConfiguration.Username=m_etUserName.getText().toString();
    }
}
