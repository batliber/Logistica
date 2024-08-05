package uy.com.amensg.riesgoCrediticio.robot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

public class BCUInterfaceRiesgoCrediticioInstitucionFinanciera extends DataProcessingTemplateMethod {

	protected Collection<Object> parseParameters(Collection<String> data) {
		Collection<Object> result = new LinkedList<Object>();
		
		Iterator<String> iterator = data.iterator();
		
		// Parsear los par�metros tipados.
		
		// empresaId
		try {
			result.add(Long.decode(iterator.next()));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// documento
		result.add(iterator.next());
		
		// institucionFinanciera
		result.add(iterator.next());
		
		// calificacion
		result.add(iterator.next());
		
		// vigente
		try {
			result.add(Double.parseDouble("0" + iterator.next().replace(",", ".")));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// vigenteNoAutoLiquidable
		try {
			result.add(Double.parseDouble("0" + iterator.next().replace(",", ".")));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// previsionesTotales
		try {
			result.add(Double.parseDouble("0" + iterator.next().replace(",", ".")));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// contingencias
		try {
			result.add(Double.parseDouble("0" + iterator.next().replace(",", ".")));
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
			+ " FROM bcu_interface_riesgo_crediticio_institucion_financiera"
			+ " WHERE empresa_id = ? documento = ? AND institucion_financiera = ?" 
		);
		
		Iterator<Object> iterator = parsedParameters.iterator();
		
		Long empresaId = (Long) iterator.next();
		String documento = (String) iterator.next();
		String institucionFinanciera = (String) iterator.next();
		
		int parameterCount = 1;
		
		preparedStatement.setLong(parameterCount++, empresaId);
		preparedStatement.setString(parameterCount++, documento);
		preparedStatement.setString(parameterCount++, institucionFinanciera);
		
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
			"INSERT INTO bcu_interface_riesgo_crediticio_institucion_financiera(documento, institucion_financiera, calificacion, vigente,"
			+ " vigente_no_autoliquidable, previsiones_totales, contingencias, uact, fact, term)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
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
		
		String institucionFinanciera = (String) iterator.next();
		if (institucionFinanciera != null) {
			result.setString(parameterIndex++, institucionFinanciera);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String calificacion = (String) iterator.next();
		if (calificacion != null) {
			result.setString(parameterIndex++, calificacion);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		} 
		
		Double vigenteParsed = (Double) iterator.next();
		if (vigenteParsed != null) {
			result.setDouble(parameterIndex++, vigenteParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		Double vigenteNoAutoLiquidableParsed = (Double) iterator.next();
		if (vigenteNoAutoLiquidableParsed != null) {
			result.setDouble(parameterIndex++, vigenteNoAutoLiquidableParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		Double previsionesTotalesParsed = (Double) iterator.next();
		if (previsionesTotalesParsed != null) {
			result.setDouble(parameterIndex++, previsionesTotalesParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		Double contingenciasParsed = (Double) iterator.next();
		if (contingenciasParsed != null) {
			result.setDouble(parameterIndex++, contingenciasParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		result.setLong(parameterIndex++, Long.valueOf(1));
		result.setTimestamp(parameterIndex++, new Timestamp(new Date().getTime()));
		result.setLong(parameterIndex++, Long.valueOf(1));
		
		return result;
	}

	protected PreparedStatement prepareStatementForUpdate(Collection<Object> parsedParameters) throws SQLException {
		PreparedStatement result = this.connection.prepareStatement(
			"UPDATE bcu_interface_riesgo_crediticio_institucion_financiera"
				+ " SET calificacion = ?,"
				+ " vigente = ?,"
				+ " vigente_no_autoliquidable = ?,"
				+ " previsiones_totales = ?,"
				+ " contingencias = ?,"
				+ " uact = ?,"
				+ " fact = ?,"
				+ " term = ?"
			+ " WHERE documento = ? AND institucion_financiera = ?"
		);
		
		Iterator<Object> iterator = parsedParameters.iterator();
		int parameterIndex = 1;
		
		Long empresaId = (Long) iterator.next();
		String documento = (String) iterator.next();
		String institucionFinanciera = (String) iterator.next();
		
		String calificacion = (String) iterator.next();
		if (calificacion != null) {
			result.setString(parameterIndex++, calificacion);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		} 
		
		Double vigenteParsed = (Double) iterator.next();
		if (vigenteParsed != null) {
			result.setDouble(parameterIndex++, vigenteParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		Double vigenteNoAutoLiquidableParsed = (Double) iterator.next();
		if (vigenteNoAutoLiquidableParsed != null) {
			result.setDouble(parameterIndex++, vigenteNoAutoLiquidableParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		Double previsionesTotalesParsed = (Double) iterator.next();
		if (previsionesTotalesParsed != null) {
			result.setDouble(parameterIndex++, previsionesTotalesParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		Double contingenciasParsed = (Double) iterator.next();
		if (contingenciasParsed != null) {
			result.setDouble(parameterIndex++, contingenciasParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		result.setLong(parameterIndex++, Long.valueOf(1));
		result.setTimestamp(parameterIndex++, new Timestamp(new Date().getTime()));
		result.setLong(parameterIndex++, Long.valueOf(1));
		
		if (documento != null) {
			result.setString(parameterIndex++, documento);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		if (institucionFinanciera != null) {
			result.setString(parameterIndex++, institucionFinanciera);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		return result;
	}
}