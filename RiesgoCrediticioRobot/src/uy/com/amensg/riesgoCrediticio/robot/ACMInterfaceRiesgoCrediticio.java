package uy.com.amensg.riesgoCrediticio.robot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

public class ACMInterfaceRiesgoCrediticio extends DataProcessingTemplateMethod {

	protected Collection<Object> parseParameters(Collection<String> data) {
		Collection<Object> result = new LinkedList<Object>();
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		Iterator<String> iterator = data.iterator();
		
		// Parsear los parámetros tipados.
		
		// empresaId
		try {
			result.add(new Long(iterator.next()));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// documento
		result.add(iterator.next());
		
		// fechaCelular
		try {
			result.add(format.parse(iterator.next()));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// deudaCelular
		try {
			result.add(iterator.next().equals("S"));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// riesgoCrediticioCelular
		try {
			result.add(iterator.next().equals("S"));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// contratosCelular
		try {
			result.add(new Long(iterator.next()));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// contratosSolaFirmaCelular
		try {
			result.add(new Long("0" + iterator.next()));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// contratosGarantiaCelular
		try {
			result.add(new Long("0" + iterator.next()));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// saldoAyudaEconomicaCelular
		try {
			result.add(new Double("0" + iterator.next().replace(",", ".")).doubleValue());
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// numeroClienteFijo
		try {
			result.add(new Long(iterator.next()));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// nombreClienteFijo
		result.add(iterator.next());
		
		// estadoDeudaClienteFijo
		result.add(iterator.next());
		
		// numeroClienteMovil
		try {
			result.add(new Long(iterator.next()));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		return result;
	}

	protected boolean checkExistence(Collection<Object> parsedParameters) throws SQLException {
		boolean result = false;
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(
			"SELECT COUNT(0)"
			+ " FROM acm_interface_riesgo_crediticio"
			+ " WHERE empresa_id = ?"
			+ " AND documento = ?"
		);
		
		Iterator<Object> iterator = parsedParameters.iterator();
		
		Long empresaId = (Long) iterator.next();
		String documento = (String) iterator.next();
		
		preparedStatement.setLong(1, empresaId);
		preparedStatement.setString(2, documento);
		
		Long count = null;
		
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			count = resultSet.getLong(1);
			
			result = count >= 1;
		}
		
		return result;
	}

	protected PreparedStatement prepareStatementForInsert(Collection<Object> parsedParameters) throws SQLException {
		PreparedStatement result = this.connection.prepareStatement(
			"INSERT INTO acm_interface_riesgo_crediticio(documento, fecha_celular, deuda_celular, riesgo_crediticio_celular, contratos_celular,"
				+ " contratos_sola_firma_celular, contratos_garantia_celular, saldo_ayuda_economica_celular, numero_cliente_fijo,"
				+ " nombre_cliente_fijo, estado_deuda_cliente_fijo, numero_cliente_movil, uact, fact, term)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
		);
		
		Iterator<Object> iterator = parsedParameters.iterator();
		int parameterIndex = 1;
		
		Long empresaId = (Long) iterator.next();
		
		String documento = (String) iterator.next();
		if (documento != null) {
			result.setString(parameterIndex++, documento);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		Date fechaCelularParsed = (Date) iterator.next();
		if (fechaCelularParsed != null) {
			result.setDate(parameterIndex++, new java.sql.Date(fechaCelularParsed.getTime()));
		} else {
			result.setNull(parameterIndex++, Types.TIMESTAMP);
		}
		
		Boolean deudaCelularParsed = (Boolean) iterator.next();
		if (deudaCelularParsed != null) {
			result.setBoolean(parameterIndex++, deudaCelularParsed);
		} else {
			result.setNull(parameterIndex++, Types.BOOLEAN);
		}
		
		Boolean riesgoCrediticioCelularParsed = (Boolean) iterator.next();
		if (riesgoCrediticioCelularParsed != null) {
			result.setBoolean(parameterIndex++, riesgoCrediticioCelularParsed);
		} else {
			result.setNull(parameterIndex++, Types.BOOLEAN);
		}
		
		Long contratosCelularParsed = (Long) iterator.next();
		if (contratosCelularParsed != null) {
			result.setLong(parameterIndex++, contratosCelularParsed);
		} else {
			result.setNull(parameterIndex++, Types.BIGINT);
		}
		
		Long contratosSolaFirmaCelularParsed = (Long) iterator.next();
		if (contratosSolaFirmaCelularParsed != null) {
			result.setLong(parameterIndex++, contratosSolaFirmaCelularParsed);
		} else {
			result.setNull(parameterIndex++, Types.BIGINT);
		}
		
		Long contratosGarantiaCelularParsed = (Long) iterator.next();
		if (contratosGarantiaCelularParsed != null) {
			result.setLong(parameterIndex++, contratosGarantiaCelularParsed);
		} else {
			result.setNull(parameterIndex++, Types.BIGINT);
		}
		
		Double saldoAyudaEconomicaCelularParsed = (Double) iterator.next();
		if (saldoAyudaEconomicaCelularParsed != null) {
			result.setDouble(parameterIndex++, saldoAyudaEconomicaCelularParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		Long numeroClienteFijoParsed = (Long) iterator.next();
		if (numeroClienteFijoParsed != null) {
			result.setLong(parameterIndex++, numeroClienteFijoParsed);
		} else {
			result.setNull(parameterIndex++, Types.BIGINT);
		}
		
		String nombreClienteFijo = (String) iterator.next();
		result.setString(parameterIndex++, nombreClienteFijo);
		
		String estadoDeudaClienteFijo = (String) iterator.next();
		result.setString(parameterIndex++, estadoDeudaClienteFijo);
		
		Long numeroClienteMovilParsed = (Long) iterator.next();
		if (numeroClienteMovilParsed != null) {
			result.setLong(parameterIndex++, numeroClienteMovilParsed);
		} else {
			result.setNull(parameterIndex++, Types.BIGINT);
		}
		
		result.setLong(parameterIndex++, new Long(1));
		result.setTimestamp(parameterIndex++, new Timestamp(new Date().getTime()));
		result.setLong(parameterIndex++, new Long(1));
		
		return result;
	}

	protected PreparedStatement prepareStatementForUpdate(Collection<Object> parsedParameters) throws SQLException {
		PreparedStatement result = this.connection.prepareStatement(
			"UPDATE acm_interface_riesgo_crediticio"
			   + " SET fecha_celular = ?,"
			   + " deuda_celular = ?,"
			   + " riesgo_crediticio_celular = ?,"
			   + " contratos_celular = ?,"
			   + " contratos_sola_firma_celular = ?,"
			   + " contratos_garantia_celular = ?,"
			   + " saldo_ayuda_economica_celular = ?,"
			   + " numero_cliente_fijo = ?,"
			   + " nombre_cliente_fijo = ?,"
			   + " estado_deuda_cliente_fijo = ?,"
			   + " numero_cliente_movil = ?,"
			   + " uact = ?,"
			   + " fact = ?,"
			   + " term = ?"
			+ " WHERE documento = ?"
		);
		
		Iterator<Object> iterator = parsedParameters.iterator();
		int parameterIndex = 1;
		
		Long empresaId = (Long) iterator.next();
		String documento = (String) iterator.next();
		
		Date fechaCelularParsed = (Date) iterator.next();
		if (fechaCelularParsed != null) {
			result.setDate(parameterIndex++, new java.sql.Date(fechaCelularParsed.getTime()));
		} else {
			result.setNull(parameterIndex++, Types.TIMESTAMP);
		}
		
		Boolean deudaCelularParsed = (Boolean) iterator.next();
		if (deudaCelularParsed != null) {
			result.setBoolean(parameterIndex++, deudaCelularParsed);
		} else {
			result.setNull(parameterIndex++, Types.BOOLEAN);
		}
		
		Boolean riesgoCrediticioCelularParsed = (Boolean) iterator.next();
		if (riesgoCrediticioCelularParsed != null) {
			result.setBoolean(parameterIndex++, riesgoCrediticioCelularParsed);
		} else {
			result.setNull(parameterIndex++, Types.BOOLEAN);
		}
		
		Long contratosCelularParsed = (Long) iterator.next();
		if (contratosCelularParsed != null) {
			result.setLong(parameterIndex++, contratosCelularParsed);
		} else {
			result.setNull(parameterIndex++, Types.BIGINT);
		}
		
		Long contratosSolaFirmaCelularParsed = (Long) iterator.next();
		if (contratosSolaFirmaCelularParsed != null) {
			result.setLong(parameterIndex++, contratosSolaFirmaCelularParsed);
		} else {
			result.setNull(parameterIndex++, Types.BIGINT);
		}
		
		Long contratosGarantiaCelularParsed = (Long) iterator.next();
		if (contratosGarantiaCelularParsed != null) {
			result.setLong(parameterIndex++, contratosGarantiaCelularParsed);
		} else {
			result.setNull(parameterIndex++, Types.BIGINT);
		}
		
		Double saldoAyudaEconomicaCelularParsed = (Double) iterator.next();
		if (saldoAyudaEconomicaCelularParsed != null) {
			result.setDouble(parameterIndex++, saldoAyudaEconomicaCelularParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		Long numeroClienteFijoParsed = (Long) iterator.next();
		if (numeroClienteFijoParsed != null) {
			result.setLong(parameterIndex++, numeroClienteFijoParsed);
		} else {
			result.setNull(parameterIndex++, Types.BIGINT);
		}
		
		String nombreClienteFijo = (String) iterator.next();
		result.setString(parameterIndex++, nombreClienteFijo);
		
		String estadoDeudaClienteFijo = (String) iterator.next();
		result.setString(parameterIndex++, estadoDeudaClienteFijo);
		
		Long numeroClienteMovilParsed = (Long) iterator.next();
		if (numeroClienteMovilParsed != null) {
			result.setLong(parameterIndex++, numeroClienteMovilParsed);
		} else {
			result.setNull(parameterIndex++, Types.BIGINT);
		}
		
		result.setLong(parameterIndex++, new Long(1));
		result.setTimestamp(parameterIndex++, new Timestamp(new Date().getTime()));
		result.setLong(parameterIndex++, new Long(1));
		
		if (documento != null) {
			result.setString(parameterIndex++, documento);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		return result;
	}
}