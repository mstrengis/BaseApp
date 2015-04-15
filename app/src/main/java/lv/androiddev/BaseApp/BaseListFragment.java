package lv.androiddev.BaseApp;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import lv.androiddev.BaseApp.utils.BaseItem;
import lv.androiddev.BaseApp.utils.BaseRecyclerViewAdapter;
import lv.androiddev.BaseApp.views.BaseRecyclerView;

/**
 * Created by martinsstrengis on 14/04/15. Yey
 */
public abstract class BaseListFragment extends BaseFragment {
    public boolean _configLoadMoreFromTop = false;

    public ArrayList<BaseItem> data = new ArrayList<>();
    public BaseRecyclerView recyclerView;
    public BaseRecyclerViewAdapter adapter;

    public abstract BaseRecyclerView getRecyclerView();

    public void init(){
        recyclerView = getRecyclerView();

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
