package demo.groupnine.taobaodemo.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {

    // default empty constructor

    // methods for application to use

    public void checkUserLogin(String UrlSpec, HttpCallbackListener listener)
    {
        // TODO
    }

    // TODO by 董豪

    // low level methods

    public byte[] getUrlBytes(String urlSpec)
            throws IOException
    {
        URL url = new URL(urlSpec);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = conn.getInputStream();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(conn.getResponseMessage()
                        + ": with"
                        + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            conn.disconnect();
        }
    }

    public String getUrlString(String urlSpec)
            throws IOException
    {
        return new String(getUrlBytes(urlSpec));
    }
}
