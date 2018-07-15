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
				<form id="analize" action="AdaugaAnalize" method="post">
					<table>
						<tr>
							<td>Denumire analiza: <input type="text" name="denumire" id="denumire"
								class=" form-control">
							</td>
						</tr>
						<tr>
							<td>Pret: <input type="text" name="pret" id="pret"
								class=" form-control">
							</td>
						</tr>
						<tr>
							<td>Durata: <select name="durata" id="durata"
								class=" form-control">
								<option value="1 zi">1 zi</option>
								<%for(int i=2;i<=17;i++){ %>
								<option value="<%=i%> zile"><%=i%> zile</option>
								<%}%>
								</select>
							</td>
						</tr>
						<tr>
							<td>Tip: <select id="tip" name="tip"
								class=" form-control">
								<option value="compensat">Compensat</option>
								<option value="necompensat">Necompensat</option></select>
							</td>
						</tr>
						<tr>
							<td>Categorie:<select id="categorie" name="categorie"
								class=" form-control"> <%for(Categorie categorie :DbOperations.getCategorii()){ %>
									<option value="<%=categorie.getCod()%>">
										<%=categorie.getDenumire() %>
									</option>
									<%} %>
									</select>
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

					$(document).ready(
					function() {
						$('#analize').validate({
							rules:{
								pret: { required: true,
									number: true
									},
									denumire:{
										required: true
									}
								}
							   });
							})
							
						
					jQuery.extend(jQuery.validator.messages, {
					    required: "Campul este obligatoriu.",
					   	number:"Valoare eronata",
					 
					});
					</script>
</html>