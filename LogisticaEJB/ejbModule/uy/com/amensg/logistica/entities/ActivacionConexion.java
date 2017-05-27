package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activacion_conexion")
public class ActivacionConexion extends BaseEntity {

	private static final long serialVersionUID = -5016422292775137002L;

	@Column(name = "login")
	private String login;
	
	@Column(name = "password")
	private String password;

	@Column(name = "agente")
	private String agente;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}
	
	public String getAgente() {
		return agente;
	}

	public void setAgente(String agente) {
		this.agente = agente;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}