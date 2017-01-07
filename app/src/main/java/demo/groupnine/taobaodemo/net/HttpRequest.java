package demo.groupnine.taobaodemo.net;

import com.google.gson.Gson;

import demo.groupnine.taobaodemo.homepage.Category;
import demo.groupnine.taobaodemo.homepage.GoodsBrief;
import demo.groupnine.taobaodemo.homepage.GoodsDetail;
import demo.groupnine.taobaodemo.homepage.ShopInfo;
import demo.groupnine.taobaodemo.shoppingcart.Result;
import demo.groupnine.taobaodemo.shoppingcart.ShoppingCart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpRequest {
    private static String server = "192.168.123.123:8080";

    // default empty constructor

    // methods for application to use


    //获取分类信息。
    public static void getCategory(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
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


//TODO     /getGoodsImagesByLevelOne.action


    //通过分类获取商品基本信息
    public static void getGoodsBriefByCategory(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Url = server + "/getGoodsBriefByCategory.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    GoodsBrief c= g.fromJson(JsonStr, GoodsBrief.class);

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

    //通过关键词获取商品基本信息
    public static void getGoodsBriefByKeyword(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Url = server + "/getGoodsBriefByKeyword.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    GoodsBrief c = g.fromJson(JsonStr, GoodsBrief.class);

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


    //获取商品详细信息
    public static void getGoodsDetail(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Url = server + "/getGoodsDetail.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    GoodsDetail c = g.fromJson(JsonStr, GoodsDetail.class);

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

    //获取商品所在店铺信息
    public static void getShopInfoByGoodsId(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Url = server + "/getShopInfoByGoodsId.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    ShopInfo c = g.fromJson(JsonStr, ShopInfo.class);

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

    //获取购物车信息（所有商品）
    public static void getShoppingCart(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Url = server + "/getShoppingCart.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    ArrayList<ShoppingCart> c = g.fromJson(JsonStr, ShoppingCart.class);

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


    //添加到购物车
    public static void addToShoppingCart(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Url = server + "/addToShoppingCart.action" + UrlParam;
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

    //删除购物车中商品

    public static void deleteFromShoppingCart(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Url = server + "/deleteFromShoppingCart.action" + UrlParam;
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

    //直接修改商品数量
    public static void updateGoodsNumInShoppingCart(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Url = server + "/updateGoodsNumInShoppingCart.action" + UrlParam;
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

    //确认下单
    public static void confirmOrder(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Url = server + "/confirmOrder.action" + UrlParam;
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
    public static void getOrderByStatus(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Url = server + "/getOrderByStatus.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    OrderBrief c = g.fromJson(JsonStr, OrderBrief.class);

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

    //取消订单
    public static void cancelOrder(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
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


    //通过订单号获取订单详情
    public static void getOrderById(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Url = server + "/getOrderById.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    OrderDetail c = g.fromJson(JsonStr, OrderDetail.class);

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

    //更新订单状态
    public static void updateOrderStatus(final String UrlParam, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Url = server + "/updateOrderStatus.action" + UrlParam;
                    String JsonStr = getUrlString(Url);

                    Gson g = new Gson();
                    OrderBrief c = g.fromJson(JsonStr, OrderBrief.class);

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


        //确认支付
        public static void payOrder(final String UrlParam, final HttpCallbackListener listener) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String Url = server + "/payOrder.action" + UrlParam;
                        String JsonStr = getUrlString(Url);

                        Gson g = new Gson();
                        SuccessFail c = g.fromJson(JsonStr, SuccessFail.class);

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
            throws IOException {
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
            throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    // server address setting

    public static void setServer(String addr) {
        server = addr;
    }
}
