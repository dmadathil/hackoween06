<html>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular-touch.js"></script>
<link rel="stylesheet" type="text/css" href="/js/master.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>
$(document).ready(function(){
    $("#north_button").click(function(){
        $.post("http://localhost:8080/voice",
        {
          attr1: "data1",
          attr2: "data2"
        },
        function(data,status){
            alert("Data: " + data + "\nStatus: " + status);
        });
    });
});
</script>
</head>
<body>
<div id = "wrapper">
	<div id="north">
		<button id="north_button"><img src="/images/Button_North.png"></button>
	</div>
	<div id="east"><p>Box 1</p></div>
	<div id="central"><p>Box 2</p></div>
	<div id="west"><p>Box 3</p></div>
	<div id="south"></div>
</div>
</body>
</html>