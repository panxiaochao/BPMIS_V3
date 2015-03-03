<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<c:out value="${webRoot}" />">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<!--easyui-->
<link rel="stylesheet" type="text/css" href="plugin/easyui/themes/metro-gray/easyui.css">
<link rel="stylesheet" type="text/css" href="plugin/easyui/themes/icon.css">
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/jquery.ajaxCall.js"></script>
<script type="text/javascript" src="plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="plugin/easyui/locale/easyui-lang-zh_CN.js"></script>
<!--highcharts-->
<script src="plugin/highcharts/js/highcharts.js"></script>
<script src="plugin/highcharts/js/themes/grid-light.js"></script>
<script type="text/javascript">
var chart;
var opt;
$(function() {
	//var colors = Highcharts.getOptions().colors
	opt = {
		credits: {
			enabled: false
		},
		chart: {
			type: 'column',
			renderTo:'container',
			height: $(window).height()-100
			//zoomType: 'x' //放大功能			
		},
		yAxis: {							
			title: {
				enabled: false
			}
		},
		tooltip: {
			formatter:function(){
				return ''+ this.point.category +'<br/><b>'+this.series.name+'：'+this.y + ' 元</b>';  
			}
		},
		legend: { 
			layout: 'horizontal', 
			borderWidth: 0
		},
		plotOptions: {
			column: {
				dataLabels: {
					enabled: true
				}
			}
		},
		exporting: {
			enabled: false
		}	
	};
	//chart = new Highcharts.Chart(opt);
});
function Hsearch() {
	var url = "hicharts/hibar.do";	
	//alert($("#submitform").serialize());
	var dp1 = $('#dp1').datebox('getValue');
	var dp2 = $('#dp2').datebox('getValue');
	
	if(dp1=="" || dp2==""){
		$.messager.alert('提示', "请填写时间！", 'info');
	}else{
		if(dp1>dp2){
			$.messager.alert('注意','截止日期应大于起始日期','warning');	 
		}else{
			$.messager.progress({
				title : '提示',
				text : '数据处理中，请稍后....'
			});
			_ajaxCall.post(url, {starttime: dp1,endtime: dp2, }, function(data){	
				var categ = data.xAxis;			
				$.extend(opt, {title: {text: data.title }});
				$.extend(opt, {xAxis: {categories: categ.split(';') }});			
				$.extend(opt, {series: data.list});
				chart = new Highcharts.Chart(opt);
				$.messager.progress('close');
			}, "json", false, {});
		}		
	}
}
</script>
</head>

<body id="index_layout" class="easyui-layout">
<!--header-->
<div id="navhead" data-options="region:'north'" border="false" style="height:40px;">
  <div class="place"> <span>位置：</span>
    <ul class="placeul">
      <li><a href="#">首页</a></li>
      <li>数据统计</li>
    </ul>
  </div>
</div>
<!--主体-->
<div style="margin-top:40px; width:100%;">
  <div class="easyui-panel" style="padding:5px; margin:5px 0;"> 开始日期:
    <input class="easyui-datebox" style="width:100px" id="dp1" />
    &nbsp;|&nbsp;截止日期:
    <input class="easyui-datebox" style="width:100px" id="dp2" />
    <!--&nbsp;|&nbsp;支付类别:
    <select class="easyui-combobox" panelHeight="auto" style="width:100px" id="">
      <option value="java">Java</option>
      <option value="c">C</option>
      <option value="basic">Basic</option>
      <option value="perl">Perl</option>
      <option value="python">Python</option>
    </select>-->
    &nbsp;|&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="Hsearch()">搜索&nbsp;</a> </div>
</div>

<div id="container" style=" width:99%; margin:0 auto;"></div>
<script>
$('#dp1').datebox({    
	required:true 
});  

$('#dp2').datebox({    
    required:true   
});  


</script>
</body>
</html>
