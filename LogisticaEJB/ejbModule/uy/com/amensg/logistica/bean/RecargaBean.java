package uy.com.amensg.logistica.bean;

import java.util.Date;
import java.util.GregorianCalendar;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Recarga;
import uy.com.amensg.logistica.entities.RecargaEstado;
import uy.com.amensg.logistica.entities.RecargaMovimiento;
import uy.com.amensg.logistica.entities.RecargaPuntoVentaCota;
import uy.com.amensg.logistica.entities.RecargaTipoMovimiento;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class RecargaBean implements IRecargaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;

	@EJB
	private IBaseBean<RecargaTipoMovimiento> iBaseBean;
	
	@EJB
	private IRecargaMovimientoBean iRecargaMovimientoBean;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return 
				new QueryBuilder<Recarga>().list(
					entityManager, metadataConsulta, new Recarga()
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = 
				new QueryBuilder<Recarga>().count(
					entityManager, metadataConsulta, new Recarga()
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Recarga getById(Long id) {
		Recarga result = null;
		
		try {
			result = entityManager.find(Recarga.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Recarga recargar(Recarga recarga) {
		Recarga result = null;
		
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			RecargaEstado estado = new RecargaEstado();
			estado.setId(
				Long.decode(
					Configuration.getInstance().getProperty("estadoRecarga.Pendiente")
				)
			);
		
			recarga.setRecargaEstado(estado);
			recarga.setFechaSolicitud(hoy);
			
			recarga.setFcre(hoy);
			recarga.setUcre(recarga.getUact());
			recarga.setFact(hoy);
			
			entityManager.persist(recarga);
			
			result = recarga;
			
			Double saldo = 
				iRecargaMovimientoBean.getSaldoByPuntoVentaRecargaEmpresa(
					recarga.getPuntoVenta(),
					recarga.getRecargaEmpresa()
				);
			
			if (saldo == null || saldo < recarga.getMonto()) {
				estado = new RecargaEstado();
				estado.setId(
					Long.decode(
						Configuration.getInstance().getProperty("estadoRecarga.SaldoInsuficiente")
					)
				);
				
				recarga.setRecargaEstado(estado);
				
				recarga.setFechaRechazo(hoy);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Recarga aprobar(Recarga recarga) {
		Recarga result = null;
		
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			RecargaEstado estadoAprobado = new RecargaEstado();
			estadoAprobado.setId(
				Long.decode(
					Configuration.getInstance().getProperty("estadoRecarga.Aprobado")
				)
			);
			
			RecargaEstado estadoAprobadoConSaldoBajo = new RecargaEstado();
			estadoAprobadoConSaldoBajo.setId(
				Long.decode(
					Configuration.getInstance().getProperty("estadoRecarga.AprobadoConSaldoBajo")
				)
			);
			
			RecargaEstado estadoSaldoInsuficiente = new RecargaEstado();
			estadoSaldoInsuficiente.setId(
				Long.decode(
					Configuration.getInstance().getProperty("estadoRecarga.SaldoInsuficiente")
				)
			);
			
			Recarga recargaManaged = this.getById(recarga.getId());
			
			Double saldo = 
				iRecargaMovimientoBean.getSaldoByPuntoVentaRecargaEmpresa(
					recargaManaged.getPuntoVenta(),
					recargaManaged.getRecargaEmpresa()
				);
			
			if (saldo == null || saldo < recargaManaged.getMonto()) {
				recargaManaged.setRecargaEstado(estadoSaldoInsuficiente);
				
				recargaManaged.setFechaRechazo(hoy);
			} else {
				recargaManaged.setFechaAprobacion(hoy);
			
				RecargaPuntoVentaCota recargaPuntoVentaCota = 
					recargaManaged.getPuntoVenta().getRecargaPuntoVentaCota();
				
				if (recargaPuntoVentaCota != null
					&& (saldo - recargaManaged.getMonto()) <= recargaPuntoVentaCota.getTopeAlarmaSaldo()
				) {
					recargaManaged.setRecargaEstado(estadoAprobadoConSaldoBajo);
				} else {
					recargaManaged.setRecargaEstado(estadoAprobado);
				}	
						
				RecargaTipoMovimiento tipoMovimiento = 
					(RecargaTipoMovimiento) iBaseBean.getById(
						Long.decode(Configuration.getInstance().getProperty("recargaTipoMovimiento.Recarga")), 
						RecargaTipoMovimiento.class
					);
				
				RecargaMovimiento recargaMovimiento = new RecargaMovimiento();
				recargaMovimiento.setFecha(hoy);
				recargaMovimiento.setMonto(
					tipoMovimiento.getSigno() * recargaManaged.getMonto()
				);
				
				recargaMovimiento.setMoneda(recargaManaged.getMoneda());
				recargaMovimiento.setPuntoVenta(recargaManaged.getPuntoVenta());
				recargaMovimiento.setRecargaTipoMovimiento(tipoMovimiento);
				recargaMovimiento.setRecargaEmpresa(recargaManaged.getRecargaEmpresa());
				
				recargaMovimiento.setFact(hoy);
				recargaMovimiento.setFcre(hoy);
				recargaMovimiento.setTerm(recargaManaged.getTerm());
				recargaMovimiento.setUact(Long.valueOf(1));
				recargaMovimiento.setUcre(recargaManaged.getUact());
				
				iRecargaMovimientoBean.save(recargaMovimiento);
			}
			
			recargaManaged.setFact(hoy);
			recargaManaged.setTerm(Long.valueOf(1));
			recargaManaged.setUact(recarga.getUact());
			
			result = recargaManaged;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Recarga timeout(Recarga recarga) {
		Recarga result = null;
		
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			RecargaEstado estado = new RecargaEstado();
			estado.setId(
				Long.decode(
					Configuration.getInstance().getProperty("estadoRecarga.Timeout")
				)
			);
		
			Recarga recargaManaged = this.getById(recarga.getId());
			
			recargaManaged.setRecargaEstado(estado);
			
			recargaManaged.setFechaTimeout(hoy);
			
			recarga.setFact(hoy);
			recarga.setTerm(Long.valueOf(1));
			recarga.setUact(recarga.getUact());
			
			result = recargaManaged;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}