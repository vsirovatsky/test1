<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Testing SCIDB 
</h1>

<form method="post">
<table>
<tr><td><b>Query:</b></td></tr>
<tr><td><input type="text" name="testquery" value="${testquery}" style="width:900px;"/></td></tr>
<tr><td>
	<input type="submit" name="aql" value="Query AQL" />
	<input type="submit" name="afl" value="Query AFL" />
	<input type="submit" name="testaction" value="Test Action" />
</td></tr>
</table>
</form>

<br/>
<c:forEach items="${messageslist}" var="mess">
	${mess}
	<br/>
</c:forEach>

</body>
</html>
