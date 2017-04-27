<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Needed args -->
<%@ attribute name="linkGenerated" required="true" type="java.lang.String" description="Url to this page" %>
<%@ attribute name="innerhtml" required="true" type="java.lang.String" description="inner html" %>

<%@ attribute name="ascgetparameter" required="true" type="java.lang.String" description="name of get parameter modified" %>
<%@ attribute name="ordergetparameter" required="true" type="java.lang.String" description="name of get parameter modified" %>

<%@ attribute name="value-asc" required="true" type="java.lang.String" description="name of get parameter modified" %>
<%@ attribute name="value-order" required="true" type="java.lang.String" description="name of get parameter modified" %>


<!-- Bean -->
<jsp:useBean id="paramUtils" scope="page" class="bean.BeanParamUtils"/>
${paramUtils.copyGetParameterFromRequest(pageContext.request)}
${paramUtils.overrideParam(ascgetparameter, value-asc)}
${paramUtils.overrideParam(ordergetparameter, value-order)}


<!-------------------------------------------------------------------------------------------------------->
<!----------------------------------HTML PAGE LENGTH GENERATION------------------------------------------->
<!-------------------------------------------------------------------------------------------------------->

<div class="btn-group btn-group-sm pull-right" role="group">
    <a href="${pageContext.request.contextPath}//${linkGenerated}${paramUtils.buildUrl()}"
       class="btn btn-default">${innerhtml} TEEEST</a>
</div>