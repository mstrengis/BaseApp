package lv.androiddev.BaseApp.api;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by martinsstrengis on 14/04/15. Yey
 */
public interface ApiInterface<T> extends Response.ErrorListener{
    T parseData(JSONObject object);
    void onSuccess(JSONObject rawData, T items);
}
