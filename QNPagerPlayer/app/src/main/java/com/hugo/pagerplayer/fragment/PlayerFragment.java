package com.hugo.pagerplayer.fragment;

import android.app.Activity;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.PLOnBufferingUpdateListener;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnVideoSizeChangedListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;
import com.hugo.pagerplayer.Config;
import com.hugo.pagerplayer.R;
import com.hugo.pagerplayer.activity.ItemPagerActivity;
import com.hugo.pagerplayer.widget.MediaController;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class PlayerFragment extends Fragment implements PLOnInfoListener, PLOnVideoSizeChangedListener, PLOnBufferingUpdateListener, PLOnCompletionListener, PLOnErrorListener {

    final String TAG = "==PlayerFragment==";

    private Toast mToast = null;

    private String mUrl;
    private int mPos;

    private View view;
    private TextView tv;
    //    private PLVideoTextureView player;
    private PLVideoTextureView player;
    private ImageView ivLoading;

    String picUrl = "http://y1.ifengimg.com/d048595168338e17/2015/0319/ori_550a65a953987.jpeg";

    public static Fragment newInstance(String url, int pos) {
        Bundle bundle = new Bundle();
        bundle.putString(Config.URL, url);
        bundle.putInt(Config.POSITION, pos);
        PlayerFragment playerFragment = new PlayerFragment();
        playerFragment.setArguments(bundle);
        return playerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);


        mUrl = getArguments().getString(Config.URL);
        mPos = getArguments().getInt(Config.POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_player, container, false);

        ivLoading = (ImageView) view.findViewById(R.id.iv_loading);

        tv = (TextView) view.findViewById(R.id.tv_url);
        tv.setText("pos==" + mPos + "==" + mUrl);

        player = (PLVideoTextureView) view.findViewById(R.id.player);
        AVOptions options = new AVOptions();
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 0);
        options.setInteger(AVOptions.KEY_MEDIACODEC, 0);
        options.setString(AVOptions.KEY_CACHE_DIR, Config.DEFAULT_CACHE_DIR);
        player.setAVOptions(options);

        player.setOnInfoListener(this);
        player.setOnVideoSizeChangedListener(this);
        player.setOnBufferingUpdateListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);

        player.setLooping(true);
        player.setVideoPath(mUrl);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "page==" + mPos + "==" + this.hashCode() + "==" + mUrl);
        if (mPos == ItemPagerActivity.mIndex) {
            Log.e(TAG, "start onResume==" + mPos);
            player.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showToastTips("start==" + mPos);
                    player.start();
                }
            }, 300);
        }

        Glide.with(getActivity()).load(picUrl).into(ivLoading);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        player.pause();
    }

    private void showToastTips(final String tips) {
        if (((Activity) getContext()) != null)
            ((Activity) getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(getContext(), tips, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            });
    }

    @Subscriber(tag = Config.EVENT_PAUSE)
    void playerPause(int i) {
        if (player != null)
            player.pause();
    }


    @Subscriber(tag = Config.EVENT_START)
    void playerStart(int pos) {
        if (pos == mPos && player != null) {
            player.start();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stopPlayback();
        EventBus.getDefault().unregister(this);
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
                showToastTips("MEDIA_INFO_VIDEO_RENDERING_START");
                ivLoading.setVisibility(View.GONE);
                Log.e(TAG, "First video render time: " + extra + "ms");
                break;
            case PLOnInfoListener.MEDIA_INFO_AUDIO_RENDERING_START:
                Log.i(TAG, "First audio render time: " + extra + "ms");
                break;
            case PLOnInfoListener.MEDIA_INFO_VIDEO_FRAME_RENDERING:
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

    @Override
    public void onVideoSizeChanged(int width, int height) {
        Log.i(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height);

    }

    @Override
    public void onBufferingUpdate(int precent) {
        Log.i(TAG, "onBufferingUpdate: " + precent);
    }

    @Override
    public void onCompletion() {
        Log.i(TAG, "Play Completed !");
        showToastTips("Play Completed !");
    }

    @Override
    public boolean onError(int errorCode) {
        Log.e(TAG, "Error happened, errorCode = " + errorCode);
        switch (errorCode) {
            case PLOnErrorListener.ERROR_CODE_IO_ERROR:
                /**
                 * SDK will do reconnecting automatically
                 */
//                showToastTips("IO Error !");
                player.pause();
                return false;
            case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:
                showToastTips("failed to open player !");
                break;
            case PLOnErrorListener.ERROR_CODE_SEEK_FAILED:
                showToastTips("failed to seek !");
                break;
            default:
                showToastTips("unknown error !");
                break;
        }
        return true;
    }
}
