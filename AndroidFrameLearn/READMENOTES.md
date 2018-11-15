## OkHttp  
[OkHttp使用介绍](http://www.cnblogs.com/ct2011/p/4001708.html)  
[OkHttp使用进阶](https://www.cnblogs.com/ct2011/p/3997368.html) 译自[OkHttp Github官方教程](https://github.com/square/okhttp/wiki/Recipes)  
[OkHttp 源码分析 from 【Piasy】](https://blog.piasy.com/2016/07/11/Understand-OkHttp/)

## Retrofit
### 原理
* App应用程序通过Retrofit请求网络，实际上是使用Retrofit接口层封装请求参数，之后由Okhttp完成后续的请求操作。
* 在服务端返回数据之后，OKhttp将原始的结果交给Retrofit，Retrofit根据用户的需求对结果进行解析。

### 步骤
* 添加Retrofit库的依赖，添加网络权限；
* 创建 接受服务器返回数据的 类；
* 创建 用于描述网络请求 的接口；
* 创建 Retrofit 实例；
* 创建 网络请求接口实例；
* 发送网络请求（异步/同步）；
* 处理服务器返回的数据。

### 静态代理
* 代理模式：为其他对象提供一种代理，用以控制对这个对象的访问
* eg.海外购物
* proxy1

### 动态代理
* 无侵入
* 通俗：增强方法
* 动态代理：代理类子在程序运行时创建的代理方式
* 相比于静态代理，不用频繁修改每个代理类
* 动态代理写法：1，jdk动态代理(proxy2)；2，CGLIB

### 动态代理总结
* 与运行期
* InvocationHandler接口和Proxy类
* 动态代理和静态代理最大的不同：动态代理的代理类是在运行期间动态生成

### Retrofit网络通信八步
* 创建retrofit实例;
* 定义一个网络请求接口并为接口中的方法添加注解；
* 通过 动态代理 生成 网络请求对象；
* 通过 网络请求适配器 将 网络请求对象 进行平台适配；
* 通过 网络请求执行器call 发送网络请求；
* 通过 数据转换器 解析数据；
* 通过 回调执行器 切换线程；
* 用户在主线程处理返回结果。


[深入浅出 Retrofit-知乎](https://zhuanlan.zhihu.com/p/24109629)

## Glide 

### Glide几个基本概念
* Model
* Data
* Resource
* TransformedResource
* TranscodedResource
* Target

[Google推荐——Glide使用详解](https://www.jianshu.com/p/7ce7b02988a4)  
[Glide 源码分析 from 【郭霖】](http://blog.csdn.net/column/details/15318.html)

## LeakCanary 性能优化框架
* 原理：watch一个即将要销毁的对象 
* 内存：1，栈（stack）2，堆（heap）3，方法区（method）  

[LeakCanary原理到核心类源码分析](https://www.jianshu.com/p/1e7e9b576391)  
[LeakCanary中文使用说明](https://www.liaohuqiu.net/cn/posts/leak-canary-read-me/)  

### OOM 
* 内存泄漏往往是罪魁祸首
* LeakCanary，它可以实时监测Activity  

### LeakCanary原理
* Activity Destroy之后将它放在一个WeakReference
* 这个WeakReference关联到一个ReferenceQueue
* 查看ReferenceQueue是否存在Activity的引用
* 如果该Activity泄漏了，Dump出heap信息，然后再去分析泄漏路径

#### Java中4种引用类型
* 强引用（StrongReference）
* 软引用（SoftReference）
* 弱引用（WeakReference）
* 虚引用

### LeakCanary源码分析
* 首先会创建一个refwatcher,启动一个ActivityRefWatcher
* 通过ActivityLifecycleCallvacks 把 Activity 的 onDestroy 生命周期关联
* 最后在线程池中去开始分析我们的泄漏
* 解析hprof文件，把这个文件封装成snapshot
* 根据弱引用和前面定义的key值，确定泄漏的对象
* 找到最短泄漏路径，作为结果反馈出来

## ButterKnife 注解库
[JakeWharton/butterknife](https://github.com/JakeWharton/butterknife)

### ButterKnife工作原理
* 1，编译的时候扫描注解，并做相应的处理，调用javapoet库生成java代码。
* 调用ButterKnife.bind(this);方法的时候，将ID与对应的上下文绑定在一起。


## blockcanary 性能优化框架



## eventbus 事件发布订阅框架

## dagger2 依赖注入库

## rxjava 异步框架

## picasso 图片框架




## 其他参考资料

### [其他开发者关于这个课程的完整详细笔记](https://www.cnblogs.com/cold-ice/tag/Android%E7%AC%AC%E4%B8%89%E6%96%B9%E6%A1%86%E6%9E%B6/)

- [Lottie 动画开源库使用 & 源码分析](https://github.com/jeanboydev/Android-ReadTheFuckingSourceCode/blob/master/android/Lottie动画开源库使用&源码分析.md)
- [Universal-Image-Loader 源码分析 from 【codeKK】](http://a.codekk.com/detail/Android/huxian99/Android%20Universal%20Image%20Loader%20%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90)
- [Glide 源码分析 from 【郭霖】](http://blog.csdn.net/column/details/15318.html)
- [EventBus 源码分析 from 【codeKK】](http://a.codekk.com/detail/Android/Trinea/EventBus%20%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90)
- [OkHttp 源码分析 from 【Piasy】](https://blog.piasy.com/2016/07/11/Understand-OkHttp/)
- [Volley 源码分析 from 【codeKK】](http://a.codekk.com/detail/Android/grumoon/Volley%20%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90)