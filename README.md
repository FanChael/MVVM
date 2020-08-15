# MVVM_组件化+LiveData+ViewModel+Repository--Eventbus-Rx2--AndroidX-Jetpack--AS3.6.1
> lib_base - androidx组件依赖、Arouter依赖和注解、等基础配置    
> lib_anotation - 需要防止被混淆的类（比如实体类）都需要添加注解（包括内部类）    
> lib_common - lib_base、lib_anotation、lib_network等公共模块依赖     
> lib_network - R家族网络框架模块、封装基本成型，可以扩展完善     
> lib_miniui - 小型的自定义控件，目前有(截图有体现): Badge角标(支持红点和文本红点)、圆形/带边框圆形/圆角图片、横向重叠头像、走路/圆环进度、上图片下文本组合、左标题右内容文本组合、单选按钮组、开关、带角标的tablayout等      
> lib_refreshlayout - 自定义刷新组件(已经集成到主页, 测试阶段，供参考)    
> lib_banner - 自定义Banner组件(ViewPaper2实现，默认添加了圆点Snake指示器)  
> lib_media - google的exoplayer音乐和视频扩展    
> lib_pop - 分享、支付、任务完成、相册/拍照 之DialogFragment弹窗，其中拍照功弹窗内置功能，可直接回调结果    
> lib_webview - 扩展Webview页面，提供Webview相关扩展设置(后续需要独立进程和完善js交互框架)  
> lib_sqlite - 下一步要完善的，准备加入Google的Room（有些人用的更高效率的LitePal)    
           
> modules - 其他各个模块，扩展新模块可以参考module_login（目前集成了请求跳转） 
>>  module_location - 收货地址管理(高德地图点选)    
>>  module_pay - 支付模块(微信，支付宝，逻辑已经加入)   
>>  module_login - 其中LoginActivity是普通的手机号，验证码登录的界面，需要可以Arouter注解改过去登录就会跳转该页面       
>>  module_productdetail - 商品详情(视频播放展示)  
>>  .....  
   
> thirdmodules - 三方模块  
>> lib-todaystepcounter - 计步器模块（来自开源库)  
>> lib_stateview - 全局转圈，网络加载/空视图 使用（来自开源库，自行扩展和完善了一下)      

#### 升级说明  
> 1.0.0 基础工程支持【混淆、基本结构搭建、基本网络，基本工具类库】  
> 1.1.0 添加沉浸式【非三方库】 - 后续有问题需要完善，这或许就是三方沉浸式的适配的强大吧  
> 1.2.0 支持夜间白天模式切换【目前设置的是页面背景色，如果需要其他的支持，请对应相关控件用到的颜色值到value_night; 同时有drawable的也需要建立对应关系，详情可以看官方资料或者网上资料】  
> 1.2.1 主页(module_main)开关按钮采用new -> Vector Asset创建矢量图svg对应的xml，分别添加到drawable和drawable-night，支持暗黑模式   
> 1.3.0 乞丐版自定义刷新组件模块lib_refreshlayout学习实践，调试完善中...  
> 1.4.0 自定义Banner组件模块lib_banner，Viewpaper2实现，目前增加了Dot_Snake指示器        
> 1.4.1 登录模块module_login加入Navigation，实现登录跳转到注册切换          
> 1.4.2 底部导航采用BottomNavigationView,加入设置网络图标的方法，同时支持再次点击刷新   
> 2019.06.25(端午) 这次将基本的运动+基本电商流程相关页面(列表，购物列表，收货地址地图点选，订单信息，物流信息等)完成后，重新整理发布               

#### 软件架构 - 只考虑高频或原生三方，后续针对一些控件会陆续添加自定义View到UI库
> 【官】DataBinding - 只是控件绑定和事件监听，未涉及到xml里面绑定数据->转kt之前可以先干掉!!!    
> 【官】ViewModel<-for-LiveData - 用作数据动态变化观察  
> 【模】Repository-for->ViewModel<-for-BaseView - 用于从数据库、网络等获取数据；最终状态回调给BaseView(由View继承实现该接口)    
> 【额】Eventbus - 用于跨页面通知刷新数据  
> 【额】R家族 - 负责lib_network模块的封装  
> 【官】Gson - 原生Json处理，内部自定义了convert，方便自定义解析处理  
> 【官】Glide - google内部开源图片加载支持  
> 【自】debug和release均开启了混淆，方便调试混淆问题；app - 负责基本的混淆配置 lib_common - 负责三方依赖的混淆 lib_network - 负责网络三方依赖混淆    
> 【自】NotProguard - lib_anotation->自定义防止混淆类，一般自定义的实体类Bean需要添加该注解，防止被混淆    
> 【额】smart刷新组件     
> 【官】exoplayer - 官方音视频播放组件      
> 【官】easypermissions - 官方权限管理(已经集成到基础页面)    
> 【额】arouter - 阿里路由组件(app下定义了拦截器，针对登录做处理)      

#### 安装教程【参】
> AS3.6.1 + classpath 'com.android.tools.build:gradle:3.6.1'   
> 参考config.gradle配置   

#### 使用说明
> config.gradle - 里面负责切换模块的状态（是application还是library）、App包名版本配置、以及三方依赖的版本配置  
> module_login - 可以作为主要参考，build.gradle配置参考、如何继承带服务的Base页面、如何创建ViewModel以及创建、如何发起请求和处理回调  
> OK 后续还需要完善请求加载转圈、上拉下拉刷新列表展示、以及过程中完善lib_network模块 
> OK 还有一些类似商场首页的页面有待实践...     
> CONTINUE 再仔细琢磨下结构，精简优化相关配置和层级....  

#### 图鉴  or https://gitee.com/heyclock/mvvm_modularization/blob/master/README.md    
<img src="zdoc/pic/1.png" width="210" height="400"/>
<img src="zdoc/pic/2.png" width="210" height="400"/>
<img src="zdoc/pic/3.png" width="210" height="400"/>
<img src="zdoc/pic/4.png" width="210" height="400"/>
<img src="zdoc/pic/5.png" width="210" height="400"/>
<img src="zdoc/pic/6.gif" width="230" height="400"/>
<img src="zdoc/pic/7.png" width="210" height="400"/>
<img src="zdoc/pic/7.gif" width="230" height="400"/>
<img src="zdoc/pic/navigation.gif" width="200" height="400"/>
<img src="zdoc/pic/bottom_navigation_neticon.gif" width="200" height="400"/>  

#### 实战第二版(含更多自定义组件，完善路由，完善加载逻辑等，之后会加强结构优化和模拟数据完善)       
<img src="zdoc/pic/1_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/2_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/3_com.hl.mvvm.png" width="108" height="192"/>
<img src="zdoc/pic/4_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/5_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/6_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/7_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/8_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/9_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/10_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/11_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/12_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/13_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/14_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/15_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/16_com.hl.mvvm.jpg" width="108" height="192"/>
<img src="zdoc/pic/17_com.hl.mvvm.jpg" width="108" height="192"/>  

#### 参与贡献