package demo.groupnine.taobaodemo.shoppingcart;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import demo.groupnine.taobaodemo.R;
import demo.groupnine.taobaodemo.homepage.Category;
import demo.groupnine.taobaodemo.homepage.GoodsBrief;
import demo.groupnine.taobaodemo.homepage.SearchFragment;
import demo.groupnine.taobaodemo.net.HttpCallbackListener;
import demo.groupnine.taobaodemo.net.HttpRequest;

public class ConfirmOrderActivity extends Activity {

    //data model
    private List<ToSettleOrder> mOrder;
    private List<>
    // receiver widget
    private TextView mReceiverNameTV;
    private TextView mReceiverPhoneTV;
    private TextView mReceiverAddressTV;
    // order list widget
    private TextView mOrderSumTV;
    private RecyclerView mOrderRecyclerView;
    private ConfirmOrderActivity.OrderAdapter mAdapter;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("确认订单");
        setContentView(R.layout.confirm_order_frame);

        getIntentExtra();

        // confirm order list widget
        mOrderRecyclerView = (RecyclerView) findViewById(R.id.confirm_order_recycler_view);
        mOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateOrderUI();

    }

    private void getIntentExtra() {


        Gson g = new Gson();
        Type t = new TypeToken<ArrayList<Category>>() {
        }.getType();
        ArrayList<Category> cs = g.fromJson(JsonStr, t);
    }

////////////////////////////////////////////////////////////
// inner class OrderHolder
//

    private class OrderHolder extends RecyclerView.ViewHolder {

        private ToSettleOrder mOrder;
        private TextView mShopNameTV;
        private TextView mGoodsNumTV;
        private TextView mPriceSumTV;

        // constructor

        public OrderHolder(View itemView) {
            super(itemView);
            mShopNameTV = (TextView) itemView.findViewById(R.id.order_item_shop);
            mGoodsNumTV = (TextView) itemView.findViewById(R.id.order_item_num);
            mPriceSumTV = (TextView) itemView.findViewById(R.id.order_item_sum);
        }

        // public methods

        public void bindOrder(final ToSettleOrder o) {
            mShopNameTV.setText(o.shopName);
            mGoodsNumTV.setText("共" + o.goodsNum + "件商品");
            mPriceSumTV.setText("¥ " + o.price);

        }
    }

////////////////////////////////////////////////////////////
// inner class OrderAdapter
//

    private class OrderAdapter
            extends RecyclerView.Adapter<OrderHolder> {

        private List<ToSettleOrder> mOrder;

        // constructor

        public OrderAdapter(List<ToSettleOrder> os) {
            mOrder = os;
        }

        // override methods

        @Override
        public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // get inflater directly from LayoutInflater
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View iv = inflater.inflate(R.layout.confirm_order_item, parent, false);
            return new OrderHolder(iv);
        }

        @Override
        public void onBindViewHolder(final ConfirmOrderActivity.OrderHolder holder, int position) {
            ToSettleOrder item = mOrder.get(position);
            holder.bindGoods(item);
        }

    }


}


