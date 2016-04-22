package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ContratoRoutingHistoryBean;
import uy.com.amensg.logistica.bean.IContratoRoutingHistoryBean;
import uy.com.amensg.logistica.entities.ContratoRoutingHistory;
import uy.com.amensg.logistica.entities.ContratoRoutingHistoryTO;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Usuario;

@RemoteProxy
public class ContratoRoutingHistoryDWR {

	private IContratoRoutingHistoryBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = ContratoRoutingHistoryBean.class.getSimpleName();
		String remoteInterfaceName = IContratoRoutingHistoryBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IContratoRoutingHistoryBean) context.lookup(lookupName);
	}

	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iContratoRoutingHistoryBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object contratoRoutingHistory : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(transform((ContratoRoutingHistory) contratoRoutingHistory));
//				for (Object contrato : metadataConsultaResultado.getRegistrosMuestra()) {
//					registrosMuestra.add(ContratoDWR.transform((Contrato) contrato));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
				
				result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<ContratoRoutingHistoryTO> listByContratoId(Long contratoId) {
		Collection<ContratoRoutingHistoryTO> result = new LinkedList<ContratoRoutingHistoryTO>();

		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			for (ContratoRoutingHistory contratoRoutingHistory : iContratoRoutingHistoryBean.listByContratoId(contratoId)) {
				result.add(transform(contratoRoutingHistory));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ContratoRoutingHistoryTO transform(ContratoRoutingHistory contratoRoutingHistory) {
		ContratoRoutingHistoryTO contratoRoutingHistoryTO = new ContratoRoutingHistoryTO();
		
		contratoRoutingHistoryTO.setFecha(contratoRoutingHistory.getFecha());
		
		contratoRoutingHistoryTO.setContrato(ContratoDWR.transform(contratoRoutingHistory.getContrato()));
		
		if (contratoRoutingHistory.getEmpresa() != null) {
			contratoRoutingHistoryTO.setEmpresa(EmpresaDWR.transform(contratoRoutingHistory.getEmpresa()));
		}
		
		if (contratoRoutingHistory.getRol() != null) {
			contratoRoutingHistoryTO.setRol(RolDWR.transform(contratoRoutingHistory.getRol()));
		}
		
		if (contratoRoutingHistory.getUsuario() != null) {
			contratoRoutingHistoryTO.setUsuario(UsuarioDWR.transform(contratoRoutingHistory.getUsuario(), false));
		}
		
		if (contratoRoutingHistory.getEstado() != null) {
			contratoRoutingHistoryTO.setEstado(EstadoDWR.transform(contratoRoutingHistory.getEstado()));
		}
		
		if (contratoRoutingHistory.getUsuarioAct() != null) {
			contratoRoutingHistoryTO.setUsuarioAct(UsuarioDWR.transform(contratoRoutingHistory.getUsuarioAct(), false));
		}
		
		contratoRoutingHistoryTO.setFact(contratoRoutingHistory.getFact());
		contratoRoutingHistoryTO.setId(contratoRoutingHistory.getId());
		contratoRoutingHistoryTO.setTerm(contratoRoutingHistory.getTerm());
		contratoRoutingHistoryTO.setUact(contratoRoutingHistory.getUact());
		
		return contratoRoutingHistoryTO;
	}

	public static ContratoRoutingHistory transform(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		ContratoRoutingHistory contratoRoutingHistory = new ContratoRoutingHistory();
		
		contratoRoutingHistory.setFecha(contratoRoutingHistoryTO.getFecha());
		
		contratoRoutingHistory.setContrato(ContratoDWR.transform(contratoRoutingHistoryTO.getContrato()));
		
		if (contratoRoutingHistoryTO.getEmpresa() != null) {
			Empresa empresa = new Empresa();
			empresa.setId(contratoRoutingHistoryTO.getEmpresa().getId());
			
			contratoRoutingHistory.setEmpresa(empresa);
		}
		
		if (contratoRoutingHistoryTO.getRol() != null) {
			Rol rol = new Rol();
			rol.setId(contratoRoutingHistoryTO.getRol().getId());
			
			contratoRoutingHistory.setRol(rol);
		}
		
		if (contratoRoutingHistoryTO.getUsuario() != null) {
			Usuario usuario = new Usuario();
			usuario.setId(contratoRoutingHistoryTO.getUsuario().getId());
			
			contratoRoutingHistory.setUsuario(usuario);
		}
		
		if (contratoRoutingHistoryTO.getEstado() != null) {
			Estado estado = new Estado();
			estado.setId(contratoRoutingHistoryTO.getEstado().getId());
			
			contratoRoutingHistory.setEstado(estado);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		contratoRoutingHistory.setFact(date);
		contratoRoutingHistory.setId(contratoRoutingHistoryTO.getId());
		contratoRoutingHistory.setTerm(contratoRoutingHistoryTO.getTerm());
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		contratoRoutingHistory.setUact(usuarioId);
		
		return contratoRoutingHistory;
	}
}