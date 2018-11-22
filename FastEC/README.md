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

---
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
 

#### 3.1 Loading框架集成与完善AVLoadingIndicatorView
【地址】[AVLoadingIndicatorView](https://github.com/81813780/AVLoadingIndicatorView)
【说明】在该地址中已经存在怎样使用的步骤；

#### 3.2 集成封装获取某种类型的View  
【说明】各种的效果的获取使用过的是反射的技术，但是**反复使用反射会影响设备的性能**；因此做了一个**机制的封装**；

【原理】以**一种缓存的方式创建loader**，不需要每次使用loader的时候进行反射，这样性能会有很大幅度的提高。

[Java基础之—反射（非常重要）](https://blog.csdn.net/sinat_38259539/article/details/71799078)   

#### 3.3 不同的style的枚举的封装
【对不同的类型进行封装】com.flj.latte.ui.loader.LoaderStyle

#### 3.4 对传入的样式/参数封装
com.flj.latte.ui.loader.LatteLoader  
【样式的封装】需要封装是否需要透明度、颜色等值的传入；  style.xml  
【传入样式参数并作为根布局】

```
LatteLoader

final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog); // 传入样式  
final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView); // 将此view作为根布局

```
 
#### 3.5 工具类-填充参数的设置
com.flj.latte.util.dimen.DimenUtil （屏幕的宽高）  
【工具类】新建工具类的权限一般 放为：public static； 

#### 3.6 继续完善LatteLoader类
【设置缩放比例】为了适应 不同的设备的屏幕的不同的大小，需要对加载的loader进行缩放  

【创建集合，统一管理不同类型的loader】【**学习思想**】在不需要loaders的时候，只要遍历集合，一一的关闭loaders即可；  

#### 3.7 网络请求中加入loader  
【说明】handler声明的时候加关键字static；可以避免内存泄露；  


#### 4.0 网络框架优化与完善

#### 4.1【支持原始数据的post请求】 postRaw  
#### 4.2【支持原始数据的put请求】 putRaw
#### 4.3【增加UPLOAD上传方法】

#### 5.0 文件下载功能设计与实现  
【新建com.flj.latte.net.download.DownloadHandler】新建下载处理类；  
【file工具类】没有讲解，课下编写的 com.flj.latte.util.file.FileUtil  
【继续封装com.flj.latte.net.download.DownloadHandler】  
【调用的方法】在后面的使用文件下载然后更新应用程序；


#### 6.0 拦截器功能设计与实现之拦截器的初始化
【说明】没有搭建服务器，然后使用okhttp库中的拦截功能，将接收到请求之后做出响应，返回json文件；


#### 6.1【配置文件中的拦截器的配置】  

#### 6.2 将配置文件中的interceptors请求配置到okhttp


#### 7.0 拦截器功能设计与实现之模拟请求
【说明】模拟服务器：获取传入的参数，

【get方法】则：从url获取参数；

【post方法】：从请求体中获取参数；  

#### 7.1 基类封装
com.flj.latte.net.interceptors.BaseInterceptor  

#### 7.2 调试类封装
com.flj.latte.net.interceptors.DebugInterceptor  

#### 7.3 使用
com.diabin.fastec.example.ExampleApp  
【效果】就可以不适用服务器的数据，直接可以在本地进行json数据的加载和测试；

#### Retrofit与RxJava的整合，让网络框架支持响应式编程



---
### 启动图功能开发与封装

#### 1.启动图功能开发与封装(倒计时效果)  
【添加依赖】Banner依赖；fastjson库；

#### 2. 持久化   
【持久化】包含三种：sp；file；sqlit；

【sp封装源码】com.flj.latte.util.storage.LattePreference

#### 3.倒计时工具库封装  

#### 4.第一个启动页面的倒计时  

#### 5.启动图功能开发与封装(轮播效果)

* 5.1 轮播图片的添加
* 5.2 指示器的添加  
【设置指示器】新建两个颜色的选中与未选中的指示器；  
【原则】业务中可以使用图片代替代码的就是用图片；框架中可以使用代码就使用代码，不要使用图片；

#### 6.启动图功能优化与完善  
【设计】如果是第一次启动，则需要显示滚动轮播图，如果不是第一次启动，则需要显示单张图片5s；  
【新建枚举类】标记是否为第一次开启登录；  
【检查是否显示启动页】第一次进入APP的时候需要展示滚动画面，非第一次，渐变图进入到主页面；  

---
### 登录、注册功能开发(ORM框架-GreenDao)
#### 1.注册UI及验证逻辑实现
##### 1.1 布局  
【说明】属于业务逻辑，登陆的业务逻辑，新建sign，新建类；   
【注意】如果在ScrollView布局中如果嵌套了其他的布局，则其他的布局的layout_height属性应该为wrap_content;  
##### 1.2 注册信息的验证和逻辑
#### 2.登录UI及验证逻辑实现
##### 2.1 布局
 【源码】layout/delegate_sign_in.xml，支持微信第三方登录
##### 2.2 登录的逻辑框架
#### 3.服务器数据简单介绍
##### 3.1 服务器的数据
【说明】提前准备的json数据
##### 3.2 数据端访问的数据
##### 3.3 打印信息的级别类封装  
【源码】com.flj.latte.util.log.LatteLogger
#### 4.与基于GreenDao的数据库框架设计
【课程链接】[Android框架-GreenDao](https://www.imooc.com/learn/760)  

##### 4.1 添加依赖和配置
##### 4.2 创建entivity  
【说明】【生成代码数据的显示】如果生成的代码以文件的形式显示出来，需要配置相应的代码路径；GreenDao的本意是不允许修改代码，一般不要修改，修改之后会出现莫名其妙的问题；  
【GreenDao生成代码】
【说明】GreenDao生成了一些DaoSession的内容，不要去修改；  

##### 4.3 openHelper类的创建  
【说明】GreenDao在提供了openHelper类，但是在每次的APP打开之后，openHelper会将之前的APP的储存的数据删除掉，现在我们建立现在自己的配置类；

【说明】需要首先生成entivity之后才能书写此类，因为DaoMaster是基于entivity的；
##### 4.4 功能的抽取  
com.flj.latte.ec.database.DatabaseManager  
【单例模式】【惰性加载】使用单例模式，使用Holder惰性加载；
##### 4.5 数据的存储
##### 4.6 添加数据反射显示机制  
【说明】简化使用终端查看的繁琐的步骤，使用facebook的依赖库；原理也是抽象层，reactNative；

【功能】查看数据库；将原生的界面映射到Web界面上；
#### 5.用户状态与用户信息的回调封装  
【说明】我们需要用户状态的回调和用户信息的回调；需要创建一些接口；
##### 5.1 注册的回调实例  
【说明】登录和注册是登录APP的唯一的接口，没有必要分散在别的接口；在入口处理是最好的；
【测试】【逻辑的使用】注册成功，弹出土司；当然在此接口中可以做出一些其他的动作，比如发送统计信息等等；
##### 5.2 登录回调的实例
##### 5.3 登录注册的封装  
【说明】登录和注册属于一体的内容，不应该分开独立，应该整合在一起；
【设置登录监听接口】 com.flj.latte.ec.launcher.LauncherDelegate  

* onAttach方法  
Fragment和Activity建立关联的时候调用（获得activity的传递的值）

【登录成功/失败的信息的保存】
####  6. 框架的总结
【说明】书写最少的代码，完成最多的逻辑；最后在example中书写的代码的数量很少；


### 代码生成器设计与实践（仿ButterKnife注解框架，编译期生成代码）
#### 1.编写自己的元注解和annotationProcessor
##### 1.1 微信登录说明
【说明】微信登录比较坑，在官网文档中说明，必须在app下建立wxapi的目录，然后建立两个activity（微信登录和微信支付）

本节的内容就是要绕过这个限制。使用到的黄油刀的模仿；
##### 1.2 butterKnifer的元注解  
【说明】模仿[butterknife](https://github.com/JakeWharton/butterknife)完成微信的入口的解释器，注解和代码的提取；
##### 1.3 模仿的butterKnifer的元注解  
【主要讲解的内容】基于butterknife的元注解的原理，annimotionprocessor生成我们所需要的代码；进而绕过微信的限制，最大限度的提高代码的封装方式；
#### 2. 通过注解生成指定模板的代码
##### 2.1 通过注解生成指定模板的代码
【扫描每个注解标注的东西】首先生成EntryAnnimitor标注的注解，生成微信的EntryAcitivity；  
解析的代码和扫描的代码分开；代码解析器只能通过循环代码环境当中的内容，一层层的解析，这些处理是在编译期间完成的，不会影响性能；  
【visitor的生成】相当于访问器，相当与属性、注解的类、变量、方法中传入的值然后取出来，
##### 2.2 使用注解类的扫描查找功能
##### 2.3 增加支付和注册的代码的查找
##### 2.4 效果演示
#### 3. 通过代码生成器，生成微信登录代码，绕过微信包名限制
##### 3.1 添加依赖  
【网址】[微信平台-Android接入指南](https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=1417751808&token=&lang=zh_CN）   
【说明】需要企业提前注册好appId和key；
##### 3.2 应用程序的逻辑  
【说明】在点击登录之后会出现微信的登录页面，是微信回调的页面；现在打破了微信的定制（需要在目录下建立一个回调activity）；    
【登录页面回调基类】  
【登录页面-子类继承父类】com.flj.latte.wechat.BaseWXEntryActivity  
【回到专门生成代码的模板类】-对于登录返回界面的处理，市面大多数的应用都是这么处理的；  
【自动生成文件，对入口文件添加配置项】
##### 3.3 使用  
【测试效果演示】【需要在真机演示】生成必要的签名文件；
##### 3.4 调用的流程总结

### 主界面-通用底部导航设计与一键式封装  
#### 1.底部导航BottomBar设计与实现
##### 1.1 说明  
【说明】底部的每个按钮对应的内容页面是fragmemt；
##### 1.2 基于每个tab的子frament的父类的实现  
【思路】底部的按钮需要一个bean或者entivity来存储每个按钮的信息和图标；需要一个基类，实现每个tab的共有的功能；底层的delegate是容器；  
【BottomItemDelegate】//BottomItemDelegate 是每一个页面  
【退出程序的功能】点击两次返回按钮可以退出应用的app；  
 [request请求]fragment在返回的时候需要将fouse再次去request；需要读取fragment的源码  
 
##### 1.3  建立bean类包含tab的信息（icon+文字）  
 【说明】tab信息不使用图片，使用icon+文字；  
 【构造器】ItemBuilder 容器，将BottomItemDelegate 和 BottomItemDelegate（fragment的基类） 构造关联起来；  
  【初始化链表】子类调用方法初始化链表  
  【tab的fragment的布局】 
  【bottembar的添加】
  【关联tab和fragment】
  【重置点击之后的颜色】
#### 2.打造适合电商主界面导航框架



## 附：

[博客园完整课程笔记](https://www.cnblogs.com/Oztaking/tag/_0005_Android%E5%BC%80%E5%8F%91/default.html?page=5)