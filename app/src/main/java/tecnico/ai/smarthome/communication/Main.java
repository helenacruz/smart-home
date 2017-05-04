package tecnico.ai.smarthome.communication;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tecnico.ai.smarthome.domobus.Device;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import java.awt.Desktop;
import javax.xml.transform.stream.*;

public class Main
{
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException
    {
    	File input = new File("Interface/Interface.xml");
    	File input2 = new File("Interface/Interface.xsl");
    	List<Device> myList = new ArrayList<Device>();
    	
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
        
        Document dom = Jsoup.parse(file, "UTF-8");
        
        Elements devices = dom.getElementsByClass("button");
        
        for(int i =0;i<devices.size();i++){
        	 Element e = (Element) devices.get(i);
        	Device d = new Device(Integer.parseInt(e.attr("id")), e.attr("name"), null);
        	myList.add(d);//integrate Domobus left
        	System.out.println(d.getId());
        }
        
        String URL= "file:///"+file.getAbsolutePath();
        
        
        
		
    }
}
