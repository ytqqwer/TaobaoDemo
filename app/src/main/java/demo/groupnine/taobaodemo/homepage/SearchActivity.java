package demo.groupnine.taobaodemo.homepage;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import demo.groupnine.taobaodemo.R;

public class SearchActivity
        extends AppCompatActivity {

    // lifetime methods

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.search_fragment_container);

        if (fragment == null) {
            String keyword = getIntent().getStringExtra("keyword");
            fragment = SearchFragment.newInstance(keyword);
            fm.beginTransaction()
                    .add(R.id.search_fragment_container, fragment)
                    .commit();
        }
    }

}
