package uy.com.amensg.logistica.bean;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

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

import uy.com.amensg.logistica.entities.ACMInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryHelper;

@Stateless
public class ACMInterfaceRiesgoCrediticioBean implements IACMInterfaceRiesgoCrediticioBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			// Obtener el usuario para el cual se consulta
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<ACMInterfaceRiesgoCrediticio> criteriaQuery = criteriaBuilder.createQuery(ACMInterfaceRiesgoCrediticio.class);
			
			Root<ACMInterfaceRiesgoCrediticio> root = criteriaQuery.from(ACMInterfaceRiesgoCrediticio.class);
			root.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, root);
			
			Subquery<UsuarioRolEmpresa> subqueryEmpresasUsuario = criteriaQuery.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootEmpresasUsuario = subqueryEmpresasUsuario.from(UsuarioRolEmpresa.class);
			subrootEmpresasUsuario.alias("subrootEmpresasUsuario");
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.exists(
					subqueryEmpresasUsuario
						.select(subrootEmpresasUsuario)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(subrootEmpresasUsuario.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "loggedUsuarioId")),
								criteriaBuilder.equal(subrootEmpresasUsuario.get("empresa"), root.get("empresa"))
							)
						)
				)
			);
			
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

			TypedQuery<ACMInterfaceRiesgoCrediticio> query = entityManager.createQuery(criteriaQuery);
			
			query.setParameter("loggedUsuarioId", usuarioId);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los par�metros seg�n las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<ACMInterfaceRiesgoCrediticio> field = root;
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
			for (ACMInterfaceRiesgoCrediticio acmInterfaceRiesgoCrediticio : query.getResultList()) {
				registrosMuestra.add(acmInterfaceRiesgoCrediticio);
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
			Root<ACMInterfaceRiesgoCrediticio> rootCount = criteriaQueryCount.from(ACMInterfaceRiesgoCrediticio.class);
			rootCount.alias("root");
			
			Predicate where = new QueryHelper().construirWhere(metadataConsulta, criteriaBuilder, rootCount);
			
			Subquery<UsuarioRolEmpresa> subqueryEmpresasUsuario = criteriaQueryCount.subquery(UsuarioRolEmpresa.class);
			Root<UsuarioRolEmpresa> subrootEmpresasUsuario = subqueryEmpresasUsuario.from(UsuarioRolEmpresa.class);
			subrootEmpresasUsuario.alias("subrootEmpresasUsuario");
			
			where = criteriaBuilder.and(
				where,
				criteriaBuilder.exists(
					subqueryEmpresasUsuario
						.select(subrootEmpresasUsuario)
						.where(
							criteriaBuilder.and(
								criteriaBuilder.equal(subrootEmpresasUsuario.get("usuario").get("id"), criteriaBuilder.parameter(Long.class, "loggedUsuarioId")),
								criteriaBuilder.equal(subrootEmpresasUsuario.get("empresa"), rootCount.get("empresa"))
							)
						)
				)
			);
			
			criteriaQueryCount
				.select(criteriaBuilder.count(rootCount.get("documento")))
				.where(where);
			
			TypedQuery<Long> queryCount = entityManager.createQuery(criteriaQueryCount);
			
			queryCount.setParameter("loggedUsuarioId", usuarioId);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			// Setear los par�metros seg�n las condiciones del filtro
			int i = 0;
			for (MetadataCondicion metadataCondicion : metadataConsulta.getMetadataCondiciones()) {
				if (!metadataCondicion.getOperador().equals(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO)) {
					for (String valor : metadataCondicion.getValores()) {
						String[] campos = metadataCondicion.getCampo().split("\\.");
						
						Path<ACMInterfaceRiesgoCrediticio> field = rootCount;
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
	
	public void save(ACMInterfaceRiesgoCrediticio acmInterfaceRiesgoCrediticio) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			acmInterfaceRiesgoCrediticio.setFact(date);
			acmInterfaceRiesgoCrediticio.setTerm(new Long(1));
			acmInterfaceRiesgoCrediticio.setUact(new Long(1));
			
			entityManager.persist(acmInterfaceRiesgoCrediticio);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			
			printWriter.println(
				"Empresa"
				+ ";Documento" 
				+ ";Antigüedad celular"
				+ ";Deuda celular"
				+ ";Riesgo crediticio celular"
				+ ";Estado deuda cliente fijo"
				+ ";Obtenido"
			);
			
			metadataConsulta.setTamanoMuestra(new Long(Integer.MAX_VALUE));
			
			String etiquetaSi = "SI";
			String etiquetaNo = "NO";
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			for (Object object : this.list(metadataConsulta, loggedUsuarioId).getRegistrosMuestra()) {
				ACMInterfaceRiesgoCrediticio acmInterfaceRiesgoCrediticio = (ACMInterfaceRiesgoCrediticio) object;
				
				String line = 
					(acmInterfaceRiesgoCrediticio.getEmpresa() != null ?
						acmInterfaceRiesgoCrediticio.getEmpresa().getNombre()
						: "")
					+ ";" + (acmInterfaceRiesgoCrediticio.getDocumento() != null ? 
						acmInterfaceRiesgoCrediticio.getDocumento()
						: "")
					+ ";" + (acmInterfaceRiesgoCrediticio.getFechaCelular() != null ?
						format.format(acmInterfaceRiesgoCrediticio.getFechaCelular())
						: "")
					+ ";" + (acmInterfaceRiesgoCrediticio.getDeudaCelular() != null ?
						(acmInterfaceRiesgoCrediticio.getDeudaCelular() ? etiquetaSi : etiquetaNo) 
						: "")
					+ ";" + (acmInterfaceRiesgoCrediticio.getRiesgoCrediticioCelular() != null ?
						(acmInterfaceRiesgoCrediticio.getRiesgoCrediticioCelular() ? etiquetaSi : etiquetaNo)
						: "")
					+ ";" + (acmInterfaceRiesgoCrediticio.getEstadoDeudaClienteFijo() != null ?
						acmInterfaceRiesgoCrediticio.getEstadoDeudaClienteFijo()
						: "")
					+ ";" + (acmInterfaceRiesgoCrediticio.getFact() != null ?
						format.format(acmInterfaceRiesgoCrediticio.getFact())
						: "");
					
				printWriter.println(line.replaceAll("\n", ""));
			}
			
			printWriter.close();
			
			result = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}