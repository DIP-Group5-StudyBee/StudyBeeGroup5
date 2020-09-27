package com.example.studybee;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpAsyncTaskForGet extends AsyncTask<String, Void, String> {

    private final static String TAG = "HttpAsynTask";
    private OnTaskCompleted listener;

    public HttpAsyncTaskForGet(OnTaskCompleted listener) {
        this.listener=listener;
    }

    public static String GET(String url, String data) {
        String result = "";
        try {

            Log.d(TAG, "Sending data["+data+"]");

            URL urlString = new URL(url+"?"+data);
            HttpURLConnection urlConnection = (HttpURLConnection)
                    urlString.openConnection();
            InputStream inputStream = null;
            try {
                inputStream = new
                        BufferedInputStream(urlConnection.getInputStream());
                // convert inputstream to string
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";
            } finally {
                if (inputStream!=null)
                    inputStream.close();
                if (urlConnection!=null)
                    urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    @Override
    protected String doInBackground(String... urls) {
        return GET(urls[0], urls[1]);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String response) {
        Log.d (TAG, response);
        listener.onTaskCompleted(response);

    }
}

