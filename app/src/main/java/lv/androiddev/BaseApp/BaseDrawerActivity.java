package lv.androiddev.BaseApp;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.KeyEvent;
import android.view.MenuItem;

import lv.androiddev.BaseApp.views.BaseDrawerLayout;

/**
 * Created by martinsstrengis on 15/04/15. Yey
 */
public class BaseDrawerActivity extends BaseActivity{
    private BaseDrawerLayout mDrawerLayout;
    public void onCreate(Bundle state){
        super.onCreate(state);

        mDrawerLayout = (BaseDrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }else{
                mDrawerLayout.closeDrawer(GravityCompat.END);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
