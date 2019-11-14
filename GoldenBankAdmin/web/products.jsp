<%-- 
    Document   : products
    Created on : Jun 2, 2016, 12:24:52 AM
    Author     : Rhea
--%>

<%@page import="goldenbank.ProductsCards"%>
<%@page import="goldenbank.ProductsLoans"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="assets/css/zabuto_calendar.css">
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
              <div class="row mt">
                  <div class="col-md-12">
                      <div class="content-panel">
                            <a href="/GoldenBankAdmin/newPLoan.jsp"
                                <button class="btn btn-primary btn-m">+ Add New Loan</button>
                            </a>
                                <%  
                                    List<ProductsLoans> loans = (List<ProductsLoans>) request.getAttribute("loans");
                                %>
                            <h4><i class="fa fa-angle-right"></i> All Loans Products</h4>
                            
                          <table class="table table-striped table-advance table-hover">  
                            <hr>
                              <thead>
                                <tr>
                                  <th>Name</th>
                                  <th>Maximum Amount</th>
                                  <th>Interest</th>
                                  <th>Description</th>
                                  <th></th>
                                </tr>
                              </thead>
                                
                              <tbody>
                                  <% for(ProductsLoans l: loans){ %>
                                        <tr>
                                            <td> <%= l.getName() %> </td>
                                            <td> <%= l.getAmount() %> </td>
                                            <td> <%= l.getRate() %> %</td>
                                            <td> <%= l.getDescription() %> </td>
                                            <td>
                                                <form action="deleteProduct" method="get">
                                                    <input type="hidden" name="loan_id" value="<%=l.getId()%>">
                                                    <input type="submit" value="Delete" class="btn btn-danger btn-m" />
                                                </form>
                                            </td>
                                        </tr>
                                    <% } %>
                              </tbody>
                          </table>
                      </div><!-- /content-panel -->
                  </div><!-- /col-md-12 -->
                  
                  <div class="col-md-12">
                      <div class="content-panel">
                            <a href="/GoldenBankAdmin/newPCard.jsp"
                                <button class="btn btn-primary btn-m">+ Add New Card</button>
                            </a>
                                <%  
                                    List<ProductsCards> cards = (List<ProductsCards>) request.getAttribute("cards");
                                %>
                            <h4><i class="fa fa-angle-right"></i> All Products Cards</h4>
                            
                          <table class="table table-striped table-advance table-hover">  
                            <hr>
                              <thead>
                                <tr>
                                  <th>Name</th>
                                  <th>Type</th>
                                  <th>Monthly Limit</th>
                                  <th>Daily Limit</th>
                                  <th>Description</th>
                                  <th></th>
                                </tr>
                              </thead>
                                
                              <tbody>
                                  <% for(ProductsCards c: cards){ %>
                                        <tr>
                                            <td> <%= c.getName() %> </td>
                                            <td> <%= c.getType() %> </td>
                                            <td> <%= c.getMonthlyLimit() %> </td>
                                            <td> <%= c.getDayLimit() %> </td>
                                            <td> <%= c.getDescription() %> </td>
                                            <td>
                                                <form action="deleteProduct" method="get">
                                                    <input type="hidden" name="card_id" value="<%=c.getId()%>">
                                                    <input type="submit" value="Delete" class="btn btn-danger btn-m" />
                                                </form>
                                            </td>
                                        </tr>
                                    <% } %>
                              </tbody>
                          </table>
                      </div><!-- /content-panel -->
                  </div><!-- /col-md-12 -->
              </div><!-- /row -->
          </section>
      </section>

      <!--main content end-->
      <!--footer start-->
      
  </section>

    <!-- js placed at the end of the document so the pages load faster -->
    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/jquery-1.8.3.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="assets/js/jquery.dcjqaccordion.2.7.js"></script>
    <script src="assets/js/jquery.scrollTo.min.js"></script>
    <script src="assets/js/jquery.nicescroll.js" type="text/javascript"></script>
    <script src="assets/js/jquery.sparkline.js"></script>

    <!--common script for all pages-->
    <script src="assets/js/common-scripts.js"></script>

    <!--script for this page-->
    <script src="assets/js/sparkline-chart.js"></script>    
    <script src="assets/js/zabuto_calendar.js"></script>  
    
    <script type="application/javascript">
      $(document).ready(function () {
          $("#date-popover").popover({html: true, trigger: "manual"});
          $("#date-popover").hide();
          $("#date-popover").click(function (e) {
          $(this).hide();
      });
        
            $("#my-calendar").zabuto_calendar({
                action: function () {
                    return myDateFunction(this.id, false);
                },
                action_nav: function () {
                    return myNavFunction(this.id);
                },
                ajax: {
                    url: "show_data.php?action=1",
                    modal: true
                },
                legend: [
                    {type: "text", label: "Special event", badge: "00"},
                    {type: "block", label: "Regular event", }
                ]
            });
        });
        
        
        function myNavFunction(id) {
            $("#date-popover").hide();
            var nav = $("#" + id).data("navigation");
            var to = $("#" + id).data("to");
            console.log('nav ' + nav + ' to: ' + to.month + '/' + to.year);
        }
    </script>
  

  </body>
</html>