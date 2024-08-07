package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;

@Remote
public interface IUsuarioRolEmpresaBean {
	
	public Collection<UsuarioRolEmpresa> listByUsuario(Usuario usuario);
	
	public Collection<UsuarioRolEmpresa> listVendedoresByUsuario(Usuario usuario);
	
	public Collection<UsuarioRolEmpresa> listBackofficesByUsuario(Usuario usuario);
	
	public Collection<UsuarioRolEmpresa> listDistribuidoresByUsuario(Usuario usuario);
	
	public Collection<UsuarioRolEmpresa> listDistribuidoresByUsuarioMinimal(Usuario usuario);
	
	public Collection<UsuarioRolEmpresa> listActivadoresByUsuario(Usuario usuario);
	
	public Collection<UsuarioRolEmpresa> listDistribuidoresChipsByUsuario(Usuario usuario);
	
	public Collection<UsuarioRolEmpresa> listAtencionClienteOperadoresByUsuario(Usuario usuario);
	
	public Collection<UsuarioRolEmpresa> listAtencionClienteGestionadoresByUsuario(Usuario usuario);
	
	public void save(UsuarioRolEmpresa usuarioRolEmpresa);
}