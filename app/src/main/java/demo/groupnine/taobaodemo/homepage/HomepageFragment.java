package demo.groupnine.taobaodemo.homepage;

import android.content.Intent;
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

public class HomepageFragment
        extends Fragment {
    private static String TAG = "HomepageFragment";

    private List<Drawable> mGalleryImgList;
    private List<String> mGalleryKeywordList;
    private RecyclerView mGalleryRV;
    private List<CategoryGoods> mCategoryGoodsList;
    private RecyclerView mCategoryRV;

    // lifetime methods

    @Override
    public void onCreate(
            @Nullable
                    Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable
                                     ViewGroup container,
                             @Nullable
                                     Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_homepage, container, false);

        // gallery widget
        mGalleryRV = (RecyclerView) v.findViewById(R.id.fragment_homepage_gallery_rv);
        mGalleryRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        if (isAdded()) {
            populateGalleryList();
            GalleryAdapter adapter = new GalleryAdapter(mGalleryImgList);
            mGalleryRV.setAdapter(adapter);
        }

        // category widget
        mCategoryRV = (RecyclerView) v.findViewById(R.id.fragment_homepage_category_rv);
        mCategoryRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCategoryRV.setNestedScrollingEnabled(false);
        if (isAdded()) {
            fetchCategoryGoods();
            CategoryAdapter adapter = new CategoryAdapter(mCategoryGoodsList);
            mCategoryRV.setAdapter(adapter);
        }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
    {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_homepage, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                // start SearchActivity
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("keyword", s);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                // do nothing
                return false;
            }
        });
    }

    // private methods

    private void populateGalleryList()
    {
        mGalleryImgList = new ArrayList<>();
        mGalleryImgList.add(getResources().getDrawable(R.drawable.gallery_01));
        mGalleryImgList.add(getResources().getDrawable(R.drawable.gallery_02));
        mGalleryImgList.add(getResources().getDrawable(R.drawable.gallery_03));
        mGalleryImgList.add(getResources().getDrawable(R.drawable.gallery_04));
        mGalleryImgList.add(getResources().getDrawable(R.drawable.gallery_05));
        mGalleryImgList.add(getResources().getDrawable(R.drawable.gallery_06));

        mGalleryKeywordList = new ArrayList<>();
        mGalleryKeywordList.add("羽绒服");
        mGalleryKeywordList.add("粮油调味");
        mGalleryKeywordList.add("笔记本");
        mGalleryKeywordList.add("帆布鞋");
        mGalleryKeywordList.add("进口食品");
        mGalleryKeywordList.add("手机");
    }

    private void fetchCategoryGoods()
    {
        mCategoryGoodsList = new ArrayList<CategoryGoods>();
        String[] l1Names = new String[]{"手机", "笔记本", "钢琴", "羽绒服", "帆布鞋", "竖笛", "进口食品", "粮油调味"};
        for (int i = 0; i < l1Names.length; i++) {
            final CategoryGoods c = new CategoryGoods();
            c.name = l1Names[i];
            /*
            HttpRequest.getGoodsImagesByLevelOne("?levelOne=" + l1Names[i] + "&imageNum=3",
                    new HttpCallbackListener() {
                        public void onFinish(Object o)
                        {
                            c.goods = (ArrayList<GoodsBrief>) o;
                        }

                        public void onError(Exception e)
                        {
                            // do nothing
                        }
                    });
                    */
            mCategoryGoodsList.add(c);
        }
    }

////////////////////////////////////////////////////////////
// inner class GalleryImgHolder
//

    private class GalleryImgHolder
            extends RecyclerView.ViewHolder {
        private ImageView mGalleryImageView;

        // constructor

        public GalleryImgHolder(View itemView)
        {
            super(itemView);
            mGalleryImageView = (ImageView) itemView.findViewById(R.id.homepage_gallery_item_image);
        }

        // public methods

        public void bindDrawable(Drawable d)
        {
            mGalleryImageView.setImageDrawable(d);
        }

        public void setSearchKeyword(final String k)
        {
            mGalleryImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra("keyword", k);
                    startActivity(intent);
                }
            });
        }

    }

////////////////////////////////////////////////////////////
// inner class GalleryAdapter
//

    private class GalleryAdapter
            extends RecyclerView.Adapter<HomepageFragment.GalleryImgHolder> {
        private List<Drawable> mGalleryImgList;

        // constructor

        public GalleryAdapter(List<Drawable> imgs)
        {
            mGalleryImgList = imgs;
        }

        // override methods

        @Override
        public HomepageFragment.GalleryImgHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // get inflater directly from LayoutInflater
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View iv = inflater.inflate(R.layout.homepage_gallery_item, parent, false);
            return new GalleryImgHolder(iv);
        }

        @Override
        public void onBindViewHolder(final HomepageFragment.GalleryImgHolder holder, int position)
        {
            holder.bindDrawable(mGalleryImgList.get(position));
            holder.setSearchKeyword(mGalleryKeywordList.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mGalleryImgList.size();
        }
    }

////////////////////////////////////////////////////////////
// inner class CategoryHolder
//

    private class CategoryHolder
            extends RecyclerView.ViewHolder {

        private TextView mCategoryNameTV;
        private ImageView mGoodsImageView_1;
        private ImageView mGoodsImageView_2;
        private ImageView mGoodsImageView_3;
        private int mImagesCount;
        private ArrayList<String> mGoodsIds;

        // constructor

        public CategoryHolder(View itemView)
        {
            super(itemView);
            mGoodsIds = new ArrayList<>();
            mCategoryNameTV = (TextView) itemView.findViewById(R.id.homepage_category_name);
            mGoodsImageView_1 = (ImageView) itemView.findViewById(R.id.homepage_category_img_1);
            mGoodsImageView_2 = (ImageView) itemView.findViewById(R.id.homepage_category_img_2);
            mGoodsImageView_3 = (ImageView) itemView.findViewById(R.id.homepage_category_img_3);
        }

        // public methods

        public void setCategoryName(String name)
        {
            mCategoryNameTV.setText(name);
        }

        public void clearGoods()
        {
            mImagesCount = 0;
            mGoodsIds = new ArrayList<>();
        }

        public void addGoodsId(String gid)
        {
            mGoodsIds.add(gid);
        }

        public void addGoodsImg(Drawable img)
        {
            mImagesCount += 1;
            switch (mImagesCount) {
                case 1:
                    mGoodsImageView_1.setImageDrawable(img);
                    mGoodsImageView_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            Log.d(TAG, "detail of goods 1");
                        }
                    });
                    break;
                case 2:
                    mGoodsImageView_2.setImageDrawable(img);
                    mGoodsImageView_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            Log.d(TAG, "detail of goods 2");
                        }
                    });
                    break;
                case 3:
                    mGoodsImageView_3.setImageDrawable(img);
                    mGoodsImageView_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            Log.d(TAG, "detail of goods 3");
                        }
                    });
                    break;
            }
        }
    }

////////////////////////////////////////////////////////////
// inner class CategoryAdapter
//

    private class CategoryAdapter
            extends RecyclerView.Adapter<HomepageFragment.CategoryHolder> {
        private List<CategoryGoods> mCategoryGoodsList;

        // constructor

        public CategoryAdapter(List<CategoryGoods> list)
        {
            mCategoryGoodsList = list;
        }

        // override methods

        @Override
        public HomepageFragment.CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // get inflater directly from LayoutInflater
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View iv = inflater.inflate(R.layout.homepage_category_item, parent, false);
            return new CategoryHolder(iv);
        }

        @Override
        public void onBindViewHolder(final HomepageFragment.CategoryHolder holder, int position)
        {
            CategoryGoods c = mCategoryGoodsList.get(position);
            holder.setCategoryName(c.name);
            Drawable placeHolder = getResources().getDrawable(R.drawable.img_place_holder);
            holder.clearGoods();

            // TODO
            holder.addGoodsImg(placeHolder);
            holder.addGoodsImg(placeHolder);
            holder.addGoodsImg(placeHolder);
            /*
            holder.addGoodsId(c.goods.get(1).goodsId);
            holder.addGoodsId(c.goods.get(2).goodsId);
            holder.addGoodsId(c.goods.get(3).goodsId);
                for (int i = 0; i < 3; i++) {
                    String imgAddr = (c.goods).get(i).imageAddr;
                    HttpRequest.getImage(imgAddr,
                            new HttpCallbackListener() {
                                public void onFinish(Object o)
                                {
                                    holder.addGoodsImg((Drawable) o);
                                }

                                public void onError(Exception e)
                                {
                                    Log.d(TAG, "fetch image failed.");
                                }
                            });
                }
            */
        }

        @Override
        public int getItemCount()
        {
            return mCategoryGoodsList.size();
        }
    }
}


