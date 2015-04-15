package lv.androiddev.BaseApp.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class BaseRecyclerView extends RecyclerView {

    public static final int SCROLL_UP = 1;
    public static final int SCROLL_DOWN = 2;
    public static final int SCROLL_NONE = 0;

    private int mLastScrollState = SCROLL_NONE;

    private int mInitialScroll, mLastScrollRange;
    private boolean mIsLoading = false;
    private boolean mLoadMoreFromTop = false;

    public BaseRecyclerView(Context context) {
        super(context);
        init();
    }

    public BaseRecyclerView(Context context, AttributeSet set){
        super(context, set);
        init();
    }

    public BaseRecyclerView(Context context, AttributeSet set, int style){
        super(context, set, style);
        init();
    }

    public void setIsLoading(boolean loading){
        mIsLoading = loading;
        mInitialScroll = 0;
    }

    public void setLoadMoreFromTop(){
        mLoadMoreFromTop = true;
    }

    void init() {
        setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

                if (getChildCount() > 1) {
                    if (mLoadMoreFromTop) {
                        int firstChildPosition = getChildPosition(recyclerView.getChildAt(0));
                        if ((scrollState == RecyclerView.SCROLL_STATE_SETTLING || scrollState == RecyclerView.SCROLL_STATE_DRAGGING) && firstChildPosition < 2) {
                            if (mOnLoadMoreListener != null && !mIsLoading) {
                                mOnLoadMoreListener.onLoadMore();
                            }
                        }
                    } else {
                        int lastChildPosition = getChildPosition(recyclerView.getChildAt(recyclerView.getChildCount() - 1));
                        if ((scrollState == RecyclerView.SCROLL_STATE_SETTLING || scrollState == RecyclerView.SCROLL_STATE_DRAGGING)
                                && lastChildPosition >= getAdapter().getItemCount() - 3) {
                            if (mOnLoadMoreListener != null && !mIsLoading) {
                                mOnLoadMoreListener.onLoadMore();
                            }
                        }
                    }

                    if (mScrollListener != null) {
                        if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                            mLastScrollState = SCROLL_NONE;
                            mScrollListener.onScrollDirection(SCROLL_NONE);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mLoadMoreFromTop) {
                    int firstChildPosition = getChildPosition(recyclerView.getChildAt(0));
                    if (firstChildPosition < 2) {
                        if (mOnLoadMoreListener != null && !mIsLoading) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                    }
                } else {
                    int lastChildPosition = getChildPosition(recyclerView.getChildAt(recyclerView.getChildCount() - 1));
                    if (lastChildPosition >= getAdapter().getItemCount() - 3) {
                        if (mOnLoadMoreListener != null && !mIsLoading) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                    }
                }

                int scrolledOffset = computeVerticalScrollOffset();
                if (mScrollListener != null && mInitialScroll != 0 && mLastScrollRange == computeVerticalScrollRange()) {
                    boolean scrollUp = scrolledOffset > mInitialScroll;
                    boolean scrollDown = scrolledOffset < mInitialScroll;

                    int scrollState = scrollUp ? SCROLL_UP : scrollDown ? SCROLL_DOWN : SCROLL_NONE;
                    if (mLastScrollState != scrollState) {
                        mLastScrollState = scrollState;
                        mScrollListener.onScrollDirection(scrollState);
                    }
                }

                mLastScrollRange = computeVerticalScrollRange();

                if (mScrollListener != null) {
                    mScrollListener.onScroll(scrolledOffset);
                }

                mInitialScroll = scrolledOffset;
            }
        });
    }

    public int getLastScrollRange(){
        return mLastScrollRange;
    }

    private OnLoadMoreListener mOnLoadMoreListener;
    private ScrollListener mScrollListener;

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public ScrollListener getScrollListener(){
        return mScrollListener;
    }

    public static interface OnLoadMoreListener {

        public void onLoadMore();
    }

    public static interface ScrollListener {

        public void onScroll(int scrolledOffset);
        public void onScrollDirection(int scrollDirection);
    }

    public void setScrollListener(ScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            int childCount = getChildCount();
            if(childCount > 1) {
                if (getChildPosition(getChildAt(getChildCount() - 1)) == getAdapter().getItemCount() - 1) {
                    if (getChildPosition(getChildAt(0)) == 0 && mOnLoadMoreListener != null && !mIsLoading){
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }
        }

        return super.onTouchEvent(event);
    }
}