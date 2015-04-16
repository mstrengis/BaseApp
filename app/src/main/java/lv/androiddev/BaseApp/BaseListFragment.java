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

    public ArrayList<BaseItem> data = new ArrayList<>();
    public BaseRecyclerView recyclerView;
    public BaseSwipeRefreshLayout swipeRefreshLayout;
    public BaseRecyclerViewAdapter adapter;

    public void init(){
        recyclerView = (BaseRecyclerView) rootView.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (BaseSwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    page = 1;
                    load(true);
                }
            });
        }

        recyclerView.setOnLoadMoreListener(new BaseRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                load(false);
            }
        });

        if(_configLoadMoreFromTop){
            recyclerView.setLoadMoreFromTop();
        }

        adapter = new BaseRecyclerViewAdapter(getActivity(), data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void dataLoaded(JSONObject rawData, ArrayList<BaseItem> items){
        if(page == 1){
            adapter.clear();
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
        super.hideRequestIndicator();
    }
}
