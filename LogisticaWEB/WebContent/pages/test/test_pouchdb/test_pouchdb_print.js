var db = null;

$(document).ready(function() {
	db = new PouchDB("database").destroy().then(function() {
		db = new PouchDB("database");
		return db;
	}).then(function(db) {
		var img = document.getElementById("imgLogo");
		
//		blobUtil.imgSrcToBlob(img.src).then(function (blob) {
//			return db.putAttachment('logo', 'logo-vos.png', blob, 'image/png')
//		}).then(function(result) {
//			return db.get('logo', {attachments: true});
//		}).then(function (doc) {
//			return db.getAttachment('logo', 'logo-vos.png');
//		}).then(function(attachment) {
//			var url = URL.createObjectURL(attachment);
//			var imgdb = document.createElement("img");
//			imgdb.src = url;
//			document.body.appendChild(imgdb);
//		}).catch(function(error){ 
//			alert("put " + error); 
//		});
		
		return db;
	}).catch(function(error) {
		alert("Destroy: " + error);
	}).then(function(db) {
		var input = document.getElementById("inputFile");
		
		input.addEventListener('change', function(){
			var file = document.getElementById("inputFile").files[0];
			
			db.putAttachment(
				"logo",
				"uploaded", 
				file,
				file.type
			).catch(function (error) {
				alert("put:" + error);
			});
		});
	});
});

function show() {
	new PouchDB("database").get('logo', { attachments: true }).then(function(doc) {
		for (var attachment in doc._attachments) {
			alert(JSON.stringify(doc._attachments[attachment]));
			var url = URL.createObjectURL(doc._attachments[attachment]);
			var imgdb = document.createElement("img");
			imgdb.src = url;
			document.body.appendChild(imgdb);
		}
	}).catch(function(error){ 
		alert("get " + error); 
	});
}