package uy.com.amensg.logistica.robot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import uy.com.amensg.logistica.robot.util.Configuration;

public class ConnectionStrategyDirect implements IConnectionStrategy {

	private Connection connection;
	private PreparedStatement preparedStatementUpdateMid;
	private PreparedStatement preparedStatementDeleteContrato;
	private PreparedStatement preparedStatementDeletePrepago;
	
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
		
		this.preparedStatementDeleteContrato = this.connection.prepareStatement(
			"delete from acm_interface_contrato where mid = ?"
		);
		
		this.preparedStatementDeletePrepago = this.connection.prepareStatement(
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
		this.preparedStatementUpdateMid.setString(5, mid);
		
		this.preparedStatementUpdateMid.executeUpdate();
	}
	
	public String getSiguienteMidSinProcesar() {
		String result = null;
		
		try {
			this.initializeConnection();
			
			PreparedStatement preparedStatementQuery = this.connection.prepareStatement(
				"select mid"
				+ " from acm_interface_mid"
				+ " where estado in ("
					+ "	?, ?"
				+ " )"
				+ " order by estado desc"
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
				result = resultSet.getString(1);
				
				Long estado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.EnProceso"));
				Long uact = new Long(1);
				Date fact = new Date();
				Long term = new Long(1);
				
				this.actualizarACMInterfaceMid(
					estado,
					uact,
					fact,
					term,
					result
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
		String tipoContratoDescripcion
	) {
		try {
			this.initializeConnection();
			
			this.preparedStatementDeleteContrato.setString(1, mid);
			this.preparedStatementDeletePrepago.setString(1, mid);
			
			this.preparedStatementDeleteContrato.executeUpdate();
			this.preparedStatementDeletePrepago.executeUpdate();
			
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
					+ " uact,"
					+ " fact,"
					+ " term,"
					+ " mid"
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
					+ " ?"
				+ ")"
			);
			preparedStatementUpdateContrato.setString(1, direccion);
			preparedStatementUpdateContrato.setLong(2, new Long(documentoTipo));
			preparedStatementUpdateContrato.setString(3, documento);
			preparedStatementUpdateContrato.setString(4, fechaFinContrato);
			preparedStatementUpdateContrato.setString(5, localidad);
			preparedStatementUpdateContrato.setString(6, codigoPostal);
			preparedStatementUpdateContrato.setString(7, nombre);
			preparedStatementUpdateContrato.setString(8, tipoContratoCodigo);
			preparedStatementUpdateContrato.setString(9, tipoContratoDescripcion);
			preparedStatementUpdateContrato.setLong(10, 1);
			preparedStatementUpdateContrato.setTimestamp(11, new Timestamp(new Date().getTime()));
			preparedStatementUpdateContrato.setLong(12, 1);
			preparedStatementUpdateContrato.setString(13, mid);
			
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
		String montoMesAnterior2
	) {
		try {
			this.initializeConnection();
			
			this.preparedStatementDeleteContrato.setString(1, mid);
			this.preparedStatementDeletePrepago.setString(1, mid);
			
			this.preparedStatementDeleteContrato.executeUpdate();
			this.preparedStatementDeletePrepago.executeUpdate();
			
			String montoPromedio = "-1";
			
			try {
				montoPromedio =
					Long.toString(
						(
							((new Long(montoMesActual)
								+ new Long(montoMesAnterior1)
								+ new Long(montoMesAnterior2))) / 3
						)
					);
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
					+ " uact,"
					+ " fact,"
					+ " term,"
					+ " mid"
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
					+ " ?"
				+ ")"
			);
			preparedStatementUpdatePrepago.setString(1, mesAno);
			preparedStatementUpdatePrepago.setString(2, montoMesActual);
			preparedStatementUpdatePrepago.setString(3, montoMesAnterior1);
			preparedStatementUpdatePrepago.setString(4, montoMesAnterior2);
			preparedStatementUpdatePrepago.setString(5, montoPromedio);
			preparedStatementUpdatePrepago.setLong(6, 1);
			preparedStatementUpdatePrepago.setTimestamp(7, new Timestamp(new Date().getTime()));
			preparedStatementUpdatePrepago.setLong(8, 1);
			preparedStatementUpdatePrepago.setString(9, mid);
			
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
			
			this.preparedStatementDeleteContrato.setString(1, mid);
			this.preparedStatementDeletePrepago.setString(1, mid);
			
			this.preparedStatementDeleteContrato.executeUpdate();
			this.preparedStatementDeletePrepago.executeUpdate();
			
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
}