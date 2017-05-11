package tecnico.ai.smarthome.communication;

public class Message
{
    int device;
    int property;
    int value;

    public Message()
    {

    }

    public Message(int device, int property, int value)
    {
        this.device = device;
        this.property = property;
        this.value = value;
    }

    public int getDevice()
    {
        return device;
    }

    public void setDevice(int device)
    {
        this.device = device;
    }

    public int getProperty()
    {
        return property;
    }

    public void setProperty(int property)
    {
        this.property = property;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public String toArduinoMessage()
    {
        String message = "<";

        String valueText = Integer.toString(value);
        if (valueText.length() == 1) {
            message += '0';
            message += '0';
        }
        if (valueText.length() == 2) {
            message += '0';
        }

        message += valueText;

        String propText = Integer.toString(property);
        if (propText.length() == 1) {
            message += '0';
            message += '0';
        }
        if (propText.length() == 2) {
            message += '0';
        }

        message += propText;

        message += '1';
        message += '0';
        message += '0';
        message += '0';
        message += '0';

        String deviceText = Integer.toString(device);
        if (deviceText.length() == 1) {
            message += '0';
            message += '0';
        }
        if (deviceText.length() == 2) {
            message += '0';
        }

        message += deviceText;
        message += '>';

        return message;
    }

    @Override
    public String toString()
    {
        return "Device=" + device + " Property=" + property + "Value=" + value;
    }
}
