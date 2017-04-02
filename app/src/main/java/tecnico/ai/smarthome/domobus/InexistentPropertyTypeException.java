package tecnico.ai.smarthome.domobus;

public class InexistentPropertyTypeException extends Exception
{
    private String propertyTypeName;

    public InexistentPropertyTypeException(String propertyTypeName)
    {
        this.propertyTypeName = propertyTypeName;
    }

    public String getPropertyTypeName()
    {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName)
    {
        this.propertyTypeName = propertyTypeName;
    }
}
