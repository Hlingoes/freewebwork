<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
	<link href="${pageContext.request.contextPath}/static/css/bootstrap-fileinput-4.4.7.css" rel="stylesheet">
	
    <!-- MetisMenu CSS -->
    <link href="${pageContext.request.contextPath}/static/js/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet" type="text/css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/datatables/media/css/dataTables.bootstrap.min.css">

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
                    <h4 class="page-header">用户管理</h4>

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            用户列表
                            <a href="javascript:;" id="addNewUser" class="btn btn-success btn-xs pull-right"><i class="fa fa-plus"></i> 新增</a>
                        </div>
                        <div class="panel-body">
                            <table class="table" id="userTable">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>姓名</th>
                                    <th>联系电话</th>
                                    <th>状态</th>
                                    <th>角色</th>
                                    <th width="100">#</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                </tbody>
                            </table>
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


<div class="modal fade" id="newUserModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加用户</h4>
            </div>
            <div class="modal-body">
                <form id="newUserForm" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="username">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">联系电话</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="tel">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" name="password" value="123123">
                            <span class="help-block">默认密码为：123123</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">微信号</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="weixinid">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">角色</label>
                        <div class="col-sm-10">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="role" value="1"> 管理员
                                </label>
                                <label>
                                    <input type="checkbox" name="role" value="2"> 经理
                                </label>
                                <label>
                                    <input type="checkbox" name="role" value="3"> 普通员工
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">文件</label>
                        <div class="col-sm-10">
                            <input type="file" class="form-control file" name="testFile">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="saveBtn" class="btn btn-primary">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class="modal fade" id="editUserModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑用户</h4>
            </div>
            <div class="modal-body">
                <form id="editUserForm" class="form-horizontal">
                    <input type="hidden" name="id" id="id">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="username" id="username">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">联系电话</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="tel" id="tel">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">角色</label>
                        <div class="col-sm-10">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="role" value="1" class="role"> 管理员
                                </label>
                                <label>
                                    <input type="checkbox" name="role" value="2" class="role"> 经理
                                </label>
                                <label>
                                    <input type="checkbox" name="role" value="3" class="role"> 普通员工
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-10">
                            <div class="checkbox">
                                <label class="radio-inline">
                                    <input type="radio" name="state" value="正常" id="ok"> 正常
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="state" value="禁用" id="disable"> 禁用
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="editBtn" class="btn btn-primary">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->



<!-- jQuery -->
<script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap-fileinput-4.4.7.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="${pageContext.request.contextPath}/static/js/metisMenu/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="${pageContext.request.contextPath}/static/js/sb-admin-2.js"></script>
<script src="${pageContext.request.contextPath}/static/js/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/datatables/media/js/jquery.dataTables.min.js"></script>
<script>
    $(function(){

        var dt = $("#userTable").DataTable({
            "processing": true, //loding效果
            "serverSide":true, //服务端处理
            "searchDelay": 1000,//搜索延迟
            "order":[[0,'desc']],//默认排序方式
            "lengthMenu":[5,10,25,50,100],//每页显示数据条数菜单
            "ajax":{
                url:"${pageContext.request.contextPath}/account/users.json", //获取数据的URL
                type:"get" //获取数据的方式
            },
            "columns":[  //返回的JSON中的对象和列的对应关系
                {"data":"id","name":"id"},
                {"data":"username","name":"username"},
                {"data":"tel","name":"tel"},
                {"data":function(row){
                    if(row.state == "禁用") {
                        return "<span class='label label-danger'>"+row.state+"</span>";
                    } else {
                        return row.state;
                    };
                },"name":"state"},
                {"data":function(row){
                    var roleName = "";
                    for(var i = 0;i < row.roleList.length;i++) {
                        var role = row.roleList[i];
                        roleName = roleName + role.rolename + "&nbsp&nbsp";
                    }
                    return roleName;
                }},
                {"data":function(row){
                    return "<a href='javascript:;' class='editLink' data-id='"+row.id+"'>编辑</a> <a href='javascript:;' class='delLink' data-id='"+row.id+"'>删除</a>";
                }}
            ],
            "columnDefs":[ //具体列的定义
                {
                    "targets":[0],
                    "visible":false
                },
                {
                    "targets":[3],
                    "orderable":true
                },
                {
                    "targets":[1,2,4,5],
                    "orderable":false
                }
            ],
            "language":{
                "lengthMenu":"显示 _MENU_ 条记录",
                "search":"搜索:",
                "info": "从 _START_ 到 _END_ 共 _TOTAL_ 条记录",
                "processing":"加载中...",
                "zeroRecords":"暂无数据",
                "infoEmpty": "从 0 到 0 共 0 条记录",
                "infoFiltered":"(从 _MAX_ 条记录中读取)",
                "paginate": {
                    "first":      "首页",
                    "last":       "末页",
                    "next":       "下一页",
                    "previous":   "上一页"
                }
            }
        });

        //添加新用户
        $("#addNewUser").click(function(){
            $("#newUserModal").modal('show');
        });
        $("#saveBtn").click(function(){
        	var formData = new FormData(document.getElementById('newUserForm'));
			$.ajax({
                url: "${pageContext.request.contextPath}/account/new",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function(res){
                	if("success" == res) {
                        $("#newUserForm")[0].reset();
                        $("#newUserModal").modal("hide");
                        dt.ajax.reload();
                    }
                },
                error:function(e){
                	alert("添加时出现异常");
                }
            });
        });

        //删除用户
        $(document).delegate(".delLink","click",function(){
            var id = $(this).attr("data-id");
            if(confirm("确定要删除该数据吗?")) {
                $.post("${pageContext.request.contextPath}/account/del",{"id":id}).done(function(result){
                    if("success" == result) {
                        dt.ajax.reload();
                    }
                }).fail(function(){
                    alert("删除出现异常");
                });

            }
        });

        //编辑用户
        $(document).delegate(".editLink","click",function(){
            $("#editUserForm")[0].reset();
            var id = $(this).attr("data-id");
            $.get("${pageContext.request.contextPath}/account/user.json",{"id":id}).done(function(result){
                $("#id").val(result.id);
                $("#username").val(result.username);
                $("#tel").val(result.tel);

                $(".role").each(function(){
                    var roleList = result.roleList;
                    for(var i = 0;i < roleList.length;i++) {
                        var role = roleList[i];
                        if($(this).val() == role.id) {
                            $(this)[0].checked = true;
                        }
                    }
                });

                if(result.state == "正常") {
                    $("#ok")[0].checked = true;
                } else {
                    $("#disable")[0].checked = true;
                }
            }).fail(function(){

            });

            $("#editUserModal").modal("show");
        });

        $("#editBtn").click(function(){
            $.post("${pageContext.request.contextPath}/account/edit",$("#editUserForm").serialize()).done(function(result){
                if(result == "success") {
                    $("#editUserModal").modal("hide");
                    dt.ajax.reload();
                }
            }).fail(function(){
                alert("修改用户异常");
            });

        });
    });
</script>

</body>

</html>
