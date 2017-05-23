<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Needed args -->
<%@ attribute name="linkGenerated" required="true" type="java.lang.String" description="Url to this page" %>
<%@ attribute name="innerhtml" required="true" type="java.lang.String" description="inner html" %>
<%@ attribute name="valueOrder" required="true" type="java.lang.String" description="name of get parameter modified" %>
<!-- Get Parameter -->
<%@ attribute name="ascGetParameter" required="true" type="java.lang.String"
              description="name of get parameter modified" %>
<%@ attribute name="orderGetParameter" required="true" type="java.lang.String"
              description="name of get parameter modified" %>



<!-- Checking the current state of order -->
<%@ attribute name="valueAsc" required="false" type="java.lang.Boolean"
              description="choose if chevron up / down" %>
<c:choose>
    <c:when test="${pageContext.request.getParameter(ascGetParameter).equals('true')
     && pageContext.request.getParameter(orderGetParameter).equals(valueOrder)}">
        <c:set var="valueAsc" value="true"/>
    </c:when>
    <c:otherwise>
        <c:set var="valueAsc" value="false"/>
    </c:otherwise>
</c:choose>
<c:set var="invValueAsc" value="${!valueAsc}"/>

<!-- Bean -->
<jsp:useBean id="paramUtils" scope="page" class="cdb.bean.BeanParamUtils"/>
${paramUtils.copyGetParameterFromRequest(pageContext.request)}
${paramUtils.overrideParam(ascGetParameter, invValueAsc)}
${paramUtils.overrideParam(orderGetParameter, valueOrder)}

<!-------------------------------------------------------------------------------------------------------->
<!----------------------------------HTML PAGE LENGTH GENERATION------------------------------------------->
<!-------------------------------------------------------------------------------------------------------->

<a href="${pageContext.request.contextPath}/${linkGenerated}${paramUtils.buildUrl()}">
    <spring:message code="${innerhtml}" />
    <c:choose>
        <c:when test="${valueAsc}"><i class="fa fa-chevron-up" aria-hidden="true"></i></c:when>
        <c:otherwise><i class="fa fa-chevron-down" aria-hidden="true"></i></c:otherwise>
    </c:choose>
</a>