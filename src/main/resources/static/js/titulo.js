$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var codigoTitulo = button.data('codigo');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if(!action.endsWith('/')){
		action += '/';
	}
	form.attr('action', action + codigoTitulo);
});

$(function(){
	$('.js-atualizar-status').on('click', function(event){
		event.preventDefault();
		
		var btnReceber = $(event.currentTarget);
		var urlReceber = btnReceber.attr('href');
		
		var response = $.ajax({
			url: urlReceber,
			type: 'PUT'
		});
		
		response.done(function(e){
			var codigoTitulo = btnReceber.data('codigo');
			$('[data-role=' + codigoTitulo + ']').html('<span class="badge badge-success">' + e + '</span>');
			
			btnReceber.hide();
			
		});
		
		response.fail(function(e){
			console.log('Erro: ', e);
			alert('Erro ao receber cobrança');
		});
	});
});