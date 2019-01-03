<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<%
int currentPage = 1;
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/UInnovation";
String url = request.getRequestURI();
//判断外网
/* if(url.indexOf(".com")>0 || url.indexOf(".cn")>0 || url.indexOf(".net")>0) {
	basePath = request.getRemoteHost();
} */
	basePath = "http://org.lzy.com/UInnovation";

%>
<head>
    <title>广州青年创新创业综合管理平台</title>
    <meta content="text/html;charset=UTF-8" http-equiv="Content-Type"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/reset.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/paging.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/guide.css">
    <style> 
      .bg_s_btn{
         width:100px;
         height:34px;
         color:#FFF;
         font-size: 20px;
         letter-spacing: 1px;
         background: none repeat scroll 0% 0% #3385FF;
         border-bottom: 1px solid #2D78F4;
         outline: medium none;
         margin-bottom:auto;
      }
    .bg_s_input{
        border-color: #B8B8B8 transparent #CCC #B8B8B8;
        display: inline-block;
        border-style: solid;
        border-width: 1px 0px 1px 1px;
        height:25px;
    }
    .tableStyle{
       cellpadding:1px;
       cellspacing:1px;
       border:1px; 
       width:80%;
       fontSize:12px;
    }
     .lb_bt1 td{
     text-align:left;
     border-right:1px solid #E1E1E1;
     height:25px;
     width:60%;
     }
    <!-- 搜索框样式 -->
	.search {
	  width: 100%;
	  display: inline-block;
	  padding: 36px 0;
	}
	.search .search-main {
	  width: 940px;
	  margin: 0 auto;
	  padding-top: 10px;
	}
	.search .search-main .searchDiv {
	  width: 940px;
	  overflow-x: auto;
	}
	.search .search-main .searchDiv .search-list {
	  min-width: 720px;
	  height: 40px;
	}
	.search .search-main .searchDiv .search-list li {
	  padding: 0 16px;
	  height: 100%;
	  font-size: 12px;
	  text-align: center;
	  line-height: 32px;
	}
	.search .search-main .searchDiv .search-list li a {
	  display: block;
	  width: 100%;
	  text-decoration: none;
	  color: #999;
	}
	.search .search-main .searchDiv .search-list > li.active {
	  background: url(../../images/front/liactive.png);
	}
	.search .search-main .searchDiv .search-list > li.active a {
	  color: #fff;
	}
	.search form {
	  width: 100%;
	  overflow: hidden;
	  height: 45px;
	  border: 1px solid #e5e5e5;
	}
	.search form input {
	  float:left;
	  width: 790px;
	  background: none;
	  outline: none;
	  padding: 13px 0;
	  text-indent: 14px;
	  color: #999;
	  border: none;
	}
	.search form p {
	  float:right;
	  width: 150px;
	  color: #fff;
	  height: 100%;
	  line-height: 45px;
	  text-align: center;
	  background-color: #e1455d;
	}
    </style>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-min-1.6.2.js"></script>
    <script type="text/javascript">
        function cx(){
             var content=document.getElementById("content").value;
             if(content==""||content==null){
               document.getElementById("content").focus();
               return false;
             }
             form1.submit();
             return true;
        }
        function getPages(arg0){
        	$("#content2").val($("#content").val());
        	console.log($("#content2").val());
            form2.action="/analyse/servlet/AnalyseSearchServlet?page_curPage="+arg0;
            form2.submit();
        }
        
        function jumpPage(arg0){
        	$("#content2").val($("#content").val());
        	console.log($("#content2").val());
            form2.action="/analyse/servlet/AnalyseSearchServlet?page_curPage="+arg0;
            form2.submit();
        }
    </script>
</head>
<body>
<!-- 	<div align="center"> -->
<!-- 	   <form id="form1" name="form1" method="post" action="/analyse/servlet/AnalyseSearchServlet"> -->
<!-- 	      <div> -->
<!-- 	      <div><font size="20">全文检索</font></div> -->
<%-- 	      <div><input class="bg_s_input" id="content" name="contentStr" type="text" value="${content}" style="width:300px;"/><input type="submit" class="bg_s_btn" value="查询" onclick="return cx();"/> --%>
<!-- 	       </div></div> -->
<!-- 	       <input name="page_curPage" value="1" type="hidden"/> -->
<!-- 	       <input name="page_size" value="10" type="hidden"/> -->
<!-- 	   </form> -->
<!-- 	</div> -->
	<!-- 搜索框 -->
	<div class="search">
		<div class="wrap">
			<div class="search-main">
				<div><a href="<%=basePath %>">返回首页</a></div>
				<div class="searchDiv">
					<ul class="search-list" style="height:auto;">
						<li style="font-size:36px;margin:20px 0" ><a href="javascript:void(0);"> 全站检索 </a></li>
					</ul>
				</div>
				<form id="form1" name="form1" method="post" action="/analyse/servlet/AnalyseSearchServlet">
					<input type="text" id="content" name="contentStr" placeholder="请输入搜索内容" value="${content}" />
					<p onclick="return cx();" style="cursor: pointer;">搜索</p>
<%-- 					<input name="returnUrl" value="${returnUrl}" type="hidden" /> --%>
					<input name="page_curPage" value="1" type="hidden"/>
	       			<input name="page_size" value="10" type="hidden"/>
				</form>
			</div>
		</div>
	</div>
	<!-- main -->
    <div class="main">
    	<div class="wrap">
    	<c:if test="${flag==1}">
    		<!-- 列表 -->
    		<div class="guide_list clearfix" style="margin-left:80px;">
    			<ul>
    			<c:choose>
			       <c:when test="${fn:length(list)>0}">
			          <c:forEach var="u" items="${list}" varStatus="status">
					    <li>
					    <!-- 列表内容 -->
    					<div class="li_main">
    						<div class="title">
    							<a href="<%=basePath %>${u.doc.url}">
    								[${u.doc.navi }]&nbsp;${u.doc.title }
    							</a>
    						</div>
    						<div class="li_nr clearfix">
    							<a href="<%=basePath %>${u.doc.url}">
    								<span class="nr">
    									${u.doc.content}
    								</span>
    								<span class="nr">
    									发布时间：${u.create_date}
    								</span>
    								<span class="arrow_right">
    									<i></i>
    								</span>
    							</a>
    						</div>
    					</div>
    					</li>
				     </c:forEach>
			       </c:when>
		           <c:otherwise>
		              <li><font color="red">查询不到数据！</font></li>
		           </c:otherwise>
		        </c:choose>
    			</ul>
	<!-- 通用分页开始 -->
<c:if test="${fn:length(list)>0}">
			          
<form id="form2" name="form2" method="post">
	<input type="hidden" name="contentStr" id="content2" />
   <div class="paging">
  	<ul>
		<c:if test="${curPage > 1}">  <!-- 如果页面大于一页，首页和上一页都可以操作 -->
			<li class="liW">
				<a href="javascript:void(0)" onclick="jumpPage(1)">首页</a>
	      	</li>
		    <li class="liW">
				<a href="javascript:void(0)" onclick="jumpPage(${curPage-1})">上一页</a>
			</li>
	    </c:if>
		<c:if test="${curPage <= 1}">   <!-- 如果页面最多一页，首页和上一页都一样的操作，无需写页面改变事件 -->
	    	<li class="liW">
	      		<a href="javascript:void(0)">首页</a>
	      	</li>
	      	<li class="liW">
	      		<a href="javascript:void(0)">上一页</a>
	      	</li>　 
	    </c:if>
	    <c:if test="${curPage <= 3 && (curPage+2) < totalPages}">
	    	<c:forEach var="i" begin="1" end="${curPage+2}">
				<c:if test="${i == curPage }">
					<li class="currentLis"><a href="javascript:void(0)">${i}</a></li>  
				</c:if>
				<c:if test="${i != curPage }">
					<li><a href="javascript:void(0)" onclick="jumpPage(${i})">${i}</a></li>
				</c:if>							
	  		</c:forEach>
	    </c:if>
	    <c:if test="${curPage <= 3 && (curPage+2) >= totalPages}">
	    	<c:forEach var="i" begin="1" end="${totalPages}">
				<c:if test="${i == curPage }">
					<li class="currentLis"><a href="javascript:void(0)">${i}</a> </li>  
				</c:if>
				<c:if test="${i != curPage }">
					<li><a href="javascript:void(0)" onclick="jumpPage(${i})">${i}</a></li>
				</c:if>								
	  		</c:forEach>
	    </c:if>
    	<c:if test="${curPage > 3 && (curPage+2) < totalPages}">
	   		<c:forEach var="i" begin="${curPage-2}" end="${curPage+2}">
				<c:if test="${i == curPage }">
					<li class="currentLis"><a href="javascript:void(0)">${i}</a> </li>  
				</c:if>
				<c:if test="${i != curPage }">
					<li><a href="javascript:void(0)" onclick="jumpPage(${i})">${i}</a></li>
				</c:if>							
	  		</c:forEach>
	    </c:if>
	    <c:if test="${curPage > 3 && (curPage+2) >= totalPages}">
	    	<c:forEach var="i" begin="${curPage-2}" end="${totalPages}">
				<c:if test="${i == curPage }">
					<li class="currentLis"><a href="javascript:void(0)">${i}</a> </li>  
				</c:if>
				<c:if test="${i != curPage }">
					<li><a href="javascript:void(0)" onclick="jumpPage(${i})">${i}</a></li>
				</c:if>								
	  		</c:forEach>
	    </c:if>
        <c:if test="${curPage < totalPages}">
			<li class="liW">
	       		<a href="javascript:void(0)" onclick="jumpPage(${curPage+1})">下一页</a>
	    	</li>
		    <li class="liW">
		       	<a href="javascript:void(0)" onclick="jumpPage(${totalPages})">尾页</a>
		    </li>　
	    </c:if>
		<c:if test="${curPage != null and curPage >= totalPages}">
	    	<li class="liW">
	       		<a href="javascript:void(0)">下一页</a>
	       	</li>
	   	   	<li class="liW">
	       	  	<a href="javascript:void(0)"> 尾页</a>	
	       	</li>　 
	  	</c:if>
    </ul>
    <div>共<b>${total }</b>条信息分<b>${totalPages }</b>页 每页显示<b>10</b>条 当前显示第<b>${curPage }</b>页</div>
  </div>
</form>
</c:if>
<!-- 通用分页结束 --> 

			</div>
		</c:if>
    	</div>
	</div>
<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.min.js"></script> --%>
<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/js/overtext.js"></script> --%>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/query.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/js/paging.js"></script> --%>
<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/js/guide.js"></script> --%>
</body>
</html>