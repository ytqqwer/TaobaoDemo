package demo.groupnine.taobaodemo.shoppingcart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.*;

import java.util.List;

import demo.groupnine.taobaodemo.R;

public class CartFragment
        extends Fragment {
    private static String TAG = "CartFragment";

    // data model
    private List<ShoppingCart> mCart;
    //result widget
    private RecyclerView mCartRecyclerView;
    private CartAdapter mAdapter;
    private View mBlankListPrompt;

    // lifetime methods

    public static CartFragment newInstance(String keyword)
    {
        CartFragment fragment = new CartFragment();
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
        getActivity().setTitle("Cart");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable
                                     ViewGroup container,
                             @Nullable
                                     Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    /*
     * 在这个方法里面写从其他 fragment 切换至此 Fragment 时，所要进行的操作
     */
    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }

}
