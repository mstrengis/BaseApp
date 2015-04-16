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

    public void add(BaseItem item){
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void addAll(ArrayList<BaseItem> items){
        if(items.size() == 0){
            return;
        }

        int itemCount = items.size();
        mItems.addAll(items);

        notifyItemRangeInserted(itemCount, items.size());
    }

    public void addAll(int startPos, ArrayList<BaseItem> items){
        int itemCount = items.size();
        mItems.addAll(items);

        notifyItemRangeInserted(itemCount, items.size());
    }

    public void insert(BaseItem item, int position){
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public boolean remove(BaseItem item){
        int removed = mItems.indexOf(item);
        if(removed >= 0) {
            mItems.remove(item);
            notifyItemRemoved(removed);
            return true;
        }
        return false;
    }

    public void clear(){
        mItems.clear();
        notifyDataSetChanged();
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
