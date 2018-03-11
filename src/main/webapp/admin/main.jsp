<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
Hi, <c:out value="${admin.email}"/> !
<br>
<form action="controller" method="GET">
    <input type="hidden" name="command" value="users"/>
    <input type ="hidden" name="pageNumber" value="1"/>
    <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">
    Watch users list
</button>
</form>
<form action="controller" method="GET">
    <input type="hidden" name="command" value="campaigns"/>
    <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">
        Watch campaigns list
    </button>
</form>
<form action="controller" method="GET">
    <input type="hidden" name="command" value="donations"/>
    <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">
        Watch donations list
    </button>
</form>
<form name="logoutForm" method="POST" action="controller" class="w3-right-align" >
        <input type="hidden" name="command" value="adminLogout" />
        <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-top">Log out</button>
</form>