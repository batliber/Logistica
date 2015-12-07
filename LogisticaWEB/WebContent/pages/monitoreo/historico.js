$(document).ready(function() {
	ContratoRoutingHistoryDWR.listByContratoId(
		id,
		{
			callback: function(data) {
				$("#tableHistorico > tbody:last > tr").remove();
				
				for (var i=0; i<data.length; i++) {
					$("#tableHistorico > tbody:last").append(
						"<tr>"
							+ "<td class='tdFecha'><div class='divFecha'>" 
								+ formatLongDate(data[i].fecha) 
							+ "</div></td>"
							+ "<td class='tdEmpresa'><div class='divEmpresa' title='"
								+ (data[i].empresa != null ?
									data[i].empresa.nombre : "&nbsp;") + "'>" 
								+ (data[i].empresa != null ?
									data[i].empresa.nombre : "&nbsp;")
							+ "</div></td>"
							+ "<td class='tdUsuario'><div class='divUsuario' title='"
								+ (data[i].usuario != null ?
									data[i].usuario.nombre : "&nbsp;") + "'>" 
								+ (data[i].usuario != null ?
									data[i].usuario.nombre : "&nbsp;")
							+ "</div></td>"
							+ "<td class='tdRol'><div class='divRol' title='"
								+ (data[i].rol != null ?
									data[i].rol.nombre : "&nbsp;") + "'>" 
								+ (data[i].rol != null ?
									data[i].rol.nombre : "&nbsp;")
							+ "</div></td>"
							+ "<td class='tdEstado'><div class='divEstado' title='"
								+ (data[i].contrato.estado.nombre != null ?
									data[i].contrato.estado.nombre : "&nbsp;") + "'>" 
								+ (data[i].contrato.estado.nombre != null ?
									data[i].contrato.estado.nombre : "&nbsp;")
							+ "</div></td>"
						+ "</tr>"
					);
				}
			}, async: false
		}
	);
});