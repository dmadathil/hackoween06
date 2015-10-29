<html>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular-touch.js"></script>
<script src="/js/backgroundImage.js"></script>
<link rel="stylesheet" type="text/css" href="/js/master.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>
$(document).ready(function(){
    $("button").click(function(){
        $.post("http://localhost:8080/hackoween06/voice",
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

<button><img src=""></button>
</body>
</html>