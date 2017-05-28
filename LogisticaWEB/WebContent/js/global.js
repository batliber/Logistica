var __FORM_MODE_READ = 0;
var __FORM_MODE_NEW = 1;
var __FORM_MODE_VENTA = 2;
var __FORM_MODE_BACKOFFICE = 3;
var __FORM_MODE_DISTRIBUCION = 4;
var __FORM_MODE_ACTIVACION = 5;
<<<<<<< HEAD
var __FORM_MODE_SUPERVISOR_ACTIVACION = 6;
var __FORM_MODE_ADMIN = 7;
var __FORM_MODE_USER = 8;
var __FORM_MODE_RECOORDINACION = 9;
var __FORM_MODE_PRINT = 10;
var __FORM_MODE_REDISTRIBUCION = 11;
=======
var __FORM_MODE_ADMIN = 6;
var __FORM_MODE_USER = 9;
var __FORM_MODE_RECOORDINACION = 7;
var __FORM_MODE_PRINT = 8;
var __FORM_MODE_REDISTRIBUCION = 10;
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git

var __TIPO_CAMPO_NUMERICO = 1;
var __TIPO_CAMPO_STRING = 2;
var __TIPO_CAMPO_FECHA = 3;
var __TIPO_CAMPO_FECHA_HORA = 4;
var __TIPO_CAMPO_FECHA_MES_ANO = 5;
var __TIPO_CAMPO_RELACION = 6;
var __TIPO_CAMPO_MULTIPLE = 7;
var __TIPO_CAMPO_DECIMAL = 8;
<<<<<<< HEAD
var __TIPO_CAMPO_BOOLEAN = 9;
var __TIPO_CAMPO_DETAIL = 10;
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git

var __FORM_FIELD_MODE_EDIT = 1;
var __FORM_FIELD_MODE_READ = 2;

var __TIPO_CAMPO_FORMULARIO_NUMERICO = 1;
var __TIPO_CAMPO_FORMULARIO_STRING = 2;
var __TIPO_CAMPO_FORMULARIO_TEXT = 3;
var __TIPO_CAMPO_FORMULARIO_FECHA = 4;
var __TIPO_CAMPO_FORMULARIO_SELECCION = 5;

var __ESTADO_LLAMAR = 1;
var __ESTADO_RELLAMAR = 2;
var __ESTADO_RECHAZADO = 3;
var __ESTADO_VENDIDO = 4;
var __ESTADO_REAGENDAR = 5;
var __ESTADO_RENOVADO = 6;
var __ESTADO_TELELINK = 7;
var __ESTADO_DISTRIBUIR = 8;
var __ESTADO_NO_FIRMA = 9;
var __ESTADO_ACTIVAR = 10;
var __ESTADO_ACM = 11;
var __ESTADO_FALTA_DOCUMENTACION = 12;
var __ESTADO_ACT_DOC_VENTA = 13;
var __ESTADO_RECOORDINAR = 14;
var __ESTADO_REDISTRIBUIR = 15;
var __ESTADO_CONTROL_ANTEL = 16;

function showPopUp(element) {
//	window.top.showBackground();
	element.style.display = "";
}

function closePopUp(event, element) {
//	window.top.hideBackground();
	element.style.display = "none";
}