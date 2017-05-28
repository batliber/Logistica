$(document).ready(function() {
	refinarForm();
	
	$("#divEliminarProducto").hide();
	
	EmpresaServiceDWR.list(
		{
			callback: function(data) {
				$("#selectProductoEmpresaService option").remove();
				
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectProductoEmpresaService").append(html);
			}, async: false
		}
	);
	
	MarcaDWR.list(
		{
			callback: function(data) {
				$("#selectProductoMarca option").remove();
				
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectProductoMarca").append(html);
			}, async: false
		}
	);
	
	if (id != null) {
		ProductoDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputProductoDescripcion").val(data.descripcion);
					
					if (data.empresaService != null) {
						$("#selectProductoEmpresaService").val(data.empresaService.id);
					}
					
					if (data.marca != null) {
						$("#selectProductoMarca").val(data.marca.id);
					}
					
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
		marca: {
			id: $("#selectProductoMarca").val()
		},
		empresaService: {
			id: $("#selectProductoEmpresaService").val()
		}
	};
	
	if (producto.descripcion == "") {
		alert("Debe ingresar una descripción.");
		
		return;
	}
	
	if (producto.marca.id == "") {
		alert("Debe seleccionar una marca.");
		
		return;
	}
	
	if (producto.empresaService.id == "0") {
		alert("Debe seleccionar un service");
		
		return;
	}
	
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