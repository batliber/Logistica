package uy.com.amensg.logistica.robot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import uy.com.amensg.logistica.robot.util.Configuration;

public class ConnectionStrategyDirect implements IConnectionStrategy {

	private Connection connection;
	private PreparedStatement preparedStatementUpdateMid;
	private PreparedStatement preparedStatementUpdateNumeroContrato;
	private PreparedStatement preparedStatementDeleteContratoByMid;
	private PreparedStatement preparedStatementDeletePrepagoByMid;
	private PreparedStatement preparedStatementDeleteContratoByNumeroContrato;
	
	private void initializeConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		
		this.connection = DriverManager.getConnection(
			Configuration.getInstance().getProperty("db.connectionURL"), 
			Configuration.getInstance().getProperty("db.connectionUser"), 
			Configuration.getInstance().getProperty("db.connectionPassword")
		);
		
		this.connection.setAutoCommit(false);
		
		this.preparedStatementUpdateMid = this.connection.prepareStatement(
			"update acm_interface_mid"
			+ " set estado = ?,"
			+ " uact = ?,"
			+ " fact = ?,"
			+ " term = ?"
			+ " where mid = ?"
		);
		
		this.preparedStatementUpdateNumeroContrato = this.connection.prepareStatement(
			"update acm_interface_numero_contrato"
			+ " set estado = ?,"
			+ " uact = ?,"
			+ " fact = ?,"
			+ " term = ?"
			+ " where numero_contrato = ?"
		);
		
		this.preparedStatementDeleteContratoByMid = this.connection.prepareStatement(
			"delete from acm_interface_contrato where mid = ?"
		);
		
		this.preparedStatementDeleteContratoByNumeroContrato = this.connection.prepareStatement(
			"delete from acm_interface_contrato where numero_contrato = ?"
		);
		
		this.preparedStatementDeletePrepagoByMid = this.connection.prepareStatement(
			"delete from acm_interface_prepago where mid = ?"
		);
	}
	
	private void actualizarACMInterfaceMid(
		Long estado,
		Long uact,
		Date fact,
		Long term,
		String mid
	) throws SQLException {
		this.preparedStatementUpdateMid.setLong(1, estado);
		this.preparedStatementUpdateMid.setLong(2, uact);
		this.preparedStatementUpdateMid.setTimestamp(3, new Timestamp(fact.getTime()));
		this.preparedStatementUpdateMid.setLong(4, term);
		this.preparedStatementUpdateMid.setLong(5, new Long(mid));
		
		this.preparedStatementUpdateMid.executeUpdate();
	}
	
	private void actualizarACMInterfaceNumeroContrato(
		Long estado,
		Long uact,
		Date fact,
		Long term,
		Long numeroContrato
	) throws SQLException {
		this.preparedStatementUpdateNumeroContrato.setLong(1, estado);
		this.preparedStatementUpdateNumeroContrato.setLong(2, uact);
		this.preparedStatementUpdateNumeroContrato.setTimestamp(3, new Timestamp(fact.getTime()));
		this.preparedStatementUpdateNumeroContrato.setLong(4, term);
		this.preparedStatementUpdateNumeroContrato.setLong(5, numeroContrato);
		
		this.preparedStatementUpdateNumeroContrato.executeUpdate();
	}
	
	public String getSiguienteMidSinProcesar() {
		String result = null;
		
		try {
			this.initializeConnection();
			
			PreparedStatement preparedStatementQuery = this.connection.prepareStatement(
				"select mid.mid, contrato.documento, contrato.numero_contrato, random() as ordinal"
				+ " from acm_interface_mid mid"
				+ " left outer join acm_interface_contrato contrato on contrato.mid = mid.mid"
				+ " where estado in ("
					+ "	?, ?"
				+ " )"
				+ " order by mid.estado desc, ordinal asc"
				+ " limit 1"
			);
			preparedStatementQuery.setLong(
				1, new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ParaProcesar"))
			);
			preparedStatementQuery.setLong(
				2, new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ParaProcesarPrioritario"))
			);
			
			ResultSet resultSet = preparedStatementQuery.executeQuery();
			if (resultSet.next()) {
				String mid = new Long(resultSet.getLong(1)).toString();
				String documento = resultSet.getString(2) != null ? resultSet.getString(2) : "";
				String numeroContrato = resultSet.getString(3) != null ? new Long(resultSet.getLong(3)).toString() : "";
				
				if (!documento.equals("")) {
					result = "C " + mid + " " + documento + " " + numeroContrato;
				} else {
					result = "P " + mid;
				}
				
				Long estado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.EnProceso"));
				Long uact = new Long(1);
				Date fact = new Date();
				Long term = new Long(1);
				
				this.actualizarACMInterfaceMid(
					estado,
					uact,
					fact,
					term,
					mid
				);
				
				this.connection.commit();
			}
			
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String getSiguienteNumeroContratoSinProcesar() {
		String result = null;
		
		try {
			this.initializeConnection();
			
			PreparedStatement preparedStatementQuery = this.connection.prepareStatement(
				"select numero_contrato.numero_contrato, random() as ordinal"
				+ " from acm_interface_numero_contrato numero_contrato"
				+ " where estado in ("
					+ "	?, ?"
				+ " )"
				+ " order by numero_contrato.estado desc, ordinal asc"
				+ " limit 1"
			);
			preparedStatementQuery.setLong(
				1, new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ParaProcesar"))
			);
			preparedStatementQuery.setLong(
				2, new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ParaProcesarPrioritario"))
			);
			
			ResultSet resultSet = preparedStatementQuery.executeQuery();
			if (resultSet.next()) {
				Long numeroContrato = resultSet.getLong(1);
				
				result = numeroContrato.toString();
				
				Long estado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.EnProceso"));
				Long uact = new Long(1);
				Date fact = new Date();
				Long term = new Long(1);
				
				this.actualizarACMInterfaceNumeroContrato(
					estado,
					uact,
					fact,
					term,
					numeroContrato
				);
				
				this.connection.commit();
			}
			
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void actualizarDatosMidContrato(
		String direccion, 
		String documentoTipo,
		String documento,
		String fechaFinContrato, 
		String localidad,
		String codigoPostal,
		String mid,
		String nombre, 
		String tipoContratoCodigo,
		String tipoContratoDescripcion,
		String agente,
		String equipo,
		String numeroCliente,
		String numeroContrato,
		String estadoContrato
	) {
		try {
			this.initializeConnection();
			
			this.preparedStatementDeleteContratoByMid.setLong(1, new Long(mid));
			this.preparedStatementDeletePrepagoByMid.setLong(1, new Long(mid));
			
			this.preparedStatementDeleteContratoByMid.executeUpdate();
			this.preparedStatementDeletePrepagoByMid.executeUpdate();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			Random random = new Random();
			
			PreparedStatement preparedStatementUpdateContrato = this.connection.prepareStatement(
				"insert into acm_interface_contrato("
					+ " direccion,"
					+ " documento_tipo,"
					+ " documento,"
					+ " fecha_fin_contrato,"
					+ " localidad,"
					+ " codigo_postal,"
					+ " nombre,"
					+ " tipo_contrato_codigo,"
					+ " tipo_contrato_descripcion,"
					+ " agente,"
					+ " equipo,"
					+ " numero_cliente,"
					+ " numero_contrato,"
					+ " estado_contrato,"
					+ " uact,"
					+ " fact,"
					+ " term,"
					+ " mid,"
					+ " random"
				+ ")"
				+ " values ("
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?"
				+ ")"
			);
			preparedStatementUpdateContrato.setString(1, direccion);
			preparedStatementUpdateContrato.setLong(2, new Long(documentoTipo));
			preparedStatementUpdateContrato.setString(3, documento);
			preparedStatementUpdateContrato.setDate(4, new java.sql.Date(format.parse(fechaFinContrato).getTime()));
			preparedStatementUpdateContrato.setString(5, localidad);
			preparedStatementUpdateContrato.setString(6, codigoPostal);
			preparedStatementUpdateContrato.setString(7, nombre);
			preparedStatementUpdateContrato.setString(8, tipoContratoCodigo);
			preparedStatementUpdateContrato.setString(9, tipoContratoDescripcion);
			preparedStatementUpdateContrato.setString(10, agente);
			preparedStatementUpdateContrato.setString(11, equipo);
			preparedStatementUpdateContrato.setLong(12, new Long(numeroCliente));
			preparedStatementUpdateContrato.setLong(13, new Long(numeroContrato));
			if (estadoContrato != null && !estadoContrato.equals("")) {
				preparedStatementUpdateContrato.setString(14, estadoContrato);
			} else {
				preparedStatementUpdateContrato.setNull(14, Types.VARCHAR);
			}
			
			preparedStatementUpdateContrato.setLong(15, 1);
			preparedStatementUpdateContrato.setTimestamp(16, new Timestamp(new Date().getTime()));
			preparedStatementUpdateContrato.setLong(17, 1);
			preparedStatementUpdateContrato.setLong(18, new Long(mid));
			preparedStatementUpdateContrato.setLong(19, new Long(random.nextInt()));
			
			Long estado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.Procesado"));
			Long uact = new Long(1);
			Date fact = new Date();
			Long term = new Long(1);
			
			this.actualizarACMInterfaceMid(
				estado,
				uact,
				fact,
				term,
				mid
			);
			
			this.actualizarACMInterfaceNumeroContrato(
				estado,
				uact,
				fact,
				term,
				new Long(numeroContrato)
			);
			
			preparedStatementUpdateContrato.executeUpdate();
			
			this.connection.commit();
			
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosMidPrepago(
		String mesAno, 
		String mid,
		String montoMesActual, 
		String montoMesAnterior1,
		String montoMesAnterior2,
		String fechaActivacionKit,
		String agente
	) {
		try {
			this.initializeConnection();
			
			this.preparedStatementDeleteContratoByMid.setLong(1, new Long(mid));
			this.preparedStatementDeletePrepagoByMid.setLong(1, new Long(mid));
			
			this.preparedStatementDeleteContratoByMid.executeUpdate();
			this.preparedStatementDeletePrepagoByMid.executeUpdate();
			
			Date mesAnoDate = null;
			Double montoMesActualDouble = new Double(-1);
			Double montoMesAnterior1Double = new Double(-1);
			Double montoMesAnterior2Double = new Double(-1);
			Double montoPromedio = new Double(-1);
			Date fechaActivacionKitDate = null;
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			Random random = new Random();
			
			try {
				// Parsear los parámetros tipados.
				mesAnoDate = format.parse("01/" + mesAno);
				montoMesActualDouble = new Double(montoMesActual);
				montoMesAnterior1Double = new Double(montoMesAnterior1);
				montoMesAnterior2Double = new Double(montoMesAnterior2);
				montoPromedio = 
					(montoMesActualDouble + montoMesAnterior1Double + montoMesAnterior2Double) / 3;
				fechaActivacionKitDate = format.parse(fechaActivacionKit);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			PreparedStatement preparedStatementUpdatePrepago = this.connection.prepareStatement(
				"insert into acm_interface_prepago("
					+ " mes_ano,"
					+ " monto_mes_actual,"
					+ " monto_mes_anterior_1,"
					+ " monto_mes_anterior_2,"
					+ " monto_promedio,"
					+ " fecha_activacion_kit,"
					+ " agente,"
					+ " uact,"
					+ " fact,"
					+ " term,"
					+ " mid,"
					+ " random"
				+ ")"
				+ " values ("
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?,"
					+ " ?"
				+ ")"
			);
			if (mesAnoDate != null) {
				preparedStatementUpdatePrepago.setDate(1, new java.sql.Date(mesAnoDate.getTime()));
			} else {
				preparedStatementUpdatePrepago.setNull(1, Types.DATE);
			}
			preparedStatementUpdatePrepago.setDouble(2, montoMesActualDouble);
			preparedStatementUpdatePrepago.setDouble(3, montoMesAnterior1Double);
			preparedStatementUpdatePrepago.setDouble(4, montoMesAnterior2Double);
			preparedStatementUpdatePrepago.setDouble(5, montoPromedio);
			if (fechaActivacionKitDate != null) {
				preparedStatementUpdatePrepago.setDate(6, new java.sql.Date(fechaActivacionKitDate.getTime()));
			} else {
				preparedStatementUpdatePrepago.setNull(6, Types.DATE);
			}
			preparedStatementUpdatePrepago.setString(7, agente);
			preparedStatementUpdatePrepago.setLong(8, 1);
			preparedStatementUpdatePrepago.setTimestamp(9, new Timestamp(new Date().getTime()));
			preparedStatementUpdatePrepago.setLong(10, 1);
			preparedStatementUpdatePrepago.setLong(11, new Long(mid));
			preparedStatementUpdatePrepago.setLong(12, new Long(random.nextInt()));
			
			Long estado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.Procesado"));
			Long uact = new Long(1);
			Date fact = new Date();
			Long term = new Long(1);
			
			this.actualizarACMInterfaceMid(
				estado,
				uact,
				fact,
				term,
				mid
			);
			
			preparedStatementUpdatePrepago.executeUpdate();
			
			this.connection.commit();
			
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosMidListaVacia(
		String mid
	) {
		try  {
			this.initializeConnection();
			
			this.preparedStatementDeleteContratoByMid.setLong(1, new Long(mid));
			this.preparedStatementDeletePrepagoByMid.setLong(1, new Long(mid));
			
			this.preparedStatementDeleteContratoByMid.executeUpdate();
			this.preparedStatementDeletePrepagoByMid.executeUpdate();
			
			Long estado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ListaVacia"));
			Long uact = new Long(1);
			Date fact = new Date();
			Long term = new Long(1);
			
			this.actualizarACMInterfaceMid(
				estado,
				uact,
				fact,
				term,
				mid
			);
			
			this.connection.commit();
			
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosNumeroContratoListaVacia(
		String numeroContrato
	) {
		try  {
			this.initializeConnection();
			
			this.preparedStatementDeleteContratoByNumeroContrato.setLong(1, new Long(numeroContrato));
			
			this.preparedStatementDeleteContratoByNumeroContrato.executeUpdate();
			
			Long estado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ListaVacia"));
			Long uact = new Long(1);
			Date fact = new Date();
			Long term = new Long(1);
			
			this.actualizarACMInterfaceNumeroContrato(
				estado,
				uact,
				fact,
				term,
				new Long(numeroContrato)
			);
			
			this.connection.commit();
			
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosPersona(
		String idCliente,
		String mid,
		String pais,
		String tipoDocumento,
		String documento,
		String apellido,
		String nombre,
		String razonSocial,
		String tipoCliente,
		String actividad,
		String fechaNacimiento,
		String sexo,
		String direccionCalle, 
		String direccionNumero,
		String direccionBis,
		String direccionApartamento,
		String direccionEsquina,
		String direccionBlock,
		String direccionManzana,
		String direccionSolar,
		String direccionObservaciones,
		String direccionLocalidad,
		String direccionCodigoPostal,
		String direccionCompleta,
		String distribuidor,
		String telefonosFijo,
		String telefonosAviso,
		String telefonosFax,
		String email) {
		try  {
			DataProcessingTemplateMethod dataProcessingTemplateMethod = new ACMInterfacePersona();
			
			Collection<String> data = new LinkedList<String>();
			data.add(idCliente);
			data.add(mid);
			data.add(pais);
			data.add(tipoDocumento);
			data.add(documento);
			data.add(apellido);
			data.add(nombre);
			data.add(razonSocial);
			data.add(tipoCliente);
			data.add(actividad);
			data.add(fechaNacimiento);
			data.add(sexo);
			data.add(direccionCalle); 
			data.add(direccionNumero);
			data.add(direccionBis);
			data.add(direccionApartamento);
			data.add(direccionEsquina);
			data.add(direccionBlock);
			data.add(direccionManzana);
			data.add(direccionSolar);
//			data.add(direccionObservaciones);
			data.add(direccionLocalidad);
			data.add(direccionCodigoPostal);
			data.add(direccionCompleta);
			data.add(distribuidor);
			data.add(telefonosFijo);
//			data.add(telefonosAviso);
//			data.add(telefonosFax);
			data.add(email);
			
			dataProcessingTemplateMethod.doProcessing(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}