equery 和 == 的区别

== 和 Object中的equery是一样的 比较两个内存中的地址值是否一致



但是 String中的equery 比较的是 两者之间的内容是否 一模一样







1）abstract修饰类，修饰方法

​	1：修饰类：抽象类

​		a:此类不能实例化

​		b:抽象类中不一定有构造器，便于子类实例化调用

​		c:开发中，都会提供抽象类的子类，让子类对象实例化，完成相关的操作。

​	2：修饰方法：抽象方法

​		a:抽象方法只能被声明，没方法体

​		b:包含抽象方法一定是一个抽象类，反之，抽象类中不一定有抽象方法。

​		c:若子类重写了父类中所有的抽象方法，该子类可以被实例化，反之，不能实例化，该子类亦是一个抽象类



​	注意事项：abstract 不能用来修饰：属性，构造器等

​			abstract 不能用来修饰 私有化的，final修饰的，static修饰的。

​	





2）相似的对比

​	1：**throw 和 throws的对比**

​		throw：一般在try catch finally中使用 代表抛出某个异常

​			如：throw new xxxx异常类();

​		throws：本方法中所有异常都由该类进行处理

​			如：public void xxx throws RuntimeException(){}



​	2：**Collection 和 Collections的对比**

​		Collerction：是一个集合接口，提供了对集合对象的基本操作。该接口的意义是为各种具体的集合提供了最大化的统一操作方式，其直接继承接口有List和Set

​		Collerctions：是一个集合类的工具类，其中提供了很多静态方法，用于对集合中元素进行排序，搜索以及线程安全等操作。



​	3：**HashMap 和 LinkedHashMap 的区别**

​		HashMap：允许一个键为空，允许值为空，键唯一，值可以有多个，操作是把键转为hash值进行存储代表其唯一性。HashMap不支持线程的同步，代表任何时候可以进行多线程操作，可能会导致数据的不一致性，如果需要同步，可以使用Collections的synchroniezMap方法使其具有同步功能。

​		LinkedHashMap：也是一个HashMap但是内部有一个双向链表，可以保持顺序，

​		区别：如果存储数据是想保证进入的顺序和被取出的顺序一致的时候，使用LinkedHashMap，HashMap允许一个键为空，值可以多个为空。但是LinkedHashMap不允许键值为空。



​	4：**ArrayList 和 LinkedList 的区别**

​		ArrayList：基于动态数组实现，会有一个初始容量，当该容量接近满的时候，会自行扩容，在原有的基础上加大1.5倍

​		LinedList：基于链表结构，会有一个header，代表头和尾

​		区别：ArrayList是一个动态数组的结构，LinedList是一个链表结构。

​			ArrayList是使用可增长的数组实现的，所以如果插入元素和删除元素，除非插入和删除的位置都在表末尾，否则开销会很大，因为每次需要重构索引，如果插入中间位置，后面的都需要退一位。

​			LindedList是使用双链表实现的，插入和删除不怎么会消耗资源，但是也正是因为是双链表结构，查询的时候相较于ArrayList会更加消耗资源.

​			ArrayList更为适合读取数据，LinedList更为适合添加和删除数据，

​			但是在for循环随机遍历的时候，ArrayList表现很好，LinedList表现很差，因为LinkedList进行随机访问的时候，总会进行一次列表的遍历操作，性能很差，需要避免。

​			还需注意一点，LindedList的实现中，首先通过循环找到要删除的元素，如果删除的元素在List的前半段，则从前往后找，如果在处于后半段，则从后往前找，但是如果需要移除的元素刚好在中间，则几乎要遍历完半个List，在List中拥有大量元素的情况下，效率很低。。。

​			LindedList的插入，因为是链表结构，在任意位置插入都没区别，不会因为插入的位置靠前而导致性能降低。

​			ArrayList的表现，每一次进行数组的删除，都要被删除后的数据进行数组的重组，所以删除的位置越靠前，开销越大。且每一次的插入都会进行数组的重组，插入的位置越靠前，开销越大。

​		



#### 多线程

##### 1）进程和线程

​		说到进行就要先说到程序

​		程序：可以理解为是由某个语言编写的一组指令的集合，即指一段静态的代码，静态的对象（比如下载一个软件，软件本身不会产生变化，如果不操作，一直是原样，所以可以理解为是一个静态的）

​		进程：是程序的一次执行过程，或者说是一个正在运行的程序。是一个动态的过程，有它的产生，存在，消亡的过程

​		比如：在windows下点开某个 .exe 代表这个程序开始运行，占用cpu和内存资源，开始执行某些操作，这种叫进程。

​		线程：进程进一步细分化为线程，

​			若同一时间并行执行了多个线程，就是支持多线程的

​			线程作为调度和执行的单位，每个线程拥有独立的运行栈和程序计数器，线程的切换的开销小

​			一个进程中的多个线程共享相同的内存单位/内存地址空间，它们可以从同一堆中分配对象，可以访问相同的变量和对象，这就使得线程中通信更简便，和高效，但多个线程操作共享的系统资源就会带来安全的隐患。



​		之间的关系 就像，一个房间，就是一个进程，然后其中的人就是一个线程，当有多个人的时候为多线程，这些线程共享饮水机，等公共堆和变量，但是也正因为人多，会有安全的隐患。





##### 2）并行和并发

​	并行：多个CPU同时执行多个任务。比如：多个人同时做不同的事情

​	并发：一个CPU同时执行多个任务。比如：秒杀就类似并发，又比如一个篮球场，当有多个场地的时候，每个人都可以在不同的场地上打可以理解为并行，但是如果只有一个场地，多个人争抢这一个场地可以理解为并发。



##### 3）何时需要多线程

​	1：程序需要同时执行两个或多个任务的时候

​	2：程序需要实现一些 等待任务时，如文件读写操作，网络操作，搜索等。

​	3：需要后台运行的程序时





##### 4）如何创建和使用多线程

​	**1：继承 Thread类**，然后重写其中的run方法，之后实例对象，用实例对象.start(); 开启该线程

​		.start(); 作用1：会开启一个线程

​				作用2：会调用当前线程的run方法

​		注意事项：如果想在创建多个线程，可以再次实例化对象，之后在调用 .start() 即可开启第二个线程



​	使用匿名类部类的方式更为简洁

~~~java
new Thread(){
  @Override
  public void run(){
   	//需要被该方法调用的方法体
  }
}.start();
~~~



​	**2：实现Runnable接口**，实现其中的run方法

~~~java
/*

	1:实现Runnable接口
	2:重写其中的run方法
	3:创建实现Runnable类的实例对象
	4:使用Thread中的构造器将实现Runnable的类传入
		new Thread(实现Runnable的课);
	5:调用Thread的start()方法
*/

class Thread_test implements Runnable{
  public void run(){
    //方法体
  }
}
~~~



3：实现Callable接口

~~~java
/*
	相比Runnable，Callable更加强大
		1）相比run()方法，可以有返回值
		2）方法可以抛出异常
		3）支持泛型的返回值
		4）借助FutureTask类，可以获得返回值
*/
class Thraed_test implements Callable{
  public Object call() throws Exception{
    //需要该线程执行的方法
    return ;
  }
}

public class test{
  public static void main(String[] args){
    Thraed_test test1 = new Thraed_test();
    
    //需要借助该类进行 该类实现了 Callable 和 Runnable接口
    FutureTask task = new FuturTask();
    
    //最终还是需要 Thread 进行线程启动
    new Thread(task).start();
    
    //如果想获取线程的返回值 使用
    //task.get();	即可 需要try catch
  }
}
~~~



4：使用线程池的方式（开发常用）

​	概述：提前创建好多个线程，放入线程池中，使用时直接获取，使用完放入池中，可以避免线程频繁的创建和销毁，实现重复利用



​	使用方式：

​		ExecutorService：真正的线程池接口，常见的子类ThreadPoolExecutor。

​			void execute(Runnable command)	---执行任务/命令 没有返回值，一般用于执行Runnable

​			<T> Future<T> submit(Callable<T> task)		---执行任务,有返回值，一般用于执行Future





​		Executors：工具类，线程池的工厂类，用于创建并返回不同类型的线程池

​			常用方法：

​				Executors.newCachedThreadPool();

​					创建一个 线程池

​				Executors.newFixedThreadPool(n);

​					创建一个 可重用固定线程数的线程池

​				Executors.newSingleThreadExecutor();

​					创建一个只有一个线程的线程池

​				Executors.newScheduledThreadPool(n)

​					创建一个线程池，它可安排在给定延迟后运行命令或定期的执行

~~~java

public class executors{
  public static void main(Stringp[] args){
    //造了一个线程池 内部有10条可重复使用的线程
    Executors pool = Executors.newFixedThreadPool(10);
    
    pool.execute();	//调用线程执行 Runnable使用
    
    pool.submit();	//同样也是执行，Callable使用
  }
}

~~~







开发中的实际使用：

​	开发中，优先选择，实现Runnable接口的方式

​		原因：

​			实现的方式没有类的单继承的局限性

​			实现的方式更适合来处理多个线程有共享数据的情况





###### Thread中的常用方法

~~~java
/**

常用方法
1: start()	---执行该线程,调用当前线程的run方法
2: currentThread()	---静态方法
	Thread.currentThread.getName()	---获取当前线程名
	setName()	---设置线程名，需要在 .start 方法前设置
3: yield()	---释放当前线程的执行权，让其他线程先执行
4：join()	---和上方法相反，可以理解为握紧执行权，当我执行完后，在交于其他线程执行。
5：sellp(毫秒值)	---休眠该线程
6: isAlive()	---判断当前线程是否存活

7：wait()	---休眠当前线程
8：notify()	---唤醒休眠的线程（如果有多个线程进入了休眠，则优先唤醒优先级高的线程）
9：notifyAll()	---唤醒所有休眠的线程


线程优先级
	MAX_PRIORITY:10	---最大优先级
	MIN_PRIORITY:1	---最小优先级
	NORM_PRIORITY：5	---默认优先级
	
1:获取当前线程的优先级
	getPrioroty();
2:设置当前线程优先级
	setPrioroty(int p);
	
*/
~~~



###### 线程安全问题

​	synchronized	---同步锁

**方式一：同步代码块**

~~~java
public class Thread_test extends Thread{
  
  Object obj = new Object();
  
  public void run(){
    //这里的同步锁的锁 任何对象都可以是锁
    //但是需要的是多个线程的锁是唯一的
    synchronized(obj){
      //这锁了的线程方法 是安全的
    }
  }
}
~~~

**方式二：同步方法**

~~~java
public static synchronized void test(){
  //被synchronized 包起来的方法 为同步方法 一样是线程安全的
}

public synchronized void test1(){
  
}

/*

同步方法总结：
	1：同步方法依旧涉及到同步监视器，但是不需要我们显示声明
	2：同步方法默认的监视器为 this
		如果有static 修饰的同步方法的监视器为 当前类本身

*/

~~~

**方式三：Lock锁**

~~~java
public class lock_test{
  
}

class locktest extends Runnable{
  //该类是 Java的实现lock锁的子类 提供了操作lock的基础方法
  private ReentrantLock lock = new RenntrantLock();
  
  public void run(){
    try{
      lock.lock();	//锁
      
      //需要被同步的方法
      
    }finally{
      lock.unlock();	//释放锁
    }
  }
}
~~~



**两者之间的区别：**

​	synchronized  ||  lock;

​	相同点：两者都可以解决线程安全问题

​	不同点：synchronized执行完锁住的代码后自动释放

​			lock是手动的开始锁和释放锁。



​	sleep  ||  wait；

​	相同点：都可以使线程进行阻塞状态

​	不同点：sleep 可以在任何场景调用，wait只能在同步代码块或同步方法中

​			sleep声明在Thread中，wait声明在Object中

​			sleep执行后不会释放锁，wait执行后会释放锁



