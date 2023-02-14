<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<h1><a href="${pageContext.request.contextPath }/${blogVo.id}">${blogVo.title }</a></h1>
<ul>
	<c:if test="${empty authUser }">
		<li><a href="${pageContext.request.contextPath }/user/login/${blogVo.id}">로그인</a></li>
	</c:if>
	<c:if test="${not empty authUser }">
		<li><a href="${pageContext.request.contextPath }/user/logout/${authUser.id}">로그아웃</a></li>
	</c:if>
	<c:if test="${authUser.id == blogVo.id }">
		<li><a href="${pageContext.request.contextPath }/${blogVo.id}/admin">블로그 관리</a></li>
	</c:if>
	<li><a href="${pageContext.request.contextPath }">메인 페이지로</a></li>
	
</ul>