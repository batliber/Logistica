package uy.com.amensg.logistica.bean;

import java.io.BufferedReader;
import java.io.FileReader;
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
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoRoutingHistory;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ContratoRoutingHistoryBean implements IContratoRoutingHistoryBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	@EJB
	private IRolBean iRolBean;
	
	@EJB
	private IContratoBean iContratoBean;
	
	@EJB
	private IEstadoBean iEstadoBean;
	
	@EJB
	private IEmpresaBean iEmpresaBean;
	
	/**
	 * Lista los ContratoRoutingHistory que cumplen los criterios encapsulados en metadataConsulta y que puede ver el usuarioId.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param usuarioId ID del usuario que consulta.
	 */
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			// Obtener el usuario para el cual se consulta
			Usuario usuario = iUsuarioBean.getById(usuarioId);
			
			Collection<Rol> roles = new LinkedList<Rol>();
			Collection<Empresa> empresas = new LinkedList<Empresa>();
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
				roles.addAll(usuarioRolEmpresa.getRol().getSubordinados());
				empresas.add(usuarioRolEmpresa.getEmpresa());
			}
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			// Consulta para la obtención de los ids de las últimas asignaciones por cada (contrato, empresa, usuario)
			CriteriaQuery<MaxContratoRoutingHistoryResult> criteriaQueryMax = 
				criteriaBuilder.createQuery(MaxContratoRoutingHistoryResult.class);
			
			Root<ContratoRoutingHistory> rootMax = criteriaQueryMax.from(ContratoRoutingHistory.class);
			
			// Procesar las ordenaciones para los registros de la muestra
			List<Order> orders = new LinkedList<Order>();
			for (MetadataOrdenacion metadataOrdenacion : metadataConsulta.getMetadataOrdenaciones()) {
				String[] campos = metadataOrdenacion.getCampo().split("\\.");
				
				Join<?, ?> join = null;
				for (int j=0; j<campos.length - 1; j++) {
					if (join != null) {
						join = join.join(campos[j], JoinType.LEFT);
					} else {
						join = rootMax.join(campos[j], JoinType.LEFT);
					}
				}
				
				if (metadataOrdenacion.getAscendente()) {
					orders.add(
						criteriaBuilder.asc(
							join != null ? join.get(campos[campos.length - 1]) : rootMax.get(campos[campos.length - 1])
						)
					);
				} else {
					orders.add(
						criteriaBuilder.desc(
							join != null ? join.get(campos[campos.length - 1]) : rootMax.get(campos[campos.length - 1])
						)
					);
				}
			}
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, rootMax);
			
			Subquery<UsuarioRolEmpresa> subquery = 
				criteriaQueryMax.subquery(UsuarioRolEmpresa.class);
			
			Root<UsuarioRolEmpresa> subroot = subquery.from(UsuarioRolEmpresa.class);
			Join<Rol, Rol> subordinados = subroot.join("rol", JoinType.INNER).join("subordinados", JoinType.INNER);
			
			subquery
				.select(subroot)
				.where(
					criteriaBuilder.and(
						criteriaBuilder.equal(subroot.get("usuario"), usuario),
						criteriaBuilder.equal(subroot.get("empresa"), rootMax.get("contrato").get("empresa")),
						rootMax.get("contrato").get("rol").in(subordinados)
					)
				);
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.or(
					criteriaBuilder.equal(
						rootMax.get("contrato").get("usuario"), 
						criteriaBuilder.parameter(Usuario.class, "usuario")),
					criteriaBuilder.exists(
						subquery
					)
				)
			);
			
			criteriaQueryMax
				.multiselect(
					criteriaBuilder.greatest(rootMax.get("id").as(Long.class)),
					rootMax.get("empresa").get("id"),
					rootMax.get("contrato").get("id")
				)
				.where(where)
				.groupBy(
					rootMax.get("empresa").get("id"),
					rootMax.get("contrato").get("id")
				);
			
			TypedQuery<MaxContratoRoutingHistoryResult> queryMax = entityManager.createQuery(criteriaQueryMax);
			
			queryMax.setParameter("usuario", usuario);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los parámetros según las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<ContratoRoutingHistory> field = rootMax;
						for (String path : campos) {
							field = field.get(path);
						}
						
						try {
							if (field.getJavaType().equals(Date.class)) {
								queryMax.setParameter(
									"p" + i,
									format.parse(valor)
								);
							} else if (field.getJavaType().equals(Long.class)) {
								queryMax.setParameter(
									"p" + i,
									new Long(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								queryMax.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								queryMax.setParameter(
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
			
			// Acotar al tamaño de la muestra
			queryMax.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Long> contratoRoutingHistoryIds = new LinkedList<Long>();
			contratoRoutingHistoryIds.add(new Long(-1));
			for (MaxContratoRoutingHistoryResult maxContratoRoutingHistoryResult : queryMax.getResultList()) {
				contratoRoutingHistoryIds.add(maxContratoRoutingHistoryResult.getId());
			}
			
			// Consulta para la obtención de los registros de la muestra (por id)
			CriteriaQuery<ContratoRoutingHistory> criteriaQueryFetch = criteriaBuilder.createQuery(ContratoRoutingHistory.class);
			
			Root<ContratoRoutingHistory> rootFetch = criteriaQueryFetch.from(ContratoRoutingHistory.class);
			
			orders = new LinkedList<Order>();
			for (MetadataOrdenacion metadataOrdenacion : metadataConsulta.getMetadataOrdenaciones()) {
				String[] campos = metadataOrdenacion.getCampo().split("\\.");
				
				Join<?, ?> join = null;
				for (int j=0; j<campos.length - 1; j++) {
					if (join != null) {
						join = join.join(campos[j], JoinType.LEFT);
					} else {
						join = rootFetch.join(campos[j], JoinType.LEFT);
					}
				}
				
				if (metadataOrdenacion.getAscendente()) {
					orders.add(
						criteriaBuilder.asc(
							join != null ? join.get(campos[campos.length - 1]) : rootFetch.get(campos[campos.length - 1])
						)
					);
				} else {
					orders.add(
						criteriaBuilder.desc(
							join != null ? join.get(campos[campos.length - 1]) : rootFetch.get(campos[campos.length - 1])
						)
					);
				}
			}
			
			criteriaQueryFetch
				.where(rootFetch.get("id").in(contratoRoutingHistoryIds))
				.orderBy(orders);
			
			TypedQuery<ContratoRoutingHistory> queryFetch = entityManager.createQuery(criteriaQueryFetch);
	
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			for (ContratoRoutingHistory contratoRoutingHistory : queryFetch.getResultList()) {
				registrosMuestra.add(contratoRoutingHistory);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			// Cantidad de registros de la muestra
			queryMax.setMaxResults(Integer.MAX_VALUE);
			
			result.setCantidadRegistros(new Long(queryMax.getResultList().size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Procesa el archivo .csv de nombre fileName para la Empresa empresaId.
	 * Retorna un String que informa cuántos MIDs se importarán, cuántos se sobreescribirán y cuántos se omitirán.
	 * 
	 * @param fileName El nombre del archivo a Importar.
	 * @param emmpresaId ID de la empresa a la cual asignar los contratos importados.
	 */
	public String preprocesarArchivoEmpresa(String fileName, Long empresaId) {
		String result = "";
		
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Estado estadoVendido = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.VENDIDO"))
				);
			
			Collection<Long> mids = new LinkedList<Long>();
			
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] fields = line.split(";");
				
				Long mid = new Long (fields[0].trim());
				
				mids.add(mid);
			}
			
			TypedQuery<Long> query = 
				entityManager.createQuery(
					"SELECT COUNT(c) AS cantidad"
					+ " FROM Contrato c"
					+ " WHERE c.estado.id >= :estadoVendidoId"
					+ " AND c.mid IN :mids",
					Long.class
				);
			query.setParameter("estadoVendidoId", estadoVendido.getId());
			query.setParameter("mids", mids);
			
			Long noSobreescribir = new Long(0);
			
			List<Long> resultList = query.getResultList();
			if (resultList.size() > 0) {
				noSobreescribir = resultList.get(0);
			}
			
			query = 
				entityManager.createQuery(
					"SELECT COUNT(c) AS cantidad"
					+ " FROM Contrato c"
					+ " WHERE c.empresa.id = :empresaId"
					+ " AND c.estado.id < :estadoVendidoId"
					+ " AND c.mid IN :mids",
					Long.class
				);
			query.setParameter("empresaId", empresaId);
			query.setParameter("estadoVendidoId", estadoVendido.getId());
			query.setParameter("mids", mids);
			
			Long sobreescribir = new Long(0);
			
			resultList = query.getResultList();
			if (resultList.size() > 0) {
				sobreescribir = resultList.get(0);
			}
			
			Long nuevos = Math.max(0, mids.size() - sobreescribir - noSobreescribir);
			sobreescribir = Math.max(0, mids.size() - nuevos - noSobreescribir);
			
			return 
				"Se importarán " + nuevos + " MIDs nuevos.|"
				+ "Se sobreescribirán " + sobreescribir + " MIDs.|"
				+ "Se omitirán " + noSobreescribir + " MIDs.";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Importa el archivo .csv de nombre fileName para la Empresa empresaId.
	 * Genera un Contrato (con estado "LLAMAR") por cada línea del archivo y los asigna a la "bandeja" de Supervisores de Call-Center de la Empresa empresaId.
	 *  
	 * @param fileName El nombre del archivo a Importar.
	 * @param emmpresaId ID de la empresa a la cual asignar los contratos importados.
	 */
	public void procesarArchivoEmpresa(String fileName, Long empresaId) {
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			Date hoy = gregorianCalendar.getTime();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			Rol rolSupervisorCallCenter = 
				iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")));
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.LLAMAR"))
				);
			
			Long estadoVendidoId = 
				new Long(Configuration.getInstance().getProperty("estado.VENDIDO"));
			
			Empresa empresa = 
				iEmpresaBean.getById(empresaId);
			
			TypedQuery<Contrato> query = 
				entityManager.createQuery(
					"SELECT c"
					+ " FROM Contrato c"
					+ " WHERE c.estado.id >= :estadoVendidoId"
					+ " AND c.mid = :mid",
					Contrato.class
				);
			query.setParameter("estadoVendidoId", estadoVendidoId);
			
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] fields = line.split(";");
				
				Long mid = new Long (fields[0].trim());
				Date fechaFinContrato = 
					(fields[1] != null && !fields[1].equals("")) ? simpleDateFormat.parse(fields[1].trim()) : null;
				String tipoContratoCodigo = 
					fields[2] != null ? fields[2].trim() : null;
				String tipoContratoDescripcion = 
					fields[3] != null ? fields[3].trim() : null;
				Long documentoTipo = 
					(fields[4] != null && !fields[4].equals("")) ? new Long(fields[4].trim()) : null;
				String documento = 
					fields[5] != null ? fields[5].trim() : null;
				String nombre = 
					fields[6] != null ? fields[6].trim() : null;
				String direccion = 
					fields[7] != null ? fields[7].trim() : null;
				String codigoPostal = 
					fields[8] != null ? fields[8].trim() : null;
				String localidad = 
					fields[9] != null ? fields[9].trim() : null;
				String equipo = 
					fields[10] != null ? fields[10].trim() : null;
				String agente = 
					fields[11] != null ? fields[11].trim() : null;
//				Date fechaExportacion = simpleDateFormat.parse(fields[12].trim());
//				Date fechaAsignacion = simpleDateFormat.parse(fields[13].trim());
				Long numeroCliente = 
					(fields[14] != null && !fields[14].equals("")) ? new Long(fields[14].trim()) : null;
				Long numeroContrato = 
					(fields[15] != null && !fields[15].equals("")) ? new Long(fields[15].trim()) : null;
				String observaciones =
					(fields[16] != null && !fields[16].equals("")) ? fields[16].trim() : null;
				
				// Busco si el mid está en estado VENDIDO o superior
				query.setParameter("mid", new Long(mid));
				
				List<Contrato> resultList = query.getResultList();
				if (resultList.size() == 0) {
					// Busco si el contrato ya existe para la empresa.
					Contrato contrato = iContratoBean.getByMidEmpresa(new Long(mid), empresa);
					
					if (contrato == null) {
						contrato = new Contrato();
					}
					
					contrato.setAgente(agente);
					contrato.setCodigoPostal(codigoPostal);
					contrato.setDireccion(direccion);
					contrato.setDocumento(documento);
					contrato.setDocumentoTipo(documentoTipo);
					contrato.setEquipo(equipo);
					contrato.setFechaFinContrato(fechaFinContrato);
					contrato.setLocalidad(localidad);
					contrato.setMid(new Long(mid));
					contrato.setNombre(nombre);
					contrato.setNumeroCliente(numeroCliente);
					contrato.setNumeroContrato(numeroContrato);
					contrato.setObservaciones(observaciones);
					contrato.setTipoContratoCodigo(tipoContratoCodigo);
					contrato.setTipoContratoDescripcion(tipoContratoDescripcion);
					
					contrato.setEmpresa(empresa);
					contrato.setEstado(estado);
					contrato.setRol(rolSupervisorCallCenter);
					
					contrato.setFact(hoy);
					contrato.setTerm(new Long(1));
					contrato.setUact(new Long(1));
					
					contrato = entityManager.merge(contrato);
					
					ContratoRoutingHistory contratoRoutingHistory = new ContratoRoutingHistory();
					contratoRoutingHistory.setContrato(contrato);
					contratoRoutingHistory.setEmpresa(empresa);
					contratoRoutingHistory.setFecha(hoy);
					contratoRoutingHistory.setRol(rolSupervisorCallCenter);
					
					contratoRoutingHistory.setFact(hoy);
					contratoRoutingHistory.setTerm(new Long(1));
					contratoRoutingHistory.setUact(new Long(1));
					
					entityManager.persist(contratoRoutingHistory);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Asigna un Contrato (con estado "LLAMAR") a la "bandeja" de Supervisores de Call-Center de la Empresa empresaId.
	 * 
	 * @param empresaId ID de la empresa a la cual asignar el Contrato.
	 * @param contrato El Contrato a asignar.
	 */
	public String addAsignacionManual(Long empresaId, Contrato contrato) {
		String result = "Operación exitosa.";
		
		try {
			Empresa empresa = iEmpresaBean.getById(empresaId);
			
			Rol rol = iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.SupervisorCallCenter")));
			
			Estado estado = iEstadoBean.getById(new Long(Configuration.getInstance().getProperty("estado.LLAMAR")));
			
			Long estadoVendidoId = 
				new Long(Configuration.getInstance().getProperty("estado.VENDIDO"));
			
			TypedQuery<Contrato> query = 
				entityManager.createQuery(
					"SELECT c"
					+ " FROM Contrato c"
					+ " WHERE c.estado.id >= :estadoVendidoId"
					+ " AND c.mid = :mid", 
					Contrato.class
				);
			query.setParameter("estadoVendidoId", estadoVendidoId);
			query.setParameter("mid", contrato.getMid());
			
			List<Contrato> resultList = query.getResultList();
			if (resultList.size() == 0) {
				contrato.setEmpresa(empresa);
				contrato.setEstado(estado);
				contrato.setRol(rol);
				
				Contrato contratoManaged = iContratoBean.update(contrato);
				
				Date date = GregorianCalendar.getInstance().getTime();
				
				ContratoRoutingHistory contratoRoutingHistoryNew = new ContratoRoutingHistory();
				contratoRoutingHistoryNew.setContrato(contratoManaged);
				contratoRoutingHistoryNew.setEmpresa(empresa);
				contratoRoutingHistoryNew.setFecha(date);
				contratoRoutingHistoryNew.setRol(rol);
				
				contratoRoutingHistoryNew.setFact(date);
				contratoRoutingHistoryNew.setTerm(new Long(1));
				contratoRoutingHistoryNew.setUact(new Long(1));
				
				entityManager.persist(contratoRoutingHistoryNew);
			} else {
				result = "El MID ya fue vendido. No se puede asignar.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Asigna los Contratos que cumplen con los criterios especificados al Usuario y Rol especificados y actualiza su Estado según parámetro.
	 * 
	 * @param usuario Usuario a asignar los Contratos.
	 * @param rol Rol a asignar los Contratos.
	 * @param estado Estado a actualizar en los Contratos.
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId ID del Usuario que consulta.
	 */
	public void asignar(Usuario usuario, Rol rol, Estado estado, MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		try {
			Collection<ContratoRoutingHistory> resultList = new LinkedList<ContratoRoutingHistory>();
			if (metadataConsulta.getTamanoSubconjunto() != null) {
				metadataConsulta.setTamanoMuestra(metadataConsulta.getTamanoSubconjunto());
				
				List<ContratoRoutingHistory> toOrder = new LinkedList<ContratoRoutingHistory>();
				for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
					toOrder.add((ContratoRoutingHistory) object);
				}
				
				Collections.sort(toOrder, new Comparator<ContratoRoutingHistory>() {
					public int compare(ContratoRoutingHistory arg0, ContratoRoutingHistory arg1) {
						Random random = new Random();
						
						return random.nextBoolean() ? 1 : -1;
					}
				});
				
				int i = 0;
				for (ContratoRoutingHistory contratoRoutingHistory : toOrder) {
					resultList.add(contratoRoutingHistory);
					
					i++;
					if (i == metadataConsulta.getTamanoSubconjunto()) {
						break;
					}
				}
			} else {
				metadataConsulta.setTamanoMuestra(new Long(Integer.MAX_VALUE));
				
				for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
					resultList.add((ContratoRoutingHistory) object);
				}
			}
			
			Date currentDate = GregorianCalendar.getInstance().getTime();
			
			Long rolDistribuidorId = 
				new Long(Configuration.getInstance().getProperty("rol.Distribuidor"));
			
			for (ContratoRoutingHistory contratoRoutingHistory : resultList) {
				Contrato contrato = contratoRoutingHistory.getContrato();
				
				contrato.setEstado(estado);
				contrato.setRol(rol);
				contrato.setUsuario(usuario);
				
				if (rol.getId().equals(rolDistribuidorId)) {
					contrato.setFechaEntregaDistribuidor(currentDate);
				} 
				
				entityManager.merge(contrato);
				
				ContratoRoutingHistory contratoRoutingHistoryNew = new ContratoRoutingHistory();
				contratoRoutingHistoryNew.setContrato(contratoRoutingHistory.getContrato());
				contratoRoutingHistoryNew.setEmpresa(contratoRoutingHistory.getEmpresa());
				contratoRoutingHistoryNew.setFecha(currentDate);
				contratoRoutingHistoryNew.setRol(rol);
				contratoRoutingHistoryNew.setUsuario(usuario);
				
				contratoRoutingHistoryNew.setFact(currentDate);
				contratoRoutingHistoryNew.setTerm(new Long(1));
				contratoRoutingHistoryNew.setUact(new Long(1));
				
				entityManager.persist(contratoRoutingHistoryNew);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Asigna el Contrato relacionado con la asignación anterior (contratoRoutingHistory) al Usuario y Rol especificados.
	 * 
	 * @param usuario Usuario a asignar el Contrato.
	 * @param rol Rol a asignar el Contrato.
	 * @param contratoRoutingHistory Asignación anterior.
	 */
	public void asignar(Usuario usuario, Rol rol, ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			ContratoRoutingHistory contratoRoutingHistoryManaged = entityManager.find(ContratoRoutingHistory.class, contratoRoutingHistory.getId());
			
			ContratoRoutingHistory contratoRoutingHistoryNew = new ContratoRoutingHistory();
			
			contratoRoutingHistoryNew.setFecha(date);
			contratoRoutingHistoryNew.setContrato(contratoRoutingHistoryManaged.getContrato());
			contratoRoutingHistoryNew.setEmpresa(contratoRoutingHistoryManaged.getEmpresa());
			contratoRoutingHistoryNew.setRol(rol);
			contratoRoutingHistoryNew.setUsuario(usuario);
			
			contratoRoutingHistoryNew.setFact(date);
			contratoRoutingHistoryNew.setTerm(new Long(1));
			contratoRoutingHistoryNew.setUact(new Long(1));
			
			entityManager.persist(contratoRoutingHistoryNew);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Asigna los contratos que cumplen con los criterios especificados al Usuario (Vendedor) especificado.
	 * 
	 * @param usuario Usuario a asignar los Contratos.
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId Id del Usuario que consulta.
	 */
	public void asignarVentas(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioid) {
		Rol rolVendedor = 
			iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.Vendedor")));
		
		Estado estadoLlamar = 
			iEstadoBean.getById(new Long(Configuration.getInstance().getProperty("estado.LLAMAR")));
		
		asignar(usuario, rolVendedor, estadoLlamar, metadataConsulta, loggedUsuarioid);
	}
	
	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "VENDIDO" y lo asigna a la "bandeja" de Supervisores de Back-office.
	 * 
	 * @param contratoRoutingHistory Asignación actual.
	 */
	public void agendar(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.VENDIDO"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorBackOffice"))
			);
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			contrato.setFechaVenta(date);
			contrato.setVendedor(contrato.getUsuario());
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			contrato.setUact(new Long(1));
			
			iContratoBean.update(contrato);
			
			asignar(null, rol, contratoRoutingHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "RECHAZADO" y lo asigna a la "bandeja" de Supervisores de Call-center.
	 * 
	 * @param contratoRoutingHistory Asignación actual
	 */
	public void rechazar(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.RECHAZADO"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorCallCenter"))
			);
			
			contrato.setFechaRechazo(date);
			contrato.setVendedor(contrato.getUsuario());
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			contrato.setUact(new Long(1));
			
			iContratoBean.update(contrato);
			
			asignar(null, rol, contratoRoutingHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "RELLAMAR" y lo asigna a la "bandeja" de Vendedores.
	 * 
	 * @param contratoRoutingHistory Asignación actual.
	 */
	public void posponer(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.RELLAMAR"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.Vendedor"))
			);
			
			contrato.setEstado(estado);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			contrato.setUact(new Long(1));
			
			iContratoBean.update(contrato);
			
			asignar(null, rol, contratoRoutingHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Asigna los contratos que cumplen con los criterios especificados al Usuario (Back-office) especificado.
	 * 
	 * @param usuario Usuario a asignar los Contratos.
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId Id del Usuario que consulta.
	 */
	public void asignarBackoffice(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		Rol rolBackoffice = 
			iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.Backoffice")));
		
		Estado estadoVendido = 
			iEstadoBean.getById(new Long(Configuration.getInstance().getProperty("estado.VENDIDO")));
		
		asignar(usuario, rolBackoffice, estadoVendido, metadataConsulta, loggedUsuarioId);
	}
	
	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "DISTRIBUIR" y lo asigna a la "bandeja" de Supervisores de Distribución.
	 * 
	 * @param contratoRoutingHistory Asignación actual.
	 */
	public void distribuir(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.DISTRIBUIR"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorDistribucion"))
			);
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			
			contrato.setFechaBackoffice(date);
			contrato.setBackoffice(contrato.getUsuario());
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			contrato.setUact(new Long(1));
			
			iContratoBean.update(contrato);
			
			asignar(null, rol, contratoRoutingHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "RE-DISTRIBUIR" y lo asigna a la "bandeja" de Supervisores de Distribución.
	 * 
	 * @param contratoRoutingHistory Asignación actual.
	 */
	public void redistribuir(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.REDISTRIBUIR"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorDistribucion"))
			);
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setDistribuidor(null);
			contrato.setFechaEntregaDistribuidor(null);
			contrato.setFechaDevolucionDistribuidor(null);
			contrato.setActivador(null);
			contrato.setFechaActivacion(null);
			contrato.setFechaActivarEn(null);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			contrato.setUact(new Long(1));
			
			iContratoBean.update(contrato);
			
			asignar(null, rol, contratoRoutingHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "TELELINK".
	 * 
	 * @param contratoRoutingHistory Asignación actual.
	 */
	public void telelink(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.TELELINK"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorBackOffice"))
			);
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setFechaBackoffice(date);
			contrato.setBackoffice(contrato.getUsuario());
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			contrato.setUact(new Long(1));
			
			iContratoBean.update(contrato);
			
			asignar(null, rol, contratoRoutingHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "RENOVADO" y lo asigna a la "bandeja" de Supervisores de Back-office.
	 * 
	 * @param contratoRoutingHistory Asignación actual.
	 */
	public void renovo(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.RENOVADO"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorBackOffice"))
			);
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			
			contrato.setFechaBackoffice(date);
			contrato.setBackoffice(contrato.getUsuario());
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			contrato.setUact(new Long(1));
			
			iContratoBean.update(contrato);
			
			asignar(null, rol, contratoRoutingHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Asigna los contratos que cumplen con los criterios especificados al Usuario (Distribuidor) especificado.
	 * 
	 * @param usuario Usuario a asignar los Contratos.
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId Id del Usuario que consulta.
	 */
	public void asignarDistribuidor(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		Rol rolBackoffice = 
			iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.Distribuidor")));
		
		Estado estadoDistribuir = 
			iEstadoBean.getById(new Long(Configuration.getInstance().getProperty("estado.DISTRIBUIR")));
		
		asignar(usuario, rolBackoffice, estadoDistribuir, metadataConsulta, loggedUsuarioId);
	}
	
	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "REAGENDAR" y lo asigna a la "bandeja" del último Vendedor que realizó la venta.
	 * 
	 * @param contratoRoutingHistory Asignación actual.
	 */
	public void reagendar(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.REAGENDAR"))
				);
			
			contrato.setEstado(estado);
			
			TypedQuery<ContratoRoutingHistory> query = 
				entityManager.createQuery(
					"SELECT crh"
					+ " FROM ContratoRoutingHistory crh"
					+ " WHERE crh.contrato.id = :contratoId"
					+ " AND crh.rol.id = :rolId"
					+ " ORDER BY crh.id DESC",
					ContratoRoutingHistory.class
				);
			query.setParameter("contratoId", contrato.getId());
			query.setParameter("rolId", new Long(Configuration.getInstance().getProperty("rol.Vendedor")));
			
			query.setMaxResults(1);
			
			Collection<ContratoRoutingHistory> resultList = query.getResultList();
			if (resultList.size() > 0) {
				ContratoRoutingHistory contratoRoutingHistoryVendedor = resultList.toArray(new ContratoRoutingHistory[]{})[0];
				
				ContratoRoutingHistory contratoRoutingHistoryReagendar = new ContratoRoutingHistory();
				
				contratoRoutingHistoryReagendar.setContrato(contratoRoutingHistoryVendedor.getContrato());
				contratoRoutingHistoryReagendar.setEmpresa(contratoRoutingHistoryVendedor.getEmpresa());
				contratoRoutingHistoryReagendar.setFecha(date);
				contratoRoutingHistoryReagendar.setRol(contratoRoutingHistoryVendedor.getRol());
				contratoRoutingHistoryReagendar.setUsuario(contratoRoutingHistoryVendedor.getUsuario());
				
				contratoRoutingHistoryReagendar.setFact(date);
				contratoRoutingHistoryReagendar.setTerm(new Long(1));
				contratoRoutingHistoryReagendar.setUact(new Long(1));
				
				entityManager.persist(contratoRoutingHistoryReagendar);
				
				contrato.setRol(contratoRoutingHistoryVendedor.getRol());
				contrato.setUsuario(contratoRoutingHistoryVendedor.getUsuario());
				
				contrato.setFact(date);
				contrato.setTerm(new Long(1));
				contrato.setUact(new Long(1));
				
				iContratoBean.update(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "ACTIVAR" y lo asigna a la "bandeja" de Supervisores de Activación.
	 * 
	 * @param contratoRoutingHistory Asignación actual.
	 */
	public void activar(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.ACTIVAR"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.SupervisorActivacion"))
			);
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			
			contrato.setFechaDevolucionDistribuidor(date);
			contrato.setDistribuidor(contrato.getUsuario());
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			contrato.setUact(new Long(1));
			
			iContratoBean.update(contrato);
			
			asignar(null, rol, contratoRoutingHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "NO FIRMA".
	 * 
	 * @param contratoRoutingHistory Asignación actual.
	 */
	public void noFirma(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.NOFIRMA"))
				);
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			
			contrato.setFechaDevolucionDistribuidor(date);
			contrato.setDistribuidor(contrato.getUsuario());
			contrato.setEstado(estado);
			contrato.setRol(contratoManaged.getRol());
			contrato.setUsuario(contrato.getUsuario());
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			contrato.setUact(new Long(1));
			
			iContratoBean.update(contrato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "RE-COORDINAR" y lo asigna a la "bandeja" de Coordinadores de Distribución.
	 * 
	 * @param contratoRoutingHistory Asignación actual.
	 */
	public void recoordinar(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.RECOORDINAR"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.CoordinadorDistribucion"))
			);
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			
			contrato.setFechaDevolucionDistribuidor(date);
			contrato.setDistribuidor(contrato.getUsuario());
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			contrato.setUact(new Long(1));
			
			iContratoBean.update(contrato);
			
			asignar(null, rol, contratoRoutingHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Asigna los contratos que cumplen con los criterios especificados al Usuario (Activador) especificado.
	 * 
	 * @param usuario Usuario a asignar los Contratos.
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId Id del Usuario que consulta.
	 */
	public void asignarActivador(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		Rol rolBackoffice = 
			iRolBean.getById(new Long(Configuration.getInstance().getProperty("rol.Activador")));
		
		Estado estadoActivar = 
			iEstadoBean.getById(new Long(Configuration.getInstance().getProperty("estado.ACTIVAR")));
		
		asignar(usuario, rolBackoffice, estadoActivar, metadataConsulta, loggedUsuarioId);
	}
	
	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "ACT. DOC. VENTA" y lo asigna a la "bandeja" del Activador que agendó la activación.
	 * 
	 * @param contratoRoutingHistory Asignación actual.
	 */
	public void agendarActivacion(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.ACTDOCVENTA"))
				);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.Activador"))
			);
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			contrato.setUact(new Long(1));
			
			contrato.setEstado(estado);
			
			iContratoBean.update(contrato);
			
			asignar(contratoRoutingHistory.getUsuario(), rol, contratoRoutingHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "ACM".
	 * 
	 * @param contratoRoutingHistory Asignación actual.
	 */
	public void terminar(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.ACM"))
				);
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			
			contrato.setFechaActivacion(date);
			contrato.setActivador(contrato.getUsuario());
			contrato.setEstado(estado);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			contrato.setUact(new Long(1));
			
			iContratoBean.update(contrato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Actualiza el Contrato relacionado con la asignación (contratoRoutingHistory) a estado "FALTA DOCUMENTACION" y lo asigna a la "bandeja" de Coordinadores de Distribución.
	 * 
	 * @param contratoRoutingHistory Asignación actual.
	 */
	public void faltaDocumentacion(ContratoRoutingHistory contratoRoutingHistory) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			Contrato contrato = contratoRoutingHistory.getContrato();
			
			Estado estado = 
				iEstadoBean.getById(
					new Long(Configuration.getInstance().getProperty("estado.FALTADOCUMENTACION"))
				);
			
			contrato.setEstado(estado);
			
			Rol rol = iRolBean.getById(
				new Long(Configuration.getInstance().getProperty("rol.CoordinadorDistribucion"))
			);
			
			Contrato contratoManaged = entityManager.find(Contrato.class, contrato.getId());
			
			contrato.setFechaVenta(contratoManaged.getFechaVenta());
			contrato.setVendedor(contratoManaged.getVendedor());
			contrato.setBackoffice(contratoManaged.getBackoffice());
			contrato.setFechaBackoffice(contratoManaged.getFechaBackoffice());
			contrato.setDistribuidor(contratoManaged.getDistribuidor());
			contrato.setFechaEntregaDistribuidor(contratoManaged.getFechaEntregaDistribuidor());
			contrato.setFechaDevolucionDistribuidor(contratoManaged.getFechaDevolucionDistribuidor());
			contrato.setActivador(contratoManaged.getActivador());
			
			contrato.setEstado(estado);
			contrato.setRol(rol);
			contrato.setUsuario(null);
			
			contrato.setFact(date);
			contrato.setTerm(new Long(1));
			contrato.setUact(new Long(1));
			
			iContratoBean.update(contrato);
			
			asignar(null, rol, contratoRoutingHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lista las asignaciones existentes para el contrato con ID contratoId.
	 * 
	 * @param contratoId ID del Contrato a consultar.
	 * @return La lista de asignaciones del Contrato especificado.
	 */
	public Collection<ContratoRoutingHistory> listByContratoId(Long contratoId) {
		Collection<ContratoRoutingHistory> result = new LinkedList<ContratoRoutingHistory>();
		
		try {
			TypedQuery<ContratoRoutingHistory> query = 
				entityManager.createQuery(
					"SELECT crh"
					+ " FROM ContratoRoutingHistory crh"
					+ " WHERE crh.contrato.id = :contratoId"
					+ " ORDER BY crh.id DESC",
					ContratoRoutingHistory.class
				);
			query.setParameter("contratoId", contratoId);
			
			for (ContratoRoutingHistory contratoRoutingHistory : query.getResultList()) {
				result.add(contratoRoutingHistory);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * Exporta los datos que cumplen con los criterios especificados al un archivo .csv de nombre generado según: YYYYMMDDHHmmSS en la carpeta de exportación del sistema.
	 * 
	 * @param metadataConsulta Criterios de la consulta.
	 * @param loggedUsuarioId ID del Usuario que consulta.
	 */
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId) {
		String result = null;
		
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			String fileName = 
				gregorianCalendar.get(GregorianCalendar.YEAR) + ""
				+ (gregorianCalendar.get(GregorianCalendar.MONTH) + 1) + ""
				+ gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH) + ""
				+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY) + ""
				+ gregorianCalendar.get(GregorianCalendar.MINUTE) + ""
				+ gregorianCalendar.get(GregorianCalendar.SECOND)
				+ ".csv";
			
			PrintWriter printWriter = 
				new PrintWriter(
					new FileWriter(
						Configuration.getInstance().getProperty("exportacion.carpeta") + fileName
					)
				);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				ContratoRoutingHistory contratoRoutingHistory = (ContratoRoutingHistory) object;
				Contrato contrato = contratoRoutingHistory.getContrato();
				
				printWriter.println(
					contrato.getMid()
					+ ";" + (contrato.getFechaFinContrato() != null ? 
						format.format(contrato.getFechaFinContrato())
						: "")
					+ ";" + (contrato.getTipoContratoCodigo() != null ?
						contrato.getTipoContratoCodigo() 
						: "")
					+ ";" + (contrato.getTipoContratoDescripcion() != null ?
						contrato.getTipoContratoDescripcion()
						: "")
					+ ";" + (contrato.getDocumentoTipo() != null ?
						contrato.getDocumentoTipo()
						: "")
					+ ";'" + (contrato.getDocumento() != null ?
						contrato.getDocumento()
						: "")
					+ ";" + (contrato.getNombre() != null ?
						contrato.getNombre()
						: "")
					+ ";" + (contrato.getDireccion() != null ?
						contrato.getDireccion()
						: "")
					+ ";" + (contrato.getCodigoPostal() != null ?
						contrato.getCodigoPostal()
						: "")
					+ ";" + (contrato.getLocalidad() != null ?
						contrato.getLocalidad()
						: "")
					+ ";" + (contrato.getEquipo() != null ?
						contrato.getEquipo()
						: "")
					+ ";" + (contrato.getAgente() != null ?
						contrato.getAgente()
						: "")
					+ ";" + (contrato.getNumeroContrato() != null ?
						contrato.getNumeroContrato()
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
}