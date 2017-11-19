<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Task Manage</title>
<link href="${pageContext.request.contextPath}/static/pic/favicon.png" rel="shortcut icon" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-v3.3.7.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ui.jqgrid-bootstrap.css" />
</head>
<body>
	<!-- Navigation -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/">DataTables 中文网</a>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a href="/taskSchedule/showJobs">任务调度</a></li>
				<li><a href="/example">例子</a></li>
				<li><a href="/manual">手册</a></li>
				<li><a href="/reference">文档(Options&API)</a></li>
				<li><a href="/extensions">扩展</a></li>
				<li><a href="/plug-ins">插件</a></li>
				<li><a href="/faqs">FAQS</a></li>
				<li><a href="/upgrade">更新</a></li>
				<li><a href="/about">关于</a></li>
				<li><a href="/tour">导航</a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	</nav>
	<!-- Page Content -->
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<table id="taskGridTable"></table>
				<div id="taskGridPager"></div>
			</div>
		</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/scripts/jquery-2.2.4.js"></script>
<script src="${pageContext.request.contextPath}/scripts/bootstrap-v3.3.7.js"></script>
<script src="${pageContext.request.contextPath}/scripts/task/quartzTask.js"></script>
</html>

