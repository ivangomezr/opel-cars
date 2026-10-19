
$(document).ready(function() {
    console.log('ready');      
    init();
});

function init(){
   onShowCita();
   onShowAcabados();
}

function onShowCita(){
    $('#modalCita').on('show.bs.modal', function (event) {
      var button = $(event.relatedTarget)    
      var nombreusuario = button.data('nombreusuario')
      var modal = $(this)
      modal.find('.changeText').text('Se va a proceder a registrar a ' +nombreusuario+ ' a fecha de hoy del siguiente modelo') 
    })
}

function onShowAcabados(){
    $('#modalAcabados').on('show.bs.modal', function (event) {
      var button = $(event.relatedTarget)
      var idmodelo = button.data('idmodelo')
      var nombremodelo = button.data('nombremodelo')
      var modal = $(this)
      modal.find('h4').text(nombremodelo)
      //AJAX
        $.ajax({
          type: "GET",
            url: "Controller?op=acabados&modelo="+idmodelo,
            success : function(info) {
                $("#acabados").html(info);
            }
        });
    })
}
