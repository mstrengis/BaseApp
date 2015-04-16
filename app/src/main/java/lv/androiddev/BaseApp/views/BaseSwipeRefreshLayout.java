package lv.androiddev.BaseApp.views;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class BaseSwipeRefreshLayout extends SwipeRefreshLayout {
    public BaseSwipeRefreshLayout(Context context, AttributeSet set){
        super(context, set);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        try {
            return super.onTouchEvent(event);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
