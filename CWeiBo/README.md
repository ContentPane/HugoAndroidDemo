[**菜鸟微博**](http://www.cniao5.com)通过对新浪微博开发案例的详细解析，讲解了一个完整的 Android 实际项目的开发过程。

有新浪微博的主要功能，有Toolbar,RecyclerView等最新控件的用法；各种快速开发框架的使用，比如 Glide,PhotoView,EventBus,OKHttp，pullToRefresh等。

学习视频+源码  视频中还会讲到MVP设计模式以及一些架构师的入门知识。 

[Toolbar的详细介绍和自定义Toolbar](https://blog.csdn.net/da_caoyuan/article/details/79557704)  

[App底部菜单-FragmentTabHost](https://www.jianshu.com/p/4d4a83945193)

[微博android平台](http://open.weibo.com/wiki/Sdk/android)

抽象类用法：networks/BaseNetWork  
抽象方法：  
public abstract WeiboParameters onPrepare();
public abstract void onFinish(HttpResponse response, boolean success);


HomepageListAdapter extends RecyclerView.Adapter

**Glide的优势** 相对于Picasso而言，Glide与 Picasso 的用法基本类似。Glide在缓存图片的时候是缓存全尺寸的图片，而Glide则是根据ImageView的大小进行缓存的。而且， Glide做到了Picasso没有做到的：加载 Gif.






