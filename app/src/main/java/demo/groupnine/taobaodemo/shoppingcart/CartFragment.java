package demo.groupnine.taobaodemo.shoppingcart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import demo.groupnine.taobaodemo.R;

public class CartFragment
        extends Fragment {

    // lifetime methods

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
