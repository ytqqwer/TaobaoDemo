package demo.groupnine.taobaodemo.shoppingcart;

import java.util.ArrayList;

/*
 * 一个店铺算一个购物车 所以，用户所看到的购物车其实是多个购物车，每个购物车分别属于不同的店铺
 */

public class ShoppingCart {
    public String shopId;
    public String shopName;
    public ArrayList<GoodsItemInSC> goodsInThisShop;
}
