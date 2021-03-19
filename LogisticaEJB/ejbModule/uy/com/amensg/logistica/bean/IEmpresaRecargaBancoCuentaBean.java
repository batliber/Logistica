package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EmpresaRecargaBancoCuenta;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.RecargaBanco;

@Remote
public interface IEmpresaRecargaBancoCuentaBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Collection<EmpresaRecargaBancoCuenta> listByEmpresaRecargaBancoMoneda(
		Empresa empresa, RecargaBanco recargaBanco, Moneda moneda
	);
	
	public EmpresaRecargaBancoCuenta getById(Long id);
	
	public EmpresaRecargaBancoCuenta save(EmpresaRecargaBancoCuenta empresaRecargaBancoCuenta);
	
	public void remove(EmpresaRecargaBancoCuenta empresaRecargaBancoCuenta);
	
	public void update(EmpresaRecargaBancoCuenta empresaRecargaBancoCuenta);
}