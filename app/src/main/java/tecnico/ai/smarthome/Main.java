package tecnico.ai.smarthome;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;

import javax.xml.transform.TransformerException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.sun.net.httpserver.HttpServer;

import tecnico.ai.smarthome.communication.Message;
import tecnico.ai.smarthome.communication.SerialPortReader;
import tecnico.ai.smarthome.ui.Request;
import tecnico.ai.smarthome.ui.Server;
import tecnico.ai.smarthome.ui.Myhandler;

public class Main
{
    public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, TransformerException
    {
        SerialPortReader serialPortReader = new SerialPortReader();

        // TODO separate thread for the server
    	Server serv = new Server();
    	Myhandler myhandler = new Myhandler();
    	 HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
         server.createContext("/r.html", myhandler );
         server.setExecutor(null); // creates a default executor
    	 server.start();
        while (true) {
           serialPortReader.run(); // thread for the serial port
            Message message = serialPortReader.getMessage();
            if (message != null) {//send to HTML
               serv.update(message.getDevice(),message.getProperty(),message.getValue());
            }
            Request request = myhandler.getMessage();
            if (request != null) {//send to Arduino
            	Message msg =new Message(request.getId(),request.getCategory(),request.getValue());
            	serialPortReader.sendMessage(msg.toArduinoMessage());
                System.out.println("recebi"+request.getId());
            }
        }
    }
}