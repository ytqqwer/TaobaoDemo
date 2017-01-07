package demo.groupnine.taobaodemo.net;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import demo.groupnine.taobaodemo.account.OrderBrief;
import demo.groupnine.taobaodemo.account.OrderDetail;
import demo.groupnine.taobaodemo.homepage.Category;
import demo.groupnine.taobaodemo.homepage.GoodsBrief;
import demo.groupnine.taobaodemo.homepage.GoodsDetail;
import demo.groupnine.taobaodemo.homepage.ShopInfo;
import demo.groupnine.taobaodemo.shoppingcart.Result;
import demo.groupnine.taobaodemo.shoppingcart.ShoppingCart;
import demo.groupnine.taobaodemo.shoppingcart.SuccessFail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpRequest {
    private static final String TAG = "HttpRequest";
    private static String server = "http://192.168.123.123:8080/oss";
    private static String mCookie;

    public static boolean loginSuccess;
    public static boolean hasTriedLogin;

    // default empty constructor

    // methods for application to use

    // 获取图像
    public static void getImage(final String imgAddr, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String[] serverSegArr = server.split("/");
                    String imgServer = serverSegArr[0] + "//" + serverSegArr[2] + "/images";
                    String Url = imgServer + imgAddr;
                    byte[] imgBytes = getUrlBytes(Url);

                    InputStream is = new ByteArrayInputStream(imgBytes);
                    BitmapDrawable imgDrawable = new BitmapDrawable(is);

                    if (listener != null) {
                        listener.onFinish(imgDrawable);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();

    }


    //登录
    public synchronized static void login(final String UrlParam)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String UrlStr = server + "/checkUserLogin.action" + UrlParam;
                    URL url = new URL(UrlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    mCookie = null;

                    try {
                        conn.setInstanceFollowRedirects(false);

                        // 先不考虑多线程同时访问 mCookie 的问题
                        String[] cookieLine = conn.getHeaderField("Set-Cookie").split(";");
                        mCookie = cookieLine[0];
                        Log.d(TAG, "get cookie: " + mCookie);

                        // 根据重定向地址判定是否登录成功
                        if (conn.getHeaderField("Location").contains("index")) {
                            loginSuccess = true;
                        } else {
                            loginSuccess = false;
                        }

                    } finally {
                        conn.disconnect();
                    }
                } catch (Exception e) {
                    // do nothing
                    e.printStackTrace();
                } finally {
                    // 为了跟 LoginActivity 协作
                    hasTriedLogin = true;
                }
            }
        }).start();
    }

    //获取分类信息
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
                    Type t = new TypeToken<ArrayList<Category>>() {
                    }.getType();
                    ArrayList<Category> cs = g.fromJson(JsonStr, t);

                    if (listener != null) {
                        listener.onFinish(cs);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //通过一级分类获取商品图像
    public static void getGoodsImagesByLevelOne(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/getGoodsImagesByLevelOne.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    Type t = new TypeToken<ArrayList<GoodsBrief>>() {
                    }.getType();
                    ArrayList<GoodsBrief> gs = g.fromJson(JsonStr, t);

                    if (listener != null) {
                        listener.onFinish(gs);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //通过分类获取商品基本信息
    public static void getGoodsBriefByCategory(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/getGoodsBriefByCategory.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    Type t = new TypeToken<ArrayList<GoodsBrief>>() {
                    }.getType();
                    ArrayList<GoodsBrief> gs = g.fromJson(JsonStr, t);

                    if (listener != null) {
                        listener.onFinish(gs);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //通过关键词获取商品基本信息
    public static void getGoodsBriefByKeyword(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/getGoodsBriefByKeyword.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    Type t = new TypeToken<ArrayList<GoodsBrief>>() {
                    }.getType();
                    ArrayList<GoodsBrief> gs = g.fromJson(JsonStr, t);

                    if (listener != null) {
                        listener.onFinish(gs);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //获取商品详细信息
    public static void getGoodsDetail(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/getGoodsDetail.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    GoodsDetail gd = g.fromJson(JsonStr, GoodsDetail.class);

                    if (listener != null) {
                        listener.onFinish(gd);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //获取商品所在店铺信息
    public static void getShopInfoByGoodsId(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/getShopInfoByGoodsId.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    ShopInfo s = g.fromJson(JsonStr, ShopInfo.class);

                    if (listener != null) {
                        listener.onFinish(s);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //获取购物车信息（所有商品）
    public static void getShoppingCart(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/getShoppingCart.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    Type t = new TypeToken<ArrayList<ShoppingCart>>() {
                    }.getType();
                    ArrayList<ShoppingCart> sc = g.fromJson(JsonStr, t);

                    if (listener != null) {
                        listener.onFinish(sc);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //添加到购物车
    public static void addToShoppingCart(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/addToShoppingCart.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    Result r = g.fromJson(JsonStr, Result.class);

                    if (listener != null) {
                        listener.onFinish(r);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //删除购物车中商品
    public static void deleteFromShoppingCart(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/deleteFromShoppingCart.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    Result r = g.fromJson(JsonStr, Result.class);

                    if (listener != null) {
                        listener.onFinish(r);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //直接修改商品数量
    public static void updateGoodsNumInShoppingCart(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/updateGoodsNumInShoppingCart.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    Result r = g.fromJson(JsonStr, Result.class);

                    if (listener != null) {
                        listener.onFinish(r);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //确认下单
    public static void confirmOrder(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/confirmOrder.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    Type t = new TypeToken<ArrayList<String>>() {
                    }.getType();
                    ArrayList<String> os = g.fromJson(JsonStr, t);

                    if (listener != null) {
                        listener.onFinish(os);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //确认支付
    public static void payOrder(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/payOrder.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    SuccessFail sf = g.fromJson(JsonStr, SuccessFail.class);

                    if (listener != null) {
                        listener.onFinish(sf);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //取消订单
    public static void cancelOrder(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/cancelOrder.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    Result c = g.fromJson(JsonStr, Result.class);

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


    //通过订单状态获取订单
    public static void getOrderByStatus(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/getOrderByStatus.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    Type t = new TypeToken<ArrayList<OrderBrief>>() {
                    }.getType();
                    ArrayList<OrderBrief> os = g.fromJson(JsonStr, t);

                    if (listener != null) {
                        listener.onFinish(os);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //通过订单号获取订单详情
    public static void getOrderById(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/getOrderById.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    OrderDetail o = g.fromJson(JsonStr, OrderDetail.class);

                    if (listener != null) {
                        listener.onFinish(o);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }


    //更新订单状态
    public static void updateOrderStatus(final String UrlParam, final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    String Url = server + "/updateOrderStatus.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    Result r = g.fromJson(JsonStr, Result.class);

                    if (listener != null) {
                        listener.onFinish(r);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }

    // low level methods

    public static byte[] getUrlBytes(String urlSpec)
            throws IOException
    {
        URL url = new URL(urlSpec);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (mCookie != null) {
            conn.addRequestProperty("Cookie", mCookie);
            Log.d(TAG, "sent cookie: " + mCookie);
        }

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
        server = "http://" + addr + "/OSS";
    }
}
