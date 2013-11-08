package uy.com.amensg.logistica.robot.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;

import org.junit.Test;

import uy.com.amensg.logistica.robot.ConnectionStrategyDirect;
import uy.com.amensg.logistica.robot.IConnectionStrategy;
import uy.com.amensg.logistica.robot.util.Configuration;

public class TestLogisticaProxy extends TestCase {

	private Connection connection;
	private IConnectionStrategy strategy;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		strategy = new ConnectionStrategyDirect();
//		strategy = new ConnectionStrategyRemoting();
		
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
				"01/01/2014",
				"localidad",
				"CP",
				mid,
				"nombre",
				"0",
				"tipoContratoDescripcion",
				"agente",
				"equipo"
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
					+ " tipo_contrato_descripcion,"
					+ " agente,"
					+ " equipo"
				+ " from acm_interface_contrato where mid = ?"
			);
			preparedStatement.setString(1, mid);
			
			resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			assertEquals("direccion", resultSet.getString(1));
			assertEquals("1", resultSet.getString(2));
			assertEquals("documento", resultSet.getString(3));
			assertEquals(format.parse("01/01/2014"), resultSet.getDate(4));
			assertEquals("localidad", resultSet.getString(5));
			assertEquals("CP", resultSet.getString(6));
			assertEquals("nombre", resultSet.getString(7));
			assertEquals("0", resultSet.getString(8));
			assertEquals("tipoContratoDescripcion", resultSet.getString(9));
			assertEquals("agente", resultSet.getString(10));
			assertEquals("equipo", resultSet.getString(11));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public final void testActualizarDatosMidPrepago() {
		try {
			String mid = strategy.getSiguienteMidSinProcesar();
			
			strategy.actualizarDatosMidPrepago(
				"01/2013",
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
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			assertEquals(format.parse("01/01/2013"), resultSet.getDate(1));
			assertEquals(new Double("10"), resultSet.getDouble(2));
			assertEquals(new Double("10"), resultSet.getDouble(3));
			assertEquals(new Double("10"), resultSet.getDouble(4));
			assertEquals(new Double("10"), resultSet.getDouble(5));
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