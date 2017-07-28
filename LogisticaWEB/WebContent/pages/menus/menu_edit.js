$(document).ready(init);

function init() {
	refinarForm();
	
	$("#divEliminarMenu").hide();
	
	MenuDWR.list(
		{
			callback: function(data) {
				$("#selectMenuPadre option").remove();
				
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					if (data[i].padre == null) {
						html += "<option value='" + data[i].id + "'>" + data[i].titulo + "</option>";
					}
				}
				
				$("#selectMenuPadre").append(html);
			}, async: false
		}
	);
	
	if (id != null) {
		MenuDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputMenuTitulo").val(data.titulo);
					$("#inputMenuURL").val(data.url);
					$("#inputMenuOrden").val(data.orden);
					
					if (data.padre != null) {
						$("#selectMenuPadre").val(data.padre);
					}
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarMenu").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
					}
				}, async: false
			}
		);
	}
}

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function inputGuardarOnClick(event) {
	var menu = {
		titulo: $("#inputMenuTitulo").val(),
		url: $("#inputMenuURL").val(),
		orden: $("#inputMenuOrden").val(),
		padre: $("#selectMenuPadre").val()
	};
	
	if (menu.titulo == "") {
		alert("Debe ingresar un título.");
		
		return;
	}
	
	if (menu.url == "") {
		alert("Debe ingresar una URL.");
		
		return;
	}
	
	if (menu.orden == "") {
		alert("Debe ingresar un orden.");
		
		return;
	}
	
	if (menu.padre == "") {
		menu.padre = null;
	}
	
	if (id != null) {
		menu.id = id;
		
		MenuDWR.update(
			menu,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		MenuDWR.add(
			menu,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarMenu").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Menu")) {
		var menu = {
			id: id
		};
		
		MenuDWR.remove(
			menu,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}