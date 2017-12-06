package com.example.roger.oving5;

/**
 * Created by Bjarne on 06/10/17.
 */

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

class HttpWrapper {

    private CookieManager cm = new CookieManager(null, CookiePolicy.ACCEPT_ALL);

    /* Sends HTTP GET request and returns body of response from server as String */
    public String httpGet(String urlToServer, Map<String,String> params) throws IOException {
        CookieHandler.setDefault(cm);

        Uri.Builder builder = Uri.parse(urlToServer).buildUpon();
        for(Map.Entry<String,String> entry : params.entrySet()){
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }

        URL url = new URL(builder.build().toString());
        Log.i("***URL***", url.toString());

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        String response = "";
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;

        try {
            /* Read body of HTTP-message from server */

            Log.i("***Headers***", urlConnection.getHeaderFields().toString());

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            response = sb.toString();
        }catch (Exception e){
            Log.i("httpGET", "Error reading from webserver");
            response = "Error reading from webserver";
        }finally{
            if(br != null) {
                try {
                    br.close();
                } catch (IOException ie) {
                    Log.i("httpGET", "Error closing input stream");
                }
            }
        }

        return response;
    }
}