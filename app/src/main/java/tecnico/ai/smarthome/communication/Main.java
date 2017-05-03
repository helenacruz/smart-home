package tecnico.ai.smarthome.communication;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import java.awt.Desktop;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

public class Main
{
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException
    {
    	File input = new File("Interface/Interface.xml");
    	File input2 = new File("Interface/Interface.xsl");
    	
    	TransformerFactory factory = TransformerFactory.newInstance();
        Source stylesheetSource = new StreamSource(new File("Interface/Interface.xsl").getAbsoluteFile());
        Transformer transformer = factory.newTransformer(stylesheetSource);
        Source inputSource = new StreamSource(new File("Interface/Interface.xml").getAbsoluteFile());
        Result outputResult = new StreamResult(new File("Interface/Output.html").getAbsoluteFile());
        transformer.transform(inputSource, outputResult);
    	
        
        File file = new File("Interface/Output.html");
        try {
			Desktop.getDesktop().browse(file.toURI());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        String URL= "file:///"+file.getAbsolutePath();
        
        
        
		
    }
}
