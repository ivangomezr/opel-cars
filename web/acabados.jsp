
<%@page import="entities.Itemacabado"%>
<%@page import="entities.Modelo"%>
<%@page import="java.util.List"%>
<%@page import="entities.Modeloacabado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%List<Modeloacabado> modeloAcabados = (List<Modeloacabado>)session.getAttribute("modeloAcabados");
  Modelo modelo = (Modelo)session.getAttribute("modelo");%>

<img class="card-img-top" src="img/<%=modelo.getImagen()%>" alt="">


<%if(modeloAcabados.size()!=0) {%>
<div class="card-body bg-secondary text-white text-center py-2">
      <h4 class="card-title mt-2">ACABADOS</h4>
</div>
<div class="accordion" id="accordionExample">
    <%for(int i = 0; i < modeloAcabados.size(); i++) {%>
    <div class="card">
        <div class="card-header" id="heading<%=i%>">
            <h2 class="mb-0 text-center">
                <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapse<%=i%>" aria-expanded="false" aria-controls="collapse<%=i%>">
                    <%=modeloAcabados.get(i).getAcabado().getNombre()%>
                </button>
            </h2>
        </div>
        <div id="collapse<%=i%>" class="collapse" aria-labelledby="heading<%=i%>" data-parent="#accordionExample">
            <div class="card-body">
                <%List<Itemacabado> itemacabadoslist = modeloAcabados.get(i).getItemacabadoList();%>
                <%for(int j = 0; j < itemacabadoslist.size(); j++) {%>
                    <p class="text-center"><%=itemacabadoslist.get(j).getNombre()%></p>
                <%}%>
            </div>
        </div>
    </div>
    <% } %>
</div>
<% } %>

