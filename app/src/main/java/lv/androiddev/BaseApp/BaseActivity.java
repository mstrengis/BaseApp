package lv.androiddev.BaseApp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by martinsstrengis on 14/04/15. Yey
 */
public class BaseActivity extends ActionBarActivity {
    public int layout;

    public void onCreate(Bundle state) {
        super.onCreate(state);

        setContentView(layout);
    }
}

