package myPlotter;

public class plotSomething {

    public static void main(String[] args) throws Exception {
	    String prefix = "F:/GridSimResults/"; 
	    //String networkUsageFilename = "makespan_comparison.csv";
	    //plotter.plot(prefix + networkUsageFilename, "bandwidth (Gbps)","makespan (s)" );
	    //plotter.plot(prefix + "KISTI_improvement.csv", "bandwidth (Gbps)","makespan improvement (%)" );
	    //comparisonPlotter.plot(prefix + "2cites_interconnected_CPU_comparison.csv",
	    //	    " 1 remote site ", "time (s)", "CPU usage");
	    /*MakespanComparisonPlotter.plot(prefix + "1site_makespan_comparison.csv",
	    	    " 1 remote site ", "Bandwidth (Gbps)", "makespan");
	    MakespanComparisonPlotter.plot(prefix + "KISTI_improvement_vs_par.csv",
	    	    " ", "Bandwidth (Gbps)", "makespan improvement (%)");*/
	   // MakespanComparisonPlotter.plot(prefix + "3nodes/3nodes_makespans.csv",
	   // 	    " ", "Bandwidth (Gbps)", "Makespan (s)");
	    //MakespanComparisonPlotter.plot(prefix + "3nodes/3nodes_relative_makespan.csv",
	    //	    " ", "Bandwidth (Gbps)", "Makespan");
	    //MakespanComparisonPlotter.plot(prefix + "3nodes/3nodes_makespans_improvement.csv",
	    	//    " ", "Bandwidth (Gbps)", "Makespan improvement (%)");
	    
	    //MakespanComparisonPlotter.plot(prefix + "cpu/cpu_scalability_makespan.csv",
	    //	    " ", "Number of CPUs", "Makespan (s)");
	    //MakespanComparisonPlotter.plot(prefix + "cpu/cpu_scalability_makespan_relative.csv",
	    //	    " ", "Number of CPUs", "Makespan");
	    //MakespanComparisonPlotter.plot(prefix + "cpu/cpu_scalability_makespan_relative_constant.csv",
	    	//    " ", "Number of CPUs", "Makespan");
	    //MakespanComparisonPlotter.plot(prefix + "cpu/cpu_scalability_makespan_improvement.csv",
	    	//    " ", "Number of CPUs", "Makespan improvement (%)");
	    //MakespanComparisonPlotter.plot(prefix + "1node/1node_relative_makespan_vs_bandwidth.csv",
	    //	    " ", "Bandwidth (Gbps)", "Makespan");
	    //MakespanComparisonPlotter.plot(prefix + "1node/1node_makespan_vs_bandwidth.csv",
	    //	    " ", "Bandwidth (Gbps)", "Makespan (s)");
	    //MakespanComparisonPlotter.plot(prefix + "1node/1node_makespan_improvement_vs_bandwidth.csv",
	    //	    " ", "Bandwidth (Gbps)", "Makespan improvement (%)");
	    	    
	    MakespanComparisonPlotter.plot(prefix + "BackGroungTraffic/background_multiple_files_relative_makespan.csv",
	    	    "1 node, 1 Gbps link, 1000 CPUs, 24 TB disk, 7000 jobs, (increasing number of junk files)", "Background traffic (% of bandwidth)", "Makespan");
	    MakespanComparisonPlotter.plot(prefix + "BackGroungTraffic/background_multiple_files_makespan_improvement.csv",
	    	    "1 node, 1 Gbps link, 1000 CPUs, 24 TB disk, 7000 jobs, (increasing number of junk files)", "Background traffic (% of bandwidth)", "Makespan improvement (%)");
	    
    }

}
