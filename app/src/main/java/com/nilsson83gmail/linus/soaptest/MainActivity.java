package com.nilsson83gmail.linus.soaptest;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<String>>{

    private final String NAMESPACE = "http://www.webserviceX.NET";
    private final String METHOD_NAME = "GetCitiesByCountry";

    private final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

    private final String URL = "http://www.webservicex.net/globalweather.asmx?WSDL";

    private int LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void init() {



    }

    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<String>>(this) {

            @Override
            public void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public List<String> loadInBackground() {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("CountryName", "Germany");

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransport = new HttpTransportSE(URL);

                try {
                    httpTransport.call(SOAP_ACTION, envelope);

                    return extrtactDataFromXmlResponse(envelope);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    private List<String> extrtactDataFromXmlResponse(SoapSerializationEnvelope envelope) {
        List<String> citiesList = new ArrayList<>();

        DocumentBuilderFactory documentBuilderFactory = new DocumentBuilderFactory.newInstance();
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
