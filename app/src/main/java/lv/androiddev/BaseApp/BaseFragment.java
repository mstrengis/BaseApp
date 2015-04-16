package lv.androiddev.BaseApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;

import lv.androiddev.BaseApp.api.ApiInterface;
import lv.androiddev.BaseApp.api.BaseApiBuilder;
import lv.androiddev.BaseApp.utils.BaseItem;

/**
 * Created by martinsstrengis on 14/04/15. Yey
 */
public abstract class BaseFragment extends Fragment{
    public boolean _configDisableLoad = false;

    public boolean isPaused = true;

    public int page = 1;
    private boolean mIsRefreshing;

    public BaseApiBuilder apiBuilder;


    public int layout;
    public View rootView;
    private ApiInterface mApiInterface;

    public View progressIndicator; //TODO assign in init();

    public abstract void init();
    public abstract void setRequestParams();
    public abstract ArrayList<BaseItem> parseData(JSONObject object);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state){
        if (rootView != null && rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
            return rootView;
        } else {
            rootView = inflater.inflate(layout, parent, false);
        }

        init();

        if(!_configDisableLoad){
            mApiInterface = new ApiInterface<ArrayList<BaseItem>>() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideRequestIndicator();
                }

                @Override
                public ArrayList<BaseItem> parseData(JSONObject object) {
                    return BaseFragment.this.parseData(object);
                }

                @Override
                public void onSuccess(JSONObject rawData, ArrayList<BaseItem> items) {
                    hideRequestIndicator();
                    BaseFragment.this.dataLoaded(rawData, items);
                }

            };

            load(false);
        }

        progressIndicator = rootView.findViewById(R.id.progress_indicator);

        return rootView;
    }

    public void dataLoaded(JSONObject data, ArrayList<BaseItem> items){

    }

    /*
    * return true if you want back button to capture it
    * f.e if you want to show some kind of dialog with view in fragment
    * if(dialog.getVisibility() == View.VISIBLE){
    *   dialog.setVisibility(View.GONE);
    *   return true; //back stack wont be triggered
    * }
    *
    * return false; //Back stack or finish will be triggered on activity
    * */
    public boolean onBackPressed(){
        return false;
    }

    @Override
    public void onResume(){
        isPaused = false;
        super.onResume();
    }

    @Override
    public void onPause(){
        isPaused = true;
        super.onPause();
    }

    public void showRequestIndicator(){
        if(progressIndicator != null && !mIsRefreshing){
            progressIndicator.setVisibility(View.VISIBLE);
            //TODO animate ?
        }

        mIsRefreshing = false;
    }

    public void hideRequestIndicator(){
        mIsRefreshing = false;
        if(progressIndicator != null){
            progressIndicator.setVisibility(View.GONE);
            //TODO ANIMATE
        }
    }

    public void load(boolean isRefreshing){
        getRequest();
        mIsRefreshing = isRefreshing;
        if(apiBuilder.request != null) {
            apiBuilder.request.cancel();
        }

        setRequestParams();
        showRequestIndicator();
        apiBuilder.execute();
    }

    public BaseApiBuilder getRequest(){
        if(apiBuilder == null){
            apiBuilder = new BaseApiBuilder().setListener(mApiInterface);
        }

        return apiBuilder;
    }

    public int dp(int dp){
        if(getResources() == null) {
            return dp;
        }

        return BaseActivity.dp(dp, getResources().getDisplayMetrics());
    }
}
