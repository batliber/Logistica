function FormularioContrato(element, mode) {
	this.element = element;
	this.mode = mode;
	this.campos = {
		numeroTramite: { 
			campo: "numeroTramite", 
			etiqueta: "Número de trámite", 
			tipo: __TIPO_CAMPO_FORMULARIO_NUMERICO,
			mode: __FORM_FIELD_MODE_READ
		},
		estado: {
			campo: "estado.id", 
			campoAlternativo: "estado.nombre",
			etiqueta: "Estado", 
			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION, 
			listOptions: this.listarEstados,
			mode: __FORM_FIELD_MODE_READ
		},
//		vendedor: { 
//			campo: "vendedor.id", 
//			campoAlternativo: "vendedor.nombre",
//			etiqueta: "Vendedor", 
//			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION, 
//			listOptions: this.listarVendedores,
//			mode: __FORM_FIELD_MODE_READ
//		},
//		fechaVenta: { 
//			campo: "fechaVenta", 
//			etiqueta: "Fecha de venta", 
//			tipo: __TIPO_CAMPO_FORMULARIO_FECHA,
//			mode: __FORM_FIELD_MODE_READ
//		},
//		backoffice: { 
//			campo: "backoffice.id", 
//			campoAlternativo: "backoffice.nombre",
//			etiqueta: "Backoffice", 
//			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION, 
//			listOptions: this.listarBackoffices,
//			mode: __FORM_FIELD_MODE_READ
//		},
//		fechaBackoffice: { 
//			campo: "fechaBackoffice", 
//			etiqueta: "Fecha de armado", 
//			tipo: __TIPO_CAMPO_FORMULARIO_FECHA,
//			mode: __FORM_FIELD_MODE_READ
//		},
//		distribuidor: { 
//			campo: "distribuidor.id",
//			campoAlternativo: "distribuidor.nombre",
//			etiqueta: "Distribuidor", 
//			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION, 
//			listOptions: this.listarDistribuidores,
//			mode: __FORM_FIELD_MODE_READ
//		},
//		fechaEntregaDistribuidor: { 
//			campo: "fechaEntregaDistribuidor", 
//			etiqueta: "Fecha de entregado a distribuidor", 
//			tipo: __TIPO_CAMPO_FORMULARIO_FECHA,
//			mode: __FORM_FIELD_MODE_READ
//		},
//		fechaDevolucionDistribuidor: { 
//			campo: "fechaDevolucionDistribuidor", 
//			etiqueta: "Fecha de devuelto por distribuidor", 
//			tipo: __TIPO_CAMPO_FORMULARIO_FECHA,
//			mode: __FORM_FIELD_MODE_READ
//		},
//		activador: { 
//			campo: "activador.id", 
//			campoAlternativo: "activador.nombre",
//			etiqueta: "Activador", 
//			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION, 
//			listOptions: this.listarActivadores,
//			mode: __FORM_FIELD_MODE_READ
//		},
//		fechaEnvioAntel: { 
//			campo: "fechaEnvioAntel", 
//			etiqueta: "Fecha de envío a ANTEL", 
//			tipo: __TIPO_CAMPO_FORMULARIO_FECHA,
//			mode: __FORM_FIELD_MODE_READ
//		},
//		fechaActivacion: { 
//			campo: "fechaActivacion", 
//			etiqueta: "Fecha de activación", 
//			tipo: __TIPO_CAMPO_FORMULARIO_FECHA,
//			mode: __FORM_FIELD_MODE_READ
//		},
//		rol: { 
//			campo: "rol.id", 
//			campoAlternativo: "rol.nombre",
//			etiqueta: "Rol", 
//			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION, 
//			listOptions: this.listarRoles,
//			mode: __FORM_FIELD_MODE_READ
//		},
//		usuario: { 
//			campo: "usuario.id", 
//			campoAlternativo: "usuario.nombre",
//			etiqueta: "Usuario", 
//			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION, 
//			listOptions: this.listarUsuarios,
//			mode: __FORM_FIELD_MODE_READ
//		},
		empresa: { 
			campo: "empresa.id", 
			campoAlternativo: "empresa.nombre",
			etiqueta: "Empresa", 
			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION, 
			listOptions: this.listarEmpresas,
			mode: __FORM_FIELD_MODE_EDIT
		},
		mid: { 
			campo: "mid", 
			etiqueta: "MID", 
			tipo: __TIPO_CAMPO_FORMULARIO_NUMERICO,
			mode: __FORM_FIELD_MODE_EDIT
		},
		numeroContrato: { 
			campo: "numeroContrato", 
			etiqueta: "Número de contrato", 
			tipo: __TIPO_CAMPO_FORMULARIO_NUMERICO,
			mode: __FORM_FIELD_MODE_READ
		},
		fechaFinContrato: { 
			campo: "fechaFinContrato", 
			etiqueta: "Fin de contrato", 
			tipo: __TIPO_CAMPO_FORMULARIO_FECHA,
			mode: __FORM_FIELD_MODE_READ
		},
		tipoContratoDescripcion: { 
			campo: "tipoContratoDescripcion", 
			etiqueta: "Plan actual", 
			tipo: __TIPO_CAMPO_FORMULARIO_STRING,
			mode: __FORM_FIELD_MODE_EDIT
		},
		nuevoPlan: { 
			campo: "nuevoPlan", 
			etiqueta: "Nuevo plan", 
			tipo: __TIPO_CAMPO_FORMULARIO_STRING,
			mode: __FORM_FIELD_MODE_EDIT
		},
		equipo: { 
			campo: "equipo", 
			etiqueta: "Equipo", 
			tipo: __TIPO_CAMPO_FORMULARIO_STRING,
			mode: __FORM_FIELD_MODE_EDIT
		},
		producto: { 
			campo: "producto.id", 
			campoAlternativo: "producto.nombre",
			etiqueta: "Nuevo equipo", 
			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION,
			listOptions: this.listarProductos,
			mode: __FORM_FIELD_MODE_EDIT
		},
		numeroSerie: { 
			campo: "numeroSerie", 
			etiqueta: "Número de serie", 
			tipo: __TIPO_CAMPO_FORMULARIO_STRING,
			mode: __FORM_FIELD_MODE_EDIT
		},
		precio: { 
			campo: "precio", 
			etiqueta: "Precio", 
			tipo: __TIPO_CAMPO_FORMULARIO_NUMERICO,
			mode: __FORM_FIELD_MODE_EDIT
		},
		localidad: { 
			campo: "localidad", 
			etiqueta: "Localidad", 
			tipo: __TIPO_CAMPO_FORMULARIO_STRING,
			mode: __FORM_FIELD_MODE_EDIT
		},
		codigoPostal: { 
			campo: "codigoPostal", 
			etiqueta: "Código postal", 
			tipo: __TIPO_CAMPO_FORMULARIO_STRING,
			mode: __FORM_FIELD_MODE_EDIT
		},
		direccion: { 
			campo: "direccion", 
			etiqueta: "Dirección", 
			tipo: __TIPO_CAMPO_FORMULARIO_STRING,
			mode: __FORM_FIELD_MODE_READ
		},
		documento: { 
			campo: "documento", 
			etiqueta: "Documento", 
			tipo: __TIPO_CAMPO_FORMULARIO_STRING,
			mode: __FORM_FIELD_MODE_EDIT
		},
		nombre: { 
			campo: "nombre", 
			etiqueta: "Nombre", 
			tipo: __TIPO_CAMPO_FORMULARIO_STRING,
			mode: __FORM_FIELD_MODE_EDIT
		},
		numeroFactura: { 
			campo: "numeroFactura", 
			etiqueta: "Número de factura", 
			tipo: __TIPO_CAMPO_FORMULARIO_NUMERICO,
			mode: __FORM_FIELD_MODE_EDIT
		},
		direccionFactura: { 
			campo: "direccionFactura", 
			etiqueta: "Dirección de factura", 
			tipo: __TIPO_CAMPO_FORMULARIO_STRING,
			mode: __FORM_FIELD_MODE_EDIT
		},
		direccionEntrega: { 
			campo: "direccionEntrega", 
			etiqueta: "Dirección de entrega", 
			tipo: __TIPO_CAMPO_FORMULARIO_STRING,
			mode: __FORM_FIELD_MODE_EDIT
		},
		telefonoContacto: { 
			campo: "telefonoContacto", 
			etiqueta: "Teléfono de contacto", 
			tipo: __TIPO_CAMPO_FORMULARIO_STRING,
			mode: __FORM_FIELD_MODE_EDIT
		},
		email: { 
			campo: "email", 
			etiqueta: "E-Mail", 
			tipo: __TIPO_CAMPO_FORMULARIO_STRING,
			mode: __FORM_FIELD_MODE_EDIT
		},
		departamento: { 
			campo: "zona.departamento.id", 
			campoAlternativo: "zona.departamento.nombre",
			etiqueta: "Departamento", 
			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION, 
			listOptions: this.listarDepartamentos,
			onChange: this.departamentoOnChange,
			mode: __FORM_FIELD_MODE_EDIT
		},
		barrio: { 
			campo: "barrio.id", 
			campoAlternativo: "barrio.nombre",
			etiqueta: "Barrio", 
			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION, 
			listOptions: this.listarBarrios,
			mode: __FORM_FIELD_MODE_EDIT
		},
		zona: { 
			campo: "zona.id", 
			campoAlternativo: "zona.nombre",
			etiqueta: "Zona", 
			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION, 
			listOptions: this.listarZonas,
			mode: __FORM_FIELD_MODE_EDIT
		},
		turno: { 
			campo: "turno.id", 
			campoAlternativo: "turno.nombre",
			etiqueta: "Turno", 
			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION, 
			listOptions: this.listarTurnos,
			mode: __FORM_FIELD_MODE_EDIT
		},
		fechaEntrega: { 
			campo: "fechaEntrega", 
			etiqueta: "Fecha de entrega", 
			tipo: __TIPO_CAMPO_FORMULARIO_SELECCION, 
			listOptions: this.listarFechasEntrega,
			mode: __FORM_FIELD_MODE_EDIT
		},
		fechaActivarEn: { 
			campo: "fechaActivarEn", 
			etiqueta: "Activar en", 
			tipo: __TIPO_CAMPO_FORMULARIO_FECHA,
			mode: __FORM_FIELD_MODE_EDIT
		},
		observaciones: { 
			campo: "observaciones", 
			etiqueta: "Observaciones", 
			tipo: __TIPO_CAMPO_FORMULARIO_TEXT,
			mode: __FORM_FIELD_MODE_EDIT
		}
	};
	
	this.formularioDinamico = new FormularioDinamico(
		this.element,
		this.mode,
		this.campos
	);

	this.formularioDinamico.rebuild();
};

FormularioContrato.prototype.listarEmpresas = function() {
	var result = [];
	
	UsuarioRolEmpresaDWR.listEmpresasByContext(
		{
			callback: function(data) {
				for (var i=0; i<data.length; i++) {
					result[result.length] = {
						valor: data[i].id,
						etiqueta: data[i].nombre
					};
				}
			}, async: false
		}
	);
	
	return result;
};

FormularioContrato.prototype.listarProductos = function() {
	var result = [];
	
	StockMovimientoDWR.listStockByEmpresaId(
		1,
		{
			callback: function(data) {
				for (var i=0; i<data.length; i++) {
					result[result.length] = {
						etiqueta: data[i].producto.descripcion + " (" + data[i].cantidad + ")",
						valor: data[i].producto.id
					};
				}
			}, async: false
		}
	);
	
	return result;
};

FormularioContrato.prototype.listarVendedores = function() {
	var result = [];
	
	UsuarioRolEmpresaDWR.listVendedoresByContext(
		{
			callback: function(data) {
				var ids = {};
				
				for (var i=0; i<data.length; i++) {
					if (ids[data[i].id] == null || !ids[data[i].id]) {
						result[result.length] = {
							valor: data[i].id,
							etiqueta: data[i].nombre
						};
						
						ids[data[i].id] = true;
					}
				}
			}, async: false
		}
	);
	
	return result;
};

FormularioContrato.prototype.listarBackoffices = function() {
	var result = [];
	
	UsuarioRolEmpresaDWR.listBackofficesByContext(
		{
			callback: function(data) {
				var ids = {};
				
				for (var i=0; i<data.length; i++) {
					if (ids[data[i].id] == null || !ids[data[i].id]) {
						result[result.length] = {
							valor: data[i].id,
							etiqueta: data[i].nombre
						};
						
						ids[data[i].id] = true;
					}
				}
			}, async: false
		}
	);
	
	return result;
};

FormularioContrato.prototype.listarDistribuidores = function() {
	var result = [];
	
	UsuarioRolEmpresaDWR.listDistribuidoresByContext(
		{
			callback: function(data) {
				var ids = {};
				
				for (var i=0; i<data.length; i++) {
					if (ids[data[i].id] == null || !ids[data[i].id]) {
						result[result.length] = {
							valor: data[i].id,
							etiqueta: data[i].nombre
						};
						
						ids[data[i].id] = true;
					}
				}
			}, async: false
		}
	);
	
	return result;
};

FormularioContrato.prototype.listarActivadores = function() {
	var result = [];
	
	UsuarioRolEmpresaDWR.listActivadoresByContext(
		{
			callback: function(data) {
				var ids = {};
				
				for (var i=0; i<data.length; i++) {
					if (ids[data[i].id] == null || !ids[data[i].id]) {
						result[result.length] = {
							valor: data[i].id,
							etiqueta: data[i].nombre
						};
						
						ids[data[i].id] = true;
					}
				}
			}, async: false
		}
	);
	
	return result;
};

FormularioContrato.prototype.listarUsuarios = function() {
	var result = [];
	
	UsuarioDWR.list(
		{
			callback: function(data) {
				var ids = {};
				
				for (var i=0; i<data.length; i++) {
					if (ids[data[i].id] == null || !ids[data[i].id]) {
						result[result.length] = {
							valor: data[i].id,
							etiqueta: data[i].nombre
						};
						
						ids[data[i].id] = true;
					}
				}
			}, async: false
		}
	);
	
	return result;
};

FormularioContrato.prototype.listarRoles = function() {
	var result = [];
	
	RolDWR.list(
		{
			callback: function(data) {
				var ids = {};
				
				for (var i=0; i<data.length; i++) {
					if (ids[data[i].id] == null || !ids[data[i].id]) {
						result[result.length] = {
							valor: data[i].id,
							etiqueta: data[i].nombre
						};
						
						ids[data[i].id] = true;
					}
				}
			}, async: false
		}
	);
	
	return result;
};

FormularioContrato.prototype.listarEstados = function() {
	var result = [];
	
	EstadoDWR.list(
		{
			callback: function(data) {
				for (var i=0; i<data.length; i++) {
					result[result.length] = {
						valor: data[i].id,
						etiqueta: data[i].nombre
					};
				}
			}, async: false
		}
	);
	
	return result;
};

FormularioContrato.prototype.listarDepartamentos = function() {
	var result = [];
	
	DepartamentoDWR.list(
		{
			callback: function(data) {
				for (var i=0; i<data.length; i++) {
					result[result.length] = {
						valor: data[i].id,
						etiqueta: data[i].nombre
					};
				}
			}, async: false
		}
	);
	
	return result;
};

FormularioContrato.prototype.listarBarrios = function() {
	var result = [];
	
	BarrioDWR.list(
		{
			callback: function(data) {
				for (var i=0; i<data.length; i++) {
					result[result.length] = {
						valor: data[i].id,
						etiqueta: data[i].nombre
					};
				}
			}, async: false
		}
	);
	
	return result;
};

FormularioContrato.prototype.listarZonas = function() {
	var result = [];
	
	ZonaDWR.list(
		{
			callback: function(data) {
				for (var i=0; i<data.length; i++) {
					result[result.length] = {
						valor: data[i].id,
						etiqueta: data[i].nombre
					};
				}
			}, async: false
		}
	);
	
	return result;
};

FormularioContrato.prototype.listarTurnos = function() {
	var result = [];
	
	TurnoDWR.list(
		{
			callback: function(data) {
				for (var i=0; i<data.length; i++) {
					result[result.length] = {
						valor: data[i].id,
						etiqueta: data[i].nombre
					};
				}
			}, async: false
		}
	);
	
	return result;
};

FormularioContrato.prototype.listarFechasEntrega = function() {
	var result = [];
	
	return result;
};

FormularioContrato.prototype.departamentoOnChange = function(eventObject) {
	var barrios = [];
	
	BarrioDWR.listByDepartamentoId(
		1,
		{
			callback: function(data) {
				for (var i=0; i<data.length; i++) {
					barrios[barrios.length] = {
						valor: data[i].id,
						etiqueta: data[i].nombre
					};
				}
			}, async: false
		}
	);
	
	formularioDinamico.setOptions("barrios.id", barrios);
};

FormularioContrato.prototype.clear = function(contrato) {
	this.formularioDinamico.clear();
};

FormularioContrato.prototype.setContrato = function(contrato) {
	this.contrato = contrato;
	
	this.formularioDinamico.reload(this.contrato);
};