public interface ExpressionNode
{
    public boolean isDefined();

    String toString();

    void apply(java.util.Stack<Boolean> executionStack);
}
