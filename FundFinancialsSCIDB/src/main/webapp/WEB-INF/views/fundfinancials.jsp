<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
	<title>Fund Financials</title>
</head>
<body>
<h1>
	Fund Financials 
</h1>
time: ${runningtime}
<c:forEach items="${messages}" var="m">
	${m}<br/>
</c:forEach>

<!-- <form method="post">
<table>
<tr><td><b>Query:</b></td></tr>
<tr><td><input type="text" name="testquery" value="${testquery}" style="width:900px;"/></td></tr>
<tr><td>
	<input type="submit" name="aql" value="Query AQL" />
	<input type="submit" name="afl" value="Query AFL" />
	<input type="submit" name="testaction" value="Test Action" />
</td></tr>
</table>
</form> -->


<br/>
<H1>Balance:</H1>
<br/>
<table>
<c:forEach items="${ff.balance.values}" var="rec">
	<tr>
		<td>${rec.chart}</td>
		<td><fmt:formatNumber value="${rec.value}" type="number" maxFractionDigits="0"/></td>
	</tr>
</c:forEach>
</table>

<br/><br/><br/>

<H1>Statement of Operations:</H1>
<br/>
<table>
	<c:forEach items="${ff.statements.values}" var="val">
	<tr>
		<td>${val.chart}</td>
		<td><fmt:formatNumber value="${val.value}" type="number" maxFractionDigits="0"/></td>
	</tr>
	</c:forEach>
</table>

<br/><br/><br/>

<H1>Statement of Changes in Partners Capital:</H1>
<br/>
<table>
	<c:forEach items="${ff.partnerCapital.values}" var="val">
	<tr>
		<td>${val.chart}</td>
		<td><fmt:formatNumber value="${val.valueLP}" type="number" maxFractionDigits="0"/></td>
		<td><fmt:formatNumber value="${val.valueGP}" type="number" maxFractionDigits="0"/></td>
		<td><fmt:formatNumber value="${val.valueTotal}" type="number" maxFractionDigits="0"/></td>
	</tr>
	</c:forEach>
</table>

<br/><br/><br/>

<H1>Statement of Cash Flow:</H1>
<br/>
<table>
	<c:forEach items="${ff.cashFlow.values}" var="val">
	<tr>
		<td>${val.chart}</td>
		<td><fmt:formatNumber value="${val.value}" type="number" maxFractionDigits="0"/></td>
	</tr>
	</c:forEach>
</table>

</body>
</html>
