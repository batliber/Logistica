import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.SimpleDateFormat;

public class Test {

	public Test() {
		Connection connectionAccess = null;
		Connection connectionPostgreSQL = null;
		
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			connectionAccess = DriverManager.getConnection(
				"jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=E:/Clientes.mdb", "mesmoris", "1234");
			
			PreparedStatement preparedStatement = connectionAccess.prepareStatement(
				"SELECT * FROM Contratos"
			);
			
			Class.forName("org.postgresql.Driver");
			
			connectionPostgreSQL = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/phonehouse_logistica", "postgres", "amensg"
			);
			
			PreparedStatement preparedStatementInsert = connectionPostgreSQL.prepareStatement(
				"INSERT INTO acm_interface_contrato_access ("
					+ "mid, "
					+ "fecha_fin_contrato, "
					+ "tipo_contrato_codigo, "
					+ "tipo_contrato_descripcion, "
					+ "documento_tipo, "
					+ "documento, "
					+ "nombre, "
					+ "direccion, "
					+ "codigo_postal, "
					+ "localidad, "
					+ "uact, "
					+ "fact, "
					+ "term"
					+ ")"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
			);
			preparedStatementInsert.setLong(11, new Long(1));
			preparedStatementInsert.setLong(13, new Long(1));
			
			
			int info = 0;
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
//				String line = "";
//				for (int i=1; i<14; i++) {
//					line += resultSet.getString(i) + " | ";
//				}
//				
//				System.out.println(line);
				
				String mid = resultSet.getString(1);
				String fechaFinContrato = resultSet.getString(2);
				String tipoContrato = resultSet.getString(3);
				String tipoContratoCodigo = tipoContrato.split(" ")[0];
				String tipoContratoDescripcion = tipoContrato;
				String documento = resultSet.getString(4);
				String nombre = resultSet.getString(5);
				String direccion = resultSet.getString(6);
				String localidad = resultSet.getString(7);
				String obtenido = resultSet.getString(10);
				
//				System.out.println(
//					mid
//					+ " | " + fechaFinContrato
//					+ " | " + tipoContrato
//					+ " | " + documento
//					+ " | " + nombre
//					+ " | " + direccion
//					+ " | " + localidad
//					+ " | " + obtenido
//				);
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				preparedStatementInsert.setString(1, mid);
				if (fechaFinContrato != null) {
					preparedStatementInsert.setDate(2, new java.sql.Date(format.parse(fechaFinContrato).getTime()));
				} else {
					preparedStatementInsert.setNull(2, Types.TIMESTAMP);
				}
				preparedStatementInsert.setString(3, tipoContratoCodigo);
				preparedStatementInsert.setString(4, tipoContratoDescripcion);
				preparedStatementInsert.setLong(5, new Long(0));
				preparedStatementInsert.setString(6, documento);
				preparedStatementInsert.setString(7, nombre);
				preparedStatementInsert.setString(8, direccion);
				preparedStatementInsert.setString(9, "1");
				preparedStatementInsert.setString(10, localidad);
				preparedStatementInsert.setDate(12, new java.sql.Date(format.parse(obtenido).getTime()));
				
				preparedStatementInsert.executeUpdate();
				
				info++;
				
				if (info % 100 == 0) {
					System.out.println(info);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connectionAccess != null) {
					connectionAccess.close();
				}
				if (connectionPostgreSQL != null) {
					connectionPostgreSQL.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		

		new Test();
//		if (args[0].equals("dame")) {
//			System.out.println("099455097");
//		} else if (args[0].equals("toma")) {
//			System.out.println(args[1]);
//		}
	}
}