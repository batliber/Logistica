package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "seguridad_menu")
public class Menu extends BaseEntity {

	private static final long serialVersionUID = 5284482722583748958L;

	@Column(name = "titulo")
	private String titulo;

	@Column(name = "url")
	private String url;

	@Column(name = "orden")
	private Long orden;
	
	@Column(name = "padre")
	private Long padre;
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Long getPadre() {
		return padre;
	}

	public void setPadre(Long padre) {
		this.padre = padre;
	}
}