<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!-- Needed args -->
<%@ attribute name="linkGenerated" required="true" type="java.lang.String" description="Url to this page" %>
<%@ attribute name="currentPage" required="false" type="java.lang.Integer" description="Page being displayed" %>
<%@ attribute name="totalCount" required="true" type="java.lang.Integer" description="Total count" %>
<%@ attribute name="itemPerPage" required="true" type="java.lang.Integer" description="Items per page" %>


<!-- Name of the get params generated -->
<%@ attribute name="paramNameUrlCurrent" required="true" type="java.lang.String" description="Name of get parameter" %>


<!--if currentpage is 10 and 'numberPageLeftRight'=3 => 7-8-9-currentpage-11-12-13 -->
<%@ attribute name="numberPageLeftRight" required="false" type="java.lang.Integer"
              description="Number of page generated on left and right" %>

<!-- Default value for numberPageLeftRight -->
<c:if test="${ empty numberPageLeftRight }">
    <c:set var="numberPageLeftRight" value="2"/>
</c:if>


<!-- Checking for default values -->
<c:if test="${ itemPerPage eq 0 }">
    <c:set var="itemPerPage" value="20"/>
</c:if>

<!-- Default current page -->
<c:if test="${ empty currentPage }">
    <c:set var="currentPage" value="0"/>
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

<!-- Bean -->
<jsp:useBean id="paramUtils" scope="page" class="bean.BeanParamUtils"/>
${paramUtils.copyGetParameterFromRequest(pageContext.request)}


<!-------------------------------------------------------------------------------------------------------->
<!--------------------------------HTML GENERATION FOR PAGINATION------------------------------------------>
<!-------------------------------------------------------------------------------------------------------->
<ul class="pagination">
    <!-- Generation of [1] [...] [currentpage-1] [currentpage] etc-->
    <c:choose>
        <c:when test="${beginLoop > numberPageLeftRight}">
            ${paramUtils.overrideParam(paramNameUrlCurrent, 0)}
            <li><a href="${pageContext.request.contextPath}/${linkGenerated}${paramUtils.buildUrl()}">1</a></li>
            <li class="disabled"><a href="#">...</a></li>
        </c:when>
        <c:otherwise>
            <c:set var="beginLoop" value="0"/>
        </c:otherwise>
    </c:choose>

    <!-- Now we check if we can avoid putting [CurrentPage] [...] [Endpage] by generating  [CurrentPage] [CurrentPage+1] [Endpage]-->
    <c:if test="${not(endLoop < pageCount - numberPageLeftRight)}">
        <c:set var="endLoop" value="${pageCount}"/>
    </c:if>

    <c:if test="${currentPage < numberPageLeftRight + 2.0}">
        <c:set var="endLoop" value="${Math.min(pageCount, endLoop +  (numberPageLeftRight-currentPage)+2.0)}"/>
    </c:if>



    <c:if test="${currentPage > pageCount - numberPageLeftRight - 2.0}">
        <c:set var="beginLoop" value="${Math.max(0.0, beginLoop - (currentPage + numberPageLeftRight - pageCount) - 2.0)}"/>
    </c:if>


    <!-- Generation of pages on left and right of the current one -->
    <c:forEach begin="${beginLoop}" end="${endLoop}" varStatus="loop">
        ${paramUtils.overrideParam(paramNameUrlCurrent, loop.index)}
        <li <c:if test="${currentPage eq loop.index}">class="active"</c:if>>
            <a href="${pageContext.request.contextPath}/${linkGenerated}${paramUtils.buildUrl()}">${loop.index+1}</a>
        </li>
    </c:forEach>

    <!-- Generation of [currentpage] [currentpage+1] [...] [LASTPAGE] etc-->
    <!-- parsing of pagecount+1 from double to integer -->
    <fmt:parseNumber var="i" integerOnly="true" type="number" value="${pageCount+1}"/>
    <c:if test="${endLoop < pageCount - numberPageLeftRight}">
        ${paramUtils.overrideParam(paramNameUrlCurrent, pageCount)}
        <li class="disabled"><a href="#">...</a></li>
        <li><a href="${pageContext.request.contextPath}/${linkGenerated}${paramUtils.buildUrl()}"><c:out
                value="${i}"/></a></li>
    </c:if>
</ul>


