<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp"/>
<div class="w3-card-4">
    <div class="w3-container ">
        <form name="loginForm" method="POST" action="controller">
            <input type="hidden" name="command" value="donate" />
            <input type="hidden" name="campaignId" value="${param.campaignId}" />
            <div class="w3-text-black">Money:<br/></div>
            <input type="number" name="money" value="" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"/>
            <br/>Your comment<br/>
            <input type="text" name="description" value="" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"/>
            <br/>
            <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Submit money</button>
            <label class="w3-text-red">${errorMessageLogin}<br/></label>
        </form>
    </div>
</div>
<div class="w3-container w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-green w3-round-large w3-border" onclick="location.href='/sciencefunding'">Back to main</button>
</div>

</body>
</html>
