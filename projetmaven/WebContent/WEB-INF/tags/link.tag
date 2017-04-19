<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="linkGenerated" required="true" type="java.lang.String" description="Url to this page" %>
<%@ attribute name="currentPage" required="false" type="java.lang.Integer" description="Page being displayed" %>
<%@ attribute name="totalCount" required="true" type="java.lang.Integer" description="Total count" %>
<%@ attribute name="itemPerPage" required="true" type="java.lang.Integer" description="Item per page" %>

<!--if currentpage is 10 and 'numberPageLeftRight'=3 => 7-8-9-10-11-12-13 -->
<%@ attribute name="numberPageLeftRight" required="false" type="java.lang.Integer"
              description="Number of page generated on left and right" %>


<!-- Name of the get params generated -->
<%@ attribute name="paramNameUrlCurrent" required="true" type="java.lang.String" description="Name of get parameter" %>
<%@ attribute name="paramNameUrlItemPerPage" required="true" type="java.lang.String"
              description="Name of get parameter" %>


<!-- Checking for default values -->
<c:if test="${ itemPerPage eq 0 }">
    <c:set var="itemPerPage" value="20"/>
</c:if>

<!-- Default current page -->
<c:if test="${ empty currentPage }">
    <c:set var="currentPage" value="0"/>
</c:if>


<!-- Default value for numberPageLeftRight -->
<c:if test="${ empty numberPageLeftRight }">
    <c:set var="numberPageLeftRight" value="3"/>
</c:if>


<!-- Getting the total number of pages -->
<c:set var="pageCount" value="${Math.max(0.0, Math.ceil(totalCount / itemPerPage) - 1)}"/>


<!-- Left Page Generation -->
<c:choose>
    <c:when test="${ currentPage - numberPageLeftRight < 0}">
        <c:set var="beginLoop" value="0"/>
    </c:when>
    <c:otherwise>
        <c:set var="beginLoop" value="${currentPage-numberPageLeftRight}"/>
    </c:otherwise>
</c:choose>


<!-- Right Page Generation -->
<c:choose>
    <c:when test="${ currentPage + numberPageLeftRight < pageCount}">
        <c:set var="endLoop" value="${currentPage + numberPageLeftRight}"/>
    </c:when>
    <c:otherwise>
        <c:set var="endLoop" value="${pageCount}"/>
    </c:otherwise>
</c:choose>


<jsp:useBean id="paramUtils" scope="page" class="bean.BeanParamUtils"/>
${paramUtils.copyGetParameterFromRequest(pageContext.request)}
<table>
    <tr>
        <c:forEach begin="${beginLoop}" end="${endLoop}" varStatus="loop">
            ${paramUtils.overrideParam(paramNameUrlCurrent, loop.index+1)}
            <li>
                <a href="${pageContext.request.contextPath}/${linkGenerated}${paramUtils.buildUrl()}">${loop.index+1}</a>
            </li>
        </c:forEach>
    </tr>
</table>