<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	if (session.getAttribute("tipUser") == "admin") {
%>
<jsp:include page="indexAdmin.jsp" />
<%
	} else {
		if (session.getAttribute("tipUser") == "receptioner") {
%>
<jsp:include page="indexReceptioner.jsp" />
<%
	} else {
%><script>
window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
</script>
<%
	}
	}
%>
<head>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%if(session.getAttribute("tipUser")=="admin"){%>
<title>Pagina administrator</title>
<%}else{
	if(session.getAttribute("tipUser")=="receptioner"){%>
<title>Pagina receptioner</title>
<%}
	}%>
</head>

<body id="gradient">
	<%String msg=(String)request.getAttribute("msg"); %>

	<div id="right">
		<div class="alert alert-info alert-dismissible fade show" role="alert"
			style="display: none" id="mesaj"></div>
		<center>
			<fieldset>
				<legend style="text-align: center">Adauga analiza
					</legend>
				<form id="indicator" action="AdaugaIndicator" method="post">
					<table>
					<tr>
							<td>Analiza:<select id="analiza" name="analiza"
								class=" form-control"> <%for(Analiza analiza :DbOperations.getAnalize()){ %>
									<option value="<%=analiza.getCod()%>">
										<%=analiza.getDenumire() %>
									</option>
									<%} %>
									</select>
							</td>
						</tr>
						<tr>
							<td><br>Interval referinta min.: <input type="text" name="min"
								id="min" class=" datepicker form-control" >
							</td>
						</tr>
						<tr>
							<td>Interval referinta max.: <input type="text" name="max" id="max"
								class="form-control">
							</td>
						</tr>
						<tr >
							<td>Sex persoana: <select id="sex" name="sex"
								class=" form-control">
								<option value=""></option>
								<option value="M">Masculin</option>
								<option value="F">Feminin</option></select>
							</td>
						</tr>
						<tr>
							<td>Tip persoana:<select id="tipPers" name="tipPers"
								class=" form-control">
								<option value=""></option>
								<option value="copil">Copil</option>
								<option value="adult">Adult</option>
								<option value="batran">Batran</option></select>
							</td>
						</tr>
						<tr>
							<td><br> <input type="submit" class="btn btn-secondary"
								id="adauga" name="adauga" value="Adauga"></td>
						</tr>

					</table>
				</form>
			</fieldset>
		</center>
	</div>
</body>


<script>
					
					var msj = "<%=msg%>";
					if (msj != "null"){
						 var mesaj=document.getElementById("mesaj");
			        	 mesaj.innerHTML=msj+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
			         document.getElementById("mesaj").style.display="block";
					}
					</script>
</html>