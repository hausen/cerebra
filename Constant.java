public class Constant implements ExpressionNode
{
    Constant(String value)
    {
        this.value = Integer.parseInt(value, 10);
    }

    public String toString()
    {
        return Integer.toString(value);
    }

    public boolean isDefined()
    {
        return true;
    }

    public void apply(java.util.Stack<Boolean> executionStack)
    {
        if ( value == 0 )
            executionStack.push(false);
        else
            executionStack.push(true);
    }

    protected int value;
}
