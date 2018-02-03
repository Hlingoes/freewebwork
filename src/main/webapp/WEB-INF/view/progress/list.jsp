<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>CRM-客户关系管理系统</title>
	<link href="${pageContext.request.contextPath}/static/pic/favicon.png" rel="shortcut icon" />
    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${pageContext.request.contextPath}/static/js/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<div id="wrapper">

    <%@ include file="../include/nav.jsp"%>

    <!-- Page Content -->
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <h4 class="page-header">
                        <i class="fa fa-fax"></i> 跟进列表
                    </h4>

                    <c:if test="${not empty message}">
                        <div class="alert ${message.state}">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                ${message.message}
                        </div>
                    </c:if>

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-search"></i> 搜索
                        </div>
                        <div class="panel-body">
                            <form class="form-inline">
                                <select name="userid" class="form-control">
                                    <option value="">所属员工</option>
                                    <c:forEach items="${userList}" var="user">
                                        <option ${userid == user.id ? 'selected' : ''} value="${user.id}">${user.username}</option>
                                    </c:forEach>
                                </select>
                                <select name="progress" class="form-control">
                                    <option value="">进度</option>
                                    <option ${progress == '初访' ? 'selected' : ''} value="初访">初访</option>
                                    <option ${progress == '意向' ? 'selected' : ''} value="意向">意向</option>
                                    <option ${progress == '报价' ? 'selected' : ''} value="报价">报价</option>
                                    <option ${progress == '成交' ? 'selected' : ''} value="成交">成交</option>
                                    <option ${progress == '暂时搁置' ? 'selected' : ''} value="暂时搁置">暂时搁置</option>
                                </select>
                                <select name="date" class="form-control">
                                    <option value="">时间</option>
                                    <option ${date == '今天' ? 'selected' : ''} value="今天">今天</option>
                                    <option ${date == '昨天' ? 'selected' : ''} value="昨天">昨天</option>
                                    <option ${date == '本周' ? 'selected' : ''} value="本周">本周</option>
                                    <option ${date == '本月' ? 'selected' : ''} value="本月">本月</option>
                                    <option ${date == '更早' ? 'selected' : ''} value="更早">更早</option>
                                </select>

                                <input type="text" name="context" class="form-control" placeholder="跟进内容" value="${context}">

                                <button class="btn btn-info">搜索</button>
                            </form>

                        </div>
                    </div>

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-list"></i> 跟进列表
                            <button class="btn btn-primary pull-right btn-xs" id="newBtn"><i class="fa fa-plus"></i> 新增跟进信息</button>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${page.list}" var="pro">
                            	<c:choose>
		                            <c:when test="${pro.progress == '成交'}">
		                            	<div class="panel panel-success">
                                	</c:when>
                                	<c:when test="${pro.progress == '暂时搁置'}">
                                		<div class="panel panel-danger">
                                    </c:when>
	                                <c:otherwise>
	                                    <div class="panel panel-default">
	                                </c:otherwise>
                                 </c:choose>

                                 <div class="panel-heading">
                                     <i class="fa fa-calendar"></i> ${pro.monthAndDay} - <span class="text-muted">${pro.user.username}</span> - ${pro.customer.custname}
                                     <c:choose>
                                         <c:when test="${pro.progress == '初访'}">
                                             <span class="label label-default pull-right">${pro.progress}</span>
                                         </c:when>
                                         <c:when test="${pro.progress == '意向'}">
                                             <span class="label label-info pull-right">${pro.progress}</span>
                                         </c:when>
                                         <c:when test="${pro.progress == '报价'}">
                                             <span class="label label-primary pull-right">${pro.progress}</span>
                                         </c:when>
                                         <c:when test="${pro.progress == '成交'}">
                                             <span class="label label-success pull-right">${pro.progress}</span>
                                         </c:when>
                                         <c:when test="${pro.progress == '暂时搁置'}">
                                             <span class="label label-danger pull-right">${pro.progress}</span>
                                         </c:when>
                                     </c:choose>
                                 </div>
                                 <div class="panel-body">
                                         ${pro.mark}
                                 </div>
                                 <c:choose>
                                     <c:when test="${sessionScope.curr_user.id == pro.user.id}">
                                         <c:if test="${not empty pro.progressFileList}">
                                             <div class="panel-footer">
                                                 <c:forEach items="${pro.progressFileList}" var="file">
                                                     <a href="${file.path}?attname=${file.filename}">${file.filename}</a>
                                                 </c:forEach>
                                             </div>
                                         </c:if>
                                     </c:when>
                                     <c:otherwise>
                                         <shiro:hasRole name="经理">
                                             <c:if test="${not empty pro.progressFileList}">
                                                 <div class="panel-footer">
                                                     <c:forEach items="${pro.progressFileList}" var="file">
                                                         <a href="${file.path}?attname=${file.filename}">${file.filename}</a>
                                                     </c:forEach>
                                                 </div>
                                             </c:if>
                                         </shiro:hasRole>
                                     </c:otherwise>
                                 </c:choose>
                              </div>
                           </c:forEach>
                        </div>
                    </div>
                    <ul id="pagination" class="pagination-sm pull-right"></ul>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container-fluid -->
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<div class="modal fade" id="newModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增跟进记录</h4>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/progress/new" method="post" id="newForm" enctype="multipart/form-data">
                    <div class="form-group">
                        <label>选择客户</label>
                        <select name="custid" class="form-control">
                            <c:forEach items="${customerList}" var="cust">
                                <option value="${cust.id}">${cust.custname}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>选择进度</label>
                        <select name="progress" class="form-control">
                            <option value="初访">初访</option>
                            <option value="意向">意向</option>
                            <option value="报价">报价</option>
                            <option value="成交">成交</option>
                            <option value="暂时搁置">暂时搁置</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>内容</label>
                        <textarea name="mark" class="form-control" rows="3"></textarea>
                    </div>
                    <div class="form-group" id="fileControls">
                        <label>相关文件 <button type="button" class="btn btn-default btn-xs" id="addFileControl"><i class="fa fa-plus"></i></button></label>
                        <input type="file" name="file" class="form-control">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->




<!-- jQuery -->
<script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="${pageContext.request.contextPath}/static/js/metisMenu/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="${pageContext.request.contextPath}/static/js/sb-admin-2.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function(){

        $("#pagination").twbsPagination({
            totalPages:${page.totalPages},
            visiblePages:5,
            first:"首页",
            last:"末页",
            next:"下一页",
            prev:"上一页",
            href:"?p={{number}}&userid=${userid}&progress="+encodeURIComponent('${progress}')+"&date="+encodeURIComponent('${date}')+"&context="+encodeURIComponent('${context}')
        });

        $("#newBtn").click(function(){
            $("#newModal").modal('show');
        });

        $("#addFileControl").click(function(){
            var html = "<input type='file' name='file' class='form-control' style='margin-top:8px;'/>";
            $("#fileControls").append(html);
        });

        $("#saveBtn").click(function(){
            $("#newForm").submit();
        });

    });
</script>

</body>

</html>
