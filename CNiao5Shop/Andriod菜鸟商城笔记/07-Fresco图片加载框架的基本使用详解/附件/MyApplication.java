package com.nan.frescodemo;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		//在Application初始化的时候初始化Fresco
		Fresco.initialize(this);
	}
}
