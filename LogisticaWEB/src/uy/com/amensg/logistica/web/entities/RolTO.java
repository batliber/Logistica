package uy.com.amensg.logistica.entities;

import java.util.Collection;

public class RolTO extends BaseTO {

	private String nombre;
	private Collection<MenuTO> menus;
	private Collection<RolTO> subordinados;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<MenuTO> getMenus() {
		return menus;
	}

	public void setMenus(Collection<MenuTO> menus) {
		this.menus = menus;
	}

	public Collection<RolTO> getSubordinados() {
		return subordinados;
	}

	public void setSubordinados(Collection<RolTO> subordinados) {
		this.subordinados = subordinados;
	}
}