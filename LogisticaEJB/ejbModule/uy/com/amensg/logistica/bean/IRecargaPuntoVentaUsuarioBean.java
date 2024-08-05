package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.RecargaPuntoVentaUsuario;
import uy.com.amensg.logistica.entities.Usuario;

@Remote
public interface IRecargaPuntoVentaUsuarioBean {

	public PuntoVenta getPuntoVentaByUsuario(Usuario usuario);
	
	public RecargaPuntoVentaUsuario update(RecargaPuntoVentaUsuario usuario);
}