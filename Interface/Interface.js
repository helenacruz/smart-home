var Switch = 0;
var value = 1;
var button_pressed=null;
var result;
var slidebar;

function outputUpdate(value){
	if(value==1)
		result= "Low";
	else if( value==2)
		result= "Medium";
	else if(value==3)
		result="High";

var d = document.getElementById("itensity_chosen");
d.innerHTML=result;
d= document.getElementById(button_pressed);
d.itensity=result;
d.innerHTML=d.name;
d.innerHTML+="<br/>";
if(d.value==0)
	d.innerHTML+="OFF";
else if(d.value==1)
	d.innerHTML+="ON";
d.innerHTML+="<br/>";
d.innerHTML+="Itensity:";
d.innerHTML+=d.itensity;
	
}

function UpdateSwitch(){
	var d;
	console.log(button_pressed);
	d= document.getElementById('button_pressed');
	var result;
	if(Switch == 0){
		result = 1;
		Switch = 1;
	}
	else if(Switch == 1){
		result =0;
		Switch = 0;
	}
	
	d.value=result;
	d.innerHTML=d.name;
	d.innerHTML+="<br/>";
	
	if(d.value==0)
		d.innerHTML+="OFF";
	else if(d.value==1)
		d.innerHTML+="ON";
	
	d.innerHTML+="<br/>";
	d.innerHTML+="Itensity:";
	d.innerHTML+=d.getAttribute("itensity");
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
	
}

function CleanCanvas(){
	var canvas = document.getElementById("Canvas");
	canvas.style.display="inline-block";
	slidebar = document.getElementById("bar");
	if(slidebar.style.display!="none")
		slidebar.style.display="none";
}