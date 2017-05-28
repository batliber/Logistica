package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Plan;

@Remote
public interface IPlanBean {

<<<<<<< HEAD
	public Collection<Plan> list();
	
	public Collection<Plan> listVigentes();
=======
public Collection<Plan> list();
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
	
	public Plan getById(Long id);
	
	public void save(Plan plan);
	
	public void remove(Plan plan);
	
	public void update(Plan plan);
}