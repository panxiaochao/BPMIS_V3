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
<script type="text/javascript" src="js/jquery.ajaxCall.js"></script>
<script type="text/javascript" src="plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="plugin/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
var datagrid ;
$(function() {
	datagrid = $('#tt').datagrid({  
		url:'salary/salarylist.do', 
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
		pageList: [15,20,30,40,50],//可以设置每页记录条数的列表 
		onBeforeLoad : function(param) {
			
		},		
		onLoadSuccess : function() {
			SalaryInit();
			datagrid.datagrid('clearSelections');
		},
		onLoadError : function() {
			
		},
		   
	}); 
	//设置分页控件  
    pagination(datagrid.datagrid('getPager'))
	//		
});

var SalaryInit = function(){
	_ajaxCall.post("salary/getbanktotal.do", {}, function(data){				
		var $salaryinfo = $("#salaryinfo");
		var html = "<b>剩余资金：</b>";
		var sum = 0.0;
		$.each(data,function(i, value) { 
			html += "<b>"+value[1]+"：</b><b><span style=color:#1bb974>"+value[0]+"</span></b>&nbsp;|&nbsp;";
			sum += parseFloat(value[0]);
		})
		html += "<b>总资产：</b><b><span style=color:#ff7b0e>"+sum+"</span></b>";
		$salaryinfo.html(html);
	}, "json", true, {});
}

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

var empty = function(){
	var rowsize = datagrid.datagrid('getRows').length;
	if(rowsize==0)
		parent.$.messager.alert('提示', "如果您是第一次使用，请先去银行类别中定义自己的银行卡或市民卡，然后定义金额初始值！", 'info');
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

var addFun = function() {		
	var dialog = parent.ext.modalDialog({
		title : '添加账单信息',
		url : 'webpage/commen/salary/salaryadd.jsp',
		height: 550,
		buttons : [{
			text : '&nbsp;添加&nbsp;',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.submitForm(dialog, datagrid, parent.$);
			}
		}]
	});
	empty();
}

var editFun = function(id) {	
	var dialog = parent.ext.modalDialog({
		title : '编辑用户信息',
		url : 'salary/getPojo.do?id='+id,
		buttons : [ {
			text : '&nbsp;编辑&nbsp;',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.submitForm(dialog, datagrid, parent.$);
			}
		}]
	});
};

var editRoleFun = function(id) {	
	var dialog = parent.ext.modalDialog({
		title : '菜单权限',
		url : 'role/getrolefun.do?roleid='+id,
		buttons : [ {
			text : '&nbsp;更新&nbsp;',
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
				$.post('salary/delete.do', {id : ids.join(';')}, function(result) {
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
        <th data-options="field:'tradeid',width:160">流水号</th>
        <th data-options="field:'summary',width:250" formatter="summary">名称</th>
        <th data-options="field:'tradetime',width:80">交易时间</th>
        <th data-options="field:'store',width:80" formatter="store">收入(元)</th> 
        <th data-options="field:'pay',width:80" formatter="pay">支出(元)</th>       
        <th data-options="field:'balance',width:100" formatter="balance">账户余额(元)</th>
        <th data-options="field:'bank',width:150" formatter="bank">支付方式</th>
        <th data-options="field:'action',width:100" formatter="action">操作</th>
      </tr>
    </thead>
  </table>
  <div id="tb" style="padding:5px;height:auto">
      <div style="margin-bottom:5px">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onClick="addFun()">添加</a>
	    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onClick="removeFun()">删除</a> 
	  </div>
	  <div style="margin-bottom:5px"> 开始日期:
	    <input class="easyui-datebox" style="width:100px" name="starttime" id="starttime" />
	    &nbsp;截止日期:
	    <input class="easyui-datebox" style="width:100px" name="endtime" id="endtime"/>
	    &nbsp;支付类别:
	    <select class="easyui-combobox" panelHeight="auto" style="width:150px" name="banktype" id="banktype">
	      <option value="all">全部</option>
	      <option value="PSBC">中国邮政储蓄银行</option>   
		  <option value="CMB">招商银行</option>  
		  <option value="HZSMK">杭州市民卡</option> 
		  <option value="FLK">福利卡</option>
		  <option value="BOC">中国银行</option>
		  <option value="ICBC">中国工商银行</option>   
		  <option value="CCB">中国建设银行</option> 
		  <option value="ABC">中国农业银行</option> 
		  <option value="OTHER">其他</option>
	    </select>
	    &nbsp;<a href="#" class="easyui-linkbutton" iconCls="icon-search" onClick="searchFun()">搜索&nbsp;</a> 
	  </div>
	  <div id="salaryinfo"></div>
  </div>
</div>
  
</body>
</html>
