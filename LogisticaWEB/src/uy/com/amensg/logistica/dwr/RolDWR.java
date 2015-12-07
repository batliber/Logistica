package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.IRolBean;
import uy.com.amensg.logistica.bean.RolBean;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.RolTO;

@RemoteProxy
public class RolDWR {

	private IRolBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = RolBean.class.getSimpleName();
		String remoteInterfaceName = IRolBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IRolBean) context.lookup(lookupName);
	}
	
	public Collection<RolTO> list() {
		Collection<RolTO> result = new LinkedList<RolTO>();
		
		try {
			IRolBean iRolBean = lookupBean();
			
			for (Rol rol : iRolBean.list()) {
				result.add(transform(rol));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static RolTO transform(Rol rol) {
		RolTO rolTO = new RolTO();
		
		rolTO.setNombre(rol.getNombre());
		
		rolTO.setFact(rol.getFact());
		rolTO.setId(rol.getId());
		rolTO.setTerm(rol.getTerm());
		rolTO.setUact(rol.getUact());
		
		return rolTO;
	}
}