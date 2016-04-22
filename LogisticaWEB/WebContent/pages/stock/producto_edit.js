$(document).ready(function() {
	refinarForm();
	
	$("#divEliminarProducto").hide();
	
	if (id != null) {
		ProductoDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputProductoDescripcion").val(data.descripcion);
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarProducto").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
					}
				}, async: false
			}
		);
	}
});

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function inputGuardarOnClick(event) {
	var producto = {
		descripcion: $("#inputProductoDescripcion").val(),
	};
	
	if (id != null) {
		producto.id = id;
		
		ProductoDWR.update(
			producto,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		ProductoDWR.add(
			producto,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarProducto").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Equipo")) {
		var producto = {
			id: id
		};
		
		ProductoDWR.remove(
			producto,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}