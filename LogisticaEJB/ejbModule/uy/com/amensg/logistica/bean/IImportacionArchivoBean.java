package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;

@Remote
public interface IImportacionArchivoBean {

	public String preprocesarArchivo(String fileName);
	
	public Long procesarArchivo(String fileName, Long formatoImportacionArchivoId, Long loggedUsuarioId);
	
	public String consultarEstadoImportacionArchivo(Long procesoImportacionId);
}