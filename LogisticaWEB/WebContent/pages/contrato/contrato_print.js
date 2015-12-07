$(document).ready(function() {
	ContratoDWR.getById(
		id,
		{
			callback: function(data) {
				$("#divNumeroTramite").html(
					data.numeroTramite != null ? data.numeroTramite : "&nbsp;"
				);
				$("#divNumeroTramiteBarCode").css("background-image", "url(/LogisticaWEB/Barcode?code=" + data.numeroTramite + ")");
				$("#divMid").html(
					data.mid != null ? data.mid : "&nbsp;"
				);
				$("#divEstado").html(
					data.estado != null ? data.estado.nombre : "&nbsp;"
				);
				$("#divFechaVenta").html(
					data.fechaVenta != null ? formatShortDate(data.fechaVenta) : "&nbsp;"
				);
				$("#divLocalidad").html(
					data.localidad != null ? data.localidad : "&nbsp;"
				);
				$("#divCodigoPostal").html(
					data.codigoPostal != null ? data.codigoPostal : "&nbsp;"
				);
				$("#divFechaVencimiento").html(
					data.fechaFinContrato != null ? formatShortDate(data.fechaFinContrato) : "&nbsp;"
				);
				$("#divNumeroContrato").html(
					data.numeroContrato != null ? data.numeroContrato : "&nbsp;"
				);
				$("#divPlan").html(
					data.tipoContratoDescripcion != null ? data.tipoContratoDescripcion : "&nbsp;"
				); 
				$("#divNuevoPlan").html(
					data.nuevoPlan != null ? data.nuevoPlan : "&nbsp;"
				);
				$("#divEquipo").html(
					data.producto != null ? data.producto.descripcion : "&nbsp;"
				);
				$("#divNumeroSerie").html(
					data.numeroSerie != null ? data.numeroSerie : "&nbsp;"
				);
				$("#divDocumento").html(
					data.documento != null ? data.documento : "&nbsp;"
				);
				$("#divNombre").html(
					data.nombre != null ? data.nombre : "&nbsp;"
				);
				$("#divNumeroFactura").html(
					data.numeroFactura != null ? data.numeroFactura : "&nbsp;"
				); 
				$("#divDireccionFactura").html(
					data.direccionFactura != null ? data.direccionFactura : "&nbsp;"
				);
				$("#divDireccionEntrega").html(
					data.direccionEntrega != null ? data.direccionEntrega : "&nbsp;"
				);
				$("#divTelefonoContacto").html(
					data.telefonoContacto != null ? data.telefonoContacto : "&nbsp;"
				);
				$("#divEmail").html(
					data.email != null ? data.email : "&nbsp;"
				);
				$("#divPrecio").html(
					data.precio != null ? data.precio : "&nbsp;"
				);
				$("#divDepartamento").html(
					data.zona != null ? data.zona.departamento.nombre : "&nbsp;"
				);
				$("#divZona").html(
					data.zona != null ? data.zona.nombre : "&nbsp"
				);
				$("#divTurno").html(
					data.turno != null ? data.turno.nombre : "&nbsp;"
				);
				$("#divFechaEntrega").html(
					data.fechaEntrega != null ? formatShortDate(data.fechaEntrega) : "&nbsp;"
				);
				$("#divFechaActivarEn").html(
					data.fechaActivarEn != null ? formatShortDate(data.fechaActivarEn) : "&nbsp;"
				);
				$("#divObservaciones").html(
					data.observaciones != null ? data.observaciones : "&nbsp;"
				);
				
				/*
				$("#divEmpresa").html(
					data.empresa != null ? data.empresa.nombre : "&nbsp;"
				);
				
				$("#divRol").html(
					data.rol != null ? data.rol.nombre : "&nbsp;"
				);
				
				$("#divUsuario").html(
					data.usuario != null ? data.usuario.nombre : "&nbsp;"
				);
				*/
			}, async: false
		}
	);
});