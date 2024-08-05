package uy.com.amensg.logistica.bean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import uy.com.amensg.logistica.entities.FormatoImportacionArchivo;

@Stateless
public class FormatoImportacionArchivoBean implements IFormatoImportacionArchivoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
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