<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mystuff" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="index"> Application -
            Computer Database </a>
    </div>
</header>
<section id="main">

    <div class="container">
        <h1 id="homeTitle">${totalCount} <spring:message code="computers.found"/></h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action="#" method="GET" class="form-inline">
                    <input value="${search}" type="search" id="searchbox" name="search"
                           class="form-control" placeholder="Search name"/>
                    <input type="hidden" value="${lengthPage}" name="lengthPage"/>
                    <spring:message code="filter.by.name" var="tradfilter"/>
                    <input type="submit" id="searchsubmit" value="${tradfilter}"
                           class="btn btn-primary"/>
                </form>
            </div>
            <div class="pull-right">
                <a class="btn btn-success" id="addComputer" href="computer"><spring:message code="computer.add"/></a>
                <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message
                        code="edit"/></a>
            </div>
        </div>
    </div>

    <form id="deleteForm" action="#" method="POST">
        <input type="hidden" name="selection" value="">
    </form>

    <div class="container" style="margin-top: 10px;">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <!-- Variable declarations for passing labels as parameters -->
                <!-- Table header for Computer Name -->

                <th class="editMode" style="width: 60px; height: 22px; display: none;">
                    <input type="checkbox" id="selectall"/>
                    <span style="vertical-align: top;"> -
                        <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                            <i class="fa fa-trash-o fa-lg"></i>
                        </a>
                    </span>
                </th>
                <th>
                    <mystuff:link-orderby linkGenerated="index" innerhtml="computer.name"
                                          ascGetParameter="asc" orderGetParameter="colOrder" valueOrder="0"/>
                </th>
                <th>
                    <mystuff:link-orderby linkGenerated="index" innerhtml="computer.introduced"
                                          ascGetParameter="asc" orderGetParameter="colOrder" valueOrder="1"/>
                </th>
                <th>
                    <mystuff:link-orderby linkGenerated="index" innerhtml="computer.discontinued"
                                          ascGetParameter="asc" orderGetParameter="colOrder" valueOrder="2"/>

                </th>

                <th>
                    <mystuff:link-orderby linkGenerated="index" innerhtml="company"
                                          ascGetParameter="asc" orderGetParameter="colOrder" valueOrder="3"/>
                </th>
            </tr>
            </thead>
            <!-- Browse attribute computers -->
            <tbody id="results">
            <c:forEach var="computer" items="${computers}">
                <tr>
                    <td class="editMode" style="display: none;"><input type="checkbox" name="cb" class="cb"
                                                                       value="${computer.id}"></td>
                    <td><a href="computer?id=${computer.id}" onclick="">${computer.name}</a></td>
                    <td>${computer.introduced}</td>
                    <td>${computer.discontinued}</td>
                    <td>${computer.companyName}</td>
                </tr>
            </c:forEach>


            </tbody>
        </table>
    </div>
</section>

<footer class="navbar-fixed-bottom">

    <div class="btn-group btn-group-sm pull-left" role="group">
        <a class="btn btn-default" href="?locale=fr">fr</a>
        <a class="btn btn-default" href="?locale=en">en</a>
    </div>

    <div class="container text-center">
        <mystuff:pagination-link totalCount="${totalCount}" itemPerPage="${lengthPage}" currentPage="${currentPage}"
                                 linkGenerated="index" paramNameUrlCurrent="currentPage"/>

        <mystuff:pagination-length linkGenerated="index" paramNameLengthList="lengthPage"
                                   displayLength="${[20,30,50,100]}"/>

    </div>
</footer>

<script>
    var traductions = {
        "view": "<spring:message code="view" />",
        "edit": "<spring:message code="edit" />",
        "confirmation_delete": "<spring:message code="confirmation_delete" />"
    };

</script>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>

</body>
</html>