<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
	<title>Fund Financials</title>	
	<script src="<c:url value="/resources/js/jquery-1.7.2.js" />" ></script>
	<script src="<c:url value="/resources/js/underscore.js" />" ></script>
	<script src="<c:url value="/resources/js/backbone.js" />" ></script>
	<script src="<c:url value="/resources/js/main.js" />" ></script>
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





<script id="control_panel" type="text/template">
		<INPUT TYPE="text" value="Time from" />
		<INPUT TYPE="text" value="Time Till" />	
		<INPUT TYPE="button" value="Show" class="show" />		
</script>








<div id="mainDiv"></div>






















<br/>
<H1>Balance:</H1>
<br/>
<table>
<tr>
<td>ASSETS: ${ff.balance.assetsSize}<td>
</tr>
<c:forEach items="${ff.balance.valuesAssets}" var="asset">
	<tr>
		<td>${asset.chart}</td>
		<td><fmt:formatNumber value="${asset.value}" type="number" maxFractionDigits="0"/></td>
	</tr>
</c:forEach>
<tr>
<td>LIABILITIES: ${ff.balance.liabilitiesSize}<td>
</tr>
<c:forEach items="${ff.balance.valuesLiabilities}" var="liability">
	<tr>
		<td>${liability.chart}</td>
		<td><fmt:formatNumber value="${liability.value}" type="number" maxFractionDigits="0"/></td>
	</tr>
</c:forEach>
<tr>
<td>EQUITIES: ${ff.balance.equitiesSize}<td>
</tr>
<c:forEach items="${ff.balance.valuesEquities}" var="equity">
	<tr>
		<td>${equity.chart}</td>
		<td><fmt:formatNumber value="${equity.value}" type="number" maxFractionDigits="0"/></td>
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

<script>
(function(){

App.initialize();
})();

</script>

</html>
