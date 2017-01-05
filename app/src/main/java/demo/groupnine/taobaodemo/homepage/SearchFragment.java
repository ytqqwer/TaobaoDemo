package demo.groupnine.taobaodemo.homepage;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import demo.groupnine.taobaodemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SearchFragment
        extends Fragment {
    private static String TAG = "SearchFragment";

    // data model
    private List<GoodsBrief> mGoods;
    // search option widget
    private TextView mSaleDescTV;
    private TextView mPriceAscTV;
    private TextView mPriceDescTV;
    private String mKeyword;
    private String mSortByPrice = "false";
    private String mPriceUp = "true";
    // search result widget
    private RecyclerView mGoodsRecyclerView;
    private GoodsAdapter mAdapter;
    private View mBlankListPrompt;

    // lifetime methods

    public static SearchFragment newInstance(String keyword)
    {
        SearchFragment fragment = new SearchFragment();
        fragment.mKeyword = keyword;
        return fragment;
    }

    @Override
    public void onCreate(
            @Nullable
                    Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        fetchResult();
        getActivity().setTitle("Search");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable
                                     ViewGroup container,
                             @Nullable
                                     Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        // search result list widget
        mGoodsRecyclerView = (RecyclerView) v.findViewById(R.id.search_result_recycler_view);
        mGoodsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBlankListPrompt = v.findViewById(R.id.goods_blank_list);
        if (isAdded()) {
            updateResultUI();
        }

        // search option widget
        mSaleDescTV = (TextView) v.findViewById(R.id.search_sales_desc);
        mPriceAscTV = (TextView) v.findViewById(R.id.search_price_asc);
        mPriceDescTV = (TextView) v.findViewById(R.id.search_price_desc);
        mSaleDescTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setSearchOptionUI(1);
                mSortByPrice = "false";
                fetchResult();
                updateResultUI();
            }
        });
        mPriceAscTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setSearchOptionUI(2);
                mSortByPrice = "true";
                mPriceUp = "true";
                fetchResult();
                updateResultUI();
            }
        });
        mPriceDescTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setSearchOptionUI(3);
                mSortByPrice = "true";
                mPriceUp = "false";
                fetchResult();
                updateResultUI();
            }
        });

        return v;
    }

    // menu related

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
    {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_search, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                mKeyword = s;
                fetchResult();
                updateResultUI();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                mKeyword = s;
                fetchResult();
                updateResultUI();
                return false;
            }
        });
    }

    // private methods

    /* 作用: 更新搜索选项的 UI，给用户直观反馈
     * 调用时机: 用户改变搜索选项时
     */
    private void setSearchOptionUI(int index)
    {
        mSaleDescTV.setTextColor(getResources().getColor(R.color.black));
        mPriceDescTV.setTextColor(getResources().getColor(R.color.black));
        mPriceAscTV.setTextColor(getResources().getColor(R.color.black));
        switch (index) {
            case 1:
                mSaleDescTV.setTextColor(getResources().getColor(R.color.tb_red));
                break;
            case 2:
                mPriceAscTV.setTextColor(getResources().getColor(R.color.tb_red));
                break;
            case 3:
                mPriceDescTV.setTextColor(getResources().getColor(R.color.tb_red));
                break;
            default:
                mSaleDescTV.setTextColor(getResources().getColor(R.color.tb_red));
        }
    }

    /* 作用: 调用 HttpRequest 中方法，更新 model
     * 调用时机: 搜索关键词或搜索选项变化时
     */
    private void fetchResult()
    {
        // -- debug begin
        mGoods = new ArrayList<GoodsBrief>();
        int total = Math.abs(new Random().nextInt()) % 10;
        Log.d(TAG, "doge total: " + total);
        for (int i = 0; i < total; i++) {
            mGoods.add(new GoodsBrief());
        }
        // -- debug end

        /*
        HttpRequest.getGoodsBriefByKeyword("?keyword=" + keyword
                        + "&maxNumInOnePage=200"
                        + "&pageNum=1"
                        + "&sortByPrice=" + mSortByPrice
                        + "&priceUp=" + mPriceUp,
                new HttpCallbackListener() {
                    @Override
                    public void onFinish(Object responese)
                    {
                        mGoods = (List<GoodsBrief>) responese;
                    }

                    @Override
                    public void onError(Exception e)
                    {
                        Log.d(TAG, "request failed");
                    }
                });
        */
    }

    /* 作用: 根据 model 更新 UI
     * 调用时机: 一般在 fetchResult() 调用之后，此时 model 已经更新，要把更新反应在 UI 上
     */
    private void updateResultUI()
    {
        if (mGoods.size() == 0) {
            mGoodsRecyclerView.setVisibility(View.GONE);
            mBlankListPrompt.setVisibility(View.VISIBLE);
        } else {
            mBlankListPrompt.setVisibility(View.GONE);
            mGoodsRecyclerView.setVisibility(View.VISIBLE);

            mAdapter = new GoodsAdapter(mGoods);
            mGoodsRecyclerView.setAdapter(mAdapter);
        }
    }

////////////////////////////////////////////////////////////
// inner class GoodsHolder
//

    private class GoodsHolder
            extends RecyclerView.ViewHolder {

        private GoodsBrief mGoods;
        private ImageView mItemImageView;
        private TextView mGoodsNameTV;
        private TextView mGoodsPriceTV;
        private TextView mGoodsSalesTV;

        // constructor

        public GoodsHolder(View itemView)
        {
            super(itemView);
            mItemImageView = (ImageView) itemView.findViewById(R.id.search_result_item_pic);
            mGoodsNameTV = (TextView) itemView.findViewById(R.id.search_result_item_name);
            mGoodsPriceTV = (TextView) itemView.findViewById(R.id.search_result_item_price);
            mGoodsSalesTV = (TextView) itemView.findViewById(R.id.search_result_item_sales);
        }

        // public methods

        public void bindDrawable(Drawable drawable)
        {
            mItemImageView.setImageDrawable(drawable);
        }

        public void setGoodsName(String name)
        {
            mGoodsNameTV.setText(name);
        }

        public void setGoodsPrice(String price)
        {
            mGoodsPriceTV.setText(price);
        }

        public void setmGoodsSales(String sales)
        {
            mGoodsSalesTV.setText(sales);
        }

    }

////////////////////////////////////////////////////////////
// inner class GoodsAdapter
//

    private class GoodsAdapter
            extends RecyclerView.Adapter<GoodsHolder> {
        private List<GoodsBrief> mGoods;

        // constructor

        public GoodsAdapter(List<GoodsBrief> gs)
        {
            mGoods = gs;
        }

        // override methods

        @Override
        public GoodsHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // get inflater directly from LayoutInflater
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View iv = inflater.inflate(R.layout.search_result_item, parent, false);
            return new GoodsHolder(iv);
        }

        @Override
        public void onBindViewHolder(GoodsHolder holder, int position)
        {
            GoodsBrief item = mGoods.get(position);
            Drawable placeHolder = getResources().getDrawable(R.drawable.doge);
            holder.bindDrawable(placeHolder);

            // TODO
            /*
            holder.setGoodsName(mGoods.get(position).goodsName);
            holder.setGoodsPrice(mGoods.get(position).price);
            holder.setmGoodsSales(mGoods.get(position).sales);
            BitmapDrawable img = HttpRequest.getImage(mGoods.get(position).imageAddr);
            holder.bindDrawable(img);
            */
        }

        @Override
        public int getItemCount()
        {
            return mGoods.size();
        }
    }
}
