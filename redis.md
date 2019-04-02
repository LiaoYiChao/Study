### Redis 基础

​	1）keys *	---查询当前库中所有键

​			keys ?	---这里的问号代表通配符 可以有多个

​			keys *[1,2]	---也可以理解为通配符

​		dbsize		---查询当前数据库的key的数量

​		flushdb		---清空当前库

​		flushall		---通杀全部库





​	2）exists <key>  		---判断某个键是否存在

​		exists <key> <seconds>	---为键值设置过期时间 单位秒 作用：会在时间到后自动删除该键 但是前提是该key没有被修改 如果被修改 会永久存在 

​		ttl <key>		---查询键剩余多长时间过期 -1表示永不过期 -2表示已经过期





​	3）type <key>		---查询键的类型





​	4）del <key>		---删除某个键



#### Redis五大数据类型

##### 	**string类型：**

​		set ：set <key> <value>

​			set <key> <value> Ex 数值	---设置一个以秒单位的过期值

​			set <key> <value> Px 数值	---设置一个以毫秒单位的过期值

​		mset：设置多个值

​			mset <key1> <value1> <key2> <value2>

​		mget：获取多个值

​		msetnx：设置多个值 但是前提为 设置的key 不存在 ，如果存在不进行设置

​		getrange <key> <索引> <索引>	：截取字符串

​			案例：如果有个 k1 abcd

​				getrange k1 1 2 ：得出的结果ab

​			注意事项：

​				getrange <key> 0 -1	---代表从头到尾

​		setrange <key> <起始索引> <替换的值>	---替换

​		setex <key> <过期时间> <value>	---设置键值的同时 设置过期时间 单位秒

​		getset <key> <value>	---以新换旧 用新的值替换旧的的值同时返回旧的值	在java中 和 HashMap.put 类似





##### 	**list类型：**

​		**一键多值**

​		概述：在Redis中 该类型是一个 双向链表的结构，对两端的操作性能很高，但是最中间的节点性能较差



​		lpush / rpush <key> <value> <value> <value>

​			---从左边 or 右边插入一个或者多个值

​		lpop / rpop <key>

​			---从左边 or 右边 吐出一个值

​			值在键在 ， 值亡键亡

​		rpoplpush <key1> <key2>

​			---从<key1> 列表右边吐出一个值， 插到<key2> 列表左边

​		lrange <key> <初始索引> <结束索引>

​			---按照索引下标获得元素(从左到右)

​		lindex <key> <索引>

​			---按照索引下标获得元素(从左到右)

​		llen <key>

​			---获得列表长度

​		linsert <key> befor <被插入的值> <插入的值>

​			---在 被插入值前 插入一个值

​		linsert <key> after <被插入的值> <插入的值>

​			---在 被插入的值后 插入一个值

​		lrem <key> <要删除的数量> <要删除的value>

​			--- 删除数量大于0：从左至右删除

​				删除数量等于0：全部删除

​				删除数量小于0：从右至左删除







##### 	set类型：

​		概述：对外提供的功能和list类似，特殊之处在于set自动排重，并且set 提供了某个成员是否在set集合内的重要方法，list并没有

​		set是string类型的无序集合，底层是一个value为null的hash表，所以添加，删除，查找的复杂度为o(1)



​		sadd <key> <value> <value>

​			---将一个或多个 member 元素加入到集合 key中，已经存在于集合member 元素将被忽略

​		smembers <key>

​			---取出该集合的所有值

​		sismember <key> <value>

​			---判断集合中是否 包含该 key 且含有该 value 有返回1，没有返回0

​		scard <key>

​			---返回该集合的元素个数

​		srem <key> <value> <value>

​			---删除集合中的某个元素

​		spop <key>

​			--- 随机从该集合中吐出一个值

​		srandmember <key> <n>

​			---随机从该集合中取出 n 个值（并不会从集合中删除）

​		sinter <key1> <key2>

​			---返回两个集合的交集元素 (相同的元素)

​		sunion <key1> <key2>

​			---返回两个集合的并集元素 (两者结果集合 并去重)

​		sdiff <key1> <key2>

​			---返回两个集合的差集元素

​				这里的差集 需要注意一下

​				案例：sadd s1 1 2 3 4 5

​					sadd s2 4 5 6 7 8

​				这里使用 sdiff s1 s2 结果为：1 2 3

​				但是如果 sdiff s2 s1 结果为：6 7 8

​					差集：即把第二个key中 和 第一个key中 交集的部分 排除后的结果 （顺序很重要）







##### 	hash类型

​		概述：结构 一个 key 对应这一个 map 而一个map中也分有key 和 value

​			语法：

​				hset <key> <map中的key> <map中的value>



​		hset <key> <MapKey> <MapValue>

​			---给 key 集合中 <fieId> 键赋值 <value>

​		hget <key> <MapKey>

​			---从 key 集合中的 MapKey 取出value

​		hmset <key> <MapKey1> <MapValue1>  <MapKey2> <MapValue2>

​			---批量设置 hash 值

​		hexists key <MapKey>

​			---查看hash key 中给定域 MapKey是否存在

​		hkeys <key>

​			---列出该hash集合中所有MapKey

​		hvals <key>

​			---列出该hash集合中所有MapValue

​		hincrby <key> <MapKey> <>

​			---xx

​		hsetnx <key> <MapKey> <MapValue>

​			---把该key 中的 MapKey的原始value 设置为该MapValue 





##### zset类型

​		概述：和set类型 类似但是 zset是一个有序的集合 内部需要传一个分数值，而有序则是根据分数大小进行排列



​		zadd <key> <分数值> <value> <分数值2> <value>

​			---将一个或多个 传入到有序集中

​		zrange <key> <初始索引> <结束索引> [withscores]

​			---查找从 该初始索引到结束索引的所有值 如果加上后面的withscores 则会显示每个值的分数大小

​		zrangebyscore<key> <min> <max> [withscores]

​			--- 查找 该key中 分数值~分数值 多少之间的 从小到大

​		zrevrangebyscore <key> <max> <min> [withscores]

​			--- 同上 但是是从 大至小









#### Redis中的事物

​		概率：redis是单线程操作的，按理是没有事物这个概念，但是也正是因为是单线程操作，所以又顺序执行的问题，如果某个靠后执行的操作在前面进行操作，会导致结果出现很大的问题



​		redis事物的定义：redis事物是一个单独的隔离操作，事物中所有命令都会被序列化，按顺序的执行，事物在执行的过程中，不会被其他客户端发送过来的命令打断



​		redis事物的主要作用：串联多个命令防止别的命令插队



​		**关键字：watch unwatch multi exec discard**

​			watch：监控一个key

​			unwatch：取消所有key的监视（慎用）

​			multi：开始事物队列

​			exec：结束事物队列并开始执行

​			discard：终止事物队列



​	流程：

​		[watch <key>]

​		multi

​			某个操作

​			某个操作

​			.......

​		exec



##### redis锁

​	悲观锁：用户操作的时候 上一把锁，除了自己，他人无法操作，这种效率性能很低，因为需要该用户操作完后锁释放，他人才能操作。安全性较高

​		

​	悲观锁在Mysql中为 行级锁 ，而行级锁会出现一个问题 幻读。

​	行级锁：如mysql中的一条sql语句 select * from 表 for update

​	这样就加上了行级锁，该语句查出来的所有数据都会有锁，但是问题是如果该表中有新添加了一个数据，那么该数据无法被读取到，所以需要表锁，把整张表锁上，他人无法进行操作，效率极低.





​	乐观锁：每个用户都能同时操作，不会说我操作了就锁上，他人不能操作，乐观锁不是这样，用户都能操作，但是每个用户在操作的时候，都会加上一个版本号，如果某个用户先操作完毕，那个数据也会有版本号，该版本号为用户的版本号+1，那么当某个用户拿着老版本号去进行操作的时候，该操作会被拒绝。





##### redis中三特性：

​	1）单独的隔离操作

​		事物中所有命令都会被序列化，按顺序的执行，事物在执行的过程中，不会被其他客户端发送过来的命令所打断。

​	2）没有隔离级别的概念

​		队列中的命令没有提交之前都不会执行，因为事物提交前任何执行都不会被实际的执行，也就不存在关系数据库中的 重复读，幻读等

​	3）不保证原子性

​		该事物队列中某个操作失败，不会影响其他操作的成功，也不会进行回滚操作。









#### Redis中的持久化

​	redis提供了两个不同方式的持久化方式：



###### 

###### 		1）RDB （Redis DataBase）

​			使用方式：在指定时间间隔内将内存中的数据集以快照的方式写入磁盘，在恢复的时候，将快照中的文件直接读到内存中。

​		

​			备份是如何执行的：redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入一个临时文件中，待持久化结束后，在用这个临时文件替换上次持久化好的文件，整个过程中，主进程不会进行io操作，这就确保了极高的性能，如果对数据的恢复的完整性不是非常敏感，那么RDB方式要比AOF方式更加高效，RDB的缺点，最后一次持久化的数据会丢失。



操作流程：主线程开始持久化--------------------------》

​				分支---》子线程开始备份（进行io操作）

​		不会阻碍主线程的执行

​			但是如果主线程在中间出现了某个操作，导致宕机了，那么子线程至上一个节点备份后面备份的数据会丢失



配置文件 redis.conf

​		配置文件中备份的名称 dbfilename xxxxx.rdb ----备份的名称

​		配置文件中备份的地址 dir ./	---默认./ 代表当前执行的目录下，可以修改为一个目录 用于专门保存备份数据		

​		配置文件中保存配置 save 300 10	---代表在30秒中有10次操作则进行保存。

​		手动备份：

​			命令：save	--- 只管保存，其他不管，全部阻塞

​					bgsave	---推荐使用



stop-writes-on-bgsave-error  yes

​	---当redis 无法写入磁盘的话，直接关掉redis的写操作



rdbcompression  yes

​	---进行rdb保存时，将文件压缩



rdbchechsum  yes

​	---在存储备份后，还可以让redis用crc64算法进行数据校验，但是这样会增加10%左右的性能消耗，如果希望得到最大性能，可以关闭此功能



**RDB持久化的优缺点：**

​	优点：节省磁盘空间

​			恢复速度快

​	缺点：因为备份是在一定时间做一次备份，所以当redis意外宕机的时候，最后一次备份会丢失







###### 	2）AOF（Append Of File）

​		概述：以日志的形式来记录每个写操作，将redis执行过的所有写操作记录下来(读操作不会记录),只许追加文件，但不允许修改文件，redis启动的时候会读取该文件重构数据，换言之，redis重启会根据日志中的内容将写指令从前到后执行一次，以完成数据的恢复工作。



​		AOF 默认不开，需要手动在配置文件中配置

​		配置文件中 appendonly no (默认no 开启yes)

​		配置文件中备份的地址 dir ./	---默认./ 代表当前执行的目录下，可以修改为一个目录 用于专门保存备份数据



​		AOF以每秒备份一次

​		AOF文件故障恢复：redis-check-aof  --fix  文件名

​	

​		当文件越来越大的时候，AOF拥有重写机制，当文件大小超过设定的阈值时，redis会启动AOF的内容压缩，只保留可以恢复数据的最小指令集	

​		可以使用命令：bgrewriteaof

​		但是AOF的重写机制，是类似重新创建一个快照，然后覆盖旧版的快照

​		重写的时机：

​			auto-aof-rewrite-percentage 100	---当重写大小为上次备份大小的一倍的时候重写

​			auto-aof-rewrite-min-size 64mb	---最小重写大小64mb



​	AOF的优缺点：

​		优点：

​			1）备份机制更稳健，以秒为备份，丢失数据的概率会更低，但是不代表不会存在数据丢失的情况

​			2）可读的日志文本，通过操作AOF稳健，可以处理误操作

​		缺点：

​			1）比起RDB占用更多的磁盘空间

​			2）恢复备份速度相比RDB更慢

​			3）每次读写都同步的话，有一定的性能压力

​			4）存在个别BUG，造成恢复不能	

​		如果RDB和AOF两者都开启，默认会使用AOF恢复数据





#### Redis中的主从复制



​		查看redis 信息 info replication

​		手动把从机升为主机：slaveof no one





​		读写分离：主机专门写，从机专门读

​		容灾性较高



​		如何使用主从分离：

​			1）以docker 为例

​				启动三个redis 以不同的ip地址启动 但是对应都是同一个端口，然后在其中把另外两台设置为从机，使用命令 slaveof <ip> <port>

​			2）把redis解压在linux中配置

​				以配置文件的方式进行也是可以的，redis -server /配置文件的不同 启动的redis也不同

​				开启daemonize yes	---开启守护线程

​				指定端口port		---不同的redis 需要不同的端口

​				log文件名字		---不同的redis 日志文件也要不同

​				dbfilename		---不同的RDB储存的文件名称应不同

​				appendonly no	---关闭AOF

​				修改Pidfile名称	





###### 		主从之间的一些概念：

​			一主两仆：

​				1）当一个主机新增一个从机的时候，是从头开始复制主机中的数据

​				2）主机负责写，从机负责读

​				3）当主机宕机后，从机不会上位还是从机

​				4）主机恢复后，新增记录，从机照样能复制

​				5）当一台从机宕机后，恢复后，会从主机那边重新复制一份数据



​			薪火相传：

​				概述：一个主机对应两个从机，两个从机又对应两个从机。即主机后有从机，从机后还有从机

​				但是主机有且只有一个，后面的都为从机，但是这种方法有效的减少master的压力



###### 哨兵模式：

​	概述：当主机宕机后，哨兵发现后会指定一个从机升为主机。

​	作用：能在后台监控主机是否故障，如果故障了会根据投票数自动将从机升为主机

​	

​	哨兵：每过一小段时间 会ping一下主机，如果在某个时候，发现ping没有回数据，会在ping几次，然后通知其他哨兵也对主机ping，当发现都没有返回结果的时候，认为主机宕机，根据优先级或者某些定义将从机升为主机



​	**配置哨兵：**

​		1）调整为一主二仆模式

​		2）自定义的 redis 目录下 新建 sentinel.conf 文件

​		3）在配置文件中填写内容

​			sentinel  monitor  自定义的监控服务器名称  master的ip  master的端口  设置几个哨兵认同主机宕机是宕机

​		--------------------------

​			sentinel：哨兵

​			monitor：报错的名称，当报改错的时候，认为宕机



​	**启动哨兵：**redis  -sentinel  路径/配置文件名称sentinel.conf



​	当主机宕机后从机的上位选择：

​		1）按照优先级，在redis.conf 中有一个配置

​			slave-priority  数值

​				数值越小优先级越高，但是有一个数值例外 0，当数值为0时 代表不想让该从机有成为主机的机会.....

​		2）当优先级一致时，选择偏移量最大的

​			偏移量即数据量多的从机

​		3）选择runid最小的从服务



#### Redis 基础方法

​	类型：string ， set ，list ， hash ，zset



​	1）设置值

​		类型 <key > <value>	---如果有相同的key 下一个会覆盖上一个

​	

​	2）获取值

​		get <key>



​	3）一些方法

​		**字符串方法**：

​			1：append	--在字符串后追加方法

​				append <key1> <key2>

​				key1 后面追加 key2

​			2：strlen <key>	--查看该key的value长度

​			3：setnx <key>	--设置key值 前提key中的value没有值 如果该key的value存在值 则不进行设置



​		**数值方法：**

​			1：incr <key>	---将key中储存的值+1

​				注意事项：只能对数值类型进行操作，如果为空，新增值为1

​			2：decr <key>	---将key中储存的值-1

​				注意事项：和上类似

​			3：incrby / decrby <key> <步长>	---将key中储存的数值增减，自定义步长

​			**步长**：	---代表自定义增长幅度

​			incrby：增		decrby：减				





#### Redis集群：

​	1）安装ruby环境

​		1：yum install ruby

​		2：yum install rubygems

​	2）修改redis.conf配置文件

​		1：增加 cluster-enabled yes	---开启集群模式

​		2：增加  cluster-config-file	 xxx名称	  ---配置该节点集群配置名称

​		3：增加  cluster-node-timeout  数值		---设置节点失联时间，超过该时间，集群自动主从切换

​	3）合体，将独立的个体转为集群

​		1）进入redis的src目录下

​		2）使用命令

​			./redis-trib-rb  create  --replicas 1

​				192.168.223.46:6379  192.168.223.46:6380

​				192.168.223.46:6381  192.168.223.46:6389

​				192.168.223.46:6390  192.168.223.46:6391

​			

​			--replicas 1 : 的含义代表 有一个master 一个slave

​				如上六台机器，会有3个master，3个slave

​			192.168.223.46:6379 ：开启的redis服务

​				这里使用的为不同端口，也可以用不同的ip地址



​			注意事项：如果使用该命令 没有成功启动 那就先要看一下启动的redis服务器中是否存在数据，启动集群内部不能有数据

​		3）集群成功启动后，

​			使用命令 redis -cli -c -p xxx端口

​				-c :代表集群模式启动

​				-p :指定端口

​			

​			注意事项：redis集群中有一个概念 slots

​				slots：代表一个redis集群中包含16384个插槽，数据库中每一个键都属于这16384个插槽中的其中一个，这些插槽redis使用CRC16(key) % 16384来计算键 key 属于哪个槽



​			举例：如上 第二步 启动命令而言

​				启动之后，redis 会自动分配16384个插槽进入三个master中，比如 节点a 负责 0~5500 ， 

​				节点b 负责 5501~11000

​				节点c 负责 11001~16383

​				

如果发现启动一直错误，可以把配置全部清除，然后重新配置启动



##### 集群的一些命令

​	cluster nodes	---查看当前集群信息

​	cluster keyslot <key>	---计算键 key 被放置在那个槽上

​	cluster countkeysinslot <slot>	--- 返回槽 slot 目前包含的键值对数量

​	cluster getkeysinslot <slot> <count> ---返回count个slot槽中的键





##### 集群在java中的开发：

~~~java
public void JedisClusterTest{
  public static void main(String[] args){
    Set<HostAndPort> set = new HashSet<HostAndPort>();
    set.add(new HostAndPort("ip号"),端口);
    JedisCluster jedisCluster = new JedisCluster(set);
    
    jedisCluster.set("k1","v1");
    System.out.print(jedisCluster.get("k1"));
  }
}
~~~







##### 集群的故障恢复

​	1）当主机当掉后，从机会如何做

​		即便集群中不设置哨兵，当主机宕机后，从机也会自动升为主机，当主机恢复后，主机会将为从机，而上升为主机会一直为主机，直到再次宕机

​	2）当某一段插槽的主从节点都宕掉，redis服务是否还能继续

​		当出现这种情况的时候，根据需求进行操作

​		1）该数据不重要，那么redis还是可以使用，只是那些插槽中的数据可能无法找到

​		2）该数据很重要，那么需要在redis.conf 中添加一个参数

​				cluster-require-full-coverage

​			该参数代表，当发现插槽中主从都宕掉的时候，关闭集群，停止集群服务，这样可以减少数据损失。





##### 集群提供的优缺点

​	优点：

​		1）实现扩容

​		2）分摊压力

​		3）无中心配置（相对简单）

​	缺点：

​		1）多键操作不被支持

​		2）多键的redis事务是不被支持的，lua脚本不被支持





#### redis配置文件redis.conf

~~~proper
daemonize yes		---开始守护线程
port 6379			---端口号
protected-mode no	---是否开启保护
timeout 0			---设置超时时间
save 900 1			---当 xxx时间 xxx数据 进行备份
dir ./				---默认会在当前运行路径下创建
dbfilename "xxx"	---RDB备份名称
include 继承目录	---继承那个 redis.conf 文件
cluster-enabled yes	---开启集群模式
cluster-config-file	xxx	---设置节点配置文件名称
cluster-node-timeout 数值	---设置节点失联时间。超过该时间，集群自动进行主从切换
~~~
