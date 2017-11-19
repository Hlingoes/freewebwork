<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="${pageContext.request.contextPath}/home"><i class="fa fa-coffee"></i> CRM</a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-right">

        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="#"><i class="fa fa-gear fa-fw"></i> 设置</a>
                </li>
                <li class="divider"></li>
                <li><a href="${pageContext.request.contextPath}/logout"><i class="fa fa-sign-out fa-fw"></i> 安全退出</a>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <!-- /.navbar-top-links 顶部导航栏结束-->

    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li>
                    <a href="${pageContext.request.contextPath}/home"><i class="fa fa-dashboard fa-fw"></i> 首页</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/customer"><i class="fa fa-users fa-fw"></i> 客户</a>
                    <!-- /.nav-second-level -->
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/progress"><i class="fa fa-table fa-fw"></i> 跟进</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/task"><i class="fa fa-edit fa-fw"></i> 待办</a>
                </li>
                <li>
                    <a href="chart.html"><i class="fa fa-bar-chart"></i> 统计</a>

                    <!-- /.nav-second-level -->
                </li>
                <shiro:hasRole name="管理员">
                    <li>
                        <a href="${pageContext.request.contextPath}/account"><i class="fa fa-sitemap fa-fw"></i> 用户管理</a>
                    </li>
                </shiro:hasRole>
            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
</nav>
