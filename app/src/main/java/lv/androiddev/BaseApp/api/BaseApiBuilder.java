package lv.androiddev.BaseApp.api;

import com.android.volley.Request;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import lv.androiddev.BaseApp.BaseApplication;

/**
 * Created by martinsstrengis on 14/04/15. Yey
 */
public class BaseApiBuilder {
    public static String BASE_URL = ""; //on Application onCreate set your url

    public ApiRequestList request;

    private HashMap<String, String> mPostFields;
    private HashMap<String, String> mHeaders;
    private ApiInterface mApiInterface;
    private JSONObject mParams;
    private String mUrl;

    public BaseApiBuilder setUrl(String url){
        mUrl = url;
        return this;
    }

    public BaseApiBuilder setListener(ApiInterface apiInterface){
        mApiInterface = apiInterface;
        return this;
    }

    public BaseApiBuilder setParams(JSONObject params){
        mParams = params;
        return this;
    }

    public BaseApiBuilder addPostField(String key, String value){
        if(mPostFields == null){
            mPostFields = new HashMap<>();
        }

        mPostFields.put(key, value);
        return this;
    }

    public BaseApiBuilder add(String key, String value){
        try {
            if(mUrl.indexOf("?") > -1) {
                mUrl += "&"+ key + "=" + URLEncoder.encode(value, "UTF-8");
            }else{
                mUrl += "?"+ key + "=" + URLEncoder.encode(value, "UTF-8");
            }
        }catch (Exception ignore){

        }
        return this;
    }

    public BaseApiBuilder addHeader(String key, String value){
        if(mHeaders == null){
            mHeaders = new HashMap<>();
        }

        mHeaders.put(key, value);
        return this;
    }

    public void execute(){
        request = new ApiRequestList(Request.Method.POST, mUrl == null ? BASE_URL : mUrl, mPostFields, mHeaders, mParams, mApiInterface);
        BaseApplication.RQ.add(request);
    }
}
