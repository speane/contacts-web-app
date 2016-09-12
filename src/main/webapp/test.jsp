<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Список контакто</title>
</head>
<body>

<h2>Results</h2>

<p>${contact.name}</p>
<c:forEach var="contact" items="${allcontacts}">
    <c:out value="${contact.id} ${contact.name} ${contact.company}" />
</c:forEach>

</body>
</html>