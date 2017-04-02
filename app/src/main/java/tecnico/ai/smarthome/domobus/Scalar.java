package tecnico.ai.smarthome.domobus;

public class Scalar extends Value
{
    private int min;
    private int max;
    private String units;
    private Conversion conversion;

    public Scalar(int min, int max, String units, Conversion conversion)
    {
        this.min = min;
        this.max = max;
        this.units = units;
        this.conversion = conversion;
    }

    public int getMin()
    {
        return min;
    }

    public void setMin(int min)
    {
        this.min = min;
    }

    public int getMax()
    {
        return max;
    }

    public void setMax(int max)
    {
        this.max = max;
    }

    public String getUnits()
    {
        return units;
    }

    public void setUnits(String units)
    {
        this.units = units;
    }

    public Conversion getConversion()
    {
        return conversion;
    }

    public void setConversion(Conversion conversion)
    {
        this.conversion = conversion;
    }
}
