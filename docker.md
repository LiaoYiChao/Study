#### Docker

​	1）docker 安装

​		1：uname -r		---查看当前内核版本 至少需要3.10版本以上才能使用docker

​		2：yum install docker		---安装docker

​			输入 y 代表确认下载

​		3：docker -v		---查询docker版本

​		4：systemctl start docker		---启动docker

​		5：systemctl stop docker		---停止docker

​		6：systemctl status docker	---重启docker

​		7：systemctl enable docker		---设置为开机自启

​	2）docker 镜像操作

​		1：查看镜像有哪些版本

​			docker search xxx

​				--- 如 docker search mysql  会搜索出docker 支持下载的mysql版本



​		2：下载镜像

​			docker pull xxx:xxx

​				---如果不加版本号 默认下载lates版本

​				docker pull mysql ---默认下载lates版本

​				docker pull mysql:5.7	---下载指定版本5.7



​		3：查看下载的镜像

​			docker images



​		3：删除镜像

​			docker rml image-镜像id



​		4：查看正在运行的容器

​			docker ps



​		5：查看没有运行的容器

​			docker pa -a



​		6：运行容器

​			docker run --name xxx名称 -d -p xxx端口：xxx端口 xxx容器id

​		重点：挂在本机和容器之间交互的 桥梁

​			docker run -d -v /本机的目录地址(如果不存在自行会新建) : /容器中的挂在目录(如果不存在 一样会新建)    镜像名

​			赋予权限的方式：

​			docker run -d -v /本机的目录地址 : /容器中的挂在目录: ro(代表只读)  镜像名

​	

​		7：进入到容器中

​			docker attach 容器id



​		8：查看容器相关信息 为json串格式

​			docker inspect 容器id



​		9：构建一个容器镜像

​			docker build 

​				-f  /xxx目录  /下的xxx(dockerfile)

​				-t  自己取的名称	（建议使用 xxx名称/xxx镜像名称）



​		10：容器继承

​			容器继承 会完全和上一个容器的配置一致，且中间互通

​			docker  run  -it  --name  自定义名称  

​				--volumes-from  需要继承的容器名称或者id

​			[自定义一个镜像名称]

​		

​		11：已停止的镜像容器重新运行 或者运行后进行某些操作

​			docker exec 镜像id

​				---如果该镜像为centos

​					--如果在创建该镜像的时候 在dockerfile最后有CMD [/bin/bash]  使用 docker exec 镜像id 会直接运行并进入镜像内

​					--如果没有 使用 docker exec /bin/bash 是一样的

​					--可以使用其他命令 比如 docker exec ls -a 查询该centos该目录下的目录



##### DockerFile的指令：	

​	案例：

​		\# file  DockerFiletest

​		FORM redis:版本号(可以不写)

​		MAINTAINER 作者信息

​		RUN redis-server(运行)

​		RUN (可以有多个运行)

​		EXPOSE 本机端口号:容器端口号(可以为多个)

​		CMD 



​		FORM	--- 基础镜像 ， 当前新的镜像是基于那个镜像的

​		MAINTAINER		--- 镜像的作者信息 or 邮箱地址

​		ENV		---变量 可以被引用  ENV  名称 内容

​			比如：ENV  mycentos  /tmp

​					引用使用  $mycentos

​		RUN	--- 容器构建的时候 需要运行的命令

​		EXPOSE		--- 当前容器对外暴露的端口

​		WORKDIR	--- 指定在创建容器后，终端默认登录的进来目录（即如果有一个centos镜像 在其中设置了该属性 然后在进入容器的时候 默认目录会在此）

​		ADD	--- 拷贝 + 解压缩（将宿主机目录下的文件拷贝进镜像且ADD命令会自动处理URL和解压tar压缩包）

​		COPY	--- 拷贝 （类似ADD，将从构建上下文目录中 <源路径>的文件 / 目录复制到新的一层的镜像内的 <目标路径> 位置）

​		VOLUME	---容器数据卷，用于数据保存和持久化工作





​		CMD	--- 指定一个容器启动时 要运行的命令（但是DockeFIle中可以有多个CMD指定，但是只有最后一个生效，CMD会被docker run 之后的参数替换）

​		ENTRYPOINT		--- 指定一个容器启动时需要运行的命令（和CMD命令类似 但是使用该命令是追加 而不是替换）

​		**注意事项：** 两者区别：CMD命令在 docker run 命令后会被覆盖，但是使用 entrypoint 在 docker run 后面的参数 会以添加的形式添加到执行的命令中 而不是以覆盖的方式



​		ONBUILD	--- 当构建一个被继承的DockFile时运行命令，父镜像在被子继承后父镜像的onbuild会被触发（简单说父镜像在被子镜像继承前做一点收尾工作）

​		









#### Docker的持久化（数据卷）

​	特点：1）数据卷可以在容器之间共享或重用数据

​			2）卷中的修改可以直接生效

​			3）数据卷中的更改不会包含在镜像的更新中

​			4）数据卷的生命周期一直持续到没有容器使用它为止





#### Docker 上传阿里云

​	1）把自身的镜像确认一个版本号 也不可已确认

​		docker  commit  -a  自定义名称(作者名)  -m  "该镜像注释"  镜像id  镜像名称(加上版本号最佳)

​	2）登录阿里云容器管理中心 创建本地仓库

​	3）根据阿里云提示 进行镜像push







#### docker其他命令

​		进入镜像的centos中

​			docker run -it 镜像id /bin/bash

​		重新进入centos中

​			docker exec -ti 容器id /bin/bash



​		docker 已停止的镜像批量清除

​			docker rm -f $(docker ps -a -q)



​		查看docker 镜像安装步骤

​			docker history 镜像id





​		在容器卷创建的时候 如果没有写权限 使用run命令的时候

​			docker run -d -v xxxx:xxxx **--privileged=true** 镜像id

​		使用 --privileged=true 可以设置为拥有读写权限

#### 其余知识

​	vim中模式的切换

​		正常模式：按两下esc

​		插入模式：按a键即可

​		命令模式：在正常模式下 :

​			wq! 保存+退出

​			q! 不保存退出

​			ZZ 退出