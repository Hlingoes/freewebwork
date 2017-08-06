<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<style type="text/css">
.datagrid-mask {
	background: #ccc;
}

.datagrid-mask-msg {
	border-color: #95B8E7;
}

.datagrid-mask-msg {
	background: #ffffff url('../images/loading.gif') no-repeat scroll 5px center;
}

.datagrid-mask {
	position: absolute;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	opacity: 0.3;
	filter: alpha(opacity = 30);
	display: none;
}

.datagrid-mask-msg {
	position: absolute;
	top: 50%;
	margin-top: -20px;
	padding: 12px 5px 10px 30px;
	width: auto;
	height: 16px;
	border-width: 2px;
	border-style: solid;
	display: none;
}

.list_table {
	border: 1px solid #CCCCCC;
	border-collapse: collapse;
	color: #333333;
	margin: 0 0 0;
	width: 100%;
}

.list_table tbody td {
	border-top: 1px solid #CCCCCC;
	text-align: left;
}

.list_table th {
	line-height: 1.2em;
	vertical-align: top;
}

.list_table td {
	line-height: 2em;
	font-size: 12px;
	vertical-align: central;
	align: left;
}

.list_table td input {
	width: 90%;
}

.list_table tbody tr:hover th, .list_table tbody tr:hover td {
	background: #EEF0F2;
}

.list_table thead tr {
	background: none repeat scroll 0 0 #09f;
	color: #fff;
	font-weight: bold;
	border-bottom: 1px solid #CCCCCC;
	border-right: 1px solid #CCCCCC;
}
</style>
</head>

<title>task</title>
<body class="bgray">
	<table class="list_table">
		<thead>
			<tr>
				<td style="width: 20xp">id&nbsp;&nbsp;&nbsp;</td>
				<td>name</td>
				<td>group</td>
				<td>状态&nbsp;&nbsp;</td>
				<td>cron表达式</td>
				<td>描述</td>
				<td>同步否</td>
				<td>类路径</td>
				<td>spring id</td>
				<td>方法名</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="job" items="${taskList}">
				<tr>
					<td>${job.jobId }</td>
					<td>${job.jobName }</td>
					<td>${job.jobGroup }</td>
					<td>${job.jobStatus }</td>
					<td>${job.cronExpression }</td>
					<td>${job.description }</td>
					<td>${job.isConcurrent }</td>
					<td>${job.beanClass }</td>
					<td>${job.springId }</td>
					<td>${job.methodName }</td>
					<td><a href="javascript:;">更新cron</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.9.1.min.js"></script>
</html>

