package com.fstream.flickrstream;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Smarty on 18/05/16.
 */
public class HTTPRequester {

    private static int CONNECT_TIMEOUT_MS = 5000;
    private static int READ_TIMEOUT_MS = 15000;

    public static ByteArrayOutputStream getRequest(String urlString) {
        HttpURLConnection urlConnection = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            int response = urlConnection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                urlConnection.setConnectTimeout(CONNECT_TIMEOUT_MS);
                urlConnection.setReadTimeout(READ_TIMEOUT_MS);
                is = new BufferedInputStream(urlConnection.getInputStream());

                int size = 1024;
                byte[] buffer = new byte[size];

                baos = new ByteArrayOutputStream();
                int read = 0;
                while ((read = is.read(buffer)) != -1) {
                    if (read > 0) {
                        baos.write(buffer, 0, read);
                        buffer = new byte[size];
                    }

                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return baos;
    }
}
