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
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/locales/bootstrap-datepicker.ro.min.js" ></script>
<script src="Styles/bootstrap.min.js"></script>
 <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>
 <script src="https://momentjs.com/downloads/moment.js"></script>
 
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
        <a class="nav-link" href="AfiseazaSpecialitati.jsp" id="navbar"  aria-haspopup="true" aria-expanded="false">
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
   String tipUser=(String)session.getAttribute("tipUser");
   if(msj!="" && msj!=null){ %>
   <div class="alert alert-info alert-dismissible fade show" role="alert"  id="msg">
   <%=msj %>
</div>
   <%} %>
   <div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
<div class="jumbotron ">
<img src="resources/medical.png" width="500" height="400" class="center">
  <h3 class="display-4" align="center">Medici</h3>
  <hr class="my-4">
  <section class="container p-t-3">
    <div class="row">
        <div class="col-lg-12">
     Echipa noastră de medici este sufletul rețelei noastre private de sănătate.
      Pregătirea şi implicarea lor neobosită, atenţia acordată fiecărui pacient şi performanţele medicale pe care le-au atins sunt garanţii
       ale excelenţei pe care o promovăm.
        </div>
        <br>
    </div>
    <section id="team" class="pb-5">
    <div class="container">
        <div class="row">
            <!-- Team member -->
            <%
            int i=0;
            for(Medic m:DbOperations.getMedici()){%>
            <div class="col-xs-12 col-sm-6 col-md-4">
                <div class="image-flip" ontouchstart="this.classList.toggle('hover');">
                    <div class="mainflip">
                        <div class="frontside">
                            <div class="card">
                                <div class="card-body text-center" id="Medic<%=i%>">
                               		 <input type="hidden" value="<%=m.getId() %>" id="codMedic" name="codMedic">
                                    <p><img class=" img-fluid" src="resources/user-md.svg" alt="card image"></p>
                                    <h4 class="card-title" id="numeMedic">Dr. <%=m.getNume()+' '+m.getPrenume() %></h4>
                                    <p class="card-text"><%=DbOperations.getSpecializari(m.getCodSpec())%></p>
                                   
                                </div>
                            </div>
                        </div>
                        <div class="backside">
                            <div class="card">
                                <div class="card-body text-center mt-4">
                                    <h4 class="card-title"> <%=m.getNume()+' '+m.getPrenume() %></h4>
                                    <p class="card-text">&nbsp Email:<%=m.getEmail() %> &nbsp</p>
                                    <p class="card-text">&nbsp Numar telefon:<%=m.getTelefon() %> &nbsp</p>
                                    <input type="button" value="Vezi detalii" class="btn btn-secondary" onclick="detaliiMedic('<%=DbOperations.getSpecializari(m.getCodSpec())%>',<%=i%>)">
                                    <input type="button" value="Programeaza-te" class="btn btn-secondary" onclick="programare(<%=i%>)">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
      <% i++;} %>

        </div>
    </div>
</section>
</section>
</div>
<div class="modal fade"  id="exempleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Programare</h5>
      </div>
      <div class="modal-body" id="modal">
      
        <form id="consultatie" action="ProgramariConsultatie?pacientpag=true" method="post" style="display:none;text-align:center"> 
 <table>
<tr style="display:none">
<td><input type="text" name="pacientcod" id="pacientcod" class=" form-control"></td>
</tr>
<tr>
<td><input type="hidden" id="medic" name="medic" class="form-control">
</td>
</tr>


<tr>
<td>Motiv Consultatie:<br><select id="serviciu" name="serviciu" class="custom-select" onchange="ReinitializeDatePicker();getOreDisponibile()">
</select></td>
</tr>
<tr>
<td>
	Data:<br><input id="data1" name="data1" class="data"  onchange="getOreDisponibile()" readonly>
    <input type="hidden" id="data" name="data" readonly>  </td>      			
</tr>
<tr>
<td>Ora:<br><select id="ora" name="ora" class="custom-select">
</select></td>
</tr>
<tr>
<td>Detalii Consultatie:<br><textarea id="detalii" name="detalii" class="form-control"></textarea></td>
</tr>

<tr>
<td><br><input type="submit" class="btn btn-secondary" id="continua" name="continua" value="Programeaza" onclick="changeDateFormat()"></td>
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
function changeDateFormat(){
	document.getElementById("data").value=moment(document.getElementById("data1").value,"DD/MM/YYYY").format("YYYY-MM-DD");
}
function programare(i){

	var user="<%=tipUser%>";
	var element = document.getElementById("Medic"+i);
	var cod=element.firstElementChild.value;
	if(user!="pacient"){
		modal.innerHTML="Autentificati-va pentru a putea solicita o programare!";
		
	}else{
		document.getElementById("medic").value=cod;
		fillMotivCons();
		InitializeDatepicker();
		getOreDisponibile();
		document.getElementById("consultatie").style.display="block";
	}
	$('#exempleModal').modal("toggle");
}
function detaliiMedic(spec,i){
	var element = document.getElementById("Medic"+i);
	var cod=element.firstElementChild.value;
	 var nume=$("#Medic"+i).find("#numeMedic")[0].innerHTML;
	window.location.href = "DetaliiMedic.jsp?spec="+spec+"&cod="+cod+"&nume="+nume;
}

function ReinitializeDatePicker(){
	$('#data1').datepicker('update');
}
function getOreDisponibile(){
	var data=$('#data1').datepicker( "getDate" );
	 var medic=document.getElementById("medic").value;
	 var serviciu=document.getElementById("serviciu").value;
	
	$.post("ProgramariConsultatie",
	        {
			  metoda:"ora",
	          data:moment(data,"DD/MM/YYYY").format("YYYY-MM-DD"),
	          codMedic:medic,
	          serviciu:serviciu
	        },
	        function(data,status){
	        	if(data=="") return;
	        	var response = JSON.parse(data);
	        	response[0]=response[0].substring(0,5);
	        	var selectOra=document.getElementById("ora");
					selectOra.innerHTML="";
        	response.forEach(ora=>
        		selectOra.innerHTML+="<option value="+ora+">"+ora+"</option>"	
	        );
	        })
}

     
function InitializeDatepicker(){
	$('#data1').datepicker({
		startDate: "+1d",
		endDate: '+3m',
	    changeyear:false,
		daysOfWeekDisabled: "0,6",
		format:"dd/mm/yyyy",
		language:"ro",
		 beforeShowDay: function (date){
			 var response;
			 var medic=document.getElementById("medic").value;
			 var serviciu=document.getElementById("serviciu").value;
			 $.ajax({
			        type: "POST",
			        url: "ProgramariConsultatie",
			        data:{
			        	metoda:"data",
			        	data: moment(date,"DD/MM/YYYY").format("YYYY-MM-DD"),
			        	codMedic:medic,
			        	serviciu:serviciu
			        },
			        async: false,
			        success: function(results) {
			        	response=JSON.parse(results);
			        }
			        
			    });
			
			 	return{
		        	classes:response.color,
		        	enabled:response.valid
		        }
	       }
	});

	}
function fillMotivCons(){
	var medic=document.getElementById("medic").value;
	var selectMotiv=document.getElementById("serviciu");
	selectMotiv.innerHTML="";
	<%for(Serviciu codServ:DbOperations.getServicii()){%>
	if("<%=codServ.getCodMedic()%>"==medic){
		selectMotiv.innerHTML+="<option value='<%=codServ.getCod()%>'><%=DbOperations.getDenServiciuFromCodServiciu(codServ.getCod())%></option>";
	}
		<%}%>
}

</script>
</html>
