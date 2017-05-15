package tecnico.ai.smarthome;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import tecnico.ai.smarthome.communication.Message;
import tecnico.ai.smarthome.communication.SerialPortReader;
import tecnico.ai.smarthome.ui.Server;

public class Main
{
    public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, TransformerException
    {
        SerialPortReader serialPortReader = new SerialPortReader();

        // TODO separate thread for the server
    	Server serv = new Server();
    	 HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
         server.createContext("/r.html", new HttpHandler() {
     		public void handle(HttpExchange t) throws IOException{
        	 int id;
             int category;
             int value;
             String[] params = t.getRequestURI().getQuery().split("&");
             System.out.println("recebido");
             
             id = Integer.parseInt(params[0].split("=")[1]);
             category = Integer.parseInt(params[0].split("=")[1]);
             value = Integer.parseInt(params[2].split("=")[1]);
             if(category==1)
             	category=2;
             else if(category==2)
             	category=1;
             t.sendResponseHeaders(200,0);
             
            Message msg =new Message(id,category,value);
            System.out.println("sending "+id + category + value +"to "+ serialPortReader);
         	serialPortReader.sendMessage(msg.toArduinoMessage());
             
             OutputStream os = t.getResponseBody();
             os.close();
         }
         
         });
         server.setExecutor(null); // creates a default executor
    	 server.start();
    	 
    	 
        while (true) {
           serialPortReader.run(); // thread for the serial port
            Message message = serialPortReader.getMessage();
            if (message != null) {//send to HTML
               serv.update(message.getDevice(),message.getProperty(),message.getValue());
            }
        }
    }
}