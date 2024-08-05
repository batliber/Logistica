package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.ResultadoEntregaDistribucion;
import uy.com.amensg.logistica.entities.ResultadoEntregaDistribucionIZI;

@Remote
public interface IResultadoEntregaDistribucionIZIBean {

	public ResultadoEntregaDistribucionIZI getByResultadoEntregaDistribucionEstado(
		ResultadoEntregaDistribucion resultadoEntregaDistribucion,
		Estado estado
	);
}