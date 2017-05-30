<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Needed args -->
<%@ attribute name="displayLength" required="true" type="java.util.ArrayList<java.lang.Integer>" description="Page length displayed available for user" %>
<%@ attribute name="paramNameLengthList" required="true" type="java.lang.String" description="Name of get parameter" %>
<%@ attribute name="linkGenerated" required="true" type="java.lang.String" description="Url to this page" %>


<!-- Bean -->
<jsp:useBean id="paramUtils" scope="page" class="cdb.bean.BeanParamUtils"/>
${paramUtils.copyGetParameterFromRequest(pageContext.request)}

<!-------------------------------------------------------------------------------------------------------->
<!----------------------------------HTML PAGE LENGTH GENERATION------------------------------------------->
<!-------------------------------------------------------------------------------------------------------->

<div class="btn-group btn-group-sm pull-right" role="group">
    <c:forEach var="currentDisplay" items="${displayLength}">
        ${paramUtils.overrideParam(paramNameLengthList, currentDisplay)}
        <a href="${pageContext.request.contextPath}/${linkGenerated}${paramUtils.buildUrl()}"
           class="btn btn-default <c:if test="${requestScope.get(paramNameLengthList) == currentDisplay}">active</c:if>">${currentDisplay}</a>
    </c:forEach>
</div>