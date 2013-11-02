package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.IUsuarioBean;
import uy.com.amensg.logistica.bean.UsuarioBean;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioTO;
import uy.com.amensg.logistica.util.MD5Utils;

@RemoteProxy
public class UsuarioDWR {

	private IUsuarioBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = UsuarioBean.class.getSimpleName();
		String remoteInterfaceName = IUsuarioBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IUsuarioBean) context.lookup(lookupName);
	}
	
	public Collection<UsuarioTO> list() {
		Collection<UsuarioTO> result = new LinkedList<UsuarioTO>();
		
		try {
			IUsuarioBean iUsuarioBean = lookupBean();
			
			for (Usuario usuario : iUsuarioBean.list()) {
				result.add(this.transform(usuario));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public UsuarioTO getById(Long id) {
		UsuarioTO result = null;
		
		try {
			IUsuarioBean iUsuarioBean = lookupBean();
			
			result = this.transform(iUsuarioBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public UsuarioTO getByLogin(String login) {
		UsuarioTO result = null;
		
		try {
			IUsuarioBean iUsuarioBean = lookupBean();
			
			result = this.transform(iUsuarioBean.getByLogin(login));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(UsuarioTO usuarioTO) {
		this.update(usuarioTO);
	}

	public void remove(UsuarioTO usuarioTO) {
		try {
			IUsuarioBean iUsuarioBean = lookupBean();
			
			Usuario usuario = new Usuario();
			usuario.setId(usuarioTO.getId());
			
			iUsuarioBean.remove(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(UsuarioTO usuarioTO) {
		try {
			IUsuarioBean iUsuarioBean = lookupBean();
			
			Usuario usuario = new Usuario();
			
			usuario.setLogin(usuarioTO.getLogin());
			
			usuario.setContrasena(MD5Utils.stringToMD5(usuarioTO.getContrasena()));
			usuario.setNombre(usuarioTO.getNombre());
			
			usuario.setId(usuarioTO.getId());
			usuario.setFact(usuarioTO.getFact());
			usuario.setTerm(usuarioTO.getTerm());
			usuario.setUact(usuarioTO.getUact());
			
			iUsuarioBean.update(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private UsuarioTO transform(Usuario usuario) {
		UsuarioTO usuarioTO = new UsuarioTO();
		
		usuarioTO.setLogin(usuario.getLogin());
		usuarioTO.setContrasena(usuario.getContrasena());
		usuarioTO.setNombre(usuario.getNombre());

		usuarioTO.setFact(usuario.getFact());
		usuarioTO.setId(usuario.getId());
		usuarioTO.setTerm(usuario.getTerm());
		usuarioTO.setUact(usuario.getUact());
		
		return usuarioTO;
	}
}