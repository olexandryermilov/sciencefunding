<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
Hi, <c:out value="${currentUser.name}"/>
<br>
<button class="w3-btn w3-green w3-round-large w3-margin-bottom" onclick="location.href='/sciencefunding/addCampaign.jsp'">Add campaign</button>
<form name="logoutForm" method="GET" action="controller" >
        <input type="hidden" name="command" value="ridesStatistics" />
        <input type ="hidden" name="pageNumber" value="1"/>
        <button type="submit" class="w3-btn w3-green w3-round-large ">Statistics</button>
</form>
<form name="logoutForm" method="POST" action="controller" class="w3-right-align" >
        <input type="hidden" name="command" value="logout" />
        <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-top">Log out</button>
</form>

