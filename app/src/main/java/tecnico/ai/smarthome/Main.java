package tecnico.ai.smarthome;

import tecnico.ai.smarthome.communication.Message;
import tecnico.ai.smarthome.communication.SerialPortReader;
import tecnico.ai.smarthome.ui.Server;

public class Main
{
    public static void main(String[] args)
    {
       // SerialPortReader serialPortReader = new SerialPortReader();

        // TODO separate thread for the server
        Server server = new Server();
        // O servidor estava a dar erro nos ficheiros, por isso e que
        // comentei
       server.main();
        /*while (true) {
            serialPortReader.run(); // thread for the serial port
            Message message = serialPortReader.getMessage();
            if (message != null) {
                System.out.println(message.toArduinoMessage());
            }
        }*/
    }
}