$(document).ready(function() {
	reloadData();
});

function reloadData() {
	FileManagerDWR.listArchivos(
		{
			callback: function(data) {
				$("#tableArchivos > tbody:last > tr").remove();
				
				for (var i=0; i<data.length; i++) {
					$("#tableArchivos > tbody:last").append(
						"<tr>"
							+ "<td>"
								+ "<div class='divArchivosNombre'>"
									+ "<a href='/LogisticaWEB/Download?fn=" + data[i].nombre + "'>" 
										+ data[i].nombre
									+ "</a>"
								+ "</div>"
							+ "</td>"
						+ "</tr>"
					);
				}
			}
		}
	);
}

function inputActualizarOnClick(event) {
	reloadData();
}