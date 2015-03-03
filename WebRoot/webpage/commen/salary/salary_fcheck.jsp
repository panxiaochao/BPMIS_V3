<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>
<%
	String time = request.getParameter("time");
	String type = request.getParameter("type");
%>
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
<script type="text/javascript" src="js/jquery.ajaxCall.js"></script>
<script type="text/javascript" src="plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="plugin/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
var datagrid ;
$(function() {
	datagrid = $('#tt').datagrid({  
		url:'hicharts/salarydetail.do?time=<%=time%>&type=<%=type%>', 
		//border : false,	
		singleSelect: true, //单选	
		loadMsg:'数据加载中......',
		fit: true,
		nowrap: true,
		idField : 'id',
		striped:true, //斑马线
		pagination:true,//分页控件  
        rownumbers:true,//行号
		//toolbar  : '#tb',
		fitColumns: true,
		pageSize: 15,
		pageList: [15,20,30,40,50],//可以设置每页记录条数的列表 
		onBeforeLoad : function(param) {
			
		},		
		onLoadSuccess : function() {
			datagrid.datagrid('clearSelections');
		},
		onLoadError : function() {
			
		},
		   
	}); 
	//设置分页控件  
    pagination(datagrid.datagrid('getPager'))
	//
	SalaryInit();
	
	
});

var pagination = function(p){
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
}

var searchFun = function() {
	var selectText = $("#banktype").combobox("getValue");
	var starttime = $("#starttime").datebox('getValue');
	var endtime = $("#endtime").datebox('getValue');
	if(starttime<=endtime){
		datagrid.datagrid('load',{
			selectText: selectText,
			starttime: starttime,
			endtime: endtime
		});	
		 
	}else
		$.messager.alert('提示', '截止时间应大于开始时间！', 'info');

}

var action = function(value,rowData,rowIndex) {
	var str = "[<a href=\"javascript:void(0)\" onclick=\"editFun('"+rowData.id+"');\">编辑</a>]";
	return "<i>[暂不支持]</i>";
}
var store = function(value,rowData,rowIndex) {
	if(rowData.trdetype=="in")
		return "<span style=color:#1bb974><b>+"+value+"</b></span>";
}
var pay = function(value,rowData,rowIndex) {
	if(rowData.trdetype=="out")
		return "<span style=color:#ff7b0e><b>-"+value+"</b></span>";
}
var bank = function(value,rowData,rowIndex) {
	return value.split(";")[1];
}
var balance = function(value,rowData,rowIndex) {
	if(value > 0)
		return "<b>"+value+"</b>";		
	else
		return "<span style=color:red><b>"+value+"</b></span>";
}
var summary = function(value,rowData,rowIndex) {
	var str = "<span title=\""+value+"\">"+value+"</span>";
	return str;
}
</script>
</head>

<body id="index_layout" class="easyui-layout">
<!--主体-->
<div data-options="region:'center'" border="false" style="padding:0px;" >
  <table id="tt" style=" border-bottom:none;">
    <thead>
      <tr>      
        <th data-options="field:'tradeid',width:160">流水号</th>
        <th data-options="field:'summary',width:250" formatter="summary">名称</th>
        <th data-options="field:'tradetime',width:80">交易时间</th>
        <th data-options="field:'store',width:80" formatter="store">收入(元)</th> 
        <th data-options="field:'pay',width:80" formatter="pay">支出(元)</th>       
        <th data-options="field:'bank',width:150" formatter="bank">支付方式</th>
        <th data-options="field:'action',width:100" formatter="action">操作</th>
      </tr>
    </thead>
  </table>
</div>
  
</body>
</html>
