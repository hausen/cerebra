public class OperationXor extends Operation
{
    OperationXor() {}

    public void apply(java.util.Stack<Boolean> executionStack)
    {
        boolean b1 = executionStack.pop();
        boolean b2 = executionStack.pop();
        executionStack.push(b1 ^ b2);
    }

    public String toString()
    {
        return "+";
    }
}
