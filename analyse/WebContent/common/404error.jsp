<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<title>错误页提示</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${path }/css/layout/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${path }/js/jquery-min-1.6.2.js"></script>
	<script language="javascript">
		$(function(){
	    $('.error').css({'position':'absolute','left':($(window).width()-490)/2});
		$(window).resize(function(){  
	    $('.error').css({'position':'absolute','left':($(window).width()-490)/2});
	    })  
	});  
	</script> 
	
</head>
<body style="background:#edf6fa;">
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li></li>
    <li><a href="javascript:void(0)">错误提示</a></li>
    </ul>
    </div>
    
    <div class="error">
    
    <h2>非常遗憾，您访问的页面不存在！</h2>
    <p></p>
    <div class="reindex" ><a href="${path }"> 返回首页</a></div>
    
    </div>
</body>
</html>