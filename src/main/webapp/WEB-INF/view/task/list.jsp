<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>CRM-客户关系管理系统</title>

    <!-- Bootstrap Core CSS -->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/static/js/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/static/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/static/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/static/js/datepicker/css/bootstrap-datepicker3.min.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <style>
        #done .panel-body {
            color: #ccc;
            text-decoration: line-through;
        }
    </style>
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
                        <i class="fa fa-tasks"></i> 待办任务
                        <button class="btn btn-primary btn-sm pull-right" id="showModal"><i class="fa fa-plus"></i> 新增</button>
                        <div class="clearfix"></div>
                    </h4>


                    <c:if test="${not empty message}">
                        <div class="alert ${message.state}">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                ${message.message}
                        </div>
                    </c:if>

                    <!-- Nav tabs -->
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">未完成</a></li>
                        <li role="presentation"><a href="#done" aria-controls="profile" role="tab" data-toggle="tab">已完成</a></li>
                    </ul>

                    <!-- Tab panes -->
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane active" id="home">

                            <c:forEach items="${newTaskList}" var="task">
                                <div class="panel panel-default top_panel">
                                    <div class="panel-body">
                                        <c:choose>
                                            <c:when test="${task.overTime}">
                                                <strong class="text-danger"> ${task.monthAndDay} (逾期) </strong> ${task.task}
                                            </c:when>
                                            <c:otherwise>
                                                <strong> ${task.monthAndDay} </strong> ${task.task}
                                            </c:otherwise>
                                        </c:choose>

                                        <div class="pull-right">
                                            <a href="javascript:;" class="doneLink" rel="${task.id}">标记为已完成</a>
                                            <a href="">编辑</a>
                                            <a href="">删除</a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                        </div>
                        <div role="tabpanel" class="tab-pane" id="done">
                            <c:forEach items="${doneTaskList}" var="task">
                                <div class="panel panel-default top_panel">
                                    <div class="panel-body">
                                        <c:choose>
                                            <c:when test="${task.overTime}">
                                                <strong class="text-danger"> ${task.monthAndDay} (逾期) </strong> ${task.task}
                                            </c:when>
                                            <c:otherwise>
                                                <strong> ${task.monthAndDay} </strong> ${task.task}
                                            </c:otherwise>
                                        </c:choose>

                                        <div class="pull-right">
                                            <a href="javascript:;" class="unLink" rel="${task.id}">撤销已完成</a>
                                            <a href="">删除</a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>


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

<div class="modal fade" id="newTaskModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增待办事项</h4>
            </div>
            <div class="modal-body">
                <form action="/task/new" method="post" id="newTaskForm">
                    <div class="form-group">
                        <label>待办内容</label>
                        <textarea name="task" class="form-control" rows="3"></textarea>
                        <label>待办时间</label>
                        <input type="text" name="worktime" class="form-control" id="datepicker">
                        <label>关联客户</label>
                        <select name="custid" class="form-control">
                            <option value=""></option>
                            <c:forEach items="${customerList}" var="cust">
                                <option value="${cust.id}">${cust.custname}</option>
                            </c:forEach>
                        </select>
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
<script src="/static/js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/static/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="/static/js/metisMenu/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="/static/js/sb-admin-2.js"></script>
<script src="/static/js/datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="/static/js/datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>

<script>
    $(function(){

        $("#showModal").click(function(){
            $("#newTaskModal").modal("show");
        });

        // 资料地址 http://bootstrap-datepicker.readthedocs.io/en/latest/index.html
        $("#datepicker").datepicker({
            format: "yyyy-mm-dd",
            language: "zh-CN",
            autoclose: true
        });

        //保存新的待办任务
        $("#saveBtn").click(function(){
            $("#newTaskForm").submit();
        });

        //标记为已完成
        $(document).delegate(".doneLink","click",function(){
            alert("doneLink");
            var $this = $(this);
            var id = $this.attr("rel");
            $.post("/task/state/change",{"taskId":id,"state":"true"}).done(function(result){
                if(result.state == "success") {
                    $this.attr("class","unLink");
                    $this.text("撤销已完成");
                    $this.next().remove();
                    var panel = $this.parent().parent().parent();
                    $("#done").append(panel);
                } else {
                    alert(result.message);
                }
            }).fail(function(){
                alert("操作异常");
            });

        });

        //撤销已完成
        //标记为已完成
        $(document).delegate(".unLink","click",function(){
            alert("unLink");
            var $this = $(this);
            var id = $this.attr("rel");
            $.post("/task/state/change",{"taskId":id,"state":"false"}).done(function(result){
                if(result.state == "success") {
                    $this.attr("class","doneLink");
                    $this.text("标记已完成");
                    $(" <a href=''>编辑</a>").insertAfter($this);
                    var panel = $this.parent().parent().parent();
                    $("#home").append(panel);
                } else {
                    alert(result.message);
                }
            }).fail(function(){
                alert("操作异常");
            });

        });
    });
</script>

</body>

</html>
