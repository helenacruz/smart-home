package tecnico.ai.smarthome.ui;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import tecnico.ai.smarthome.domobus.DomobBusSystem;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;

public class Server extends Thread
{
	private DomobBusSystem domobus;
		public Server() throws FailingHttpStatusCodeException, MalformedURLException, IOException, TransformerException{

            File file = new File("Interface/Output.html");
            TransformerFactory factory = TransformerFactory.newInstance();
            Source stylesheetSource = new StreamSource(new File("Interface/Ui.xsl").getAbsoluteFile());
            Transformer transformer = factory.newTransformer(stylesheetSource);
            Source inputSource = new StreamSource(new File("Interface/Ui.xml").getAbsoluteFile());
            Result outputResult = new StreamResult(new File("Interface/Output.html").getAbsoluteFile());
            transformer.transform(inputSource, outputResult);
            
            
             file = new File("Interface/Output.html");
            try {
                Desktop.getDesktop().browse(file.toURI());
            }
            catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            /*
            ////Putting everything into classes
           Document dom = Jsoup.parse(file, "UTF-8");
           Elements el = dom.getElementsByClass("House");
           domobus = new DomobBusSystem(el.attr("id"), el.attr("name"), el.attr("division_id"), el.attr("division_name"));
           el = dom.getElementsByClass("ScalarValueType");
           for(Element e : el){
        	   domobus.getScalars().add(new Scalar(e.attr("min"), e.attr("max"), e.attr("units"), e.attr("name")));
           }
           el = dom.getElementsByClass("EnumeValueType");
           for(Element e : el){
        	   domobus.getEnums().add(new Enumerated(e.attr("name")));
           }
           el = dom.getElementsByClass("DeviceType");
           for(Element e : el){
        	   domobus.getDevice_types().add(new DeviceType(e.attr("name")));
           }
           el = dom.getElementsByClass("Property");
           for(Element e : el){
        	   Property prop = new Property(e.attr("name"), null, e.attr("valuetype"), e.attr("refvaluetype"));
        	   for(DeviceType d: domobus.getDevice_types()){
        		   if(e.attr("parent_id").equals(d.getName())){
        			   d.getProperties().put(prop.getName(), prop);
        		   }
        	   }
           }*/
    }
    
    
    public void update(int id, int category, int new_value) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
    	java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
    	File file = new File("Interface/Output.html");
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.waitForBackgroundJavaScriptStartingBefore(10000);
        String URL= "file:///"+file.getAbsolutePath();
		HtmlPage page = webClient.getPage(URL);
		
		System.out.println("ID: " + id + " Cat: " + category + " Value: " + new_value);
		
		String javaScriptCode="change(" + String.valueOf(id) + "," +"'"+category+"'" +","+String.valueOf(new_value)+")";
        webClient.waitForBackgroundJavaScript(10000);
        ScriptResult result = page.executeJavaScript(javaScriptCode);
        webClient.close();
        file.delete();
        file = new File("Interface/Output.html");
        
        
        String text = result.toString();
        text=text.substring(20);
        text="<!DOCTYPE HTML>" + "\n" + text;
        text=text.substring(0,text.length()-96);
        PrintStream out = new PrintStream(new FileOutputStream(file));
        out.print(text);
        
        try {
			Desktop.getDesktop().browse(file.toURI());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
    }
}
