$(document).ready(init)

function init() {
	refinarForm();
	
	$("#divEliminarModelo").hide();
	
	EmpresaServiceDWR.list(
		{
			callback: function(data) {
				$("#selectModeloEmpresaService option").remove();
				
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectModeloEmpresaService").append(html);
			}, async: false
		}
	);
	
	MarcaDWR.list(
		{
			callback: function(data) {
				$("#selectModeloMarca option").remove();
				
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectModeloMarca").append(html);
			}, async: false
		}
	);
	
	if (id != null) {
		ModeloDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputModeloDescripcion").val(data.descripcion);
					
					if (data.empresaService != null) {
						$("#selectModeloEmpresaService").val(data.empresaService.id);
					}
					
					if (data.marca != null) {
						$("#selectModeloMarca").val(data.marca.id);
					}
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarModelo").show();
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
	var modelo = {
		descripcion: $("#inputModeloDescripcion").val(),
		marca: {
			id: $("#selectModeloMarca").val()
		},
		empresaService: {
			id: $("#selectModeloEmpresaService").val()
		}
	};
	
	if (modelo.descripcion == "") {
		alert("Debe ingresar una descripción.");
		
		return;
	}
	
	if (modelo.marca.id == "") {
		alert("Debe seleccionar una marca.");
		
		return;
	}
	
	if (modelo.empresaService.id == "0") {
		alert("Debe seleccionar un service");
		
		return;
	}
	
	if (id != null) {
		modelo.id = id;
		
		ModeloDWR.update(
			modelo,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		ModeloDWR.add(
			modelo,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarModelo").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Modelo")) {
		var modelo = {
			id: id
		};
		
		ModeloDWR.remove(
			modelo,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}