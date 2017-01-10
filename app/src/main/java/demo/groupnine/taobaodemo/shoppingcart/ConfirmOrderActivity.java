package demo.groupnine.taobaodemo.shoppingcart;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import demo.groupnine.taobaodemo.R;
import demo.groupnine.taobaodemo.net.HttpCallbackListener;
import demo.groupnine.taobaodemo.net.HttpRequest;

public class ConfirmOrderActivity extends Activity {

    //data model
    private List<ToSettleOrder> mOrder;
    private Receiver mReceiver;
    private ToConfirmOrders mO;
    // receiver widget
    private TextView mReceiverNameTV;
    private TextView mReceiverPhoneTV;
    private TextView mReceiverAddressTV;
    // order list widget
    private TextView mOrderTotalMoneyTV;
    private TextView mConfirmOrder;
    private RecyclerView mOrderRecyclerView;
    private ConfirmOrderActivity.OrderAdapter mAdapter;
    //thread
    private boolean hasFetchedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("确认订单");
        setContentView(R.layout.confirm_order_frame);
        fetchReceiver();
        getIntentExtra();

        // receiver widget
        mReceiverNameTV = (TextView) findViewById(R.id.receiver_name);
        mReceiverPhoneTV = (TextView) findViewById(R.id.receiver_phone);
        mReceiverAddressTV = (TextView) findViewById(R.id.receiver_address);
        mReceiverNameTV.setText(mReceiver.receiverName);
        mReceiverPhoneTV.setText(mReceiver.receiverPhone);
        mReceiverAddressTV.setText(mReceiver.receiverAddress);
        //item list widget
        mOrderRecyclerView = (RecyclerView) findViewById(R.id.confirm_order_recycler_view);
        mOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new OrderAdapter(mOrder);
        mOrderRecyclerView.setAdapter(mAdapter);
        //sum widget
        mOrderTotalMoneyTV = (TextView) findViewById(R.id.confirm_order_sum);
        double price = 0;
        for (ToSettleOrder o : mOrder) {
            price += Double.valueOf(o.price);
        }
        mOrderTotalMoneyTV.setText("¥ " + price);
        mConfirmOrder = (TextView) findViewById(R.id.confirm_order);
        mConfirmOrder
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Gson g = new Gson();
                        List<OrderInfo> orders = mO.orders;
                        HttpRequest.confirmOrder("?orders=" + g.toJson(orders) + "&receiverId=" + mReceiver.receiverId + "&payMethod=在线支付",
                                new HttpCallbackListener() {
                                    @Override
                                    public void onFinish(Object responese) {
                                        finish();
                                    }

                                    @Override
                                    public void onError(Exception e) {

                                    }
                                });
                    }
                });

    }

    private void fetchReceiver() {

        hasFetchedReceiver = false;
        HttpRequest.getReceivers("",
                new HttpCallbackListener() {
                    @Override
                    public void onFinish(Object responese) {
                        mReceiver = ((ArrayList<Receiver>) responese).get(0);
                        hasFetchedReceiver = true;
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        while (!hasFetchedReceiver) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // do nothing
            }
        }

    }

    private void getIntentExtra() {

        String toSettle = getIntent().getStringExtra("toSettle");

        Gson g1 = new Gson();
        Type t = new TypeToken<ArrayList<ToSettleOrder>>() {
        }.getType();
        mOrder = g1.fromJson(toSettle, t);

        String toConfirm = getIntent().getStringExtra("toConfirm");
        Gson g2 = new Gson();
        mO = g2.fromJson(toConfirm, ToConfirmOrders.class);
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
            LayoutInflater inflater = LayoutInflater.from(ConfirmOrderActivity.this);
            View iv = inflater.inflate(R.layout.confirm_order_item, parent, false);
            return new OrderHolder(iv);
        }

        @Override
        public void onBindViewHolder(final ConfirmOrderActivity.OrderHolder holder, int position) {
            ToSettleOrder item = mOrder.get(position);
            holder.bindOrder(item);
        }


        @Override
        public int getItemCount() {
            return mOrder.size();
        }

    }


}


