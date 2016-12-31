package demo.groupnine.taobaodemo.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import demo.groupnine.taobaodemo.R;

public class HomepageFragment
        extends Fragment {
    private static String TAG = "HomepageFragment";

    // lifetime methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable
                                     ViewGroup container,
                             @Nullable
                                     Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView...");
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        /*
        switch (item.getItemId()) {
            case R.id.fragment_homepage_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        */
        return super.onOptionsItemSelected(item);
    }
}
