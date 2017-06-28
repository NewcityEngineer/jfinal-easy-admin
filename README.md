#项目简介
此项目为健康平台系统，整个项目采用B/S架构，面向用户角色不同分为大众群体与管理用户。大众群体关注微信公众平台，可以接收到平台的信息推送以及使用平台的服务功能。因此将系统分为微信端和后台系统网站端，其中健康信息来源于一些养生书籍和网络爬取的数据。最终实现的目的是，为用户提供一个智能的个人健康管家。目前整个项目属于半成品。

##项目截图
![首页截图](http://xine.oss-cn-beijing.aliyuncs.com/essay/TianLiXin/20170628/1.png)

##应用的技术
- 前端技术：EasyUI、JQuery
- 后端技术：JFinal2.2、JFinal-Weixin1.7、Jsoup网页解析、IK分词器、Cron4j定时调度。
- 数据库：MySQL（编码采用Utf8mb4）

##注意
- 登录名：admin；密码：123456。
- 运行项目前，先更改项目配置文件（config.txt）的数据库配置信息和微信公众平台信息。

##技术链接
- JFinal官网 [http://www.jfinal.com](http://www.jfinal.com)
- JQuery EasyUI [http://www.jeasyui.net](http://www.jeasyui.net)
