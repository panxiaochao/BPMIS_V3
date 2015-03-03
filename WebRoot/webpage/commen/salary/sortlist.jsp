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
var datagrid ;
$(function() {
	datagrid = $('#tt').datagrid({  
		url:'salary/salsort.do', 
		//border : false,	
		singleSelect: true, //单选	
		loadMsg:'数据加载中......',
		fit: true,
		nowrap: true,
		idField : 'id',
		striped:true, //斑马线
		pagination:true,//分页控件  
        rownumbers:true,//行号
		toolbar  : '#tb',
		pageSize: 15,
		pageList: [15,30,45],//可以设置每页记录条数的列表
		onBeforeLoad : function(param) {
			
		},		
		onLoadSuccess : function() {
			datagrid.datagrid('clearSelections');
		},
		onLoadError : function() {
			
		},
		   
	}); 
	//设置分页控件  
    var p = datagrid.datagrid('getPager');  
    $(p).pagination({           
        beforePageText: '第',//页数文本框前显示的汉字  
        afterPageText: '页    共 {pages} 页',  
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
        onBeforeRefresh:function(){ 
            //$(this).pagination('loading'); 
            //alert('before refresh'); 
            //$(this).pagination('loaded'); 
        } 
    }); 
});

var addFun = function() {
	var dialog = parent.ext.modalDialog({
		title : '添加账单类别信息',
		url : 'webpage/commen/salary/sortadd.jsp',
		buttons : [{
			text : '&nbsp;添加&nbsp;',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.submitForm(dialog, datagrid, parent.$);
			}
		}]
	});
}

var editFun = function(id) {	
	var dialog = parent.ext.modalDialog({
		title : '编辑类别信息',
		url : 'salary/getPojo_Sort.do?id='+id,
		buttons : [ {
			text : '&nbsp;编辑&nbsp;',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.submitForm(dialog, datagrid, parent.$);
			}
		}]
	});
};

function removeFun(){
	var rows = datagrid.datagrid('getSelections');
	if (rows.length > 0){
		$.messager.confirm('询问', '您是否要删除？', function(b) {
			if (b) {
				$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var ids = new Array();
                for (var i = 0; i < rows.length; i++) {
                    ids.push(rows[i].id);
                } 
				$.post('salary/delsort.do', {id : ids.join(';')}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						datagrid.datagrid('reload');
					}else
						parent.$.messager.alert('提示', result.msg, 'info');
					$.messager.progress('close');
				}, 'json');
			}
		});
	}else
		$.messager.alert('信息', "至少选择一条！");
	
}


var action = function(value,rowData,rowIndex) {
	var str = "[<a href=\"javascript:void(0)\" onclick=\"editFun('"+rowData.id+"');\">编辑</a>]";
	return str
}
var sorttype = function(value,rowData,rowIndex) {
	if(rowData.sorttype=="in")
		return "收入[IN]";
	else if(rowData.sorttype=="out")
		return "支出[OUT]";
}


</script>
</head>

<body id="index_layout" class="easyui-layout">
<!--header-->
<div id="navhead" data-options="region:'north'" border="false" style="height:40px;">
  <div class="place"> <span>位置：</span>
    <ul class="placeul">
      <li><a href="login/index.do">首页</a></li>
      <li>我的账单</li>
    </ul>
  </div>
</div>
<!--主体-->
<div data-options="region:'center'" border="false" style="padding:10px;" >
  <table id="tt" style=" border-bottom:none;">
    <thead>
      <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'sortname',width:120">类别名称</th>
        <th data-options="field:'sorttype',width:100" formatter="sorttype">类别方式</th>     
        <th data-options="field:'action',width:100" formatter="action">操作</th>
      </tr>
    </thead>
  </table>
  <div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onClick="addFun()">添加</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onClick="removeFun()">删除</a> 
  </div>
  </div>
</div>
</body>
</html>
