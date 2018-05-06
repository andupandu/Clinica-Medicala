<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">

<script src="Styles/bootstrap.min.js"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagina administrator</title>
</head>
<%String msg=(String)request.getAttribute("msjInserareSpecialitate"); %>
<body>
<jsp:include page="indexAdmin.jsp" />
	<div id="right">
<h2><center>Specialitati</center></h2>

<table class="table">
<tr>
    
      <th>Denumire</th>
         <th>Optiuni</th>
      </tr>
  
      <%int i=1;
  
for(Specialitate spec : DbOperations.getSpecializari()){ %>
<tr id="spec<%=i%>">
<input type="hidden" id="specId" name="specId" value="<%=spec.getCod()%>">

<td><input type="text" name="spec" id="spec" class=" form-control" value="<%=spec.getDenumire() %>" disabled></td>
<td>
<input type="submit" class="btn btn-outline-danger" id="sterge" name="sterge" value="Sterge" onclick="readyToModify(<%=i%>, 'delete')">
<input type="button" class="btn btn-outline-secondary" id="modifica" name="modifica" value="Modifica" onclick="readyToModify(<%=i%>, 'modify')">
</td>
</tr>
<%
i++;
} %>

<form name="addSpec" method="post" action="EditeazaSpecialitati" onsubmit="return Verif()">
<tr>
<input type="hidden" class=" form-control" name="verif" id="verif" value="add">

<td><input type="text" name="specNoua" id="specNoua" class=" form-control" ></td>
<td>
<input type="submit" class="btn btn-outline-secondary" id="adauga" name="adauga" value="Adauga"  >
</td>
</tr>
</form>
</table></div>
</body>
</html>
<script>

function readyToModify(i, verify){
	var accept=false;
	var element = document.getElementById("spec"+i);
	var specId = element.querySelector("#specId");
	var spec = element.querySelector("#spec");
	if(verify=="modify"){
	spec.disabled = !spec.disabled;
	if(spec.disabled){
		if (confirm('Sunteti sigur ca doriti sa modificati specialitatea?')){
			accept = true;
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
			        	alert(data);
			        	location.reload(true);
			        });}
		
	}
}
function Verif(){
	document.getElementById("verif").value="add";
	if(document.getElementById("specNoua").value==''){
		alert("Introduce o denumire pentru specialitate");
			return false;
	}else{
		if(confirm("Introduceti specialitatea?"))
			
			return true;
				return false;
		}
	}
var msjInsert="<%=msg%>";
if(msjInsert!="null")
	alert(msjInsert);
	
</script>