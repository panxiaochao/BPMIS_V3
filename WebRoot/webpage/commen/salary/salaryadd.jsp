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
<!--easyui-->
<script type="text/javascript" src="plugin/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="plugin/easyui/themes/metro-blue/easyui.css">
<link rel="stylesheet" type="text/css" href="plugin/easyui/themes/icon.css">
<script type="text/javascript" src="plugin/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

var submitForm = function($dialog, $grid, $pjq) {
	var url = "salary/save.do";		
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

var bank_select = function(){
	var options = [];
	_ajaxCall.post("salary/getbank.do", {}, function(data){						
		$.each(data, function(i, n){
	    	options.push({
		        id: n.id,
		        title: n.bankname,
		        val:n.id+";"+n.bankname+"["+n.banktype+"]"
		    });
	    });				
	}, "json", false, {});	
	return options;
}

$(document).ready(function(e) {
	var clientHeight = document.documentElement.clientHeight;
	$(".formborder").css("min-height",clientHeight - 68);
	$(".noresize").css("resize","none");
	var xhr;
	var salsel, $salsel;
	var sortsel, $sortsel;
	var banksel, $banksel;
	
	$salsel = $('#sal-sel').selectize({
		create: false,
		valueField: 'val', // 值的字段
		labelField: 'title', // label 绑定字段
		searchField: 'title', //搜索字段
		options: [
	        {id: 1, title: '收入', val: 'in'},
	        {id: 2, title: '支出', val: 'out'}
	    ],
		dropdownParent: 'body',
		onChange: function(value) {
        	if (!value.length) return;
	        sortsel.disable();
	        sortsel.clearOptions();
	        sortsel.load(function(callback) {
	            xhr && xhr.abort();
	            xhr = $.ajax({
	                url: 'salary/getSort.do',
	                data: {val: value},
	                dataType: "json",
	                success: function(data) {
	                	//console.log(data);
	                	if(data.length != 0){
	                		sortsel.enable();
	                		var opt = [];
	                		$(data).each(function(i,n){
	                			opt.push({
							        id: n.id,
							        title: n.sortname,
							        val: n.sortname+"["+n.sorttype+"]"
							    });
	                		})	
	                    	callback(opt);
	                	}else
	                		alert("没有类别！");
	                },
	                error: function() {
	                    callback();
	                }
	            })
	        });
	    }
	});	
	//类别
	$sortsel = $('#sort-sel').selectize({
		create: false,
	    valueField: 'val',
	    labelField: 'title',
	    searchField: 'title'
	});
	
	var bankoptions = bank_select();
	$banksel = $('#bank-sel').selectize({
		create: false,
		valueField: 'val', // 值的字段
		labelField: 'title', // label 绑定字段
		searchField: 'title', //搜索字段
		options: bankoptions,
		dropdownParent: 'body'	
	});	
	
	sortsel = $sortsel[0].selectize;
	salsel = $salsel[0].selectize;
	
	sortsel.disable();
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
.combo{
	line-height: 32px !important;
	height: 32px !important;
}
.combo .combo-text {
	width:270px !important;	
	margin: 0px;
	padding-left:10px;	
	vertical-align: baseline;
}
</style>
</head>

<body>
<div class="place"> <span>位置：</span>
  <ul class="placeul">
    <li>添加账单</li>
  </ul>
</div>
<!--header-->
<div class="formbody formborder" >
  <form method="post" name="submitform" id="submitform">
    <ul class="forminfo" style="padding-left:0;">
      <li>
        <label>金额<b>*</b></label>
        <input name="money" type="text" class="dfinput" value=""  style="width:300px;"/>
      </li>
      <li>
        <label>资金流向<b>*</b></label>
        <div class="vocation">
          <select class="" id="sal-sel" name="trdetype" placeholder="请选择资金流向……" style="width:300px;"></select>
        </div>
      </li>
      <li>
        <label>交易类别<b>*</b></label>
        <div class="vocation">
          <select class="" id="sort-sel" name="sorttype" placeholder="请选择交易类别……" style="width:300px;"></select>
        </div>
      </li>     
      <li>
        <label>支付方式<b>*</b></label>
        <div class="vocation">
          <select class="" id="bank-sel" name="banktype" placeholder="请选择支付方式……" style="width:300px;"></select>
        </div>
      </li>        
      <li>
        <label>交易时间<b>*</b></label>
        <input name="tradetime" id="dp1" type="text" class="dfinput" value=""  style="width:300px;"/>
        <script>
			$('#dp1').datebox({    
				formatter: function (date) {
                    var y = date.getFullYear();
                    var m = date.getMonth() + 1;
                    var d = date.getDate();
                    return y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
                }  
			}); 
		</script>
      </li>
      <li>
        <label>备注<b>*</b></label>
        <textarea  name="summary" class="dfborder noresize"  style="width:300px;height:100px;"></textarea>
      </li>
    </ul>
  </form>
</div>
</body>
</html>
