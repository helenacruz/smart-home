package tecnico.ai.smarthome;

import java.awt.EventQueue;
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
import tecnico.ai.smarthome.ui.App;
import tecnico.ai.smarthome.ui.Server;
import tecnico.ai.smarthome.ui.SmartHome;

public class Main
{
    @SuppressWarnings("restriction")
	public static void main(String[] args)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException, TransformerException
    {
    	ArrayList<String> messages = new ArrayList<>();
    	SerialPortReader serialPort = new SerialPortReader();
    	App app = new App(messages);
    	EventQueue.invokeLater(app);
    	
    	while (true) {
    		// System.out.println("here");
    		serialPort.run();
    		Message message = serialPort.getMessage();
    		if (message != null) {
    			System.out.println(message);
    			app.getInstance().update(message.getDevice(), message.getProperty(), message.getValue());
    		}
    		if (!messages.isEmpty()) {
    			String arduinoMessage = messages.get(0);
    			System.out.println("TO SEND: " + arduinoMessage);
    			messages.remove(0);
    			serialPort.sendMessage(arduinoMessage);
    		}
    	}
    }
   
}