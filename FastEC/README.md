# Android通用框架设计与完整电商APP开发

### 模块分解 
**核心model**  

* 路由架构
* HTTP请求
* 照相与二维码及图片剪裁
* 具有共性的通用UI
* 通用的工具
* WebView处理
* 微信登录与支付封装
* 支付宝支付封装
* 诸多重复性的处理

**业务model**

* 相应一类业务的特殊UI
* 相应一类业务需要的通用逻辑
* 相应一类业务的特殊处理

**具体项目model**

* 项目特有的个别功能
* 只有项目需要的第三方库
* 只有该项目会更改的UI及逻辑
* 需要在application model中使用的一些签名和验证

### 电商项目基础骨架

* latte-annotations 类型为 Java Library
* latte-compiler 类型为 Java Library
* latte-core 类型为 ANdroid Library
* latte-ec 类型为 ANdroid Library 
* 项目model类型为Android Application 



### 框架主配置入口的设计与实践


* 【1】只有一个Activity，中间的界面的切换都是使用的fragment，这是经验总结；

* 【2】使用一个全局的application，管理全局的的重复的工作；

* 【3】配置的管理：配置的管理和存储和获取；


#### 配置类的创建
* 【1】WeakHashMap 列表，资源回收及时，推荐使用； 【说明】修改之后的为下面使用的HashMap，因为配置会伴随着应用的一直的运行，因此不能使用弱引用回收；
* 【2】规范：static final 的命名使用大写，并且使用下划线分割；
* 【枚举的使用】在应用程序中是唯一的单例，只能被初始化一次，如果是多线程操作，完全可以使用枚举进行安全的惰性的单例的初始化，可以保证线程安全，相当于线程安全的懒汉模式；

#### 关于单例模式的说明

* 【1】如果使用懒汉模式，就是用双重校验锁，sychronized的关键字，防止线程冲突；在多线程开发的实际项目中一般写的都是有问题的；
* 【2】使用枚举类统一的初始化
* 【3】使用静态内部类的单例模式的初始化；


#### 检查配置

 【思想】在写类变量或者方法变量的时候，尽量让变量不可变性达到最大化。如果此变量在后续的代码中不在修改，则使用final修饰；

 最大限度的避免去更改一个本不应该更改的变量。在jvm虚拟机上，或多或少对final变量和public final的修饰会做优化，性能可以得到提升。

不增加final是可以的，但是推荐加上；

【调用的时机】在应用程序中获取配置的时候调用，如果没有调用Configurator配置一切的方法的时候，就会抛出运行时异常，保证配置的完整性和正确性。

【增加注解】注解的意义：告诉编译系统，这个类型是没有检测过的，可以对该方法不做检查。

### 使用第三方框架搭建单Activity多fragment

#### 参考的第三方的框架
【国人开发的】[Fragmentation](https://github.com/YoKeyword/Fragmentation)

【基类的抽象】子类继承基类，基类封装接口，传入布局的参数； 

[butterKnife](https://github.com/JakeWharton/butterknife)的库引用 

### 网络框架
#### 1.1 使用第三方网络框架  
【第三方框架】Retrofit，封装一个通用的框架，可以使用rxJava和rxAndroid进行封装，比较难，这里不做讲解；

#### 1.2 restful 请求
【参考文章】[restful_api](http://www.ruanyifeng.com/blog/2014/05/restful_api)

#### 1.3 网络请求的具体实现类
【网络请求的具体实现类】RestClient  
【源码】com.flj.latte.net.RestService接口的封装  
【封装枚举类】HttpMethod  
【RetrofitHolder创建成功】构建OkHttp请求

#### 2.1 Restful请求的处理-框架
【说明】首先要考虑网络请求的参数（url传入的值、文件、回调、及loder加载圈）  
【说明】使用建造者模式，将建造者类和宿主类分开；  
【新建建造者类】RestClientBuilder  
#### 2.2 restClient类的参数的定义
【restClient类的参数的定义】restClient类在每次Builder的时候会生成全新的实例，而里面的参数一次更改完毕，不允许二次更改；
#### 2.3 【回调类】callback 包
【回调类】在网路请求之后，会存在网络请求之后的回调，比如：请求失败、请求异常、请求成功等；

[新建CallBack包，书写需要调用的接口]

#### 2.4 完善RestClient
【完善com.flj.latte.net.RestClient】以Builder的形式构造出来了；  

#### 2.5 RestClientBuilder 对数据的设置
【说明】主要完成的数据的传递  

#### 2.6 RestClient的调用  
ExampleDelegate 中 testRestClient()

#### 2.7 RestClientBuilder的改进
【mParams】参数每次都会构建，比较繁琐；==> 生命为全局的变量 或者  创建内部类 

#### 2.8 requset请求  
RestClient.request  
call.enqueue (子线程)  
call.execute（主线程）  
【新建callBack类】新建类 RequestCallbacks 并实现实现接口， 复写方法；
 

#### 3.1 oading框架集成与完善AVLoadingIndicatorView
【地址】[AVLoadingIndicatorView](https://github.com/81813780/AVLoadingIndicatorView)
【说明】在该地址中已经存在怎样使用的步骤；



 

## 附：

[博客园完整课程笔记](https://www.cnblogs.com/Oztaking/tag/_0005_Android%E5%BC%80%E5%8F%91/default.html?page=5)