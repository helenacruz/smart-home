package tecnico.ai.smarthome.domobus;

import java.util.HashMap;

public class Device
{
    int id;
    private String name;
    private String address;
    private HashMap<String, Property> properties;

    public Device(int id, String name, String address)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        properties = new HashMap<>();
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void addProperty(Property property)
    {
        properties.put(property.getName(), property);
    }

    public Property getProperty(String propertyName)
            throws InexistentPropertyException
    {
        Property property = properties.get(propertyName);

        if (property == null) {
            throw new InexistentPropertyException(propertyName);
        }

        return property;
    }
}
