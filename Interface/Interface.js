var Switch = 0;
var value = 1;
var result;

function outputUpdate(value){
console.log(value);
	if(value==1)
		result= "Low";
	else if( value==2)
		result= "Medium";
	else if(value==3)
		result="High";

var d = document.getElementById("itensity_chosen");
d.innerHTML=result;
d= document.getElementById("0");
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
	d= document.getElementById('0');
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
	console.log(document.getElementById("device_name"));
	document.getElementById("device_name").innerHTML=el.name;
}