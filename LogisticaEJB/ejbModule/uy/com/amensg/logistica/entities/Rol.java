package uy.com.amensg.logistica.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "seguridad_rol")
public class Rol extends BaseEntity {

	private static final long serialVersionUID = -88488084185722876L;

	@Column(name = "nombre")
	private String nombre;

	@ManyToMany( fetch=FetchType.EAGER )
	@JoinTable(name = "seguridad_menu_rol", 
		joinColumns=@JoinColumn(name = "seguridad_rol_id"),
		inverseJoinColumns=@JoinColumn(name = "seguridad_menu_id")
	)
	private Set<Menu> menus;
	
	@OneToMany( fetch=FetchType.EAGER )
	@JoinTable(name = "seguridad_rol_jerarquia", 
		joinColumns=@JoinColumn(name = "rol_id"),
		inverseJoinColumns=@JoinColumn(name = "rol_subordinado_id")
	)
	private Set<Rol> subordinados;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	public Set<Rol> getSubordinados() {
		return subordinados;
	}
	
	public void setSubordinados(Set<Rol> subordinados) {
		this.subordinados = subordinados;
	}
}