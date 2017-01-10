package demo.groupnine.taobaodemo.shoppingcart;


public class ToSettleOrder {
    public String shopName;
    public String goodsNum;
    public String price;

    // constructor

    public ToSettleOrder(String name, String num, String p)
    {
        shopName = name;
        goodsNum = num;
        price = p;
    }
}
