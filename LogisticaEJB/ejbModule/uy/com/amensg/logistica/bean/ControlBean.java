package uy.com.amensg.logistica.bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;

import uy.com.amensg.logistica.entities.Control;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EstadoControl;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ControlBean implements IControlBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IEmpresaBean iEmpresaBean;
	
	@EJB
	private IEstadoControlBean iEstadoControlBean;

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			// Obtener el usuario para el cual se consulta
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<Control> criteriaQuery = criteriaBuilder.createQuery(Control.class);
			
			Root<Control> root = criteriaQuery.from(Control.class);
			root.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root);
			
//			Subquery<UsuarioRolEmpresa> subqueryRolesSubordinados = criteriaQuery.subquery(UsuarioRolEmpresa.class);
//			Root<UsuarioRolEmpresa> subrootRolesSubordinados = subqueryRolesSubordinados.from(UsuarioRolEmpresa.class);
//			subrootRolesSubordinados.alias("subrootRolesSubordinados");
//			Join<Rol, Rol> joinRolesSubordinados = subrootRolesSubordinados.join("rol", JoinType.INNER).join("subordinados", JoinType.INNER);
//			Expression<Collection<Rol>> expressionRolesSubordinados = joinRolesSubordinados.get("subordinados");
//			
//			where = criteriaBuilder.and(
//				where,
//				criteriaBuilder.or(
//					// Asignados al usuario.
//					criteriaBuilder.equal(root.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario1")),
//					// Asignados a alg�n rol subordinado dentro de la empresa
//					criteriaBuilder.exists(
//						subqueryRolesSubordinados
//							.select(subrootRolesSubordinados)
//							.where(
//								criteriaBuilder.and(
//									criteriaBuilder.equal(subrootRolesSubordinados.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario2")),
//									criteriaBuilder.equal(subrootRolesSubordinados.get("empresa").get("id"), root.get("empresa").get("id")),
//									criteriaBuilder.isMember(root.get("rol").as(Rol.class), expressionRolesSubordinados)
//								)
//							)
//					)
//				)
//			);
			
			// Procesar las ordenaciones para los registros de la muestra
			List<Order> orders = new LinkedList<Order>();
			for (MetadataOrdenacion metadataOrdenacion : metadataConsulta.getMetadataOrdenaciones()) {
				String[] campos = metadataOrdenacion.getCampo().split("\\.");
				
				Join<?, ?> join = null;
				for (int j=0; j<campos.length - 1; j++) {
					if (join != null) {
						join = join.join(campos[j], JoinType.LEFT);
					} else {
						join = root.join(campos[j], JoinType.LEFT);
					}
				}
				
				if (metadataOrdenacion.getAscendente()) {
					orders.add(
						criteriaBuilder.asc(
							join != null ? join.get(campos[campos.length - 1]) : root.get(campos[campos.length - 1])
						)
					);
				} else {
					orders.add(
						criteriaBuilder.desc(
							join != null ? join.get(campos[campos.length - 1]) : root.get(campos[campos.length - 1])
						)
					);
				}
			}
			
			criteriaQuery
				.select(root)
				.where(where)
				.orderBy(orders);

			TypedQuery<Control> query = entityManager.createQuery(criteriaQuery);
			
//			query.setParameter("usuario1", usuarioId);
//			query.setParameter("usuario2", usuarioId);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los par�metros seg�n las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<Control> field = root;
						Join<?, ?> join = null;
						for (int j=0; j<campos.length - 1; j++) {
							if (join != null) {
								join = join.join(campos[j], JoinType.LEFT);
							} else {
								join = root.join(campos[j], JoinType.LEFT);
							}
						}
						
						if (join != null) {
							field = join.get(campos[campos.length - 1]);
						} else {
							field = root.get(campos[campos.length - 1]);
						}
						
						try {
							if (field.getJavaType().equals(Date.class)) {
								query.setParameter(
									"p" + i,
									format.parse(valor)
								);
							} else if (field.getJavaType().equals(Long.class)) {
								query.setParameter(
									"p" + i,
									new Long(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								query.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								query.setParameter(
									"p" + i,
									new Double(valor)
								);
							} else if (field.getJavaType().equals(Boolean.class)) {
								query.setParameter(
									"p" + i,
									new Boolean(valor)
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
			
			// Acotar al tama�o de la muestra
			query.setMaxResults(metadataConsulta.getTamanoMuestra().intValue());
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			for (Control control : query.getResultList()) {
				registrosMuestra.add(control);
			}
			
			result.setRegistrosMuestra(registrosMuestra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			// Obtener el usuario para el cual se consulta
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			// -------------------------------------------
			// Query para obtener la cantidad de registros
			// -------------------------------------------
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<Control> rootCount = criteriaQueryCount.from(Control.class);
			rootCount.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
//			Subquery<UsuarioRolEmpresa> subqueryRolesSubordinados = criteriaQueryCount.subquery(UsuarioRolEmpresa.class);
//			Root<UsuarioRolEmpresa> subrootRolesSubordinados = subqueryRolesSubordinados.from(UsuarioRolEmpresa.class);
//			subrootRolesSubordinados.alias("subrootRolesSubordinados");
//			Join<Rol, Rol> joinRolesSubordinados = subrootRolesSubordinados.join("rol", JoinType.INNER).join("subordinados", JoinType.INNER);
//			Expression<Collection<Rol>> expressionRolesSubordinados = joinRolesSubordinados.get("subordinados");
//			
//			where = criteriaBuilder.and(
//				where,
//				criteriaBuilder.or(
//					// Asignados al usuario.
//					criteriaBuilder.equal(rootCount.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario1")),
//					// Asignados a alg�n rol subordinado dentro de la empresa
//					criteriaBuilder.exists(
//						subqueryRolesSubordinados
//							.select(subrootRolesSubordinados)
//							.where(
//								criteriaBuilder.and(
//									criteriaBuilder.equal(subrootRolesSubordinados.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "usuario2")),
//									criteriaBuilder.equal(subrootRolesSubordinados.get("empresa").get("id"), rootCount.get("empresa").get("id")),
//									criteriaBuilder.isMember(rootCount.get("rol").as(Rol.class), expressionRolesSubordinados)
//								)
//							)
//					)
//				)
//			);
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount.get("id")))
				.where(where);
			
			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
//			queryCount.setParameter("usuario1", usuarioId);
//			queryCount.setParameter("usuario2", usuarioId);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los par�metros seg�n las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<Control> field = rootCount;
						Join<?, ?> join = null;
						for (int j=0; j<campos.length - 1; j++) {
							if (join != null) {
								join = join.join(campos[j], JoinType.LEFT);
							} else {
								join = rootCount.join(campos[j], JoinType.LEFT);
							}
						}
						
						if (join != null) {
							field = join.get(campos[campos.length - 1]);
						} else {
							field = rootCount.get(campos[campos.length - 1]);
						}
						
						try {
							if (field.getJavaType().equals(Date.class)) {
								queryCount.setParameter(
									"p" + i,
									format.parse(valor)
								);
							} else if (field.getJavaType().equals(Long.class)) {
								queryCount.setParameter(
									"p" + i,
									new Long(valor)
								);
							} else if (field.getJavaType().equals(String.class)) {
								queryCount.setParameter(
									"p" + i,
									valor
								);
							} else if (field.getJavaType().equals(Double.class)) {
								queryCount.setParameter(
									"p" + i,
									new Double(valor)
								);
							} else if (field.getJavaType().equals(Boolean.class)) {
								queryCount.setParameter(
									"p" + i,
									new Boolean(valor)
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
			
			result = queryCount.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String preprocesarArchivoEmpresa(String fileName, Long empresaId) {
		String result = null;
		
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			Collection<Long> mids = new LinkedList<Long>();
			
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] fields = line.split(";");
				
				Long mid = new Long (fields[0].trim());
				
				mids.add(mid);
			}
			
			Map<Long, Integer> map = this.preprocesarConjunto(mids, empresaId);
			
			Long importar = new Long(0);
			Long sobreescribir = new Long(0);
			Long omitir = new Long(0);
			for (Entry<Long, Integer> entry : map.entrySet()) {
				switch (entry.getValue()) {
					case Constants.__COMPROBACION_IMPORTACION_IMPORTAR:
						importar++;
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_OMITIR:
						omitir++;
						
						break;
					case Constants.__COMPROBACION_IMPORTACION_SOBREESCRIBIR:
						sobreescribir++;
						
						break;
				}
			}
			
			result =
				"Se importar�n " + importar + " MIDs nuevos.|"
				+ "Se sobreescribir�n " + sobreescribir + " MIDs.|"
				+ "Se omitir�n " + omitir + " MIDs.";
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

	public Map<Long, Integer> preprocesarConjunto(Collection<Long> mids, Long empresaId) {
		Map<Long, Integer> result = new HashMap<Long, Integer>();
		
		try {
			for (Long mid : mids) {
				result.put(mid, Constants.__COMPROBACION_IMPORTACION_IMPORTAR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long tipoControlId, Long loggedUsuarioId) {
		BufferedReader bufferedReader = null;
		
		String result = null;
		
		try {
			bufferedReader = 
				new BufferedReader(
					new FileReader(Configuration.getInstance().getProperty("importacion.carpeta") + fileName)
				);
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			Date hoy = gregorianCalendar.getTime();
			
			gregorianCalendar.set(GregorianCalendar.YEAR, 1900);
			gregorianCalendar.set(GregorianCalendar.MONTH, GregorianCalendar.JANUARY);
			gregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
			gregorianCalendar.set(GregorianCalendar.HOUR, 0);
			gregorianCalendar.set(GregorianCalendar.MINUTE, 0);
			gregorianCalendar.set(GregorianCalendar.SECOND, 0);
			gregorianCalendar.set(GregorianCalendar.MILLISECOND, 0);
			
			Date fechaMin = gregorianCalendar.getTime();
			
			EstadoControl estado = 
				iEstadoControlBean.getById(new Long(Configuration.getInstance().getProperty("estadoControl.PENDIENTE")));
			
			Empresa empresa = 
				iEmpresaBean.getById(empresaId);
			
			Session hibernateSession = entityManager.unwrap(Session.class);
			
			SQLQuery insertControl = hibernateSession.createSQLQuery(
				"INSERT INTO control("
					+ " id,"
					+ " fecha_control,"
					+ " mes_control,"
					+ " fecha_importacion,"
					+ " fecha_activacion,"
					+ " carga_inicial,"
					+ " monto_cargar,"
					+ " fact,"
					+ " term,"
					+ " uact,"
					
					+ " empresa_id,"
					+ " estado_control_id,"
					+ " tipo_control_id,"
					+ " mid"
				+ " ) VALUES ("
					+ " nextval('hibernate_sequence'),"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?"
				+ " )"
			);
			
			insertControl.setParameter(0, fechaMin, DateType.INSTANCE);
			insertControl.setParameter(1, fechaMin, DateType.INSTANCE);
			insertControl.setParameter(2, hoy, DateType.INSTANCE);
			insertControl.setParameter(3, fechaMin, DateType.INSTANCE);
			insertControl.setParameter(4, new Long(0), LongType.INSTANCE);
			insertControl.setParameter(5, new Long(0), LongType.INSTANCE);
			insertControl.setParameter(6, hoy, DateType.INSTANCE);
			insertControl.setParameter(7, new Long(1), LongType.INSTANCE);
			insertControl.setParameter(8, loggedUsuarioId, LongType.INSTANCE);
			insertControl.setParameter(9, empresa.getId(), LongType.INSTANCE);
			insertControl.setParameter(10, estado.getId(), LongType.INSTANCE);
			insertControl.setParameter(11, tipoControlId, LongType.INSTANCE);
			
			String line = null;
			long lineNumber = 0;
			long successful = 0;
			long errors = 0;
			while ((line = bufferedReader.readLine()) != null) {
				lineNumber++;
				
				String[] fields = line.split(";", -1);
				
				if (fields.length < 1) {
					System.err.println(
						"Error al procesar archivo: " + fileName + "."
						+ " Formato de l�nea " + lineNumber + " incompatible."
						+ " Cantidad de columnas (" + fields.length + ") insuficientes."
					);
					errors++;
				} else {
					boolean ok = true;
					
					Long mid = null;
					try {
						mid = new Long(fields[0].trim());
					} catch (NumberFormatException pe) {
						System.err.println(
							"Error al procesar archivo: " + fileName + "."
							+ " Formato de l�nea " + lineNumber + " incompatible."
							+ " Campo mid incorrecto -> " + fields[0].trim());
						ok = false;
					}
					
					if (!ok) {
						errors++;
					} else {
						insertControl.setParameter(12, mid, LongType.INSTANCE);
						
						insertControl.executeUpdate();

						successful++;
					}
				}
			}
			
			result = 
				"L�neas procesadas con �xito: " + successful + ".|"
				+ "L�neas con datos incorrectos: " + errors + ".";
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

	public Control getSiguienteMidParaControlar() {
		Control result = null;
		
		try {
			Date hoy = GregorianCalendar.getInstance().getTime();
			
			TypedQuery<Control> query = 
				entityManager.createQuery(
					"SELECT c"
					+ " FROM Control c"
					+ " WHERE c.estadoControl.id = :estadoPendienteId", 
					Control.class
				);
			query.setParameter("estadoPendienteId", new Long(Configuration.getInstance().getProperty("estadoControl.PENDIENTE")));
			query.setMaxResults(1);
			
			List<Control> resultList = query.getResultList();
			if (resultList.size() > 0) {
				Control control = resultList.get(0);
				
				EstadoControl estadoControl = 
					iEstadoControlBean.getById(new Long(Configuration.getInstance().getProperty("estadoControl.PROCESANDO")));
				
				control.setEstadoControl(estadoControl);
				
				control.setFact(hoy);
				control.setTerm(new Long(1));
				control.setUact(new Long(1));
				
				control = entityManager.merge(control);
				
				result = control;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void actualizarDatosControl(Control control) {
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			
			Date hoy = gregorianCalendar.getTime();
			
			TypedQuery<Control> query = 
				entityManager.createQuery(
					"SELECT c"
					+ " FROM Control c"
					+ " WHERE c.empresa.id = :empresaId"
					+ " AND c.tipoControl.id = :tipoControlId"
					+ " AND c.mid = :mid"
					+ " AND c.estadoControlId = :estadoControlProcesandoId",
					Control.class
				);
			query.setParameter("empresaId", control.getEmpresa().getId());
			query.setParameter("tipoControlId", control.getTipoControl().getId());
			query.setParameter("mid", control.getMid());
			query.setParameter(
				"estadoControlProcesandoId", 
				new Long(Configuration.getInstance().getProperty("estadoControl.PROCESANDO"))
			);
			query.setMaxResults(1);
			
			List<Control> resultList = query.getResultList();
			if (resultList.size() > 0) {
				Control controlManaged = resultList.get(0);
				
				controlManaged.setCargaInicial(control.getCargaInicial());
				controlManaged.setEstadoControl(control.getEstadoControl());
				
				if (control.getEstadoControl().getId().equals(
					new Long(Configuration.getInstance().getProperty("estadoControl.OK"))
				)) {
					controlManaged.setFechaControl(hoy);
					controlManaged.setMesControl(hoy);
				}
				
				controlManaged.setFechaActivacion(control.getFechaActivacion());
				controlManaged.setFechaConexion(control.getFechaConexion());
				controlManaged.setFechaVencimiento(control.getFechaVencimiento());
				controlManaged.setMontoCargar(control.getMontoCargar());
				controlManaged.setMontoTotal(control.getMontoTotal());
				
				control.setFact(hoy);
				control.setTerm(new Long(1));
				control.setUact(new Long(1));
				
				control = entityManager.merge(control);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}