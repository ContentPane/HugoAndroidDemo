package com.flj.latte.util.timer;

import java.util.TimerTask;

/**
 * Created by 傅令杰 on 2017/4/22
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    /**
     * 【警告】
     * 使用接口回调的一定要判断是否为空；
     * 否则会有很多麻烦
     */
    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
