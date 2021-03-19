package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.Precio;
import uy.com.amensg.logistica.entities.TipoProducto;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class PrecioBean implements IPrecioBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	@EJB
	private IUsuarioBean iUsuarioBean;
	
	public Collection<Precio> listPreciosActuales() {
		Collection<Precio> result = new LinkedList<Precio>();
		
		try {
			TypedQuery<Precio> query = entityManager.createQuery(
				"SELECT p"
				+ " FROM Precio p"
				+ " WHERE p.fechaHasta IS NULL",
				Precio.class
			);
			
			for (Precio precio : query.getResultList()) {
				result.add(precio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Collection<Precio> listPreciosActualesByEmpresaId(Long empresaId) {
		Collection<Precio> result = new LinkedList<Precio>();
		
		try {
			TypedQuery<Precio> query = entityManager.createQuery(
				"SELECT p"
				+ " FROM Precio p"
				+ " WHERE p.fechaHasta IS NULL"
				+ " AND p.empresa.id = :empresaId",
				Precio.class
			);
			query.setParameter("empresaId", empresaId);
			
			for (Precio precio : query.getResultList()) {
				result.add(precio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public MetadataConsultaResultado listPreciosActuales(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			Usuario usuario = iUsuarioBean.getById(usuarioId, true);
			
			Collection<String> empresas = new LinkedList<String>();
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
				if (!empresas.contains(usuarioRolEmpresa.getEmpresa().getId().toString())) {
					empresas.add(usuarioRolEmpresa.getEmpresa().getId().toString());
				}
			}
			
			MetadataCondicion metadataCondicion = new MetadataCondicion();
			metadataCondicion.setCampo("empresa.id");
			metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO);
			metadataCondicion.setValores(empresas);
			
			metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
			
			metadataCondicion = new MetadataCondicion();
			metadataCondicion.setCampo("fechaHasta");
			metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NULL);
			metadataCondicion.setValores(new LinkedList<String>());
			
			metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
			
			return new QueryBuilder<Precio>().list(entityManager, metadataConsulta, new Precio());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long countPreciosActuales(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			Usuario usuario = iUsuarioBean.getById(usuarioId, true);
			
			Collection<String> empresas = new LinkedList<String>();
			for (UsuarioRolEmpresa usuarioRolEmpresa : usuario.getUsuarioRolEmpresas()) {
				if (!empresas.contains(usuarioRolEmpresa.getEmpresa().getId().toString())) {
					empresas.add(usuarioRolEmpresa.getEmpresa().getId().toString());
				}
			}
			
			MetadataCondicion metadataCondicion = new MetadataCondicion();
			metadataCondicion.setCampo("empresa.id");
			metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_INCLUIDO);
			metadataCondicion.setValores(empresas);
			
			metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
			
			metadataCondicion = new MetadataCondicion();
			metadataCondicion.setCampo("fechaHasta");
			metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NULL);
			metadataCondicion.setValores(new LinkedList<String>());
			
			metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
			
			result = new QueryBuilder<Precio>().count(entityManager, metadataConsulta, new Precio());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Precio getById(Long id) {
		Precio result = null;
		
		try {
			result = entityManager.find(Precio.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Precio getActualByEmpresaTipoProductoMarcaModeloMonedaCuotas(
		Empresa empresa, 
		TipoProducto tipoProducto, 
		Marca marca, 
		Modelo modelo, 
		Moneda moneda,
		Long cuotas
	) {
		Precio result = null;
		
		try {
			TypedQuery<Precio> query = entityManager.createQuery(
				"SELECT p"
				+ " FROM Precio p"
				+ " WHERE p.fechaHasta IS NULL"
				+ " AND p.empresa.id = :empresaId"
				+ " AND p.tipoProducto.id = :tipoProductoId"
				+ " AND p.marca.id = :marcaId"
				+ " AND p.modelo.id = :modeloId"
				+ " AND p.moneda.id = :monedaId"
				+ " AND p.cuotas = :cuotas",
				Precio.class
			);
			query.setParameter("empresaId", empresa.getId());
			query.setParameter("tipoProductoId", tipoProducto.getId());
			query.setParameter("marcaId", marca.getId());
			query.setParameter("modeloId", modelo.getId());
			query.setParameter("monedaId", moneda.getId());
			query.setParameter("cuotas", cuotas);
			
			List<Precio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Precio save(Precio precio) {
		Precio result = null;
		
		try {
			precio.setFcre(precio.getFact());
			precio.setUcre(precio.getUact());
			
			entityManager.persist(precio);
			
			result = precio;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void update(Precio precio) {
		try {
			Precio precioManaged = entityManager.find(Precio.class, precio.getId());
			
			precioManaged.setFechaHasta(GregorianCalendar.getInstance().getTime());
			
			precioManaged.setFact(precio.getFact());
			precioManaged.setTerm(precio.getTerm());
			precioManaged.setUact(precio.getUact());
			
			entityManager.merge(precioManaged);
			
			precio.setId(null);
			precio.setFcre(precio.getFact());
			precio.setUcre(precio.getUact());
			
			entityManager.persist(precio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}