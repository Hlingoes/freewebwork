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
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${pageContext.request.contextPath}/static/js/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet" type="text/css">

    <%--DataTables css--%>
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
                    <c:if test="${not empty message}">
                        <div class="alert ${message.state}">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                ${message.message}
                        </div>
                    </c:if>


                    <div class="panel panel-default top_panel">
                        <div class="panel-heading">
                            <i class="fa fa-search"></i> 搜索
                        </div>
                        <div class="panel-body">
                            <form class="form-inline" id="searchForm">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="客户姓名或联系人" id="seaName">
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="联系电话" id="seaTel">
                                </div>
                                <div class="form-group">
                                    <select id="seaState" class="form-control">
                                        <option value="">客户状态</option>
                                        <option value="无">无</option>
                                        <option value="初访">初访</option>
                                        <option value="意向">意向</option>
                                        <option value="报价">报价</option>
                                        <option value="成交">成交</option>
                                        <option value="暂时搁置">暂时搁置</option>
                                    </select>
                                </div>
                                <button type="button" class="btn btn-primary" id="searchBtn">搜索</button>
                            </form>
                        </div>
                    </div>

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-users"></i> 客户列表
                            <a href="" style="margin-left: 15px;" class="pull-right btn btn-primary btn-xs">
                                <i class="fa fa-file-excel-o"></i> 导出
                            </a>
                            <a href="javascript:;" id="newCustomer" class="pull-right btn btn-success btn-xs">
                                <i class="fa fa-plus"></i> 新增
                            </a>
                        </div>
                        <div class="panel-body">
                            <table class="table" id="cust_table">
                                <thead>
                                <tr>
                                    <th width="30"><input type="checkbox"></th>
                                    <th>ID</th>
                                    <th>客户名称</th>
                                    <th>联系人</th>
                                    <th>联系电话</th>
                                    <th>当前进度</th>
                                    <th>最近跟进</th>
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

<div class="modal fade" id="newCustomer_Modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><i class="fa fa-plus"></i> 新增客户</h4>
            </div>
            <div class="modal-body">
                <form action="" id="newCustForm">
                    <div class="form-group">
                        <label>客户名称</label>
                        <input type="text" class="form-control" placeholder="" name="custname">
                    </div>
                    <div class="form-group">
                        <label>联系人</label>
                        <input type="text" class="form-control" placeholder="" name="contact">
                    </div>
                    <div class="form-group">
                        <label>联系电话</label>
                        <input type="text" class="form-control" placeholder="" name="tel">
                    </div>
                    <div class="form-group">
                        <label>地址</label>
                        <input type="text" class="form-control" placeholder="" name="address">
                    </div>
                    <div class="form-group">
                        <label>电子邮件</label>
                        <input type="text" class="form-control" placeholder="" name="email">
                    </div>
                    <div class="form-group">
                        <label>微信</label>
                        <input type="text" class="form-control" placeholder="" name="wechar">
                    </div>
                    <div class="form-group">
                        <label>QQ</label>
                        <input type="text" class="form-control" placeholder="" name="qq">
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <input type="text" class="form-control" placeholder="" name="mark">
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
<script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="${pageContext.request.contextPath}/static/js/metisMenu/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="${pageContext.request.contextPath}/static/js/sb-admin-2.js"></script>
<%-- DataTables JS--%>
<script src="${pageContext.request.contextPath}/static/js/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/datatables/media/js/dataTables.bootstrap.min.js"></script>
<script>
    $(function(){

        var table = $("#cust_table").DataTable({
            "processing": true, //loding效果
            "serverSide":true, //服务端处理
            "searching":false,//不使用自带的搜索
            "order":[[1,'desc']],//默认排序方式
            "lengthMenu":[10,25,50,100],//每页显示数据条数菜单
            "ordering":false,
            "ajax":{
                url:"${pageContext.request.contextPath}/customer/customers.json", //获取数据的URL
                type:"get", //获取数据的方式
                data:function(d){
                    d.seaName = $("#seaName").val();
                    d.seaTel = $("#seaTel").val();
                    d.seaState = $("#seaState").val();
                }
            },
            "columns":[  //返回的JSON中的对象和列的对应关系
                {"data":function(row){
                   return "<input type='checkbox'>";
                }},
                {"data":"id","name":"id"},
                {"data":function(row){
                    var result = "<a href='${pageContext.request.contextPath}/customer/"+row.id+"'>"+row.custname+"</a>";
                    if(!row.userid) {
                        result += " <i class='fa fa-unlock text-muted'></i>";
                    }
                    return result;
                },"name":"custname"},
                {"data":"contact","name":"contact"},
                {"data":"tel","name":"tel"},
                {"data":"progress"},
                {"data":"progresstime"}
            ],
            "columnDefs":[ //具体列的定义
                {
                    "targets":[1],
                    "visible":false
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

        //自定义搜索
        $("#searchBtn").click(function(){
            table.draw(); // DataTables会自动的执行查询
        });


        //保存新客户
        $("#newCustomer").click(function(){
            $("#newCustomer_Modal").modal("show");
        });

        $("#saveBtn").click(function(){
            $.post("${pageContext.request.contextPath}/customer/new",$("#newCustForm").serialize()).done(function(result){
                if(result == "success") {
                    $("#newCustForm")[0].reset();
                    $("#newCustomer_Modal").modal('hide');
                    table.ajax.reload();
                }
            }).fail(function(){
                alert("添加新客户异常");
            });
        });

    });
</script>

</body>

</html>
