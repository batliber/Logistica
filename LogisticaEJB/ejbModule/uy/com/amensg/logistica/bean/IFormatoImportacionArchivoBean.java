package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.FormatoImportacionArchivo;

@Remote
public interface IFormatoImportacionArchivoBean {

	public FormatoImportacionArchivo getById(Long id);
}