package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Archivo;

@Remote
public interface IFileManagerBean {

	public Collection<Archivo> listArchivos();
}