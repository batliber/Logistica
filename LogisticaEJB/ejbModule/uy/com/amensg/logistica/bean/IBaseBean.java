package uy.com.amensg.logistica.bean;

import java.io.Serializable;
import java.util.Collection;

public interface IBaseBean<T> {

	public Collection<Serializable> list(Class type);
	
	public Serializable getById(Long id, Class type);
}