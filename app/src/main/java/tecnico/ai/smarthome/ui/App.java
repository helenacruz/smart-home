package tecnico.ai.smarthome.ui;

import java.util.ArrayList;

import tecnico.ai.smarthome.communication.SerialPortReader;

public class App implements Runnable {
	
	private SmartHome app;
	private ArrayList<String> messages;
	
	public App(ArrayList<String> messages) {
		app = new SmartHome(messages);
		this.messages = messages;
	}
	
	public SmartHome getInstance() {
		return app;
	}
	
	@Override
	public void run() {
		app.frame.setVisible(true);
	}

}
