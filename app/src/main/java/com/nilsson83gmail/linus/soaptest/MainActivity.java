package com.nilsson83gmail.linus.soaptest;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<String>>{

    private final String NAMESPACE = "http://service.linkon.se/additionalproducts/v1_5";
    //private final String NAMESPACE = "http://www.webserviceX.NET";
    private final String METHOD_NAME = "getServiceCatalogueList";
    //private final String METHOD_NAME = "GetCitiesByCountry";

    private final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

    private final String URL = "https://q-petra2services.linkon.se/additionalproducts/1_5/AdditionalProductsService";
    //private final String URL = "http://www.webservicex.net/globalweather.asmx?WSDL";

    private int LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(LOADER_ID, null, this);
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

                ArrayList<HeaderProperty> headerProperties = new ArrayList<>();
                headerProperties.add(new HeaderProperty("Username", "LIRB2T"));
                headerProperties.add(new HeaderProperty("Password", "xPB6g4jT"));
                headerProperties.add(new HeaderProperty("SalesUnitKey", "SalesUnitKey"));
                headerProperties.add(new HeaderProperty("EndUserId", "S102SW"));
                headerProperties.add(new HeaderProperty("RequestId", "X2xpbmtvbmxpbmVfd2ZfYl9mMDE6MDoxNDkxODMxMzA3Nzgz"));

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                //request.addProperty("CountryName", "Germany");

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransport = new HttpTransportSE(URL);

                try {
                    httpTransport.call("", envelope);

                    //return extrtactDataFromXmlResponse(envelope);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("LOG_TAG", envelope.bodyIn.toString());
                return null;
            }
        };
    }

    /*private List<String> extrtactDataFromXmlResponse(SoapSerializationEnvelope envelope) {
        List<String> citiesList = new ArrayList<>();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            String doc = String.valueOf(documentBuilder.parse(new InputSource(new StringReader(envelope.getResponse().toString()))));

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String list = doc.

    }*/

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
        TextView textView = (TextView) findViewById(R.id.empty_state_textview);
        textView.setText("Hej");
    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
