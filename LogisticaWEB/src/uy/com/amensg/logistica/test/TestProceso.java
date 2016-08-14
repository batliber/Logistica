package uy.com.amensg.logistica.test;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import uy.com.amensg.logistica.bean.BarrioBean;
import uy.com.amensg.logistica.bean.ContratoBean;
import uy.com.amensg.logistica.bean.DepartamentoBean;
import uy.com.amensg.logistica.bean.EmpresaBean;
import uy.com.amensg.logistica.bean.IBarrioBean;
import uy.com.amensg.logistica.bean.IContratoBean;
import uy.com.amensg.logistica.bean.IDepartamentoBean;
import uy.com.amensg.logistica.bean.IEmpresaBean;
import uy.com.amensg.logistica.bean.IRolBean;
import uy.com.amensg.logistica.bean.IStockMovimientoBean;
import uy.com.amensg.logistica.bean.ITurnoBean;
import uy.com.amensg.logistica.bean.IUsuarioBean;
import uy.com.amensg.logistica.bean.RolBean;
import uy.com.amensg.logistica.bean.StockMovimientoBean;
import uy.com.amensg.logistica.bean.TurnoBean;
import uy.com.amensg.logistica.bean.UsuarioBean;
import uy.com.amensg.logistica.entities.Barrio;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.Departamento;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.Producto;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.StockMovimiento;
import uy.com.amensg.logistica.entities.Turno;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;

public class TestProceso {

	private IContratoBean iContratoBean = this.lookupBean();
	private IUsuarioBean iUsuarioBean = this.lookupUsuarioBean();
	private IRolBean iRolBean = this.lookupRolBean();
	private IEmpresaBean iEmpresaBean = this.lookupEmpresaBean();
	private IStockMovimientoBean iStockMovimientoBean = this.lookupStockMovimientoBean();
	private IDepartamentoBean iDepartamentoBean = this.lookupDepartamentoBean();
	private IBarrioBean iBarrioBean = this.lookupBarrioBean();
	private ITurnoBean iTurnoBean = this.lookupTurnoBean();
	
	public TestProceso() {
		try {
			Usuario supervisorCallCenterELARED = iUsuarioBean.getByLogin("sccELARED2");
			
			// Listar un MID en estado LLAMAR sin asignar
			MetadataConsulta metadataConsulta = new MetadataConsulta();
			
			Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
			
			MetadataCondicion metadataCondicion = new MetadataCondicion();
			metadataCondicion.setCampo("estado.nombre");
			metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
			
			Collection<String> valores = new LinkedList<String>();
			valores.add("LLAMAR");
			metadataCondicion.setValores(valores);
			
			metadataCondiciones.add(metadataCondicion);
			
			metadataCondicion = new MetadataCondicion();
			metadataCondicion.setCampo("usuario.id");
			metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NULL);
			metadataCondicion.setValores(new LinkedList<String>());
			
			metadataCondiciones.add(metadataCondicion);
			
			metadataConsulta.setMetadataCondiciones(metadataCondiciones);
			
			metadataConsulta.setMetadataOrdenaciones(new LinkedList<MetadataOrdenacion>());
			metadataConsulta.setTamanoMuestra(new Long(1));
			metadataConsulta.setTamanoSubconjunto(new Long(1));
			
			MetadataConsultaResultado metadataConsultaResultado = iContratoBean.list(metadataConsulta, supervisorCallCenterELARED.getId());
			
			Contrato contrato = null;
			for (Object registro : metadataConsultaResultado.getRegistrosMuestra()) {
				contrato = ((Contrato) registro);
			}
			
			System.out.println("MID - " + contrato.getMid() + ". Tr�mite - " + contrato.getNumeroTramite() + ". Estado - " + contrato.getEstado().getNombre());
			
			// Asignaci�n a empresa ANBEL
			Empresa empresaANBEL = iEmpresaBean.getById(new Long(3));
			Usuario supervisorCallCenterANBEL = iUsuarioBean.getByLogin("sccANBEL1");
			String resultadoAsignacion = this.asignarManualmenteAOtraEmpresa(empresaANBEL, supervisorCallCenterANBEL, contrato.getMid());
			
			System.out.println("MID - " + contrato.getMid() + " asignado a ANBEL.");
			System.out.println(resultadoAsignacion);
			
			// Asignaci�n a un Vendedor ccANBEL1
			Usuario vendedorANBEL = iUsuarioBean.getByLogin("ccANBEL1");
			
			this.asignarAVendedor(contrato.getMid(), supervisorCallCenterANBEL, vendedorANBEL);
			
			System.out.println("MID - " + contrato.getMid() + " asignado a " + vendedorANBEL.getNombre() + ".");
			
			// Rechazo por ANBEL
			this.rechazar(contrato.getMid(), vendedorANBEL);
			
			System.out.println("MID - " + contrato.getMid() + " rechazado.");
			
			// Asignaci�n a empresa RELPONT
			Empresa empresaRELPONT = iEmpresaBean.getById(new Long(2));
			Usuario supervisorCallCenterRELPONT = iUsuarioBean.getByLogin("sccRELPONT1"); 
			resultadoAsignacion = this.asignarManualmenteAOtraEmpresa(empresaRELPONT, supervisorCallCenterRELPONT, contrato.getMid());
			
			System.out.println("MID - " + contrato.getMid() + " asignado a RELPONT.");
			System.out.println(resultadoAsignacion);
			
			// Asignaci�n a un Vendedor ccRELPONT1
			Usuario vendedorRELPONT = iUsuarioBean.getByLogin("ccRELPONT1");
			this.asignarAVendedor(contrato.getMid(), supervisorCallCenterRELPONT, vendedorRELPONT);
						
			System.out.println("MID - " + contrato.getMid() + " asignado a " + vendedorRELPONT.getNombre() + ".");
						
			// Rellamar por RELPONT
			this.rellamar(contrato.getMid(), vendedorRELPONT);
			
			System.out.println("MID - " + contrato.getMid() + " marcado para rellamar.");
			
			// Asignacion a un Vendedor ccELARED2
			Usuario vendedorELARED = iUsuarioBean.getByLogin("ccELARED2");
			this.asignarAVendedor(contrato.getMid(), supervisorCallCenterELARED, vendedorELARED);
			
			System.out.println("MID - " + contrato.getMid() + " asignado a " + vendedorELARED.getNombre() + ".");
			
			// Venta por ELARED
			this.vender(contrato.getMid(), vendedorELARED);
			
			System.out.println("MID - " + contrato.getMid() + " vendido.");
			
			// Asignaci�n a un Backoffice bo1
			Usuario backoffice = iUsuarioBean.getByLogin("bo1");
			Usuario supervisorBackoffice = iUsuarioBean.getByLogin("sbo1");
			
			this.asignarABackoffice(contrato.getMid(), supervisorBackoffice, backoffice);
			
			System.out.println("MID - " + contrato.getMid() + " asignado a " + backoffice.getNombre() + ".");
			
			// Armado
			this.distribuir(contrato.getMid(), backoffice);
			
			System.out.println("MID - " + contrato.getMid() + " distribuido.");
			
			// Asignaci�n a un distribuidor d1
			Usuario distribuidor = iUsuarioBean.getByLogin("d1");
			Usuario supervisorDistribucion = iUsuarioBean.getByLogin("sd1");
			
			this.asignarADistribuidor(contrato.getMid(), supervisorDistribucion, distribuidor);
			
			System.out.println("MID - " + contrato.getMid() + " asignado a " + distribuidor.getNombre() + ".");
			
			// Recepci�n de distribuci�n
			this.activar(contrato.getMid(), supervisorDistribucion);
			
			System.out.println("MID - " + contrato.getMid() + " recibido del distribuidor.");
			
			// Asignaci�n a un activador a1
			Usuario activador = iUsuarioBean.getByLogin("a1");
			Usuario supervisorActivacion = iUsuarioBean.getByLogin("sa1");
			
			this.asignarAActivador(contrato.getMid(), supervisorActivacion, activador);
			
			System.out.println("MID - " + contrato.getMid() + " asignado a " + activador.getNombre() + ".");
			
			// Env�o a ANTEL
			this.enviarAANTEL(contrato.getMid(), activador);
			
			System.out.println("MID - " + contrato.getMid() + " enviado a ANTEL.");
			
			// Terminaci�n
			this.terminar(contrato.getMid(), activador);
			
			System.out.println("MID - " + contrato.getMid() + " terminado.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String asignarManualmenteAOtraEmpresa(Empresa empresa, Usuario supervisorCallCenter, Long mid) {
		Rol rolSupervisorCallCenter = iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")));
		
		Contrato contrato = new Contrato();
		contrato.setMid(mid);
		contrato.setEmpresa(empresa);
		contrato.setRol(rolSupervisorCallCenter);
		
		return iContratoBean.addAsignacionManual(empresa.getId(), contrato, supervisorCallCenter.getId());
	}
	
	private void asignarAVendedor(Long mid, Usuario supervisorCallCenter, Usuario vendedor) {
		MetadataConsulta metadataConsulta = new MetadataConsulta();
		
		Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
		
		MetadataCondicion metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("mid");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		Collection<String> valores = new LinkedList<String>();
		valores.add(mid.toString());
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("estado.nombre");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		valores = new LinkedList<String>();
		valores.add("LLAMAR");
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataConsulta.setMetadataCondiciones(metadataCondiciones);
		
		metadataConsulta.setMetadataOrdenaciones(new LinkedList<MetadataOrdenacion>());
		metadataConsulta.setTamanoMuestra(new Long(1));
		metadataConsulta.setTamanoSubconjunto(new Long(1));
		
		iContratoBean.asignarVentas(vendedor, metadataConsulta, supervisorCallCenter.getId());
	}
	
	private void vender(Long mid, Usuario vendedor) {
		MetadataConsulta metadataConsulta = new MetadataConsulta();
		
		Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
		
		MetadataCondicion metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("mid");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		Collection<String> valores = new LinkedList<String>();
		valores.add(mid.toString());
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataConsulta.setMetadataCondiciones(metadataCondiciones);
		
		metadataConsulta.setMetadataOrdenaciones(new LinkedList<MetadataOrdenacion>());
		metadataConsulta.setTamanoMuestra(new Long(1));
		metadataConsulta.setTamanoSubconjunto(new Long(1));
		
		MetadataConsultaResultado metadataConsultaResultado = iContratoBean.list(metadataConsulta, vendedor.getId());
		
		Contrato contrato = null;
		for (Object registro : metadataConsultaResultado.getRegistrosMuestra()) {
			contrato = ((Contrato) registro);
		}
		
		contrato.setCodigoPostal("CPOSTAL");
		contrato.setDireccionEntrega("DIRECCION ENTREGA");
		contrato.setDireccionFactura("DIRECCION FACTURA");
		contrato.setDocumento("1.234.567-8");
		contrato.setEmail("EMAIL");
		
		contrato.setFechaEntrega(GregorianCalendar.getInstance().getTime());
		contrato.setFechaNacimiento(GregorianCalendar.getInstance().getTime());
//		contrato.setNuevoPlan("NUEVO PLAN");
		contrato.setNumeroContrato(new Long(1));
		contrato.setObservaciones("VENTA");
		contrato.setPrecio(new Double(1.0));
		
		UsuarioRolEmpresa usuarioRolEmpresa = vendedor.getUsuarioRolEmpresas().toArray(new UsuarioRolEmpresa[] {})[0];
		
		Producto producto = null;
		for (StockMovimiento stockMovimiento : iStockMovimientoBean.listStockByEmpresaId(usuarioRolEmpresa.getEmpresa().getId())) {
			if (stockMovimiento.getCantidad() > 0) {
				producto = stockMovimiento.getProducto();
				break;
			}
		}
		
		contrato.setProducto(producto);
		contrato.setTelefonoContacto("1234 5678");
		
		Departamento departamento = iDepartamentoBean.list().toArray(new Departamento[] {})[0];
		Barrio barrio = iBarrioBean.listByDepartamentoId(departamento.getId()).toArray(new Barrio[] {})[0];
		Turno turno = iTurnoBean.list().toArray(new Turno[] {})[0];
		
		contrato.setBarrio(barrio);
		contrato.setZona(barrio.getZona());
		contrato.setTurno(turno);
		
		iContratoBean.agendar(contrato);
	}
	
	private void rechazar(Long mid, Usuario vendedor) {
		MetadataConsulta metadataConsulta = new MetadataConsulta();
		
		Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
		
		MetadataCondicion metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("mid");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		Collection<String> valores = new LinkedList<String>();
		valores.add(mid.toString());
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataConsulta.setMetadataCondiciones(metadataCondiciones);
		
		metadataConsulta.setMetadataOrdenaciones(new LinkedList<MetadataOrdenacion>());
		metadataConsulta.setTamanoMuestra(new Long(1));
		metadataConsulta.setTamanoSubconjunto(new Long(1));
		
		MetadataConsultaResultado metadataConsultaResultado = iContratoBean.list(metadataConsulta, vendedor.getId());
		
		Contrato contrato = null;
		for (Object registro : metadataConsultaResultado.getRegistrosMuestra()) {
			contrato = ((Contrato) registro);
		}
		
		iContratoBean.rechazar(contrato);
	}
	
	private void rellamar(Long mid, Usuario vendedor) {
		MetadataConsulta metadataConsulta = new MetadataConsulta();
		
		Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
		
		MetadataCondicion metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("mid");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		Collection<String> valores = new LinkedList<String>();
		valores.add(mid.toString());
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataConsulta.setMetadataCondiciones(metadataCondiciones);
		
		metadataConsulta.setMetadataOrdenaciones(new LinkedList<MetadataOrdenacion>());
		metadataConsulta.setTamanoMuestra(new Long(1));
		metadataConsulta.setTamanoSubconjunto(new Long(1));
		
		MetadataConsultaResultado metadataConsultaResultado = iContratoBean.list(metadataConsulta, vendedor.getId());
		
		Contrato contrato = null;
		for (Object registro : metadataConsultaResultado.getRegistrosMuestra()) {
			contrato = ((Contrato) registro);
		}
		
		iContratoBean.posponer(contrato);
	}
	
	private void asignarABackoffice(Long mid, Usuario supervisorBackoffice, Usuario backoffice) {
		MetadataConsulta metadataConsulta = new MetadataConsulta();
		
		Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
		
		MetadataCondicion metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("mid");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		Collection<String> valores = new LinkedList<String>();
		valores.add(mid.toString());
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("estado.nombre");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		valores = new LinkedList<String>();
		valores.add("VENDIDO");
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataConsulta.setMetadataCondiciones(metadataCondiciones);
		
		metadataConsulta.setMetadataOrdenaciones(new LinkedList<MetadataOrdenacion>());
		metadataConsulta.setTamanoMuestra(new Long(1));
		metadataConsulta.setTamanoSubconjunto(new Long(1));
		
		iContratoBean.asignarBackoffice(backoffice, metadataConsulta, supervisorBackoffice.getId());
	}
	
	private void distribuir(Long mid, Usuario backoffice) {
		MetadataConsulta metadataConsulta = new MetadataConsulta();
		
		Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
		
		MetadataCondicion metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("mid");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		Collection<String> valores = new LinkedList<String>();
		valores.add(mid.toString());
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataConsulta.setMetadataCondiciones(metadataCondiciones);
		
		metadataConsulta.setMetadataOrdenaciones(new LinkedList<MetadataOrdenacion>());
		metadataConsulta.setTamanoMuestra(new Long(1));
		metadataConsulta.setTamanoSubconjunto(new Long(1));
		
		MetadataConsultaResultado metadataConsultaResultado = iContratoBean.list(metadataConsulta, backoffice.getId());
		
		Contrato contrato = null;
		for (Object registro : metadataConsultaResultado.getRegistrosMuestra()) {
			contrato = ((Contrato) registro);
		}
		
		contrato.setNumeroFactura("1");
		contrato.setNumeroSerie("123456");
		
		iContratoBean.distribuir(contrato);
	}
	
	private void asignarADistribuidor(Long mid, Usuario supervisorDistribucion, Usuario distribuidor) {
		MetadataConsulta metadataConsulta = new MetadataConsulta();
		
		Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
		
		MetadataCondicion metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("mid");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		Collection<String> valores = new LinkedList<String>();
		valores.add(mid.toString());
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("estado.nombre");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		valores = new LinkedList<String>();
		valores.add("DISTRIBUIR");
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataConsulta.setMetadataCondiciones(metadataCondiciones);
		
		metadataConsulta.setMetadataOrdenaciones(new LinkedList<MetadataOrdenacion>());
		metadataConsulta.setTamanoMuestra(new Long(1));
		metadataConsulta.setTamanoSubconjunto(new Long(1));
		
		iContratoBean.asignarDistribuidor(distribuidor, metadataConsulta, supervisorDistribucion.getId());
	}
	
	private void activar(Long mid, Usuario supervisorDistribucion) {
		MetadataConsulta metadataConsulta = new MetadataConsulta();
		
		Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
		
		MetadataCondicion metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("mid");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		Collection<String> valores = new LinkedList<String>();
		valores.add(mid.toString());
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataConsulta.setMetadataCondiciones(metadataCondiciones);
		
		metadataConsulta.setMetadataOrdenaciones(new LinkedList<MetadataOrdenacion>());
		metadataConsulta.setTamanoMuestra(new Long(1));
		metadataConsulta.setTamanoSubconjunto(new Long(1));
		
		MetadataConsultaResultado metadataConsultaResultado = iContratoBean.list(metadataConsulta, supervisorDistribucion.getId());
		
		Contrato contrato = null;
		for (Object registro : metadataConsultaResultado.getRegistrosMuestra()) {
			contrato = ((Contrato) registro);
		}
		
		iContratoBean.activar(contrato);
	}
	
	private void asignarAActivador(Long mid, Usuario supervisorActivacion, Usuario activador) {
		MetadataConsulta metadataConsulta = new MetadataConsulta();
		
		Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
		
		MetadataCondicion metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("mid");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		Collection<String> valores = new LinkedList<String>();
		valores.add(mid.toString());
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("estado.nombre");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		valores = new LinkedList<String>();
		valores.add("ACTIVAR");
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataConsulta.setMetadataCondiciones(metadataCondiciones);
		
		metadataConsulta.setMetadataOrdenaciones(new LinkedList<MetadataOrdenacion>());
		metadataConsulta.setTamanoMuestra(new Long(1));
		metadataConsulta.setTamanoSubconjunto(new Long(1));
		
		iContratoBean.asignarActivador(activador, metadataConsulta, supervisorActivacion.getId());
	}
	
	private void enviarAANTEL(Long mid, Usuario activador) {
		MetadataConsulta metadataConsulta = new MetadataConsulta();
		
		Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
		
		MetadataCondicion metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("mid");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		Collection<String> valores = new LinkedList<String>();
		valores.add(mid.toString());
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataConsulta.setMetadataCondiciones(metadataCondiciones);
		
		metadataConsulta.setMetadataOrdenaciones(new LinkedList<MetadataOrdenacion>());
		metadataConsulta.setTamanoMuestra(new Long(1));
		metadataConsulta.setTamanoSubconjunto(new Long(1));
		
		MetadataConsultaResultado metadataConsultaResultado = iContratoBean.list(metadataConsulta, activador.getId());
		
		Contrato contrato = null;
		for (Object registro : metadataConsultaResultado.getRegistrosMuestra()) {
			contrato = ((Contrato) registro);
		}
		
		iContratoBean.enviarAAntel(contrato);
	}
	
	private void terminar(Long mid, Usuario activador) {
		MetadataConsulta metadataConsulta = new MetadataConsulta();
		
		Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
		
		MetadataCondicion metadataCondicion = new MetadataCondicion();
		metadataCondicion.setCampo("mid");
		metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
		
		Collection<String> valores = new LinkedList<String>();
		valores.add(mid.toString());
		metadataCondicion.setValores(valores);
		
		metadataCondiciones.add(metadataCondicion);
		
		metadataConsulta.setMetadataCondiciones(metadataCondiciones);
		
		metadataConsulta.setMetadataOrdenaciones(new LinkedList<MetadataOrdenacion>());
		metadataConsulta.setTamanoMuestra(new Long(1));
		metadataConsulta.setTamanoSubconjunto(new Long(1));
		
		MetadataConsultaResultado metadataConsultaResultado = iContratoBean.list(metadataConsulta, activador.getId());
		
		Contrato contrato = null;
		for (Object registro : metadataConsultaResultado.getRegistrosMuestra()) {
			contrato = ((Contrato) registro);
		}
		
		iContratoBean.terminar(contrato);
	}
	
	private IContratoBean lookupBean() {
		IContratoBean result = null;
		
		try {
			String EARName = "Logistica";
			String beanName = ContratoBean.class.getSimpleName();
			String remoteInterfaceName = IContratoBean.class.getName();
			String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
			
			Properties properties = new Properties();
			properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			properties.put(javax.naming.Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			properties.put(javax.naming.Context.PROVIDER_URL, "jnp://" + "localhost:1099");
	
			Context context = new InitialContext(properties);
			
			result = (IContratoBean) context.lookup(lookupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IUsuarioBean lookupUsuarioBean() {
		IUsuarioBean result = null;
		
		try {
			String EARName = "Logistica";
			String beanName = UsuarioBean.class.getSimpleName();
			String remoteInterfaceName = IUsuarioBean.class.getName();
			String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
			
			Properties properties = new Properties();
			properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			properties.put(javax.naming.Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			properties.put(javax.naming.Context.PROVIDER_URL, "jnp://" + "localhost:1099");
	
			Context context = new InitialContext(properties);
			
			result = (IUsuarioBean) context.lookup(lookupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IRolBean lookupRolBean() {
		IRolBean result = null;
		
		try {
			String EARName = "Logistica";
			String beanName = RolBean.class.getSimpleName();
			String remoteInterfaceName = IRolBean.class.getName();
			String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
	
			Properties properties = new Properties();
			properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			properties.put(javax.naming.Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			properties.put(javax.naming.Context.PROVIDER_URL, "jnp://" + "localhost:1099");
	
			Context context = new InitialContext(properties);
			
			result = (IRolBean) context.lookup(lookupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IEmpresaBean lookupEmpresaBean() {
		IEmpresaBean result = null;
		
		try {
			String EARName = "Logistica";
			String beanName = EmpresaBean.class.getSimpleName();
			String remoteInterfaceName = IEmpresaBean.class.getName();
			String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
			
			Properties properties = new Properties();
			properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			properties.put(javax.naming.Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			properties.put(javax.naming.Context.PROVIDER_URL, "jnp://" + "localhost:1099");
	
			Context context = new InitialContext(properties);
			
			result = (IEmpresaBean) context.lookup(lookupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IStockMovimientoBean lookupStockMovimientoBean() {
		IStockMovimientoBean result = null;
		
		try {
			String EARName = "Logistica";
			String beanName = StockMovimientoBean.class.getSimpleName();
			String remoteInterfaceName = IStockMovimientoBean.class.getName();
			String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;

			Properties properties = new Properties();
			properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			properties.put(javax.naming.Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			properties.put(javax.naming.Context.PROVIDER_URL, "jnp://" + "localhost:1099");
	
			Context context = new InitialContext(properties);
		
			result = (IStockMovimientoBean) context.lookup(lookupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IDepartamentoBean lookupDepartamentoBean() {
		IDepartamentoBean result = null;
		
		try {
			String EARName = "Logistica";
			String beanName = DepartamentoBean.class.getSimpleName();
			String remoteInterfaceName = IDepartamentoBean.class.getName();
			String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
			
			Properties properties = new Properties();
			properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			properties.put(javax.naming.Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			properties.put(javax.naming.Context.PROVIDER_URL, "jnp://" + "localhost:1099");
	
			Context context = new InitialContext(properties);
			
			result = (IDepartamentoBean) context.lookup(lookupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IBarrioBean lookupBarrioBean() {
		IBarrioBean result = null;
		
		try {
			String EARName = "Logistica";
			String beanName = BarrioBean.class.getSimpleName();
			String remoteInterfaceName = IBarrioBean.class.getName();
			String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
			
			Properties properties = new Properties();
			properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			properties.put(javax.naming.Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			properties.put(javax.naming.Context.PROVIDER_URL, "jnp://" + "localhost:1099");
	
			Context context = new InitialContext(properties);
			
			result = (IBarrioBean) context.lookup(lookupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ITurnoBean lookupTurnoBean() {
		ITurnoBean result = null;
		
		try {
			String EARName = "Logistica";
			String beanName = TurnoBean.class.getSimpleName();
			String remoteInterfaceName = ITurnoBean.class.getName();
			String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;

			Properties properties = new Properties();
			properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			properties.put(javax.naming.Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			properties.put(javax.naming.Context.PROVIDER_URL, "jnp://" + "localhost:1099");
	
			Context context = new InitialContext(properties);
			
			result = (ITurnoBean) context.lookup(lookupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		new TestProceso();
	}
}