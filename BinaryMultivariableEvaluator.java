import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class BinaryMultivariableEvaluator
{
    public BinaryMultivariableEvaluator(
                       List<NonObservableVariable> nonobservables,
                       List<ObservableVariable> equations )
    {
        this.nonobservables = nonobservables;
        this.equations = equations;
    }

    public Probability[] evaluateAssignments(
                       List<ObservableVariable> margin,
                       Map<String,Boolean> intervention )
    {
        int nbins = (int)Math.pow(2,margin.size());
        Probability bins[] = new Probability[nbins];
        for ( int i=0; i<nbins; ++i )
            bins[i] = new Probability();

        long nAssignments = (long)Math.pow(2,nonobservables.size());
        for (long i=0; i<nAssignments; ++i)
        {
            assignNonObservables( i );
            for ( ObservableVariable eq : equations )
            {
                Boolean value = intervention.get(eq.getName());
                if ( value != null )
                    eq.setValue(value);
                else
                    eq.reevaluate();
            }
            addProbability(margin, bins);
        }

        return bins;
    }

    public void prettyPrintDistribution( PrintStream out,
                         List<ObservableVariable> margin,
                         Probability[] bins )
    {
        for (ObservableVariable ov : margin )
        {
            out.print(ov.getName());
            out.print("\t");
        }
        out.println("Prob.");

        for ( int i=0; i<bins.length; ++i )
        {
            int nbin = i;
            StringBuffer sb = new StringBuffer();
            for ( int j = 0; j < margin.size(); ++j)
            {
                sb.append("\t");
                sb.append(nbin & 0x1);
                nbin = nbin >> 1;
            }
            out.print(sb.reverse());
            out.println(bins[i]);
        }
    }

    protected void assignNonObservables(long bits)
    {
        for ( NonObservableVariable nov : nonobservables )
        {
            boolean value = ( ( bits & 0x1L ) == 0x1L );
            nov.setValue(value);
            bits = bits >> 1;
        }
    }

    protected void addProbability( List<ObservableVariable> margin,
                                   Probability[] bins )
    {
        Probability p = new Probability(1.0);
        for ( NonObservableVariable nov : nonobservables )
        {
            Probability varProb = nov.getValue() ? nov.getProbability() :
                                  nov.getProbability().getComplement();
            p.multiply( varProb );
        }
        bins[hashObservables(margin)].add(p);
    }

    protected int hashObservables(List<ObservableVariable> list)
    {
        int hash = 0;
        for ( ObservableVariable ov : list )
        {
            hash = hash << 1;
            hash = hash | ( ov.getValue() ? 0x1 : 0x0 );
        }
        return hash;
    }

    private List<NonObservableVariable> nonobservables;
    private List<ObservableVariable> equations;
}
