package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.Usuario;

@Remote
public interface IEmpresaBean {

	public Collection<Empresa> list();

	public Empresa getById(Long id, boolean initializeCollections);
	
	public Empresa getByIdAgente(Long idAgente, boolean initializeCollections);
	
	public Collection<FormaPago> listFormasPagoById(Long id);
	
	public Collection<Usuario> listEmpresaUsuarioContratosById(Long id);
	
	public Empresa save(Empresa empresa);
	
	public void remove(Empresa empresa);
	
	public void update(Empresa empresa);
}