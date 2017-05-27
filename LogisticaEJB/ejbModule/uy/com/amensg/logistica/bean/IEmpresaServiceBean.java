package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.EmpresaService;

@Remote
public interface IEmpresaServiceBean {

	public Collection<EmpresaService> list();
	
	public EmpresaService getById(Long id);
	
	public void save(EmpresaService empresaService);
	
	public void remove(EmpresaService empresaService);
	
	public void update(EmpresaService empresaService);
}