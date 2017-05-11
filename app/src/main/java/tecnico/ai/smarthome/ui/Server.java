package tecnico.ai.smarthome.ui;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tecnico.ai.smarthome.domobus.Device;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread
{
	private static HttpHandler myhandler = new MyHandler();

	public Server()
    {
        try {
            File input = new File("Interface/Interface.xml");
            File input2 = new File("Interface/Interface.xsl");
            List<Device> myList = new ArrayList<Device>();


            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/r.html", myhandler);
            server.setExecutor(null); // creates a default executor
            server.start();
            System.out.println("Running server");

            TransformerFactory factory = TransformerFactory.newInstance();
            Source stylesheetSource = new StreamSource(new File("Interface/Interface.xsl").getAbsoluteFile());
            Transformer transformer = factory.newTransformer(stylesheetSource);
            Source inputSource = new StreamSource(new File("Interface/Interface.xml").getAbsoluteFile());
            Result outputResult = new StreamResult(new File("Interface/Output.html").getAbsoluteFile());
            transformer.transform(inputSource, outputResult);


            File file = new File("Interface/Output.html");
            try {
                Desktop.getDesktop().browse(file.toURI());
            }
            catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            Document dom = Jsoup.parse(file, "UTF-8");

            Elements devices = dom.getElementsByClass("button");

            for (int i = 0; i < devices.size(); i++) {
                Element e = (Element) devices.get(i);
                Device d = new Device(Integer.parseInt(e.attr("id")), e.attr("name"), null);
                myList.add(d);//integrate Domobus left
                System.out.println(d.getId());
            }

            update(3, "intensity", 30);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            int id;
            String category;
            int value;
            String[] params = t.getRequestURI().getQuery().split("&");
            
            
            id = Integer.parseInt(params[0].split("=")[1]);
            category = params[1].split("=")[1];
            value = Integer.parseInt(params[2].split("=")[1]);
            
            System.out.println("id-->"+id);
            System.out.println("cat-->"+category);
            System.out.println("val-->"+value);
            t.sendResponseHeaders(200,0);
            OutputStream os = t.getResponseBody();
            os.write( t.getRequestURI().getQuery().getBytes());
            os.close();
        }
    }
    
    
    public static void update(int id, String category, int new_value) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
    	java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
    	File file = new File("Interface/Output.html");
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.waitForBackgroundJavaScriptStartingBefore(10000);
        String URL= "file:///"+file.getAbsolutePath();
		HtmlPage page = webClient.getPage(URL);
		
		String javaScriptCode="Receive(" + String.valueOf(id) + "," +"'"+category+"'" +","+String.valueOf(new_value)+")";
		//Receive(3,'i',30) for example
        webClient.waitForBackgroundJavaScript(10000);
        ScriptResult result = page.executeJavaScript(javaScriptCode);
        webClient.close();
        file.delete();
        file = new File("Interface/Output.html");
        
        
        String text = result.toString();
        text=text.substring(20);
        //text="<!DOCTYPE HTML>" + "\n" + text;
        text=text.substring(0,text.lastIndexOf("\n"));
        text+="</body>";
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
