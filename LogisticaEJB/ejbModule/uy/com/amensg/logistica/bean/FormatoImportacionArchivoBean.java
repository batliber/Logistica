package uy.com.amensg.logistica.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import uy.com.amensg.logistica.entities.FormatoImportacionArchivo;

@Stateless
public class FormatoImportacionArchivoBean implements IFormatoImportacionArchivoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public FormatoImportacionArchivo getById(Long id) {
		FormatoImportacionArchivo result = null;
		
		try {
			result = entityManager.find(FormatoImportacionArchivo.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}