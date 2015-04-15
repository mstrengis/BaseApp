package lv.androiddev.BaseApp.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by martinsstrengis on 14/04/15. Yey
 */
public abstract class BaseItem<T extends BaseRecyclerViewHolder> {
    public int viewType;

    public abstract T getViewHolder(LayoutInflater inflater, ViewGroup parent);
    public abstract void setViewHolder(T viewHolder);
}