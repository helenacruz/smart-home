package tecnico.ai.smarthome.domobus;

import java.util.HashMap;

public class DeviceType
{
    private String name;
    private HashMap<Integer, Device> devices;
    private HashMap<String, PropertyType> properties;

    public DeviceType(String name)
    {
        this.name = name;
        this.devices = new HashMap<>();
        this.properties = new HashMap<>();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public HashMap<Integer, Device> getDevices()
    {
        return devices;
    }

    public void setDevices(HashMap<Integer, Device> devices)
    {
        this.devices = devices;
    }

    public HashMap<String, PropertyType> getProperties()
    {
        return properties;
    }

    public void setProperties(HashMap<String, PropertyType> properties)
    {
        this.properties = properties;
    }

    public void addDevice(int id, Device device)
    {
        devices.put(id, device);
    }

    public Device getDevice(int id) throws InexistentDeviceException
    {
        Device device = devices.get(id);

        if (device == null) {
            throw new InexistentDeviceException(id);
        }

        return device;
    }

    public void addPropertyType(PropertyType propertyType)
    {
        properties.put(propertyType.getName(), propertyType);
    }

    public PropertyType getPropertyType(String propertyTypeName)
            throws InexistentPropertyTypeException
    {
        PropertyType propertyType = properties.get(propertyTypeName);

        if (propertyType == null) {
            throw new InexistentPropertyTypeException(propertyTypeName);
        }

        return propertyType;
    }
}
