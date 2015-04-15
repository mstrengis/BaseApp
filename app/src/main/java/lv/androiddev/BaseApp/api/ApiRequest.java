package lv.androiddev.BaseApp.api;


import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class ApiRequest<T> extends Request<T> {

    private static final String PROTOCOL_CHARSET = "utf-8";
    private JSONObject mParams, mJSONRawData;
    private ApiInterface mApiInterface;
    private HashMap<String, String> mPostFields, mHeaders;

    public ApiRequest(int method, String url, HashMap<String, String> postFields, HashMap<String, String> headers, JSONObject params, ApiInterface<T> apiInterface) {
        super(method, url, apiInterface);
        mParams = params;
        mApiInterface = apiInterface;
        mHeaders = headers;
        mPostFields = postFields;

        if(mPostFields == null){
            mPostFields = new HashMap<>();
        }

        if(mHeaders == null){
            mHeaders = new HashMap<>();
        }
        //setRetryPolicy(new DraugiemRetryPolicy()); //TODO request policy
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            mJSONRawData = new JSONObject(new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers)));
            return (Response<T>) Response.success(mApiInterface.parseData(mJSONRawData), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        mApiInterface.onSuccess(mJSONRawData, response);
    }

    @Override
    public HashMap<String, String> getParams(){
        return mPostFields;
    }

    @Override
    public HashMap<String, String> getHeaders(){
        return mHeaders;
    }

    @Override
    public byte[] getBody() {
        try {
            return mParams == null ? null : mParams.toString().getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mParams, PROTOCOL_CHARSET);
            return null;
        }
    }
}
