package uy.com.amensg.logistica.bean;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceEstado;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfaceNumeroContrato;
import uy.com.amensg.logistica.entities.ACMInterfacePersona;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.util.Configuration;

@Stateless
public class ACMInterfaceBean implements IACMInterfaceBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IACMInterfacePersonaBean iACMInterfacePersonaBean;
	
	public String getSiguienteMidSinProcesar() {
		String result = null;
		
		try {
			Date today = GregorianCalendar.getInstance().getTime();
			
			ACMInterfaceEstado estado = 
				entityManager.find(
					ACMInterfaceEstado.class, 
					new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.EnProceso"))
				);
			
			Query query = entityManager.createNativeQuery(
				"SELECT m.mid AS mid, c.documento AS documento, c.numero_contrato AS numero_contrato, random() AS random"
				+ " FROM acm_interface_mid m"
				+ " LEFT OUTER JOIN acm_interface_contrato c ON c.mid = m.mid"
				+ " WHERE m.estado IN ("
					+ " :paraProcesar, :paraProcesarPrioritario"
				+ " )"
				+ " ORDER BY m.estado DESC, random"
			);
			query.setParameter("paraProcesar", 
				new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesar")));
			query.setParameter("paraProcesarPrioritario", 
				new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesarPrioritario")));
			query.setMaxResults(1);
			
			List<?> resultList =  query.getResultList();
			if (!resultList.isEmpty()) {
				Object[] row = (Object[]) resultList.get(0);
				
				if (row[1] != null) {
					result = 
						Configuration.getInstance().getProperty("robotLogistica.prefijoTipoRegistro.Contrato")
						+ " " + row[0]
						+ " " + row[1]
						+ " " + (row[2] != null ? row[2] : "");
				} else {
					result =
						Configuration.getInstance().getProperty("robotLogistica.prefijoTipoRegistro.Prepago")
						+ " " + row[0];
				}
				
				ACMInterfaceMid acmInterfaceMid = new ACMInterfaceMid();
				acmInterfaceMid.setEstado(estado);
				acmInterfaceMid.setMid(new Long((Integer)row[0]));
				
				acmInterfaceMid.setFact(today);
				acmInterfaceMid.setTerm(new Long(1));
				acmInterfaceMid.setUact(new Long(1));
				
				this.update(acmInterfaceMid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String getSiguienteNumeroContratoSinProcesar() {
		String result = null;
		
		try {
			Date today = GregorianCalendar.getInstance().getTime();
			
			ACMInterfaceEstado estado = 
				entityManager.find(
					ACMInterfaceEstado.class, 
					new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.EnProceso"))
				);
			
			TypedQuery<ACMInterfaceNumeroContrato> query = entityManager.createQuery(
				"SELECT a"
				+ " FROM ACMInterfaceNumeroContrato a"
				+ " WHERE a.estado.id in ("
					+ " :paraProcesar, :paraProcesarPrioritario"
				+ " )"
				+ " ORDER BY a.estado DESC"
				, ACMInterfaceNumeroContrato.class);
			query.setParameter("paraProcesar", 
				new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesar")));
			query.setParameter("paraProcesarPrioritario", 
				new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ParaProcesarPrioritario")));
			query.setMaxResults(1);
			
			List<ACMInterfaceNumeroContrato> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				ACMInterfaceNumeroContrato acmInterfaceNumeroContrato = query.getResultList().get(0);
				
				result =  acmInterfaceNumeroContrato.getNumeroContrato().toString();
				
				acmInterfaceNumeroContrato.setEstado(estado);
				acmInterfaceNumeroContrato.setFact(today);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void remove(ACMInterfaceContrato acmInterfaceContrato) {
		try {
			TypedQuery<ACMInterfaceContrato> query =
				entityManager.createQuery(
					"SELECT a"
					+ " FROM ACMInterfaceContrato a"
					+ " WHERE a.mid = :mid",
					ACMInterfaceContrato.class
				);
			query.setParameter("mid", acmInterfaceContrato.getMid());
			
			for (ACMInterfaceContrato acmInterfaceContratoManaged : query.getResultList()) {
				entityManager.remove(acmInterfaceContratoManaged);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeByNumeroContrato(ACMInterfaceContrato acmInterfaceContrato) {
		try {
			TypedQuery<ACMInterfaceContrato> query =
				entityManager.createQuery(
					"SELECT a"
					+ " FROM ACMInterfaceContrato a"
					+ " WHERE a.numeroContrato = :numeroContrato",
					ACMInterfaceContrato.class
				);
			query.setParameter("numeroContrato", acmInterfaceContrato.getNumeroContrato());
			
			for (ACMInterfaceContrato acmInterfaceContratoManaged : query.getResultList()) {
				entityManager.remove(acmInterfaceContratoManaged);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void remove(ACMInterfacePrepago acmInterfacePrepago) {
		try {
			TypedQuery<ACMInterfacePrepago> query =
				entityManager.createQuery(
					"SELECT a"
					+ " FROM ACMInterfacePrepago a"
					+ " WHERE a.mid = :mid",
					ACMInterfacePrepago.class
				);
			query.setParameter("mid", acmInterfacePrepago.getMid());
			
			for (ACMInterfacePrepago acmInterfacePrepagoManaged : query.getResultList()) {
				entityManager.remove(acmInterfacePrepagoManaged);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(ACMInterfaceContrato acmInterfaceContrato) {
		try {
			Date today = GregorianCalendar.getInstance().getTime();
			
			ACMInterfaceEstado estado = 
				entityManager.find(
					ACMInterfaceEstado.class, 
					new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"))
				);
			
			this.remove(acmInterfaceContrato);
			
			acmInterfaceContrato.setFact(today);
			
			entityManager.merge(acmInterfaceContrato);
			
			ACMInterfaceMid acmInterfaceMidManaged = 
				entityManager.find(
					ACMInterfaceMid.class, 
					acmInterfaceContrato.getMid()
				);
			
			acmInterfaceMidManaged.setEstado(estado);
			acmInterfaceMidManaged.setFact(today);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(ACMInterfacePrepago acmInterfacePrepago) {
		try {
			Date today = GregorianCalendar.getInstance().getTime();
			
			ACMInterfaceEstado estado = 
				entityManager.find(
					ACMInterfaceEstado.class, 
					new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"))
				);
			
			this.remove(acmInterfacePrepago);
			
			acmInterfacePrepago.setFact(today);
			
			entityManager.merge(acmInterfacePrepago);
			
			ACMInterfaceMid acmInterfaceMidManaged = 
				entityManager.find(
					ACMInterfaceMid.class, 
					acmInterfacePrepago.getMid()
				);
			
			acmInterfaceMidManaged.setEstado(estado);
			acmInterfaceMidManaged.setFact(today);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(ACMInterfaceMid acmInterfaceMid) {
		try {
			Date today = GregorianCalendar.getInstance().getTime();
			
			ACMInterfaceMid acmInterfaceMidManaged = 
				entityManager.find(
					ACMInterfaceMid.class, 
					acmInterfaceMid.getMid()
				);
			
			acmInterfaceMidManaged.setEstado(acmInterfaceMid.getEstado());
			acmInterfaceMidManaged.setFact(today);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(ACMInterfaceNumeroContrato acmInterfaceNumeroContrato) {
		try {
			Date today = GregorianCalendar.getInstance().getTime();
			
			ACMInterfaceNumeroContrato acmInterfaceNumeroContratoManaged = 
				entityManager.find(
					ACMInterfaceNumeroContrato.class, 
					acmInterfaceNumeroContrato.getNumeroContrato()
				);
			
			acmInterfaceNumeroContratoManaged.setEstado(acmInterfaceNumeroContrato.getEstado());
			acmInterfaceNumeroContratoManaged.setFact(today);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(ACMInterfacePersona acmInterfacePersona, Long mid) {
		try {
			Date today = GregorianCalendar.getInstance().getTime();
			
			ACMInterfacePersona acmInterfacePersonaManaged = 
				iACMInterfacePersonaBean.getByIdCliente(acmInterfacePersona.getIdCliente());
			
			Session hibernateSession = entityManager.unwrap(Session.class);
			
			SQLQuery insertPersonaQuery = hibernateSession.createSQLQuery(
				"INSERT INTO acm_interface_persona("
					+ " id,"
					+ " fcre,"
					+ " fact,"
					+ " ucre,"
					+ " uact,"
					+ " term,"
					+ " actividad,"
					+ " apartamento,"
					+ " apellido,"
					+ " bis,"
					+ " block,"
					+ " calle,"
					+ " codigo_postal,"
					+ " direccion,"
					+ " distribucion,"
					+ " documento,"
					+ " email,"
					+ " esquina,"
					+ " fecha_nacimiento,"
					+ " id_cliente,"
					+ " localidad,"
					+ " manzana,"
					+ " nombre,"
					+ " numero,"
					+ " pais,"
					+ " razon_social,"
					+ " sexo,"
					+ " solar,"
					+ " telefono,"
					+ " tipo_cliente,"
					+ " tipo_documento"
				+ " ) VALUES ("
					+ " nextval('hibernate_sequence'),"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?,"
					+ "	?"
				+ " )"
			);
			
			if (acmInterfacePersonaManaged != null) {
				acmInterfacePersonaManaged.setActividad(acmInterfacePersona.getActividad());
				acmInterfacePersonaManaged.setApartamento(acmInterfacePersona.getApartamento());
				acmInterfacePersonaManaged.setApellido(acmInterfacePersona.getApellido());
				acmInterfacePersonaManaged.setBis(acmInterfacePersona.getBis());
				acmInterfacePersonaManaged.setBlock(acmInterfacePersona.getBlock());
				acmInterfacePersonaManaged.setCalle(acmInterfacePersona.getCalle());
				acmInterfacePersonaManaged.setCodigoPostal(acmInterfacePersona.getCodigoPostal());
				acmInterfacePersonaManaged.setDireccion(acmInterfacePersona.getDireccion());
				acmInterfacePersonaManaged.setDistribucion(acmInterfacePersona.getDistribucion());
				acmInterfacePersonaManaged.setDocumento(acmInterfacePersona.getDocumento());
				acmInterfacePersonaManaged.setEmail(acmInterfacePersona.getEmail());
				acmInterfacePersonaManaged.setEsquina(acmInterfacePersona.getEsquina());
				acmInterfacePersonaManaged.setFechaNacimiento(acmInterfacePersona.getFechaNacimiento());
				acmInterfacePersonaManaged.setIdCliente(acmInterfacePersona.getIdCliente());
				acmInterfacePersonaManaged.setLocalidad(acmInterfacePersona.getLocalidad());
				acmInterfacePersonaManaged.setManzana(acmInterfacePersona.getManzana());
				acmInterfacePersonaManaged.setNombre(acmInterfacePersona.getNombre());
				acmInterfacePersonaManaged.setNumero(acmInterfacePersona.getNumero());
				acmInterfacePersonaManaged.setPais(acmInterfacePersona.getPais());
				acmInterfacePersonaManaged.setRazonSocial(acmInterfacePersona.getRazonSocial());
				acmInterfacePersonaManaged.setSexo(acmInterfacePersona.getSexo());
				acmInterfacePersonaManaged.setSolar(acmInterfacePersona.getSolar());
				acmInterfacePersonaManaged.setTelefono(acmInterfacePersona.getTelefono());
				acmInterfacePersonaManaged.setTipoCliente(acmInterfacePersona.getTipoCliente());
				acmInterfacePersonaManaged.setTipoDocumento(acmInterfacePersona.getTipoDocumento());
				
				acmInterfacePersonaManaged.setFact(today);
				acmInterfacePersonaManaged.setFcre(today);
			} else {
				insertPersonaQuery.setParameter(0, today);
				insertPersonaQuery.setParameter(1, today);
				insertPersonaQuery.setParameter(2, new Long(1));
				insertPersonaQuery.setParameter(3, new Long(1));
				insertPersonaQuery.setParameter(4, new Long(1));
				insertPersonaQuery.setParameter(5, acmInterfacePersona.getActividad());
				insertPersonaQuery.setParameter(6, acmInterfacePersona.getApartamento());
				insertPersonaQuery.setParameter(7, acmInterfacePersona.getApellido());
				insertPersonaQuery.setParameter(8, acmInterfacePersona.getBis());
				insertPersonaQuery.setParameter(9, acmInterfacePersona.getBlock());
				insertPersonaQuery.setParameter(10, acmInterfacePersona.getCalle());
				insertPersonaQuery.setParameter(11, acmInterfacePersona.getCodigoPostal());
				insertPersonaQuery.setParameter(12, acmInterfacePersona.getDireccion());
				insertPersonaQuery.setParameter(13, acmInterfacePersona.getDistribucion());
				insertPersonaQuery.setParameter(14, acmInterfacePersona.getDocumento());
				insertPersonaQuery.setParameter(15, acmInterfacePersona.getEmail());
				insertPersonaQuery.setParameter(16, acmInterfacePersona.getEsquina());
				insertPersonaQuery.setParameter(17, acmInterfacePersona.getFechaNacimiento());
				insertPersonaQuery.setParameter(18, acmInterfacePersona.getIdCliente());
				insertPersonaQuery.setParameter(19, acmInterfacePersona.getLocalidad());
				insertPersonaQuery.setParameter(20, acmInterfacePersona.getManzana());
				insertPersonaQuery.setParameter(21, acmInterfacePersona.getNombre());
				insertPersonaQuery.setParameter(22, acmInterfacePersona.getNumero());
				insertPersonaQuery.setParameter(23, acmInterfacePersona.getPais());
				insertPersonaQuery.setParameter(24, acmInterfacePersona.getRazonSocial());
				insertPersonaQuery.setParameter(25, acmInterfacePersona.getSexo());
				insertPersonaQuery.setParameter(26, acmInterfacePersona.getSolar());
				insertPersonaQuery.setParameter(27, acmInterfacePersona.getTelefono());
				insertPersonaQuery.setParameter(28, acmInterfacePersona.getTipoCliente());
				insertPersonaQuery.setParameter(29, acmInterfacePersona.getTipoDocumento());
				
				insertPersonaQuery.executeUpdate();
			}
			
			SQLQuery deleteMidPersonaQuery = hibernateSession.createSQLQuery(
				"DELETE FROM acm_interface_mid_persona WHERE mid = :mid"
			);
			deleteMidPersonaQuery.setParameter("mid", mid, LongType.INSTANCE);
			
			SQLQuery selectPersonaQuery = hibernateSession.createSQLQuery(
				" SELECT p.id"
				+ " FROM acm_interface_persona p"
				+ " WHERE p.id_cliente = :idCliente"
			);
			selectPersonaQuery.setParameter("idCliente", acmInterfacePersona.getIdCliente(), StringType.INSTANCE);
			
			SQLQuery insertMidPersonaQuery = hibernateSession.createSQLQuery(
				"INSERT INTO acm_interface_mid_persona(mid, persona_id)"
				+ " SELECT :mid, p.id"
				+ " FROM acm_interface_persona p"
				+ " WHERE p.id_cliente = :idCliente"
			);
			insertMidPersonaQuery.setParameter("mid", mid, LongType.INSTANCE);
			insertMidPersonaQuery.setParameter("idCliente", acmInterfacePersona.getIdCliente(), StringType.INSTANCE);
			
			deleteMidPersonaQuery.executeUpdate();
			insertMidPersonaQuery.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosMidListaVacia(Long mid) {
		try {
			ACMInterfaceContrato acmInterfaceContrato = new ACMInterfaceContrato();
			acmInterfaceContrato.setMid(mid);
			
			this.remove(acmInterfaceContrato);
			
			ACMInterfacePrepago acmInterfacePrepago = new ACMInterfacePrepago();
			acmInterfacePrepago.setMid(mid);
			
			this.remove(acmInterfacePrepago);
			
			ACMInterfaceEstado estado = 
				entityManager.find(
					ACMInterfaceEstado.class, 
					new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ListaVacia"))
				);
			
			ACMInterfaceMid acmInterfaceMid = new ACMInterfaceMid();
			acmInterfaceMid.setEstado(estado);
			acmInterfaceMid.setMid(mid);
			
			acmInterfaceMid.setFact(GregorianCalendar.getInstance().getTime());
			acmInterfaceMid.setTerm(new Long(1));
			acmInterfaceMid.setUact(new Long(1));
			
			this.update(acmInterfaceMid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosNumeroContratoListaVacia(Long numeroContrato) {
		try {
			ACMInterfaceContrato acmInterfaceContrato = new ACMInterfaceContrato();
			acmInterfaceContrato.setNumeroContrato(numeroContrato);
			
			this.removeByNumeroContrato(acmInterfaceContrato);
			
			ACMInterfaceEstado estado = 
				entityManager.find(
					ACMInterfaceEstado.class, 
					new Long(Configuration.getInstance().getProperty("acmInterfaceEstado.ListaVacia"))
				);
			
			ACMInterfaceNumeroContrato acmInterfaceNumeroContrato = new ACMInterfaceNumeroContrato();
			acmInterfaceNumeroContrato.setEstado(estado);
			acmInterfaceNumeroContrato.setNumeroContrato(numeroContrato);
			
			acmInterfaceNumeroContrato.setFact(GregorianCalendar.getInstance().getTime());
			acmInterfaceNumeroContrato.setTerm(new Long(1));
			acmInterfaceNumeroContrato.setUact(new Long(1));
			acmInterfaceNumeroContrato.setUcre(new Long(1));
			
			this.update(acmInterfaceNumeroContrato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}