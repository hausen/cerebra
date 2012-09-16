import java.util.LinkedList;
import java.util.Stack;

public class ObservableVariable extends Variable
{
    ObservableVariable(String name) { super(name); }

    public void setExpression(LinkedList<ExpressionNode> exp)
    throws VariableUndefinedException
    {
        for ( ExpressionNode node : exp )
        {
            if ( !node.isDefined() )
            {
                throw new VariableUndefinedException("expression for variable " + name + " contains an undefined node " + node);
            }
        }
        expression = exp;
        setDefined();
    }

    public String toString()
    {
        StringBuffer ret = new StringBuffer(name);
        if ( expression != null )
        {
            ret.append(" = ");
            for ( ExpressionNode node : expression )
            {
                if ( node instanceof Variable )
                {
                    Variable var = (Variable)node;
                    ret.append(var.getName());
                }
                else
                {
                    ret.append( node.toString() );
                }
                ret.append(" ");
            }
        }
        return ret.toString();
    }

    public boolean reevaluate()
    {
        Stack<Boolean> stack = new Stack<Boolean>();
        for ( ExpressionNode node : expression )
        {
            node.apply(stack);
        }
        value = stack.pop();
        return value;
    }

    public LinkedList<ExpressionNode> getExpression()
    {
        return expression;
    }

    protected LinkedList<ExpressionNode> expression = null;
}
