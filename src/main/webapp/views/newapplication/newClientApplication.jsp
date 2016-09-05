<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="UTF-8" />
        <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
        <title>Royal Gas - New Contract Application</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <meta name="description" content="Royal Gas - New Contract Application" />
        <meta name="keywords" content="Royal Gas - New Contract Application" />
        <link rel="stylesheet" type="text/css" href="/dmg-rg-client/views/newapplication/css/demo.css" />
        <link rel="stylesheet" type="text/css" href="/dmg-rg-client/views/newapplication/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/dmg-rg-client/views/newapplication/css/animate-custom.css" />
		<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
		<script src='https://www.google.com/recaptcha/api.js'></script>
  <script>
  $( function() {
				$( "#startDate" ).datepicker();
				$( "#startDate" ).datepicker("option", "dateFormat", "dd/mm/2016");
			} 
   );
  </script>
    </head>
    <body>
        <div class="container">
            <header>
                <h1>Login and Registration Form </h1>
            </header>
            <section>				
                <div id="container_demo" >
                    <!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
                    <a class="hiddenanchor" id="tologin"></a>
                    <div id="wrapper">
                        <div id="login" class="form">
                            <form  action="/dmg-rg-client/newUserApplication" autocomplete="on" method="post"> 
                                <h1> Apply for New Contract </h1>

								<p class="selectInput"> 
                                    <label for="city" class="selectInput" >Select City</label>
                                    <select id="city" name="city" required="required" >
									  <option>Please select your region</option>
									  <option value="DUBAI">Abu dhabi, Alain & Western Region</option>
									  <option value="ABUDHABI">Dubai & Northern Emirates</option>
									</select>
                                </p>
                                <p> 
                                    <label for="username" class="uname" data-iconz="u">Your username</label>
                                    <input id="username" name="username" required="required" type="text" placeholder="username" />
                                </p>
                                <p> 
                                    <label for="email" class="youmail" data-iconz="e" > Your email</label>
                                    <input id="email" name="email" required="required" type="email" placeholder="name@mail.com"/> 
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd" data-iconz="p">Your password </label>
                                    <input id="password" name="password" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p> 
                                    <label for="password_confirm" class="youpasswd" data-iconz="p">Please confirm your password </label>
                                    <input id="password_confirm" name="password_confirm" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
								<p> 
                                    <label for="buildingNumber" class="uname" data-iconz="u">Building No.</label>
                                    <input id="buildingNumber" name="buildingNumber" required="required" type="text" placeholder="eg. 2323" />
                                </p>
								<p> 
                                    <label for="appartmentNumber" class="uname" data-iconz="u">Apartment No.</label>
                                    <input id="appartmentNumber" name="appartmentNumber" required="required" type="text" placeholder="eg. 104" />
                                </p>
								<p> 
                                    <label for="meterReading" class="uname" data-iconz="u">Last Meter Reading</label>
                                    <input id="meterReading" name="meterReading" required="required" type="text" placeholder="eg. 12345" />
                                </p>
                                <p> 
                                    <label for="meterNo" class="uname" data-iconz="u">Meter No.</label>
                                    <input id="meterNo" name="meterNo" required="required" type="text" placeholder="eg. 98987889" />
                                </p>
								<p> 
                                    <label for="startDate" class="" data-iconz="u">Start Date</label>
                                    <input id="startDate" name="startDate" required="required" type="text"  placeholder="dd/mm/yyyy"/>
                                </p>
								<p> 
                                    <label for="pobox" class="uname" data-iconz="u">P.O.Box</label>
                                    <input id="pobox" name="pobox" required="required" type="text" placeholder="eg. 2323" />
                                </p>
								<p> 
                                    <label for="poboxCity" class="uname" data-iconz="u">P.O.Box City</label>
                                    <input id="poboxCity" name="poboxCity" required="required" type="text" placeholder="eg. Abudhabi" />
                                </p>
								<p> 
                                    <label for="phone" class="uname" data-iconz="u">Phone No.</label>
                                    <input id="phone" name="phone" required="required" type="text" placeholder="eg. 021234567" />
                                </p>
								<p> 
                                    <label for="mobile" class="uname" data-iconz="u">Mobile No.</label>
                                    <input id="mobile" name="mobile" required="required" type="text" placeholder="eg. 0501234567" />
                                </p>
                                <p> 
                                    <label for="emiratesId" class="uname" data-iconz="u">Mobile No.</label>
                                    <input id="emiratesId" name="emiratesId" required="required" type="text" placeholder="1980-xxx-xxx" />
                                </p>
                                
                                <div class="g-recaptcha" data-sitekey="6Le68SYTAAAAALKatLmMCKxTWBZsqO9WLPpDr270"></div>
                                
                                <p class="signin button"> 
									<input type="submit" value="Sign up"/> 
								</p>
                            </form>
                        </div>
						
                    </div>
                </div>  
            </section>
        </div>
    </body>
</html>