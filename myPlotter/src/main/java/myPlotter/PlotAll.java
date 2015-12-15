package myPlotter;

public class PlotAll {

    public static void main(String[] args) throws Exception {
	    String description = "1 node, 1 Gbps link, 1000 CPUs, 01 TB disk, 7000 jobs, 0.1 Gbps background (1 thread)";
	    //String prefix1 = "F:/GridSimResults/3nodes/planer/";
	    String prefix1 = "F:/git/Grid_simulation/grid/src/main/java/flow_model/output/";  
	    String prefixPUSHseq = "F:/git/Grid_simulation/grid/src/main/java/push_model/output/"; 
	    String prefixPUSHpar = "F:/git/Grid_simulation/grid/src/main/java/PUSH_parallel_transfer/output/";
	    //Utilization plots
	    //String[] UtilizationFilenams = {"STORAGE_statistics.csv", "FAST_statistics.csv", "SLOW_statistics.csv", "MEDIUM_statistics.csv"};
	    String[] UtilizationFilenams = {"RCF_statistics.csv", "KISTI_statistics.csv"};
	    for (String filename: UtilizationFilenams){
		ResourceUtilizationPlotter.plot(prefix1 + filename, filename.split("_")[0]);
		//ResourceUtilizationPlotter.plot(prefixPUSHpar + filename, filename.split("_")[0]+ " (PUSH_par)");
	    }
	    
	    
	    //planned flows
	    String plannedFlowsFilename = "Planer_planned_net_usage.csv";
	    plotter.plot(prefix1 + plannedFlowsFilename,"planned network usage (PLANER)", "time (days)", "planned usage");
	    //network usage
	    String networkUsageFilename = "network_usage.csv";
	    plotter.plot(prefix1 + networkUsageFilename, "network usage (PLANER)", "time (days)", "usage");	    
	    //plotter.plot(prefixPUSHpar + "network_usage.csv",
		//    "network usage (PUSH_par)","time (s)", "usage");
	    //plotter.plot(prefixPUSHseq + "network_usage.csv",
		//    "network usage (PUSH_seq)","time (s)", "usage");

	    
	    //global CPU usage
	    String globalCpuUsageFilename = "CpuUsage.csv";
	    //plotter.plot(prefix1 + globalCpuUsageFilename,"CPU usage at computational nodes (PLANER)", "time (s)", "CPU usage");
	    //plotter.plot(prefixPUSHpar + globalCpuUsageFilename,"CPU usage at computational nodes (PUSH_par)", "time (s)", "CPU usage");
	    //plotter.plot(prefixPUSHseq + globalCpuUsageFilename,"CPU usage at computational nodes (PUSH_seq)", "time (s)", "CPU usage");
	    
	    //compare total CPU usage
	    String[] filenames = {  prefixPUSHpar + globalCpuUsageFilename, prefixPUSHseq + globalCpuUsageFilename, prefix1 + globalCpuUsageFilename};//
	    String[] seriesNames = { "PUSH_par", "PUSH_seq", "PLANNER" };//
	    CompareFromDifferentFiles.plot(filenames, seriesNames,
	        "TOTAL", description, "Time (days)", "CPU usage (%)", 100);
	    
	    //compare network load
	    String queueLengthStatFilename = "RCF_router_to_KISTI_router_statistics.csv";
	    String flowsNoFilename = "RCF_KISTI_link_statistics.csv";
	    String[] StatFilenames = {prefixPUSHpar + flowsNoFilename, prefixPUSHseq + queueLengthStatFilename, prefix1 + queueLengthStatFilename};//
	    //seriesNames = { "PUSH_par", "PUSH_seq", "PLANNER" };//
	    CompareFromDifferentFiles.plot(StatFilenames, seriesNames,
		        "queue", description, "Time (days)", "queued file transfers", 1);
		    

    }

}
