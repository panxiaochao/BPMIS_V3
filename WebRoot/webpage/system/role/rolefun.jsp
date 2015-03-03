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
<!-- ztree -->
<link rel="stylesheet" href="plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="plugin/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="plugin/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="plugin/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<!--easyui-->
<link rel="stylesheet" type="text/css" href="plugin/easyui/themes/metro-blue/easyui.css">
<link rel="stylesheet" type="text/css" href="plugin/easyui/themes/icon.css">
<script type="text/javascript" src="plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
var funids = new Array();
var submitForm = function($dialog, $grid, $pjq) {
	var url = "role/saverolefun.do";		
	//alert($("#submitform").serialize());
	if(funids.length > 0)
		_ajaxCall.post(url, {funid: funids.join(';'), roleid: '${requestScope.roleid}'}, function(data){				
			if(data.success){
				$pjq.messager.alert('提示', data.msg, 'info');
				//$grid.datagrid('load');
				$dialog.dialog('destroy');
			}else{
				$pjq.messager.alert('提示', data.msg, 'error');
				//$dialog.dialog('destroy');
			}			
		}, "json", false, {});
	else
		$pjq.messager.alert('提示', "请选择菜单！", 'info');
	
};


// ztree
var ztree_str = "fun_ztree";
var setting = {
	view: {
		dblClickExpand: false

	},
	check: {
		enable: true,
		chkStyle: "checkbox",
		chkboxType:  { "Y" : "ps", "N" : "ps" }
	},
	data: {
		keep: {
			parent: true
		},
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeExpand: beforeExpand,
		onExpand: onExpand,
		onClick: onClick,
		onCheck: zTreeOnCheck
	}
};

var curExpandNode = null;
function beforeExpand(treeId, treeNode) {
	var pNode = curExpandNode ? curExpandNode.getParentNode():null;
	var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
	var zTree = $.fn.zTree.getZTreeObj(ztree_str);
	for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
		if (treeNode !== treeNodeP.children[i]) {
			zTree.expandNode(treeNodeP.children[i], false);
		}
	}
	while (pNode) {
		if (pNode === treeNode) {
			break;
		}
		pNode = pNode.getParentNode();
	}
	if (!pNode) {
		singlePath(treeNode);
	}

}
function singlePath(newNode) {
	if (newNode === curExpandNode) return;
	if (curExpandNode && curExpandNode.open==true) {
		var zTree = $.fn.zTree.getZTreeObj(ztree_str);
		if (newNode.parentTId === curExpandNode.parentTId) {
			zTree.expandNode(curExpandNode, false);
		} else {
			var newParents = [];
			while (newNode) {
				newNode = newNode.getParentNode();
				if (newNode === curExpandNode) {
					newParents = null;
					break;
				} else if (newNode) {
					newParents.push(newNode);
				}
			}
			if (newParents!=null) {
				var oldNode = curExpandNode;
				var oldParents = [];
				while (oldNode) {
					oldNode = oldNode.getParentNode();
					if (oldNode) {
						oldParents.push(oldNode);
					}
				}
				if (newParents.length>0) {
					zTree.expandNode(oldParents[Math.abs(oldParents.length-newParents.length)-1], false);
				} else {
					zTree.expandNode(oldParents[oldParents.length-1], false);
				}
			}
		}
	}
	curExpandNode = newNode;
}

function onExpand(event, treeId, treeNode) {
	curExpandNode = treeNode;
}

function onClick(e,treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(ztree_str);
	zTree.expandNode(treeNode, null, null, null, true);				
}

// 下面是需要写的
function zTreeOnCheck(event, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(ztree_str);
	var checknodes = zTree.getCheckedNodes(true);
	funids = new Array();//解决重复选择问题
	for (var i=0, l=checknodes.length; i<l; i++) {
		//console.log(checknodes[i].name);
		funids.push(checknodes[i].id);
	}
	
};

$(document).ready(function(e) {
	var clientHeight = document.documentElement.clientHeight;
	$(".formborder").css("min-height",clientHeight - 68);
	$(".noresize").css("resize","none");
	//ztree
	var $ztree_str = $("#"+ztree_str);
	$.fn.zTree.init($ztree_str, setting, ${requestScope.ztree});
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
</style>
</head>

<body>
<div class="place"> <span>位置：</span>
  <ul class="placeul">
    <li>菜单权限</li>
  </ul>
</div>
<!--header-->
<div class="formbody formborder" >
  <ul id="fun_ztree" class="ztree">
  </ul>
</div>
</body>
</html>
