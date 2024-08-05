package uy.com.amensg.logistica.web.webservices.soap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.annotation.security.RolesAllowed;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import uy.com.amensg.logistica.bean.ContratoANTELTipoOperacionModeloBean;
import uy.com.amensg.logistica.bean.ContratoBean;
import uy.com.amensg.logistica.bean.IContratoANTELTipoOperacionModeloBean;
import uy.com.amensg.logistica.bean.IContratoBean;
import uy.com.amensg.logistica.entities.Barrio;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoANTELTipoOperacionModelo;
import uy.com.amensg.logistica.entities.ContratoArchivoAdjunto;
import uy.com.amensg.logistica.entities.Departamento;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.Producto;
import uy.com.amensg.logistica.entities.TipoArchivoAdjunto;
import uy.com.amensg.logistica.entities.TipoDocumento;
import uy.com.amensg.logistica.entities.TipoProducto;
import uy.com.amensg.logistica.entities.Zona;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.web.entities.ResultadoNotificacionTareaEntregaTO;

@WebService
public class LogisticaANTELWebService {

	@WebMethod
	@RolesAllowed("Guest")
	public ResultadoNotificacionTareaEntregaTO notificarTareaEntrega(
		@WebParam(name = "idTicket") Long idTicket,
		@WebParam(name = "tipoOperacion") Long tipoOperacion,
		@WebParam(name = "tipoEntrega") String tipoEntrega,
		@WebParam(name = "fechaHoraInicioTicket") String fechaHoraInicioTicket,
		@WebParam(name = "telAsociado") String telAsociado,
		@WebParam(name = "nombreTitular") String nombreTitular,
		@WebParam(name = "apellidoTitular") String apellidoTitular,
		@WebParam(name = "direccionEntrega") String direccionEntrega,
		@WebParam(name = "direccionObservacion") String direccionObservacion,
		@WebParam(name = "coordenadaX") String coordenadaX,
		@WebParam(name = "coordenadaY") String coordenadaY,
		@WebParam(name = "tipoCoordenada") String tipoCoordenada,
		@WebParam(name = "franjaHorariaEntrega") String franjaHorariaEntrega,
		@WebParam(name = "nomContacto") String nomContacto,
		@WebParam(name = "celContacto") String celContacto,
		@WebParam(name = "observacion") String observacion,
		@WebParam(name = "urlEFactura") String urlEFactura,
		@WebParam(name = "central") String central
	) {
		ResultadoNotificacionTareaEntregaTO result = new ResultadoNotificacionTareaEntregaTO();
		
		try {
			result = 
				altaTramite(
					idTicket, 
					tipoOperacion, 
					tipoEntrega, 
					fechaHoraInicioTicket, 
					telAsociado, 
					nombreTitular, 
					apellidoTitular, 
					direccionEntrega, 
					direccionObservacion, 
					coordenadaX, 
					coordenadaY, 
					tipoCoordenada, 
					franjaHorariaEntrega,
					nomContacto, 
					celContacto, 
					observacion,
					urlEFactura,
					central
				);
		} catch (Exception e) {
			e.printStackTrace();
			
			result.setCodigoError(Long.valueOf(-1));
		}
		
		return result;
	}
	
	@WebMethod
	@RolesAllowed("antel")
	public ResultadoNotificacionTareaEntregaTO altaTramite(
		@WebParam(name = "idTicket") Long idTicket,
		@WebParam(name = "tipoOperacion") Long tipoOperacion,
		@WebParam(name = "tipoEntrega") String tipoEntrega,
		@WebParam(name = "fechaHoraInicioTicket") String fechaHoraInicioTicket,
		@WebParam(name = "telAsociado") String telAsociado,
		@WebParam(name = "nombreTitular") String nombreTitular,
		@WebParam(name = "apellidoTitular") String apellidoTitular,
		@WebParam(name = "direccionEntrega") String direccionEntrega,
		@WebParam(name = "direccionObservacion") String direccionObservacion,
		@WebParam(name = "coordenadaX") String coordenadaX,
		@WebParam(name = "coordenadaY") String coordenadaY,
		@WebParam(name = "tipoCoordenada") String tipoCoordenada,
		@WebParam(name = "franjaHorariaEntrega") String franjaHorariaEntrega,
		@WebParam(name = "nomContacto") String nomContacto,
		@WebParam(name = "celContacto") String celContacto,
		@WebParam(name = "observacion") String observacion,
		@WebParam(name = "urlEFactura") String urlEFactura,
		@WebParam(name = "central") String central
	) {
		ResultadoNotificacionTareaEntregaTO result = new ResultadoNotificacionTareaEntregaTO();
		
		try {
			IContratoBean iContratoBean = lookupContratoBean();
			
			Long empresaANTELFOId = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.ANTELFO")
				);
			
			Contrato contratoManaged = iContratoBean.getByAntelNroTrn(idTicket.toString(), empresaANTELFOId);
			
			if (contratoManaged == null) {
				Long usuarioId = Long.valueOf(Configuration.getInstance().getProperty("usuario.SupervisorVentasANTEL"));
				
				SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				Date dateFechaHoraInicioTicket = null;
				if (fechaHoraInicioTicket != null) {
					dateFechaHoraInicioTicket = parser.parse(fechaHoraInicioTicket);
				}
				
				Long mid = null;
				if (celContacto != null) {
					celContacto = celContacto.replaceFirst("^0+(?!$)", "");
					
					mid = Long.decode(celContacto);
				}
				
				Boolean direccionEntregaBis = Boolean.FALSE;
	//			Long direccionEntregaCodigoPostal = Long.decode(altaANTELTO.getDireccionEntregaCodigoPostal());
				
				Departamento direccionEntregaDepartamento = new Departamento();
				direccionEntregaDepartamento.setId(
					Long.decode(Configuration.getInstance().getProperty("departamento.Montevideo"))
				);
				
				Contrato contrato = new Contrato();
				contrato.setAntelNroTrn(idTicket.toString());
				contrato.setApellido(apellidoTitular);
				if (dateFechaHoraInicioTicket != null) {
					contrato.setFechaVenta(dateFechaHoraInicioTicket);
				}
				
	//			contrato.setDireccionEntregaApto();
				contrato.setDireccionEntregaBis(direccionEntregaBis);
	//			contrato.setDireccionEntregaBlock();
				contrato.setDireccionEntregaCalle(direccionEntrega);
	//			contrato.setDireccionEntregaCodigoPostal();
				contrato.setDireccionEntregaDepartamento(direccionEntregaDepartamento);
	//			contrato.setDireccionEntregaLocalidad();
	//			contrato.setDireccionEntregaManzana();
	//			contrato.setDireccionEntregaNumero();
				contrato.setDireccionEntregaObservaciones(direccionObservacion);
	//			contrato.setDireccionEntregaSolar();
				
	//			contrato.setDocumento();
	//			contrato.setEmail();
	//			contrato.setFechaNacimiento();
				if (mid != null) {
					contrato.setMid(mid);
				} else {
					contrato.setMid(idTicket);
				}
				contrato.setNombre(nombreTitular);
				
				String observaciones = "";
				if (tipoOperacion != null) {
					observaciones += "tipoOperacion=" + tipoOperacion + ",";
					
					Marca marcaZTE = new Marca();
					marcaZTE.setId(
						Long.decode(Configuration.getInstance().getProperty("marca.ZTE"))
					);
					
					contrato.setMarca(marcaZTE);
					
					IContratoANTELTipoOperacionModeloBean iContratoANTELTipoOperacionModeloBean = 
						lookupContratoANTELTipoOperacionModeloBeanBean();
					
					ContratoANTELTipoOperacionModelo contratoANTELTipoOperacionModelo =
						iContratoANTELTipoOperacionModeloBean.getByTipoOperacion(tipoOperacion);
					
					Modelo modelo = new Modelo();
					
					if (contratoANTELTipoOperacionModelo != null) {
						modelo = contratoANTELTipoOperacionModelo.getModelo();
					} else {
						modelo.setId(
							Long.decode(Configuration.getInstance().getProperty("modelo.SinEquipo"))
						);
					}
					contrato.setModelo(modelo);
					
					TipoProducto tipoProductoANTEL = new TipoProducto();
					tipoProductoANTEL.setId(
						Long.decode(Configuration.getInstance().getProperty("tipoProducto.ANTEL"))
					);
					
					contrato.setTipoProducto(tipoProductoANTEL);
					
					Producto productoEquipoX = new Producto();
					productoEquipoX.setId(
						Long.decode(Configuration.getInstance().getProperty("producto.SinEquipo"))
					);
					
					contrato.setProducto(productoEquipoX);
				}
				
				if (tipoEntrega != null) {
					observaciones += "tipoEntrega=" + tipoEntrega + ",";
					
					if (tipoEntrega.equals(
						Configuration.getInstance().getProperty("antel.tipoEntrega.Retiro"))
					) {
						Barrio barrioLocalMillan = new Barrio();
						barrioLocalMillan.setId(
							Long.decode(Configuration.getInstance().getProperty("barrio.LocalMillan"))
						);
						
						contrato.setBarrio(barrioLocalMillan);
						
						Zona zona0 = new Zona();
						zona0.setId(
							Long.decode(Configuration.getInstance().getProperty("zona.zona0"))
						);
						
						contrato.setZona(zona0);
						
						contrato.setDireccionEntregaCalle("Entrega en local");
					}
				}
				
				if (coordenadaX != null) {
					observaciones += "coordenadaX=" + coordenadaX + ",";
				}
				
				if (coordenadaY != null) {
					observaciones += "coordenadaY=" + coordenadaY + ",";
				}
				
				if (tipoCoordenada != null) {
					observaciones += "tipoCoordenada=" + tipoCoordenada + ",";
				}
				
				if (central != null) {
					observaciones += "central=" + central + ",";
				}
				
				if (franjaHorariaEntrega != null) {
					observaciones += "franjaHorariaEntrega=" + franjaHorariaEntrega + ",";
				}
				
				if (nomContacto != null) {
					observaciones += "nomContacto=" + nomContacto + ",";
				}
				
				if (observacion != null) {
					observaciones += "observacion=" + observacion;
				}
				
				contrato.setObservaciones(observaciones);
				
				if (urlEFactura != null) {
					Set<ContratoArchivoAdjunto> contratoArchivoAdjuntos = new HashSet<ContratoArchivoAdjunto>();
					
					TipoArchivoAdjunto tipoArchivoAdjunto = new TipoArchivoAdjunto(); 
					tipoArchivoAdjunto.setId(
						Long.decode(Configuration.getInstance().getProperty("tipoArchivoAdjunto.eFacturaANTEL"))
					);
					
					ContratoArchivoAdjunto contratoArchivoAdjunto = new ContratoArchivoAdjunto();
					contratoArchivoAdjunto.setContrato(contrato);
					contratoArchivoAdjunto.setFechaSubida(dateFechaHoraInicioTicket);
					contratoArchivoAdjunto.setTipoArchivoAdjunto(tipoArchivoAdjunto);
					contratoArchivoAdjunto.setUrl(urlEFactura);
					
					contratoArchivoAdjuntos.add(contratoArchivoAdjunto);
					
					contrato.setArchivosAdjuntos(contratoArchivoAdjuntos);
				}
				
				contrato.setTelefonoContacto(telAsociado);
				
				Empresa empresa = new Empresa();
				empresa.setId(empresaANTELFOId);
				
				contrato.setEmpresa(empresa);
				
				TipoDocumento tipoDocumento = new TipoDocumento();
				tipoDocumento.setId(
					Long.valueOf(Configuration.getInstance().getProperty("tipoDocumento.cedulaDeIdentidad"))
				);
				
				contrato.setTipoDocumento(tipoDocumento);
				
				String salida = iContratoBean.addAsignacionManualANTELExterna(
					empresaANTELFOId, 
					contrato, 
					usuarioId
				);
				
				contratoManaged = 
					iContratoBean.getById(Long.decode(salida.split(";")[0]), false);
			}
			
			result.setIdRiverGreen("RG" + contratoManaged.getNumeroTramite());
			result.setCodigoError(Long.valueOf(0));
		} catch (Exception e) {
			result.setCodigoError(Long.valueOf(-1));
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IContratoBean lookupContratoBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoBean.class.getSimpleName();
		String remoteInterfaceName = IContratoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
				
		return (IContratoBean) context.lookup(lookupName);
	}
	
	private IContratoANTELTipoOperacionModeloBean lookupContratoANTELTipoOperacionModeloBeanBean() 
		throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoANTELTipoOperacionModeloBean.class.getSimpleName();
		String remoteInterfaceName = IContratoANTELTipoOperacionModeloBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IContratoANTELTipoOperacionModeloBean) context.lookup(lookupName);
	}
}