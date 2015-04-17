package lv.androiddev.BaseApp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import org.json.JSONObject;

import java.util.ArrayList;

import lv.androiddev.BaseApp.utils.BaseItem;
import lv.androiddev.BaseApp.utils.BaseRecyclerViewAdapter;
import lv.androiddev.BaseApp.views.BaseRecyclerView;
import lv.androiddev.BaseApp.views.BaseSwipeRefreshLayout;

/**
 * Created by martinsstrengis on 14/04/15. Yey
 */
public abstract class BaseListFragment extends BaseFragment {
    public boolean _configLoadMoreFromTop = false;
    public boolean _configDisableLoadMore = false;
    public boolean _configFirstElementIsStatic = false;

    public ArrayList<BaseItem> data = new ArrayList<>();
    public BaseRecyclerView recyclerView;
    public BaseSwipeRefreshLayout swipeRefreshLayout;
    public BaseRecyclerViewAdapter adapter;


    private boolean mIsRefreshing = false;

    public void init(){
        recyclerView = (BaseRecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout = (BaseSwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mIsRefreshing = true;
                    page = 1;
                    load(true);
                }
            });
        }

        if(!_configDisableLoadMore) {
            recyclerView.setOnLoadMoreListener(new BaseRecyclerView.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    page++;
                    load(false);
                }
            });
        }

        if(_configLoadMoreFromTop){
            recyclerView.setLoadMoreFromTop();
        }

        adapter = new BaseRecyclerViewAdapter(recyclerView, getActivity(), data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void dataLoaded(JSONObject rawData, ArrayList<BaseItem> items){
        if(mIsRefreshing){
            if(_configFirstElementIsStatic){
                adapter.clearAllExceptFirst();
            }else {
                adapter.clear();
            }
            mIsRefreshing = false;
        }

        adapter.addAll(items);
    }

    @Override
    public void showRequestIndicator(){
        super.showRequestIndicator();

        recyclerView.setIsLoading(true);
    }

    @Override
    public void hideRequestIndicator(){
        recyclerView.setIsLoading(false);
        if(swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }

        super.hideRequestIndicator();
    }
}
