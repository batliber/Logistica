package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EmpresaTO;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.FormaPagoTO;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioTO;

public class EmpresaDWR {
	
	public static EmpresaTO transform(Empresa empresa, boolean transformCollections) {
		EmpresaTO result = new EmpresaTO();
		
		result.setCodigoPromotor(empresa.getCodigoPromotor());
		result.setDireccion(empresa.getDireccion());
		result.setLogoURL(empresa.getLogoURL());
		result.setNombre(empresa.getNombre());
		result.setNombreContrato(empresa.getNombreContrato());
		result.setNombreSucursal(empresa.getNombreSucursal());
		
		if (transformCollections) {
			if (empresa.getFormaPagos() != null) {
				Collection<FormaPagoTO> formaPagos = new LinkedList<FormaPagoTO>();
				
				for (FormaPago formaPago : empresa.getFormaPagos()) {
					formaPagos.add(FormaPagoDWR.transform(formaPago));
				}
				
				result.setFormaPagos(formaPagos);
			}
			
			if (empresa.getEmpresaUsuarioContratos() != null) {
				Collection<UsuarioTO> empresaUsuarioContratos = new LinkedList<UsuarioTO>();
				
				for (Usuario usuario : empresa.getEmpresaUsuarioContratos()) {
					empresaUsuarioContratos.add(UsuarioDWR.transform(usuario, false));
				}
				
				result.setEmpresaUsuarioContratos(empresaUsuarioContratos);
			}
		}
		
		result.setFcre(empresa.getFcre());
		result.setFact(empresa.getFact());
		result.setId(empresa.getId());
		result.setTerm(empresa.getTerm());
		result.setUact(empresa.getUact());
		result.setUcre(empresa.getUcre());
		
		return result;
	}
}