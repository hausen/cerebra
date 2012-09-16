public class OperationNot extends Operation
{
    OperationNot() {}

    public void apply(java.util.Stack<Boolean> executionStack)
    {
        boolean b1 = executionStack.pop();
        executionStack.push(!b1);
    }

    public String toString()
    {
        return "!";
    }
}
