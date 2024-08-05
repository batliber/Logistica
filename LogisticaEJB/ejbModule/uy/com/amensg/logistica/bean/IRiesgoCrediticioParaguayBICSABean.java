package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.RiesgoCrediticioParaguayBICSA;

@Remote
public interface IRiesgoCrediticioParaguayBICSABean {

	public Collection<RiesgoCrediticioParaguayBICSA> listByRiesgoCrediticioParaguayId(Long riesgoCrediticioParaguayId);
	
	public RiesgoCrediticioParaguayBICSA save(RiesgoCrediticioParaguayBICSA riesgoCrediticioParaguayBICSA);
}