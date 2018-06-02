<%@page import="pkg.Utils.DbOperations"%>
	<%@page import="java.util.List"%>
		<%@page import="pkg.Entities.*"%>
			<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
				<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
				<html>

				<head>
					<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
					<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
					<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css">
					<script src="Styles/bootstrap.min.js"></script>
					<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
					<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
					<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/locales/bootstrap-datepicker.ro.min.js"></script>
<script src="https://momentjs.com/downloads/moment.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<script src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/additional-methods.js"></script>

				<%if(session.getAttribute("tipUser")=="admin"){%>
<title>Pagina administrator</title>
<%}else{
	if(session.getAttribute("tipUser")=="receptioner"){%>
<title>Pagina receptioner</title>
<%}
	}%>
				</head>

				<body id="gradient">
				<% 
if(session.getAttribute("tipUser")=="admin"){%>
<jsp:include page="indexAdmin.jsp" />
<%}else{
	if(session.getAttribute("tipUser")=="receptioner"){%>
	<jsp:include page="indexReceptioner.jsp" />
<%}else{%><script>
window.location.href = "index.jsp?message=Nu aveti drept de intrare pe pagina solicitata!";
</script>
<% }
}%>
					<%String msg=(String)request.getAttribute("msg"); %>
						
						<div id="right">
<div class="alert alert-info alert-dismissible fade show" role="alert" style="display:none" id="mesaj">
</div>
							<center>
							<fieldset>
										<legend style=text-align:center>Cere o programare pentru analize</legend>
								<form id="cauta" action="ProgramariAnalize" method="post">
									
										<table>
											<tr>
												<td>
													Cauta pacient:
													<input type="text" name="cnp" id="cnp" class=" form-control" placeholder="Insereaza CNP-ul pacientului">
													<input type="button" onclick="searchPacient();" value="Cauta" class="btn btn-secondary">
												</td>
											</tr>
											</table>
											</form>
											<form id="analize" action="ProgramariAnalize" method="post">
											<table>
											<tr style="display:none" id="trnume">
												<td>Nume:
													<input type="text" name="nume" id="nume" class=" form-control">
												</td>
											</tr>
											<tr style="display:none" id="trprenume">
												<td>Prenume:
													<input type="text" name="prenume" id="prenume" class=" form-control">
												</td>
											</tr>
											<tr style="display:none" id="tremail">
												<td>Email:
													<input type="text" name="email" id="email" class=" form-control">
												</td>
											</tr>
											<tr style="display:none" id="trtelefon">
												<td>Telefon:
													<input type="text" name="telefon" id="telefon" class=" form-control">
												</td>
											</tr>
											<tr style="display:none" id="trcnp">
												<td>CNP:
													<input type="text" name="cnpNou" id="cnpNou" class=" form-control" readonly>
												</td>
											</tr>
											<tr style="display:none" id="trdataNasterii">
												<td>Data nasterii:
													<input type="text" name="dataNasterii" id="dataNasterii" class=" datepicker form-control" readonly>
													<input type="hidden" name="data1" id="data1">
												</td>
											</tr>
											<tr style="display:none" id="trpacient">
												<td>Pacient:
													<input type="text" name="pacient" id="pacient" class="form-control" readonly>
												</td>
											</tr>
											<tr style="display:none">
												<td>
													<input type="text" name="pacientcod" id="pacientcod" class=" form-control">
												</td>
											</tr>

											<tr>
												<td>Data:
													<br>
													<input type="text" name="data" id="data" class=" datepicker form-control" onchange="getOreDisp();" readonly>
													<input type="hidden" name="dataprog" id="dataprog">
												</td>
											</tr>
											<tr>
												<td>Ora:
													<br>
													<select id="ora" name="ora" class="custom-select">
													</select>
												</td>
											</tr>
											
											<tr>
												<td>Analize:
													<br>
													<select id="analize" name="analize" class="custom-select" multiple style="height: 50pt">
														<%for(Analiza analiza :DbOperations.getAnalize()){ %>
															<option value="<%=analiza.getCod()%>">
																<%=analiza.getDenumire() %>
															</option>
															<%} %>
													</select>
												</td>
											</tr>

											<tr>
												<td>
													<br>
													<input type="submit" class="btn btn-secondary" id="continua" name="continua" value="Programeaza">
												</td>
											</tr>

										</table>
										</form>
									</fieldset>
							</center>
						</div>
				</body>

				
				<script>
					function searchPacient() {
						var cnp = document.getElementById("cnp");
						if($("#cauta").valid()){
						$.post("ProgramariAnalize",
							{
								metoda: "detalii",
								cnp: cnp.value
							},
							function (data, status) {

								var response = JSON.parse(data)
								if (response.valid == true) {
									document.getElementById("pacient").value = response.nume + ' ' + response.prenume;
									document.getElementById("pacientcod").value = response.id;
									document.getElementById("trpacient").style.display = "block";
									document.getElementById("trnume").style.display = "none";
									document.getElementById("trprenume").style.display = "none";
									document.getElementById("trtelefon").style.display = "none";
									document.getElementById("tremail").style.display = "none";
									document.getElementById("trcnp").style.display = "none";
									document.getElementById("trdataNasterii").style.display = "none";
								}
								else {
									document.getElementById("trcnp").style.display = "block";
									document.getElementById("trdataNasterii").style.display = "block";
									document.getElementById("trnume").style.display = "block";
									document.getElementById("trprenume").style.display = "block";
									document.getElementById("trtelefon").style.display = "block";
									document.getElementById("tremail").style.display = "block";
									document.getElementById("trpacient").style.display = "none";
									document.getElementById("pacient").value = "";
									document.getElementById("cnpNou").value=document.getElementById("cnp").value;

								}
							});
					}
					}
					function getOreDisp() {
						var data = document.getElementById("dataprog");
						$.post("ProgramariAnalize",
							{
								metoda: "ore",
								dataAnalize: moment(data).format("YYYY-MM-DD")
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
					var msj = "<%=msg%>";
					if (msj != "null"){
						 var mesaj=document.getElementById("mesaj");
			        	 mesaj.innerHTML=msj+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
			         document.getElementById("mesaj").style.display="block";
					}
						
				    $('#continua').click(function(){
				        $('#data1').val(moment($('#dataNasterii').datepicker("getDate")).format("YYYY-MM-DD"));
				        $('#dataprog').val(moment($('#data').datepicker("getDate")).format("YYYY-MM-DD"));

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
						endDate:'+30d',
						daysOfWeekDisabled: "0,6"
					});
					$('#dataNasterii').datepicker({
						format: 'dd/mm/yyyy',
						language:"ro"
					})
					}));
			
					$.validator.addMethod(
					        "roCNP",
					        function(value, element) {
					            var check = false;
					            var re = /^\d{1}\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])(0[1-9]|[1-4]\d| 5[0-2]|99)\d{4}$/;
					            if( re.test(value)) {
					                var bigSum = 0, rest = 0, ctrlDigit = 0;
					                var control = '279146358279';
					                for (var i = 0; i < 12; i++) {
					                    bigSum += value[i] * control[i];
					                }
					                ctrlDigit = bigSum % 11;
					                if ( ctrlDigit == 10 ) ctrlDigit = 1;
					 
					                if ( ctrlDigit != value[12] ) return false;
					                else return true;
					            } return false;
					        }, 
					        "CNP invalid"
					    );
					$(document).ready(
					function(){
						$("#cauta").validate({
							rules:{
								cnp: { 
									required: true,
									roCNP: true
								}
						
							}
						})
					})
					$(document).ready(
					function() {
						$('#analize').validate({
							rules:{
								cnpNou: { required: true,
									roCNP: true
									},
					        nume:{
								required:true,
								lettersonly: true
							},
							prenume:{
								required:true,
								lettersonly: true
							},
							email:{
								required:true,
								email:true
							},
							telefon:{
									required:true,
								verifTelefon:true
							},
							dataNasterii:{
									required:true
								}
							,
							data:{ required:true
								
							},
							ora:{
								required:true
							},
							analize:{
								required:true
							}
							}
							    });
							})
							
							$.validator.addMethod("verifTelefon", function (value, element) {
					    return this.optional(element) || /(02|07)\d{8}$/.test(value);
					}, 'Telefon invalid');
					jQuery.extend(jQuery.validator.messages, {
					    required: "Campul este obligatoriu.",
					   	lettersonly:"Va rog inserati doar litere",
					    email: "Inserati un email valid.",
					    equalTo: "Please enter the same value again.",
					    accept: "Please enter a value with a valid extension.",
					    email:"Email invalid",
					    cnp:"CNP invalid",
					    cnpNou:"CNP invalid"
					});
					</script>
					</html>