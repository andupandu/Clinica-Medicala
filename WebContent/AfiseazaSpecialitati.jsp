<%@page import="pkg.Utils.CreatePDF"%>
<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.*"%>
<%@page import="pkg.Entities.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
<script src="Styles/bootstrap.min.js"></script>
 <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>
 
 <link rel="stylesheet" href="Styles/Style.css"/>
<title>Clinica medicala</title>
</head>
<body id="gradient">
<%Persoana persoanaLogata=(Persoana)session.getAttribute("persoanaLogata"); %>
<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
  <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <a class="navbar-brand" href="index.jsp"><img src="resources/clinica.png" width="30" height="30">Clinica medicala</a>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
 
      <li class="nav-item dropdown">
        <a class="nav-link " href="AfiseazaSpecialitati.jsp" id="navbar"  aria-haspopup="true" aria-expanded="false">
          Specialitati
        </a>
      </li>
        <li class="nav-item dropdown">
        <a class="nav-link" href="AfiseazaMedici.jsp" id="navbar" aria-haspopup="true" aria-expanded="false">
        Medici
        </a>
      </li>
         <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        Laborator
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">    
      <a href="GenerareRezultatAnaliza.jsp" class="dropdown-item">Rezultate analize</a>
       <a href="AfiseazaAnalize.jsp" class="dropdown-item">Gama de analize</a>
        </div>
      </li>
         <li class="nav-item dropdown">
        <a class="nav-link" href="IstoricMedical.jsp" id="navbar" aria-haspopup="true" aria-expanded="false">
        Istoric medical
        </a>
      </li>
   </ul>
   
    <% if(persoanaLogata==null){%>
<ul class="navbar-nav ml-auto">
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#"
					id="navbarDropdownMenuLink" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false"> Autentificare</a>
					<div class="dropdown-menu dropdown-menu-right">
						<form class="px-4 py-3" action="Login" method="post">
							<div class="form-group">
								<label for="exampleDropdownFormEmail1">Email</label> <input
									type="email" name="email" class="form-control"
									id="exampleDropdownFormEmail1" placeholder="email@example.com">
							</div>
							<div class="form-group">
								<label for="exampleDropdownFormPassword1">Parola</label> <input
									type="password" name="password" class="form-control"
									id="exampleDropdownFormPassword1" placeholder="Password">
							</div>
							<button type="submit" class="btn btn-primary">Autentificare</button>
						</form>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="ContNou.jsp">Nu ai cont?
							Creeaza unul acum </a> <a class="dropdown-item" href="#">Mi-am uitat parola</a>
					</div></li>
			</ul>
			<%}else{%>
				<a class="navbar-nav ml-auto"><%=persoanaLogata.getNume()+" "+persoanaLogata.getPrenume() %></a>
				 <input type="button" class="btn btn-sm btn-outline-secondary" value="Delogare" onclick="reloadPage();">
				
			<% }%>
   </div>
   </nav>
   <%String msj=(String)request.getAttribute("msg");
   if(msj!="" && msj!=null){ %>
   <div class="alert alert-info alert-dismissible fade show" role="alert"  id="msg">
   <%=msj %>
</div>
   <%} %>
   <div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
<div class="jumbotron ">
<img src="resources/medical.png" width="500" height="400" class="center">
  <h3 class="display-4" align="center">Specialitățile noastre</h3>
  <hr class="my-4">
  <section class="container p-t-3">
    <div class="row">
        <div class="col-lg-12">
       La clinica noastra imbinam serviciile medicale integrate cu profesionalismul, grija si responsabilitatea personalului nostru medical.
        Pentru a veni in intampinarea unui numar cat mai mare de pacienti, clinica ofera acces la o gama larga de specialitati medicale.
         Va invitam mai jos sa vizualizati specialitatile noastre.
        </div>
        <br>
    </div>

    <table class="table table-striped">
  <thead>
    <tr>
     
      <th scope="col">Specialitate</th>
      <th scope="col">Numar Medici</th>
    </tr>
  </thead>
  <tbody>
  <% 
  Map <String,Long> nrMedici=DbOperations.getNumarMediciPerSpecialitate();
  for(Specialitate spec:DbOperations.getSpecializari()){ %>
    <tr >
     <input type="hidden" value="<%=spec.getCod() %>" id="specId" name="specId">
      <td><a href="SpecialitateDetalii.jsp?spec=<%=spec.getDenumire() %>"><%=spec.getDenumire() %></a></td>
      <td><%=nrMedici.get(spec.getDenumire())==null?"0":nrMedici.get(spec.getDenumire()) %></td>
      
    </tr>
    <%} %>
  </tbody>
</table>
</section>
</div>
<section style="background-color:lightblue">
<div style="display:inline-block;width:48%" >
<iframe width="100%" height="400" src="https://maps.google.com/maps?width=100%&amp;height=600&amp;hl=en&amp;q=Galati+(My%20Business%20Name)&amp;ie=UTF8&amp;t=&amp;z=13&amp;iwloc=B&amp;output=embed" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"><a href="https://www.maps.ie/create-google-map/">Add map to website</a></iframe><br />
</div>
<div style="display:inline-block;width:48%;vertical-align:top">
<h3> &nbsp Program de lucru:</h3>
<hr></hr>
  &nbsp Luni-Vineri: 08:00-20:00
<hr></hr>
  &nbsp Sambata:09:00-13:00
<hr></hr>
  &nbsp Duminica: inchis
<hr></hr>
<h3> &nbsp Contact:</h3>
Str. Nicolae Mantu Nr. 15, Cladirea Charles de Gaulle Plaza, et. 4, Galati
<hr></hr>
Tel.: 021.9886
<hr></hr>

</div>
</section>
<div>
</div>
<div style="background-color:#eaeaea;height:70;text-align: center" >
<span>©2018 Limba Andreea All Rights Reserved</span><br />
</div >

<form id="back" action="Delogare" method="post"></form>
</body>
<script>
function reloadPage(){
	document.getElementById("back").submit();
}
</script>
</html>
