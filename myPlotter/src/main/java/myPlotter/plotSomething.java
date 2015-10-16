package myPlotter;

public class plotSomething {

    public static void main(String[] args) throws Exception {
	    String prefix = "F:/GridSimResults/"; 
	    String networkUsageFilename = "makespan_comparison.csv";
	    //plotter.plot(prefix + networkUsageFilename, "bandwidth (Gbps)","makespan (s)" );
	    //plotter.plot(prefix + "KISTI_improvement.csv", "bandwidth (Gbps)","makespan improvement (%)" );
	    //comparisonPlotter.plot(prefix + "2cites_interconnected_CPU_comparison.csv",
	    //	    " 1 remote site ", "time (s)", "CPU usage");
	    MakespanComparisonPlotter.plot(prefix + "1site_makespan_comparison.csv",
	    	    " 1 remote site ", "Bandwidth (Gbps)", "makespan");
	    MakespanComparisonPlotter.plot(prefix + "KISTI_improvement_vs_par.csv",
	    	    " ", "Bandwidth (Gbps)", "makespan improvement (%)");
    }

}
