var __ROL_ADMINISTRADOR = 1;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonNew").hide();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
    }).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR) {
				mode = __FORM_MODE_ADMIN;
				
				grid = new Grid(
					document.getElementById("divTableCuentas"),
					{
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" } },
						tdRecargaBanco: { campo: "recargaBanco.nombre", clave: "recargaBanco.id", descripcion: "Banco", abreviacion: "Banco", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listRecargaBancos, clave: "id", valor: "nombre" } },
						tdMoneda: { campo: "moneda.simbolo", clave: "moneda.id", descripcion: "Moneda", abreviacion: "Moneda", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMonedas, clave: "id", valor: "nombre" } },
						tdNumero: { campo: "numero", descripcion: "Número", abreviacion: "Número", tipo: __TIPO_CAMPO_STRING }
					}, 
					false,
					reloadData,
					trCuentaOnClick
				);
				
				grid.rebuild();
				
				$("#divButtonNew").show();
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				break;
			}
		}
		
		reloadData();
		
		$("#divIFrameCuenta").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/EmpresaRecargaBancoCuentaREST/listContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/EmpresaRecargaBancoCuentaREST/countContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.setCount(data);
    });
}

function trCuentaOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_NEW;
	
	document.getElementById("iFrameCuenta").src = "./cuenta_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameCuenta"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameCuenta").src = "./cuenta_edit.jsp";
	showPopUp(document.getElementById("divIFrameCuenta"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}