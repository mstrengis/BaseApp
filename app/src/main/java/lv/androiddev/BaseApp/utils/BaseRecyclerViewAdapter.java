package lv.androiddev.BaseApp.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by martinsstrengis on 14/04/15. Yey
 */
public class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder>{
    private ArrayList<BaseItem> mItems;
    private LayoutInflater mInflater;
    private SparseArray<BaseItem> mItemsToType = new SparseArray<>();
    public BaseRecyclerViewAdapter(Context context, ArrayList<BaseItem> items){
        mItems = items;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return mItemsToType.get(viewType).getViewHolder(mInflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder baseRecyclerViewHolder, int position) {
        mItems.get(position).setViewHolder(baseRecyclerViewHolder);
    }

    @Override
    public int getItemViewType(int position){
        BaseItem item = mItems.get(position);
        mItemsToType.put(item.viewType, item);
        return item.viewType;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
