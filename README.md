# freewebwork
项目的主要功能：管理定时任务<br>
项目中的User类是做基础测试用例使用，调试版本和配置文件。
项目构建环境jdk-1.8，tomcat 8.0，spring 4.0，mysql，配置过程中要注意jar版本，按需调节。
使用静态html配合ajax的形式渲染页面，基于[bootstrap-v3.3.7](http://www.bootcss.com/)，[jquery](https://jquery.com/)，表格使用[jqGrid](http://www.guriddo.net/demo/bootstrap/)，字体文件[fontawesome](http://fontawesome.dashgame.com/)等比较常用的插件，以使用顺手为主。
# 1. 主线配置Spring+SpringMVC+MyBatis(注意jar包版本)
[参考：SSM框架——详细整合教程（Spring+SpringMVC+MyBatis）](http://blog.csdn.net/zhshulin/article/details/37956105) 
# 2. 副线配置mybatis的代码自动生成和分页查询
2.1 [参考：eclipse下用maven插件+Mabatis-generator生成mybatis的文件](http://blog.csdn.net/donggang1992/article/details/50847484)<br>
2.2 [参考： 解决Maven中pom.xml报plugin execution not covered by lifecycle configuration ](http://blog.csdn.net/zouxucong/article/details/53786752)<br>
2.3 [参考：mybatis-generator使用Maven Plugin管理和生成代码](http://liyunpeng.iteye.com/blog/1987818)<br>
2.4 [参考：Mybatis+SpringMVC实现分页查询（附源码）](http://www.cnblogs.com/zhangtan/p/5846955.html)<br>
2.5 [参考：Mybatis分页插件-PageHelper的使用](http://blog.csdn.net/u012728960/article/details/50791343)<br>
2.6 [参考：Mybatis分页插件-PageHelper(5.0)的使用](http://blog.csdn.net/u014695188/article/details/65629225)<br>
2.7 [参考：Mybatis物理分页插件PageHelper 5.0](http://blog.csdn.net/wzyxdwll/article/details/66473466)<br>
2.8 [参考：mybatis通过map来自动处理查询条件](http://www.jianshu.com/p/e33993f328f3)<br>
2.9 [参考：MyBatis--Map实现多条件查询](http://blog.csdn.net/sinat_27115575/article/details/70144177)
# 3. 整合定时任务
[参考：Spring回顾之七 —— 和Quartz一起进行定时任务](http://veiking.iteye.com/blog/2371511)
# 4. 配置项目静态文件
[参考：Maven工程JSP页面无法加载.js文件的解决方案](http://blog.csdn.net/javaee_sunny/article/details/52513160)
# 5. 常用工具类
5.1 [参考：Java下利用Jackson进行JSON解析和序列化](http://blog.csdn.net/accountwcx/article/details/24585987)<br>
5.2 [参考：Jackson介绍（1）-jackson2.x与Jackson1.9的比较](http://blog.csdn.net/u011179993/article/details/46454059)<br>
5.3 [参考：spring使用jackson实现json输出](http://blog.chinaunix.net/uid-192452-id-3967223.html)<br>
5.4 [参考：java 验证框架oval的使用方法](http://blog.csdn.net/neweastsun/article/details/50473717)<br>
5.5 [开源的java后端校验框架](http://oval.sourceforge.net/)<br>
5.6 [用于导入导出Excel的Util包，基于Java的POI。可将List<Bean>导出成Excel，或读取Excel成List<Bean>,读取时有验证和Log](https://github.com/SargerasWang/ExcelUtil)
# 6. 前端插件
6.1 [bootstrap-validator 小巧的表单验证插件，无需引入额外的css，api不易用](https://github.com/1000hz/bootstrap-validator)

# 参考项目
1. [snailxr/quartz-spring_demo, SSM框架做的客户关系管理系统](https://github.com/fankay/crm)<br>
2. [SargerasWang/ExcelUtil, quartz spring结合实现动态任务管理](https://github.com/snailxr/quartz-spring_demo)<br>
3. [fankay/crm, 用于导入导出Excel的Util包，基于Java的POI](https://github.com/snailxr/quartz-spring_demo)