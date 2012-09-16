import java.util.Stack;

public abstract class Variable implements ExpressionNode
{
    public Variable(String name_)
    {
        name = name_;
    }

    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return name;
    }

    public boolean isDefined()
    {
        return defined;
    }

    public void setDefined()
    {
        defined = true;
    }

    public void setUndefined()
    {
        defined = false;
    }

    public boolean getValue()
    {
        return value;
    }

    public void setValue(boolean value)
    {
        this.value = value;
    }

    public void apply(Stack<Boolean> executionStack)
    {
        executionStack.push(value);
    }

    protected String name;
    protected boolean value;
    protected boolean defined = false;

}
