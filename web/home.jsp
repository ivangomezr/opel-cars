<%@page import="entities.Usuario"%>
<%@page import="entities.Tipo"%>
<%@page import="entities.Modelo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
  <head>
    <title>Opel</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/style.css">
  </head>
  <body>
      
      <%List<Modelo> modelos = (List<Modelo>)session.getAttribute("modelos");
        List<Modelo> modelosAll = (List<Modelo>)session.getAttribute("modelosAll");
        List<Tipo> tipos = (List<Tipo>)session.getAttribute("tipos");
        Usuario usuario = (Usuario)session.getAttribute("usuario");
        String msg = (String) session.getAttribute("msg");
        session.removeAttribute("msg");%>
      
      <div class="container shadow p-0 bg-light">
          
        <%if(msg!=null) {%>
          <div class="toast">
              <div class="toast-header">
                  <%=msg%>
              </div>
          </div>
        <%}%> 
          
        <nav class="navbar navbar-expand-sm navbar-light bg-light">
            <a class="navbar-brand" href="Controller?op=inicio"><img src="img/logo.png" alt=""></a>
            <button class="navbar-toggler d-lg-none" type="button" data-toggle="collapse" data-target="#collapsibleNavId" aria-controls="collapsibleNavId"
                aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="collapsibleNavId">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="Controller?op=inicio">Todos<span class="sr-only">(current)</span></a>
                    </li>
                    <%for(Tipo tipo : tipos) {%>
                    <li class="nav-item">
                        <a class="nav-link" href="Controller?op=vatipo&tipo=<%=tipo.getId()%>"><%=tipo.getNombre()%></a>
                    </li>
                    <% } %>
                </ul>
                <%if(usuario==null) {%>
                <form class="form-inline my-2 my-lg-0">
                    <button class="btn btn-outline-danger my-2 my-sm-0" type="button" data-toggle="modal" data-target="#modalLogin">Log In</button>
                </form>
                <% } else {%>
                <h4 class="mt-1">Welcome <%=usuario.getNick()%></h4>
                <button class="btn btn-outline-primary my-2 my-sm-0 mr-2 ml-2" type="button" data-toggle="modal" data-target="#modalCita" data-nombreusuario="<%=usuario.getNick()%>">Pedir cita</button>
                <form class="form-inline my-2 my-lg-0" action="Controller?op=logout" method="post">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Log Out</button>
                </form>
                <% } %>
            </div>
        </nav>
          
          <!-- Modal LogIn -->
          <div class="modal fade" id="modalLogin" tabindex="-1" role="dialog">
              <div class="modal-dialog" role="document">
                  <div class="modal-content">
                      <div class="modal-header">
                          <h4 class="modal-title">Login / Register</h4>
                      </div>
                      <form action="Controller?op=login" method="post">
                          <div class="modal-body">
                              <div class="form-group">
                                  <label>Nickname</label>
                                  <input type="text" class="form-control" name="nick" id="nick" placeholder="Nickname">
                              </div>
                              <div class="form-group">
                                  <label>Password</label>
                                  <input type="password" class="form-control" name="pass" id="pass" placeholder="Password">
                              </div>
                          </div>
                              
                          <div class="modal-footer">
                              <button type="submit" class="btn btn-primary">
                                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-person-circle mb-1" viewBox="0 0 16 16">
                                  <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                  <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                                  </svg> LOGIN/REGISTER</button>
                              <button type="button" class="btn btn-danger" data-dismiss="modal">
                                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-x-circle mb-1" viewBox="0 0 16 16">
                                  <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                  <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                                  </svg> CANCELAR</button>
                          </div>
                      </form>
                  </div>
              </div>
          </div>
              
          <!-- Modal Cita -->
          <div class="modal fade" id="modalCita" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog" role="document">
                  <div class="modal-content">
                      <div class="modal-header bg-secondary">
                          <h5 class="modal-title text-white" id="exampleModalLabel">Petición</h5>
                          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                              <span aria-hidden="true" class="text-white">&times;</span>
                          </button>
                      </div>
                      <form action="Controller?op=cita" method="post">
                          <div class="modal-body text-center">
                              <h4 class="changeText">Se va a proceder a registrar a PACO a fecha de hoy del siguiente modelo</h4>
                              <select style="width: 80%" class="my-4" name="selectmodelo" >
                                  <option disabled selected value="">Elija Modelo</option>
                                  <%for(Modelo modelo : modelosAll) {%>
                                  <option value="<%=modelo.getId()%>"><%=modelo.getNombre()%></option>
                                  <% } %>
                              </select>
                              <h4>Le avisaremos informando del día que puede realizar la prueba</h4>
                          </div>
                          <div class="modal-footer">
                              <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                              <button type="submit" class="btn btn-primary">Solicitar</button>
                          </div>
                      </form>
                  </div>
              </div>
          </div>
              
          
              
        <div class="row justify-content-center">
        <%for(Modelo modelo : modelos) {%>
        <div class="col-lg-4 col-md-6 col-sm-12 d-flex">
            <button style="border: none; background: none" data-toggle="modal" data-target="#modalAcabados" data-idmodelo="<%=modelo.getId()%>" data-nombremodelo="<%=modelo.getNombre()%>">
            <div class="card flex-fill">
                <div class="card-body">
                    <h4 class="card-title"><%=modelo.getNombre()%></h4>
                </div>
                <img class="card-img-top" src="img/<%=modelo.getImagen()%>" alt="">
            </div>
            </button>
        </div>
        <% } %>
        </div>
          
          <!-- Modal Acabados-->
          <div class="modal fade" id="modalAcabados" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog" role="document">
                  <div class="modal-content">
                      <div class="modal-body">
                          <div class="card-body bg-secondary text-white text-center py-3">
                              <h4 class="card-title mt-2">Astra 5 puertas</h4>
                          </div>
                          <div id="acabados">
                              
                          </div>
                      </div>
                      <div class="modal-footer">
                          <button type="button" class="btn btn-secondary" data-dismiss="modal">Aceptar</button>
                      </div>
                  </div>
              </div>
          </div>
          
        <footer>
            <h3 class="text-white text-center bg-secondary py-3 m-0">Iván Gómez 2023 &copy AD - Azarquiel</h3>
        </footer>
      </div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="myjs.js"></script>
    <%if(msg!=null) {%>
        <script>
            $(document).ready(function(){
                $('.toast').toast('show');
            });
        </script>
    <%}%>
  </body>
</html>