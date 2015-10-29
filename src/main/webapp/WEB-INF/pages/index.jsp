<html>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular-touch.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" type="text/css" href="/js/master.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<script>
$(document).ready(function(){
    $("#north_button").click(function(){
        $.post("/voice",
        {
          attr1: "data1",
          attr2: "data2"
        },
        function(data,status){
            alert("Data: " + data + "\nStatus: " + status);
        });
    });
    $("#south_button").click(function(){
        $.post("/voice",
        {
          attr1: "data1",
          attr2: "data2"
        },
        function(data,status){
            alert("Data: " + data + "\nStatus: " + status);
        });
    });
    $("#east_button").click(function(){
        $.post("/voice",
        {
          attr1: "data1",
          attr2: "data2"
        },
        function(data,status){
            alert("Data: " + data + "\nStatus: " + status);
        });
    });
    $("#west_button").click(function(){
        $.post("/voice",
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


<div data-role="page" class="room1">

<h2>Welcome ${userName}</h2>   
 
    <div data-role="content">
        
    </div><!-- /content -->

    <div data-role="footer" data-position="fixed">
        <button id="west_button" class="ui-btn ui-btn-inline">West</button>
        <button id="north_button" class="ui-btn ui-btn-inline">North</button>
        <button id="south_button" class="ui-btn ui-btn-inline">South</button>
        <button id="east_button" class="ui-btn ui-btn-inline">East</button>
    </div><!-- /footer -->

</div>
</body>
</html>