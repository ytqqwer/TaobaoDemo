package demo.groupnine.taobaodemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import demo.groupnine.taobaodemo.account.AccountFragment;
import demo.groupnine.taobaodemo.homepage.GoodsDetailActivity;
import demo.groupnine.taobaodemo.homepage.HomepageFragment;
import demo.groupnine.taobaodemo.shoppingcart.CartFragment;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener {

    private LinearLayout ll_homepage;
    private LinearLayout ll_cart;
    private LinearLayout ll_account;
    private ImageView iv_homepage;
    private ImageView iv_cart;
    private ImageView iv_account;
    private TextView tv_homepage;
    private TextView tv_cart;
    private TextView tv_account;
    private Fragment homepageFragment;
    private Fragment cartFragment;
    private Fragment accountFragment;

    // lifetime methods

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
        initFragment(1);

        setTitle("Taobao Demo");

        /* First start: initView -> initListener -> hideFragment -> initFragment
         * Click:       onClick -> restartBotton -> hideFragment -> initFragment
         */
    }

    // private methods

    private void initFragment(int index)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case 1:
                if (homepageFragment == null) {
                    homepageFragment = new HomepageFragment();
                    transaction.add(R.id.activity_main_fragment_container, homepageFragment);
                } else {
                    transaction.show(homepageFragment);
                }
                break;

            case 4:
                if (cartFragment == null) {
                    cartFragment = new CartFragment();
                    transaction.add(R.id.activity_main_fragment_container, cartFragment);
                } else {
                    transaction.show(cartFragment);
                }
                break;

            case 5:
                if (accountFragment == null) {
                    accountFragment = new AccountFragment();
                    transaction.add(R.id.activity_main_fragment_container, accountFragment);
                } else {
                    transaction.show(accountFragment);
                }
                break;

            default:
                break;
        }

        transaction.commit();

    }

    private void hideFragment(FragmentTransaction transaction)
    {
        if (homepageFragment != null) {
            transaction.hide(homepageFragment);
        }
        if (cartFragment != null) {
            transaction.hide(cartFragment);
        }
        if (accountFragment != null) {
            transaction.hide(accountFragment);
        }
    }

    private void initListener()
    {
        ll_homepage.setOnClickListener(this);
        ll_cart.setOnClickListener(this);
        ll_account.setOnClickListener(this);
    }

    private void initView()
    {
        this.ll_homepage = (LinearLayout) findViewById(R.id.ll_homepage);
        this.ll_cart = (LinearLayout) findViewById(R.id.ll_cart);
        this.ll_account = (LinearLayout) findViewById(R.id.ll_account);

        this.iv_homepage = (ImageView) findViewById(R.id.iv_homepage);
        this.iv_cart = (ImageView) findViewById(R.id.iv_cart);
        this.iv_account = (ImageView) findViewById(R.id.iv_account);

        this.tv_homepage = (TextView) findViewById(R.id.tv_homepage);
        this.tv_cart = (TextView) findViewById(R.id.tv_cart);
        this.tv_account = (TextView) findViewById(R.id.tv_account);
    }

    @Override
    public void onClick(View v)
    {

        restartBotton();
        switch (v.getId()) {
            case R.id.ll_homepage:
                iv_homepage.setImageResource(R.drawable.guide_home_on);
                tv_homepage.setTextColor(getResources().getColor(R.color.tb_red));
                initFragment(1);
                setTitle("Taobao Demo");
                break;
            case R.id.ll_cart:
                iv_cart.setImageResource(R.drawable.guide_cart_on);
                tv_cart.setTextColor(getResources().getColor(R.color.tb_red));
                initFragment(4);
                setTitle("购物车");
                break;
            case R.id.ll_account:
                iv_account.setImageResource(R.drawable.guide_account_on);
                tv_account.setTextColor(getResources().getColor(R.color.tb_red));
                initFragment(5);
                setTitle("设置");

                //TODO delete it after debug GoodsDetailActivity
                // Intent intent = new Intent(this, GoodsDetailActivity.class);
                // intent.putExtra("goodsId", "12345789");
                // startActivity(intent);
                // end
                break;

            default:
                break;
        }

    }

    private void restartBotton()
    {
        iv_homepage.setImageResource(R.drawable.guide_home_nm);
        iv_cart.setImageResource(R.drawable.guide_cart_nm);
        iv_account.setImageResource(R.drawable.guide_account_nm);
        tv_homepage.setTextColor(getResources().getColor(R.color.black));
        tv_cart.setTextColor(getResources().getColor(R.color.black));
        tv_account.setTextColor(getResources().getColor(R.color.black));
    }
}
