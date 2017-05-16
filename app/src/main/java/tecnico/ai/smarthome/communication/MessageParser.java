package tecnico.ai.smarthome.communication;

public class MessageParser
{
    public MessageParser()
    {

    }

    public Message parse(String messageText)
    {
        Message message = new Message();

        if (messageText != null && messageText.length() == 16) {

            message.setDevice(Integer.parseInt(String.valueOf(messageText.charAt(14))));

            String propertyText = messageText.substring(4, 7);
            int property = Integer.parseInt(propertyText);
            message.setProperty(property);

            String valueText = messageText.substring(1, 4);
            int value = Integer.parseInt(valueText);
            message.setValue(value);

            return message;
        }

        return null;
    }
}
