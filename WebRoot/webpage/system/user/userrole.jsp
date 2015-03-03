<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<c:out value="${webRoot}" />">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="css/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>

<script type="text/javascript" src="js/jquery.ajaxCall.js"></script>

<!--jqselect -->
<script src="plugin/jqselect/js/standalone/selectize.js"></script>
<link rel="stylesheet" href="plugin/jqselect/css/selectize.default.css">
<script type="text/javascript">

var submitForm = function($dialog, $grid, $pjq) {
	var url = "user/updaterole.do";		
	//alert($("#submitform").serialize());
	_ajaxCall.post(url, $("#submitform").serialize(), function(data){				
		if(data.success){
			$pjq.messager.alert('提示', data.msg, 'info');
			$grid.datagrid('load');
			$dialog.dialog('destroy');
		}else{
			$pjq.messager.alert('提示', data.msg, 'error');
			$dialog.dialog('destroy');
		}			
	}, "json", false, {});
};	
//role  -  select
var role_select = function(){
	var options = [];
	_ajaxCall.post("user/getrole.do", {}, function(data){						
		$.each(data, function(i, n){
	    	options.push({
		        id: n.id,
		        title: n.roleName
		    });
	    });				
	}, "json", false, {});	
	return options;
}


$(document).ready(function(e) {
	var clientHeight = document.documentElement.clientHeight;
	$(".formborder").css("min-height",clientHeight - 68);
	$(".noresize").css("resize","none");
	
	//role
	var roleoptions = role_select();	
	var $rolesel = $('#role-sel').selectize({
		create: false,
		plugins: ['remove_button'],
 		persist: false,
		maxItems: 100, // 多选，最大数目
		valueField: 'id', // 值的字段
		labelField: 'title', // label 绑定字段
		searchField: 'title', //搜索字段
		options: roleoptions,
		dropdownParent: 'body'			
	});	
	
	var control = $rolesel[0].selectize;
	control.setValue([${requestScope.roleid}]);
});



</script>
<style>
* {
	border:0;
	margin:0;
	padding:0;
}
body {
	min-width:0;
}
.vocation{float:left;}
</style>
</head>

<body>
<div class="place"> <span>位置：</span>
  <ul class="placeul">
    <li>用户角色</li>
  </ul>
</div>
<!--header-->
<div class="formbody formborder" >
  <form method="post" name="submitform" id="submitform">
  	<input type="hidden" name="id" value="${requestScope.id}" />
    <ul class="forminfo" style="padding-left:0;">
      <li>
        <label>角色<b>*</b></label>
        <div class="vocation">
          <select id="role-sel" class="" name="rolegroup" placeholder="请选择角色……" style="width:300px;"></select>
        </div>
      </li>
    </ul>
  </form>
</div>
</body>
</html>
