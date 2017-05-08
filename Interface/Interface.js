var value = 1;
var button_pressed=null;
var result;
var slidebar;
var i;
var correspondence_onoff ={
			0:"OFF",
			1:"ON"
	};
var correspondence_itensity ={
			1:"Low",
			2:"Medium",
			3:"High"
	};

function outputUpdate(value){
	result=value;

var d = document.getElementById("itensity_chosen");
d.innerHTML=correspondence_itensity[result];

d=document.getElementsByClassName("button");
for(i=0; i<d.length ;i++){
	if(d[i].getAttribute("category")=="LED"){
		d[i].setAttribute("itensity",result);
		d[i].innerHTML=d[i].name;
		d[i].innerHTML+="<br/>";
	
	
		d[i].innerHTML+=correspondence_onoff[d[i].value];
		
		d[i].innerHTML+="<br/>";
		d[i].innerHTML+="Itensity:";
		d[i].innerHTML+=correspondence_itensity[result];
	
	}	
}

	
}

function UpdateSwitch(){
	var d;
	d= document.getElementById(button_pressed);
	var result;
	if(d.value==0){
		result = 1;
		document.getElementById("checkbox").checked = true;
	}
	else if(d.value == 1){
		result =0;
		document.getElementById("checkbox").checked = false;
	}
	
	d.value=result;
	d.innerHTML=d.name;
	d.innerHTML+="<br/>";
	d.innerHTML+= correspondence_onoff[d.value];
	d.innerHTML+="<br/>";
	if(d.getAttribute("category")=="LED"){
		d.innerHTML+="Itensity:";
		d.innerHTML+=correspondence_itensity[d.getAttribute("itensity")];
	}
	else
		d.innerHTML+="<br/>";	
	
	SendHTTP(d.getAttribute("id"),"itensity",d.getAttribute("itensity"));////EXAMPLE------------------------------------
}

function MakeCanvas(el){
	CleanCanvas();
	var canvas = document.getElementById("Canvas");
	canvas.style.display="inline-block";
	
	if(button_pressed!=null){
		var last = document.getElementById(button_pressed);
		last.style.background="#d3d3d3";
	}
	
	el.style.background="#0000FF";
	button_pressed=el.id;
	document.getElementById("device_name").innerHTML=el.name;
	
	if(el.getAttribute("category")=="LED"){
		slidebar = document.getElementById("bar");
		slidebar.style.display="inline-block";
	}
	if(el.value==0)
		document.getElementById("checkbox").checked = false;
	else
		document.getElementById("checkbox").checked = true;
	
	if(el.getAttribute("name")=="Temperature Sensor"){
			document.getElementById("regulator").style.display="inline-block";
			document.getElementById("temp_value").innerHTML=el.getAttribute("itensity");
	}
		
	
}

function IncreaseTemp(){
	var ts = document.getElementById(button_pressed);
	ts.setAttribute("itensity",parseInt(ts.getAttribute("itensity"))+1);
	document.getElementById("temp_value").innerHTML =ts.getAttribute("itensity");
}

function DecreaseTemp(){
	var ts = document.getElementById(button_pressed);
	ts.setAttribute("itensity",parseInt(ts.getAttribute("itensity"))-1);
	document.getElementById("temp_value").innerHTML =ts.getAttribute("itensity");
}


function CleanCanvas(){
	var canvas = document.getElementById("Canvas");
	canvas.style.display="inline-block";
	slidebar = document.getElementById("bar");
	if(slidebar.style.display!="none")
		slidebar.style.display="none";
	var regulator = document.getElementById("regulator");
	if(regulator.style.display!="none")
		regulator.style.display="none";
}


function Receive(id,i,value){
	var d= document.getElementById(id);
	if(i=='i')
	d.setAttribute("itensity",value);
	else
	d.setAttribute("value",value);
	
	return document.documentElement.outerHTML;
}

function SendHTTP(id,category,value){
	var message = "http://localhost:8000/r.html?id="+id+"&category="+category+"&value="+value;
console.log(message);
	var wnd = window.open(message);
    setTimeout(function() {
      wnd.close();
    }, 100);
    return false;
}