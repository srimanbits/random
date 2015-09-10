$(document).ready(function(){
	$( "#alert").dialog({
		autoOpen: false,
		width: 300,
		draggable: true,
		resizable: true,
		maxHeight: 400,
		modal:true,
		buttons: [
			{
				text: "OK",
				click: function() {
					$( this ).dialog( "close" );
				}				
			}
		]
	});	
	
	$( "#reloadConfirmation").dialog({
		autoOpen: false,
		width: 300,
		draggable: true,
		resizable: true,
		maxHeight: 400,
		modal:true,
		buttons: [
			{
				text: "Reload",
				click: function() {
					window.location.reload();
				}				
			},{
				text: "Close",
				click: function() {
					 window.close();
				}				
			}
		]
	});
});

function setDialog(message,id) {
	$('#'+id+' P').empty();
	$('#'+id+' P').append($("<div></div>").text(message).html());
};

function notification(message){
	$.blockUI({ 
        message: message, 
        fadeIn: 700, 
        fadeOut: 700, 
        timeout: 2000, 
        showOverlay: false, 
        centerY: false, 
        css: { 
            width: '300px', 
            top: '10px', 
            left: '', 
            right: '10px', 
            border: 'none', 
            padding: '5px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .6, 
            color: '#fff' 
        } 
    }); 	
};

function applyDatePicker(selector,root){
	 $(selector).datepicker({
			showOn: "button",
			buttonImage: root+"common/images/calendar.png",
			buttonImageOnly: true,
			duration: 'fast',
//			changeMonth: true,
//	        changeYear: true,
	        dateFormat: 'dd-mm-yy'
		});
};



function getPageLocation(element){
	var pageLocation;
	var p1=$(element).html();
	if(p1 == 'Performance Management Tool Kit' || p1 == 'Org Chart'){
		pageLocation=p1;
	} else {
		var p0=$(element).parent().parent().parent().find("a").html();
		pageLocation=p0+' > '+p1;
	}
   	return pageLocation;
};
	

function preventBackspace(e) {
    var evt = e || window.event;
    if (evt) {
        var keyCode = evt.charCode || evt.keyCode;
        if (keyCode === 8) {
        	evt.returnValue = false;
        } else {
            evt.returnValue = false;
        }
    }
}