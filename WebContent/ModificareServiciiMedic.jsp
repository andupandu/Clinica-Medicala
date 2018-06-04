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
<%String msg=(String)request.getAttribute("msg"); %>
<body id="gradient">
<%if(session.getAttribute("tipUser")=="medic"){%>
<jsp:include page="indexMedic.jsp" />
<%}else{%><script>
	window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
	</script>
<% }%>
	<div id="right">
<h2 style="text-align:center">Servicii oferite</h2>
<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
<form id="listaServicii">
<table class="table">
<tr>
    
      <th>Denumire</th>
       <th>Timp</th>
        <th>Pret</th>
         <th>Optiuni</th>
      </tr>
  
      <%int i=1;
      Persoana user=(Persoana)session.getAttribute("persoanaLogata");
		String tipUser=(String)session.getAttribute("tipUser");
for(Serviciu serv : DbOperations.getMedicServicii(DbOperations.getUserCodFromPassword(user,tipUser).toString())){ %>
<tr id="serv<%=i%>">


<td>
<input type="hidden" id="servId" name="servId" value="<%=serv.getCod()%>">
<input type="text" name="serviciu" id="serviciu" class=" form-control" value="<%=serv.getDenumire() %>" disabled></td>
<td><input type="time" name="timp" id="timp" class=" form-control" value="<%=serv.getTimp() %>" disabled></td>
<td><input type="text" name="pret" id="pret" class=" form-control" value="<%=serv.getPret() %>" disabled></td>

<td>
<input type="button" class="btn btn-secondary" id="modifica" name="modifica" value="Modifica" onclick="readyToModify(<%=i%>, 'modify')">
</td>
</tr>
<%
i++;
} %>
</table>
</form>
</div>
</body>
</html>
<script>

function readyToModify(i, verify){
	var accept=false;
	var element = document.getElementById("serv"+i);
	var servId = element.querySelector("#servId");
	var timp = element.querySelector("#timp");
	var pret = element.querySelector("#pret");
	
		if(!$("#listaServicii").valid() && !timp.disabled){
			return;
		}
	timp.disabled = !timp.disabled;
	pret.disabled = !pret.disabled;

	if(timp.disabled){
		
		if (confirm('Sunteti sigur ca doriti sa modificati serviciul oferit?')){
			accept = true;
		}
		else{
			window.location.href = "ModificareServiciiMedic.jsp";
		}
	}
	
	if(accept){
	if(timp.disabled){
			$.post("ModificareServiciuMedic",
			        {
			          verif:verify,
			          servId:servId.value,
			          timp:timp.value,
			          pret:pret.value
			        },
			        function(data,status){
			        	window.location.href = "ModificareServiciiMedic.jsp?message="+data;
			        });}
		
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
			$('#listaServicii').validate({
				rules:{
						timp:{
							required:true
							
							},
							pret:{
								required:true,
								digits:true

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