### Git

#### 基础命令

- git仓库初始化 
  - git init
- 存放至暂存区 
  - git add 文件名
    - 文件名
      - .   --代表全部
      - ./*.java   --代表全部的java文件
      - xxx.xx    --代表该文件
- 存放至本地仓库区
  - git commit .
    - 可选参数
      - -m "注释" ---暂存区直接提交到本地仓库区
      - -a ---提交工作区自上次connit后的变化，到仓库区
      - -v ---提交时显示所有的diff信息
- 设置仓库提交位置
  - git remote add 自定义名称 仓库提交地址(即github仓库网址)
- 仓库push到远程仓库
  - git push -u origin [master],[branch]
  - -f 强制推送
- 取回远程仓库变化，并于本地分支合并
  - git pull [remote] \[branch]
- 推送所有分支到远程仓库
  - git push [remote] --all
- 创建一个分支
  - git checkout -b 分支名称
    - -b ---代表 git branch 分支名称 + git checkout 分支名称(该命令为切换到该分支中)
- 查看git 状态
  - git status
- 下载远程仓库的所有变动
  - git fetch [remote]
- 合并分支到指定分子
  - git push 分支名(下载时的分支) 本地分支名()
- 拉取远程仓库的变化到本地
  - git pull 分支名
- 查看当前的修改的那些
  - git diff

- 显示某个远程仓库的信息
  - git remote show 仓库名称
- 显示所有的远程仓库
  - git remote -v
- 强行推送当前分支到远程仓库
  - git push 仓库地址 分支名称
- 删除分支
  - git branch -d 分支名称



#### 配置文件

- 查看全局配置
  - git config --list
- 配置全局配置
  - git config --global user.name "名称"
  - git config --global user.email "邮箱"
- 删除全局配置项
  - git config --global --edit
  - git config --gloabl --unset 名称





#### 日志查看

- git log
  - 后可以跟 -2 代表查看最新的两条信息
  - 操作：空格向下翻页，b向上翻页，q退出
- git log --pretty=oneline (以一行显示)
- git log --oneline (只显示指针之后的版本，不显示之前的)
- git reflog (显示历史版本)



#### 版本回退

- git reset --hard 标识符
  - 这里的标识符 为使用 git reflog 前面的一串标识符
- git reset --hand HEAD^
  - 一个 ^ 代表回退一个版本，回退多个，就加上多个 ^
- git reset --hand HEAD~数字
  - 数字代表回退几个版本