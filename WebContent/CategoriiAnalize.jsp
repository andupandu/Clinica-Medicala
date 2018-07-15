<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%if(session.getAttribute("tipUser")=="admin"){%>
<jsp:include page="indexAdmin.jsp" />
<%}else{%><script>
	window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
	</script>
<% }%>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<script src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/additional-methods.js"></script>
<script src="Styles/bootstrap.min.js"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagina administrator</title>
</head>
<%String msg=(String)request.getAttribute("msjInserareCategorie"); %>
<body id="gradient">
	<div id="right">
<h2 style="text-align:center">Categorii analize</h2>
<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
<form id="listaCategorii">
<table class="table">
<tr>
    
      <th>Denumire</th>
         <th>Optiuni</th>
      </tr>
  
      <%int i=1;
  
for(Categorie categorie : DbOperations.getCategorii()){ %>
<tr id="categorie<%=i%>">


<td>
<input type="hidden" id="categorieId" name="categorieId" value="<%=categorie.getCod()%>">
<input type="text" name="categorie" id="categorie" class=" form-control" value="<%=categorie.getDenumire() %>" disabled></td>
<td>
<input type="button" class="btn btn-secondary" id="modifica" name="modifica" value="Modifica" onclick="readyToModify(<%=i%>, 'modify')">
</td>
</tr>
<%
i++;
} %>
</table>
</form>
<form id="addCategorie" method="post" action="CategoriiAnalize" onsubmit="return Verif()">
<table class="table">
<tr>
<td>
<input type="hidden" class=" form-control" name="verif" id="verif" value="add">
<input type="text" name="categorieNoua" id="categorieNoua" class=" form-control" ></td>
<td>
<input type="hidden">
<input type="submit" class="btn btn-secondary" id="adauga" name="adauga" value="Adauga"  >
</td>
</tr>
</table>
</form>
</div>
</body>
</html>
<script>

function readyToModify(i, verify){
	var accept=false;
	var element = document.getElementById("categorie"+i);
	var categorieId = element.querySelector("#categorieId");
	var categorie = element.querySelector("#categorie");
		if(!$("#listaCategorii").valid() && !categorie.disabled){
			return;
		}
	categorie.disabled = !categorie.disabled;
	if(categorie.disabled){
		
		if (confirm('Sunteti sigur ca doriti sa modificati categoria?')){
			accept = true;
		}
		else{
			window.location.href = "CategoriiAnalize.jsp";
		}
	}
	
	
	if(accept){
	if(categorie.disabled){
			$.post("CategoriiAnalize",
			        {
			          verif:verify,
			          categorieId:categorie.value,
			          categorie:categorie.value
			        },
			        function(data,status){
			        	window.location.href = "CategoriiAnalize.jsp?message="+data;
			        });}
		
	}
}
function Verif(){
	
	if(!$("#addCategorii").valid()){
		
			return false;
	}else{
		if(confirm("Introduceti categoria?"))
			
			return true;
				return false;
		}
	}
var msjInsert="<%=msg%>";
if(msjInsert!="null"){
	 var mesaj=document.getElementById("mesaj");
	 mesaj.innerHTML=msjInsert+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
 document.getElementById("mesaj").style.display="block";
}
	
$(document).ready(
		function() {
			$('#addCategorii').validate({
				rules:{
					categorieNoua:{
						required:true,
						lettersonly:true
						}
					}
				    });
			$('#listaCategorii').validate({
				rules:{
					categorie:{
						required:true,
						lettersonly:true
						}
					}
				    });		
			if(window.location.href.indexOf("message=") > 0)
			{	
				var message = window.location.href.split("message=")[1].replace(/%20/g," ");
				var mesaj=document.getElementById("mesaj");
			    mesaj.innerHTML=message+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
			    document.getElementById("mesaj").style.display="block";
			}
				})
				
		jQuery.extend(jQuery.validator.messages, {
		    required: "Campul este obligatoriu.",
		   	lettersonly:"Va rog inserati doar litere"
		});
</script>