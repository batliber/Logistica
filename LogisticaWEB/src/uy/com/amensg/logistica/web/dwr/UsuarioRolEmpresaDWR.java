package uy.com.amensg.logistica.web.dwr;

import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.web.entities.UsuarioRolEmpresaTO;

public class UsuarioRolEmpresaDWR {

	public static UsuarioRolEmpresaTO transform(UsuarioRolEmpresa usuarioRolEmpresa) {
		UsuarioRolEmpresaTO result = new UsuarioRolEmpresaTO();
		
		result.setEmpresa(EmpresaDWR.transform(usuarioRolEmpresa.getEmpresa(), false));
		result.setRol(RolDWR.transform(usuarioRolEmpresa.getRol(), true));
		
		result.setFcre(usuarioRolEmpresa.getFcre());
		result.setFact(usuarioRolEmpresa.getFact());
		result.setId(usuarioRolEmpresa.getId());
		result.setTerm(usuarioRolEmpresa.getTerm());
		result.setUact(usuarioRolEmpresa.getUact());
		result.setUcre(usuarioRolEmpresa.getUcre());
		
		return result;
	}
}