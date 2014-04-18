<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
	<title>Fund Financials</title>	
	<link rel="stylesheet" href="<c:url value="/resources/css/dark-hive/jquery-ui-1.10.4.custom.css"/>" />
	<link rel="stylesheet" href="<c:url value="/resources/css/styles.css"/>" />
	<script src="<c:url value="/resources/js/jquery-1.10.2.js" />" ></script>
	<script src="<c:url value="/resources/js/jquery-ui-1.10.4.custom.js" />" ></script>
	<script src="<c:url value="/resources/js/underscore.js" />" ></script>
	<script src="<c:url value="/resources/js/backbone.js" />" ></script>
	<script src="<c:url value="/resources/js/lib/backbone.validation.js" />" ></script>
	<script src="<c:url value="/resources/js/Backbone.ModalDialog.js" />" ></script>
	
	<script src="<c:url value="/resources/js/jshashtable-3.0.js" />" ></script>
	<script src="<c:url value="/resources/js/jquery.numberformatter-1.2.4.min.js" />" ></script>
	
	
	<script src="<c:url value="/resources/js/main.js" />" ></script>
	
	<script id="control_panel" type="text/template">
		<div id="control_panel_div">
			<span><p><span class="select_fund_span">Select Fund:</span> <select>
  				<option selected value="Diamond Head">Diamond Head</option>  
			   </select>
			</p> </span>
			<table>
				<tr>
					<td>
						<input type="text" id="datepicker_from" PLACEHOLDER="From">
					</td>
					<td>
						<input type="text" id="datepicker_till" PLACEHOLDER="To">
					</td>
					<td>
						<INPUT TYPE="button" value="Show" class="show" id="show_button"/>
					</td>
				</tr>
			</table>
		<div>		
	</script>	
	
	<script id="tab_panel" type="text/template">
		<div id="tab_panel_div" class="tabs">			
		<ul class="tab-links">
			<li class="balance tab"><span>Balance</span></li>
			<li class="statements tab"><span>Statements</span></li>
			<li class="cash_flow tab"><span>Cash Flow</span></li>
			<li class="partner_capital tab"><span>Partner Capital</span></li>			
		</ul>
		</div>
				
	</script>
	
	
	
	
	<script></script>
	
</head>


<body>
<div id="up_side_container">
<div id=controlPanelDiv></div>
</div>

<div id="bottom_side_container">

<div id="tabDiv"></div>

<div id="tableDiv"></div>

</div>

<script>
(function(){
	
	
})();	

jQuery(document).ready(function($){
		App.Urls.baseUrl = 'http://localhost:8080'+'${pageContext.request.contextPath}';	
		App.initialize();
	   $("#datepicker_from").datepicker({ dateFormat: "yy-mm-dd" });
	   $("#datepicker_till").datepicker({ dateFormat: "yy-mm-dd" });
	   //$('.tabs .tab-links span').on('click', function(e)  {	        
	   //     $(this).parent('li').addClass('active').siblings().removeClass('active');	 
	        
	  //  });
	});

</script>

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





<%-- <h1>
	Fund Financials 
</h1>
time: ${runningtime}
<c:forEach items="${messages}" var="m">
	${m}<br/>
</c:forEach>





<br/>
<H1>Balance:</H1>
<br/>
<table>
<<<<<<< HEAD
<tr>
 <td>ASSETS: ${ff.balance.assetsSize}<td> 
</tr>
<c:forEach items="${ff.balance.valuesAssets}" var="asset">
=======
<c:forEach items="${ff.balance.values}" var="rec">
>>>>>>> refs/remotes/origin/newOne
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
</table> --%>

</body>



</html>
