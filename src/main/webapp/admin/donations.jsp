<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp"/>
<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">
            <table class="w3-table-all w3-light-green">
                <tr>
                    <td>Id</td>
                    <td>From user</td>
                    <td>To campaign</td>
                    <td>Value</td>
                    <td>Comment</td>
                </tr>
                <jsp:useBean id="donations" type="java.util.List<com.yermilov.domain.Donation>" scope="request"/>

                <c:forEach var="donation" items="${donations}"><tr>
                    <td>${donation.id}</td>
                    <td>${donation.fromUser.email}</td>
                    <td>${donation.toCampaign.name}</td>
                    <td>${donation.value}</td>
                    <td>${donation.comment}</td>
                </tr></c:forEach>
            </table>
        <c:if test="${param.pageNumber>1}"><a href="controller?command=donations&pageNumber=${param.pageNumber-1}">Previous page</a></c:if>
        <c:if test="${param.pageNumber<pageAmount}"><a href="controller?command=donations&pageNumber=${param.pageNumber+1}">Next page</a></c:if>
        <label class="w3-text-red">${errorMessage}</label>
        </form>
        <div class="w3-container w3-opacity w3-right-align w3-padding">
            <button class="w3-btn w3-green w3-round-large w3-border" onclick="location.href='/sciencefunding/admin'">Back to main</button>
        </div>
        <br><br>
        <!--{users}></!-->
    </div>
</div>
</body>
</html>

