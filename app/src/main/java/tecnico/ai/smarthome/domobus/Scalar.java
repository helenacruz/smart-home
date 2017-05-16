package tecnico.ai.smarthome.domobus;

public class Scalar extends Value
{
    private String min;
    private String max;
    private String units;
    private String name;

    public Scalar(String string, String string2, String units, String name)
    {
        this.min = string;
        this.max = string2;
        this.units = units;
        this.setName(name);
    }

    public String getMin()
    {
        return min;
    }

    public void setMin(String min)
    {
        this.min = min;
    }

    public String getMax()
    {
        return max;
    }

    public void setMax(String max)
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
