<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp"/>
<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">
        <jsp:useBean id="campaigns" type="java.util.List<com.yermilov.domain.Campaign>" scope="request"/>
        <c:choose>
            <c:when test="${campaigns!=null&&campaigns.size()>0}">
                <table class="w3-table-all w3-light-green">
                    <tr>
                        <td>Name</td>
                        <td>Domain</td>
                        <td>Need to raise</td>
                        <td>Learn more</td>
                    </tr>


                    <c:forEach var="campaign" items="${campaigns}"><tr>
                        <!--<td>${user.id}</td> -->
                        <td>${campaign.name}</td>
                        <td>${campaign.domain.name}</td>
                        <td>${campaign.needToRaise}</td>
                        <td><form action="controller" method="get">
                            <input type="hidden" name="command" value="campaign" />
                            <input type="hidden" name="campaignId" value=${campaign.id} >
                            <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Learn more</button>
                        </form></td>
                    </tr></c:forEach>
                </table>
                <c:if test="${param.pageNumber>1}"><a href="controller?command=campaigns&pageNumber=${param.pageNumber-1}">Previous page</a></c:if>
                <c:if test="${param.pageNumber<pageAmount}"><a href="controller?command=campaigns&pageNumber=${param.pageNumber+1}">Next page</a></c:if>

            </c:when>
            <c:otherwise>
                Sorry, there are no campaigns available now. But maybe you want to create your own?
            </c:otherwise>
        </c:choose>
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

