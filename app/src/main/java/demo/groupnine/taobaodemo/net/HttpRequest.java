package demo.groupnine.taobaodemo.net;

import com.google.gson.Gson;
import demo.groupnine.taobaodemo.homepage.Category;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    private static String server = "192.168.123.123:8080";

    // default empty constructor

    // methods for application to use

    public static void getCategory(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/getCategory.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    Category c = g.fromJson(JsonStr, Category.class);

                    if (listener != null) {
                        listener.onFinish(c);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }

    // TODO by 董豪

    // low level methods

    public static byte[] getUrlBytes(String urlSpec)
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

    public static String getUrlString(String urlSpec)
            throws IOException
    {
        return new String(getUrlBytes(urlSpec));
    }

    // server address setting

    public static void setServer(String addr)
    {
        server = addr;
    }
}
