# NewsSub_Manage_System
- java课设报刊订阅管理系统
- 需要：装好java8、mysql
- 将kernal-utils-Dbutil中的URL、USER、PASSWORD更换为自己的数据库
- 然后执行kernal-dbs中的newSystem脚本进行数据库的初始化！

感谢16届学长做的课设，通过他的项目学到了很多:https://github.com/Haut-Stone/Java_newsSystem
# 前言
相比起C#、qt来说，Java确实不适合做GUI，用swing做课设对我来说更是一种折磨，所以此次课程设计，我听从另一个学长的建议，用较为现代的javaFX进行GUI的设计与实现，没有使用课本教材上的swing，因为之前有过开发网页和小程序的些许html跟css经验，我尝试将其融入javaFX进行设计，惊讶的发现javaFX支持css，是我理想中的现代系统界面，不同于以往的控制台程序，其页面更加现代化，切换动画效果也更加流畅。在github搜索javaFX相关资料时更是意外发现了16届学长做的课设，其代码之规范，javaFX运用之熟练都让我感受到了差距，于是我下定决心弄懂这个大工程并自己复现一份，数据库方面，由于这学期没有学数据库，自己看着学长的代码进行学习，从mysql的安装、到怎么连接数据库导入jdbc驱动的、再到数据库仓库的创建、表的创建、视图的创建、数据的插入删除修改，再通过学长的代码了解到了java的反射机制，了解到数据库视图在查询时便利的使用方法，不同类型的代码放在不同包内封装，合理的进行文档注释以便最后生成javadoc，css中各属性的修改对界面变化的影响，自己试着在系统中增加按钮阴影等，得到了很大的提升，虽然由于两周的课设一周都在准备考试，事实上只有一周时间做这个课设，还有很多遗憾的地方没有解决，还有很多功能可以添加，但是亲手学习并完成了到目前为止最大的一个项目，还是很有成就感的一件事！在代码文件夹中有生成的javadoc，更多的细节可以在里面看到。
# 说明
本系统是一个报刊订阅管理系统，系统内分为管理员和普通用户两个角色
管理员可实现的操作为：报刊管理、用户管理、管理员管理、订单管理、查询与统计。
普通用户可实现的操作为：报刊订阅、个人信息修改、查看订阅情况。
界面总体采用JavaFX Scene Builder进行图形化设计，美观方便，异常处理也十分完善，对于管理员、普通用户操作成功与否都有系统提示弹窗。
本系统实现主要功能的结构图如图
![图片10](https://user-images.githubusercontent.com/43498495/141929585-007fdaad-a240-488a-930c-fb4f04613eb3.png)
# 界面展示
- 不要在意这个辣眼睛的配色了啦（逃
- ![图片1](https://user-images.githubusercontent.com/43498495/141929433-72f490ce-a29f-419d-919d-6649a3b53305.png)
![图片2](https://user-images.githubusercontent.com/43498495/141929434-49338a31-d9c9-4ca9-93f3-62baaf22d65a.png)
![图片3](https://user-images.githubusercontent.com/43498495/141929436-85b7ea6f-c635-4021-8120-aa5cf6d07337.png)
![图片4](https://user-images.githubusercontent.com/43498495/141929438-a757c689-b2b3-4478-9fee-c14b581d9de5.png)
![图片5](https://user-images.githubusercontent.com/43498495/141929445-cfc917ff-1a75-456d-96ad-bf4081023cfc.png)
![图片6](https://user-images.githubusercontent.com/43498495/141929446-2b49eb65-8b39-4c2c-8132-1dc8d6d2277e.png)
![图片7](https://user-images.githubusercontent.com/43498495/141929427-8de8c537-4e2f-49eb-9b57-6ba732da4d96.png)
![图片8](https://user-images.githubusercontent.com/43498495/141929430-792c8b81-b748-4591-9282-226645806269.png)
# javadoc
![image](https://user-images.githubusercontent.com/43498495/141786387-a7231fc7-794e-4ce4-bad4-2b9823c964f3.png)


