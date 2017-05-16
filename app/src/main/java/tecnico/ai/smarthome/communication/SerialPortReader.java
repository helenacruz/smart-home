package tecnico.ai.smarthome.communication;

import arduino.Arduino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SerialPortReader implements Runnable
{
    private static final String PORT = "COM3";
    private static final int BAUD_RATE = 9600;

    private String message;
    private List<Message> messages;
    private Arduino serialPort;
    private MessageParser messageParser;

    public SerialPortReader()
    {
        message = "";
        messages = Collections.synchronizedList(new ArrayList<Message>());
        serialPort = new Arduino(PORT, BAUD_RATE);
        serialPort.openConnection();
        messageParser = new MessageParser();
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public Message getMessage()
    {
        if (!messages.isEmpty()) {
            Message message = messages.get(0);
            messages.remove(0);
            return message;
        }

        return null;
    }

    public void sendMessage(String message)
    {
        serialPort.serialWrite(message);
    }

    @Override
    public void run()
    {
        String str = serialPort.serialRead(16);
        if (!str.isEmpty()) {
            for (int i = 0, n = str.length(); i < n; i++) {
                char c = str.charAt(i);
                if (c == '<') {
                    message += c;
                }
                else if (c == '>') {
                    message += c;

                    Message messageObj = messageParser.parse(message);

                    if (messageObj != null) {
                        messages.add(messageObj);
                    }

                    message = "";
                }
                else if (c == '\n') {
                    // do nothing
                }
                else {
                    message += c;
                }
            }
        }
    }
}
