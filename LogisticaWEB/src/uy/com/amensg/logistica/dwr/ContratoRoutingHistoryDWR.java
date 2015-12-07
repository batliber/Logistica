package uy.com.amensg.logistica.dwr;

import java.util.Collection;
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
import uy.com.amensg.logistica.entities.ContratoTO;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioTO;

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
				}
				
				result.setRegistrosMuestra(registrosMuestra);
				
				result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void procesarArchivoEmpresa(String fileName, Long empresaId) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.procesarArchivoEmpresa(fileName, empresaId);;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String addAsignacionManual(Long empresaId, ContratoTO contratoTO) {
		String result = null;
		
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			result = iContratoRoutingHistoryBean.addAsignacionManual(empresaId, ContratoDWR.transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void asignarVentas(UsuarioTO usuarioTO, MetadataConsultaTO metadataConsultaTO) {
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
				
				iContratoRoutingHistoryBean.asignarVentas(
					UsuarioDWR.transform(usuarioTO),
					MetadataConsultaDWR.transform(metadataConsultaTO),
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agendar(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.agendar(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void rechazar(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.rechazar(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void posponer(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.posponer(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void asignarBackoffice(UsuarioTO usuarioTO, MetadataConsultaTO metadataConsultaTO) {
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
				
				iContratoRoutingHistoryBean.asignarBackoffice(
					UsuarioDWR.transform(usuarioTO),
					MetadataConsultaDWR.transform(metadataConsultaTO),
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void distribuir(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.distribuir(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void redistribuir(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.redistribuir(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void telelink(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.telelink(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void renovo(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.renovo(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void asignarDistribuidor(UsuarioTO usuarioTO, MetadataConsultaTO metadataConsultaTO) {
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
				
				iContratoRoutingHistoryBean.asignarDistribuidor(
					UsuarioDWR.transform(usuarioTO),
					MetadataConsultaDWR.transform(metadataConsultaTO),
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reagendar(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.reagendar(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void activar(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.activar(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void noFirma(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.noFirma(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void recoordinar(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.recoordinar(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void asignarActivador(UsuarioTO usuarioTO, MetadataConsultaTO metadataConsultaTO) {
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
				
				iContratoRoutingHistoryBean.asignarActivador(
					UsuarioDWR.transform(usuarioTO),
					MetadataConsultaDWR.transform(metadataConsultaTO),
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agendarActivacion(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.agendarActivacion(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void terminar(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.terminar(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void faltaDocumentacion(ContratoRoutingHistoryTO contratoRoutingHistoryTO) {
		try {
			IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
			
			iContratoRoutingHistoryBean.faltaDocumentacion(transform(contratoRoutingHistoryTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	public String exportarAExcel(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoRoutingHistoryBean iContratoRoutingHistoryBean = lookupBean();
				
				result = iContratoRoutingHistoryBean.exportarAExcel(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
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
			contratoRoutingHistoryTO.setUsuario(UsuarioDWR.transform(contratoRoutingHistory.getUsuario()));
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
		}
		
		if (contratoRoutingHistoryTO.getRol() != null) {
			Rol rol = new Rol();
			rol.setId(contratoRoutingHistoryTO.getRol().getId());
		}
		
		if (contratoRoutingHistoryTO.getUsuario() != null) {
			Usuario usuario = new Usuario();
			usuario.setId(contratoRoutingHistoryTO.getUsuario().getId());
		}
		
		contratoRoutingHistory.setFact(contratoRoutingHistoryTO.getFact());
		contratoRoutingHistory.setId(contratoRoutingHistoryTO.getId());
		contratoRoutingHistory.setTerm(contratoRoutingHistoryTO.getTerm());
		contratoRoutingHistory.setUact(contratoRoutingHistoryTO.getUact());
		
		return contratoRoutingHistory;
	}
}