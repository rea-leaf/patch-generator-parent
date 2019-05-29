#  **本项目说明
# 参考 https://gitee.com/hackempire/patch-generator-desk 进行了二次修改 
### 修改内容

- 1 : 项目结构调整
- 2 : 路径修正bug
- 3 : 配置保存于读取优化
- 4 : 增量包文件夹名称修改为添加版本号+日期

#  ----------------------------- 一下为原项目说明-------------------------------
#  **patch-generator-desk** 【技术QQ群：456742016】

项目增量补丁包神器：全自动web增量打包发版，支持git/svn，支持多模块项目。

### empire

- author : aaron
- [项目地址：https://gitee.com/hackempire/patch-generator-desk](https://gitee.com/hackempire/patch-generator-desk)
- [empire团队地址：https://gitee.com/organizations/hackempire/](https://gitee.com/organizations/hackempire/)
- 【宣言】：打造帝国最强无敌武士套装！来吧！加入帝国军团，一起征服这个物欲横流的世界！ 

### 软件启动脚本和.exe文件下载

- ###          方式一：附件中下载！[立即下载](https://gitee.com/hackempire/patch-generator-desk/attach_files)
- ###          方式二：技术QQ群中下载！
### 软件介绍

- 	本软件是empire团队打造的一款用于app、web等等项目增量打包的全自动发版部署工具。支持exe运行，或者点击run.sh/startup.sh/jar运行。

### 最新版本

- 	3.2.0

### 功能介绍：

	1.GIT服务器增量打包;
	2.GIT日志增量打包;
	3.SVN服务器增量打包;
	4.SVN日志增打包;
	5.pom依赖分析（增量|全量）;

### 开发工具
- 	NetBeans IDE8.2、jdk1.8、maven3.5、HBuilder、GIT

### 历史轨迹
-     1.修复控制台无法显示操作日志的bug-----2018-05-01 18:48
-     2.修复jvm路径启动bug,无须安装jdk-----2018-05-09 18:20 
-     3.新增pom依赖分析功能---------------2018-08-25 23:00
-     4.异步pom分析功能优化解决顿卡问题-----2018-08-25 23:00
-     5.修复svn服务器增量打包时,排除版本不生效的bug[原因：该字段在获取时选错了输入字段]-----2019-01-15 18:30

### 使用教程：

	通用配置部分
		 1.项目名称：必须填写本地项目的文件名；
		 2.项目路径：必须填写项目的本地路径；
		 3.输出目录：必须填写增量包的输出路径；
		 4.项目类型：必须选择项目类型是单模块项目还是多模块项目；
		 5.sourceMapper表：sourceDir：源码目录,targetDir：.class目录、源文件目录；patchDir：打包后放置的目录
		 6.配置按钮：点击导入项目打包的配置（配置必须以.xml结尾）
		 7.保存按钮：点击保存当前项目打包的配置（配置必须以.xml结尾）

	GIT服务器增量私有配置部分
		 1.GIT本地URL路径：对应项目在本地的.git目录；例如D:\Users\Administrato\patch\git\.git
		 2.GIT范围版本：要打包的GIT提交版本范围；例如：757212d，544515f

	SVN服务器增量私有配置部分
		 1.SVN URL路径：对应项目在SVN服务器的地址；例如https://xxxxx/svn/scrm/tags/ump20170420_chery_pc
		 2.SVN范围版本：要打包的SVN提交版本范围；例如：14431，14439
		 3.修正路径：从svn服务器获取的增量路径中可能包含多余的在本地不存在的目录；例
                   如：/tags/ump20170420_chery_pc/src/main/webapp/WEB-INF/views/cherrywcc/wccchrescue/list.jspx
		   可以设置该值为 /tags:将其替换为空,/tags为需要替换的路径,:后面的空表示将/tags去掉;还可以将其设
                   置/tags/ump20170420_chery_pc:ump ,表示本地项目文件名为ump
		 4.SVN账户：svn服务器的账户
		 5.SVN密码：svn服务器的密码
		 6.排除版本：svn版本范围内需要排除掉的不用发版的版本号,多个版本以逗号分隔；

	GIT日志增量私有配置部分
		 1.GIT日志路径：对应的git提交日志存放的本地路径；
		   该路径或得方式可以通过右键点击项目-Team-show in history-视图中会显示提交的版本，选择需要发布的某个版本拷贝
                   右下角的本次版本的提交路径存入GIT提交日志即可。
 
	SVN日志增量私有配置部分	  
		 1.SVN日志路径：对应SVN提交日志存放的本地路径；
		   日志记录方式，提交SVN后控制台会输出提交日志，将其拷贝到.txt结尾的日志文件中保存起来，用于发版
		   
	pom依赖分析私有配置部分
		 1.分析名称：本次操作记录一个名称，方便以后查阅历史操作记录
		 2.项目名词：分析的项目名称，方便以后查阅历史操作记录
		 3.pom_new路径：最新的pom文件路径
		 4.pom_old路径：要进行分析对比的老版本pom文件路径
		 5.输出目录：分析出来的依赖包输出路径
		 6.分析类型：a.完全分析，只分别分析出两个pom对应的所有依赖分别保存；b.差异分析，除了完全分析的功能，还增加了差异依赖的            
		   分析
		 7.依赖级别：pom.xml文件中设置的scope字段

```
GIT日志内容实例：(注意日志需顶格记录)
patch-generator/src/main/java/com/empire/patch/generator/GeneratePatchExecutor.java
patch-generator/src/main/java/com/empire/patch/generator/GitPatchGenerator.java
```
```
SVN日志内容实例：(注意日志需顶格记录)
commit -m "1.服务点评bug修复2.道路救援bug修复3.全屏报表(二阶)bug修复"      
    Sending        D:/SpringRooWorkSpace/ump20170420_chery_pc/src/main/webapp/WEB-INF/views/cheryreport/RegAndAuthResult.jsp
    Transmitting file data ...
    Committed revision 14471.
```
###注意事项
1. 增量打包工具是根据本地项目代码生成的class文件进行打包，并不会自动从项目所在版本管理软件下载项目并maven编译后打包.
1. 想要打包的代码的截止版本并非当前最新版本时，需要拉取对应需要打包的版本进行编译后在打包.否则将打包的class为最新版本的代码.
1. 项目中改动了常量类里面的某个常量，需要手动将使用该常量的类的以日志分析的方式打包进去【建议将常量类改为配置中心的方式来替换apollo（携程）/disconf（百度）/diamond（阿里）】.

### **相关项目** 

1.  [emsite分布式开源系统生态圈](https://gitee.com/hackempire/emsite-parent)
1.  [分布式服务框架Dubbo](https://github.com/apache/incubator-dubbo)
1.  [Emsite-patch-generator](https://gitee.com/hackempire/patch-generator-parent)
1.  [Emsite-patch-desk](https://gitee.com/hackempire/patch-generator-desk)
### **软件效果图** 
![增量打包神器桌面版](https://gitee.com/uploads/images/2018/0501/203654_cbe498db_302505.png "_BJ8NS6R@(3MWDPSPJ6MQ5U.png")
![增量打包神器桌面版](https://images.gitee.com/uploads/images/2018/0828/223405_c0b5c70b_302505.png "`L9OTSH%XJ4@JSSJYX[Z`DY.png")