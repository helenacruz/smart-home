
function change_temp(value) {
	/*
	 * var d = document.getElementById(id).rows; console.log(d[index]);
	 * d[index].elementbyClassName("simple");=value+" ºC"; } "<p class="simple">21
	 * ºC</p>"
	 */

	var d = document.getElementById("temp_value");
	console.log(d);
	d.innerHTML = value + "ºC";
}



// cat: Categoria de sensor
// 1-- manual/auto
// 2--switch on/off
// 3-- Intensity
function change(id, cat, value) {
	var d = document.getElementById(id);

	if (d.getAttribute("category") == "1") // temperature
		change_temp(value);
	else if (d.getAttribute("category") == "2") {// regul_light
		if (cat == 1) {
			if (value == 1) {
				document.getElementById("regul_switch2").checked = true;
				d.setAttribute("value_m", "true");
			} else if (value == 0) {
				document.getElementById("regul_switch2").checked = false;
				d.setAttribute("value_m", "false");
			} else {
				console.log("Wrong Value parameter");
			}
		} else if (cat == "2") {
			if (value == 1) {
				document.getElementById("regul_switch").checked = true;
				d.setAttribute("value", "true");
			} else if (value == 0) {
				document.getElementById("regul_switch").checked = false;
				d.setAttribute("value", "false");
			} else
				console.log("Wrong Value parameter");
		} else if (cat == "3") {
			if (document.getElementById("regul_switch2").checked == "true") {
				console.log("its in auto cannot be changed");
			} else {
				document.getElementById("regul_intensity").setAttribute(
						"value", value);
				d.setAttribute("value_i", value);
			}
		} else
			console.log("Wrong category chosen");
	} else if (d.getAttribute("category") == "3") {// simple_light
		if (value == 1) {
			document.getElementById("simple_switch").checked = true;
			d.setAttribute("value", true);
		} else if (value == 0) {
			document.getElementById("simple_switch").checked = false;
			d.setAttribute("value", true);
		} else
			console.log("Wrong Value parameter");
	} else if (d.getAttribute("category") == "4") {// fire alarm
		if (value == 1) {
			document.getElementById("fire_alarm").innerHTML = "On fire!";
			d.setAttribute("on_fire", true);
			alert("THERE IS A FIRE!!!!!!!!!!!!!!!!!!");
		} else if (value == 0) {
			document.getElementById("fire_alarm").innerHTML = "OFF";
			d.setAttribute("on_fire", false);
		} else
			console.log("Wrong Value parameter");
	}

	return document.documentElement.outerHTML;
}

// --------------------------------------------------------------- HTML ->
// Arduino
// -----------------------------------------------------------------------

function update(id, cat, value) {// cat: 1-- manual/auto 2--switch on/off 3--
									// itensity
	var d = document.getElementById(id);
	console.log(d);
	if (cat != 3) {
		if (d.getAttribute("checked") == true)
			value = 0;
		else
			value = 1;
	}
	console.log("Sent " + id + " and " + cat + " and " + value);
	SendHTTP(id, cat, value);
}

function SendHTTP(id, category, value) {
	var message = "http://localhost:8000/r.html?id=" + id + "&category="
			+ category + "&value=" + value;
	var wnd = window.open(message);
	setTimeout(function() {
		wnd.close();
	}, 100);
	return false;
}

function check_all() {
	if (document.getElementById(1).getAttribute("value") == "true") {
		document.getElementById("regul_switch").checked = true;
	} else if (document.getElementById(1).getAttribute("value") == "false") {
		document.getElementById("regul_switch").checked = false;
	}

	if (document.getElementById(2).getAttribute("value") == "true") {
		document.getElementById("simple_switch").checked = true;
	} else if (document.getElementById(2).getAttribute("value") == "false") {
		document.getElementById("simple_switch").checked = false;
	}

	if (document.getElementById(1).getAttribute("value_m") == "true") {
		document.getElementById("regul_switch2").checked = true;
	} else if (document.getElementById(1).getAttribute("value_m") == "false") {
		document.getElementById("regul_switch2").checked = false;
	}

	document.getElementById("regul_intensity").setAttribute("value",
			document.getElementById(1).getAttribute("value_i"));

	if (document.getElementById(4).getAttribute("on_fire") == "true") {
		document.getElementById("fire_alarm").innerHTML = "On fire!";
	} else if (document.getElementById(4).getAttribute("on_fire") == "false") {
		document.getElementById("fire_alarm").innerHTML = "OFF";
	}
}