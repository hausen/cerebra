import java.io.*;
import java.util.List;
import java.util.Map;
import org.antlr.runtime.*;
import org.apache.commons.cli.*;

public class Cerebra
{
    public static void main(String args[]) throws Exception
    {
        parseCommandlineArguments(args);

        CerebraLexer lex = new CerebraLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);

        CerebraParser g = new CerebraParser(tokens);
        try {
            ProblemDescription pd = g.file();
            BinaryMultivariableEvaluator ev =
                             new BinaryMultivariableEvaluator(
                                              pd.nonobservables,
                                              pd.equations );
            for ( Map.Entry<Integer, String> out :
                      pd.outputComments.entrySet() )
            {
                Integer OutputID = out.getKey();
                String comment = out.getValue();
                Map<String,Boolean> intervention =
                    pd.interventions.get( OutputID );
                List<ObservableVariable> margin =
                    pd.margins.get( OutputID );
                
                Probability[] bins = ev.evaluateAssignments(
                                         margin, intervention  );
                output.println("OUTPUT " + OutputID + ": " + comment);
                ev.prettyPrintDistribution( output, margin, bins );
                output.println("END OUTPUT");
                output.println();
            }
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
    }

    public static void parseCommandlineArguments(String args[])
    throws org.apache.commons.cli.ParseException, IOException
    {
        Options opts = new Options();
        opts.addOption("h", false, "Print help for this application");
        opts.addOption("i", true, "Input File");
        opts.addOption("o", true, "Output File");

        BasicParser parser = new BasicParser();
        CommandLine cl = parser.parse(opts, args);

        if ( cl.hasOption('h') )
        {
            HelpFormatter f = new HelpFormatter();
            f.printHelp("Cerebra", opts);
            System.exit(0);
        }
        if ( cl.hasOption('i') )
        {
            input = new ANTLRFileStream(cl.getOptionValue("i"));
        }
        else
        {
            input = new ANTLRInputStream(System.in);
        }

        if ( cl.hasOption('o') )
        {
            output = new PrintStream(
                            new FileOutputStream(cl.getOptionValue("o")) );
        }
        else
        {
            output = System.out;
        }
    }

    public static CharStream input;
    public static PrintStream output;
}
