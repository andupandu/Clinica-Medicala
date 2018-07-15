<%@page import="pkg.Utils.DbOperations"%>
<%@page import="java.util.List"%>
<%@page import="pkg.Entities.*"%>
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
  <a class="navbar-brand" href="#"><img src="resources/clinica.png" width="30" height="30">Clinica medicala</a>

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
						<a class="dropdown-item" href="CreareCont.jsp">Nu ai cont?
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
<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
  <ol class="carousel-indicators">
    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
  </ol>
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img class="d-block w-100" src="resources/img1.jpg" alt="First slide">
    </div>
    <div class="carousel-item">
      <img class="d-block w-100" src="resources/img2.jpg" width="848" height="566" alt="Second slide">
    </div>
    <div class="carousel-item">
      <img class="d-block w-100" src="resources/img3.jpg" width="848" height="566" alt="Third slide">
    </div>
  </div>
  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
 
</div>
<div class="jumbotron">
  <h1 class="display-4" align="center">Servicile noastre</h1>
  <hr class="my-4">
  <section class="container p-t-3">
    <div class="row">
        <div class="col-lg-12">
        </div>
    </div>
</section>
<section class="carousel slide" data-ride="carousel" id="postsCarousel">
    <div class="container">
        <div class="row">
        </div>
    </div>
    <div class="container p-t-0 m-t-2 carousel-inner">
        <div class="row row-equal carousel-item active m-t-0">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-img-top card-img-top-250">
                        <img class="img-fluid" src="resources/cardiologie.jpg" alt="Carousel 1">
                    </div>
                    <div class="card-block p-t-2">
                        <h2>
                           Cardiologie
                        </h2>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-img-top card-img-top-250">
                        <img class="img-fluid" src="resources/pediatrie.JPG" alt="Carousel 2">
                    </div>
                    <div class="card-block p-t-2">
                       
                        <h2>
                           Pediatrie
                        </h2>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-img-top card-img-top-250">
                        <img class="img-fluid" src="resources/neurologie.jpg" alt="Carousel 3">
                    </div>
                    <div class="card-block p-t-2">
                        
                        <h2>
                           Neurologie
                        </h2>
                    </div>
                </div>
            </div>
        </div>
        <div class="row row-equal carousel-item m-t-0">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-img-top card-img-top-250">
                        <img class="img-fluid" src="resources/oftalmologie.jpg" alt="Carousel 4">
                    </div>
                    <div class="card-block p-t-2">
                        <h2>
                           Oftalmologie
                        </h2>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-img-top card-img-top-250">
                        <img class="img-fluid" src="resources/stomatologie.jpg" alt="Carousel 5">
                    </div>
                    <div class="card-block p-t-2">
                        <h2>
                            Stomatologie
                        </h2>
                    </div>
                </div>
            </div>
            <div class="col-md-4 fadeIn wow">
                <div class="card">
                    <div class="card-img-top card-img-top-250">
                        <img class="img-fluid" src="resources/dermatologie.jpg" alt="Carousel 6">
                    </div>
                    <div class="card-block p-t-2">
                      
                        <h2>
                           Dermatologie
                        </h2>
                    </div>
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
function reloadPage(){
	document.getElementById("back").submit();
}
<%String msg = (String) request.getAttribute("msg");%>
if(window.location.href.indexOf("message=") > 0)
{	
	var message = window.location.href.split("message=")[1].replace(/%20/g," ");
	var mesaj=document.getElementById("mesaj");
    mesaj.innerHTML=message+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
    document.getElementById("mesaj").style.display="block";
}
else if(msg!=""){
	 mesaj.innerHTML=msg;
}</script>
</html>
