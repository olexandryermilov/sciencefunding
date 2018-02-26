<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp"/>
<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">
            <table class="w3-table-all w3-light-green">
                <tr>
                    <td>Id</td>
                    <td>Name</td>
                    <td>Surname</td>
                    <td>Email</td>
                    <td>Is active</td>
                    <td>Change state</td>
                    <td>Register as scientist</td>
                </tr>
                <jsp:useBean id="users" type="java.util.List<com.yermilov.domain.User>" scope="request"/>

                <c:forEach var="user" items="${users}"><tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.email}</td>
                    <td>${user.isActive}</td>
                    <td><form action="controller" method="post">
                        <input type="hidden" name="command" value="delete" />
                        <input type="hidden" name="userid" value=${user.id} >
                        <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Change state</button>
                    </form></td>
                    <td>
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="addScientist"/>
                            <input type="hidden" name="userid" value=${user.id} >
                            <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Register as scientist</button>
                        </form>
                    </td>
                </tr></c:forEach>
            </table>
        <c:if test="${param.pageNumber>1}"><a href="controller?command=users&pageNumber=${param.pageNumber-1}">Previous page</a></c:if>
        <c:if test="${param.pageNumber<pageAmount}"><a href="controller?command=users&pageNumber=${param.pageNumber+1}">Next page</a></c:if>
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

