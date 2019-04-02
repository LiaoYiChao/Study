#### java

```java
Assert:断言

Assert.notNull		---判断是否为null
	使用方法：Assert.notNull(一个值的判断，如果为空的输出);

Assert.isTrue		---判断一个值 正确为true 反为false
	使用方法如上。
```

BigDecimal类：

​	BigDecimal.ZERO		---设置为 0 

​	BigDecimal.valueOf()	---设置一个整数值





enum 枚举类：

~~~jav
package top.liaoyichao.enums;

/**
 * @Author: LiaoYiChao
 * @Date: 2019/1/23 14:47
 * @Description: 订单处理情况状态
 */
public enum OrderStatus {
    /**
     * 初始化
     */
    INIT("初始化"),

    /**
     * 处理中
     */
    PROCESS("处理中"),

    /**
     * 成功
     */
    SUCCESS("成功"),

    /**
     * 失败
     */
    FAIL("失败");

    private String desc;

    OrderStatus(String desc){		//使用枚举类需要私有化该类
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}

~~~



@BeforcClass : 初始方法 在开始方法前执行，是一个静态 只会执行一次

@Befor：效果类似上 也是在方法前执行

@Test：加上该注解 表明是一个测试用例（Assert：断言）

@After：在方法结束之后执行

@AfterClass: 结束方法 方法结束之后执行



#### Java面试





### 1) Java多线程

​	java多线程：指一个程序运行时产生了不止一个线程

​	java线程具有的五种基本状态：新建，就绪，运行，阻塞，死亡



java多线程的创建和使用：

​	1）继承Thread 重写该类的run()方法

​	1.1）new 一个Thread	

​		如：Thread t = new Thread;	创建了一个新线程 t线程进行新建状态。	

​		t.start();		//调用该方法 线程进入就绪状态

​	2）实现Runnable接口 重写其中的run方法

​	2.1）如上 同样是new 一个Runnable 同样使用.start 进入就绪状态





可能出现的问题：

​	并行：多个CPU实例或者多台机器同时执行一段处理逻辑。

​	并发：通过CPU调度算法，让用户看上去同时执行，实际是分开执行，并发大多发生在公用资源中。



​	线程安全一：`@synchronized`关键字

```java
public class Thread1 implements Runnable {		//用代码块进行锁定
   Object lock;
   public void run() {  
       synchronized(lock){
         ..do something
       }
   }
}



public class Thread1 implements Runnable {		//用于方法 效果是一样的
   public synchronized void run() {  
        ..do something
   }
}
```



​	线程安全二：Lock类

```java
ReentrantLock							//三种实现
ReentrantReadWriteLock.ReadLock
ReentrantReadWriteLock.WriteLock
```

和synchronized的区别

```properties
lock更灵活，可以自由定义多把锁的枷锁解锁顺序（synchronized要按照先加的后解顺序）
提供多种加锁方案，lock 阻塞式, trylock 无阻塞式, lockInterruptily 可打断式， 还有trylock的带超时时间版本。
本质上和监视器锁（即synchronized是一样的）
能力越大，责任越大，必须控制好加锁和解锁，否则会导致灾难。
和Condition类的结合。

lock性能稳定 synchronized前提性能高于lock 但中期后期远低于lock
```

**ReentrantLock**　　　　
可重入的意义在于持有锁的线程可以继续持有，并且要释放对等的次数后才真正释放该锁。
使用方法是：

1.先new一个实例

static ReentrantLock r=new ReentrantLock();

2.加锁　　　　　　
r.lock()或r.lockInterruptibly();

此处也是个不同，后者可被打断。当a线程lock后，b线程阻塞，此时如果是lockInterruptibly，那么在调用b.interrupt()之后，b线程退出阻塞，并放弃对资源的争抢，进入catch块。（如果使用后者，必须throw  interruptable exception 或catch）　　　　

3.释放锁　　　

```
r.unlock()
```

必须做！何为必须做呢，要放在finally里面。以防止异常跳出了正常流程，导致灾难。这里补充一个小知识点，finally是可以信任的：经过测试，哪怕是发生了OutofMemoryError，finally块中的语句执行也能够得到保证。



### 2) Servlet生命周期和与CGI的区别

​	Servlet生命周期分为5个阶段：

​		实例化：Servlet容器创建Servlet类的实例

​		初始化：该容器调用init()方法，通常会申请资源

​		服务：由容器调用Servlet()方法 也就是doGet和doPost方法	

​		破坏：在释放Servlet实例之前调用destroy()方法，通常会释放资源	

​		不可用：释放内存实例





概括来讲，Servlet可以完成和CGI相同的功能。

CGI（Common Gateway Interface通用网关接口）程序来实现数据在Web上的传输，使用的是如Perl这样的语言编写的，它对于客户端作出的每个请求，必须创建CGI程序的一个新实例，这样占用大量的内存资源。由此才引入了Servlet技术。

Servlet是一个用java编写的应用程序，在服务器上运行，处理请求信息并将其发送到客户端。对于客户端的请求，只需要创建Servlet的实例一次，因此节省了大量的内存资源。Servlet在初始化后就保留在内存中，因此每次作出请求时无需加载。

　CGI应用开发比较困难，因为它要求程序员有处理参数传递的知识，这不是一种通用的技能。CGI不可移植，为某一特定平台编写的CGI应用只能运行于这一环境中。每一个CGI应用存在于一个由客户端请求激活的进程中，并且在请求被服务后被卸载。这种模式将引起很高的内存、CPU开销，而且在同一进程中不能服务多个客户。

　　Servlet提供了Java应用程序的所有优势——可移植、稳健、易开发。使用Servlet Tag技术，Servlet能够生成嵌于静态HTML页面中的动态内容。

　　Servlet对CGI的最主要优势在于一个Servlet被客户端发送的第一个请求激活，然后它将继续运行于后台，等待以后的请求。每个请求将生成一个新的线程，而不是一个完整的进程。多个客户能够在同一个进程中同时得到服务。一般来说，Servlet进程只是在Web Server卸载时被卸载。



### 3) java自增变量

​	java的自增 有两种 前++ 和 后++

​		但这里有一个坑 比如 如果 int a = 1; int b = a++;

​			b的结果为多少呢 为1 因为++在后 为先赋值在自增 而这个自增 确实a为2了 但并没有进入栈中 只有1这个值进入了 所以局部变量里 a为2 栈中a为1



### 4) 单例模式(*singleton*)

在java中单例模式 很常见

​	理论：

​		1）某个类中只能有一个实例	---构造器私有化

​		2）必须自行创建该实例对象	---使用静态变量来保存该唯一的实例

​		3）对外提供访问方法			---对外提供获取该实例对象的方式(1:直接获取 , 2:用静态变量的get方法获取)



​	饿汉式：不管你使没使用我 我都要创建该实例

​	懒汉式：不使用我 我不会创建该实例 使用我 才创建

​	

​	如以下代码所示：

```java
//饿汉式
//外部使用 直接调用 Dome.DEME
public class Dome{
    private Dome(){}	//构造器私有化
    private static final Dome DOME = new Dome();	//自行创建实例
}

//懒汉式(多线程会有 线程安全问题)
public class Dome{
    private Deom(){}	//构造器私有化
    private static Dome dome;	//这里不创建实例
    public static Dome getDome(){
        if(Dome == null){
            dome = new Dome();
        }
        return dome;
    }
}

//懒汉式(多线程解决方案)
public class Dome {
    private Dome(){}	//构造器私有化
    private static Dome dome;
    public static Dome getDome(){
        if(dome == null){	//优化性能 如对象存在 不在进入
            synchronized(Dome.class) {	//使用锁进行线程控制
                if (dome == null) {
                    dome = new Dome();
                }
            }
        }
        return dome;
    }
}
```

### 5) 类的初始化过程

1）main方法所在的类先进行初始化 加载

2）子类需要初始化的时候需要先初始化父类

​	1：如果该类拥有父类 则父类先进行加载

例子：如下代码图所示

![1548992783444](C:\Users\Administrator.ETOSS4S9LE39TEM\AppData\Roaming\Typora\typora-user-images\1548992783444.png)

 1）子类初始化过程

​	0：静态方法不初始化也会执行 一样先由父类中的静态方法先执行

​	1：super() 先执行 即父类

​	2：从上至下执行顺序执行

​	3：子类中的非静态代码块

​	4：子类的无参构造

**注意事项：**

​	1：这里的从上图子类中可以看出 private static int j = method();

​		这个会先进行执行 之后会执行其中的静态代码块

​		但这里的执行顺序是从上至下**

​		即：（5） ， （1）， （10） ， （6）

​	2：方法的重写 如图所示 其中的test方法会被子类中的test方法重写 即(9) 会代替 (4) 

​	 **不能被重写的方法：**

​		final修饰的方法 ， static 修饰静态的方法  private等子类中不可见的方法

​	对象的多态性：

​	  1：子类如果重写了父类中的方法 通过子类对象调用的一定是子类重写后的方法

​	   2：非静态方法默认调用的是this

​	   3：this对象在构造器或者说是 <init> 方法中是正在创建的对象 即谁调用我 谁就是this

2）父类的初始化过程，和子类相似

​	0：先执行其中的静态方法 或者静态代码块 以从上至下的顺序执行	

### 6) 递归和迭代的区别：

```java
public class testDome {
    /**
     * 这种使用的为 递归    --- 方法调用自身为递归
     *  优点：代码量少 精简 可读性强
     *  缺点：性能差 递归太深 容易栈内存溢出
     * @param n
     * @return
     */
    public int f(int n){
        if(n == 1 || n == 2){
            return n;
        }
        return f(n - 2) + f(n - 1);
    }

    /**
     * 这种使用的为迭代     ---利用变量的原值 推出新值称为迭代
     *  优点：性能较好 根据循环的次数增加而增加 而没有增大栈内存开销
     *  缺点：代码较于递归 可读性差些 精简度差些
     * @param n
     * @return
     */
    public int d(int n){
        int one = 1;
        int tow = 2;
        int sum = 0;

        if(n == 1 || n == 2){
            return n;
        }

        for(int i = 3; i <= n; i++){
            sum = one + tow;
            one = tow;
            tow = sum;
        }
        return sum;
    }
}
```

### 7) 局部变量和成员变量的区别

​	1）声明的位置不同：

​		---局部变量：方法体{}中 ， 形参 ， 代码块{}中

​		---成员变量：类中方法外

​			---类变量：有static 修饰

​			---实例变量：没有static修饰

```java
//比如：
public class Dome{
    private String name; 	// 成员变量中的实例变量 调用一次后弹出 下一个重新开始
    private String address; //成员变量中的实例变量 调用一次后弹出 下一个重新开始
    static int count; //成员变量中的类变量 随着类的调用而调用 会一直增长
}
```

​	2）修饰符的区别：

​		---局部变量：final

​		---成员变量：public priavte protected static transient final 等

​	3）值存储位置不同：

​		---局部变量：栈	---从声明处开始 调用后存于栈中 并等待下一次执行 直到所属的  } 结束

​		---实例变量：堆	---随着调用的结束 弹出结果 下一个重新开始

​		---类变量：方法区中	---只要调用了该类 会一直处于增加状态

## 其他知识：

### 1)Http

#### 1.1）超文本传输协议主要特点:

​		1：支持客户/服务器模式

​		2：简单快速

​			传输只需传输路径 如 get ， post	因http程序规模小 故传输速度块

​		3：灵活

​			可以传输任意类型的数据

​		4：无连接

​			链接完 即断 但现在默认使用长链接：即链接后一段时间才断 但并不清楚是否处于长链接状态 所以可以理解为短链接 用完即断

​		5：无状态

​			不会保存之前的状态 如果某个链接需要之前的信息 可能需要重新传递



#### 1.2）请求/响应步骤:

​		--> 客户端连接到Web服务器

​		--> 发送Http请求

​		--> 服务器接受请求并返回Http响应

​		--> 释放连接Tcp连接

​		--> 客户端浏览器解析Html内容

​	

#### ​	1.3）常见的Http状态码

​		200 : 正常返回信息

​		400： 客户端请求有语法错误 ， 无法被服务器理解

​		401： 请求未经授权 改状态码必须和 WWW-Authenticate 报头域一起使用

​		403： 服务器收到请求，但拒绝访问

​		404：请求资源不存在

​		500：服务器发送不可预估的错误

​		503：服务器当前不能处理客户端的请求，一段时间后可能恢复正常





#### 1.4）Http面试题：

​		在浏览器地址栏键入Url 按下回车之后会经过什么

​		DNS解析 --》 Tcp连接 --》 发送Http请求 --》 服务器处理请求并返回Http报文 --》浏览器解析并渲染页面 --》 连接结束



#### ​	1.5) GET 请求和 POST 请求的区别

​		--》 Http报文层面 ： GET 将请求地址信息放在Url中即为显性的

​							POST 将请求放在报文体中 可以理解为隐性的

​		--》 数据库层面 ： GET 符合其安全性 POST不符合

​		--》 其他层面： GET可以被缓存 被存储 而POST不行

​	

#### ​	1.6）Cookie 和 Session的区别

​		Cookie概述：服务器发送到客户端中的一段文本 可以在客户端中进行存储

​				客户端再次请求的时候，会把Cookie回发

​				服务端接受到后，会解析Cookie并生成对应的返回信息

​		Seesion概述：服务端上保存信息

​				解析客户端请求并操作Session Id  ， 按需保存状态信息(该Id 最佳为随机且唯一的Id)

​		两者区别：Cookie 数据存放在客户的浏览器上 ， Session数据存放在服务器上

​					Session 相对 Cookie 更加安全

​					但Cookie 因存放在客服端浏览器上 更加节省服务器资源



### 2）Https和Http之间的区别

​	https = http + 加密 + 认证 + 完整性保护

​		https 默认使用443端口 http 80端口

​		https 密文传输  http 明文传输

​		使用SSL通信 并采用身份验证和数据加密保证网络通信安全和数据的完整性

​			注意事项：在很多人使用浏览器进行浏览时 输入网站 很多人 不会输入前缀的https

​				而是直接输入网站名 让网站自动填充 而网站自动填充大多时http 且如果该网支持跳转 但这个时候很容易发送劫持 因为在跳转前为 http 而不是 https

#### ​	2.1）加密的方式

​		对称加密：加密和解密为同一个密钥

​		非对称加密：加密和解密非同一个密钥

​		哈希算法：将任意长度的信息转换为固定长度的知 如MD5 算法不可逆

​		数字签名：证明某个消息或文件时某人发出/认同的











#### 简单理解 String , StringBuff , StringBuilder

​	String：字符串常量

​	StringBuff：线程安全的字符串变量(内部有很多锁)

​	StringBuilder：线程不安全的字符串变量

​		速度方面：String < StringBuff < StringBuilder

​			但有例外：即最简单的使用

​			String test = "String"; 这种最快

​			String test = "String" + "String" + "String" ; 这种也最快



####  

#### 简易理解 JDK ， JRE ， JVM ， JAVA

​	JDK：java开发工具包

​	JRE：java运行环境

​	JVM：java虚拟机（JRE的一部分）主要工作：解释指令集 并映射到cpu的指令集或os系统调用

​	JAVA：编程语言

​	

从底部 -> 上层

​	jvm(虚拟机) -> jre(运行环境) -> jdk(开发工具包) -> java(集成开发工具 如idea，eclipse)



#### Java的锁机制

##### **1）synchronized：锁**

​	功能：同一时间只能拥有一个线程能够访问被该锁 锁住的代码 比如方法 代码块等..



​	缺陷：因为该锁的机制，当某线程进入该同步方法或者同步代码块的时候，那么其他线程无法访问 该同步方法 or 同步代码块，如果之前的线程出现了无限循环等状况 那么其余的线程无法进入 造成死锁的状况



##### **2）lock：锁**

​	功能：功能更加强大



​	lock 的一些常用方法：

​		void lock()	---在等待获取锁的过程中休眠并禁止一切线程调度

​		

​		unlock()		---释放锁（必须）



​		void lockInterruptibly() throws InterruptedException;	---在等待获取锁的过程可以被中断

​		官方解释：

​			如果锁不可用，则当前线程将被禁用以用于线程调度，并处于休眠状态，直到发生两种情况之一：锁由当前线程获取；或者其他线程中断当前线程，并且支持中断锁获取



​		boolean tryLock();	---获取到锁返回true 获取不到锁返回false（很实用 在锁前面进行一个if 判断）



​		boolean tryLock(long time, TimeUnit unit) throws InterruptedException;		---在指定时间内等待获取锁，过程中可被中断



​	案例：

​		假如线程`A`和线程`B`使用同一个锁`LOCK`，此时线程A首先获取到锁`LOCK.lock()`，并且始终持有不释放。如果此时B要去获取锁，有四种方式：

- `LOCK.lock()`: 此方式会始终处于等待中，即使调用`B.interrupt()`也不能中断，除非线程A调用`LOCK.unlock()`释放锁。
- `LOCK.lockInterruptibly()`: 此方式会等待，但当调用`B.interrupt()`会被中断等待，并抛出`InterruptedException`异常，否则会与`lock()`一样始终处于等待中，直到线程A释放锁。
- `LOCK.tryLock()`: 该处不会等待，获取不到锁并直接返回false，去执行下面的逻辑。
- `LOCK.tryLock(10, TimeUnit.SECONDS)`：该处会在10秒时间内处于等待中，但当调用`B.interrupt()`会被中断等待，并抛出`InterruptedException`。10秒时间内如果线程A释放锁，会获取到锁并返回true，否则10秒过后会获取不到锁并返回false，去执行下面的逻辑。

##### 两者之间的对比：

synchronized和Lock均为可重入锁。即可为该对象多次加锁，通过锁标志+1进行操作；当所标志为0时，释放所。重入锁目的是为防止死锁发生。

synchronized：基于系统内核实现线程等待（通过linux系统pthread_mutex_lock命令进行等待）。
        a、将线程通过CAS操作放入ContentionList队列头部。
        b、当Owner拥有锁者unlock时，会将ContentionList队列中的内容移动到EntryList中，并将头结点设置为ready，可以去竞争锁。
        c、当Owner拥有锁者被wait时，进入waitSet队列。被notify时重新进入EntryList中，进行锁竞争。

        自旋锁：线程被阻塞为系统阻塞，处于内核态，java执行为用户态；为避免经常在用户态和内核态频繁切换导致性能下降，实现了自旋锁（即没竞争到锁后，自己循环一段时间竞争锁，如果超期没获得，再阻塞）。

        偏向锁：线程加锁操作采用CAS操作。当线程已经获得锁后，再重入该锁时，后续操作将不会采用CAS，因为不存在锁竞争。

        

Lock：JAVA自己实现。基于AQS非阻塞队列和CAS原子操作实现。
        a、将所有请求锁的线程通过CAS放入AQS队列（FIFO）中，如果失败则无线循环放入。阻塞调用LockSupport.park完成，park调用sun.misc.Unsafe.park本地方法实现。最终也是调用pthread_mutex_lock。AQS也是一个CHL队列，即实现了自旋锁，所以这是一个非公平锁。
        b、也实现了自旋锁和偏向锁（JAVA代码实现）。

**俩种锁对比：Lock扩展性强，因为是JAVA实现的，可以基于AQS实现其他锁功能（读写锁、中断锁）。**



**3）Volatile 轻量锁（但是不能解决原子性问题）**

​	Volatile 主要作用 是把值转变为可见的 这里的可见 是让cpu可见，等于该值在cpu中变为全局变量，但是并不能真正解决原子性问题 因为如果某一个cpu在写入的时候，该值已经被另外一个cpu读取的 那么最终的值还是不正确的



4）JUC	Atomiclnteger(这是一个类)

​	可以解决原子性问题 内部使用 CAS算法 ：简单说 一直循环 直到每个cpu都知道该变量变了。

​	

​		





