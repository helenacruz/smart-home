package tecnico.ai.smarthome.domobus;

import java.util.function.Function;

public class Conversion
{
    private final Function conversion;

    public Conversion(Function conversion)
    {
        this.conversion = conversion;
    }

    public Function getConversion()
    {
        return conversion;
    }
}
