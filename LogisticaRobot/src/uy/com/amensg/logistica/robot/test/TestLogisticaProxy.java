package uy.com.amensg.logistica.robot.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import junit.framework.TestCase;

import org.junit.Test;

import uy.com.amensg.logistica.robot.ConnectionStrategyRemoting;
import uy.com.amensg.logistica.robot.IConnectionStrategy;
import uy.com.amensg.logistica.robot.util.Configuration;

public class TestLogisticaProxy extends TestCase {

	private Connection connection;
	private IConnectionStrategy strategy;
	
	protected void setUp() throws Exception {
		super.setUp();
		
//		strategy = new ConnectionStrategyDirect();
		strategy = new ConnectionStrategyRemoting();
		
		Class.forName("org.postgresql.Driver");
		
		this.connection = DriverManager.getConnection(
				Configuration.getInstance().getProperty("db.connectionURL"), 
				Configuration.getInstance().getProperty("db.connectionUser"), 
				Configuration.getInstance().getProperty("db.connectionPassword")
			);
			
		this.connection.setAutoCommit(true);
	}

	@Test
	public final void testGetSiguienteMidSinProcesar() {
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(
				"select mid from acm_interface_mid where estado in (?, ?) order by estado desc"
			);
			preparedStatement.setLong(1, new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ParaProcesar")));
			preparedStatement.setLong(2, new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ParaProcesarPrioritario")));
			preparedStatement.setMaxRows(1);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
			
			String expectedMidSinProcesar = resultSet.getString(1);
			
			assertEquals(expectedMidSinProcesar, strategy.getSiguienteMidSinProcesar());
			
			preparedStatement = this.connection.prepareStatement(
				"select mid from acm_interface_mid where estado = ? order by fact desc"
			);
			preparedStatement.setLong(1, new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.EnProceso")));
			
			resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
			
			String expectedMidEnProceso = resultSet.getString(1);
			
			assertEquals(expectedMidEnProceso, expectedMidSinProcesar);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public final void testActualizarDatosMidContrato() {
		try {
			String mid = strategy.getSiguienteMidSinProcesar();
			
			strategy.actualizarDatosMidContrato(
				"direccion",
				"1",
				"documento",
				"fechaFinContrato",
				"localidad",
				"CP",
				mid,
				"nombre",
				"TCC",
				"tipoContratoDescripcion"
			);
			
			PreparedStatement preparedStatement = this.connection.prepareStatement(
				"select mid, estado from acm_interface_mid where mid = ?"
			);
			preparedStatement.setString(1, mid);
			
			Long expectedEstado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.Procesado"));
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
			
			assertEquals(expectedEstado, new Long(resultSet.getLong(2)));
			
			preparedStatement = this.connection.prepareStatement(
				"select direccion, documento_tipo, documento,"
					+ " fecha_fin_contrato, localidad, codigo_postal, nombre,"
					+ " tipo_contrato_codigo,"
					+ " tipo_contrato_descripcion"
				+ " from acm_interface_contrato where mid = ?"
			);
			preparedStatement.setString(1, mid);
			
			resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
			
			assertEquals("direccion", resultSet.getString(1));
			assertEquals("1", resultSet.getString(2));
			assertEquals("documento", resultSet.getString(3));
			assertEquals("fechaFinContrato", resultSet.getString(4));
			assertEquals("localidad", resultSet.getString(5));
			assertEquals("CP", resultSet.getString(6));
			assertEquals("nombre", resultSet.getString(7));
			assertEquals("TCC", resultSet.getString(8));
			assertEquals("tipoContratoDescripcion", resultSet.getString(9));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public final void testActualizarDatosMidPrepago() {
		try {
			String mid = strategy.getSiguienteMidSinProcesar();
			
			strategy.actualizarDatosMidPrepago(
				"mesAno",
				mid,
				"10",
				"10",
				"10"
			);
			
			PreparedStatement preparedStatement = this.connection.prepareStatement(
				"select mid, estado from acm_interface_mid where mid = ?"
			);
			preparedStatement.setString(1, mid);
			
			Long expectedEstado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.Procesado"));
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
			
			assertEquals(expectedEstado, new Long(resultSet.getLong(2)));
			
			preparedStatement = this.connection.prepareStatement(
				"select mes_ano, monto_mes_actual, monto_mes_anterior_1,"
					+ " monto_mes_anterior_2, monto_promedio"
				+ " from acm_interface_prepago where mid = ?"
			);
			preparedStatement.setString(1, mid);
			
			resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
			
			assertEquals("mesAno", resultSet.getString(1));
			assertEquals("10", resultSet.getString(2));
			assertEquals("10", resultSet.getString(3));
			assertEquals("10", resultSet.getString(4));
			assertEquals("10", resultSet.getString(5));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public final void testActualizarDatosMidListaVacia() {
		try {
			String mid = strategy.getSiguienteMidSinProcesar();
			
			strategy.actualizarDatosMidListaVacia(mid);
			
			PreparedStatement preparedStatement = this.connection.prepareStatement(
				"select mid, estado from acm_interface_mid where mid = ?"
			);
			preparedStatement.setString(1, mid);
			
			Long expectedEstado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ListaVacia"));
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
			
			assertEquals(expectedEstado, new Long(resultSet.getLong(2)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		
		this.connection.close();
	}
}