package uy.com.amensg.logistica.bean;

import javax.ejb.Local;

@Local
public interface IImportacionArchivoBeanLocal {

	public String preprocesarArchivo(String fileName);
	
	public Long procesarArchivo(String fileName, Long formatoImportacionArchivoId, Long loggedUsuarioId);
	
	public void iniciarProcesamientoAsincrono(
		String fileName, Long procesoImportacionId, Long formatoImportacionArchivoId, Long loggedUsuarioId
	);
	
	public String consultarEstadoImportacionArchivo(Long procesoImportacionId);
}