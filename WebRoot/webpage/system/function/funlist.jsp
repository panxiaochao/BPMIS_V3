<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<c:out value="${webRoot}" />">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<!--easyui-->
<link rel="stylesheet" type="text/css" href="plugin/easyui/themes/metro-blue/easyui.css">
<link rel="stylesheet" type="text/css" href="plugin/easyui/themes/icon.css">
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
var treeGrid ;
$(function() {
	treeGrid = $('#tt').treegrid({  
		url:'function/funlist.do', 
		//method: 'post',
		rownumbers: true,
		//fitColumns:true,
		fit:true,
		//border:false,
		loadMsg:'数据加载中......',
		striped:true, //斑马线
		idField: 'id',
		animate: true,
		treeField: 'nodename',
		toolbar  : '#tb',
		onLoadSuccess : function() {
			
		}		   
	}); 
});

var addFun = function() {
	var dialog = parent.ext.modalDialog({
		title : '添加菜单信息',
		url : 'webpage/system/function/funadd.jsp',
		buttons : [{
			text : '&nbsp;添加&nbsp;',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.submitForm(dialog, treeGrid, parent.$);
			}
		}]
	});
}

var editFun = function(id) {	
	var dialog = parent.ext.modalDialog({
		title : '编辑菜单信息',
		url : 'function/getPojo.do?id='+id,
		buttons : [ {
			text : '&nbsp;编辑&nbsp;',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.submitForm(dialog, treeGrid, parent.$);
			}
		}]
	});
};

var action = function(value,rowData,rowIndex) {
	var str = "[<a href=\"javascript:void(0)\" onclick=\"editFun('"+rowData.id+"');\">编辑</a>]";
	return str;
}

function removeFun(){
	var row = treeGrid.treegrid('getSelected');
	if (row){
		if(row.nodename == 'root'){
			$.messager.alert('提示', "根节点不能删除！", 'info');
		}else{
			$.messager.confirm('询问', '您是否要删除？', function(b) {
			if (b) {
					$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					}); 
					$.post('function/delete.do', {id : row.id}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							treeGrid.treegrid('reload');
						}else
							parent.$.messager.alert('提示', result.msg, 'info');
						$.messager.progress('close');
					}, 'json');
				}
			});
		}		
	}else
		$.messager.alert('信息', "至少选择一条！");
	
}

//formatter
var nodeview = function(value,rowData,rowIndex) {
	if(rowData.nodeview=="0")
		return "关闭";
	else if(rowData.nodeview=="1")
		return "启用";
}
//reload
var reloadFun = function(){
	parent.document.getElementById('topFrame').contentWindow.reload();
}
</script>
</head>

<body id="index_layout" class="easyui-layout">
<!--header-->
<div id="navhead" data-options="region:'north'" border="false" style="height:40px;">
  <div class="place"> <span>位置：</span>
    <ul class="placeul">
      <li><a href="login/index.do">首页</a></li>
      <li>菜单管理</li>
    </ul>
  </div>
</div>
<!--主体-->
<div data-options="region:'center'" border="false" style="padding:10px;" >
  <table id="tt" style="">
    <thead>
      <tr>
        <th data-options="field:'nodename'" width="200">菜单名</th>
        <!--  
        <th data-options="field:'iconsname'" width="50">图标</th>-->
        <th data-options="field:'nodeurl'" width="250">URL地址</th>
        <th data-options="field:'nodeorder'" width="80">菜单值</th>
        <th data-options="field:'nodeview'" width="80" formatter="nodeview">是否启用</th>
        <th data-options="field:'remark'" width="200">备注</th>    
        <th data-options="field:'action',width:100" formatter="action">操作</th>        
      </tr>
    </thead>
  </table>
  <div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onClick="addFun()">添加</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onClick="removeFun()">删除</a> 
    <a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onClick="reloadFun()">刷新组件</a>
  </div>
  </div>
</div>
</body>
</html>
