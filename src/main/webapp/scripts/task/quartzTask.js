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
		            { label: 'Job Name', name: 'jobName', width: 120 },
		            { label: 'Job ID', name: 'jobId', key: true, width:0, hidden: true },
		            { label: 'Job Group', name: 'jobGroup', width: 120 },
		            { label:'Method Name', name: 'methodName', width: 80 },
		            { label: 'Job Status', name: 'jobStatus', width: 80 },
		            { label:'Cron Expression', name: 'cronExpression', width: 150 },
		            { label:'Description', name: 'description', width: 200 },
		            { label:'Bean Class', name: 'beanClass', width: 200 },
		            { label:'Concurrent', name: 'isConcurrent', width: 80 },
		            { label:'springId', name: 'springId', width: 80 },
		            { label:'Concurrent', name: 'isConcurrent', width: 80 },
		            { label:'Create Time', name: 'createTime', width: 120 },
		            { label:'Update Time', name: 'updateTime', width: 120 }
		        ],
				viewrecords: true,
		        rowNum: 10,
		        rowList:[10,20,30], 
		        sortname: 'id', 
		        sortorder: "desc",
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
		grid("taskGridTable", "taskGridPager").load();
	};
	
	return {
		init: init
	};
};