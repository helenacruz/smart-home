package pt.tecnico.ai.domobus;

public class Enumerated extends Value
{
    private int value;
    private String designation;

    public Enumerated(int value, String designation)
    {
        this.value = value;
        this.designation = designation;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public String getDesignation()
    {
        return designation;
    }

    public void setDesignation(String designation)
    {
        this.designation = designation;
    }
}
