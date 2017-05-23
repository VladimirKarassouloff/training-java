<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="css/bootstrap-datepicker.min.css" rel="stylesheet" media="screen">
    <link href="css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="css/main.css" rel="stylesheet" media="screen">
    <link href="css/bootstrapValidator.min.css" rel="stylesheet" media="screen">
    <style>
        .datepicker.datepicker-dropdown.dropdown-menu {
            z-index: 1600;
        }
    </style>
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="index"> Application - Computer Database </a>
    </div>
</header>

<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1>
                    <c:choose>
                        <c:when test="${formComputer.id != null}">
                            <spring:message code="computer.edit" />
                        </c:when>
                        <c:otherwise>
                            <spring:message code="computer.add" />
                        </c:otherwise>
                    </c:choose>
                </h1>
                <c:if test="${not empty error}">
                    <div id="error" class="alert alert-danger">
                            ${error}
                    </div>
                </c:if>

                <c:choose>
                    <c:when test="${formComputer.id != null}">
                        <c:set var="actionForm" value="?id=${formComputer.id}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="actionForm" value=""/>
                    </c:otherwise>
                </c:choose>

                <form:form id="myform" action="${actionForm}" method="POST" modelAttribute="formComputer">

                    <fieldset>
                        <div class="form-group">
                            <form:label path="name"><spring:message code="computer.name" /></form:label>
                            <form:input path="name" type="text" class="form-control" id="computerName"
                                        value="${formComputer.name}" name="name_computer"/>

                            <form:errors path="name" cssClass="text-danger"/>
                        </div>

                        <div class="form-group">
                            <form:label path="introduced" for="introduced"><spring:message code="computer.introduced" /></form:label>
                            <div class="input-group date introduced">
                                <form:input path="introduced" id="introduced" type="text" class="form-control"
                                            name="introduced_computer" value="${formComputer.introduced}"/><span
                                    class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                            </div>
                        </div>

                        <div class="form-group">
                            <form:label path="discontinued" for="discontinued"><spring:message code="computer.discontinued" /></form:label>
                            <div class="input-group date discontinued">
                                <form:input path="discontinued" id="discontinued" type="text" class="form-control"
                                            name="discontinued_computer" value="${formComputer.discontinued}"/><span
                                    class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="companyId"><spring:message code="company" /></label>
                            <form:select path="companyId" class="form-control" id="companyId" name="company_id_computer"
                                         value="${formComputer.companyId}">
                                <option value="" <c:if test="${formComputer.companyId == null}">selected</c:if>>--
                                </option>
                                <c:forEach var="company" items="${companies}">
                                    <option value="${company.id}" <c:if
                                            test="${formComputer.companyId == company.id}"> selected</c:if> >${company.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="hidden" id="id_computer" name="id_computer" value="${formComputer.id}"/>
                        <input id="submit-button" type="submit"
                               value="<c:choose><c:when test="${formComputer.id == null}"><spring:message code="add" /></c:when><c:otherwise><spring:message code="edit" /></c:otherwise></c:choose>"
                               class="btn btn-primary">
                        <spring:message code="or" />
                        <a href="index" class="btn btn-default"><spring:message code="abort" /></a>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</section>
</body>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-datepicker.js"></script>
<script src="js/bootstrapValidator.min.js"></script>
</html>