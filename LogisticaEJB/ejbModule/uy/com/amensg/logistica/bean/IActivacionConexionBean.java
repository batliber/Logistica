package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.ActivacionConexion;

@Remote
public interface IActivacionConexionBean {

	public ActivacionConexion getRandomByEmpresaId(Long empresaId);
}