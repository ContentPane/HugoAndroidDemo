package com.hugo.pagerplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;
import com.hugo.pagerplayer.Config;
import com.hugo.pagerplayer.R;
import com.hugo.pagerplayer.activity.ItemPagerActivity;
import com.hugo.pagerplayer.view.PlayerView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;
import java.util.Map;

public class StringAdapter extends BaseListAdapter<String> {

    final static String TAG = "==StringAdapter==";

    public static final int ITEM_PLAYER = 0x00;
    public static final int ITEM_STRING = 0x01;

    public StringAdapter(Context context, List<String> models) {
        super(context, models);
        EventBus.getDefault().register(context);
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 5) {
            return ITEM_STRING;
        } else {
            return ITEM_STRING;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = null;
        switch (getItemViewType(position)) {
            case ITEM_PLAYER:
                holder = BaseViewHolder.getViewHolder(context,
                        convertView,
                        parent,
                        R.layout.item_player,
                        position);
                String url = ItemPagerActivity.TEST_URL_ARRAY[position % ItemPagerActivity.TEST_URL_ARRAY.length].trim();

                PlayerView player = holder.getView(R.id.player_view);
                player.setPlayer(position, url);
                break;
            default:
                holder = BaseViewHolder.getViewHolder(context,
                        convertView,
                        parent,
                        R.layout.item_string,
                        position);

                LinearLayout ll = holder.getView(R.id.ll);
                TextView tv = holder.getView(R.id.tv_string);

                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ItemPagerActivity.class);
                        intent.putExtra(Config.POSITION, position);

                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(
                                R.anim.activity_horizonal_entry,
                                R.anim.activity_half_horizonal_exit);


                    }
                });
                if (tv != null)
                    tv.setText(models.get(position));
                break;
        }
        return holder.getConvertView();
    }
}
