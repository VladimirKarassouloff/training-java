<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index"> Application - Computer Database </a>
    </div>
</header>

<section id="main">
    <div class="container">
        <div class="panel">
            <div class="panel-heading">
                <h3><spring:message code="login.page.title"/></h3>
            </div>
            <div class="panel-body">

                <form action="${pageContext.request.contextPath}/login" method="post" class="form-horizontal">
                    <div class="form-group">
                        <label for="login" class="col-sm-2 col-md-2 col-xs-2 col-lg-2 control-label"><spring:message
                                code="login"
                                var="login"/>${login}</label>
                        <div class="col-sm-10 col-md-10 col-xs-10 col-lg-10">
                            <input name="j_username" class="form-control" id="login" placeholder="${login}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-2 col-md-2 col-xs-2 col-lg-2 control-label"><spring:message
                                code="password"
                                var="password"/>${password}</label>
                        <div class="col-sm-10 col-md-10 col-xs-10 col-lg-10">
                            <input name="j_password" type="password" class="form-control" id="password"
                                   placeholder="${password}">
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary pull-right"><spring:message code="submit"/></button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </div>

</section>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>

</body>
</html>