package uy.com.amensg.logistica.robot.test;

public class TestLogisticaProxy {

//	private Connection connection;
//	private IConnectionStrategy strategy;
//	
//	@Before
//	public void setUp() throws Exception {
////		strategy = new ConnectionStrategyDirect();
////		strategy = new ConnectionStrategyRemoting();
//		strategy = new ConnectionStrategyWebService();
//		
//		Class.forName("org.postgresql.Driver");
//		
//		this.connection = DriverManager.getConnection(
//			Configuration.getInstance().getProperty("db.connectionURL"), 
//			Configuration.getInstance().getProperty("db.connectionUser"), 
//			Configuration.getInstance().getProperty("db.connectionPassword")
//		);
//			
//		this.connection.setAutoCommit(true);
//	}
//
//	@Test
//	public final void testGetSiguienteMidSinProcesar() {
//		try {
//			PreparedStatement preparedStatement = this.connection.prepareStatement(
//				"select mid from acm_interface_mid where estado in (?, ?) order by estado desc"
//			);
//			preparedStatement.setLong(1, new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ParaProcesar")));
//			preparedStatement.setLong(2, new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ParaProcesarPrioritario")));
//			preparedStatement.setMaxRows(1);
//			
//			ResultSet resultSet = preparedStatement.executeQuery();
//			
//			resultSet.next();
//			
//			Long expectedMidSinProcesar = resultSet.getLong(1);
//			
//			String datosMid = strategy.getSiguienteMidSinProcesar("");
//			String mid = datosMid.substring(2, 10);
//			
//			assertEquals(expectedMidSinProcesar, mid);
//			
//			preparedStatement = this.connection.prepareStatement(
//				"select mid from acm_interface_mid where estado = ? order by fact desc"
//			);
//			preparedStatement.setLong(1, new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.EnProceso")));
//			
//			resultSet = preparedStatement.executeQuery();
//			
//			resultSet.next();
//			
//			Long expectedMidEnProceso = resultSet.getLong(1);
//			
//			assertEquals(expectedMidEnProceso, expectedMidSinProcesar);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Ignore
//	@Test
//	public final void testGetSiguienteNumeroContratoSinProcesar() {
//		try {
//			PreparedStatement preparedStatement = this.connection.prepareStatement(
//				"select numero_contrato from acm_interface_numero_contrato where estado in (?, ?) order by estado desc"
//			);
//			preparedStatement.setLong(1, new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ParaProcesar")));
//			preparedStatement.setLong(2, new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ParaProcesarPrioritario")));
//			preparedStatement.setMaxRows(1);
//			
//			ResultSet resultSet = preparedStatement.executeQuery();
//			
//			resultSet.next();
//			
//			Long expectedNumeroContratoSinProcesar = resultSet.getLong(1);
//			
//			assertEquals(
//				expectedNumeroContratoSinProcesar.toString(), 
//				strategy.getSiguienteNumeroContratoSinProcesar("")
//			);
//			
//			preparedStatement = this.connection.prepareStatement(
//				"select numero_contrato from acm_interface_numero_contrato where estado = ? order by fact desc"
//			);
//			preparedStatement.setLong(1, new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.EnProceso")));
//			
//			resultSet = preparedStatement.executeQuery();
//			
//			resultSet.next();
//			
//			Long expectedNumeroContratoEnProceso = resultSet.getLong(1);
//			
//			assertEquals(expectedNumeroContratoEnProceso, expectedNumeroContratoSinProcesar);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Ignore
//	@Test
//	public final void testActualizarDatosMidContrato() {
//		try {
//			String datosMid = strategy.getSiguienteMidSinProcesar("");
//			String mid = datosMid.substring(2, 10);
//			
//			strategy.actualizarDatosMidContrato(
//				"",
//				"direccion",
//				"1",
//				"documento",
//				"01/01/2014",
//				"localidad",
//				"CP",
//				mid,
//				"nombre",
//				"0",
//				"tipoContratoDescripcion",
//				"agente",
//				"equipo",
//				"0",
//				"0",
//				"0"
//			);
//			
//			PreparedStatement preparedStatement = this.connection.prepareStatement(
//				"select mid, estado from acm_interface_mid where mid = ?"
//			);
//			preparedStatement.setLong(1, new Long(mid));
//			
//			Long expectedEstado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.Procesado"));
//			
//			ResultSet resultSet = preparedStatement.executeQuery();
//			
//			resultSet.next();
//			
//			assertEquals(expectedEstado, new Long(resultSet.getLong(2)));
//			
//			preparedStatement = this.connection.prepareStatement(
//				"select direccion, documento_tipo, documento,"
//					+ " fecha_fin_contrato, localidad, codigo_postal, nombre,"
//					+ " tipo_contrato_codigo,"
//					+ " tipo_contrato_descripcion,"
//					+ " agente,"
//					+ " equipo,"
//					+ " numero_cliente,"
//					+ " numero_contrato"
//				+ " from acm_interface_contrato where mid = ?"
//			);
//			preparedStatement.setLong(1, new Long(mid));
//			
//			resultSet = preparedStatement.executeQuery();
//			
//			resultSet.next();
//			
//			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//			
//			assertEquals("direccion", resultSet.getString(1));
//			assertEquals("1", resultSet.getString(2));
//			assertEquals("documento", resultSet.getString(3));
//			assertEquals(format.parse("01/01/2014"), resultSet.getDate(4));
//			assertEquals("localidad", resultSet.getString(5));
//			assertEquals("CP", resultSet.getString(6));
//			assertEquals("nombre", resultSet.getString(7));
//			assertEquals("0", resultSet.getString(8));
//			assertEquals("tipoContratoDescripcion", resultSet.getString(9));
//			assertEquals("agente", resultSet.getString(10));
//			assertEquals("equipo", resultSet.getString(11));
//			assertEquals("0", resultSet.getString(12));
//			assertEquals("0", resultSet.getString(13));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Ignore
//	@Test
//	public final void testActualizarDatosMidPrepago() {
//		try {
//			String datosMid = strategy.getSiguienteMidSinProcesar("");
//			String mid = datosMid.substring(2, 10);
//			
//			strategy.actualizarDatosMidPrepago(
//				"",
//				"01/2013",
//				mid,
//				"10",
//				"10",
//				"10",
//				"01/11/2013",
//				"agente"
//			);
//			
//			PreparedStatement preparedStatement = this.connection.prepareStatement(
//				"select mid, estado from acm_interface_mid where mid = ?"
//			);
//			preparedStatement.setLong(1, new Long(mid));
//			
//			Long expectedEstado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.Procesado"));
//			
//			ResultSet resultSet = preparedStatement.executeQuery();
//			
//			resultSet.next();
//			
//			assertEquals(expectedEstado, new Long(resultSet.getLong(2)));
//			
//			preparedStatement = this.connection.prepareStatement(
//				"select mes_ano, monto_mes_actual, monto_mes_anterior_1,"
//					+ " monto_mes_anterior_2, monto_promedio, fecha_activacion_kit"
//				+ " from acm_interface_prepago where mid = ?"
//			);
//			preparedStatement.setLong(1, new Long(mid));
//			
//			resultSet = preparedStatement.executeQuery();
//			
//			resultSet.next();
//			
//			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//			
//			assertEquals(format.parse("01/01/2013"), resultSet.getDate(1));
//			assertSame(new Double("10"), resultSet.getDouble(2));
//			assertSame(new Double("10"), resultSet.getDouble(3));
//			assertSame(new Double("10"), resultSet.getDouble(4));
//			assertSame(new Double("10"), resultSet.getDouble(5));
//			assertEquals(format.parse("01/11/2013"), resultSet.getDate(6));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Ignore
//	@Test
//	public final void testActualizarDatosMidListaVacia() {
//		try {
//			String datosMid = strategy.getSiguienteMidSinProcesar("");
//			String mid = datosMid.substring(2, 10);
//			
//			strategy.actualizarDatosMidListaVacia("", mid);
//			
//			PreparedStatement preparedStatement = this.connection.prepareStatement(
//				"select mid, estado from acm_interface_mid where mid = ?"
//			);
//			preparedStatement.setLong(1, new Long(mid));
//			
//			Long expectedEstado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ListaVacia"));
//			
//			ResultSet resultSet = preparedStatement.executeQuery();
//			
//			resultSet.next();
//			
//			assertEquals(expectedEstado, new Long(resultSet.getLong(2)));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Ignore
//	@Test
//	public final void testActualizarDatosNumeroContratoListaVacia() {
//		try {
//			String numeroContrato = strategy.getSiguienteNumeroContratoSinProcesar("");
//			
//			strategy.actualizarDatosNumeroContratoListaVacia("", numeroContrato);
//			
//			PreparedStatement preparedStatement = this.connection.prepareStatement(
//				"select numero_contrato, estado from acm_interface_numero_contrato where numero_contrato = ?"
//			);
//			preparedStatement.setLong(1, new Long(numeroContrato));
//			
//			Long expectedEstado = new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ListaVacia"));
//			
//			ResultSet resultSet = preparedStatement.executeQuery();
//			
//			resultSet.next();
//			
//			assertEquals(expectedEstado, new Long(resultSet.getLong(2)));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Ignore
//	@Test
//	public final void testInsertarDatosPersona() {
//		try {
//			String millis = new Long(GregorianCalendar.getInstance().getTimeInMillis()).toString();
//			
//			strategy.actualizarDatosPersona(
//				"",
//				millis,
//				"99455097",
//				"URUGUAY" + millis,
//				"CI" + millis,
//				"42273192" + millis,
//				"BATALLA" + millis,
//				"LIBER" + millis,
//				"RS" + millis, 
//				"TC", 
//				"AC", 
//				"22/02/1983",
//				"M", 
//				"AVDA. BUSCHENTAL" + millis, 
//				"3327", 
//				"F", 
//				"AP", 
//				"FELIX OLMEDO" + millis, 
//				"BL", 
//				"M", 
//				"S", 
//				"OBS", 
//				"MONTEVIDEO", 
//				"12400", 
//				"AVDA. BUSCHENTAL 3327" + millis, 
//				"1", 
//				"2355 4232", 
//				"AVISO" + millis, 
//				"FAX", 
//				"BATLIBER@GMAIL.COM" + millis
//			);
//			
//			PreparedStatement preparedStatement = this.connection.prepareStatement(
//				"select id from acm_interface_persona where id_cliente = ?"
//			);
//			preparedStatement.setString(1, millis);
//			
//			ResultSet resultSetPersona = preparedStatement.executeQuery();
//			
//			resultSetPersona.next();
//			
//			Long id = new Long(resultSetPersona.getLong(1));
//			
//			assertNotNull(id);
//			
//			preparedStatement = this.connection.prepareStatement(
//				"select mid from acm_interface_mid_persona where persona_id = ?"
//			);
//			preparedStatement.setLong(1, id);
//			
//			ResultSet resultSetMidPersona = preparedStatement.executeQuery();
//			
//			Long mid = new Long(resultSetMidPersona.getLong(1));
//			
//			assertEquals(new Long(99455097), mid);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Ignore
//	@Test
//	public final void testActualizarDatosPersona() {
//		try {
//			strategy.actualizarDatosPersona(
//				"",
//				"123456",
//				"99455097",
//				"URUGUAY M",
//				"CI M",
//				"42273192 M",
//				"BATALLA M",
//				"LIBER M",
//				"RS M", 
//				"TC M", 
//				"AC M", 
//				"22/02/1983 M",
//				"M M", 
//				"AVDA. BUSCHENTAL M", 
//				"3327 M", 
//				"F M", 
//				"AP M", 
//				"FELIX OLMEDO M", 
//				"BL M", 
//				"M M", 
//				"S M", 
//				"OBS M", 
//				"MONTEVIDEO M", 
//				"12400 M", 
//				"AVDA. BUSCHENTAL 3327 M", 
//				"1 M", 
//				"2355 4232 M", 
//				"AVISO M", 
//				"FAX M", 
//				"BATLIBER@GMAIL.COM M"
//			);
//		
//			PreparedStatement preparedStatement = this.connection.prepareStatement(
//				"select * from acm_interface_persona where id_cliente = ?"
//			);
//			preparedStatement.setString(1, "123456");
//			
//			ResultSet resultSet = preparedStatement.executeQuery();
//			
//			resultSet.next();
//			
//			assertTrue(resultSet.getString("pais").endsWith("M"));
//			assertTrue(resultSet.getString("tipo_documento").endsWith("M"));
//			assertTrue(resultSet.getString("documento").endsWith("M"));
//			assertTrue(resultSet.getString("apellido").endsWith("M"));
//			assertTrue(resultSet.getString("nombre").endsWith("M"));
//			assertTrue(resultSet.getString("razon_social").endsWith("M"));
//			assertTrue(resultSet.getString("tipo_cliente").endsWith("M"));
//			assertTrue(resultSet.getString("actividad").endsWith("M"));
//			assertTrue(resultSet.getString("fecha_nacimiento").endsWith("M"));
//			assertTrue(resultSet.getString("sexo").endsWith("M"));
//			assertTrue(resultSet.getString("calle").endsWith("M"));
//			assertTrue(resultSet.getString("numero").endsWith("M"));
//			assertTrue(resultSet.getString("bis").endsWith("M"));
//			assertTrue(resultSet.getString("apartamento").endsWith("M"));
//			assertTrue(resultSet.getString("esquina").endsWith("M"));
//			assertTrue(resultSet.getString("block").endsWith("M"));
//			assertTrue(resultSet.getString("manzana").endsWith("M"));
//			assertTrue(resultSet.getString("solar").endsWith("M"));
//			assertTrue(resultSet.getString("localidad").endsWith("M"));
//			assertTrue(resultSet.getString("codigo_postal").endsWith("M"));
//			assertTrue(resultSet.getString("direccion").endsWith("M"));
//			assertTrue(resultSet.getString("distribucion").endsWith("M"));
//			assertTrue(resultSet.getString("telefono").endsWith("M"));
//			assertTrue(resultSet.getString("email").endsWith("M"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		this.connection.close();
//	}
}