package com.example.soapservicesex1;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(() -> {
            String SOAP_ACTION = "http://yournamespace/sayHello";
            String METHOD_NAME = "sayHello";
            String NAMESPACE = "http://yournamespace/";
            String URL = "http://yourserver.com/hello?wsdl";

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("name", "Namrata");

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(SOAP_ACTION, envelope);

                final SoapObject response = (SoapObject) envelope.getResponse();
                runOnUiThread(() -> {
                    TextView textView = findViewById(R.id.textView);
                    textView.setText(response.toString());
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    TextView textView = findViewById(R.id.textView);
                    textView.setText("Error: " + e.getMessage());
                });
            }
        }).start();
    }
}
