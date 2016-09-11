<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:query var="rs" dataSource="jdbc/TestDB">
    select contactId, name, company from contact
</sql:query>

<html>
<head>
    <title>DB Test</title>
</head>
<body>

<h2>Results</h2>

<c:forEach var="row" items="${rss.rows}">
    <tr>
        <td><c:out value="${row.contactId}"/></td>
        <td><c:out value="${row.name}"/></td>
        <td><c:out value="${row.company}"/></td>
    </tr>
</c:forEach>

</body>
</html>