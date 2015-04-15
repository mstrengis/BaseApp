package lv.androiddev.BaseApp;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import lv.androiddev.BaseApp.utils.LruBitmapCache;

/**
 * Created by martinsstrengis on 14/04/15. Yey
 */
public class BaseApplication extends Application {
    public static RequestQueue RQ;
    public static ImageLoader IMAGE_LOADER;
    public static LruBitmapCache mBitmapCache;

    @Override
    public void onCreate(){
        super.onCreate();

        RQ = Volley.newRequestQueue(getApplicationContext(), Integer.MAX_VALUE);

        int maxMemory = (int) (Runtime.getRuntime().maxMemory());
        int max = maxMemory / 8;
        int cacheSize = Math.min(max, 1024 * 1024 * 8);
        mBitmapCache = new LruBitmapCache(cacheSize);
        IMAGE_LOADER = new ImageLoader(RQ, mBitmapCache);
    }
}
