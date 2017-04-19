<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Needed args -->
<%@ attribute name="displayLength" required="true" type="java.util.ArrayList<java.lang.Integer>" description="Page length displayed" %>
<%@ attribute name="paramNameLengthList" required="true" type="java.lang.String" description="Name of get parameter" %>
<%@ attribute name="linkGenerated" required="true" type="java.lang.String" description="Url to this page" %>



<!-- Bean -->
<jsp:useBean id="paramUtils" scope="page" class="bean.BeanParamUtils"/>
${paramUtils.copyGetParameterFromRequest(pageContext.request)}

<!-------------------------------------------------------------------------------------------------------->
<!-----------------------------------------HTML PAGE LENGTH----------------------------------------------->
<!-------------------------------------------------------------------------------------------------------->

<div class="btn-group btn-group-sm pull-right" role="group">
    <c:forEach var="currentDisplay" items="${displayLength}">
        ${paramUtils.overrideParam(paramNameLengthList, currentDisplay)}
        <a href="${pageContext.request.contextPath}/${linkGenerated}${paramUtils.buildUrl()}" class="btn btn-default">${currentDisplay}</a>
    </c:forEach>
</div>