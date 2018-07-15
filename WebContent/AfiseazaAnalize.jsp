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
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/locales/bootstrap-datepicker.ro.min.js"></script>
<script src="https://momentjs.com/downloads/moment.js"></script>
<script src="Styles/bootstrap.min.js"></script>
 <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>
 
 <link rel="stylesheet" href="Styles/Style.css"/>

<title>Clinica medicala</title>
</head>
<body id="gradient">

<%Persoana persoanaLogata=(Persoana)session.getAttribute("persoanaLogata");
   String tipUser=(String)session.getAttribute("tipUser");%>
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
  <h3 class="display-4" align="center">Gama de analize</h3>
  <hr class="my-4">
  <section class="container p-t-3">
    <div class="row">
        <div class="col-lg-12">
    <div class="card center" style="width: 18rem;">
	  <div class="card-header">
	   Servicii de laborator selectate
	  </div>
 
	  	 <table id="analizeSelectate" width="100%">
	  	 </table>
 	<hr/>
 	<form method="post" action="ProgramariAnalize?analiza=true" style="margin:0 auto;widt">
 	<table width="100%">
 		<tr>
 		<td>Total</td>
 		<td id="pretTotalAnalize"> 0</td>
 		<td>LEI</td>
 		<td><input type="hidden" id="listaAnalize" name="listaAnalize"></td>
 		</tr>
 		 
 	</table>
 	<hr>
 		<input style="width:100%" type="button" value="Continua" id="continua" class="btn btn-secondary" onclick="return continuaProg()">
 	</form>
	</div>
	<hr>
        <%int i=0;
        for(Categorie c:DbOperations.getCategorii()) {%>
             <div id="accordion">
  <div class="card">
    <div class="card-header" id="heading<%=i%>">
      <h5 class="mb-0">
        <button class="btn btn-link" data-toggle="collapse" data-target="#collapse<%=i%>" aria-expanded="true" aria-controls="collapse<%=i%>">
         <%=c.getDenumire() %>
        </button>
      </h5>
    </div>

    <div id="collapse<%=i%>" class="collapse" aria-labelledby="heading<%=i%>" data-parent="#accordion">
      <div class="card-body">
           <table class="table table-striped">
             <thead>
    <tr>
     
      <th scope="col">Denumire</th>
      <th scope="col">Durata</th>
      <th scope="col">Pret</th>
    </tr>
  </thead>
  <tbody>
  <% 
  for(Analiza analiza:DbOperations.getAnalizeFromCodCategorie(c.getCod())){ %>
    <tr>
      <td><%=analiza.getDenumire()%></td>
      <td><%=analiza.getDurata() %></td>
      <td><%=analiza.getPret()%> Lei</td>
      <td><input type="button" value="Adauga" onClick="adaugaAnaliza('<%=analiza.getDenumire()%>', <%=analiza.getPret()%>, <%=analiza.getCod() %>)" class="btn btn-secondary"> 
    </tr>
    <%} %>
  </tbody>
</table>
      </div>
    </div>
  </div>
</div>
<%i++;} %>
        </div>
        <br>
    </div>
<div class="modal fade"  id="exempleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Programare</h5>
      </div>
      <div class="modal-body" id="modal">
      
        <form id="consultatie" action="ProgramariAnalize?analiza=true" method="post" style="display:none;text-align:center"> 
 <table>
<tr>
<td><input type="hidden" id="ListaAnalize" name="ListaAnalize" class="form-control">
</td>
</tr>
<tr>
<td>
	Data:<input type="text" name="data" id="data" class="datepicker form-control" onchange="getOreDisp();" readonly>
<input type="hidden" name="dataprog" id="dataprog">    			
</tr>
<tr>
<td>Ora:<br><select id="ora" name="ora" class="custom-select">
</select></td>
</tr>
<tr>
<td><br><input type="submit" class="btn btn-secondary" id="programeaza" name="programeaza" value="Programeaza-te"></td>
</tr>
</table>
</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
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
var idAnalizeSelecate = [];
function reloadPage(){
	document.getElementById("back").submit();
}

function stergeAnalizaSelectata(id){
	var analizaSelectata = document.getElementById(id);
	var pretAnaliza = parseInt(analizaSelectata.querySelector("#pretAnaliza").innerText);
	analizaSelectata.remove();
	var locatiePretCurent = document.getElementById("pretTotalAnalize");
	idAnalizeSelecate.pop(id);
	AdaugaPret(-pretAnaliza);
}

function AdaugaPret(pret){
	var locatiePretCurent = document.getElementById("pretTotalAnalize");
	var pretCurent = parseInt(locatiePretCurent.innerText);
	locatiePretCurent.innerHTML = pretCurent + pret;
	
	document.getElementById("listaAnalize").value = idAnalizeSelecate.map(String);
}

function adaugaAnaliza(nume, pret, cod){
    if(idAnalizeSelecate.indexOf(cod) >=0){
        alert("Analiza deja selecatata");
        return;
    }
	var locatieAnalize = document.getElementById("analizeSelectate");

	var rand = locatieAnalize.insertRow(-1);
	rand.id = cod;
	var coloanaNume = rand.insertCell(0);
	var coloanaPret = rand.insertCell(1);
	var coloanaMoneda = rand.insertCell(2);
	var coloanaButton = rand.insertCell(3);
	
	coloanaNume.innerHTML = nume;
	coloanaPret.innerHTML = pret;
	coloanaPret.id = "pretAnaliza";
	coloanaMoneda.innerHTML = "LEI";
	coloanaButton.innerHTML = "<input type='button' value='x' class='btn btn-secondary' onClick='stergeAnalizaSelectata(" + cod + ")' style='text-align:right'>";
	
	idAnalizeSelecate.push(cod);
	AdaugaPret(pret);
}
function getOreDisp() {
	var data = document.getElementById("dataprog");
	$.post("ProgramariAnalize",
		{
			metoda: "ore",
			dataAnalize: moment(data,"dd/MM/yyyy").format("YYYY-MM-DD")
		},
		function (data, status) {

			if (data != null) {
				var response = JSON.parse(data);

				var selectOra = document.getElementById("ora");
				selectOra.innerHTML = "";
				response.forEach(ora =>
					selectOra.innerHTML += "<option value=" + ora + ">" + ora + "</option>"
				);
			} else {
				alert("Nu mai sunt ore disponibile pentru aceasta zi.Va rugam selectati o alta zi!")
			}
		});
}
$('#programeaza').click(function(){
    $('#dataprog').val(moment($('#data').datepicker("getDate"),"dd/MM/yyyy").format("YYYY-MM-DD"));

}); 
var disableddates = ["01-01-2018", "30-11-2018", "01-12-2018", "25-12-2018","26-12-2018","02-01-2018","24-01-2018","09-04-2018",
	"01-05-2018","27-05-2018","28-05-2018","01-06-2018","15-08-2018"];


function DisableSpecificDates(date) {
    var string = jQuery.datepicker.formatDate('dd-mm-yy', date);
    console.log(string + " " + disableddates.indexOf(string) == -1)
    return disableddates.indexOf(string) == -1;
  }
$(document).ready($(function () {
$('#data').datepicker({
	format: 'dd/mm/yyyy',
	startDate: '+1d',
	language:"ro",
	beforeShowDay: DisableSpecificDates,
	endDate:'+3m',
	daysOfWeekDisabled: "0,6"
});

}))


function continuaProg(){
	var user="<%=tipUser%>";
	if(user!="pacient"){
		modal.innerHTML="Autentificati-va pentru a putea solicita o programare!";
	}else{
		if(document.getElementById("pretTotalAnalize").innerText==0){
			alert("Selectati cel putin o analiza pentru a continua");
			return false;
		}
	document.getElementById("ListaAnalize").value=document.getElementById("listaAnalize").value;
	document.getElementById("consultatie").style.display="block";
	}
	$('#exempleModal').modal("toggle");
}
</script>
</html>
