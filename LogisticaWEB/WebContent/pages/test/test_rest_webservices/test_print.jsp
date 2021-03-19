<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
		<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
		<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
		<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
		<script type="text/javascript" src="./test_print.js"></script>
		<link rel="stylesheet" type="text/css" href="./test_print.css"/>
	</head>
	<body>
		<div class="divRequest">
			<div>Method:</div><div class="divMethod"><select id="selectMethod"></select></div>
			<div>URL:</div><div class="divURL"><input type="text" id="inputURL"/></div>
			<div>Parameters:</div><div class="divParameters"><select id="selectParametersTemplate" onchange="javascript:selectParametersTemplateOnChange(event, this)"></select></div>
			<div class="divParameters"><textarea id="textareaParameters"></textarea></div>
		</div>
		<div class="divResponse">
			<div>Response:</div><div class="divResponse"><textarea id="textareaResponse"></textarea></div>
		</div>
		<div class="divButtonSubmit"><input type="button" value="Submit" onclick="javascript:inputSubmitOnClick(event, this)"/></div>
	</body>
</html>