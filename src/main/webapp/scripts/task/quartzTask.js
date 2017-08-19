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
	
	var grid = function (tableId, pager) {
		var load = function () {
			$("#" + tableId).jqGrid({
		        url: pageUrl.loadTableUrl,
		        mtype: "GET",
				styleUI : 'Bootstrap',
		        datatype: "json",
		        colModel: [
		            { label: 'Job Name', name: 'jobName', width: 85 },
		            { label: 'Job ID', name: 'jobId', key: true, width:0, hidden: true },
		            { label: 'Job Group', name: 'jobGroup', width: 100 },
		            { label: 'Method Name', name: 'methodName', width: 115 },
		            { label: 'Job Status', name: 'jobStatus', width: 115 },
		            { label: 'Cron Expression', name: 'cronExpression', width: 140 },
		            { label: 'Description', name: 'description', width: 100 },
		            { label: 'Bean Class', name: 'beanClass', width: 240 },
		            { label: 'Concurrent', name: 'isConcurrent', width: 95 },
		            { label: 'Create Time', name: 'createTime', width: 135 },
		            { label: 'Update Time', name: 'updateTime', width: 135 }
		        ],
				viewrecords: true,
		        rowNum: 10,
		        rowList:[10,20,30], 
		        sortname: 'updateTime', 
                width: 1440,
                height: 480,
		        sortorder: "desc",
                rownumbers: true, // show row numbers
                rownumWidth: 35, // the width of the row numbers columns
		        pager: "#" + pager
		    }).navGrid("#" + pager,
                { edit: true, add: true, del: true, search: false, refresh: true, view: false, position: "left"}
	        );
		}
		return {
			load: load
		};
	}
	
	var init = function () {
		$.jgrid.defaults.responsive = true;
		$.jgrid.defaults.styleUI = 'Bootstrap';
		grid("taskGridTable", "taskGridPager").load();
	};
	
	return {
		init: init
	};
};