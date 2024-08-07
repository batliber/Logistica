package uy.com.amensg.logistica.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "seguridad_rol")
public class Rol extends BaseEntity {

	private static final long serialVersionUID = -88488084185722876L;

	@Column(name = "nombre")
	private String nombre;

	@ManyToMany
	@JoinTable(name = "seguridad_menu_rol", 
		joinColumns=@JoinColumn(name = "seguridad_rol_id"),
		inverseJoinColumns=@JoinColumn(name = "seguridad_menu_id")
	)
	private Set<Menu> menus;
	
	@OneToMany
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