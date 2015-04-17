package lv.androiddev.BaseApp.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import lv.androiddev.BaseApp.views.BaseRecyclerView;

/**
 * Created by martinsstrengis on 14/04/15. Yey
 */
public abstract class BaseItem<T extends BaseRecyclerViewHolder> {
    public long id;
    public int viewType;
    private T mViewHolder;
    private BaseRecyclerView mRecyclerView;

    public abstract T getViewHolder(LayoutInflater inflater, ViewGroup parent);
    public abstract void setDataToHolder(T viewHolder);
    public void setViewHolder(BaseRecyclerView baseRecyclerView, T viewHolder){
        mRecyclerView = baseRecyclerView;
        mViewHolder = viewHolder;
        if(mRecyclerView.hasItemClickListener()) {
            mViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecyclerView.hasItemClickListener()) {
                        mRecyclerView.itemClick(v);
                    }
                }
            });
        }

        setDataToHolder(viewHolder);
    }
}