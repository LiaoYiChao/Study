### JFinal基础

​	前言：

~~~html
本人学习视频教程url地址：
https://www.bilibili.com/video/av40732021?from=search&seid=11986286675183721441

本人学习FreeMarker模板视频：
https://www.bilibili.com/video/av25717932/?p=5

JFinal官网文档：
https://www.jfinal.com/doc
~~~



#### 1）Pom文件

~~~java
// Pom文件

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>top.liaoyichao</groupId>
  <artifactId>myjfinal</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>war</packaging>
   
   <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.encoding>UTF-8</maven.compiler.encoding>
    <jdk.version>1.8</jdk.version>
    <junit.version>3.8.1</junit.version>
    <jfinal.version>3.6</jfinal.version>
    <jfinalundertow.version>1.5</jfinalundertow.version>
</properties>
  
  <!-- 使用阿里 maven 库 -->
	<repositories>
		<repository>
			<id>ali-maven</id>
			<url>http://maven.aliyun.com/nexus/content/groups/public</url>
			<releases>
				<enabled>true</enabled>
			</releases>
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
				<checksumPolicy>fail</checksumPolicy>
			</snapshots>
		</repository>
	</repositories>
	
    <dependencies>
       <dependency>
	      <groupId>junit</groupId>
	      <artifactId>junit</artifactId>
	      <version>${junit.version}</version>
	      <scope>test</scope>
	    </dependency>
	     <dependency>
	    	<groupId>com.jfinal</groupId>
	    	<artifactId>cos</artifactId>
	    	<version>${cos.version}</version>
	    </dependency>
			<groupId>com.jfinal</groupId>
			<artifactId>jfinal-undertow</artifactId>
			<version>${jfinalundertow.version}</version>
		</dependency>
	 <dependency>
	    <groupId>com.jfinal</groupId>
	    <artifactId>jfinal</artifactId>
	    <version>${jfinal.version}</version>
	  </dependency>
  </dependencies>
</project>
~~~



#### 2）JFinal常用简介

​		JFinalConfig全局配置类

~~~java
package top.liaoyichao.common.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.template.Engine;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.plugin.druid.DruidPlugin;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.render.ViewType;
import top.liaoyichao.index.IndexController;
import top.liaoyichao.index.LoginController;

public class MainConfig extends JFinalConfig {
	/**
	 * 将全局配置提出来 方便其他地方重用
	 */
	private static Prop p;
	private WallFilter wallFilter;

	/**
	 * 配置JFinal常量
	 */
	@Override
	public void configConstant(Constants me) {
		// 读取数据库配置文件
		loadConfig();
		// 设置当前是否为开发模式
		me.setDevMode(p.getBoolean("devMode"));
		// 设置默认上传文件保存路径 getFile等使用
		me.setBaseUploadPath("upload");
		// 设置上传最大限制尺寸
		// me.setMaxPostSize(1024*1024*10);
		// 设置默认下载文件路径 renderFile使用
		me.setBaseDownloadPath("download");
		// 设置默认视图类型
		me.setViewType(ViewType.FREE_MARKER);

		//设置错误码 和 发生错误码时转到的页面
		// me.setErrorView(errorCode, errorView);

		// 设置启用依赖注入
		me.setInjectDependency(true);

		//设置JFinal编码格式
		// me.setEncoding("UTF-8"); //设置JFinal编码格式

		//true ： 先执行方法 后打印日志  false ：先执行日志 后执行方法
		// me.setReportAfterInvocation(true);
		
		//设置是否可以多次提交 设置该属性 不能进行多次提交 比如：转账的时候 如果发生延迟 可能会转账两个 如果只能提交一次 这种情况就能避免
		// me.setTokenCache(null);
		
		/**
		 * 设置分隔符 可以为 _ — @等等 主要作用 配合方法 getPara(下标) 比如url地址下有个地址 为
		 * www.liaoyichao.top/nihao@nihaoya 这里用个映射器为 /nihao 在该方法里面调用 getPara(0)
		 * 可以得到nihaoya 这个字符串 注意事项； 只能识别最后一个/后面的内容 且第一个要跟访问的路径
		 */
//		 me.setUrlParaSeparator("");

	}

	/**
	 * 配置项目路由 路由拆分到 FrontRutes 与 AdminRoutes 之中配置的好处： 1：可分别配置不同的 baseViewPath 与
	 * Interceptor 2：避免多人协同开发时，频繁修改此文件带来的版本冲突 3：避免本文件中内容过多，拆分后可读性增强 4：便于分模块管理路由
	 */
	@Override
	public void configRoute(Routes me) {
		// 推荐拆分方式 如果需要就解开注释 创建对应的 Routes
		// me.add(new AdminRoutes());//配置后台管理系统路由

		// me.add(new WechatRoutes());//配置微信端访问路由

		// 普通不拆分的方式配置 如下
		// 设置默认访问首页路由 可使用http://localhost:port 直接访问 如果80端口 port可以省略
		me.add("/", IndexController.class);
		me.add("/login", LoginController.class , "/static");
	}

	// 先加载开发环境配置，再追加生产环境的少量配置覆盖掉开发环境配置
	static void loadConfig() {
		if (p == null) {
			p = PropKit.use("config-dev.properties").appendIfExists("config-pro.properties");
		}
	}

	/**
	 * 获取数据库插件 抽取成独立的方法，便于重用该方法，减少代码冗余
	 */
	public static DruidPlugin getDruidPlugin() {
		loadConfig();
		return new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password"));
	}

	/**
	 * 配置JFinal插件 数据库连接池 ActiveRecordPlugin 缓存 定时任务 自定义插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		loadConfig();
		// 配置数据库连接池插件
		DruidPlugin dbPlugin = getDruidPlugin();
		wallFilter = new WallFilter(); // 加强数据库安全
		wallFilter.setDbType("mysql");
		dbPlugin.addFilter(wallFilter);
		dbPlugin.addFilter(new StatFilter()); // 添加 StatFilter 才会有统计数据

		// 数据映射 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dbPlugin);
		arp.setShowSql(p.getBoolean("devMode"));
		arp.setDialect(new MysqlDialect());
		dbPlugin.setDriverClass("com.mysql.jdbc.Driver");
		/******** 在此添加数据库 表-Model 映射 *********/
		// 如果使用了JFinal Model 生成器 生成了BaseModel 把下面注释解开即可
		// _MappingKit.mapping(arp);

		// 添加到插件列表中
		me.add(dbPlugin);
		me.add(arp);

	}

	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new SessionInViewInterceptor());
	}

	/**
	 * 配置全局处理器
	 */
	@Override
	public void configHandler(Handlers me) {
		// 说明：druid的统计页面涉及安全性 需要自行处理根据登录权限判断是否能访问统计页面
		// me.add(DruidKit.getDruidStatViewHandler()); // druid 统计页面功能
	}

	/**
	 * 项目启动后调用
	 */
	@Override
	public void onStart() {
		// 让 druid 允许在 sql 中使用 union
		// https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter
		wallFilter.getConfig().setSelectUnionCheck(false);
	}

	/**
	 * 配置模板引擎
	 */
	@Override
	public void configEngine(Engine me) {
		// 配置模板支持热加载
		me.setDevMode(p.getBoolean("engineDevMode", false));
		// 这里只有选择JFinal TPL的时候才用
		// 配置共享函数模板
		// me.addSharedFunction("/view/common/layout.html")
	}

	public static void main(String[] args) {
		UndertowServer.create(MainConfig.class, "undertow.properties").start();
	}

}
~~~



##### 	2.1) getPara方法的使用	

![getPara使用方法](https://www.jfinal.com/upload/img/document/0/1_20180112235732.png)

说明：getpara(): 

​		传入字符串 代表获取页面中的 name 中的值 

​		传入字符串：参数一：页面中的name值(需要保持一致名称) 参数二：选填 设置改name的默认值	

​		传入数值：获取url 中最后一个  /  后的值 如果在全局配置中的进行了设置 如下：

~~~java
public void configConstant(Constants me) {
	me.setUrlParaSeparator(""); //设置分隔符 默认分隔符为 - 设置了分隔符 参数与参数之间用此隔开 即可取到对应的值 比如 url中有 /test@nihao@Hello 代码中使用 getpara(0) 取到nihao
    getpara(1) 取到Hello 
}
~~~



##### 	2.2) getBean(  ,  )

​		常用的两个参数：

​			参数一：一个javaBean的类 (类中的变量名需和页面中的name一致)

​				注意事项：想要映射到javaBean中 需要在页面的name中 类名.变量名的方式

~~~html
<form class="form-signin" action="/">
	<img class="mb-4" src="asserts/img/bootstrap-solid.svg" alt="" width="72" height="72">
	<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
	<label class="sr-only">Username</label>
    
    <!-- 如下name所示 在javaBean中 类名为 PersonOne(大小写需要一致).变量名的方式进行映射 -->
    
	<input type="text" name="PersonOne.username" class="form-control" placeholder="Username" required="" autofocus="">
	<label class="sr-only">Password</label>
	<input type="text" name="PersonOne.password" class="form-control" placeholder="Password" required="">
	<div class="checkbox mb-3">
		<label>
          	<input type="checkbox" value="remember-me"> Remember me
        </label>
	</div>
	<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	<p class="mt-5 mb-3 text-muted">© 2017-2018</p>
	<a class="btn btn-sm">中文</a>
	<a class="btn btn-sm">English</a>
</form>
~~~



##### 	2.3)  getMould



##### 2.4) getFile

​		准备操作：在页面中需要加上

~~~html
<from action="/" method="post" enctype="multipart/form-data">
    <label for="exampleInputFile">上传文件</label>
	<input type="file" name="file" >
</from>
~~~

​	getFile()	---调用该方法 必须是 multipart/form-data 类型

​	注意事项：

​		**1) **文件上传 默认只有1m上传大小,在全局配置类中 JFinalConfig 中的抽象方法configConstant中

​			1.1)设置文件上传大小： me.setMaxPostSize(1024 * 1024 * 10);

​			1.2)设置文件上传位置： me.setBaseUploadPath("upload");	

​			1.3)设置文件下载位置： me.setBaseDownloadPath("download");

​		**2) **当from表单中需要进行多个提交操作 不仅仅有文件的时候 需要把getFile 放置在getPara之上 不然报错

​			因为当其他from提交的时候，先调用getFile解析MultipartRequest，才能调用getPara接受参数

​	

​	**获取文件中的信息** uploadFile file = getFile("");  ---getFile中填写 页面中 name 的值



##### 2.5) setCookie()

​	其中主要三个参数：

​		参数一：Cookie 的key

​		参数二：Cookie 的value

​		参数三：Cookie 存在的时间

​		参数四：是否隐藏Cookie 信息 true 隐藏不会被外界js代码获取 但在浏览器中还是能被查看

​			删除Cookie ：removeCookie("Cookie的key");

​			获取Cookie ：getCookie("Cookie的key");



​			Cookie安全问题：明文显示 易修改 易被抓包

​				易修改使用类似下述方法进行 可以使用uuid 得到一个连自己都不知道的随机数 在使用md5加密

~~~java
package top.liaoyichao.index;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;

import top.liaoyichao.entity.PersonOne;
/**
 * 
 * @author Liao
 * @date 2019年2月22日
 * @time 下午2:01:36
 * @Description: 用户登陆Controller
 */
public class LoginController extends Controller {
	
	private static final String MD5_LIAO = "LIAONIHAO";
	
	public void index(){
		render("login.html");
	}
	
	public void login() {
		String user = getPara("name");
		String password = getPara("password");
		String pwd = users.get(user);
		
		if(pwd == null){
			redirect("/login");
			return;
		}
		
		if(pwd.equals(password)){
			
			//进行自我加密 后面的MD5_LIAO 除非查看了源码 不然不会知道
			String cookie = user + "#" +HashKit.md5(user + MD5_LIAO);
			
			//但是Cookie又安全问题，因为是明文显示，这个时候可以使用另外的方法进行加密。
			setCookie("user", cookie, 1000);
			redirect("/login/centen");
			return;
		}else{
			redirect("/login");
			return;
		}
		
	}
	
	public void centen(){
		String cookie = getCookie("user");
		
		String user = cookie.split("#")[0];
		String md5 = cookie.split("#")[1];
		
		if(HashKit.md5(user + MD5_LIAO).equals(md5)){
			System.out.println("合法");
		}else{
			System.out.println("非法");
		}
		
		if(user == null){
			redirect("/login");
			return;
		}
		
		render("LoginYes.html");
	}
	
	static Map<String , String> users = new HashMap<String , String>();
	
	static{
		users.put("nihao", "jfinal");
	}
}
~~~

​			易被抓包 能使用httpOnly防XSS获取  或者在 setCookie中加上第四个参数 true



##### 2.6) setSessionAttr()

​	主要参数：

​		参数一：Session的key

​		参数二：Session的value

​			获取Session ： getSessionAttr(Session的key);

​			删除Seesion：removeSession(Session的key);

2.7) redirect("") 和 render("");

​	该两个方法 需要在继承Controller类中使用 主要用于返回视图

​		redirect : 返回的是url地址 

​		render：返回页面



##### 2.7) setAttr

​	里面设置的值 在界面中 可以使用 $(设置的名称拿到值)

​	里面为key value 结构如果放入的为list集合数据 在页面中 如果使用为freemarker模板  可以通过 #list 名称 as 名称的 方式取出里面的 数据



##### 2.8) 批量接受 批量设置 keepPara keepModel keepBean

​	主要用于批量接受 如果使用getPara 和 setAttr 一个一个接受和传出 代码过于复杂

​		所以使用批量接受更强 

##### 2.9) JFinal验证码

​		JFinal 中定义验证码图片的类 只需调用即可

​			renderCaptcha();	//验证码图片调用 

​			实现页面点击图片切换

~~~html
    <div class="form-group" id="yzm">
        <label>请输入验证码</label>
        <input type="text" name="yzm" class="form-control"  placeholder="验证码"/>
        <!-- src 填写验证码对应的url地址 后面的点击事件中传递一个随机数即可切换图片 -->
        <img src="/login/yzm" onclick="this.src='/login/yzm?x='+Math.random();">
    </div>
~~~

​			获取验证码中的值 JFinal封装好了 validateCaptcha("填写页面中验证码input的name值 如上为yzm")



##### 3.0) Handle

​	自行定义的Handle 继承 JFinal 的Handle 即可

​	Handle 自己设置的Handle 需要在 JFinalConfig 全局配置中的 configHandler 中进行配置 多个Handle会从上至下执行

​		主要作用 用于处理在页面渲染的前的工作

​		3.0.1）**JFinal 执行流程** 

​			请求进入 --》 JFinalFilter --》 Handle(自行定义的Handle)  --》 Handle n --》 ActionHandle(JFinal定于的Handle)(最终由该Handel 进行页面分配 渲染等)

​		注意事项：当有多个自定义的Handle时 需要在Handle最后加上 next.handle();  交予下一个Handle进行处理 在最后一个自定义的Handle后添加 最后会交予JFinal的Handle ActionHandle进行处理

​		3.0.2）**伪静态Handle**

​			作用：在url中不会显示 后面的 .html 类似这种直白的静态文件访问

​				在JFinalConfig 中的 configHandler  添加 me.add(new FakeStaticHandle);

​					这个new的类是 JFinal 自带的伪静态Handle 可以传一个参数 默认为 .html			

​		3.0.3）**拦截请求**

​			有一些文件需要登陆之后 才能进行下载 这个拦截可以在Handle中进行判断 因为用户登陆成功会保存一个 Cookie 或者一个 Session 这个时候 在Handle中 如果这两个为空 则跳转到登陆界面 并提示请登录 如果有 则进行权限查看 看其权限是否可以进行下载

​		**注意事项：**

​			Handle 编写中 其中重写的方法形参中 isHandle 这个boolean数组设置为了true 代表不会去下一个Handle 这个Handel为最终Handle

##### 3.1) JFinal拦截器设置

​	需要实现 Interceptor类 内部会重写intercept方法 形参为 Invocation

​		主要编写 Invocation 进行拦截

​			比如：Invocation.getActionKey()  ---获取到Action的key 如果该Key中的某写值满足要求 让其进行其他操作 比如 外部传入一个 /user 的请求 被该拦截器拦截 在拦截器中进行判断 如果为 /user 的请求 让其重定向 或者跳转至 那个 都是可以的 重定向 为 Invocation.getController.redirect("重定向的url地址");



​	**JFinal自带的拦截器：**

​		CacheInterceptor ：缓存拦截器

​			主要作用：使用缓存来缓存视图

​		SessionInViewInterceptor：Session拦截器

​			主要作用：拦截Session 在页面中不会存在Session

​		GET.class , POST.class：请求拦截

​			主要作用：标记了该class的类 只能使用该类型的请求进行请求



​	注意事项：

​		a. 每个拦截器后面需要添加 inv.invoke(); ---代表该拦截器结束 进入到下一个拦截器 直到JFinal自身的拦截器为止 和Handle的 nexthandle的性质一样

~~~java
package top.liaoyichao.common.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;

/**
 * @author Liao
 * @date 2019年2月23日
 * @Description:
 */
public class MyHandle extends Handler{

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		
		//  /download  在JFinal全局配置中 进行了设置 该位置为文件下载路径
		//判断 如果请求的url中出现了 该字符串
		if(target.startsWith("/download")){
			
			String user = (String) request.getSession().getAttribute("用户登陆成功之后的Session");
			
			if(user == null){
				//如果 没有关于用户登陆后的Session 则转发至登陆界面的url
				HandlerKit.redirect("跳转至登陆的url", request, response, isHandled);
				return;
			}
		}
	}

}

~~~

##### 3.2) JFinal缓存设置 

​	以 EhCache 为例

​		第一步：pom文件

~~~xml
		<dependency>
			<groupId>net.sf.ehcache</groupId>
			<artifactId>ehcache-core</artifactId>
			<version>2.6.11</version>
		</dependency>
~~~

​		第二步：在src 下添加ehcache.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="ehcache.xsd"
         updateCheck="false" monitoring="autodetect"
         dynamicConfig="true">
         
    <diskStore path="java.io.tmpdir"/>
 
    <cache name="action"
           maxEntriesLocalHeap="10000"
           maxEntriesLocalDisk="1000"
           eternal="false"
           overflowToDisk="true"
           diskSpoolBufferSizeMB="20"
           timeToIdleSeconds="300"
           timeToLiveSeconds="100"
           memoryStoreEvictionPolicy="LFU"
           transactionalMode="off"
            />
              
</ehcache>
~~~

​		第三步：在JFinal的全局配置JFinConfig中的configPlugin下添加**me.add(new EhCachePlugin());**

​		第四步：在需要缓存的方法上添加

​			@Before(CacheInterceptor.class)
​			@CacheName("定义的一个缓存名称")	

​	注意事项：@Before中为JFinal自带的缓存拦截器 也是使用自己我缓存拦截器

​			@CacheName 为ehcache.xml 中设置的name 

​			如果不设置 使用默认的缓存配置

##### 3.3) JFinal 对数据库的增删该查

​	JFinal 对数据的增删改查流程

###### ​		3.3.1）在JFinal中进行数据源配置

​			不使用配置文件方式配置：

~~~java
	public void configPlugin(Plugins me) {
		// 配置数据库连接池插件
        //这里使用阿里云连接池连接 需要传入url user password
		DruidPlugin dbPlugin = getDruidPlugin("url","user","password");
		wallFilter = new WallFilter(); // 加强数据库安全
		wallFilter.setDbType("mysql");
		dbPlugin.addFilter(wallFilter);
		dbPlugin.addFilter(new StatFilter()); // 添加 StatFilter 才会有统计数据

		// 数据映射 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dbPlugin);
        //是否打印sql语句
		arp.setShowSql(true);
		arp.setDialect(new MysqlDialect());
		dbPlugin.setDriverClass("com.mysql.jdbc.Driver");
        
		/******** 在此添加数据库 表-Model 映射 *********/
        //每一个Modle 都需要在此进行配置 第一个参数为数据库中的表名 第二参数为Modle类名
		arp.addMapping("t_liao", Person.class);
		// 添加到插件列表中
		me.add(dbPlugin);
		me.add(arp);
		
		//把缓存插件 EhCachePlugin 加入到插件中
		me.add(new EhCachePlugin());
	}
~~~



###### ​		3.3.2）配置完成之后有几种使用方法

~~~java
		第一种：Modle modle = new Modle
		
			//增加 key为数据中的字段名称 value需要与之类型对应
			modle.set("主键字段", value);
			modle.save;	//保存进数据库

			//删除
			modle.deleteById("主键字段");
			
			//修改 需要传入一个id值 或者说需要传入主键
			modle.set("主键字段", value);
			modle.set("需要修改的字段", value);
			modle.update();

			//单个查询 
			modle..findById(传入id值);
			
			注意事项：
				查询如果有多个 使用new 对象会出错 解决方案 单独创建一个 里面主要用于查询
					比如：创建ModleService 
public class PersonOneFindService {
	private static final Person P = new Person();
	/**
	 * 自定义查询 如果需要查询其他 直接在形参中添加
	 * @param DbName 数据库表名
	 * @param variable 需要查询的值
	 * @param FieldName 需要查询的字段
	 * @param IfOpenDimInquire 是否开启模糊查询
	 * @return
	 */
	public static List<Person> find(String DbName, Object Variable,String FieldName,boolean IfOpenDimInquire){
		//如果IfOpenDimInquire为true 则启动模糊查询
		if(IfOpenDimInquire == true){
			StringBuffer sql = new StringBuffer("select * from " + DbName);
			List<Object> parans = new ArrayList<Object>();
			boolean addWhere = false;
			addWhere = AppendDbLike(Variable, FieldName, sql, parans, addWhere);
			System.out.println("variable  =  "+Variable);
			return P.find(sql.toString() , parans.toArray());
		}
		//IfOpenDimInquire默认为false
		IfOpenDimInquire = false;
		StringBuffer sql = new StringBuffer("select * from " + DbName);
		List<Object> parans = new ArrayList<Object>();
		boolean addWhere = false;
		System.out.println("variable  =  "+Variable);
		addWhere = AppendDbOne(Variable, FieldName, sql, parans, addWhere);
		return P.find(sql.toString() , parans.toArray());
	}
	
	/**
	 * 单个值查询
	 * @param variable 查询的值
	 * @param pojo 查询的条件
	 * @param sql SQL语句
	 * @param arr 把需要的查询条件放入
	 * @param addWhere 是否在后面添加的Where
	 * @return
	 */
	public static boolean AppendDbOne(Object variable, String pojo, StringBuffer sql, List<Object> arr, boolean addWhere) {
		if(variable != null){
			if(addWhere == false){
				sql.append(" where ");
				addWhere = true;
			}else{
				sql.append(" and ");
			}

			sql.append(pojo + " = ?");
			arr.add(variable);
		}
		return addWhere;
	}	

	/**
	 * 单个值模糊查询
	 * @param variable 查询的值
	 * @param pojo 查询的条件
	 * @param sql SQL语句
	 * @param arr 把需要的查询条件放入
	 * @param addWhere 是否在后面添加的Where
	 * @return
	 */
	public static boolean AppendDbLike(Object variable, String pojo, StringBuffer sql, List<Object> arr, boolean addWhere) {
		if(variable != null){
			if(addWhere == false){
				sql.append(" where ");
				addWhere = true;
			}else{
				sql.append(" and ");
			}

			sql.append(pojo + " like ? ");
			arr.add("%" + variable + "%");
		}
		return addWhere;
	}
}

~~~

###### 		3.3.3）Page分页查询

~~~java
	public static Page<Person> paginate(int pageNumber , int pageSize , String DbName){
		String select = "select *";
		String sqlExceptSelect = "from " + DbName;
		
//		return P.paginate(pageNumber, pageSize, select, sqlExceptSelect);
		
		//缓存分页 设置参考 3.2）JFinal缓存设置
		//使用缓存进行增删改的时候 需要先清空缓存 或者先判断 数据是否进行过修改 修改后需要重新读取并写入缓存
		return P.paginateByCache("test", "pageinate"+pageNumber, pageNumber, pageSize, select, sqlExceptSelect);
	}
~~~

###### 		3.3.4）批量操作

​			批量保存主要调用了 **Db**这个方法 该方法中有很多其他方法 比如批量保存 批量更改等

~~~java
		//批量操作
		List<Modle> personsList = new ArrayList<>();
		
		for(int i = 0; i <= 20; i++){
			Modle Modle = new Modle();
            //需要添加的数据
			person.set("user", "nihao" + i);
			person.set("password", "888");
			System.out.println(i);
			System.out.println(person);
			personsList.add(person);
		}
		
		//批量保存
		Db.batchSave(personsList, personsList.size());
~~~

###### 		3.3.5）复合组建的使用

​			用法只是在 JFnialConfig在配置一下 比如

​				arp.addMapping("t_liao", Person.class); 这是普通的

​				arp.addMapping("t_liao",“主键1，主键2”, Person.class); 这是复合主键的使用 

​					其余一样 只是传值需要传2个



##### 3.4）JFinal 数据校验 validator

[Validator参考类](https://github.com/iveryang/jfinal-bbs/blob/master/src/cn/iver/validator/UpdateUserValidator.java)

~~~java
public class PersonValidator extends Validator{
    
	@Override
	protected void validate(Controller c) {
		/**
		 * String field : 需要进行校验的值 类似 getPara("该值")
		 * int minLen : 该字符串小长度
		 * int maxLen : 该字符串最大长度
		 * String errorKey : 发生错误的key 类似 setAttr("key");
		 * String errorMessage : 发生错误的value 类似 setAttr("","value");
		 * 	当发生错误的时候 会执行 handleError 下面的类 会跳过Controlle 执行下方类
		 */
		validateString("updateName", 1, 200, "msgName", "名称需要1~20之间哦");
		validateString("updateAge", 1, 3, "msgAge", "超越三位小于一位数的神啊 保佑我");
		validateString("updateAddress", 1, 200, "msgAddress", "地址需要1~50之间哦");
        
        //非空字符校验
        validateRequiredString(field, errorKey, errorMessage);
        
	}

	@Override
	protected void handleError(Controller c) {
        c.keepModel("");	//保存该数据在页面
		c.redirect("/user/PersonErrorUpdate");
	}
}
~~~

需要继承Validator 实现其中的两个方法 第一个方法定义错误 第二个方法定义发生错误之后的动作

​	使用在 需要改数据校验的类上 加上 @Before(该类.class) 即可

#### 3）注解

~~~java
@Clear(一个类.class)  //去除该类执行 在拦截器中放入或者在Handle中放入 在初始化时放入最佳
@Before(一个类.class) //在该类执行前 执行这个类 先执行完Before中的类 才会执行之后的类

上两个配合在JFinal拦截器中使用较好
	比如有个拦截器 但是不想在初始化执行 加上@Clear即可 在需要该拦截器的类中加上@Before即可
	
@CacheName("字符串")
    设置一个缓存名字 该缓存名字需要在配置文件中存在 开启缓存后 执行该类 第二次执行 会去带这个名称的缓存中寻找 	配合 @Before(CacheInterceptor.class)
    该类时JFinal 自带的缓存拦截器类
~~~



#### 4）全局配置文件基础详解

##### 3.1) undertow.properties

~~~prop
# ---开发环境配置文件---

# 配置undertow使用说明：
#
# 1：系统默认在 class path 根目录下先加载 undertow-dev.properties 再加载 undertow-pro.properties
#    进行配置，当上述两个配置文件不存在时不抛异常并使用默认值配置
#
# 2：所有配置可以省略，省略时使用默认配置
#
# 3：开发阶段 undertow.devMode 配置为 true 才支持热加载
#
# 4：该文件列出了绝大多数可配置项，更多不常用配置可以查看 UndertowConfig 源码中的配置常量定义
#
# 5：当配置项不足以满足需求时，可以通过如下方式添加额外配置：
#     UndertowServer
#        .create(AppConfig.class)
#        .config( config -> {
#            config.setHost("0.0.0.0");
#            config.setSessionTimeout(30 * 60);
#         })
#        .start();
#
#   类似上面的配置方式还有带 Builder 参数的 onStart(...) 可以对 undertow 进行配置
#

# true 值支持热加载，生产环境建议配置成 false
undertow.devMode=true

# 避免项目中的 .class 打成 jar 包以后，同时在使用 devMode 时报的异常
# 只要 undertow.devMode 设置为 false，或者不打包就不会有异常
# 添加此配置以后则无需关心上面这类事情，多个前缀用逗号分隔开
# undertow.hotSwapClassPrefix=com.jfinal.club.


undertow.host=localhost
undertow.port=8081
undertow.contextPath=/

# js、css 等等 web 资源存放的目录
# undertow.resourcePath=webapp, src/main/webapp
undertow.resourcePath = src/main/webapp
# io 线程数与 worker 线程数
# undertow.ioThreads=
# undertow.workerThreads=

# session 过期时间，注意单位是秒
# undertow.session.timeout=1800
# 热加载保持 session 值，避免依赖于 session 的登录型项目反复登录，默认值为 true。仅用于 devMode，生产环境无影响
# undertow.session.hotSwap=true


# 开启 gzip 压缩
undertow.gzip.enable=false
# 配置压缩级别，默认值 -1。 可配置 1 到 9。 1 拥有最快压缩速度，9 拥有最高压缩率
undertow.gzip.level=-1
# 触发压缩的最小内容长度
undertow.gzip.minLength=1024




# SSL 配置 ----------------------------------------------------------------------------------
# 生产环境从阿里云下载 tomcat 类型的密钥库。以下两行命令生成密钥库仅用于测试：
# 下面两行命令生成密钥库
# keytool -genkeypair -validity 3650 -alias jbolt -keyalg RSA -keystore jbolt.jks
# keytool -importkeystore -srckeystore jbolt.jks -destkeystore jbolt.pfx -deststoretype PKCS12
# 生成过程中提示输入 "名字与姓氏" 时输入 localhost

# 是否开启 ssl
undertow.ssl.enable=false
# ssl 监听端口号，部署环境设置为 443
#undertow.ssl.port=443
# 密钥库类型，建议使用 PKCS12
#undertow.ssl.keyStoreType=PKCS12
# 密钥库文件 请在配置文件所在目录下放置 jbolt.pfx
#undertow.ssl.keyStore=jbolt.pfx
# 密钥库密码 请按照实际情况填写
#undertow.ssl.keyStorePassword=your_keyStorePassword

# 别名配置，一般不使用
# undertow.ssl.keyAlias=club

# ssl 开启时，是否开启 http2。检测该配置是否生效在 chrome 地址栏中输入: chrome://net-internals/#http2
#undertow.http2.enable=false


# ssl 开启时，http 请求是否重定向到 https
#undertow.http.toHttps=false
# ssl 开启时，http 请求重定向到 https 使用的状态码 默认就是302
# undertow.http.toHttpsStatusCode=302
# ssl 开启时，是否关闭 http
#undertow.http.disable=false

~~~



##### 3.2) config-dev.properties	---开发文件配置

~~~prop
# ---开发环境配置文件---

#database config
dbType=mysql
jdbcUrl = jdbc:mysql://127.0.0.1:3306/myjfinaldb?characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull
user = root
password =root

devMode =true
engineDevMode=false
~~~



##### 3.3) config-pro.properties	---生产文件配置

~~~prop
# ---生产环境配置文件---

#jdbcUrl = 填写生产环境地址
#user = 填写生产环境数据库用户名
#password =填写生产环境数据库密码

#devMode =false
#engineDevMode=false
~~~



##### 3.4) log4j.properties

~~~prop
log4j.rootLogger=WARN, stdout, file
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%n%-d{yyyy-MM-dd HH:mm:ss}%n[%p]-[Thread: %t]-[%C.%M()]: %m%n

 # Output to the File 
log4j.appender.file=org.apache.log4j.FileAppender
log4j.appender.file.File=./myjfinal.log
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%n%-d{yyyy-MM-dd HH:mm:ss}%n[%p]-[Thread: %t]-[%C.%M()]: %m%n
~~~



##### 3.5) logging.properties

~~~prop

# 下载地址：
# https://github.com/undertow-io/undertow/blob/master/examples/src/main/resources/logging.properties

#
# JBoss, Home of Professional Open Source.
# Copyright 2012 Red Hat, Inc., and individual contributors
# as indicated by the @author tags.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Additional logger names to configure (root logger is always configured)
loggers=org.xnio.listener,org.xnio.ssl,org.apache,io.undertow.util.TestHttpClient

# Root logger configuration
logger.level=${test.level:ERROR}
logger.handlers=CONSOLE

# Console handler configuration
handler.CONSOLE=org.jboss.logmanager.handlers.ConsoleHandler
handler.CONSOLE.properties=autoFlush,target
handler.CONSOLE.target=SYSTEM_ERR
handler.CONSOLE.level=ALL
handler.CONSOLE.autoFlush=true
handler.CONSOLE.formatter=PATTERN

# The log format pattern
formatter.PATTERN=org.jboss.logmanager.formatters.PatternFormatter
formatter.PATTERN.properties=pattern
formatter.PATTERN.pattern=%d{HH:mm:ss,SSS} %-5p (%t) [%c] <%F:%L> %m%n

logger.org.xnio.listener.level=DEBUG

logger.org.xnio.ssl.level=DEBUG

logger.org.apache.level=WARN
logger.org.apache.useParentHandlers=false
logger.io.undertow.util.TestHttpClient.level=WARN
~~~





#### 5) Controller中需要注意的事项

##### 	4.1）render("");

​		返回一个页面的时候 如果在其中不加 / 代表是去webapp目录下 找该目录下的该模板

​			比如 webapp 下有个 test.html  而render("test") 没有加上 / 这个时候会报错 资源找不到 因为它默认在webapp下查找test文件夹下的test.html 而不是webapp下的test

​		加上/ 代表在webapp 模板下寻找

##### 4.2）redirect("url");

​		主要作用转发 内部填写URL值

​			内部原理 直白就是 告诉浏览器你想浏览的路径 不存在 302 not fund 到这个页面去(内部填写的url)

### 其他知识

##### 1）Html

​	JFinal 获取html请求头信息：

​			getRequest().getHeader("头信息中的key");

​		设置头信息

​			getRequest().setHeader("key" , "value");

​	1）CDN : 云解析 类似把一个网站所有的静态资源 储存在云中 当有人访问的时候 访问的这些静态资源是在 云中访问 而不是网站服务器 这样会大大降低网站压力 ， 也会较为提高网站浏览速度



##### 2）Java一些方法

​	2.1) .endsWith

​		测试字符串是否以指定后缀结束 字符串或者变量.endsWith("指定的后缀");

​	2.2) .startsWith("")

​		判断该字符串中是否含有该字符串

​			startsWith(b) --判断字符串a,是不是以字符串b开头

​			endsWith(b) --判断字符串a,是不是以字符串b结尾

3）快捷查看

~~~java
        System.out.println("getRequestURI: "+getRequest().getRequestURI());
        System.out.println("getQueryString: "+getRequest().getQueryString());
        System.out.println("getRemoteAddr: "+getRequest().getRemoteAddr());
        System.out.println("getRemoteHost: "+getRequest().getRemoteHost());
        System.out.println("getRemotePort: "+getRequest().getRemotePort());
        System.out.println("getRemoteUser: "+getRequest().getRemoteUser());
        System.out.println("getLocalAddr: "+getRequest().getLocalAddr());
        System.out.println("getLocalName: "+getRequest().getLocalName());
        System.out.println("getLocalPort: "+getRequest().getLocalPort());
        System.out.println("getMethod: "+getRequest().getMethod());
~~~

