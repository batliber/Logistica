package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class RiesgoCrediticioTO extends BaseTO {

	private String documento;
	private Date fechaImportacion;
	private Date fechaVigenciaDesde;
	private EmpresaTO empresa;
	private EstadoRiesgoCrediticioTO estadoRiesgoCrediticio;
	private TipoControlRiesgoCrediticioTO tipoControlRiesgoCrediticio;
	private CalificacionRiesgoCrediticioAntelTO calificacionRiesgoCrediticioAntel;
	private CalificacionRiesgoCrediticioBCUTO calificacionRiesgoCrediticioBCU;

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Date getFechaImportacion() {
		return fechaImportacion;
	}

	public void setFechaImportacion(Date fechaImportacion) {
		this.fechaImportacion = fechaImportacion;
	}

	public Date getFechaVigenciaDesde() {
		return fechaVigenciaDesde;
	}

	public void setFechaVigenciaDesde(Date fechaVigenciaDesde) {
		this.fechaVigenciaDesde = fechaVigenciaDesde;
	}

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}

	public EstadoRiesgoCrediticioTO getEstadoRiesgoCrediticio() {
		return estadoRiesgoCrediticio;
	}

	public void setEstadoRiesgoCrediticio(EstadoRiesgoCrediticioTO estadoRiesgoCrediticio) {
		this.estadoRiesgoCrediticio = estadoRiesgoCrediticio;
	}

	public TipoControlRiesgoCrediticioTO getTipoControlRiesgoCrediticio() {
		return tipoControlRiesgoCrediticio;
	}

	public void setTipoControlRiesgoCrediticio(TipoControlRiesgoCrediticioTO tipoControlRiesgoCrediticio) {
		this.tipoControlRiesgoCrediticio = tipoControlRiesgoCrediticio;
	}

	public CalificacionRiesgoCrediticioAntelTO getCalificacionRiesgoCrediticioAntel() {
		return calificacionRiesgoCrediticioAntel;
	}

	public void setCalificacionRiesgoCrediticioAntel(
			CalificacionRiesgoCrediticioAntelTO calificacionRiesgoCrediticioAntel) {
		this.calificacionRiesgoCrediticioAntel = calificacionRiesgoCrediticioAntel;
	}

	public CalificacionRiesgoCrediticioBCUTO getCalificacionRiesgoCrediticioBCU() {
		return calificacionRiesgoCrediticioBCU;
	}

	public void setCalificacionRiesgoCrediticioBCU(CalificacionRiesgoCrediticioBCUTO calificacionRiesgoCrediticioBCU) {
		this.calificacionRiesgoCrediticioBCU = calificacionRiesgoCrediticioBCU;
	}
}