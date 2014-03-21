   function DetectorMarker(map, position) {
        this.map = map;
        this.positon = position;
        
        var marker = new google.maps.Marker({
			draggable : true,
			animation : google.maps.Animation.DROP,
			position : position
		});
        this.marker = marker;
        marker.setMap(map);
		google.maps.event.addListener(marker, "drag", function() {
									var point = marker.getPosition();
									var msgTxt = $("#positionTxt");
									if (msgTxt != null) {
									    msgTxt.val(point.lat().toFixed(6) + ", " + point.lng().toFixed(6));
									}
								});
								
        google.maps.event.addListener(marker, "click", function() {
        							var point = marker.getPosition();
        							var msg = point.lat().toFixed(6) + ", " + point.lng().toFixed(6);
        							if (this.infoWin == undefined) {
        								this.infoWin = new google.maps.InfoWindow();
        								
        							}
        							
						        	this.infoWin.setContent("HH:" + msg);
						        	this.infoWin.open(this.map, marker);
								});	

    }
    
    //DetectorMarker.prototype.getMarker = function() {return DetectorMarker.this.marker;}
    
   DetectorMarker.prototype.bindClickEvent= function(event) {
	   google.maps.event.clearListeners(this.marker, "click");
	   google.maps.event.addListener(this.marker, "click", event);
   }
   
   
   
   function DataMarker(map, position) {
       this.map = map;
       this.positon = position;
       
       var opt = {
               position: position,
               raiseOnDrag: true,
               map: map,
               labelAnchor: new google.maps.Point(22, 0),
               labelClass: "markerLabelWithGreen", // the CSS class for the label
               labelStyle: {opacity: 1.0},
               Icon:'./img/normal_status.png'
             };
       //marker2.setIcon('./img/error_status.png');
	   //marker2.set('labelContent', 'ddd');
	   var marker = new MarkerWithLabel(opt);
       this.marker = marker;
       
       google.maps.event.addListener(marker, "drag", function() {
									var point = marker.getPosition();
									var msgTxt = $("#positionTxt");
									if (msgTxt != null) {
									    msgTxt.val(point.lat().toFixed(6) + ", " + point.lng().toFixed(6));
									}
								});
		
	   var dataMarker = this;
       google.maps.event.addListener(marker, "click", function() {
    	   							dataMarker.showPopWin();
								});	

		
   }
   
   DataMarker.prototype.setMap = function(map) {
	  this.marker.setMap(map);
   }
   
   DataMarker.prototype.setIcon = function(icon) {
	   this.marker.setIcon(icon);
   }
   
 
   DataMarker.prototype.getModel = function() {
	   return this.model;
   }
   
   DataMarker.prototype.setModel = function(model) {
	   this.model = model;
	   this.marker.setIcon('./img/status_' + model.status + '.png');
	   
	   this.marker.set('labelContent', model.id);
	   if (model.status != "1") {
		   this.marker.set('labelClass', 'markerLabelWithRed');
	   }
   }

   DataMarker.prototype.getDeviceId= function() {
	   return this.model.id;
   }
   
   DataMarker.prototype.getPosition = function() {
	   return this.marker.getPosition();
   }
   
   DataMarker.prototype.getMap = function() {
	   return this.marker.getMap();
   }
   
   DataMarker.prototype.bindClickEvent= function(event) {
	   google.maps.event.clearListeners(this.marker, "click");
	   google.maps.event.addListener(this.marker, "click", event);
   }
   
   DataMarker.prototype.bindDetailInfo= function(binder) {
	   if (binder != null) {
		   this.binder = binder;
	   }
   }
  
   DataMarker.prototype.getDetailInfo= function() {
	   var msg;
	   if (this.binder != null) {
		   msg = this.binder(this.model);
		   return msg;
	   } 
	   if (this.model != null) {
		   msg = "<Strong>Device Id</Strong>:" + this.model.id;
      	   msg += "<br/><Strong>Address</Strong>:" + this.model.address;
      	   var content = $('<div>');
      	   $('#vmDetailTemplate').tmpl(this.model.data).appendTo(content);	  
      	   msg += content.html();
      	   msg += "<br/><A href='#'>[Refresh] </Strong>";
       	/*msg = "<Strong>Device Id</Strong>:" + this.model.id;
       	msg += "<br/><Strong>Address</Strong>:" + this.model.address;
       	msg += "<br/><Strong>Sold</Strong>:" + this.model.sold;
       	msg += "<br/><Strong>Stock</Strong>:" + this.model.stock;
       	msg += "<br/>";
       	msg += "<br/><A href='#'>[Refresh] </Strong>";
       	msg += "<A href='#'>[Show History]</Strong>";
       	*/
       } 
	   return msg;
   }
   
   DataMarker.prototype.showPopWin= function() {
	   msg = this.getDetailInfo();
       if (this.infoWin == undefined) {
    	   this.infoWin = new google.maps.InfoWindow();
	   }
			
   	   this.infoWin.setContent(msg);
   	   this.infoWin.open(this.map, this.marker);
   }
   
   
   function DeviceLineMarker(map, positions, models) {
       this.map = map;
       this.positions = positions;
       this.models = models;
       this.markers = new Array();
       this.polyOptions =  {
               strokeColor: '#733100',
               strokeOpacity: 1.0,
               strokeWeight: 4,
               zIndex:100
             }
       var line = new google.maps.Polyline(this.polyOptions);
       line.setMap(map);
       this.line = line;
       for(var i in positions) {
			var marker = new DataMarker(map, positions[i]);
			//var model = models[i];
			marker.setModel(models[i]);
			
			var status = this.models[i].status;
		    var color = "00FF00";
			   if (status == 'INV') {
				   color = "FF0000";
			   } else if (status == 'ALARM') {
				   color = "FFFF00";
			   }
			var icon =  'http://chart.apis.google.com/chart?chst=d_map_spin&chld=0.7|0|' + color +'|9|_|' + models[i].id;
			marker.setIcon(icon);
			marker.bindDetailInfo(function(model) {
				var msg;
			    msg = "<Strong>Device Id</Strong>:" + model.id;
			    msg += "<br/><Strong>Device Type</Strong>:" + model.deviceType;
			    msg += "<br/><Strong>voltage</Strong>:" + model.voltage;
			    msg += "<br/><Strong>temperature</Strong>:" +  model.temperature;
			    msg += "<br/><Strong>power</Strong>:" + model.power;
			    msg += "<br/>";
			    msg += "<br/><A href='#'>[Refresh] </Strong>";
				return msg;
			});
			
			this.markers.push(marker);
			this.line.getPath().push(positions[i]);
	   }
   }
   
   DeviceLineMarker.prototype.hide = function() {
	   for(var i in this.markers) {
			this.markers[i].setMap(null);
	   }
	   this.line.setMap(null);
   }
   
   DeviceLineMarker.prototype.show = function() {
	   for(var i in this.markers) {
			this.markers[i].setMap(this.map);
	   }
	   this.line.setMap(map);
   }
   
   DeviceLineMarker.prototype.setModel = function(model) {
	   this.models = model;
	   for(var i in this.markers) {
			this.markers[i].setModel(model[i]);
	   }
   }
   
   function MarkerGroups(id, map, positions, models) {
	   this.id = id;
	   this.map = map;
       this.positions = positions;
       var deviceMarkers = new Array();
       this.deviceMarkers = deviceMarkers;
       for(var i in positions) {
			var marker = new DataMarker(map, positions[i]);
			marker.setModel(models[i]);
			deviceMarkers.push(marker);
		}
       
       var mcOptions = {gridSize: 4000, maxZoom: 15};
       var mc = new MarkerClusterer(map, deviceMarkers, mcOptions);
       this.mc = mc;
       this.mc.id = id;
      
   }
   
   MarkerGroups.prototype.getMarkers = function() {
	   return this.deviceMarkers;
   }
   
   MarkerGroups.prototype.bindClickEvent = function(eve) {
	   var id = this.id;
	   google.maps.event.addListener(this.mc, 'clusterclick', function(cluster) {
		   eve(id);
	   });
   }
   
   function DeviceList(id) {
	   this.id = id;
   }
   
   DeviceList.prototype.reset = function() {
	   //$(this.id).remove();
	   $(this.id + " ul").remove();
	   $(this.id + " li").remove();
   }
   
   DeviceList.prototype.setModel = function(models, deviceMarkers) {
	   this.models = models;
	   this.deviceMarkers = deviceMarkers;
	   reset();
  	   $('#vmListTemplate').tmpl(this.models).appendTo($(this.id));	  
  	   for(var i in models) {
  		   var link = $('#item-' + models[i].id);
  		   link.data("index", i);
  		   $('#item-' + models[i].id).click(function(i) {
  			   var ind = $(this).data("index");
  			   deviceMarkers[ind].showPopWin();
  		   });
  	   }
   }
   
   function WaterLevelMarker(map, model) {
       this.map = map;
       //this.model = model;
       
       var opt = {
               position: new google.maps.LatLng(model.coordinator.lat, model.coordinator.lng),
               raiseOnDrag: true,
               map: map,
               labelAnchor: new google.maps.Point(22, 0),
               labelClass: "markerLabelWithGreen", // the CSS class for the label
               labelStyle: {opacity: 1.0},
               Icon:'./img/normal_status.png'
             };
       
	   var marker = new MarkerWithLabel(opt);
       this.marker = marker;
       
	   var dataMarker = this;
       google.maps.event.addListener(marker, "click", function() {
    	   							dataMarker.showPopWin();
								});	
       
       this.setModel(model);
		
   }
   
   WaterLevelMarker.prototype.showPopWin= function() {
	   msg = this.getDetailInfo();
       if (this.infoWin == undefined) {
    	   this.infoWin = new google.maps.InfoWindow();
	   }
			
   	   this.infoWin.setContent(msg);
   	   this.infoWin.open(this.map, this.marker);
   }
   
   WaterLevelMarker.prototype.getDetailInfo= function() {
	   var msg;
	   if (this.binder != null) {
		   msg = this.binder(this.model);
		   return msg;
	   } 
	   if (this.model != null) {
		   msg = "<Strong>Device Id</Strong>:" + this.model.id;
      	   msg += "<br/><Strong>Address</Strong>:" + this.model.address;
      	 msg += "<br/><Strong>Water Level</Strong>:" + this.model.data + "Meters";
      	   /*var content = $('<div>');
      	   $('#wlDetailTemplate').tmpl(this.model.data).appendTo(content);	  
      	   msg += content.html();
      	   */
       } 
	   return msg;
   }
   
   
   WaterLevelMarker.prototype.setModel = function(model) {
	   this.model = model;
	   this.marker.setIcon('./img/status_' + model.status + '.png');
	   
	   this.marker.set('labelContent', model.id);
	   if (model.status != "1") {
		   this.marker.set('labelClass', 'markerLabelWithRed');
	   }
   }
   
  
   document.write('<script type="text/javascript" src="./js/vm_detail_template.js" ></script>');
   document.write('<script type="text/javascript" src="./js/wl_detail_template.js" ></script>');
   document.write('<script type="text/javascript" src="./js/vm_list_template.js" ></script>');

   