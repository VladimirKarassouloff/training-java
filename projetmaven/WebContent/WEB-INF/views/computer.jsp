<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
    </div>
</header>

<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1>
                    <c:choose>
                        <c:when test="${form.id != null}">
                            Edit computer
                        </c:when>
                        <c:otherwise>
                            Add Computer
                        </c:otherwise>
                    </c:choose>
                </h1>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">
                            ${error}
                    </div>
                </c:if>

                <form action="computer" method="POST">
                    <fieldset>
                        <div class="form-group">
                            <label for="computerName">Computer name</label>
                            <input type="text" class="form-control" id="computerName" placeholder="Computer name"
                                   value="${form.name}" name="name_computer">
                        </div>
                        <div class="form-group">
                            <label for="introduced">Introduced date</label>
                            <input type="date" class="form-control" id="introduced" placeholder="Introduced date"
                                   value="${form.introduced}" name="introduced_computer">
                        </div>
                        <div class="form-group">
                            <label for="discontinued">Discontinued date</label>
                            <input type="date" class="form-control" id="discontinued" placeholder="Discontinued date"
                                   value="${form.discontinued}" name="discontinued_computer">
                        </div>
                        <div class="form-group">
                            <label for="companyId">Company</label>
                            <select class="form-control" id="companyId" name="company_id_computer"
                                    value="${form.companyId}">
                                <option value="null" <c:if test="${form.companyId == null}">selected</c:if>>--</option>
                                <c:forEach var="company" items="${companies}">
                                    <option value="${company.id}" <c:if
                                            test="${form.companyId == company.id}"> selected</c:if> >${company.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="hidden" name="id_computer" value="${form.id}"/>
                        <input type="submit"
                               value="<c:choose><c:when test="${form.id == null}">Add</c:when><c:otherwise>Edit</c:otherwise></c:choose>"
                               class="btn btn-primary">
                        or
                        <a href="index" class="btn btn-default">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
</body>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/computer.js"></script>
</html>