package tecnico.ai.smarthome.domobus;

public class Enumerated extends Value
{
    private String designation;

    public Enumerated(String designation)
    {
        this.designation = designation;
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
