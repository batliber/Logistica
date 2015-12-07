package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.Empresa;

@Remote
public interface IContratoBean {

	public Contrato getById(Long id);
	
	public Contrato getByMidEmpresa(Long mid, Empresa empresa);
	
	public Contrato update(Contrato contrato);
}