var __FORM_MODE_READ = 0;
var __FORM_MODE_NEW = 1;
var __FORM_MODE_VENTA = 2;
var __FORM_MODE_BACKOFFICE = 3;
var __FORM_MODE_DISTRIBUCION = 4;
var __FORM_MODE_ACTIVACION = 5;
var __FORM_MODE_ADMIN = 6;
var __FORM_MODE_USER = 7;
var __FORM_MODE_RECOORDINACION = 7;
var __FORM_MODE_PRINT = 8;

var __TIPO_CAMPO_NUMERICO = 1;
var __TIPO_CAMPO_STRING = 2;
var __TIPO_CAMPO_FECHA = 3;
var __TIPO_CAMPO_MULTIPLE = 4;

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
var __ESTADO_RECOORDINAR = 14;
var __ESTADO_REDISTRIBUIR = 15;

function showPopUp(element) {
	element.style.display = "";
}

function closePopUp(event, element) {
	element.style.display = "none";
}