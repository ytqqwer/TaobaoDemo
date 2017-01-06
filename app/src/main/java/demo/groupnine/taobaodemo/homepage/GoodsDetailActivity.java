package demo.groupnine.taobaodemo.homepage;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.widget.ViewSwitcher.ViewFactory;
import demo.groupnine.taobaodemo.R;
import demo.groupnine.taobaodemo.net.HttpCallbackListener;
import demo.groupnine.taobaodemo.net.HttpRequest;
import demo.groupnine.taobaodemo.shoppingcart.Result;

import java.util.Date;

/**
 * Created by rainstorm on 1/4/17.
 */

public class GoodsDetailActivity
        extends AppCompatActivity
        implements ViewFactory, OnTouchListener {

    public static final String TAG = GoodsDetailActivity.class.toString();

    private String goodsId;
    private GoodsDetail detail;
    private String checkedAttrId;
    private int goodsNum;
    private ShopInfo shop;
    private boolean hasFetchedResult;

    private ImageSwitcher mImageSwitcher;
    private Drawable[] drawables;
    private int currentPosition;
    private float downX;
    private LinearLayout linearLayout;
    private ImageView[] tips;

    private TextView goodsName;
    private TextView price;
    private TextView addToShoppingCart;
    private TextView discount;
    private LinearLayout oldPriceLinearLayout;
    private TextView oldPrice;
    private TextView shopName;
    private TextView numDec;
    private TextView num;
    private TextView numInc;
    private LinearLayout attrList;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_detail);

        //add delete line on oldPrice
        ((TextView) findViewById(R.id.oldPrice)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        Log.d(TAG, "onCreate: entered");

        //实例化ImageSwitcher
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        //设置Factory
        mImageSwitcher.setFactory(this);
        //设置OnTouchListener，我们通过Touch事件来切换图片
        mImageSwitcher.setOnTouchListener(this);

        linearLayout = (LinearLayout) findViewById(R.id.viewGroup);

        goodsName = (TextView) findViewById(R.id.goodsName);
        price = (TextView) findViewById(R.id.price);
        oldPrice = (TextView) findViewById(R.id.oldPrice);
        oldPriceLinearLayout = (LinearLayout) findViewById(R.id.oldPriceLinearLayout);
        discount = (TextView) findViewById(R.id.discount);
        shopName = (TextView) findViewById(R.id.shopName);
        num = (TextView) findViewById(R.id.goodsNum);
        numDec = (TextView) findViewById(R.id.numDec);
        numDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int n = Integer.parseInt(num.getText().toString());
//                Toast.makeText(GoodsDetailActivity.this, Integer.toString(n), Toast.LENGTH_SHORT).show();
                if (num.getText().toString().equals("1")) {
                    return;
                }
                goodsNum = n - 1;
                num.setText(Integer.toString(n - 1));
            }
        });

        numInc = (TextView) findViewById(R.id.numInc);
        numInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int n = Integer.parseInt(num.getText().toString());
//                Toast.makeText(GoodsDetailActivity.this, Integer.toString(n), Toast.LENGTH_SHORT).show();
                goodsNum = n + 1;
                num.setText(Integer.toString(n + 1));
            }
        });

        attrList = (LinearLayout) findViewById(R.id.attrList);

        addToShoppingCart = (TextView) findViewById(R.id.addToShoppingCart);
        addToShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                addToShoppingCartClickListener();
            }
        });
    }

    private void addToShoppingCartClickListener()
    {
        goodsNum = Integer.parseInt(num.getText().toString());
        StringBuilder para = new StringBuilder();
        para.append("?goodsId=").append(goodsId)
                .append("&attributeId=").append(checkedAttrId)
                .append("&goodsNum=").append(goodsNum);

        HttpRequest.addToShoppingCart(para.toString(), new HttpCallbackListener() {
            @Override
            public void onFinish(Object responese)
            {
                if (((Result) responese).result.equals("true")) {
                    Toast.makeText(GoodsDetailActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GoodsDetailActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e)
            {
                Log.d(TAG, "onError: add to shopping cart failed");
            }
        });
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems)
    {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.drawable.banner_round_select);
            } else {
                tips[i].setBackgroundResource(R.drawable.banner_round_normal);
            }
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (getIntent().getStringExtra("goodsId").equals(goodsId)) {
            return;
        }
        goodsId = getIntent().getStringExtra("goodsId");
        Log.d(TAG, "onStart: " + goodsId);
//        fetchGoodsDetail();
//        updateUI();
//        checkedAttrId = detail.attributes.get(0).attributeId;
//        goodsNum = 1;
    }

    private void fetchGoodsDetail()
    {
        hasFetchedResult = false;
        HttpRequest.getGoodsDetail("?goodsId=" + goodsId,
                new HttpCallbackListener() {
                    @Override
                    public void onFinish(Object responese)
                    {
                        detail = (GoodsDetail) responese;
                        hasFetchedResult = true;
                    }

                    @Override
                    public void onError(Exception e)
                    {
                        Log.d(TAG, "request failed");
                    }
                });

        while (!hasFetchedResult) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fetchGoodsImages();
        fetchShopInfo();
    }

    private void fetchShopInfo()
    {
        HttpRequest.getShopInfoByGoodsId("?goodsId=" + goodsId, new HttpCallbackListener() {
            @Override
            public void onFinish(Object responese)
            {
                shop = (ShopInfo) responese;
            }

            @Override
            public void onError(Exception e)
            {
                Log.d(TAG, "onError: fetch shop info by id failed");
            }
        });
    }

    private void fetchGoodsImages()
    {
        for (int i = 0; i < detail.images.size(); ++i) {

            HttpRequest.getImage(detail.images.get(i), new HttpCallbackListener() {
                @Override
                public void onFinish(Object responese)
                {
                    synchronized (drawables) {
                        drawables[drawables.length] = (Drawable) responese;
                    }
                }

                @Override
                public void onError(Exception e)
                {
                    Log.d(TAG, "onError: fetch image failed");
                }
            });
        }
    }

    private void updateUI()
    {
        updateImages();
        updateDescribe();
        updateAttrs();
    }

    private void updateDescribe()
    {
        // name && describe
        goodsName.setText(detail.goodsName + "\n" + detail.goodsDescribe);

        // price
        double dprice = Double.parseDouble(detail.attributes.get(0).price);
        double ddiscount = Double.parseDouble(detail.discountRate);
        if (new Date(detail.discountDeadline).before(new Date())) {
            price.setText("￥ " + detail.attributes.get(0).price);
            discount.setVisibility(View.INVISIBLE);
            oldPriceLinearLayout.setVisibility(View.INVISIBLE);
        } else {
            price.setText("￥ " + String.valueOf(dprice * ddiscount).format("%.2f"));
            discount.setVisibility(View.VISIBLE);
            oldPriceLinearLayout.setVisibility(View.VISIBLE);
            oldPrice.setText(detail.attributes.get(0).price);
        }

        // shopName
        shopName.setText(shop.shopName);
    }

    private void updateImages()
    {
        // 图像
        tips = new ImageView[detail.images.size()];
        for (int i = 0; i < detail.images.size(); i++) {
            ImageView mImageView = new ImageView(this);
            tips[i] = mImageView;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.rightMargin = 3;
            layoutParams.leftMargin = 3;

            mImageView.setBackgroundResource(R.drawable.banner_round_select);
            linearLayout.addView(mImageView, layoutParams);
        }

        mImageSwitcher.setImageDrawable(drawables[0]);

        setImageBackground(currentPosition);
    }

    private void updateAttrs(){
        attrList.removeAllViews();
        for(int i = 0; i < detail.attributes.size(); ++i){
            View attr = createGoodsAttr(detail.attributes.get(i));
            attrList.addView(attr);
        }
    }

    private View createGoodsAttr(GoodsAttrString goodsAttrString)
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View attr = inflater.inflate(R.layout.activity_goods_detail_attr, null);
        TextView id = (TextView) attr.findViewById(R.id.attributeId);
        TextView value = (TextView) attr.findViewById(R.id.attributeValue);
        final TextView price = (TextView) attr.findViewById(R.id.attrPrice);
        
        id.setText(goodsAttrString.attributeId);
        value.setText(goodsAttrString.attributeValue);
        price.setText(goodsAttrString.price);
        attr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                for(int i = 0; i < attrList.getChildCount(); ++i){
                    View attr = attrList.getChildAt(i);
                    TextView value = (TextView) attr.findViewById(R.id.attributeValue);
                    value.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    value.setTextColor(getResources().getColor(R.color.black));
                }
                checkedAttrId = ((TextView)v.findViewById(R.id.attributeId)).getText().toString();
                TextView vvalue = (TextView) v.findViewById(R.id.attributeValue);
                vvalue.setBackgroundColor(getResources().getColor(R.color.tb_red));
                vvalue.setTextColor(getResources().getColor(R.color.white));
                String p = ((TextView)v.findViewById(R.id.attrPrice)).getText().toString();
                price.setText("￥ " + p);
            }
        });
        return attr;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //手指按下的X坐标
                downX = event.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                float lastX = event.getX();
                //抬起的时候的X坐标大于按下的时候就显示上一张图片
                if (lastX > downX) {
                    if (currentPosition > 0) {
                        mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
                        mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                        currentPosition--;
                        mImageSwitcher.setImageDrawable(drawables[currentPosition % drawables.length]);
                        setImageBackground(currentPosition);
                    } else {
                        Toast.makeText(getApplication(), "已经是第一张", Toast.LENGTH_SHORT).show();
                    }
                }

                if (lastX < downX) {
                    if (currentPosition < drawables.length - 1) {
                        mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_in));
                        mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.lift_out));
                        currentPosition++;
                        mImageSwitcher.setImageDrawable(drawables[currentPosition]);
                        setImageBackground(currentPosition);
                    } else {
                        Toast.makeText(getApplication(), "到了最后一张", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            break;
        }

        return true;
    }

    @Override
    public View makeView()
    {
        final ImageView i = new ImageView(this);
        i.setBackgroundColor(0xff000000);
        i.setScaleType(ImageView.ScaleType.CENTER_CROP);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        return i;
    }
}
