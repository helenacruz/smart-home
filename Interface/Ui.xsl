<?xml version = "1.0" encoding = "UTF-8"?>
<!-- xsl stylesheet declaration with xsl namespace: 
Namespace tells the xlst processor about which 
element is to be processed and which is used for output purpose only 
-->
<xsl:stylesheet version = "2.0" xmlns:xsl = "http://www.w3.org/1999/XSL/Transform">

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
	   <script src="Ui.js"/>
	   <head>
		
	  <title>Smart home</title>
	  
	  <style>
	  header, footer
{
    width: 100%;
}

html, body
{
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
}

#title
{
    width: 100%;
    color: #FFFFFF;
    border: none;
    border-collapse: collapse;
    text-align: left;
    font-family: 'Roboto', sans-serif;
}

#title h1
{
    margin-left: 10%;
    font-size: 240%;
}

#title h2
{
    margin-left: 10%;
    font-size:210%;
}

#title tr:nth-child(1)
{
    background: #00BFFF;
    line-height: 500%;
}

#title tr:nth-child(2)
{
    background: #87CEFA;
    line-height: 350%;
}

.devices
{
	display: inline-block;
    margin-top: 3%;
    width: 20%;
	height: 50%;
    font-family: 'Source Sans Pro', sans-serif;
	border-spacing: 20px;
}

.devices
{
    font-size: 120%;
    padding-left: 50px;
}

.switch 
{
	margin-top: 5px;
    position: relative;
    display: inline-block;
	margin-left: 5px;
	margin-right: 5px;
    width: 60px;
    height: 34px;
}

.switch input
{
    display: none;
}

.slider
{
    position: absolute;
    cursor: pointer;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #ccc;
    -webkit-transition: .4s;
    transition: .4s;
    border-radius: 34px;
}

.slider:before 
{
    position: absolute;
    content: "";
    height: 26px;
    width: 26px;
    left: 4px;
    bottom: 4px;
    background-color: white;
    -webkit-transition: .4s;
    transition: .4s;
    border-radius: 50%;
}

input:checked + .slider
{
    background-color: #2196F3;
}

input:focus + .slider
{
    box-shadow: 0 0 1px #2196F3;
}

input:checked + .slider:before
{
    -webkit-transform: translateX(26px);
    -ms-transform: translateX(26px);
    transform: translateX(26px);
}

.simple
{
    font-size: 150%;
}

.text
{
	position: relative;
	bottom : 10px;
	text-align: center;
}

#regul_intensity
{
width: 60%;
}

.none{
display: none;
}

.buttons 
{
	text-align: center;	
}


</style>
	  
<link href="https://fonts.googleapis.com/css?family=Hind:400,700" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet"/>
	</head>
	<body>
	<header>
    <table id="title">
        <tr>
            <th><h1><xsl:value-of select="DomobusSystem/House/Name"/></h1></th>
        </tr>
        <tr>
            <th><h2><xsl:value-of select="DomobusSystem/House/DivisionList/Division/Name"/></h2></th>
        </tr>
    </table>
	</header>
	
	
	 <xsl:for-each select="DomobusSystem/DeviceList/Device">
	 <xsl:sort select="ID"/>

				<xsl:if test="RefDevicetype=2"><!--Regul Light-->
				<table class="devices" category="{RefDevicetype}" value="false" value_m="false" value_i="0" id="{ID}" name="{Name}">
				<tr>
					<th padding-left="10px"><xsl:value-of select="Name"/></th>
				</tr>
				 		<tr>
				 			<td><span>OFF</span></td>
				 			<td>
				 				<label class="switch">
								<input   type="checkbox" id="regul_switch" oninput="update(this.parentNode.parentNode.parentNode.parentNode.parentNode.id,2,-1)"/>
								<div class="slider round"></div>
								</label>
				 			</td>
				 			<td><span>ON</span></td>
				 		</tr>
				 		<tr>
				 			<td><span>MANUAL</span></td>
				 			<td>
				 				<label class="switch">
								<input type="checkbox" id="regul_switch2"  oninput="update(this.parentNode.parentNode.parentNode.parentNode.parentNode.id,1,-1)"/>
								<div class="slider round"></div>
								</label>
				 			</td>
				 			<td><span>AUTO</span></td>
				 		</tr>
				 		<tr>
				 			<td><span>0</span></td>
				 			<td>
				 				<input type="range" id="regul_intensity" min="0" value="0" max="100" step="10"  oninput="update(this.parentNode.parentNode.parentNode.parentNode.id,3,this.value)" />
				 			</td>
				 			<td><span>100</span></td>
				 		</tr>
				</table>
				</xsl:if>
				
				<xsl:if test="RefDevicetype=3"><!--Simple Light-->
				<table class="devices" category="{RefDevicetype}" value="false" id="{ID}" name="{Name}">
	
				<tr>
				<th padding-left="10px"><xsl:value-of select="Name"/></th>
				</tr>
				<tr>
				 			<td><span>OFF</span></td>
				 				<td><label class="switch">
								<input type="checkbox" id="simple_switch"  oninput="update(this.parentNode.parentNode.parentNode.parentNode.parentNode.id,2,-1)"/>
								<div class="slider round"></div>
								</label></td>
				 			<td><span>ON</span></td>

				</tr>
				</table>
				</xsl:if>

				<xsl:if test="RefDevicetype=1"><!--Temp sensor-->
				<table class="devices" category="{RefDevicetype}" id="{ID}" name="{Name}">
	
				<tr>
					<th><xsl:value-of select="Name"/></th>
				</tr>
				<tr>
				</tr>
				<tr>
				<td><p class="simple" id="temp_value">21 DC</p></td>
				</tr>
				</table>
				</xsl:if>

				<xsl:if test="RefDevicetype=4"><!--Fire sensor-->
				<table class="devices" category="{RefDevicetype}" id="{ID}" name="{Name}">
				
				<tr>
					<th><xsl:value-of select="Name"/></th>
				</tr>

				<tr>
				<td><p class="simple" id="alarm">I am always Alert...</p></td>
				</tr>
				</table>
				</xsl:if>     
    <!-- rest of the table-->
</xsl:for-each>	   


<div class ="none">
<xsl:for-each select="DomobusSystem/House">
<div class="House" id="{ID}" name="{Name}" division_id="{DivisionList/Division/ID}" division_name="{DivisionList/Division/Name}"/>	  
</xsl:for-each>

<xsl:for-each select="DomobusSystem/ScalarValueTypeList/ScalarValueType">
<div class= "ScalarValueType" id="{ID}" Name="{Name}" Units="{Units}" min="{MinValue}" max="{MaxValue}"	step="{Step}"/>  
</xsl:for-each>	  
	  
<xsl:for-each select="DomobusSystem/EnumeValueTypeList/EnumeValueType">	  
<div class= "EnumeValueType" id="{ID}" Name="{Name}"/> 
</xsl:for-each>

<xsl:for-each select="DomobusSystem/DeviceTypeList/DeviceType">	  
<div class= "DeviceType" id="{ID}" Name="{Name}"/> 	  
<xsl:for-each select="PropertyList/Property">
<div class="Property" id="{ID}" parent_id="{../../Name}" Name="{Name}" ValueType="{ValueType}" RefValueType="{RefValueType}"/>
</xsl:for-each>
</xsl:for-each>
</div>
		  
 </body>
	  </html>
	  <script>check_all()</script>
	  
   </xsl:template>  
</xsl:stylesheet>