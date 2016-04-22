package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ResultadoEntregaDistribucion;

@Remote
public interface IResultadoEntregaDistribucionBean {

	public Collection<ResultadoEntregaDistribucion> list();
}