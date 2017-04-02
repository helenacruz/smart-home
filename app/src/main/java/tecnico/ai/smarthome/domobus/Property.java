package tecnico.ai.smarthome.domobus;

public class Property extends PropertyType
{
    private Value value;

    public Property(String name, AccessMode accessMode, ValueType valueType, Value value)
    {
        super(name, accessMode, valueType);
        this.value = value;
    }

    public Value getValue()
    {
        return value;
    }

    public void setValue(Value value)
    {
        this.value = value;
    }
}
