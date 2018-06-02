<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<script src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/additional-methods.js"></script>
<script src="Styles/bootstrap.min.js"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagina administrator</title>
</head>
<%String msg=(String)request.getAttribute("msjInserareSpecialitate"); %>
<body id="gradient">
<%if(session.getAttribute("tipUser")=="admin"){%>
<jsp:include page="indexAdmin.jsp" />
<%}else{%><script>
	window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
	</script>
<% }%>
	<div id="right">
<h2 style="text-align:center">Specialitati</h2>
<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
<form id="listaSpec">
<table class="table">
<tr>
    
      <th>Denumire</th>
         <th>Optiuni</th>
      </tr>
  
      <%int i=1;
  
for(Specialitate spec : DbOperations.getSpecializari()){ %>
<tr id="spec<%=i%>">


<td>
<input type="hidden" id="specId" name="specId" value="<%=spec.getCod()%>">
<input type="text" name="spec" id="spec" class=" form-control" value="<%=spec.getDenumire() %>" disabled></td>
<td>
<input type="submit" class="btn btn-danger" id="sterge" name="sterge" value="Sterge" onclick="readyToModify(<%=i%>, 'delete')">
<input type="button" class="btn btn-secondary" id="modifica" name="modifica" value="Modifica" onclick="readyToModify(<%=i%>, 'modify')">
</td>
</tr>
<%
i++;
} %>
</table>
</form>
<form id="addSpec" method="post" action="EditeazaSpecialitati" onsubmit="return Verif()">
<table class="table">
<tr>
<td>
<input type="hidden" class=" form-control" name="verif" id="verif" value="add">
<input type="text" name="specNoua" id="specNoua" class=" form-control" ></td>
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
	var element = document.getElementById("spec"+i);
	var specId = element.querySelector("#specId");
	var spec = element.querySelector("#spec");
	if(verify=="modify"){
		if(!$("#listaSpec").valid() && !spec.disabled){
			return;
		}
	spec.disabled = !spec.disabled;
	if(spec.disabled){
		
		if (confirm('Sunteti sigur ca doriti sa modificati specialitatea?')){
			accept = true;
		}
		else{
			window.location.href = "InformatiiSpecialitati.jsp";
		}
	}
	}else{
		if(verify=="delete")
			 if (confirm('Sunteti sigur ca doriti sa stergeti specialitatea?'))
				accept = true;
			}
	
	if(accept){
	if(spec.disabled ||verify=="delete"){
			$.post("EditeazaSpecialitati",
			        {
			          verif:verify,
			          specId:specId.value,
			          spec:spec.value
			        },
			        function(data,status){
			        	window.location.href = "InformatiiSpecialitati.jsp?message="+data;
			        });}
		
	}
}
function Verif(){
	
	if(!$("#addSpec").valid()){
		
			return false;
	}else{
		if(confirm("Introduceti specialitatea?"))
			
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
			$('#addSpec').validate({
				rules:{
					specNoua:{
						required:true,
						lettersonly:true
						}
					}
				    });
			$('#listaSpec').validate({
				rules:{
					spec:{
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