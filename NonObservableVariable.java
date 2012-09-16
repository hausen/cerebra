public class NonObservableVariable extends Variable
{
    public NonObservableVariable(String nome)
    {
        super(nome);
    }

    public void setProbability(Probability prob)
    {
        probability = prob;
        setDefined();
    }

    public Probability getProbability()
    {
        return probability;
    }

    protected Probability probability = null;
}
