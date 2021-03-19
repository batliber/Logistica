package uy.com.amensg.logistica.bean;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.RecargaEstadoAcreditacionSaldo;
import uy.com.amensg.logistica.entities.RecargaMovimiento;
import uy.com.amensg.logistica.entities.RecargaSolicitudAcreditacionSaldo;
import uy.com.amensg.logistica.entities.RecargaSolicitudAcreditacionSaldoArchivoAdjunto;
import uy.com.amensg.logistica.entities.RecargaTipoMovimiento;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class RecargaSolicitudAcreditacionSaldoBean implements IRecargaSolicitudAcreditacionSaldoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IBaseBean<RecargaTipoMovimiento> iBaseBean;
	
	@EJB
	private IRecargaMovimientoBean iRecargaMovimientoBean;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return 
				new QueryBuilder<RecargaSolicitudAcreditacionSaldo>().list(
					entityManager, metadataConsulta, new RecargaSolicitudAcreditacionSaldo()
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
				new QueryBuilder<RecargaSolicitudAcreditacionSaldo>().count(
					entityManager, metadataConsulta, new RecargaSolicitudAcreditacionSaldo()
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RecargaSolicitudAcreditacionSaldo getById(Long id, boolean initializeCollections) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			result = entityManager.find(RecargaSolicitudAcreditacionSaldo.class, id);
			
			TypedQuery<RecargaSolicitudAcreditacionSaldoArchivoAdjunto> queryArchivosAdjuntos =
				entityManager.createQuery(
					"SELECT raa"
					+ " FROM RecargaSolicitudAcreditacionSaldoArchivoAdjunto raa"
					+ " WHERE raa.recargaSolicitudAcreditacionSaldo.id = :recargaSolicitudAcreditacionSaldoId",
					RecargaSolicitudAcreditacionSaldoArchivoAdjunto.class
				);
			queryArchivosAdjuntos.setParameter("recargaSolicitudAcreditacionSaldoId", id);
			
			if (initializeCollections) {
				entityManager.detach(result);
				
				Set<RecargaSolicitudAcreditacionSaldoArchivoAdjunto> recargaSolicitudAcreditacionSaldoArchivoAdjuntos = 
					new HashSet<RecargaSolicitudAcreditacionSaldoArchivoAdjunto>();
				
				for (RecargaSolicitudAcreditacionSaldoArchivoAdjunto recargaSolicitudAcreditacionSaldoArchivoAdjunto : 
					queryArchivosAdjuntos.getResultList()) {
					recargaSolicitudAcreditacionSaldoArchivoAdjuntos.add(
						recargaSolicitudAcreditacionSaldoArchivoAdjunto
					);
				}
				
				result.setArchivosAdjuntos(recargaSolicitudAcreditacionSaldoArchivoAdjuntos);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RecargaSolicitudAcreditacionSaldo save(RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			recargaSolicitudAcreditacionSaldo.setFcre(recargaSolicitudAcreditacionSaldo.getFact());
			recargaSolicitudAcreditacionSaldo.setUcre(recargaSolicitudAcreditacionSaldo.getUact());
			
			entityManager.persist(recargaSolicitudAcreditacionSaldo);
			
			result = recargaSolicitudAcreditacionSaldo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void update(RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo) {
		try {
			RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldoManaged = 
				entityManager.find(RecargaSolicitudAcreditacionSaldo.class, recargaSolicitudAcreditacionSaldo.getId());
			
			recargaSolicitudAcreditacionSaldoManaged.setMoneda(recargaSolicitudAcreditacionSaldo.getMoneda());
			recargaSolicitudAcreditacionSaldoManaged.setMonto(recargaSolicitudAcreditacionSaldo.getMonto());
			recargaSolicitudAcreditacionSaldoManaged.setObservaciones(recargaSolicitudAcreditacionSaldo.getObservaciones());
			recargaSolicitudAcreditacionSaldoManaged.setPuntoVenta(recargaSolicitudAcreditacionSaldo.getPuntoVenta());
			recargaSolicitudAcreditacionSaldoManaged.setRecargaEstadoAcreditacionSaldo(
				recargaSolicitudAcreditacionSaldo.getRecargaEstadoAcreditacionSaldo()
			);
			recargaSolicitudAcreditacionSaldoManaged.setRecargaEmpresa(
				recargaSolicitudAcreditacionSaldo.getRecargaEmpresa()
			);
			recargaSolicitudAcreditacionSaldoManaged.setRecargaFormaPago(
				recargaSolicitudAcreditacionSaldo.getRecargaFormaPago()
			);
			recargaSolicitudAcreditacionSaldoManaged.setRecargaBanco(
				recargaSolicitudAcreditacionSaldo.getRecargaBanco()
			);
			
			recargaSolicitudAcreditacionSaldoManaged.setArchivosAdjuntos(
				recargaSolicitudAcreditacionSaldo.getArchivosAdjuntos()
			);
			
			recargaSolicitudAcreditacionSaldoManaged.setFact(recargaSolicitudAcreditacionSaldo.getFact());
			recargaSolicitudAcreditacionSaldoManaged.setTerm(recargaSolicitudAcreditacionSaldo.getTerm());
			recargaSolicitudAcreditacionSaldoManaged.setUact(recargaSolicitudAcreditacionSaldo.getUact());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RecargaSolicitudAcreditacionSaldo solicitar(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo
	) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			RecargaEstadoAcreditacionSaldo estado = new RecargaEstadoAcreditacionSaldo();
			estado.setId(
				Long.decode(
					Configuration.getInstance().getProperty("estadoRecargaSolicitudAcreditacionSaldo.Pendiente")
				)
			);
		
			recargaSolicitudAcreditacionSaldo.setRecargaEstadoAcreditacionSaldo(estado);
			recargaSolicitudAcreditacionSaldo.setFechaSolicitud(hoy);
			
			recargaSolicitudAcreditacionSaldo.setFcre(hoy);
			recargaSolicitudAcreditacionSaldo.setUcre(recargaSolicitudAcreditacionSaldo.getUact());
			recargaSolicitudAcreditacionSaldo.setFact(hoy);
			
			entityManager.persist(recargaSolicitudAcreditacionSaldo);
			
			result = recargaSolicitudAcreditacionSaldo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RecargaSolicitudAcreditacionSaldo preaprobar(
			RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo
	) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			RecargaEstadoAcreditacionSaldo estado = new RecargaEstadoAcreditacionSaldo();
			estado.setId(
				Long.decode(
					Configuration.getInstance().getProperty("estadoRecargaSolicitudAcreditacionSaldo.Preaprobado")
				)
			);
			
			RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldoManaged =
				entityManager.find(RecargaSolicitudAcreditacionSaldo.class, recargaSolicitudAcreditacionSaldo.getId());
			
			recargaSolicitudAcreditacionSaldoManaged.setRecargaEstadoAcreditacionSaldo(estado);
			
			recargaSolicitudAcreditacionSaldoManaged.setFechaPreaprobacion(hoy);
			
			recargaSolicitudAcreditacionSaldoManaged.setFact(hoy);
			recargaSolicitudAcreditacionSaldoManaged.setTerm(Long.valueOf(1));
			recargaSolicitudAcreditacionSaldoManaged.setUact(recargaSolicitudAcreditacionSaldo.getUact());
			
			result = recargaSolicitudAcreditacionSaldoManaged;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return result;
	}
	
	public RecargaSolicitudAcreditacionSaldo aprobar(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo
	) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			RecargaEstadoAcreditacionSaldo estado = new RecargaEstadoAcreditacionSaldo();
			estado.setId(
				Long.decode(
					Configuration.getInstance().getProperty("estadoRecargaSolicitudAcreditacionSaldo.Aprobado")
				)
			);
			
			RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldoManaged =
				entityManager.find(RecargaSolicitudAcreditacionSaldo.class, recargaSolicitudAcreditacionSaldo.getId());
			
			recargaSolicitudAcreditacionSaldoManaged.setRecargaEstadoAcreditacionSaldo(estado);
			
			recargaSolicitudAcreditacionSaldoManaged.setFechaAcreditacion(hoy);
			
			recargaSolicitudAcreditacionSaldoManaged.setFact(hoy);
			recargaSolicitudAcreditacionSaldoManaged.setTerm(Long.valueOf(1));
			recargaSolicitudAcreditacionSaldoManaged.setUact(recargaSolicitudAcreditacionSaldo.getUact());
			
			result = recargaSolicitudAcreditacionSaldoManaged;
			
			RecargaTipoMovimiento tipoMovimiento = 
				(RecargaTipoMovimiento) iBaseBean.getById(
					Long.decode(Configuration.getInstance().getProperty("recargaTipoMovimiento.AltaSaldoEmpresa")), 
					RecargaTipoMovimiento.class
				);
			
			RecargaMovimiento recargaMovimiento = new RecargaMovimiento();
			recargaMovimiento.setFecha(hoy);
			recargaMovimiento.setMonto(
				tipoMovimiento.getSigno() * result.getMonto()
			);
			
			recargaMovimiento.setMoneda(result.getMoneda());
			recargaMovimiento.setPuntoVenta(result.getPuntoVenta());
			recargaMovimiento.setRecargaTipoMovimiento(tipoMovimiento);
			recargaMovimiento.setRecargaEmpresa(result.getRecargaEmpresa());
			
			recargaMovimiento.setFact(hoy);
			recargaMovimiento.setFcre(hoy);
			recargaMovimiento.setTerm(result.getTerm());
			recargaMovimiento.setUact(Long.valueOf(1));
			recargaMovimiento.setUcre(result.getUact());
			
			iRecargaMovimientoBean.save(recargaMovimiento);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RecargaSolicitudAcreditacionSaldo denegar(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo
	) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			RecargaEstadoAcreditacionSaldo estado = new RecargaEstadoAcreditacionSaldo();
			estado.setId(
				Long.decode(
					Configuration.getInstance().getProperty("estadoRecargaSolicitudAcreditacionSaldo.Denegado")
				)
			);
			
			RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldoManaged =
				entityManager.find(RecargaSolicitudAcreditacionSaldo.class, recargaSolicitudAcreditacionSaldo.getId());
			
			recargaSolicitudAcreditacionSaldoManaged.setRecargaEstadoAcreditacionSaldo(estado);
			
			recargaSolicitudAcreditacionSaldoManaged.setFechaDenegacion(hoy);
			
			recargaSolicitudAcreditacionSaldoManaged.setFact(hoy);
			recargaSolicitudAcreditacionSaldoManaged.setTerm(Long.valueOf(1));
			recargaSolicitudAcreditacionSaldoManaged.setUact(recargaSolicitudAcreditacionSaldo.getUact());
			
			result = recargaSolicitudAcreditacionSaldoManaged;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return result;
	}
	
	public RecargaSolicitudAcreditacionSaldo credito(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo
	) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			RecargaEstadoAcreditacionSaldo estado = new RecargaEstadoAcreditacionSaldo();
			estado.setId(
				Long.decode(
					Configuration.getInstance().getProperty("estadoRecargaSolicitudAcreditacionSaldo.Credito")
				)
			);
			
			RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldoManaged =
				entityManager.find(RecargaSolicitudAcreditacionSaldo.class, recargaSolicitudAcreditacionSaldo.getId());
			
			recargaSolicitudAcreditacionSaldoManaged.setRecargaEstadoAcreditacionSaldo(estado);
			
			recargaSolicitudAcreditacionSaldoManaged.setFechaCredito(hoy);
			
			recargaSolicitudAcreditacionSaldoManaged.setFact(hoy);
			recargaSolicitudAcreditacionSaldoManaged.setTerm(Long.valueOf(1));
			recargaSolicitudAcreditacionSaldoManaged.setUact(recargaSolicitudAcreditacionSaldo.getUact());
			
			result = recargaSolicitudAcreditacionSaldoManaged;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return result;
	}
	
	public RecargaSolicitudAcreditacionSaldo eliminar(
			RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo
	) {
		RecargaSolicitudAcreditacionSaldo result = null;
		
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			RecargaEstadoAcreditacionSaldo estado = new RecargaEstadoAcreditacionSaldo();
			estado.setId(
				Long.decode(
					Configuration.getInstance().getProperty("estadoRecargaSolicitudAcreditacionSaldo.Eliminado")
				)
			);
			
			RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldoManaged =
				entityManager.find(RecargaSolicitudAcreditacionSaldo.class, recargaSolicitudAcreditacionSaldo.getId());
			
			recargaSolicitudAcreditacionSaldoManaged.setRecargaEstadoAcreditacionSaldo(estado);
			
			recargaSolicitudAcreditacionSaldoManaged.setFechaEliminacion(hoy);
			
			recargaSolicitudAcreditacionSaldoManaged.setFact(hoy);
			recargaSolicitudAcreditacionSaldoManaged.setTerm(Long.valueOf(1));
			recargaSolicitudAcreditacionSaldoManaged.setUact(recargaSolicitudAcreditacionSaldo.getUact());
			
			result = recargaSolicitudAcreditacionSaldoManaged;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return result;
	}
}