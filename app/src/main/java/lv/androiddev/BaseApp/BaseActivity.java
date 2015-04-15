package lv.androiddev.BaseApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;

/**
 * Created by martinsstrengis on 14/04/15. Yey
 */
public class BaseActivity extends ActionBarActivity {
    public int layout;
    public Toolbar toolbar;

    public void onCreate(Bundle state) {
        super.onCreate(state);

        setContentView(layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

    public static int dp(int px, DisplayMetrics dm) {
        try {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, dm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return px;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(handleBackButton()){
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        boolean re = super.onOptionsItemSelected(item);
        if(!re) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    handleBackButton();
                    break;
            }
        }

        return true;
    }

    public boolean handleBackButton(){
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            if (f != null && !((BaseFragment) f).isPaused && ((BaseFragment) f).onBackPressed()) {
                return true;
            }
        }

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            supportFinishAfterTransition();
            return false;
        }else{
            getSupportFragmentManager().popBackStack();
            return true;
        }
    }
}