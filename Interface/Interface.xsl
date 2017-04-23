<?xml version = "1.0" encoding = "UTF-8"?>
<!-- xsl stylesheet declaration with xsl namespace: 
Namespace tells the xlst processor about which 
element is to be processed and which is used for output purpose only 
-->
<xsl:stylesheet version = "1.0" 
xmlns:xsl = "http://www.w3.org/1999/XSL/Transform">   
<!-- xsl template declaration:  
template tells the xlst processor about the section of xml 
document which is to be formatted. It takes an XPath expression. 
In our case, it is matching document root element and will 
tell processor to process the entire document with this template. 
--> 
   <xsl:template match = "/"> 
      <!-- HTML tags 
         Used for formatting purpose. Processor will skip them and browser 
            will simply render them. 
      --> 
	   <html> 
	  <head>
	  
	  <script src="Interface.js"></script>
	  
	  <title>Arduino SmartHome</title>
	  
	  <style>
	.button {
	border: solid;
		background-color: #d3d3d3;
		color: black;
		padding: 10px 32px 0px 50px;
		text-align: center;
		text-decoration: none;
		display: inline-block;
		font-size: 16px;
		margin: 4px 2px;
		cursor: pointer;
	}
	
	 /* The switch - the box around the slider */
		.switch {
		position: relative;
		display: inline-block;
		width: 63px;
		height: 34px;
	}

	/* Hide default HTML checkbox */
		.switch input {display:none;}

	/* The slider */
		.slider {

		position: absolute;
		cursor: pointer;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: #ccc;
		-webkit-transition: .4s;
		transition: .4s;
	}

	.slider:before {
		position: absolute;
		content: "OFF";
		height: 26px;
		width: 30px;
		left: 4px;
		bottom: 4px;
		background-color: white;
		-webkit-transition: .4s;
		transition: .4s;
	}

	input:checked + .slider {
		background-color: #2196F3;
	}

	input:focus + .slider {
		box-shadow: 0 0 1px #2196F3;
	}

	input:checked + .slider:before {
		content:"ON";
		-webkit-transform: translateX(26px);
		-ms-transform: translateX(26px);
		transform: translateX(26px);
	}
	
	.Canvas {
		width: 100%;
		height: 230px;
		display: none;
		border-style: double;
	}
	
	.bar {
		display: none;
		padding-left: 50px;
	}
	
	.device_name{
		font-size: 30px;
		text-transform: uppercase;
	}
	
	.regulator{
		display: none;
		padding-left: 50px;
		width: 100%;
		height: 80px;
	}
	
	.button2 {
		border: solid;
		background-color: #d3d3d3;
		color: black;
		width: 60px;
		text-align: center;
		text-decoration: none;
		display: inline-block;
		font-size: 16px;
		margin: 4px 2px;
		cursor: pointer;
	}
	
	.temp_value {
		border: solid;
		color: black;
		width: 40px;
		text-align: center;
		text-decoration: none;
		display: inline-block;
		font-size: 16px;
		margin: 4px 2px;
		cursor: pointer;
	}
	
	</style>
	  </head>
		
    
         <body>
		 <h2>Smart Home Arduino</h2>
		 <xsl:for-each select="Devices/Device">
		 <xsl:sort select="ID"/>
		 <button class="button" onclick ="MakeCanvas(this)" category="{Type}" value="{Value}" id="{ID}" name="{Name}"><xsl:if test="Itensity" ><xsl:attribute name="itensity"><xsl:value-of select="Itensity"/></xsl:attribute></xsl:if>
		 <xsl:value-of select="Name"/><br/>
			<xsl:choose>
				<xsl:when test="Value=0">
				OFF
				</xsl:when>
				<xsl:otherwise>
				ON
				</xsl:otherwise>
			</xsl:choose> 
			<br/>
		<!--<xsl:if test="Itensity" >
				Itensity:<xsl:value-of select="Itensity"/>
			</xsl:if> -->

		<xsl:choose>
				<xsl:when test="Itensity">
				<xsl:choose>
				<xsl:when test = "Itensity=1">
				Itensity: Low
				</xsl:when>
				<xsl:when test = "Itensity=2">
				Itensity: Medium
				</xsl:when>
				<xsl:when test = "Itensity=3">
				Itensity: High
				</xsl:when>
				<xsl:otherwise>
				<br/>
				</xsl:otherwise>
				</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
				<br/> <!-- insert so that buttons are all the same size -->
				</xsl:otherwise>
		</xsl:choose>
		</button>
		</xsl:for-each>
		<div class ="Canvas" id="Canvas"><span class="device_name" id="device_name"></span>
		<br/>  <br/>  
			<span class="bar" id="bar" >Itensity:
			<input type="range" id="weight" min="1" value="0" max="3" step="1" oninput="outputUpdate(value)" />
			<output for="weight" id="itensity_chosen">Low</output>
			</span>
			<span class="log" id="log">
				
			</span>
			<p/>
			<label class="regulator" id="regulator">
			<button class="button2" onclick="DecreaseTemp()">-</button>
			<span class="temp_value" id="temp_value"></span>
			<button class="button2" onclick="IncreaseTemp()">+</button>
			</label>
			<label class="switch" >
				<input id="checkbox" type="checkbox" onclick="UpdateSwitch()"/>
				<div class="slider"></div>
			</label>
		 </div>

		 </body>
	  </html>
   </xsl:template>  
</xsl:stylesheet>