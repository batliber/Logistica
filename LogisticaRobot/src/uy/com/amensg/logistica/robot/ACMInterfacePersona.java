package uy.com.amensg.logistica.robot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

public class ACMInterfacePersona extends DataProcessingTemplateMethod {

	public void doProcessing(Collection<String> data) {
		try {
			this.init();
			
			Collection<Object> parsedParameters = this.parseParameters(data);
			
			if (parsedParameters.size() > 0) {
				Long id = this.checkExistenceWithID(parsedParameters);
				
				Collection<Object> newParsedParameters = new LinkedList<Object>();
				
				newParsedParameters.add(id);
				
				for (Object object : parsedParameters) {
					newParsedParameters.add(object);
				}
				
				if (id != null) {
					this.prepareStatementForUpdate(newParsedParameters);
					
//					this.insert(preparedStatementUpdate);
				} else {		
					this.prepareStatementForInsert(newParsedParameters);
					
//					this.update(preparedStatementInsert);
				}
			}
			
			this.end();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected Collection<Object> parseParameters(Collection<String> data) {
		Collection<Object> result = new LinkedList<Object>();
		
		Iterator<String> iterator = data.iterator();
		
		// Parsear los parámetros tipados.
		
		// idCliente
		result.add(iterator.next());
		
		// mid
		result.add(iterator.next());
		
		// pais
		result.add(iterator.next());
		
		// tipoDocumento
		result.add(iterator.next());
		
		// documento
		result.add(iterator.next());
		
		// apellido
		result.add(iterator.next());
		
		// nombre
		result.add(iterator.next());
		
		// razonSocial
		result.add(iterator.next());
		
		// tipoCliente
		result.add(iterator.next());
		
		// actividad
		result.add(iterator.next());
		
		// fechaNacimiento
		result.add(iterator.next());
		
		// sexo
		result.add(iterator.next());
		
		// direccionCalle
		result.add(iterator.next());
		
		// direccionNumero
		result.add(iterator.next());
		
		// direccionBis
		result.add(iterator.next());
		
		// direccionApartamento
		result.add(iterator.next());
		
		// direccionEsquina
		result.add(iterator.next());
		
		// direccionBlock
		result.add(iterator.next());
		
		// direccionManzana
		result.add(iterator.next());
		
		// direccionSolar
		result.add(iterator.next());
		
		// direccionObservaciones
//		result.add(iterator.next());
		
		// direccionLocalidad
		result.add(iterator.next());
		
		// direccionCodigoPostal
		result.add(iterator.next());
		
		// direccionCompleta
		result.add(iterator.next());
		
		// distribuidor
		result.add(iterator.next());
		
		// telefonosFijo
		result.add(iterator.next());
		
		// telefonosAviso
//		result.add(iterator.next());
		
		// telefonosFax
//		result.add(iterator.next());
		
		// email
		result.add(iterator.next());		
		
		return result;
	}

	protected boolean checkExistence(Collection<Object> parsedParameters) throws SQLException {
		boolean result = false;
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(
			"SELECT COUNT(0)"
			+ " FROM acm_interface_persona"
			+ " WHERE id_cliente = ?"
		);
		
		Iterator<Object> iterator = parsedParameters.iterator();
		
		preparedStatement.setString(1, (String) iterator.next());
		
		Long count = null;
		
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			count = resultSet.getLong(1);
			
			result = count >= 0;
		}
		
		return result;
	}

	protected Long checkExistenceWithID(Collection<Object> parsedParameters) throws SQLException {
		Long result = null;
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(
			"SELECT id"
			+ " FROM acm_interface_persona"
			+ " WHERE id_cliente = ?"
		);
		
		Iterator<Object> iterator = parsedParameters.iterator();
		
		String idCliente = (String) iterator.next();
		
		preparedStatement.setString(1, idCliente);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			result = resultSet.getLong(1);
		}
		
		return result;
	}
	
	protected PreparedStatement prepareStatementForInsert(Collection<Object> parsedParameters) throws SQLException {
		PreparedStatement result = this.connection.prepareStatement(
			"INSERT INTO acm_interface_persona(id, id_cliente, pais, tipo_documento, documento, apellido, nombre, razon_social, tipo_cliente,"
				+ " actividad, fecha_nacimiento, sexo, calle, numero, bis, apartamento, esquina, block, manzana, solar, localidad, codigo_postal,"
				+ " direccion, distribucion, telefono, email, uact, fact, term)"
			+ " values (nextval('hibernate_sequence'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
		);
		
		Iterator<Object> iterator = parsedParameters.iterator();
		int parameterIndex = 1;
		
		Long id = (Long) iterator.next();
		
		String idCliente = (String) iterator.next();
		if (idCliente != null) {
			result.setString(parameterIndex++, idCliente);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String mid = (String) iterator.next();
		
		String pais = (String) iterator.next();
		if (pais != null) {
			result.setString(parameterIndex++, pais);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String tipoDocumento = (String) iterator.next();
		if (tipoDocumento != null) {
			result.setString(parameterIndex++, tipoDocumento);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String documento = (String) iterator.next();
		if (documento != null) {
			result.setString(parameterIndex++, documento);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}

		String apellido = (String) iterator.next();
		if (apellido != null) {
			result.setString(parameterIndex++, apellido);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String nombre = (String) iterator.next();
		if (nombre != null) {
			result.setString(parameterIndex++, nombre);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String razonSocial = (String) iterator.next();
		if (razonSocial != null) {
			result.setString(parameterIndex++, razonSocial);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}

		String tipoCliente = (String) iterator.next();
		if (tipoCliente != null) {
			result.setString(parameterIndex++, tipoCliente);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String actividad = (String) iterator.next();
		if (actividad != null) {
			result.setString(parameterIndex++, actividad);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String fechaNacimiento = (String) iterator.next();
		if (fechaNacimiento != null) {
			result.setString(parameterIndex++, fechaNacimiento);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String sexo = (String) iterator.next();
		if (sexo != null) {
			result.setString(parameterIndex++, sexo);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionCalle = (String) iterator.next();
		if (direccionCalle != null) {
			result.setString(parameterIndex++, direccionCalle);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}

		String direccionNumero = (String) iterator.next();
		if (direccionNumero != null) {
			result.setString(parameterIndex++, direccionNumero);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}

		String direccionBis = (String) iterator.next();
		if (direccionBis != null) {
			result.setString(parameterIndex++, direccionBis);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionApartamento = (String) iterator.next();
		if (direccionApartamento != null) {
			result.setString(parameterIndex++, direccionApartamento);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionEsquina = (String) iterator.next();
		if (direccionEsquina != null) {
			result.setString(parameterIndex++, direccionEsquina);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionBlock = (String) iterator.next();
		if (direccionBlock != null) {
			result.setString(parameterIndex++, direccionBlock);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionManzana = (String) iterator.next();
		if (direccionManzana != null) {
			result.setString(parameterIndex++, direccionManzana);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionSolar = (String) iterator.next();
		if (direccionSolar != null) {
			result.setString(parameterIndex++, direccionSolar);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionLocalidad = (String) iterator.next();
		if (direccionLocalidad != null) {
			result.setString(parameterIndex++, direccionLocalidad);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionCodigoPostal = (String) iterator.next();
		if (direccionCodigoPostal != null) {
			result.setString(parameterIndex++, direccionCodigoPostal);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionCompleta = (String) iterator.next();
		if (direccionCompleta != null) {
			result.setString(parameterIndex++, direccionCompleta);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String distribuidor = (String) iterator.next();
		if (distribuidor != null) {
			result.setString(parameterIndex++, distribuidor);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String telefonosFijo = (String) iterator.next();
		if (telefonosFijo != null) {
			result.setString(parameterIndex++, telefonosFijo);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}

		String email = (String) iterator.next();
		if (email != null) {
			result.setString(parameterIndex++, email);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		result.setLong(parameterIndex++, Long.valueOf(1));
		result.setTimestamp(parameterIndex++, new Timestamp(new Date().getTime()));
		result.setLong(parameterIndex++, Long.valueOf(1));
		
		result.executeUpdate();
		
		PreparedStatement preparedStatementId = connection.prepareStatement(
			"SELECT id"
			+ " FROM acm_interface_persona"
			+ " WHERE id_cliente = ?"
		);
		preparedStatementId.setString(1, idCliente);
		
		ResultSet resultSetId = preparedStatementId.executeQuery();
		while (resultSetId.next()) {
			id = resultSetId.getLong(1);
			
			PreparedStatement preparedStatementInsertMidPersona = connection.prepareStatement(
				"INSERT INTO acm_interface_mid_persona(mid, persona_id) VALUES (?, ?)"
			);
			preparedStatementInsertMidPersona.setLong(1, Long.decode(mid));
			preparedStatementInsertMidPersona.setLong(2, id);
			
			preparedStatementInsertMidPersona.executeUpdate();
		}
		
		this.connection.commit();
		
		return result;
	}

	protected PreparedStatement prepareStatementForUpdate(Collection<Object> parsedParameters) throws SQLException {
		PreparedStatement result = this.connection.prepareStatement(
			"UPDATE acm_interface_persona"
				+ " SET id_cliente = ?,"
				+ " pais = ?,"
				+ " tipo_documento = ?,"
				+ " documento = ?,"
				+ " apellido = ?,"
				+ " nombre = ?,"
				+ " razon_social = ?,"
				+ " tipo_cliente = ?,"
				+ " actividad = ?,"
				+ " fecha_nacimiento = ?,"
				+ " sexo = ?,"
				+ " calle = ?,"
				+ " numero = ?,"
				+ " bis = ?,"
				+ " apartamento = ?,"
				+ " esquina = ?,"
				+ " block = ?,"
				+ " manzana = ?,"
				+ " solar = ?,"
				+ " localidad = ?,"
				+ " codigo_postal = ?,"
				+ " direccion = ?,"
				+ " distribucion = ?,"
				+ " telefono = ?,"
				+ " email = ?,"
				+ " uact = ?,"
				+ " fact = ?,"
				+ " term = ?"
			+ " WHERE id = ?"
		);
		
		Iterator<Object> iterator = parsedParameters.iterator();
		int parameterIndex = 1;
		
		Long id = (Long) iterator.next();
		
		String idCliente = (String) iterator.next();
		if (idCliente != null) {
			result.setString(parameterIndex++, idCliente);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String mid = (String) iterator.next();
		
		String pais = (String) iterator.next();
		if (pais != null) {
			result.setString(parameterIndex++, pais);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String tipoDocumento = (String) iterator.next();
		if (tipoDocumento != null) {
			result.setString(parameterIndex++, tipoDocumento);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String documento = (String) iterator.next();
		if (documento != null) {
			result.setString(parameterIndex++, documento);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}

		String apellido = (String) iterator.next();
		if (apellido != null) {
			result.setString(parameterIndex++, apellido);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String nombre = (String) iterator.next();
		if (nombre != null) {
			result.setString(parameterIndex++, nombre);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String razonSocial = (String) iterator.next();
		if (razonSocial != null) {
			result.setString(parameterIndex++, razonSocial);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}

		String tipoCliente = (String) iterator.next();
		if (tipoCliente != null) {
			result.setString(parameterIndex++, tipoCliente);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String actividad = (String) iterator.next();
		if (actividad != null) {
			result.setString(parameterIndex++, actividad);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String fechaNacimiento = (String) iterator.next();
		if (fechaNacimiento != null) {
			result.setString(parameterIndex++, fechaNacimiento);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String sexo = (String) iterator.next();
		if (sexo != null) {
			result.setString(parameterIndex++, sexo);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionCalle = (String) iterator.next();
		if (direccionCalle != null) {
			result.setString(parameterIndex++, direccionCalle);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}

		String direccionNumero = (String) iterator.next();
		if (direccionNumero != null) {
			result.setString(parameterIndex++, direccionNumero);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}

		String direccionBis = (String) iterator.next();
		if (direccionBis != null) {
			result.setString(parameterIndex++, direccionBis);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionApartamento = (String) iterator.next();
		if (direccionApartamento != null) {
			result.setString(parameterIndex++, direccionApartamento);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionEsquina = (String) iterator.next();
		if (direccionEsquina != null) {
			result.setString(parameterIndex++, direccionEsquina);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionBlock = (String) iterator.next();
		if (direccionBlock != null) {
			result.setString(parameterIndex++, direccionBlock);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionManzana = (String) iterator.next();
		if (direccionManzana != null) {
			result.setString(parameterIndex++, direccionManzana);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionSolar = (String) iterator.next();
		if (direccionSolar != null) {
			result.setString(parameterIndex++, direccionSolar);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionLocalidad = (String) iterator.next();
		if (direccionLocalidad != null) {
			result.setString(parameterIndex++, direccionLocalidad);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionCodigoPostal = (String) iterator.next();
		if (direccionCodigoPostal != null) {
			result.setString(parameterIndex++, direccionCodigoPostal);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String direccionCompleta = (String) iterator.next();
		if (direccionCompleta != null) {
			result.setString(parameterIndex++, direccionCompleta);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String distribuidor = (String) iterator.next();
		if (distribuidor != null) {
			result.setString(parameterIndex++, distribuidor);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		String telefonosFijo = (String) iterator.next();
		if (telefonosFijo != null) {
			result.setString(parameterIndex++, telefonosFijo);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}

		String email = (String) iterator.next();
		if (email != null) {
			result.setString(parameterIndex++, email);
		} else {
			result.setNull(parameterIndex++, Types.VARCHAR);
		}
		
		result.setLong(parameterIndex++, Long.valueOf(1));
		result.setTimestamp(parameterIndex++, new Timestamp(new Date().getTime()));
		result.setLong(parameterIndex++, Long.valueOf(1));
		
		result.setLong(parameterIndex++, id);
		
		result.executeUpdate();
		
		PreparedStatement preparedStatementMidPersona = connection.prepareStatement(
			"SELECT COUNT(0)"
			+ " FROM acm_interface_mid_persona"
			+ " WHERE mid = ?"
			+ " AND persona_id = ?"
		);
		preparedStatementMidPersona.setLong(1, Long.decode(mid));
		preparedStatementMidPersona.setLong(2, Long.valueOf(id));
		
		ResultSet resultSetMidPersona = preparedStatementMidPersona.executeQuery();
		while(resultSetMidPersona.next()) {
			Long count = resultSetMidPersona.getLong(1);
			
			if (count <= 0) {
				PreparedStatement preparedStatementInsertMidPersona = connection.prepareStatement(
					"INSERT INTO acm_interface_mid_persona(mid, persona_id) VALUES (?, ?)"
				);
				preparedStatementInsertMidPersona.setLong(1, Long.decode(mid));
				preparedStatementInsertMidPersona.setLong(2, id);
				
				preparedStatementInsertMidPersona.executeUpdate();
			}
		}
		
		this.connection.commit();
		
		return result;
	}
}