<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp"/>
<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">
        <jsp:useBean id="campaign" type="com.yermilov.domain.Campaign" scope="request"/>
        <div class="w3-container w3-green w3-opacity ">
            <h1><c:out value="${campaign.name}"/></h1>
            <label> <c:out value="${campaign.description}"/></label>
            <label> <c:out value="${moneyRaised}/${campaign.needToRaise}"/></label>
            <label> <c:out value="${campaign.organiser}"/> </label>
        </div>
        <label class="w3-text-red">${errorMessage}</label>
        </form>
        <div class="w3-container w3-opacity w3-right-align w3-padding">
            <button class="w3-btn w3-green w3-round-large w3-border" onclick="location.href='/sciencefunding/'">Back to main</button>
        </div>
        <br><br>
        <!--{users}></!-->
    </div>
</div>
</body>
</html>

