package uy.com.amensg.logistica.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.RecargaPuntoVentaUsuario;
import uy.com.amensg.logistica.entities.Usuario;

@Stateless
public class RecargaPuntoVentaUsuarioBean implements IRecargaPuntoVentaUsuarioBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public PuntoVenta getPuntoVentaByUsuario(Usuario usuario) {
		PuntoVenta result = null;
		
		try {
			TypedQuery<PuntoVenta> query =
				entityManager.createQuery(
					"SELECT rpvu.puntoVenta"
					+ " FROM RecargaPuntoVentaUsuario rpvu"
					+ " WHERE rpvu.usuario = :usuario",
					PuntoVenta.class
				);
			query.setParameter("usuario", usuario);
			
			List<PuntoVenta> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public RecargaPuntoVentaUsuario update(RecargaPuntoVentaUsuario recargaPuntoVentaUsuario) {
		RecargaPuntoVentaUsuario result = null;
		
		try {
			Query deleteQuery = entityManager.createQuery(
				"DELETE FROM RecargaPuntoVentaUsuario rpvu"
				+ " WHERE rpvu.usuario = :usuario"
			);
			deleteQuery.setParameter("usuario", recargaPuntoVentaUsuario.getUsuario());
			
			deleteQuery.executeUpdate();
				
			if (recargaPuntoVentaUsuario.getPuntoVenta() != null) {
				recargaPuntoVentaUsuario.setUcre(recargaPuntoVentaUsuario.getUact());
				recargaPuntoVentaUsuario.setFcre(recargaPuntoVentaUsuario.getFact());
				
				entityManager.persist(recargaPuntoVentaUsuario);
				
				result = recargaPuntoVentaUsuario;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}