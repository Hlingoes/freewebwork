/**
 * @description 在页面操作任务的增删改，启动，停止
 * @author Hlingoes
 */
;$(document).ready(function(){
	taskHandler().init();
});
var taskHandler = function () {
	var pageUrl = {
			loadTableUrl: "showJobs"
	};
	
	var pageModal, pageGrid;
	
	// 使用bootstrap自带的modal.js
	function TaskModal (domId) {
		var $modal = $("#" + domId);
		
		var init = function () {
			$modal.on('shown.bs.modal', function () {
				// 执行一些动作...
			});
			$modal.on('hidden.bs.modal', function () {
				// 清空模态框的内容
				$modal.find("input[type='reset']").trigger("click");
			});
		};
		
		var show = function () {
			$modal.modal("show");
		};
		
		var hide = function () {
			$modal.modal("hide");
		};
		
		return {
			init: init,
			show: show,
			hide: hide
		};
	};
	
	// 使用jqGrid新建表格
	function Grid (tableId, pager) {
		var $grid = $("#" + tableId), $pager = "#" + pager;
		
		var load = function () {
			$grid.jqGrid({
		        url: pageUrl.loadTableUrl,
		        mtype: "GET",
		        styleUI: "Bootstrap",
		        datatype: "json",
		        colModel: [
		            { label: 'Job Name', name: 'jobName', width: 85 },
		            { label: 'Job ID', name: 'jobId', key: true, width:0, hidden: true },
		            { label: 'Job Group', name: 'jobGroup', width: 100 },
		            { label: 'Method Name', name: 'methodName', width: 115 },
		            { label: 'Job Status', name: 'jobStatus', width: 115, formatter: formatStatus },
		            { label: 'Cron Expression', name: 'cronExpression', width: 140 },
		            { label: 'Description', name: 'description', width: 100 },
		            { label: 'Bean Class', name: 'beanClass', width: 240 },
		            { label: 'Concurrent', name: 'isConcurrent', width: 95, formatter: formatAsync },
		            { label: 'Create Time', name: 'createTime', width: 145 },
		            { label: 'Update Time', name: 'updateTime', width: 145 },
		            { label: 'Operation', name: 'jobId', key: true, width: 100, title: false, formatter: formatOperate }
		        ],
				viewrecords: true,
		        rowNum: 10,
		        rowList:[10, 20, 30], 
		        sortname: 'updateTime', 
                height: 480,
		        sortorder: "desc",
		        pager: $pager
		    });
			$grid.jqGrid('navGrid', $pager, { edit: false, add: false, del: false, search: false, refresh: true });
			$grid.jqGrid('navButtonAdd', $pager, {
				caption : "",
				buttonicon : "glyphicon glyphicon-plus",
				onClickButton : function() {
					pageModal.show();
				},
				position : "first",
				title : "add task",
				cursor : "pointer"
			});
		};
		
		var reload = function () {
			$grid.jqGrid('setGridParam', { page : 1 }).trigger('reloadGrid');
		};
		
		// 格式化任务的运行状态
		function formatStatus (value, options, rowObject) {
			if (parseInt(rowObject.jobStatus, 10) === 0) {
				return "Stoped";
			} else {
				return "Running";
			}
		}
		
		// 格式化同步和异步
		function formatAsync (value, options, rowObject) {
			if (parseInt(rowObject.jobStatus, 10) === 0) {
				return "No";
			} else {
				return "Yes";
			}
		}
		
		// 给每行加上：停止(stop), 开启(start), 更新cron(update)
		function formatOperate (value, options, rowObject) {
			var stop = '<a class="task-stop task-operate" args="'+ rowObject.jobId +'" title="stop"><i class="glyphicon glyphicon-stop"></i></a>';
			var start = '<a class="task-start task-operate" args="'+ rowObject.jobId +'" title="start"><i class="glyphicon glyphicon-play"></i></a>';
			var edit = '<a class="task-update task-operate" args="'+ rowObject.jobId +'" title="edit"><i class="glyphicon glyphicon-edit"></i></a>';
			var remove = '<a class="task-update task-operate" args="'+ rowObject.jobId +'" title="remove"><i class="glyphicon glyphicon-trash"></i></a>';
			
			if (parseInt(rowObject.jobStatus, 10) === 0) {
				return start + edit + remove;
			}
			return stop + edit + remove;
		}
		
		return {
			load: function () {
				// 判断是否初次加载
				if (!$grid.jqGrid("getGridParam", "postData")) {
					load();
				} else {
					reload();
				}
			}
		};
	}
	
	var init = function () {
		pageModal = TaskModal("taskModal");
		pageGrid = Grid("taskGridTable", "taskGridPager");
		pageGrid.load();
		pageModal.init();
		
		// 委托验证并提交表单事件
		$("form").validator().on('submit', function(e) {
			// 表示验证成功
			if ($(this).find("has-error").length === 0) {
				 $.post("add", $("form").serialize()).done(function (res) {
					 if (res.flag) {
						 pageModal.hide();
						 pageGrid.load();
					 } else {
						 alert(res.msg);
					 }
				 });
			}
		});
		
		
	};
	
	return {
		init: init
	};
};
