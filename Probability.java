import java.math.BigDecimal;
import java.math.RoundingMode;

public class Probability implements Comparable<Probability>
{
    public Probability()
    {
        this.value = new BigDecimal(0.0);
    }

    public Probability(double value)
    {
        this.value = new BigDecimal(value);
    }

    public Probability(String value)
    throws NumberFormatException
    {
        this.value = new BigDecimal(value);
        // if value < 0 or value > 1
        if ( this.value.compareTo(BigDecimal.ZERO) < 0 ||
             this.value.compareTo(BigDecimal.ONE) > 0 )
        {
            throw new NumberFormatException(
                             "invalid probability: " + value );
        }
    }

    public void add(Probability p)
    {
        value = value.add(p.value);
    }
 
    public void multiply(Probability p)
    {
        value = value.multiply(p.value);
    }

    public void divide(Probability p)
    {
        value = value.divide(p.value, RoundingMode.HALF_EVEN);
    }

    public Probability getComplement()
    {
        Probability ret = new Probability( 1.0 );
        ret.value = ret.value.subtract( this.value );
        return ret;
    }

    public String toString()
    {
        return value.toString();
    }

    public int compareTo(Probability p)
    {
        return value.compareTo(p.value);
    }

    public boolean isZero()
    {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }

    protected BigDecimal value;

}
