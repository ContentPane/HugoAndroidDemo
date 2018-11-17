
# 组件化封装 Android App

### 1.整个程序的框架搭建：
HomeActivity是主Activity，因为有三个页面，所以需要三个Fragment，分别是HomeFragment,MessageFragment,MineFragment

### 2.命名规范：
HomeActivity,BaseActivity,HomeFragment,activity_layout_home,fragment_layout_home

### 3.fragment的三种切换方式：
add，replace，remove：replace:保证始终只有一个fragment 
show，hide（最常用）：把旧的fragment隐藏，添加新的fragment，比较消耗内存，但是最常用，不会销毁fragment 
detach，attach：几乎不用，不会销毁fragment，但是会销毁里面的view

### 4.BaseActivity和BaseFragment的使用
1.fragment非常多，有公共的行为，如果每一个fragment都这样写，那么一旦公共行为改变，就要在每一个fragment里面修改，工作量非常大。 
2.有了baseFragment，公共行为可以只在一个文件中修改 
3.BaseFragment为所有fragments提供公共的行为或者事件 
4.BaseActivity为所有Activity提供公共的行为

### 5.Application的使用
1.程序入口 
2.初始化工作 
3.为整个应用的其他模块提供上下文环境 
4.常以单例模式构建对象 
5.需要在AndroidManifest.xml文件中声明使用我们自己定义的application

### Activity四种启动模式

* 【stander模式】

* 【singleTop--重点】在Activity中再次启动该Activity，可以服用该Activity；减少其创建的数量；节省内存；

* 【singleTask--重点】在应用开启之后，只保持一个Activity的实例，Home主页必然是此模式，不会创建多个Home主页；

* 【singleInstance】系统级的应用会用到，全局系统的单例模式；了解即可；






## 附
### [【项目实战】-【组件化封装思想实战Android App】笔记](https://www.cnblogs.com/Oztaking/tag/_0005_Android%E5%BC%80%E5%8F%91/default.html?page=4)