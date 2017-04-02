package pt.tecnico.ai.domobus;

import java.util.HashMap;

public class PropertyType
{
    public enum AccessMode { RO, RW, WO }
    public enum ValueType { SCALAR, ENUMERATED, VECTOR }

    private String name;
    private AccessMode accessMode;
    private ValueType valueType;
    private HashMap<String, Integer> enumeration; // in case value is enumeration

    public PropertyType(String name, AccessMode accessMode, ValueType valueType)
    {
        this.name = name;
        this.accessMode = accessMode;
        this.valueType = valueType;
        this.enumeration = null;
    }

    public PropertyType(String name, AccessMode accessMode, ValueType valueType, HashMap<String, Integer> enumeration)
    {
        this.name = name;
        this.accessMode = accessMode;
        this.valueType = valueType;
        this.enumeration = enumeration;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public AccessMode getAccessMode()
    {
        return accessMode;
    }

    public void setAccessMode(AccessMode acessMode)
    {
        this.accessMode = acessMode;
    }

    public ValueType getValueType()
    {
        return valueType;
    }

    public void setValueType(ValueType valueType)
    {
        this.valueType = valueType;
    }

    public HashMap<String, Integer> getEnumeration()
    {
        return enumeration;
    }

    public void setEnumeration(HashMap<String, Integer> enumeration)
    {
        this.enumeration = enumeration;
    }
}
