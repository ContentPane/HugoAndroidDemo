package com.nan.cnshop.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nan.cnshop.R;
import com.nan.cnshop.bean.Page;

import java.util.List;

/**
 * 热卖商品的列表适配器
 */
public class HotWaresListAdapter extends RecyclerView.Adapter<HotWaresListAdapter.HotWaresViewHolder> {

	private List<Page.Ware> mDatas;
	private Context mCtx;

	public HotWaresListAdapter(List<Page.Ware> datas, Context mCtx) {
		this.mDatas = datas;
		this.mCtx = mCtx;
	}

	@Override
	public HotWaresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(mCtx);
		View v = inflater.inflate(R.layout.item_hot_wares, parent, false);
		HotWaresViewHolder viewHolder = new HotWaresViewHolder(v);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(HotWaresViewHolder holder, int position) {
		Page.Ware ware = mDatas.get(position);
		holder.img_icon.setImageURI(Uri.parse(ware.getImgUrl()));
		holder.tv_title.setText(ware.getName());
		holder.tv_price.setText(ware.getPrice() + "");
	}

	@Override
	public int getItemCount() {
		return mDatas.size();
	}

	class HotWaresViewHolder extends RecyclerView.ViewHolder {

		private final SimpleDraweeView img_icon;
		private final TextView tv_title;
		private final TextView tv_price;
		private final Button btn_add;

		public HotWaresViewHolder(View itemView) {
			super(itemView);

			img_icon = (SimpleDraweeView) itemView.findViewById(R.id.drawee_view);
			tv_title = (TextView) itemView.findViewById(R.id.text_title);
			tv_price = (TextView) itemView.findViewById(R.id.text_price);
			//需要进行监听
			btn_add = (Button) itemView.findViewById(R.id.btn_add);
		}
	}

	/**
	 * 下拉刷新，清除原有数据，添加新数据
	 *
	 * @param newData
	 */
	public void refreshData(List<Page.Ware> newData) {
		mDatas.clear();
		mDatas.addAll(newData);
		notifyItemRangeChanged(0, mDatas.size());
	}

	/**
	 * 在原来数据的末尾追加新数据
	 *
	 * @param moreData
	 */
	public void loadMoreData(List<Page.Ware> moreData) {
		int lastPosition = mDatas.size();
		mDatas.addAll(lastPosition, moreData);
		notifyItemRangeInserted(lastPosition, moreData.size());
	}

}
