package tecnico.ai.smarthome.ui;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Myhandler implements HttpHandler {
	public List<Request> requests = new ArrayList<Request>();
	
    @Override
    public void handle(HttpExchange t) throws IOException {
        int id;
        int category;
        int value;
        String[] params = t.getRequestURI().getQuery().split("&");
        
        
        id = Integer.parseInt(params[0].split("=")[1]);
        category = Integer.parseInt(params[0].split("=")[1]);
        value = Integer.parseInt(params[2].split("=")[1]);
        if(category==1)
        	category=2;
        else if(category==2)
        	category=1;
        Request r = new Request(id,category,value);
        t.sendResponseHeaders(200,0);
        add(r);
        OutputStream os = t.getResponseBody();
        os.close();
    }

    public Request getMessage() {
		if(!requests.isEmpty())
		System.out.println(requests.size());
		if (!requests.isEmpty()) {
            Request message = requests.get(0);
            requests.remove(0);
            return message;
        }
		return null;
	}
    
    public void add(Request r){
    	requests.add(r);
    	System.out.println("Adicionei");
    }
}
