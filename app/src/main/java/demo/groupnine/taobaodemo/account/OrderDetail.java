package demo.groupnine.taobaodemo.account;

import java.util.ArrayList;

public class OrderDetail {
    public String orderId;
    public String trackingNumber;
    public String orderStatus;
    public String username;
    public OrderReceiver receiver;
    public ArrayList<GoodsInOrder> goodsInOrder;
    public String payMethod;
    public String orderTime;
    public String completeTime;
    public String annotation;
    public String total;
}
