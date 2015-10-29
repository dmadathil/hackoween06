<html ng-app="app">
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular-touch.js"></script>
<script src="/js/backgroundImage.js"></script>

<body>

<div background-image="/images/landing-page1.png"></div>
<div class="overlay layer1" bg-img 
     data-bg-src="/images/landing_page1.png" 
     data-bg-size="cover"
     data-bg-repeat="no-repeat"
     data-bg-attachment="fixed">
      <div class="line center-content fixed">
      <div class="separator100"></div>
      <div class="separator100"></div>
      <div class="separator100"></div>
      <div class="col4 offset-left3">
        <input type="text" class="bg-black color-white radius5 no-margin font-fontawesome center-content" placeholder="&nbsp; &#xf030; &nbsp;Paste an img url ..." ng-keyup="setBg($event.target.value)"/>
      </div>
    </div>
</div>  

<div ng-app="">
  <p>Name: <input type="text" ng-model="name"></p>
  <p>{{name}}</p>
</div>
</body>
</html>