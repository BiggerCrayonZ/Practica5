package reza.raul.practica5;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity {

    TextView textId;
    TextView textContent;

    Button btnMainRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textId = (TextView) findViewById(R.id.textId);
        textContent = (TextView) findViewById(R.id.textContent);
        btnMainRequest = (Button) findViewById(R.id.boton1);
        btnMainRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpRequestTask().execute();
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Respuesta> {

        @Override
        protected Respuesta doInBackground(Void... params) {

            try {
                // The connection URL
                String url = "http://rest-service.guides.spring.io/greeting";

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();

                // Add the String message converter
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                // Make the HTTP GET request, marshaling the response to a String
                Respuesta respuesta = restTemplate.getForObject(url, Respuesta.class);
                return respuesta;

            } catch (Exception e) {
                Log.e("WEB Request", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Respuesta respuesta) {
            super.onPostExecute(respuesta);

            textId.setText(respuesta.getId());
            textContent.setText(respuesta.getContent());
        }
    }


}

class Respuesta {
    private String id;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}