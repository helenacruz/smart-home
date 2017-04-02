package tecnico.ai.smarthome.domobus;

public class Array extends Value
{
    private byte[] array;
    private int length;

    public Array(byte[] array)
    {
        this.array = array;
        // TODO: length
    }

    public byte[] getArray()
    {
        return array;
    }

    public void setArray(byte[] array)
    {
        this.array = array;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }
}
