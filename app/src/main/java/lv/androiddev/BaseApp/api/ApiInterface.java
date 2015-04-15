package lv.androiddev.BaseApp.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;

import lv.androiddev.BaseApp.utils.BaseItem;

/**
 * Created by martinsstrengis on 14/04/15. Yey
 */
public abstract class ApiInterface implements Response.ErrorListener {
    public abstract ArrayList<BaseItem> parseData(JSONObject object);
    public abstract void onSuccess(JSONObject rawData, ArrayList<BaseItem> items);
    public abstract void onFailure();

    @Override
    public void onErrorResponse(VolleyError error) {
        onFailure();
    }
}
