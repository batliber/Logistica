package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticioInstitucionFinanciera;

@Remote
public interface IBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean {

	public Collection<BCUInterfaceRiesgoCrediticioInstitucionFinanciera> listByBCUInterfaceRiesgoCrediticioId(Long id);
	
	public void save(BCUInterfaceRiesgoCrediticioInstitucionFinanciera bcuInterfaceRiesgoCrediticioInstitucionFinanciera);
}