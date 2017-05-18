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
<!--
HTML tags 
Used for formatting purpose. Processor will skip them and browser 
will simply render them.
-->
<html>
<head>
		
	<title>Smart home</title>
	
	<!-- Bootstrap and page styles -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" href="style.css"/>
    
    <!-- Fonts -->    
	<link href="https://fonts.googleapis.com/css?family=Hind:400,700" rel="stylesheet"/>
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet"/>
	<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet"/>
	 
	<!-- Javascript --> 
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"/>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"/>
   	<script src="Ui.js"/>
   
</head>

<body>
	<div class="container-fluid">
		<!-- Title -->
		<div id="title" class="row">
			<h1><xsl:value-of select="DomobusSystem/House/Name"/></h1>
		</div>
		
		<!-- Subtitle -->
		<div id="subtitle" class="row">
			<h2><xsl:value-of select="DomobusSystem/House/DivisionList/Division/Name"/></h2>
		</div>
	</div> <!-- container-fluid -->
	
	
	<br/>
	<div class="container">
		<xsl:for-each select="DomobusSystem/DeviceList/Device">
			<xsl:sort select="ID"/>
			
			
			<!-- Ceiling lamp sensor -->
			<xsl:if test="RefDevicetype=2">
			
			<div category="{RefDevicetype}" value="false" value_m="false" value_i="0" id="{ID}" name="{Name}">
				<div class="col-md-3">
					<h4><xsl:value-of select="Name"/></h4>
					
					<!-- Ceiling lamp buttons -->
					<div class="row">
						<div class="col-md-4"><span align="right">OFF</span></div>
						<div class="col-md-4">
							<label class="switch">
								<input type="checkbox" id="regul_switch" oninput="update(this.parentNode.parentNode.parentNode.parentNode.parentNode.id,2,-1)"/>
								<div class="slider round"></div>
							</label>
						</div>
						<div class="col-md-4"><span>ON</span></div>
					</div>
					
					<!-- Manual/Auto settings buttons -->
					<div class="row">
						<div class="col-md-4"><span align="right">Manual</span></div>
						
						<div class="col-md-4">
							<label class="switch">
								<input type="checkbox" id="regul_switch2"  oninput="update(this.parentNode.parentNode.parentNode.parentNode.parentNode.id,1,-1)"/>
								<div class="slider round"></div>
							</label>
						</div>
						
						<div class="col-md-4"><span>Auto</span></div>
					</div> <!-- row Manual/Auto-->
					
					<!--  Slider -->
					<div class="row">
						<div class="col-md-4"><span align="right">0</span></div>
						
						<div class="col-md-4">
							<input type="range" id="regul_intensity" min="0" value="0" max="100" step="10"
								oninput="update(this.parentNode.parentNode.parentNode.parentNode.id,3,this.value)"/>
						</div>
						
						<div class="col-md-4"><span>100</span></div>
					</div> <!-- slider -->
				</div> <!-- Col-md-3 -->
			</div> <!-- Ceiling lamp sensor -->
			
			</xsl:if>
		
			<!--Simple Light sensors-->
			<xsl:if test="RefDevicetype=3">
				<div category="{RefDevicetype}" value="false" id="{ID}" name="{Name}">
					<div class="col-md-3">
						<div class="row">
							<h4><xsl:value-of select="Name"/></h4>
						</div>
						
						<div class="row">
							<div class="col-md-4">OFF</div>
							
							<div class="col-md-4">
								<label class="switch">
									<input type="checkbox" id="simple_switch"  oninput="update(this.parentNode.parentNode.parentNode.parentNode.parentNode.id,2,-1)"/>
									<div class="slider round"></div>
								</label>
							</div>
							
							<div class="col-md-4">ON</div>
						</div> <!-- row -->
					</div>
				</div> <!-- Simple light sensors -->
			</xsl:if>
					
			<!-- Temperature sensors -->
			<xsl:if test="RefDevicetype=1">
				<div category="{RefDevicetype}" id="{ID}" name="{Name}">
					<div class="col-md-3">
						<div class="row">
							<h4><xsl:value-of select="Name"/></h4>
						</div>
						
						<div class="row">
							<div class="simple" id="temp_value"></div>
						</div>
					</div>
				</div> <!-- Temperature sensors -->
			</xsl:if>
	
			<!-- Fire sensor -->
			<xsl:if test="RefDevicetype=4">
				<div category="{RefDevicetype}" id="{ID}" name="{Name}">
					<div class="col-md-3">
						<div class="row">
							<h4><xsl:value-of select="Name"/></h4>
						</div>
						
						<div class="row">
							<div class="simple" id="alarm">OFF</div>
						</div>
					</div>
				</div> <!-- Fire sensor -->
			</xsl:if>  
		   
		</xsl:for-each>	   
	</div>

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