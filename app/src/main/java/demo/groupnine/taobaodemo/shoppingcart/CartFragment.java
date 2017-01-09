package demo.groupnine.taobaodemo.shoppingcart;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;

import java.util.ArrayList;
import java.util.List;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import demo.groupnine.taobaodemo.R;
import demo.groupnine.taobaodemo.homepage.GoodsAttrString;
import demo.groupnine.taobaodemo.net.HttpCallbackListener;
import demo.groupnine.taobaodemo.net.HttpRequest;

public class CartFragment
        extends Fragment {
    private static String TAG = "CartFragment";

    // data model
    private List<ShoppingCart> mCarts;
    //shopping cart list widget
    private RecyclerView mCartRecyclerView;
    private CartAdapter mAdapter;
    private View mBlankListPrompt;
    // thread
    private boolean hasFetchedCart;

    // lifetime methods

    @Override
    public void onCreate(
            @Nullable
                    Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // TODO
        setHasOptionsMenu(true);
        getActivity().setTitle("Cart");

        fetchCartList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable
                                     ViewGroup container,
                             @Nullable
                                     Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        // shopping cart list widget
        mCartRecyclerView = (RecyclerView) v.findViewById(R.id.cart_recycler_view);
        mCartRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBlankListPrompt = v.findViewById(R.id.cart_blank_list);
        if (isAdded()) {
            updateCartListUI();
        }

        return v;
    }

    /*
     * 在这个方法里面写从其他 fragment 切换至此 Fragment 时，所要进行的操作
     */
    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            fetchCartList();
            updateCartListUI();
        }
    }


    // private methods

    private void fetchCartList()
    {
        hasFetchedCart = false;
        HttpRequest.getShoppingCart("",
                new HttpCallbackListener() {
                    @Override
                    public void onFinish(Object responese)
                    {
                        mCarts = (ArrayList<ShoppingCart>) responese;
                        hasFetchedCart = true;
                    }

                    @Override
                    public void onError(Exception e)
                    {
                        Log.d(TAG, "request failed");
                    }
                });

        while (!hasFetchedCart) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }

    private void updateCartListUI()
    {
        if (mCarts.size() == 0) {
            mCartRecyclerView.setVisibility(View.GONE);
            mBlankListPrompt.setVisibility(View.VISIBLE);
        } else {
            mBlankListPrompt.setVisibility(View.GONE);
            mCartRecyclerView.setVisibility(View.VISIBLE);

            mAdapter = new CartAdapter(mCarts);
            mCartRecyclerView.setAdapter(mAdapter);
        }
    }


////////////////////////////////////////////////////////////
// inner class CartHolder
//

    private class CartHolder
            extends RecyclerView.ViewHolder {

        // fields
        private LinearLayout mGoodsContainerLL;
        private List<View> mGoodsViewAdded;
        private List<String> mGoodsNums;

        // constructor

        public CartHolder(View itemView)
        {
            super(itemView);
            mGoodsContainerLL = (LinearLayout) itemView;
            mGoodsViewAdded = new ArrayList<>();
            mGoodsNums = new ArrayList<>();
        }

        // public methods

        public void bindCart(ShoppingCart c)
        {
            /* 1. 清除上一次添加进来的商品 */

            mGoodsNums.clear();
            for (View v : mGoodsViewAdded) {
                mGoodsContainerLL.removeView(v);
            }

            /* 2. 设置店铺名字 */

            TextView shopNameView = (TextView) mGoodsContainerLL.findViewById(R.id.cart_header_shop_name);
            shopNameView.setText(c.shopName);

            /* 3. 添加这次所绑定的购物车内的商品 */

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            for (int i = 0; i < c.goodsInThisShop.size(); i++) {
                final GoodsItemInSC g = c.goodsInThisShop.get(i);
                // TODO
                // 这一步得到的 eachGoodsView 为 null

                LinearLayout eachGoodsView = (LinearLayout) inflater.inflate(R.layout.cart_item_body, null, false);
                mGoodsContainerLL.addView(eachGoodsView);
                mGoodsViewAdded.add(eachGoodsView);

                /* 3.1 图片 */

                final ImageView imgView = (ImageView) eachGoodsView.findViewById(R.id.cart_body_goods_pic);
                //Log.d(TAG, "child count = " + eachGoodsView.getChildCount());
                //final ImageView imgView = (ImageView) eachGoodsView.getChildAt(0);
                final String gid = g.goodsId;
                imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        /*
                        Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                        intent.putExtra("goodsId", gid);
                        startActivity(intent);
                        */
                    }
                });

                Drawable placeHolder = getResources().getDrawable(R.drawable.img_place_holder);
                imgView.setImageDrawable(placeHolder);
                HttpRequest.getImage(g.goods.imageAddr,
                        new HttpCallbackListener() {
                            @Override
                            public void onFinish(Object responese)
                            {
                                final Drawable img = (Drawable) responese;
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run()
                                    {
                                        imgView.setImageDrawable(img);
                                    }
                                });
                            }

                            @Override
                            public void onError(Exception e)
                            {
                                Log.d(TAG, "fetch image failed");
                            }
                        });


                /* 3.2 商品名 */

                TextView goodsName = (TextView) eachGoodsView.findViewById(R.id.cart_body_goods_name);
                goodsName.setText(g.goods.goodsName);

                /* 3.3 商品属性与商品价格 */

                TextView goodsAttr = (TextView) eachGoodsView.findViewById(R.id.cart_body_goods_attr);
                TextView goodsPrice = (TextView) eachGoodsView.findViewById(R.id.cart_body_goods_price);
                String attrValue = "默认属性";
                String price = "￥ ";
                for (GoodsAttrString attr : g.goods.goodsAttrs) {
                    if (attr.attributeId.equals(g.attributeId)) {
                        attrValue = attr.attributeValue;
                        price = attr.price;
                        break;
                    }
                }
                goodsAttr.setText(attrValue);
                goodsPrice.setText("￥ " + price);

                /* 3.5 商品数量 */

                final TextView goodsNum = (TextView) eachGoodsView.findViewById(R.id.cart_body_goods_num);
                goodsNum.setText(g.goodsNum);
                mGoodsNums.add(g.goodsNum);

                /* 3.6 增减商品数量 */

                final int currGoodsIndex = i;
                final TextView deNum = (TextView) eachGoodsView.findViewById(R.id.cart_body_goods_decrease);
                TextView inNum = (TextView) eachGoodsView.findViewById(R.id.cart_body_goods_increase);
                deNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        int newNum = Integer.valueOf(mGoodsNums.get(currGoodsIndex)) - 1;
                        final String newNumStr = Integer.valueOf(newNum).toString();

                        HttpRequest.updateGoodsNumInShoppingCart("?id=" + g.id
                                        + "&goodsNum=" + newNumStr,
                                new HttpCallbackListener() {
                                    @Override
                                    public void onFinish(Object responese)
                                    {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run()
                                            {
                                                goodsNum.setText(newNumStr);
                                                mGoodsNums.set(currGoodsIndex, newNumStr);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(Exception e)
                                    {
                                    }
                                });
                    }
                });

                inNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        int newNum = Integer.valueOf(mGoodsNums.get(currGoodsIndex)) + 1;
                        final String newNumStr = Integer.valueOf(newNum).toString();

                        HttpRequest.updateGoodsNumInShoppingCart("?id=" + g.id
                                        + "&goodsNum=" + newNumStr,
                                new HttpCallbackListener() {
                                    @Override
                                    public void onFinish(Object responese)
                                    {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run()
                                            {
                                                goodsNum.setText(newNumStr);
                                                mGoodsNums.set(currGoodsIndex, newNumStr);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(Exception e)
                                    {
                                    }
                                });
                    }
                });

                /* 3.7 删除商品 */

                // TODO
            }

        }
    }

////////////////////////////////////////////////////////////
// inner class CartAdapter
//

    private class CartAdapter
            extends RecyclerView.Adapter<CartHolder> {
        private List<ShoppingCart> mCarts;

        // constructor

        public CartAdapter(List<ShoppingCart> carts)
        {
            mCarts = carts;
        }

        // override methods

        @Override
        public CartFragment.CartHolder onCreateViewHolder(ViewGroup parent, int viewType)

        {
            // get inflater directly from LayoutInflater
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View iv = inflater.inflate(R.layout.cart_item_header, parent, false);
            return new CartHolder(iv);
        }

        @Override
        public void onBindViewHolder(CartHolder holder, int position)
        {
            holder.bindCart(mCarts.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mCarts.size();
        }
    }
}

