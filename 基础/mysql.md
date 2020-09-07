#### Mysql 数据库基础

​	DB：数据库

​	DBMS：数据库管理系统

​	SQL：结构化查询语言

~~~proper
	1）基础的查询
		select 
			查询的东西 
		from 
			表名

	2) 设置别名
		select
			xxx as 别名, xxx as 别名
		from
			xxx

	
	3) 去重	---distinct
		select distinct
			xxx
		from
			xxx

	4)拼接	---concat
		select
			concat(xxx,xxx,xxx,可以为多个) as 别名
		from
			xxx

	5)查看表结构
		desc xxx表名;

	6)判断是否为空
		select ifnull(需要判断的值,如果判断值为空显示的值 )
	
	7）一些基础的表达式
		条件表达式：> < = != >= <=
         逻辑表达式
         	&& || ！
            and or not
              这是对应的 逻辑与 逻辑或 逻辑非
              
         between and 提高简洁度：
         	比如 where xxx >= xxx and xxx <= xxx
         		使用between and 这样表达
         		where xxx between xxx and xxx
         			即代表在 xxx ~ xxx 之间
         			
         in 提高简洁度
         	等价于 or
         		where xxx='xxx' or xxx='xxx'
         	where xxx in ('xxx','xxx')
      
	8)模糊查询 like
		select
			*
		from
			xxx
		where
			xxx like '% xxx %';
			
			通配符
			%: 代表任意多个字符 
				比如上述 代表只有存在 xxx 都是可以的 
			_: 代表任意单个字符
				比如
					select xxx from xxx where xxx like '__x__'   ：代表只有 第三个为 x 的才满足条件
					
			转义字符： \ 
			
	9) 查询有null 的字段值	is null
		直接使用 = 是不能查询null值的数据
			需要使用 is null
			相反的使用：is not null
			
			
	10)
---		排序查询 order by
		select
			查询字段
		from
			表名
		where order by
			条件
		desc ---表示从高到低
		asc	---表示从低到高
	
    
---		分组 group by
		分组 会自动把 重复的归于一类
			案例：查询男女个数
			select count(*),sex from 表名 group by sex
		
		
	11)常见函数
---		字符函数：
			length('') ---统计一个字符的字节长度
			concat('','') ---字符串拼接 可以传递多个
			upper()	---转化为大写
			lower()	---转化为小写
			
			substr()	---截取字符
				注意事项：传参有三个
					第一参数为：需要被截取的字符
					第二个参数：截取到该字符的那个索引位置
					第三个参数：存在第二个参数的时候 生效 从第二个参数 截取到第三个参数的 索引之间的字符
					
			instr() ---返回被查询值出现的第一次的索引
				传参为二：第一个参数：被查询的值
					第二个参数：想查询的值
					
			trim()	---去除前后的某个值 默认为空格
				如果传参：第一个参数：想去除前后的某个字符
					第二参数：字符
				比如 trim('a','aaaaaahhhaahhhaaaaaa')
				最后返回为 hhhaahhh 因为中间不是前后的
				
			lpad()	---左填充
				三个参数：第一个参数：字符
					第二参数：填充的个数 int
					第三个参数：左填充的字符
			rpad()	---右填充 参数和上一致
			
			replace()	---替换
				三个参数：第一个参数：字符
					第二个参数：字符中想替换的值
					第三个参数：替换被替换的值
					
---		数学函数：
			round()	---默认四舍五入
				如果加上第二个参数：代表保留小数点后 该参数位
			ceil()	---向上取整
			floor()	---向下取整
			
			truncate()	---截取
				第一个参数：被截取的int
				第二个参数：截取的位数
				
			mod()	---取余 类似 %
			
---		日期函数：
        	now()	---返回当前系统日期+时间
        	
        	curdate()	---返回当前系统日期
        	
        	curtime()	---返回当前时间
			
			日期格式自定义：str_to_date()
				第一个参数：时间
				第二个参数：时间的格式
					但是该时间的格式需要准守一些规则
						比如 %Y %m %d
							上面三个代表 年 月 日(小写显示的不一样)
			日期转换为字符：date_format()
				第一个参数：时间
				第二个参数:'%Y年%m月%d日' 类似这种
	
---		流程控制函数：
			if()
				第一个参数：表达式 返回true or false
				第二个参数：如果为true 进入
				第三个参数：如果为false 进入
			实际使用可以这样：
				select
					if(xxx is null,xxx,xxx)
				from
					xxx
				如果该字段为空 返回第二个xxx 不为空 返回第三个参数
			
			case	---等值判断
				使用案例：
			case 需要判断的字符或表达式
			when 常量1 then 要显示的语句
			when 常量2 then 要显示的语句
			。。。
			else 要显示的语句
			end
			
				实际案例
			select xxx,xxx
			case xxx
			when xxx then xxx
			when xxx then xxx
			else xxx
			end as xxx
			from xxx
			
			case使用方法二：区间判断
				在 case后面不跟需要判断的字符或者表达式
				 就类似为java中的多重if else
			case
			when xxx then xxx
			when xxx then xxx
			else xxx 
			end
			
			类似java
			if(xxx){
              xxx
			}else if(xxx){
              xxx
			}else{
              xxx
			}
			
---		分组函数：	
			sum ： 求和
			avg ： 平均值
			max ： 最大值
			mix ： 最小值
			count ： 计算个数(非空的数量)
				分组函数特点：
					sum avg 计算数值使用
					max mix count 可以用于任意值使用.
					
---			having : 排序后再次进行删选 类似where 但是不同于where
				比如：
		select xxx from xxx where xxx order by xxx having xxx
		
			注意事项：having 后面的条件为 select xxx 这里的xxx 字段 然后根据这些 xxx字段在进行删选 比如：
			select max(a),xx_id from xxx where xxx is not null groud by xx_id having a >= xxx
			
			
		12）分组查询 group by 
		注意事项：分组查询中的筛选条件
					  数据源   位置   			关键字
        	分组前筛选： 原始表   gruod by的前面	  where
        	
        	分组后筛选： 分组后的结果集  groud by的后面  having
        	
       		分组函数做条件 肯定在 having子句中
       		性能考虑 能用分组前筛选 就尽量用分组前筛选
       		
       		
       	13）union 和 union all
       		作用：合并结果集
       			union：会排除重复的值
       			union all：会保留重复的值
       		注意事项：
       			union 内部的 select 必须拥有相同数据的列，列也必须拥有相似的数据类型，同时，每条select中的顺序必须相同
~~~

多表之间的查询：

​	表起别名在 from 后面的表 from xxx表 as 别名 

​							或者 一个或多个空格 别名



​	内连接：

​		1）等值连接：

​			语法：select  表1字段，表2字段  from  表1  inner join表2  on  **表1.key = 表2.key**  where  筛选条件	 group by 分组  having 结果集筛选  order by 降序or升序

​		2）非等值连接

​			语法：select  表1字段，表2字段  from  表1  inner join表2  on  **表1中某个字段 between 表2某字段 and 表2某字段(两者之间能进行关联的地方)**  where  筛选条件	 group by 分组  having 结果集筛选  order by 降序or升序

​		3）自连接（自己连自己）

​			语法：select 表中某字段，表中某字段 from 表 join 表 on 表中某字段 = 表中某字段



​	外连接：

​			外连接查询结果 = 内连接结果 + 主表中有从表中没有的记录

​		外连接特点：

​			1）外连接的查询结果为主表中所有的记录

​				然后根据连接条件查看从表是否有和主表相匹配的记录 

​				如果有则显示 没有则显示null



​			2）左外连接 left join 左边为主表

​				右外连接 right join 右边为主表	

​				（上两者交换位置 效果一样）



​			3）全外连接 full join

​				概述：连接起来的表都查询 然后其中的交集

​				全外连接 = 内连接的结果 + 表1中有表2中没有 + 表2中有表1中没有

​				**注意事项** ：

​					1）如何判断谁才是主表

​						看主要查那个数据 因为外连接中主表会查询所有的记录，而副表只查看与主表有关联的，没有关联的会显示为null



​			4）交叉连接：cross join

​				主表中的一个数据会对应从表中的所有数据 即笛卡尔乘积





##### 内连接和外连接总结：

​	内连接：查询 表1 和 表2 的交集

​		理解：两个圆 中间重合的部分 即为交集

​		语法：select 查询字段 fron 表1 inner join 表2 on 连接条件

​		

​	左外连接：查询 表1(左)所有记录 和 表2与表1的交集

​		理解：两个圆 左边的圆的全部 和与右边圆与左边圆之间的交集

​		语法：select 查询字段 from 表 left join 表2 on 连接条件



​	右外连接：和左外类似 只是查询的为右边圆的全部 和左边圆与右边圆的交集部分 关键字为 right join



​	

​	



**子查询：**

​		拥有函数： any ：该子查询中最小的那个值

​						可以使用 在子查询中 该字段使用 min() 可以代替该函数

​					all：该子查询中最大的那个值

​						可以在子查询中 使用max() 得到一样的效果

​					 in：等于列表中的任意一个值 类似实现笛卡尔乘积 一对多 主数据 一条数据 对应子查询中的每一条数据 in的主要使用场景 比较多值 但是大多的时候 使用 min(xx),max(xx) 即可比较 所以大多时候不适用in 但是如果需要拿着外面的每一条数据 和in中的每一条数据 进行比对 就需要使用in

​					 not in：不等于列表中的任意一个值 类似查询两者之间非交集的部分

​	分类：

​		按子查询的位置进行

​			select后面:

​				仅支持标量子查询

​			from后面:

​				支持表子查询（在from后面加上的子查询 就是把该查询出来的结果 当做一个新表 在该新表上进行某些操作 而且必须取别名 该别名即 临时表的 表名）

​			where having 后面: (重点)

​				标量子查询 (重点)

​				列子查询	(重点)

​				行子查询

​			exists(是否存在 如果存在值 返回1 如果没有值 返回0)后面:

​				表子查询

​	

​		按结果集的行列数不同：

​			标量子查询（结果集只有一行一列）

​			列子查询（结果集只有一列多行）

​			行子查询（结果集只有多行一列）

​			表子查询（结果集一般为多行多列(以上的都支持)）

​				

​		案例：

​			select 查询字段

​			from 查询的表

​			where xxx字段 = (

​				select 查询字段

​				from 查询的表

​			);

​				类似如上这种即为 子查询 这种子查询可以为多个 只要有多个条件 添加多个 and即可

​			





**分页查询**：

​	语法：

​		select 查询字段

​		from 表

​		【  where 筛选条件

​		group by 分组

​		having 结果集筛选

​		order by 排序  】

​		limit 从那个索引开始，查询几条数据



关键字：limit 第一个参数 从那个索引开始

​			第二个参数 查询几条数据

​	案例：查询第11条到20条的数据

​		select 查询字段 from 表 limit 10，15

​			---索引从10开始 显示15条数据

​	案例二：查询有奖金的员工信息，并显示工资较高前10名员工信息

​		select * from 员工表 where 奖金 is not null order by desc limit 0，10

​		**索引可以理解为 (page-1)*size**

​	即select 查询字段 from 表 limit (page-1)*size,size







查询总结：

​	**外连接：**

​	流程：select 查询列表					7

​			from 表						1

​			【join type】 表2				2

​			on 连接条件					3

​			where 筛选条件				4

​			group by 分组				5

​			having 分组后筛选			6

​			order by 排序				8

​			limit 索引，显示的数据数量		9

​	**内连接：**

​	流程：

​		select 查询列表

​		from 表

​		inner join 表2

​		on 连接条件

​		where 筛选条件

​		group by 分组

​		having 分组后筛选

​		order by 排序

​		limit 索引，查询的数量

​	**交叉连接：**

​	流程：

​		select 查询列表

​		from 表1 

​		cross join 表2

​	特点：类似笛卡尔乘积





**联合查询**：

​	union ： 把两个查询结合起来

比如有一条sql语句

​	select xxx from xxx where xxx = xxx or xxx = xxx

使用union可以这样写

​	select xxx from where xxx = xxx

​	union

​	select xxx from where xxx = xxx



应用场景: 当两个表之间没有直接关联的时候使用,但查询的信息一致时

​	注意事项：

​		1）多条查询语句的时候 查询列数 为一致(select 查询列数)

​		2）多条查询语句的时候，每一列的类型和顺序最好一致

​		3）使用union 默认去重 使用union all 不会去重

select username from user where username like '%三%'
union
select phone from user1 where phone like '%2%'



如上这种 最终查询出来的结果为

字段 username

​	张三

​	1222341





##### Mysql 中的插入 insert

​	语法：

​		1) insert into			（推荐使用）

​			表(要插入的字段)

​			value(需要插入的值)



​		2）insert into

​			表 set xxx字段 = xxx值 ， xxx字段 = xxx值



​		两者之间区别：

​			第一种方式插入 支持多行插入 同时也支持子查询插入

​			案例：

​				多行插入

​				insert into 表(字段) value (值)，(值)，(值)；



​				子查询插入

​				insert into 表(字段) 

​				select  查询字段 from 表

​					---查询出来的数据 会当做值 插入表中

​				注意事项：查询出来的数据 类型要和插入的类型一致 否则报错。







##### Mysql 中的更新Update

​	单表插入：

​		语法：update 表 set 列= 值 ， 列 = 值 [where xxx = xxx]



​	多表插入

​		语法：update 表1 【连接类型 inner，left，right】join 表2 on 连接条件 set 列 = 值，列= 值 【where 筛选条件】



##### Mysql 中删除 delete

​	单表删除：

​		语法：delete from 表 where 筛选



​	多表删除：

​		语法：delete 字段 from 表1 【inner，left，right】join 表2 on 连接条件 where 筛选条件



##### Mysql中的一些数据类型

​	1）整型

​	



​	2）浮点型

​		float  ， double：

​			注意事项：如果想明确小数点后几位 ，或者该值最长为几位 需要这样写

​			float(M,D)	---M 代表最大长度 包括小数点后

​							D：代表小数点后几位

​			double(M,D)	---和float 一样



​		decimal ：它与上两者的区别 精度更加准确 其中一样可以使用M ， D 与上含义 一致



3）字符型：

​		**较短的字符型**：char()	--- 0 ~ 255

​						varchar()	--- 0 ~ 60000多

​			char 和 varchar的区别

​				1）占用空间

​				char 如果设置了长度为5 但是存入的值长度并没有5 char会填充空格满足长度5 占用空间较大

​				varchar 如果长度设置为5 但是存入的值并没有5 会自动减少长度 不会自动填充 占用空间叫小

​				2）性能

​					char 稍大与 varchar

​			使用场景：如果能明确字符长度 且一定为此 如性别 只有男和女的时候 使用char 如果字符长度变化比较大 使用varchar

​		

​		较长的字符型：text ， blob(更大)



4）枚举型

​		Enum：比如 create table test_enum( test enum('a','b'))

​			但是该枚举型 在插入的时候 只能插入其中一个

​			insert into test_enum set test = 'a';

​			不能插入多个



​		set：功能和上类似 但是在插入的时候能插入多个

​		create table test_set( test set('a','b'))

​			在inset into test_set set test = 'a,b' 这种方式进行插入多个



5）日期类型：

​	data 只保存日期

​	time 只保存时间

​	year 只保存年

​	

​	datatime 保存日期+时间

​	timestamp 保存日期 + 时间 （会根据所在时区进行保存）





##### Mysql 中的一些约束

​	not null ：该字段不能为空

​	default：设置该字段的默认值

​	primary key：主键

​	unique：唯一





##### Mysql中的存储过程（重点）

​	1）存储过程

​		语法：

​			create procedure 存储过程名（参数列表）

​			begin

​				存储过程体（一组合法的sql语句）

​			end

​		

​		注意：

​			1：参数列表 包含三部分

​				参数模式 ， 参数名 ， 参数类型

​					举例：（结合下参数模式看）

​						in xxx char(10)

​			---参数模式：in ， out  ，inout

​				in：该参数可以作为输入（就是java中的形参）

​				out：该参数可以作为输出（java中的return）

​				inout：即可输入也可输出（即需要传入值，也可返回值）

​			2：如果存储过程中只有一句话 begin end 可以省略

​				存储过程中每条sql 语句的结尾要求必须加分号 '.'

​				存储过程的结尾需要加上结束符号 (任意符号) （在end 后面 加上）但是在调用的时候 需要同样需要加上 当时设置的符号

​			结束标记 案例：

​				---创建存储的时候 的使用

​				create procedure 存储过程名（参数列表）

​					begin

​						存储过程体（一组合法的sql语句）

​					end $

​				--- 调用存储过程的使用

​				call 存储名(实参列表) $

​		

​		2）调用存储过程

​			call 存储过程名(实参列表)

#### Mysql面试基础:

​	1）开启Mysql服务：

​			service mysqld start

​		关闭Mysql服务

​			service mysqld stop



​	2）为Mysql设置密码 and 修改密码

​		修改密码：

​			mysql admin -u root -p 12345 password 'xxx'

​		这里的 password 后面跟着为修改密码

​		修改密码之SQL 方式：

​			update mysql.user set password = password(xxx) where user='root' and host='localhost';



​	3）登录Mysql

​		mysql -u root -p 12345



​	4）查看当前Mysql字符集

​		show variables like '%charac%'



​	5）查看当前用户

​		select user();



​	6）查看某数据库中有多少表

​		use 某数据库名;

​		show tables;

​			第一个为进入该数据库



​	7）创建一个数据库 并设置编码格式

​		create database 数据库名 default character set 编码格式;



​	8）创建用户 并赋予对应权限 并设置可以管理的数据库

​		grant select,update,delect,insert,alter on 数据库名.* to 用户名@'localhost' identified by '12345';

​		注意事项：数据库名后面更的表名 代表该用户能操作的表 .*代表所有



​	9）查看用户的权限

​		show grants for 用户名@'localhost';



​	10）查看当前数据库中有哪些用户

​		select user,host from mysql.user;



​	11）在某表字段前在加一个字段

​		alert table 表名 add 要添加的字段名 字段类型 after 被往前插入的字段名

​		--- 在某表字段后加上一个字段

​		alert table 表名 add 字段名 字段类型 after 被插入的字段名



​	12）把某个字段设置为主键

​		alter table 表名 add primary key(字段名);



​	13）在某字段创建普通索引

​		alter table 表名 add index 索引名称(字段名);

​		或者使用

​		create index 索引名称 on 表名(字段名);



​	14）在某个字段上对前几个字符创建普通索引

​		create index 索引名称 on test(字段名(前几个字符));



​	15）查看创建的索引及索引类型等信息

​		show index from 表名



​	16）删除字段的索引

​		alter table 表名 drop index 索引名称;

​	

​		多列索引：
​		alter table zje add index indexes(math,english)；    

​		//在zje表中创建一个 为名 indexes 的多列索引，包含 math，english字段。



​	17）组件字段之间的联合索引

​		create index 索引名称 on 表名(字段名称,字段名称);

​		如果只想为字段 前几个字符创建联合索引：

​		create index 索引名称 on 表名(字段名称(前几个字符),字段名称(前几个字符));

​	**注意事项：**

​		使用联合索引的时候 如 create index 索引名称 on 表名(a,b,c)

​		使用的时候 比如在where 中

​		where a = xxx;	--- 使用了 a 索引

​		where a = xxx ，b = xxx;	---使用了 ab索引

​		where a = xxx ， b = xxx , c = xxx;	---使用了abc索引

​		where a = xxx , c = xxx;	---没有使用索引

​		where b = xxx, c = xxx;	---没有使用索引

​		为什么会出现这种情况 因为 在多列同时创建索引后，需要满足左前缀原则，才用到索引



​	18）收回用户的某些权限

​		revoke select on 数据库名.* from 用户名称@'localhost';

​			这里收回为 select 查询权限



​	19）删除某用户

​		drop user 用户名称@'localhost';



​	20）使用admin 关闭数据库

​		mysql admin -u root -p 12345 shutdown



​	21）mysql 密码忘记 如何找回

​		\# pkill mysql               #先关闭mysql服务

​		\#使用--skip-grant-tables启动mysql，忽略授权登陆验证

​		\# mysqld_safe --defaults-file=/etc/my.cnf --skip-grant-tables &

​		\# mysql                #此时再登陆，已经不需要密了

​		mysql> update mysql.user set password=password('abc123') where user='root' and host="localhost";            \#设置新的密码

​		mysql> flush privileges;

​		\# mysql -uroot -pabc123     #再次用新设置的密码登陆即可



​		22）关系型数据库特点

​			概述：支持关系模型的数据库

​			特点：事务的一致性。

​			优点：

​				容易理解，易于维护，支持sql

​			缺点：

​				1）高并发读写需求支持不好

​					并发量很高的时候 对于传统型数据库来说 硬盘i/o 是一个很大的瓶颈

​				2）海量数据的高效读写的查询效率非常低

​				3）固定的表结构



​		23）非关系型数据库

​			代表MongoDB ， Redis 

​			MongoDB：

​				概述：数据量巨大的时候 查询会比其他数据库明显快很多，自带优秀的分布式文件系统GridFS。

​				优点：高性能 易部署 易使用

​					自动处理碎片，以支持云计算层次的扩展性

​				应用场景：

​					网站数据：mongoDB很适合实时的插入，更新与查询。

​					缓存：作为信息基础设施的缓存层。

​			Redis:

​				概述：把内存作为缓存的非关系型数据库，性能极高。

​				特点：所有操作都具备原子性。

​				应用场景：少量的数据存储，高速读写访问.



​		24）sql语句分类 以及对应代表性的关键字

​			1）DDL --- 数据库定义语言

​				create alter drop 管理基础数据库 如:库，表

​			2）DCL --- 数据库控制语言

​				grant revoke commit rollback 用户授权，权限回收，数据提交回滚等

​			3）DML --- 数据库操作语言

​				select insert delete update 针对数据库里的表 记录等.



​		25）char() 和 varchar() 的差别

​			使用char() 固定长度为4 存储时，如果字符数不够4位 后面会自动加上空格存入数据库中

​			使用varchar() 如果定义了长度为4 但是存入的数据库中没有4位 不会补上空格



​		26）授权某用户从 xx ip 访问数据库

​			grant all on \*.\* 用户名@'ip地址' identified by '密码';



​		27）mysql多实例概述

​			mysql多实例即在一台服务器上开启多个端口，运行多个服务进程，这些mysql服务进程通过不同的socket来监听不同的数据端口。进而互不干涉的提供各自的服务。



​		28）如何加强mysql安全

​			1：避免直接从互联网直接访问mysql 确保指定主机才拥有访问权限

​			2：定期备份数据库

​			3：禁用或限制远程访问

​				在my.cnf中设置 bind-address指定ip

​			4：移除test数据库(默认匿名用户可以访问test数据库)

​			5：禁用local infile

​				在my.cnf中的 [mysqld] 下添加 set-varable=local-infile=0

​			6：移除匿名的账号 和 废弃的账号

​			7：限制mysql中用户的权限

​			8：移除 或 禁用 .mysql_history文件

​				\# cat ~/.mysql_history

​				\# export MYSQL_HISTFILE=/dev/null 



​		29）mysql sleep 线程过多解决方案

​			1：修改my.cnf文件中的wait_timeout的值，修改 wait_timeout = 28800 这个值

​			2：mysql> show processlist\G

​				\# mysqladmin -uroot -p123456 processlist

​				mysql> set global wait_timeout=100;

​				mysql> show global variables like "wait_timeout";



​		30）主库 和 从库之间的设置

​			概述：即主库中的数据 会同步备份到从库中

​			配置步骤：

​				1：主库开启binlog日志功能

​				2：全备数据库，记录好binlog文件和相应的位置

​				3：从库上配置和主库的连接信息

​				4：讲全备数据库导入从库

​				5：从库启动slave

​				6：在从库上查看同步状态，确定是否同步成功。



​			31）如何开启从库的binlog功能

​				在my.cnf文件中写入log-bin=mysql-bin

​			

​			32）binlog工作模式和特点与选择

​				工作模式：

​					1：row level 行级模式

​						优点：记录数据详细 主从一致

​						缺点：暂用磁盘空间大，降低磁盘性能

​					2：statement level模式（默认）

​						优点：记录数据简单 内容少 节约了i/o 提高了性能

​						缺点：导致主从不一致

​					3：mixED混合模式

​						优点：结合上两者优点 会根据执行的每一条sql语句来区分对待记录的日志形式。对于函数，存储过程，触发器会自动使用 row level行级模式.



​			33）mysql 实现双向互为主从 和该用法场景

​				两台数据库都开启binlog功能，相互为主从配置

​				双主的主要实现方式有两种：

​					1：让表id自增 主1写1,3,5 主2写2,4,6

​					2：不让表id自增，然后通过web程序去seq服务器取id，写入双主。

​				场景：一般为高并发使用 需慎用...



​			34）mysql 如何实现 级联同步 并说明应用场景

​				级联同步：为 每一个mysql 服务器 数据同步 

​				方法：把主服务器 设置为从服务器 把从服务器设置为主服务器 这样主服务器可以读取 从服务器的binlog日志 并进行写入操作。



​			35）主从服务器故障如何解决

​				解决办法1：

​					登陆从库上操作：

​					1：stop slave 临时停止同步开关

​					2：set global sql_slave_skip_counter=1，将同步指针向下移动一个，也可以多个，如果多次不同步，可以重复操作。

​					3：start slave，重启主从复制开关

​				解决办法2：

​					在my.cnf配置文件中加入参数

​					slave-skip-errors=1032,1062,1007



​				36）如何监控主从复制是否故障

​					查看slave(同步端) 的io 和 进程是否正常 同步延迟是否小于1分钟

​					mysql> show slave status\G

​					Slave_IO_Running: Yes

​					Slave_SQL_Running: Yes

​					Seconds_Behind_Master: 0



​				37）主从库之从库宕机 如何手动恢复

​					处理方法：重做slave(同步)

​					1：停止salve

​					2：读取备份信息

​					3：配置master.info信息

​					4：启动salve

​					5：检查从库状态



​				38）主从库之主库宕机 如何手动恢复

​					主库宕机分为数据库宕机和服务器宕机2种，不管哪种都要进行主从切换。

​					1.登陆从库检查IO线程和SQL线程状态show processlist\G，确认SQL线程已读完所有relay-log

​					2.登陆所有从库检查master.info信息，查看哪个从库的binlog文件和位置是最新的，选择最新的从库切换为主库（或利用半同步功能，直接选择做了实时同步的从库为主库）

​					3.如果主库只是数据库宕了，服务器还在运行，则可以把binlog拉取到提升为主库的从库应用。

​					4.登陆要切换为主库的从库，进行切换操作。

 						stop slave;reset master;quit

​					5.进入要切换的从库数据目录，删除master.info和relay-log.info文件，并检查授权表，read_only等参数

​					6.修改my.cnf配置文件，开启binlog，注释从库参数

​						log-bin=/data/3307/mysql-bin

​						\#log-slave-updates

​						\#read-only

​							1：对同步用户进行提权，保证权限与主库用户权限一样

​							2：重启数据库提生为主库

​							3：其他从库操作

​								（1）       检查运行环境和用户

​								（2）       停止从库，修改master信息

​								（3）       启动从库同步，检查同步状态

​						1：修改web程序的连接配置，从原主库指向新主库

​						2：维护损坏的主库，完成后作为从库使用，或切换回来

​						3：如果主库没有宕机，只是想按计划切换一下主库，就非常简单

（1）       主库锁表

（2）       登陆所有从库检查同步状态，查看是否完成同步。

（3）       其他按上面步骤进行切换



​				39）mysql 出现复制延迟有哪些原因 如何解决

​					1：一个主库的从库 过多 一般从库为3~5个最佳

​					2：从库硬件过差 升级硬件

​					3：sql语句过多 优化sql语句

​					4：主库并发压力过大 传输到从库力不从心 优化

​					5：网络延迟



​				40）mysql集群架构可行备份方案

​					1：利用mysqldump 做定时备份 根据情况全库备份

​					2：利用 rsync + inotify 对主库binlog做实时备份



​				41）mysql 备份的名词

​					全备：全部库数据备份

​					增备：一次性备份所有数据后，在进行增量备份

​					冷备：停止mysql，进行备份

​					温备：只支持读不支持写的情况下 进行备份

​					热备：服务器运行的状态下 进行备份



​				42）重点：mysql 的 sql 语句进行优化

​					1：在表中使用索引，优先考虑where ， group by等字段

的情况 

​					2：尽量避免select * 查询无用字段会降低查询效率

​					3：尽量避免使用 in  和 not in 这个会导致数据库放弃索引进行全表扫描

​					4：尽量避免使用 or 会导致数据库放弃索引进行全表扫描

​					5：尽量避免在字段开头使用模糊查询，会导致数据库放弃索引进行全表扫描

​					6：尽量避免 where 子句中使用 != 或 <> 操作符，会放弃索引查询全表

​					7：尽量避免 where 子句中对字段 进行null 值判断，会放弃索引进行全表 ， 可以在出现null值的地方 加上0 用0表示null

​					8：大多时候 使用 exists 代替 in 是一个好选择

​					9：最佳用 where 子句 代替 having 子句 ，因为 having是在查询后的 结果再次进行筛选

​					

-----				数据库结构优化：

​					1）拆分表 ：分区将数据在物理上分隔开，不同分区的数据可以制定保存在处于不同磁盘上的数据文件里。这样，当对这个表进行查询时，只需要在表分区中进行扫描，而不必进行全表扫描，明显缩短了查询时间，另外处于不同磁盘的分区也将对这个表的数据传输分散在不同的磁盘I/O，一个精心设置的分区可以将数据传输对磁盘I/O竞争均匀地分散开。对数据量大的时时表可采取此方法。可按月自动建表分区。



​					2）拆分其实又分垂直拆分和水平拆分： 

​							案例： 简单购物系统暂设涉及如下表： 1.产品表（数据量10w，稳定） 2.订单表（数据量200w，且有增长趋势） 3.用户表 （数据量100w，且有增长趋势） 以[mysql](http://www.2cto.com/database/MySQL/)为例讲述下水平拆分和垂直拆分，mysql能容忍的数量级在百万静态数据可以到千万 

​				**垂直拆分：**解决问题：表与表之间的io竞争 不解决问题：单表中数据量增长出现的压力 

​				方案： 把产品表和用户表放到一个server上 订单表单独放到一个server上 

​				**水平拆分：** 解决问题：单表中数据量增长出现的压力 不解决问题：表与表之间的io争夺

​				方案： 用户表通过性别拆分为男用户表和女用户表 订单表通过已完成和完成中拆分为已完成订单和未完成订单 产品表 未完成订单放一个server上 已完成订单表盒男用户表放一个server上 女用户表放一个server上(女的爱购物 哈哈)



​				43）企业生产mysql优化

​					硬件优化

​					软件优化

​						my.cnf中参数优化



​				44）mysql 高可用方案有哪些，特点如何

​					主从复制+读写分离(代码分离)

​						优点：成本低，实现易，维护方便

​						缺点：master(主) 出现问题 不能自动到salve上，需要进行人工修复

​					mysql cluster

​						优点：安全性高，稳定性高，可以在线增加节点

​						缺点：架构复杂 收费 引擎只能用ndb 不支持外建

​					





#### Mysql 面试题精选：

​		1）网站打开过慢，如果为数据库导致，如何排查并解决

​			1：查看数据库中sql语句执行时长

​				show processlist

​			2：使用 explain 查看消耗时长过大的sql语句是否走了索引

​			3：对sql 进行优化 建立索引



​		2）主从一致性如何校验

​			使用 checksum ， mysqldiff ，pt-table-checksum等



​		3）如何监控Mysql 增删改查次数

​			show global status where variable_name in('com_select','com_insert'.'com_delete','com_update');



​		4）Mysql 索引种类

​			普通索引：最基本的索引 没有任何限制

​			唯一索引：索引值唯一 ， 允许为空

​			主键索引：表中唯一索引，不能为空

​			组合索引：多个字段 建立到一个索引中



​		5）一般实现读写分离的方式

​			update insert delete alter走主库 

​			select 走从库



​		6）如何监控数据库

​			开源软件：zabbix ， nagios ， Lepus(天兔)



​		7）如果发现cpu 或者io 压力很大 怎么定位问题

​	   		1、首先我会用top命令和iostat命令，定位是什么进程在占用cpu和磁盘io； 
   			2、如果是mysql的问题，我会登录到数据库，通过show full processlist命令，看现在数据库在执行什么sql语句，是否有语句长时间执行使数据库卡住；
   			3、执行show engine innodb status\G命令，查看数据库是否有锁资源争用；
   			4、查看mysql慢查询日志，看是否有慢sql；
  			5、找到引起数据库占用资源高的语句，进行优化，该建索引的建索引，索引不合适的删索引，或者根据情况kill掉耗费资源的sql语句等



​		8）存储过程与触发器的区别：

​			触发器与存储过程非常相似，触发器也是SQL语句集，**两者唯一的区别是触发器不能用EXECUTE语句调用，而是在用户执行Transact-SQL语句时自动触发（激活）执行。触发器是在一个修改了指定表中的数据时执行的存储过程。**通**常通过创建触发器来强制实现不同表中的逻辑相关数据的引用完整性和一致性。**由于用户不能绕过触发器，所以可以用它来强制实施复杂的业务规则，以确保数据的完整性。触发器不同于存储过程，**触发器主要是通过事件执行触发而被执行的**，而**存储过程可以通过存储过程名称名字而直接调用**。当对某一表进行诸如UPDATE、INSERT、DELETE这些操作时，SQLSERVER就会自动执行触发器所定义的SQL语句，从而确保对数据的处理必须符合这些SQL语句所定义的规则。