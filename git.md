# Git命令操作

Git命令操作

​			初始化git仓库命令：git init

​			查看文件中的内容：cat 文件名称

​			设置签名：git config key值 value值（项目级别，在当前git文件夹的下）

​		设置签名： git config --global user.name world(系统级别，当前系统都有效)

​			但是在文件中 项目级别大于系统级别 （求近原则）

​			创建一个文件：	vim good.txt

​			添加到暂存区：git add 文件名称

​			状态查看（工作区暂存区）状态查看： git status

​			从暂存区撤回：git rm --cached 文件名称

​			提交到本地文件库： git commit -m "日志" 文件名称

​			删除文件： rm 文件名称++++++++



​			查看历史版本：git log	（多屏显示 空格向下翻页 b向上翻页 q退出）

​			查看历史版本(简洁)：git log --pretty=oneline  以一行显示

​			更简洁显示历史版本：git log --oneline（只显示指针之后的版本 不显示之前的）

​			显示版本指针历史版本： git reflog

​	

​	平常使用回退命令：

​	版本回退前进：git reset --hard 标识符(一串hash值)  //使用 git reflog 之后前面一串

​	

​	知道的回退命令：

​	git reset --hand HEAD^ 	一个^代表 回退一个版本 回退多个 就加上多个^

​	git reset --hand HEAD~数字	这个数字代表回退几个版本 3代表回退3个版本



git 查看版本

​	--- git log

​	--- git log --pretty=oneline

​	--- git log --oneline

​	--- git reflog

​		注意事项：中间如果有多页操作

​			空格：向下翻页

​			b：向上翻页

​			q：退出

tail -n  +数字 +文件名称 ： 显示最后几行​

### Git上传操作

​	1）在Github网站上创建一个仓库 之后在本地某个文件夹下打开git bash here命令窗口

​	2）git clone 仓库下载网址

​	3）把需要上传的文件拖入仓库本地文件中

​	4）git all .      (  .  代表上传该文件中的所有 但是 隐藏文件 .git 中会有上传规则 那些无法上传 但也可以进行设置)

​	5）git commit  -m  "提交信息"

​	6）git push -u origin master  （如果没有进行设置 需要输入账号密码）

​	