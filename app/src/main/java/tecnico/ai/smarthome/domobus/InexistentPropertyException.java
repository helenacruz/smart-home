package tecnico.ai.smarthome.domobus;

public class InexistentPropertyException extends Exception
{
    private String propertyName;

    public InexistentPropertyException(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }
}
