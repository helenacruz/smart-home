package tecnico.ai.smarthome.domobus;

public class Property extends PropertyType
{
    private String value;

    public Property(String name, AccessMode accessMode, String valueType, String value)
    {
        super(name, accessMode, valueType);
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
