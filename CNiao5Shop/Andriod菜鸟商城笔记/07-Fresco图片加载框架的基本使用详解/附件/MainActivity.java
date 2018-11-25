package com.nan.frescodemo;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class MainActivity extends AppCompatActivity {

	private SimpleDraweeView draweeView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);

		/**
		 * 1.基本使用，设置ImageURI就可以了
		 */
//		Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/fresco-logo.png");
//		draweeView.setImageURI(uri);

		/**
		 * 2.渐进式JPEG图
		 */
//		ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse("http://d05.res.meilishuo.net/pic/_o/81/03/393e9267381780d127df4a0d4a2e_640_842.cj.jpg_e80dc848_s7_450_680.jpg"))
//				.setProgressiveRenderingEnabled(true)
//				.build();
//		PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
//				.setImageRequest(request)
//				.build();
//		draweeView.setController(controller);

		/**
		 * 3.多图请求
		 */
//		DraweeController controller = Fresco.newDraweeControllerBuilder()
//				.setLowResImageRequest(ImageRequest.fromUri("http://d05.res.meilishuo.net/pic/_o/5c/bb/619d234d09d9e8df3233aac54682_640_960.cf.jpg_127da65a_s7_450_680.jpg"))
//				.setImageRequest(ImageRequest.fromUri("http://d04.res.meilishuo.net/pic/_o/17/87/6b240c9e4a05a72b56719375cf49_640_960.ch.jpg_0a2de440_s7_450_680.jpg"))
//				.build();
//		draweeView.setController(controller);

		/**
		 * 4.事件监听
		 */
		ControllerListener<ImageInfo> listener = new ControllerListener<ImageInfo>() {
			@Override
			public void onSubmit(String id, Object callerContext) {

			}

			@Override
			public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {

			}

			@Override
			public void onIntermediateImageSet(String id, ImageInfo imageInfo) {

			}

			@Override
			public void onIntermediateImageFailed(String id, Throwable throwable) {

			}

			@Override
			public void onFailure(String id, Throwable throwable) {

			}

			@Override
			public void onRelease(String id) {

			}
		};

		DraweeController controller = Fresco.newDraweeControllerBuilder()
				.setLowResImageRequest(ImageRequest.fromUri("http://d05.res.meilishuo.net/pic/_o/5c/bb/619d234d09d9e8df3233aac54682_640_960.cf.jpg_127da65a_s7_450_680.jpg"))
				.setImageRequest(ImageRequest.fromUri("http://d04.res.meilishuo.net/pic/_o/17/87/6b240c9e4a05a72b56719375cf49_640_960.ch.jpg_0a2de440_s7_450_680.jpg"))
				.setControllerListener(listener)
				.build();
		draweeView.setController(controller);
	}


}
