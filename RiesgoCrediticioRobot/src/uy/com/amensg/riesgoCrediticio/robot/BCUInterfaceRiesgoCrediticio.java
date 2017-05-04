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

public class BCUInterfaceRiesgoCrediticio extends DataProcessingTemplateMethod {

	protected Collection<Object> parseParameters(Collection<String> data) {
		Collection<Object> result = new LinkedList<Object>();
		
		Iterator<String> iterator = data.iterator();
		
		// Parsear los parámetros tipados.
		
		// documento
		result.add(iterator.next());
		
		// periodo
		result.add(iterator.next());
		
		// nombreCompleto
		result.add(iterator.next());
		
		// actividad
		result.add(iterator.next());
		
		// vigente
		try {
			result.add(new Double(iterator.next()));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// vigenteNoAutoLiquidable
		try {
			result.add(new Double("0" + iterator.next().replace(",", ".")));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// garantiasComputables
		try {
			result.add(new Double("0" + iterator.next().replace(",", ".")));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// garantiasNoComputables
		try {
			result.add(new Double("0" + iterator.next().replace(",", ".")));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// castigadoPorAtraso
		try {
			result.add(iterator.next().equals("S"));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// castigadoPorQuitasYDesistimiento
		try {
			result.add(iterator.next().equals("S"));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// previsionesTotales
		try {
			result.add(new Double("0" + iterator.next().replace(",", ".")));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// contingencias
		try {
			result.add(new Double("0" + iterator.next().replace(",", ".")));
		} catch (Exception e) {
			e.printStackTrace();
			result.add(null);
		}
		
		// otorgantesGarantias
		try {
			result.add(new Double("0" + iterator.next().replace(",", ".")));
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
			+ " FROM bcu_interface_riesgo_crediticio"
			+ " WHERE documento = ?"
		);
		preparedStatement.setString(1, (String) parsedParameters.iterator().next());
		
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
			"INSERT INTO bcu_interface_riesgo_crediticio(documento, periodo, nombre_completo, actividad, vigente, vigente_no_autoliquidable,"
			+ " garantias_computables, garantias_no_computables, castigado_por_atraso, castigado_por_quitas_y_desistimiento, previsiones_totales,"
			+ " contingencias, otorgantes_garantias, uact, fact, term)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
		);
		
		Iterator<Object> iterator = parsedParameters.iterator();
		int parameterIndex = 1;
		
		String documento = (String) iterator.next();
		if (documento != null) {
			result.setString(parameterIndex++, documento);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String periodo = (String) iterator.next();
		result.setString(parameterIndex++, periodo);
		
		String nombreCompleto = (String) iterator.next();
		result.setString(parameterIndex++, nombreCompleto);
		
		String actividad = (String) iterator.next();
		result.setString(parameterIndex++, actividad);
		
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
		
		Double garantiasComputablesParsed = (Double) iterator.next();
		if (garantiasComputablesParsed != null) {
			result.setDouble(parameterIndex++, garantiasComputablesParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		Double garantiasNoComputablesParsed = (Double) iterator.next();
		if (garantiasNoComputablesParsed != null) {
			result.setDouble(parameterIndex++, garantiasNoComputablesParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		Boolean castigadoPorAtrasoParsed = (Boolean) iterator.next();
		if (castigadoPorAtrasoParsed != null) {
			result.setBoolean(parameterIndex++, castigadoPorAtrasoParsed);
		} else {
			result.setNull(parameterIndex++, Types.BOOLEAN);
		}
		
		Boolean castigadoPorQuitasYDesistimientoParsed = (Boolean) iterator.next();
		if (castigadoPorQuitasYDesistimientoParsed != null) {
			result.setBoolean(parameterIndex++, castigadoPorQuitasYDesistimientoParsed);
		} else {
			result.setNull(parameterIndex++, Types.BOOLEAN);
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
		
		Double otorgantesGarantiasParsed = (Double) iterator.next();
		if (otorgantesGarantiasParsed != null) {
			result.setDouble(parameterIndex++, otorgantesGarantiasParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		result.setLong(parameterIndex++, new Long(1));
		result.setTimestamp(parameterIndex++, new Timestamp(new Date().getTime()));
		result.setLong(parameterIndex++, new Long(1));
		
		return result;
	}

	protected PreparedStatement prepareStatementForUpdate(Collection<Object> parsedParameters) throws SQLException {
		PreparedStatement result = this.connection.prepareStatement(
			"UPDATE bcu_interface_riesgo_crediticio"
				+ " SET periodo = ?,"
				+ " nombre_completo = ?,"
				+ " actividad = ?,"
				+ " vigente = ?,"
				+ " vigente_no_autoliquidable = ?,"
				+ " garantias_computables = ?,"
				+ " garantias_no_computables = ?,"
				+ " castigado_por_atraso = ?,"
				+ " castigado_por_quitas_y_desistimiento = ?,"
				+ " previsiones_totales = ?,"
				+ " contingencias = ?,"
				+ " otorgantes_garantias = ?,"
				+ " uact = ?,"
				+ " fact = ?,"
				+ " term = ?"
			+ " WHERE documento = ?"
		);
		
		Iterator<Object> iterator = parsedParameters.iterator();
		int parameterIndex = 1;
		
		String documento = (String) iterator.next();
		
		String periodo = (String) iterator.next();
		result.setString(parameterIndex++, periodo);
		
		String nombreCompleto = (String) iterator.next();
		result.setString(parameterIndex++, nombreCompleto);
		
		String actividad = (String) iterator.next();
		result.setString(parameterIndex++, actividad);
		
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
		
		Double garantiasComputablesParsed = (Double) iterator.next();
		if (garantiasComputablesParsed != null) {
			result.setDouble(parameterIndex++, garantiasComputablesParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		Double garantiasNoComputablesParsed = (Double) iterator.next();
		if (garantiasNoComputablesParsed != null) {
			result.setDouble(parameterIndex++, garantiasNoComputablesParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
		}
		
		Boolean castigadoPorAtrasoParsed = (Boolean) iterator.next();
		if (castigadoPorAtrasoParsed != null) {
			result.setBoolean(parameterIndex++, castigadoPorAtrasoParsed);
		} else {
			result.setNull(parameterIndex++, Types.BOOLEAN);
		}
		
		Boolean castigadoPorQuitasYDesistimientoParsed = (Boolean) iterator.next();
		if (castigadoPorQuitasYDesistimientoParsed != null) {
			result.setBoolean(parameterIndex++, castigadoPorQuitasYDesistimientoParsed);
		} else {
			result.setNull(parameterIndex++, Types.BOOLEAN);
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
		
		Double otorgantesGarantiasParsed = (Double) iterator.next();
		if (otorgantesGarantiasParsed != null) {
			result.setDouble(parameterIndex++, otorgantesGarantiasParsed);
		} else {
			result.setNull(parameterIndex++, Types.DECIMAL);
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