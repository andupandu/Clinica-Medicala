<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="pkg.Utils.*"%>
<%@page import="java.util.*"%>
<%@page import="pkg.Entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<%if(session.getAttribute("tipUser")=="admin"){%>
<title>Pagina administrator</title>
<%}else
	if(session.getAttribute("tipUser")=="receptioner"){%>
<title>Pagina receptioner</title>
<%}
	else
	if(session.getAttribute("tipUser")=="medic"){%>
<title>Pagina medic</title>
<%}
	%>
<%
String msg=(String)request.getAttribute("msg");%>
<body id="gradient">
<% 
if(session.getAttribute("tipUser")=="admin"){%>
<jsp:include page="indexAdmin.jsp" />
<%}else
	if(session.getAttribute("tipUser")=="receptioner"){%>
	<jsp:include page="indexReceptioner.jsp" />
<%}else
	if(session.getAttribute("tipUser")=="medic"){%>
	<jsp:include page="indexMedic.jsp" />
<%}
else{
	%><script>
	window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
	</script>
<% 
}%>

<script src="https://momentjs.com/downloads/moment.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>



</head>

<% 	Map<Long,String> zile = (Map<Long,String>)request.getAttribute("zile");%>
<div id="right">
<%if(zile.isEmpty()){%>
<div class="alert alert-info alert-dismissible fade show" role="alert" id="mesaj">
				<center>Aveti deja programul de lucru stabilit pentru toate zilele saptamanii!Daca doriti sa il modificati click <a href="ModificaProgramMedic.jsp">aici</a></center>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
<%}else{ %>

<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
<form method="post" action="AdaugaProgramMedic"  accept-charset="UTF-8" id="adaugaprogrammedic">
<center>
<fieldset>
<legend>Program de lucru</legend>
<table align="center">
<tr>
<td>
Ziua:<br><select id="zi" name="zi" class="custom-select">
<%
	
	for (Long zi: zile.keySet()) {
	%>
				<option value="<%=zi%>"><%=zile.get(zi)%></option>
				<%
					}
				%>
			</select></td></tr>
			<tr><td>
Ora inceput:<input type="time" name="orainceput" id="orainceput" class="datepicker form-control" min="00:00" max="20:00"></td></tr>
<tr><td>
Ora sfarsit:<input type="time" name="orasfarsit" id="orasfarsit" class="datepicker form-control" min="00:00" max="20:00">
</td></tr>
<tr><td>
<input type="submit" class="btn btn-secondary"  id="adauga"  name="adauga" value="Adauga program">
</td>
</tr>
</table>
</fieldset>
</center>
</form>
<%} %>
</div>

</body>
<script>
var msjInsert="<%=msg%>";
if(msjInsert!="null"){
	 var mesaj=document.getElementById("mesaj");
	 mesaj.innerHTML=msjInsert+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
 document.getElementById("mesaj").style.display="block";
}
	
$( document ).ready(function() {
	document.getElementById("orainceput").defaultValue = "08:00";
	document.getElementById("orasfarsit").defaultValue = "16:00";

})

  </script>
</html>