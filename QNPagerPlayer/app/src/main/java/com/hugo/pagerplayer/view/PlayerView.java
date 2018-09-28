package com.hugo.pagerplayer.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PlayerState;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.hugo.pagerplayer.Config;
import com.hugo.pagerplayer.R;
import com.hugo.pagerplayer.activity.ItemPagerActivity;



public class PlayerView extends LinearLayout implements View.OnClickListener, PLOnInfoListener {

    final static String TAG = "==PlayerView==";

    Context context;

    LinearLayout ll;
    TextView tvPos;
    RelativeLayout rlContainer;
    PLVideoTextureView player;
    View mask;

    int postion;
    String url;
    long curPos = 0;
    boolean needReset = false;

    public PlayerView(Context context) {
        this(context, null);
    }

    public PlayerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.view_player, this);
        ll = (LinearLayout) findViewById(R.id.ll_player);
        tvPos = (TextView) findViewById(R.id.tv_pos);
        rlContainer = (RelativeLayout) findViewById(R.id.rl_container);
        player = (PLVideoTextureView) findViewById(R.id.pvv);
        mask = findViewById(R.id.mask);
        findViewById(R.id.btn_zoom).setOnClickListener(this);
        findViewById(R.id.btn_start).setOnClickListener(this);

        ll.setOnClickListener(this);
    }

    public void setPlayer(int position, String url) {
        this.postion = position;
        this.url = url;

        mask.setVisibility(VISIBLE);
        if (player.getPlayerState() == PlayerState.PLAYING) {
            player.stopPlayback();
        }

        tvPos.setText("pos==" + position);
        player.setOnInfoListener(this);
        if (null != player.getTag() && position != (int) player.getTag()) {
            mask.setVisibility(VISIBLE);
            needReset = true;
        } else {
            needReset = false;
        }

        if (player.getPlayerState() == PlayerState.PAUSED) {
            mask.setVisibility(VISIBLE);
            player.setVisibility(INVISIBLE);

            needReset = true;
            if (null != player.getTag())
                Log.e("SSSSS", "init paused==" + position + "==" + player.getTag());
        }
        player.setTag(position);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_player:
                player.setVisibility(VISIBLE);
                Log.e("SSSSS", "player on click " + this.postion + "==state==" + player.getPlayerState());
                if (player.getPlayerState() == PlayerState.PLAYING) {
                    mask.setVisibility(INVISIBLE);
                    player.pause();
                } else if (player.getPlayerState() == PlayerState.PREPARED) {
                    player.setVideoPath(url);
                    player.start();
                } else if (player.getPlayerState() == PlayerState.PAUSED) {
                    mask.setVisibility(INVISIBLE);
                    if (needReset) {
                        player.setVideoPath(url);
                        mask.setVisibility(VISIBLE);
                    }
                    player.start();
                } else {
                    player.setVideoPath(url);
                    player.setLooping(true);
                    player.start();
                }
                break;
//            case R.id.btn_zoom:
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rlContainer.getLayoutParams();
//                int w = params.width;
//                int h = params.height;
//                Log.e(TAG, "w==" + w + "==h==" + h);
//                params.width = 400;
//                params.height = 300;
//                rlContainer.setLayoutParams(params);
//                player.setDisplayOrientation(90);
//                break;
            case R.id.btn_start:
                player.pause();
                Intent intent = new Intent(context, ItemPagerActivity.class);
                intent.putExtra(Config.POSITION, postion);

                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(
                        R.anim.activity_horizonal_entry,
                        R.anim.activity_half_horizonal_exit);
                break;
        }
    }

    @Override
    public void onInfo(int what, int extra) {
        Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
        switch (what) {
            case PLOnInfoListener.MEDIA_INFO_BUFFERING_START:
                break;
            case PLOnInfoListener.MEDIA_INFO_BUFFERING_END:
                break;
            case PLOnInfoListener.MEDIA_INFO_VIDEO_RENDERING_START:
                mask.setVisibility(View.INVISIBLE);
                Log.e(TAG, "First video render time: " + extra + "ms");
                break;
            case PLOnInfoListener.MEDIA_INFO_AUDIO_RENDERING_START:
                Log.i(TAG, "First audio render time: " + extra + "ms");
                break;
            case PLOnInfoListener.MEDIA_INFO_VIDEO_FRAME_RENDERING:
                curPos = player.getCurrentPosition();
                Log.i(TAG, "video frame rendering, ts = " + extra);
                break;
            case PLOnInfoListener.MEDIA_INFO_AUDIO_FRAME_RENDERING:
                Log.i(TAG, "audio frame rendering, ts = " + extra);
                break;
            case PLOnInfoListener.MEDIA_INFO_VIDEO_GOP_TIME:
                Log.i(TAG, "Gop Time: " + extra);
                break;
            case PLOnInfoListener.MEDIA_INFO_SWITCHING_SW_DECODE:
                Log.i(TAG, "Hardware decoding failure, switching software decoding!");
                break;
            case PLOnInfoListener.MEDIA_INFO_METADATA:
                Log.i(TAG, player.getMetadata().toString());
                break;
            case PLOnInfoListener.MEDIA_INFO_VIDEO_BITRATE:
            case PLOnInfoListener.MEDIA_INFO_VIDEO_FPS:
                break;
            case PLOnInfoListener.MEDIA_INFO_CONNECTED:
                Log.i(TAG, "Connected !");
                break;
            default:
                break;
        }
    }
}
