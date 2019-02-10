# ProcessRoute for Android 跨进程事件路由框架
## 用于解决安卓组件化下各组件间交叉通讯的问题
## 与ARoater不同的是，ProcessRoute是依托AIDL作为通讯纽带，以接口协议为中间件，采取事件机制进行通讯的框架
## 支持跨进程发送消息，且自由回调
## 通讯协议为Interface接口，可指定通讯进程、输入参数（数量最大10个）、回调参数
## 全程对象化传输，无需关注对象的序列化问题（前提：各组件共同依赖于模型包）
## 自动处理跨进程多线程异步问题
## 可跟踪通讯过程中及接口协议关联的独立组件执行过程中所有异常
## 无需配置，只需在各组件依赖的Common包引入即可集成

## 更新日志
### 【1.0.1】2019-02-09
#### 变更协议类，要求必须继承RemoteService，这样便于后期做OOP设计，也更方便做混淆配置

## 使用方式

### 【前提】
#### 你已知道如何进行组件化的基础框架搭建，如不太清楚可参照该入门教程 https://blog.csdn.net/guiying712/article/details/55213884 （请忽略组件通讯相关内容，不值得浪费时间）
#### 您将各组件包括主工程均引用了一个公用库（下面统称：Common）
#### 您将各组件包括主工程的模型均放入了引用的Common包中
#### 您创建了一个组件名为Plug1，并以独立运行APK的方式配置好了
### 【Let`s start】
```Java
        //在Common中新建一个接口协议RemoteServiceOfPlug1(即Plug1对外的通讯协议，协议必须继承RemoteService)，可以对外曝光很多功能，这里已登录为例
        //login：该方法名用于外部组件访问时识别其调用的功能
        //String username / String password： 这是实现登录需要的参数，参数支持[0-10]个，类型不限（基本类型必须换成其包装类）
        //CallbackProcessor<String> callbackProcessor： 这是回调处理器，协议的所有方法都必须将该参数放置最末，是必输参数
        //CallbackProcessor<String> callbackProcessor： <String>用于约束回调的类型，类型不限
        //void：协议无需返回参数，无论返回什么值均为无效，所有回调均使用CallbackProcessor处理
        public interface RemoteServiceOfPlug1 extends RemoteService {
          /**
           * 登录
           * @param username 姓名
           * @param password 密码
           * @param callbackProcessor @String 回调返回token
           */
          void login(String username, String password, CallbackProcessor<String> callbackProcessor);
        }
```
```Java
        //在Plug1中新建一个接口协议RemoteServiceOfPlug1Impl实现RemoteServiceOfPlug1协议
        //在这里你可以尽情的玩耍了
        //callbackProcessor.callback(): 改方法用于回调，如果你需要在别的地方回调可将callbackProcessor传递过去而无需关心序列化问题
        //"Hello!": 该参数与<String>依赖，您必须使用相同的类型进行回调
        public class RemoteServiceOfPlug1Impl implements RemoteServiceOfPlug1 {
          @Override
          public void login(String username, String password, CallbackProcessor<String> callbackProcessor) {
              System.out.print("username=>"+ username);
              System.out.print("password=>"+ password);
              callbackProcessor.callback("Hello!");
          }
        }
```
```Java
        //我们需要对RemoteServiceOfPlug1设置依赖关系
        //ProcessId: 用于指定该协议从属于哪个组件，注意是组件的包名，如果是在整个工程全量打包开发的情况下需替换为主工程的包名，为了方便您可通过gradle自动配置
        //RemoteServiceImpl: 用于指定该协议的实现类，需输入实现类的全路径
        //ProcessId, RemoteServiceImpl：缺一不可。当然你也不用太担心，如果协议上有任何的地方有问题，框架都会在调用方回调错误信息以提示您
        @ProcessId("com.demo.plug1")
        @RemoteServiceImpl("com.demo.plug1.PlugRemoteService")
        public interface RemoteServiceOfPlug1 extends RemoteService{
          /**
           * 登录
           * @param username 姓名
           * @param password 密码
           * @param callbackProcessor @String 回调返回token
           */
          void login(String username, String password, CallbackProcessor<String> callbackProcessor);
        }
```
```Java
        //假设您在某个组件中需要Plug1模块的登录功能,使用ProcessRoute发送一个进程事件消息即可
        //context：上下文
        //RemoteServiceOfPlug1.class: 指向的通讯协议
        //"login"：调用的功能
        //params: 与通讯协议的login功能中要求的参数一样，但必须确保参数数量、类型、顺序一致
        //RouteListener<String>(): <String>与通讯协议的login功能中要求的回调参数一样
        //prepare(): 事件已发出但尚且没有回调
        //callback(): 事件回调了，且成功带回需要的数据。注意：只有Plug1模块调用了callbackProcessor.callback()才会回调
        //fail(): 事件回调了，但失败了。如Plug1未安装、连接失败、未遵守协议、Plug1的实现过程报错都会在这个方法中将详细错误信息带过来
        ProcessRoute.send(context,
                        RouteReq.build(RemoteServiceOfPlug1.class,"login")
                                .params("18800000000", "198123545masd")
                                .routeListener(new RouteListener<String>() {
                                    @Override
                                    public void prepare() {}

                                    @Override
                                    public void callback(String obj) {}

                                    @Override
                                    public void fail(String errorMsg) {}
                                }));
```
### 【end】

## 混淆方式
```Xml
-keepclassmembers class **{
    @com.fanjun.processroute.remote.ProcessId <fields>;
    @com.fanjun.processroute.remote.RemoteServiceImpl <fields>;
}
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.gson.* { *;}
-dontwarn com.google.gson.**
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class * extends com.fanjun.processroute.remote.RemoteService
```

## 依赖
### Maven
```Xml
<dependency>
  <groupId>com.fanjun</groupId>
  <artifactId>processroute</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```
### Gradle
```Xml
 implementation 'com.fanjun:processroute:1.0.1'
```

## 联系我
```Xml
我的博客：https://blog.csdn.net/qwe112113215
```
```Xml
我的邮箱：810343451@qq.com
```
