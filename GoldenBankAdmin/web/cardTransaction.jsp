<%-- 
    Document   : cardTransaction
    Created on : Jun 2, 2016, 12:59:18 PM
    Author     : Rhea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="assets/lineicons/style.css">    
    <link href="assets/css/style.css" rel="stylesheet">
    <link href="assets/css/style-responsive.css" rel="stylesheet">
    <script src="assets/js/chart-master/Chart.js"></script>
    
    <title>Golden Bank</title>
    
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

  <section id="container" >
      <header class="header black-bg">
            <div class="sidebar-toggle-box">
                <img src="assets/img/logoimg.png" style=" height: 40px; margin-top: -20px;"/>
            </div>

            <a href="index.html" class="logo"><b>GOLDEN BANK</b></a>
            
            <div class="top-menu">
              <ul class="nav pull-right top-menu">
                    <li><a class="logout" href="login.html">Logout</a></li>
              </ul>
            </div>
        </header>

      <aside>
          <div id="sidebar"  class="nav-collapse ">
              <!-- sidebar menu start-->
              <ul class="sidebar-menu" id="nav-accordion">
                              
                  <li class="mt">
                        <a>
                            <form action="loginServlet" method="POST">
                                <input type="submit" value="Clients" style="background-color: transparent; border: none; font-size: 17px">
                            </form>
                        </a>
                  </li>

                  <li class="sub-menu">
                    <a>
                        <form action="allBranches" method="get">
                            <input type="submit" value="Bank/ATM Branches" style="background-color: transparent; border: none; font-size: 17px">
                        </form>
                    </a>
                  </li>
                  
                  <li class="sub-menu">
                      <a>
                        <form action="allProducts" method="get">
                            <input type="submit" value="Loans / Cards" style="background-color: transparent; border: none; font-size: 17px">
                        </form>
                    </a>
                  </li>

              </ul>
          </div>
      </aside>
      
      <section id="main-content">
          <section class="wrapper">
          	<h3><i class="fa fa-angle-right"></i> New Transaction</h3>
                          
          	<div class="row mt">
                    <div class="col-lg-12">
                        <div class="form-panel">
                            
                            <form class="form-horizontal style-form" action="cardTransaction" method="POST">
                                
                                <div class="form-group">
                                    <label class="col-lg-2 col-sm-2 control-label">Card Id</label>
                                    <div class="col-lg-10">
                                        <% String card_id = request.getAttribute("card_id").toString(); %>
                                        <p class="form-control-static"><%=card_id %></p>
                                    </div>
                                </div>
                                    
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">Type</label>
                                    <div class="radio">
                                        <label class="col-sm-2">
                                	    <input type="radio" name="rdType" value="Deposit" checked>
					    Deposit
                        		</label>
                                        <label class="col-sm-2">
                                            <input type="radio" name="rdType" value="Withdrawal">
                                            Withdrawal
                                        </label>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">Amount</label>
                                    <div class="col-sm-10">
                                        <input type="text" name="amount" class="form-control" required>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">Currency</label>
                                    <div class="radio">
                                        <label class="col-sm-2">
                                	    <input type="radio" name="rdCurrency" id="rdDollar" value="101" checked>
					    USD
                        		</label>
                                        <label class="col-sm-2">
                                            <input type="radio" name="rdCurrency" id="rdLebanese" value="102">
                                            LL
                                        </label>
                                    </div>
                                </div>
                          
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">Location</label>
                                    <div class="col-sm-10">
                                        <input type="text" name="location" class="form-control">
                                    </div>
                                </div>
                                
                                <input type="hidden" name="card_id" value="<%=card_id%>">
                                <input type="submit" value="Add" class="btn btn-primary btn-m"/>
                                
                            </form>
                        </div>
                    </div><!-- col-lg-12-->      	
          	</div><!-- /row -->
            
          </section><! --/wrapper -->
      </section><!-- /MAIN CONTENT -->
      
      <script src="assets/js/jquery.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="assets/js/jquery.dcjqaccordion.2.7.js"></script>
    <script src="assets/js/jquery.scrollTo.min.js"></script>
    <script src="assets/js/jquery.nicescroll.js" type="text/javascript"></script>


    <!--common script for all pages-->
    <script src="assets/js/common-scripts.js"></script>

    <!--script for this page-->
    <script src="assets/js/jquery-ui-1.9.2.custom.min.js"></script>

	<!--custom switch-->
	<script src="assets/js/bootstrap-switch.js"></script>
	
	<!--custom tagsinput-->
	<script src="assets/js/jquery.tagsinput.js"></script>
	
	<!--custom checkbox & radio-->
	
	<script type="text/javascript" src="assets/js/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap-daterangepicker/date.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap-daterangepicker/daterangepicker.js"></script>
	
	<script type="text/javascript" src="assets/js/bootstrap-inputmask/bootstrap-inputmask.min.js"></script>
	
	
	<script src="assets/js/form-component.js"></script>    
    
    
  <script>
      //custom select box

      $(function(){
          $('select.styled').customSelect();
      });

  </script>

  </body>
</html>
