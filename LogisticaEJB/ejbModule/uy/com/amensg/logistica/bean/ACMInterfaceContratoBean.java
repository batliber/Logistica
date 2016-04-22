package uy.com.amensg.logistica.bean;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceListaNegra;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfaceProceso;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoRoutingHistory;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.TipoContrato;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ACMInterfaceContratoBean implements IACMInterfaceContratoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IACMInterfaceProcesoBean iACMInterfaceProcesoBean;
	
	@EJB
	private IACMInterfaceBean iACMInterfaceBean;
	
	@EJB
	private IRolBean iRolBean;
	
	@EJB
	private IContratoBean iContratoBean;
	
	@EJB
	private IEstadoBean iEstadoBean;
	
	private CriteriaQuery<ACMInterfaceContrato> criteriaQuery;
	
	public Collection<ACMInterfaceContrato> list() {
		Collection<ACMInterfaceContrato> result = new LinkedList<ACMInterfaceContrato>();
		
		try {
			Query query = entityManager.createQuery("SELECT c FROM ACMInterfaceContrato c");
			
			for (Object object : query.getResultList()) {
				result.add((ACMInterfaceContrato) object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			// Query para obtener los registros de muestra
			TypedQuery<ACMInterfaceContrato> queryMuestra = this.construirQuery(metadataConsulta);
			queryMuestra.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			for (ACMInterfaceContrato acmInterfaceContrato : queryMuestra.getResultList()) {
				registrosMuestra.add(acmInterfaceContrato);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			// Query para obtener la cantidad de registros
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			
			criteriaQueryCount.select(
				criteriaBuilder.count(criteriaQueryCount.from(ACMInterfaceContrato.class))
			);
			
			criteriaQueryCount.where(criteriaQuery.getRestriction());
			
			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
			for (Parameter<?> parameter : queryMuestra.getParameters()) {
				queryCount.setParameter(parameter.getName(), queryMuestra.getParameterValue(parameter));
			}
			
			result.setCantidadRegistros(queryCount.getSingleResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String exportarAExcel(MetadataConsulta metadataConsulta) {
		String result = null;
		
		try {
			TypedQuery<ACMInterfaceContrato> query = this.construirQuery(metadataConsulta);
			
			Collection<ACMInterfaceContrato> resultList = new LinkedList<ACMInterfaceContrato>();
			if (metadataConsulta.getTamanoSubconjunto() != null) {
				List<ACMInterfaceContrato> toOrder = query.getResultList();
				
				Collections.sort(toOrder, new Comparator<ACMInterfaceContrato>() {
					public int compare(ACMInterfaceContrato arg0, ACMInterfaceContrato arg1) {
						if (arg0 == arg1) {
							return 0;
						}
						
						Random random = new Random();
						
						return random.nextBoolean() ? 1 : -1;
					}
				});
				
				int i = 0;
				for (ACMInterfaceContrato acmInterfaceContrato : toOrder) {
					resultList.add(acmInterfaceContrato);
					
					i++;
					if (i == metadataConsulta.getTamanoSubconjunto()) {
						break;
					}
				}
			} else {
				resultList = query.getResultList();
			}
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			Date currentDate = gregorianCalendar.getTime();
			
			String fileName = 
				Configuration.getInstance().getProperty("exportacion.carpeta")
					+ gregorianCalendar.get(GregorianCalendar.YEAR)
					+ (gregorianCalendar.get(GregorianCalendar.MONTH) + 1)
					+ gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH)
					+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY)
					+ gregorianCalendar.get(GregorianCalendar.MINUTE)
					+ gregorianCalendar.get(GregorianCalendar.SECOND)
					+ ".csv";
			
			PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			for (ACMInterfaceContrato acmInterfaceContrato : resultList) {
				acmInterfaceContrato.setFechaExportacionAnterior(
					acmInterfaceContrato.getFechaExportacion()
				);
				acmInterfaceContrato.setFechaExportacion(currentDate);
				
				acmInterfaceContrato = entityManager.merge(acmInterfaceContrato);
				
				printWriter.println(
					acmInterfaceContrato.getMid()
					+ ";" + (acmInterfaceContrato.getFechaFinContrato() != null ? 
						format.format(acmInterfaceContrato.getFechaFinContrato())
						: "")
					+ ";" + (acmInterfaceContrato.getTipoContratoCodigo() != null ?
						acmInterfaceContrato.getTipoContratoCodigo() 
						: "")
					+ ";" + (acmInterfaceContrato.getTipoContratoDescripcion() != null ?
						acmInterfaceContrato.getTipoContratoDescripcion()
						: "")
					+ ";" + (acmInterfaceContrato.getDocumentoTipo() != null ?
						acmInterfaceContrato.getDocumentoTipo()
						: "")
					+ ";'" + (acmInterfaceContrato.getDocumento() != null ?
						acmInterfaceContrato.getDocumento()
						: "")
					+ ";" + (acmInterfaceContrato.getNombre() != null ?
						acmInterfaceContrato.getNombre()
						: "")
					+ ";" + (acmInterfaceContrato.getDireccion() != null ?
						acmInterfaceContrato.getDireccion()
						: "")
					+ ";" + (acmInterfaceContrato.getCodigoPostal() != null ?
						acmInterfaceContrato.getCodigoPostal()
						: "")
					+ ";" + (acmInterfaceContrato.getLocalidad() != null ?
						acmInterfaceContrato.getLocalidad()
						: "")
					+ ";" + (acmInterfaceContrato.getEquipo() != null ?
						acmInterfaceContrato.getEquipo()
						: "")
					+ ";" + (acmInterfaceContrato.getAgente() != null ?
						acmInterfaceContrato.getAgente()
						: "")
					+ ";" + (acmInterfaceContrato.getFechaExportacion() != null ?
						format.format(acmInterfaceContrato.getFechaExportacion())
						: "")
					+ ";" + format.format(acmInterfaceContrato.getFact())
					+ ";" + (acmInterfaceContrato.getNumeroCliente() != null ?
						acmInterfaceContrato.getNumeroCliente()
						: "")
					+ ";" + (acmInterfaceContrato.getNumeroContrato() != null ?
						acmInterfaceContrato.getNumeroContrato()
						: "")
				);
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Empresa empresa, String observaciones) {
		String result = null;
		
		try {
			TypedQuery<ACMInterfaceContrato> query = this.construirQuery(metadataConsulta);
			
			Collection<ACMInterfaceContrato> resultList = new LinkedList<ACMInterfaceContrato>();
			if (metadataConsulta.getTamanoSubconjunto() != null) {
				List<ACMInterfaceContrato> toOrder = query.getResultList();
				
				Collections.sort(toOrder, new Comparator<ACMInterfaceContrato>() {
					public int compare(ACMInterfaceContrato arg0, ACMInterfaceContrato arg1) {
						if (arg0 == arg1) {
							return 0;
						}
						
						Random random = new Random();
						
						return random.nextBoolean() ? 1 : -1;
					}
				});
				
				int i = 0;
				for (ACMInterfaceContrato acmInterfaceContrato : toOrder) {
					resultList.add(acmInterfaceContrato);
					
					i++;
					if (i == metadataConsulta.getTamanoSubconjunto()) {
						break;
					}
				}
			} else {
				resultList = query.getResultList();
			}
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			Date currentDate = gregorianCalendar.getTime();
			
			String fileName = 
				Configuration.getInstance().getProperty("exportacion.carpeta")
					+ gregorianCalendar.get(GregorianCalendar.YEAR)
					+ (gregorianCalendar.get(GregorianCalendar.MONTH) + 1)
					+ gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH)
					+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY)
					+ gregorianCalendar.get(GregorianCalendar.MINUTE)
					+ gregorianCalendar.get(GregorianCalendar.SECOND)
					+ ".csv";
			
			PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			Rol rolSupervisorCallCenter = 
				iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")));
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.LLAMAR"))
				);
			
			for (ACMInterfaceContrato acmInterfaceContrato : resultList) {
				acmInterfaceContrato.setFechaExportacionAnterior(
					acmInterfaceContrato.getFechaExportacion()
				);
				acmInterfaceContrato.setFechaExportacion(currentDate);
				
				acmInterfaceContrato = entityManager.merge(acmInterfaceContrato);
				
				printWriter.println(
					acmInterfaceContrato.getMid()
					+ ";" + (acmInterfaceContrato.getFechaFinContrato() != null ? 
						format.format(acmInterfaceContrato.getFechaFinContrato())
						: "")
					+ ";" + (acmInterfaceContrato.getTipoContratoCodigo() != null ?
						acmInterfaceContrato.getTipoContratoCodigo() 
						: "")
					+ ";" + (acmInterfaceContrato.getTipoContratoDescripcion() != null ?
						acmInterfaceContrato.getTipoContratoDescripcion()
						: "")
					+ ";" + (acmInterfaceContrato.getDocumentoTipo() != null ?
						acmInterfaceContrato.getDocumentoTipo()
						: "")
					+ ";'" + (acmInterfaceContrato.getDocumento() != null ?
						acmInterfaceContrato.getDocumento()
						: "")
					+ ";" + (acmInterfaceContrato.getNombre() != null ?
						acmInterfaceContrato.getNombre()
						: "")
					+ ";" + (acmInterfaceContrato.getDireccion() != null ?
						acmInterfaceContrato.getDireccion()
						: "")
					+ ";" + (acmInterfaceContrato.getCodigoPostal() != null ?
						acmInterfaceContrato.getCodigoPostal()
						: "")
					+ ";" + (acmInterfaceContrato.getLocalidad() != null ?
						acmInterfaceContrato.getLocalidad()
						: "")
					+ ";" + (acmInterfaceContrato.getEquipo() != null ?
						acmInterfaceContrato.getEquipo()
						: "")
					+ ";" + (acmInterfaceContrato.getAgente() != null ?
						acmInterfaceContrato.getAgente()
						: "")
					+ ";" + (acmInterfaceContrato.getFechaExportacion() != null ?
						format.format(acmInterfaceContrato.getFechaExportacion())
						: "")
					+ ";" + format.format(acmInterfaceContrato.getFact())
					+ ";" + (acmInterfaceContrato.getNumeroCliente() != null ?
						acmInterfaceContrato.getNumeroCliente()
						: "")
					+ ";" + (acmInterfaceContrato.getNumeroContrato() != null ?
						acmInterfaceContrato.getNumeroContrato()
						: "")
					+ ";" + (observaciones != null ?
						observaciones
						: "")
				);
				
				Contrato contrato = iContratoBean.getByMidEmpresa(acmInterfaceContrato.getMid(), empresa);
				
				if (contrato == null) {
					contrato = new Contrato();
				}
				
				contrato.setAgente(acmInterfaceContrato.getAgente() != "" ? acmInterfaceContrato.getAgente() : null);
				contrato.setCodigoPostal(acmInterfaceContrato.getCodigoPostal() != "" ? acmInterfaceContrato.getCodigoPostal() : null);
				contrato.setDireccion(acmInterfaceContrato.getDireccion() != "" ? acmInterfaceContrato.getDireccion() : null);
				contrato.setDocumento(acmInterfaceContrato.getDocumento() != "" ? acmInterfaceContrato.getDocumento() : null);
				contrato.setDocumentoTipo(acmInterfaceContrato.getDocumentoTipo());
				contrato.setEquipo(acmInterfaceContrato.getEquipo() != "" ? acmInterfaceContrato.getEquipo() : null);
				contrato.setFechaFinContrato(acmInterfaceContrato.getFechaFinContrato());
				contrato.setLocalidad(acmInterfaceContrato.getLocalidad() != "" ? acmInterfaceContrato.getLocalidad() : null);
				contrato.setMid(acmInterfaceContrato.getMid());
				contrato.setNombre(acmInterfaceContrato.getNombre() != "" ? acmInterfaceContrato.getNombre() : null);
				contrato.setNumeroCliente(acmInterfaceContrato.getNumeroCliente());
				contrato.setNumeroContrato(acmInterfaceContrato.getNumeroContrato());
				contrato.setObservaciones(observaciones != "" ? observaciones : null);
				contrato.setTipoContratoCodigo(acmInterfaceContrato.getTipoContratoCodigo() != "" ? acmInterfaceContrato.getTipoContratoCodigo() : null);
				contrato.setTipoContratoDescripcion(acmInterfaceContrato.getTipoContratoDescripcion() != "" ? acmInterfaceContrato.getTipoContratoDescripcion() : null);
				
				contrato.setEmpresa(empresa);
				contrato.setEstado(estado);
				contrato.setRol(rolSupervisorCallCenter);
				
				contrato = iContratoBean.update(contrato);
				
				ContratoRoutingHistory contratoRoutingHistory = new ContratoRoutingHistory();
				contratoRoutingHistory.setContrato(contrato);
				contratoRoutingHistory.setEmpresa(empresa);
				contratoRoutingHistory.setFecha(currentDate);
				contratoRoutingHistory.setRol(rolSupervisorCallCenter);
				
				contratoRoutingHistory.setFact(currentDate);
				contratoRoutingHistory.setTerm(new Long(1));
				contratoRoutingHistory.setUact(new Long(1));
				
				entityManager.persist(contratoRoutingHistory);
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void deshacerAsignacion(MetadataConsulta metadataConsulta) {
		try {
			TypedQuery<ACMInterfaceContrato> query = entityManager.createQuery(
				"SELECT c FROM ACMInterfaceContrato c WHERE c.fechaExportacion IS NOT NULL ORDER BY c.fechaExportacion DESC",
				ACMInterfaceContrato.class
			);
			query.setMaxResults(1);
			
			Collection<ACMInterfaceContrato> resultList = query.getResultList();
			if (resultList.size() > 0) {
				ACMInterfaceContrato acmInterfaceContrato = resultList.toArray(new ACMInterfaceContrato[]{})[0];
				
				if (acmInterfaceContrato.getFechaExportacion() != null) {
					TypedQuery<ACMInterfaceContrato> queryUltimaExportacion = entityManager.createQuery(
						"SELECT c FROM ACMInterfaceContrato c WHERE c.fechaExportacion = :fechaExportacion"
						, ACMInterfaceContrato.class
					);
					queryUltimaExportacion.setParameter("fechaExportacion", acmInterfaceContrato.getFechaExportacion());
					
					TypedQuery<ContratoRoutingHistory> queryContratoRoutingHistory = entityManager.createQuery(
						"SELECT crh FROM ContratoRoutingHistory crh"
						+ " WHERE crh.fact = :fechaExportacion", 
						ContratoRoutingHistory.class
					);
					queryContratoRoutingHistory.setParameter("fechaExportacion", acmInterfaceContrato.getFechaExportacion());
					
					Collection<ACMInterfaceContrato> acmInterfaceContratosUltimaFechaExportacion = queryUltimaExportacion.getResultList();
					Collection<ContratoRoutingHistory> contratoRoutingHistoriesUltimaFechaExportacion = queryContratoRoutingHistory.getResultList();
					
					for (ACMInterfaceContrato acmInterfaceContratoUltimaExportacion : acmInterfaceContratosUltimaFechaExportacion) {
						acmInterfaceContratoUltimaExportacion.setFechaExportacion(
							acmInterfaceContratoUltimaExportacion.getFechaExportacionAnterior()
						);
						acmInterfaceContratoUltimaExportacion.setFechaExportacionAnterior(
							acmInterfaceContrato.getFechaExportacion()
						);
							
						entityManager.merge(acmInterfaceContratoUltimaExportacion);
					}
					
					for (ContratoRoutingHistory contratoRoutingHistory : contratoRoutingHistoriesUltimaFechaExportacion) {
						Contrato contrato = contratoRoutingHistory.getContrato();
						
						entityManager.remove(contratoRoutingHistory);
						entityManager.remove(contrato);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reprocesar(MetadataConsulta metadataConsulta, String observaciones) {
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			ACMInterfaceProceso acmInterfaceProceso = new ACMInterfaceProceso();
			acmInterfaceProceso.setFechaInicio(hoy);
			acmInterfaceProceso.setObservaciones(observaciones);
			
			acmInterfaceProceso.setUact(new Long(1));
			acmInterfaceProceso.setFact(hoy);
			acmInterfaceProceso.setTerm(new Long(1));
			
			acmInterfaceProceso = iACMInterfaceProcesoBean.save(acmInterfaceProceso);
			
			TypedQuery<ACMInterfaceContrato> query = this.construirQuery(metadataConsulta);
			
			Collection<ACMInterfaceContrato> resultList = new LinkedList<ACMInterfaceContrato>();
			if (metadataConsulta.getTamanoSubconjunto() != null) {
				List<ACMInterfaceContrato> toOrder = query.getResultList();
				
				Collections.sort(toOrder, new Comparator<ACMInterfaceContrato>() {
					public int compare(ACMInterfaceContrato arg0, ACMInterfaceContrato arg1) {
						Random random = new Random();
						
						return random.nextBoolean() ? 1 : -1;
					}
				});
				
				int i = 0;
				for (ACMInterfaceContrato acmInterfaceContrato : toOrder) {
					resultList.add(acmInterfaceContrato);
					
					i++;
					if (i == metadataConsulta.getTamanoSubconjunto()) {
						break;
					}
				}
			} else {
				resultList = query.getResultList();
			}
			
			for (ACMInterfaceContrato acmInterfaceContrato : resultList) {
				ACMInterfaceMid acmInterfaceMid = new ACMInterfaceMid();
				acmInterfaceMid.setEstado(
					new Long(
						Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesarPrioritario")
					)
				);
				acmInterfaceMid.setMid(acmInterfaceContrato.getMid());
				acmInterfaceMid.setProcesoId(acmInterfaceProceso.getId());
				
				acmInterfaceMid.setUact(new Long(1));
				acmInterfaceMid.setFact(hoy);
				acmInterfaceMid.setTerm(new Long(1));
				
				entityManager.merge(acmInterfaceMid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agregarAListaNegra(MetadataConsulta metadataConsulta) {
		try {
			TypedQuery<ACMInterfaceContrato> query = this.construirQuery(metadataConsulta);
			
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			for (ACMInterfaceContrato acmInterfaceContrato : query.getResultList()) {
				ACMInterfaceMid acmInterfaceMid = new ACMInterfaceMid();
				acmInterfaceMid.setEstado(
					new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ListaNegra"))
				);
				acmInterfaceMid.setMid(acmInterfaceContrato.getMid());
				
				acmInterfaceMid.setUact(new Long(1));
				acmInterfaceMid.setFact(hoy);
				acmInterfaceMid.setTerm(new Long(1));
				
				entityManager.merge(acmInterfaceMid);
				
				ACMInterfaceListaNegra acmInterfaceListaNegra = new ACMInterfaceListaNegra();
				
				acmInterfaceListaNegra.setMid(acmInterfaceContrato.getMid());
				acmInterfaceListaNegra.setObservaciones(
					(acmInterfaceContrato.getFechaFinContrato() != null ? 
						format.format(acmInterfaceContrato.getFechaFinContrato())
						: "")
					+ ";" + acmInterfaceContrato.getTipoContratoCodigo()
					+ ";" + acmInterfaceContrato.getTipoContratoDescripcion()
					+ ";" + acmInterfaceContrato.getDocumentoTipo()
					+ ";'" + acmInterfaceContrato.getDocumento()
					+ ";" + acmInterfaceContrato.getNombre()
					+ ";" + acmInterfaceContrato.getDireccion()
					+ ";" + acmInterfaceContrato.getCodigoPostal()
					+ ";" + acmInterfaceContrato.getLocalidad()
					+ ";" + acmInterfaceContrato.getEquipo()
					+ ";" + acmInterfaceContrato.getAgente()
					+ ";" + acmInterfaceContrato.getNumeroCliente()
					+ ";" + acmInterfaceContrato.getNumeroContrato()
				);
				
				acmInterfaceListaNegra.setTerm(new Long(1));
				acmInterfaceListaNegra.setFact(hoy);
				acmInterfaceListaNegra.setUact(new Long(1));
				
				entityManager.persist(acmInterfaceListaNegra);
				
				entityManager.remove(acmInterfaceContrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Collection<TipoContrato> listTipoContratos() {
		Collection<TipoContrato> result = new LinkedList<TipoContrato>();
		
		try {
			Query query = entityManager.createQuery(
				"SELECT c.tipoContratoDescripcion, COUNT(c)"
				+ " FROM ACMInterfaceContrato c"
				+ " WHERE c.tipoContratoDescripcion IS NOT NULL"
				+ " GROUP BY c.tipoContratoDescripcion"
				+ " ORDER BY COUNT(c) DESC"
			);
			
			for (Object object : query.getResultList()) {
				Object[] fields = (Object[]) object;
				
				TipoContrato tipoContrato = new TipoContrato();
				
				// tipoContrato.setTipoContratoCodigo((String) fields[0]);
				tipoContrato.setTipoContratoDescripcion((String) fields[0]);
				
				result.add(tipoContrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<TipoContrato> listTipoContratos(MetadataConsulta metadataConsulta) {
		Collection<TipoContrato> result = new LinkedList<TipoContrato>();
		
		try {
			// Query con todos los filtros
			TypedQuery<ACMInterfaceContrato> queryACMInterfaceContrato = 
				this.construirQuery(metadataConsulta);
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			// Criteria query para los tipos de contrato
			CriteriaQuery<Object[]> criteriaQueryTiposContrato = criteriaBuilder.createQuery(Object[].class);
			
			Root<ACMInterfaceContrato> root = criteriaQueryTiposContrato.from(ACMInterfaceContrato.class);
			
			criteriaQueryTiposContrato.multiselect(
				root.get("tipoContratoDescripcion")
			).distinct(true);
			
			// Mismo where que la primera Query
			criteriaQueryTiposContrato.where(criteriaQuery.getRestriction());
			
			TypedQuery<Object[]> queryTiposContrato = entityManager.createQuery(criteriaQueryTiposContrato);
			
			for (Parameter<?> parameter : queryACMInterfaceContrato.getParameters()) {
				queryTiposContrato.setParameter(
					parameter.getName(), 
					queryACMInterfaceContrato.getParameterValue(parameter));
			}
			
			for (Object object : queryTiposContrato.getResultList()) {
				if (object != null) {
					TipoContrato tipoContrato = new TipoContrato();
					
					// tipoContrato.setTipoContratoCodigo((String) fields[0]);
					tipoContrato.setTipoContratoDescripcion((String) object);
					
					result.add(tipoContrato);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private TypedQuery<ACMInterfaceContrato> construirQuery(MetadataConsulta metadataConsulta) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		criteriaQuery = criteriaBuilder.createQuery(ACMInterfaceContrato.class);
		
		Root<ACMInterfaceContrato> root = criteriaQuery.from(ACMInterfaceContrato.class);
		
		List<Order> orders = new LinkedList<Order>();
		
		for (MetadataOrdenacion metadataOrdenacion : metadataConsulta.getMetadataOrdenaciones()) {
			if (metadataOrdenacion.getAscendente()) {
				orders.add(criteriaBuilder.asc(root.get(metadataOrdenacion.getCampo())));
			} else {
				orders.add(criteriaBuilder.desc(root.get(metadataOrdenacion.getCampo())));
			}
		}
		
		criteriaQuery
			.select(root)
			.orderBy(orders)
			.where(new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root));
		
		TypedQuery<ACMInterfaceContrato> query = entityManager.createQuery(criteriaQuery);
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		int i = 0;
		for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
			if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
				for (String valor : metadataCondicion.getValores()) {
					Path<?> campo = root.get(metadataCondicion.getCampo());
					
					try {
						if (campo.getJavaType().equals(Date.class)) {
							query.setParameter(
								"p" + i,
								format.parse(valor)
							);
						} else if (campo.getJavaType().equals(Long.class)) {
							query.setParameter(
								"p" + i,
								new Long(valor)
							);
						} else if (campo.getJavaType().equals(String.class)) {
							query.setParameter(
								"p" + i,
								valor
							);
						} else if (campo.getJavaType().equals(Double.class)) {
							query.setParameter(
								"p" + i,
								new Double(valor)
							);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					i++;
				}
				
				if (metadataCondicion.getValores().size() == 0) {
					i++;
				}
			}
		}
		
		return query;
	}
}