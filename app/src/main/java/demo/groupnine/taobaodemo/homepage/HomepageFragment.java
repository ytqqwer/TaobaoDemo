package demo.groupnine.taobaodemo.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.*;
import demo.groupnine.taobaodemo.R;

public class HomepageFragment
        extends Fragment {
    private static String TAG = "HomepageFragment";

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
        Log.d(TAG, "onCreateView...");
        return inflater.inflate(R.layout.fragment_homepage, container, false);
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

}
