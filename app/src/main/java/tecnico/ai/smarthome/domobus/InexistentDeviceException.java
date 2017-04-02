package tecnico.ai.smarthome.domobus;

public class InexistentDeviceException extends Exception
{
    private int id;

    public InexistentDeviceException(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
