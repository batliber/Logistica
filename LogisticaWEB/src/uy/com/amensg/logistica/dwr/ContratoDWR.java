package uy.com.amensg.logistica.dwr;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ContratoBean;
import uy.com.amensg.logistica.bean.IContratoBean;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoTO;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.Producto;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Turno;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.Zona;

@RemoteProxy
public class ContratoDWR {

	private IContratoBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = ContratoBean.class.getSimpleName();
		String remoteInterfaceName = IContratoBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IContratoBean) context.lookup(lookupName);
	}
	
	public ContratoTO getById(Long id) {
		ContratoTO result = null;
		
		try {
			IContratoBean iContratoBean = lookupBean();
			
			result = transform(iContratoBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void update(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.update(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ContratoTO transform(Contrato contrato) {
		ContratoTO contratoTO = new ContratoTO();
		
		contratoTO.setAgente(contrato.getAgente());
		contratoTO.setCodigoPostal(contrato.getCodigoPostal());
		contratoTO.setDireccion(contrato.getDireccion());
		contratoTO.setDireccionEntrega(contrato.getDireccionEntrega());
		contratoTO.setDireccionFactura(contrato.getDireccionFactura());
		contratoTO.setDocumentoTipo(contrato.getDocumentoTipo());
		contratoTO.setDocumento(contrato.getDocumento());
		contratoTO.setEmail(contrato.getEmail());
		contratoTO.setEquipo(contrato.getEquipo());
		contratoTO.setFechaActivacion(contrato.getFechaActivacion());
		contratoTO.setFechaActivarEn(contrato.getFechaActivarEn());
		contratoTO.setFechaDevolucionDistribuidor(contrato.getFechaDevolucionDistribuidor());
		contratoTO.setFechaEntregaDistribuidor(contrato.getFechaEntregaDistribuidor());
		contratoTO.setFechaEntrega(contrato.getFechaEntrega());
		contratoTO.setFechaRechazo(contrato.getFechaRechazo());
		contratoTO.setFechaVenta(contrato.getFechaVenta());
		contratoTO.setFechaFinContrato(contrato.getFechaFinContrato());
		contratoTO.setFechaVenta(contrato.getFechaVenta());
		contratoTO.setLocalidad(contrato.getLocalidad());
		contratoTO.setMid(contrato.getMid());
		contratoTO.setNombre(contrato.getNombre());
		contratoTO.setNuevoPlan(contrato.getNuevoPlan());
		contratoTO.setNumeroCliente(contrato.getNumeroCliente());
		contratoTO.setNumeroContrato(contrato.getNumeroContrato());
		contratoTO.setNumeroFactura(contrato.getNumeroFactura());
		contratoTO.setNumeroSerie(contrato.getNumeroSerie());
		contratoTO.setNumeroTramite(contrato.getNumeroTramite());
		contratoTO.setObservaciones(contrato.getObservaciones());
		contratoTO.setPrecio(contrato.getPrecio());
		contratoTO.setTelefonoContacto(contrato.getTelefonoContacto());
		contratoTO.setTipoContratoCodigo(contrato.getTipoContratoCodigo());
		contratoTO.setTipoContratoDescripcion(contrato.getTipoContratoDescripcion());
		
		if (contrato.getProducto() != null) {
			contratoTO.setProducto(ProductoDWR.transform(contrato.getProducto()));
		}
		if (contrato.getTurno() != null) {
			contratoTO.setTurno(TurnoDWR.transform(contrato.getTurno()));
		}
		if (contrato.getZona() != null) {
			contratoTO.setZona(ZonaDWR.transform(contrato.getZona()));
		}
		if (contrato.getEstado() != null) {
			contratoTO.setEstado(EstadoDWR.transform(contrato.getEstado()));
		}
		if (contrato.getEmpresa() != null) {
			contratoTO.setEmpresa(EmpresaDWR.transform(contrato.getEmpresa()));
		}
		if (contrato.getRol() != null) {
			contratoTO.setRol(RolDWR.transform(contrato.getRol()));
		}
		if (contrato.getUsuario() != null) {
			contratoTO.setUsuario(UsuarioDWR.transform(contrato.getUsuario()));
		}
		if (contrato.getActivador() != null) {
			contratoTO.setActivador(UsuarioDWR.transform(contrato.getActivador()));
		}
		if (contrato.getBackoffice() != null) {
			contratoTO.setBackoffice(UsuarioDWR.transform(contrato.getBackoffice()));
		}
		if (contrato.getDistribuidor() != null) {
			contratoTO.setDistribuidor(UsuarioDWR.transform(contrato.getDistribuidor()));
		}
		if (contrato.getVendedor() != null) {
			contratoTO.setVendedor(UsuarioDWR.transform(contrato.getVendedor()));
		}
		
		contratoTO.setFact(contrato.getFact());
		contratoTO.setId(contrato.getId());
		contratoTO.setUact(contrato.getUact());
		contratoTO.setTerm(contrato.getTerm());
		
		return contratoTO;
	}

	public static Contrato transform(ContratoTO contratoTO) {
		Contrato contrato = new Contrato();
		
		contrato.setAgente(contratoTO.getAgente());
		contrato.setCodigoPostal(contratoTO.getCodigoPostal());
		contrato.setDireccion(contratoTO.getDireccion());
		contrato.setDireccionEntrega(contratoTO.getDireccionEntrega());
		contrato.setDireccionFactura(contratoTO.getDireccionFactura());
		contrato.setDocumentoTipo(contratoTO.getDocumentoTipo());
		contrato.setDocumento(contratoTO.getDocumento());
		contrato.setEmail(contratoTO.getEmail());
		contrato.setEquipo(contratoTO.getEquipo());
		contrato.setFechaActivacion(contratoTO.getFechaActivacion());
		contrato.setFechaActivarEn(contratoTO.getFechaActivarEn());
		contrato.setFechaDevolucionDistribuidor(contratoTO.getFechaDevolucionDistribuidor());
		contrato.setFechaEntregaDistribuidor(contratoTO.getFechaEntregaDistribuidor());
		contrato.setFechaEntrega(contratoTO.getFechaEntrega());
		contrato.setFechaVenta(contratoTO.getFechaVenta());
		contrato.setFechaFinContrato(contratoTO.getFechaFinContrato());
		contrato.setFechaRechazo(contratoTO.getFechaRechazo());
		contrato.setFechaVenta(contratoTO.getFechaVenta());
		contrato.setLocalidad(contratoTO.getLocalidad());
		contrato.setMid(contratoTO.getMid());
		contrato.setNombre(contratoTO.getNombre());
		contrato.setNuevoPlan(contratoTO.getNuevoPlan());
		contrato.setNumeroCliente(contratoTO.getNumeroCliente());
		contrato.setNumeroContrato(contratoTO.getNumeroContrato());
		contrato.setNumeroFactura(contratoTO.getNumeroFactura());
		contrato.setNumeroSerie(contratoTO.getNumeroSerie());
		contrato.setNumeroTramite(contratoTO.getNumeroTramite());
		contrato.setObservaciones(contratoTO.getObservaciones());
		contrato.setPrecio(contratoTO.getPrecio());
		contrato.setTelefonoContacto(contratoTO.getTelefonoContacto());
		contrato.setTipoContratoCodigo(contratoTO.getTipoContratoCodigo());
		contrato.setTipoContratoDescripcion(contratoTO.getTipoContratoDescripcion());
		
		if (contratoTO.getProducto() != null) {
			Producto producto = new Producto();
			producto.setId(contratoTO.getProducto().getId());
			
			contrato.setProducto(producto);
		}
		if (contratoTO.getTurno() != null) {
			Turno turno = new Turno();
			turno.setId(contratoTO.getTurno().getId());
			
			contrato.setTurno(turno);
		}
		if (contratoTO.getZona() != null) {
			Zona zona = new Zona();
			zona.setId(contratoTO.getZona().getId());
			
			contrato.setZona(zona);
		}
		if (contratoTO.getEstado() != null) {
			Estado estado = new Estado();
			estado.setId(contratoTO.getEstado().getId());
			
			contrato.setEstado(estado);
		}
		if (contratoTO.getEmpresa() != null) {
			Empresa empresa = new Empresa();
			empresa.setId(contratoTO.getEmpresa().getId());
			
			contrato.setEmpresa(empresa);
		}
		if (contratoTO.getRol() != null) {
			Rol rol = new Rol();
			rol.setId(contratoTO.getRol().getId());
			
			contrato.setRol(rol);
		}
		if (contratoTO.getUsuario() != null) {
			Usuario usuario = new Usuario();
			usuario.setId(contratoTO.getUsuario().getId());
			
			contrato.setUsuario(usuario);
		}
		if (contratoTO.getActivador() != null) {
			Usuario activador = new Usuario();
			activador.setId(contratoTO.getActivador().getId());
			
			contrato.setActivador(activador);
		}
		if (contratoTO.getBackoffice() != null) {
			Usuario backoffice = new Usuario();
			backoffice.setId(contratoTO.getBackoffice().getId());
			
			contrato.setBackoffice(backoffice);
		}
		if (contratoTO.getDistribuidor() != null) {
			Usuario distribuidor = new Usuario();
			distribuidor.setId(contratoTO.getDistribuidor().getId());
			
			contrato.setDistribuidor(distribuidor);
		}
		if (contratoTO.getVendedor() != null) {
			Usuario vendedor = new Usuario();
			vendedor.setId(contratoTO.getVendedor().getId());
			
			contrato.setVendedor(vendedor);
		}
		
		contrato.setFact(contratoTO.getFact());
		contrato.setId(contratoTO.getId());
		contrato.setUact(contratoTO.getUact());
		contrato.setTerm(contratoTO.getTerm());
		
		return contrato;
	}
}