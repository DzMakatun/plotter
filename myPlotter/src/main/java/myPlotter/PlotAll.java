package myPlotter;

public class PlotAll {

    public static void main(String[] args) throws Exception {
	    String prefix1 = "F:/git/Grid_simulation/grid/src/main/java/flow_model/output/";  
	    String prefix2 = "F:/git/Grid_simulation/grid/output/"; 
	    //Utilization plots
	    String[] UtilizationFilenams = {"STORAGE_statistics.csv", "FAST_statistics.csv", "SLOW_statistics.csv", "MEDIUM_statistics.csv"};
	    //String[] UtilizationFilenams = {"KISTI_statistics.csv"};
	    for (String filename: UtilizationFilenams){
		ResourceUtilizationPlotter.plot(prefix1 + filename, filename.split("_")[0] + " (PLANER)");
		ResourceUtilizationPlotter.plot(prefix2 + filename, filename.split("_")[0]+ " (PUSH)");
	    }
	    
	    //network usage
	    String networkUsageFilename = "network_usage.csv";
	    plotter.plot(prefix1 + networkUsageFilename, "network usage (PLANER)", "time (s)", "usage");
	    
	    //planned flows
	    String plannedFlowsFilename = "Planer_planned_net_usage.csv";
	    plotter.plot(prefix1 + plannedFlowsFilename,"planned network usage (PLANER)", "time (s)", "planned usage");
	    
	    plotter.plot("F:/git/Grid_simulation/grid/output/network_usage.csv",
		    "network usage (PUSH)","time (s)", "usage");
	    //ResourceUtilizationPlotter.plot("F:/git/Grid_simulation/grid/output/FAST_statistics.csv");
	    //ResourceUtilizationPlotter.plot("F:/git/Grid_simulation/grid/output/SLOW_statistics.csv");
	    
	    //global CPU usage
	    String globalCpuUsageFilename = "CpuUsage.csv";
	    plotter.plot(prefix1 + globalCpuUsageFilename,"CPU usage at computational nodes (PLANER)", "time (s)", "CPU usage");
	    plotter.plot(prefix2 + globalCpuUsageFilename,"CPU usage at computational nodes (PUSH)", "time (s)", "CPU usage");
	    
	    //compare total CPU usage
	    String[] filenames = {prefix1 + globalCpuUsageFilename, prefix2 + globalCpuUsageFilename};
	    String[] seriesNames = {"PLANER", "PUSH"};
	    CompareFromDifferentFiles.plot(filenames, seriesNames,
		    "TOTAL", "", "time (s)", "CPU usage");
	    

    }

}
