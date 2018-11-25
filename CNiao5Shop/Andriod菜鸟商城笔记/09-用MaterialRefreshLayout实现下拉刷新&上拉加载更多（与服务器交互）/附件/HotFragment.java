package com.nan.cnshop.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nan.cnshop.R;
import com.nan.cnshop.adapter.HotWaresListAdapter;
import com.nan.cnshop.bean.Page;
import com.nan.cnshop.http.BaseCallback;
import com.nan.cnshop.http.OkHttpHelper;
import com.nan.cnshop.widget.CNToolbar;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;


public class HotFragment extends Fragment {

	@ViewInject(R.id.toolbar)
	private CNToolbar toolbar;

	@ViewInject(R.id.refresh_view)
	private MaterialRefreshLayout refreshLayout;

	@ViewInject(R.id.hot_ware_list)
	private RecyclerView hotWareList;

	OkHttpHelper httpHelper = OkHttpHelper.getinstance();
	private HotWaresListAdapter mAdapter;

	//用于记录当前是第几页
	private int curPage = 1;
	//用于记录一页应该请求多少条数据
	private int pageSize = 10;
	//需要显示的数据
	private List<Page.Ware> wareList;

	//用于记录当前是何种状态，在请求完数据之后根据不同的状态进行不同的操作
	private static final int STATE_INIT = 0;
	private static final int STATE_REFRESH = 1;
	private static final int STATE_LOAD_MORE = 2;
	//用于记录当前的状态
	private int curState = 0;
	//用于记录总页数，在上拉的时候判断还有没有更多数据
	private int totalPage = 1;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_hot, container, false);
		ViewUtils.inject(this, view);

		initRefreshLayout();
		getData();

		return view;
	}

	private void initRefreshLayout() {
		//设置支持下拉加载更多
		refreshLayout.setLoadMore(true);
		//刷新以及加载回调
		refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
			@Override
			public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
				//下拉刷新回调，更改当前状态为下拉刷新状态，把当前页置为第一页，
				//向服务器请求数据
				curState = STATE_REFRESH;
				curPage = 1;
				getData();
			}

			@Override
			public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
				//上拉加载更多回调，更改当前状态为上拉加载更多状态，页数加1
				//并且在判断还有更多的情况下向服务器请求数据
				//否则提示用户没有更多数据，关闭上拉加载更多
				curState = STATE_LOAD_MORE;
				curPage = curPage + 1;
				if (curPage <= totalPage) {
					getData();
				} else {
					Toast.makeText(getActivity(), "没有更多啦O(∩_∩)O", Toast.LENGTH_SHORT).show();
					refreshLayout.finishRefreshLoadMore();
				}
			}
		});
	}


	private void getData() {

		String uri = "http://112.124.22.238:8081/course_api/wares/hot?curPage=" + curPage + "&pageSize=" + pageSize;

		httpHelper.get(uri, new BaseCallback<Page>() {

			@Override
			public void onRequestBefore() {

			}

			@Override
			public void onFailure(Request request, Exception e) {

			}

			@Override
			public void onSuccess(Response response, Page page) {
				//得到数据
				wareList = page.getWareList();
				//得到总页数，上拉加载更多的时候用于判断还有没有更多数据
				totalPage = page.getTotalPage();
				showData();
			}

			@Override
			public void onError(Response response, int errorCode, Exception e) {

			}
		});
	}

	private void showData() {
		switch (curState) {
			case STATE_INIT:
				//初始化状态，初始化列表
				mAdapter = new HotWaresListAdapter(wareList, getActivity());
				hotWareList.setAdapter(mAdapter);
				hotWareList.setLayoutManager(new LinearLayoutManager(getActivity()));
				hotWareList.setItemAnimator(new DefaultItemAnimator());
				break;

			case STATE_REFRESH:
				//下拉刷新状态，刷新数据，列表回到最顶端，关闭下拉刷新
				mAdapter.refreshData(wareList);
				hotWareList.scrollToPosition(0);
				refreshLayout.finishRefresh();
				break;

			case STATE_LOAD_MORE:
				//上拉加载更多状态，追加数据，关闭上拉加载更多
				mAdapter.loadMoreData(wareList);
				refreshLayout.finishRefreshLoadMore();
				break;
		}


	}

}
