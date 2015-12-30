package myPlotter;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;

public class PlotAll {

    public static void main(String[] args) throws Exception {
	    String prefix = "AAA";
	    String description = " ";//"1 node, 1 Gbps link, 1000 CPUs, 01 TB disk, 7000 jobs, 0.1 Gbps background (1 thread)";
	    //String prefix1 = "F:/GridSimResults/3nodes/planer/";
	    
	    File PLANNER = new File("F:/git/Grid_simulation/grid/src/main/java/flow_model/output/");
	    File PUSHseq = new File("F:/git/Grid_simulation/grid/src/main/java/push_model/output/");
	    File PUSHpar = new File("F:/git/Grid_simulation/grid/src/main/java/PUSH_parallel_transfer/output/");
	    
	    //File PUSHseq = new File("F:/GridSimResults/LargeGrid/PUSHseq");
	    //File PLANNER = new File("F:/GridSimResults/LargeGrid/PLANNER_NEW");
	    //File PUSHpar = new File("F:/GridSimResults/LargeGrid/PUSHpar");	    
	    
	    File[] folders = {PUSHseq, PLANNER, PUSHpar};
	    
	    File[] PUSHseqFiles = PUSHseq.listFiles();
	    File[] PLANNERFiles = PLANNER.listFiles();
	    File[] PUSHparFiles = PUSHpar.listFiles();
	    
	    File[] allFiles = new File[PUSHseqFiles.length + PLANNERFiles.length + PUSHparFiles.length];
	    
	    System.arraycopy(PUSHseqFiles, 0, allFiles, 0, PUSHseqFiles.length);
	    System.arraycopy(PLANNERFiles, 0, allFiles, PUSHseqFiles.length, PLANNERFiles.length);
	    System.arraycopy(PUSHparFiles, 0, allFiles, PUSHseqFiles.length + PLANNERFiles.length , PUSHparFiles.length);
	    
	    //String prefix1 = "F:/git/Grid_simulation/grid/src/main/java/flow_model/output/";  
	    //String prefixPUSHseq = "F:/git/Grid_simulation/grid/src/main/java/push_model/output/"; 
	    //String prefixPUSHpar = "F:/git/Grid_simulation/grid/src/main/java/PUSH_parallel_transfer/output/";
	    //Utilization plots
	    //String[] UtilizationFilenams = {"STORAGE_statistics.csv", "FAST_statistics.csv", "SLOW_statistics.csv", "MEDIUM_statistics.csv"};
	    for (File file: allFiles){
		System.out.println(file.getAbsolutePath());
		if (file.isFile() && file.getName().startsWith(prefix) && file.length() > 0){
		        //stat file
			if (file.getName().endsWith("_stat.csv") && file.getName().contains("KISTI")){
			    ResourceUtilizationPlotter.plot(file.getAbsolutePath(), 
				    file.getName().split("_")[1] + " (" + file.getName().split("-")[1] +")");
			}				
			//network usage
			if (file.getName().endsWith("network_usage.csv")){
			    plotter.plot(file.getAbsolutePath(), "network usage " + " (" + file.getName().split("-")[1] +")", "time (days)", "usage");
			}			
			//planned net usage
			if (file.getName().endsWith("_planned_net_usage.csv")){
			    plotter.plot(file.getAbsolutePath(), "planned data flows" + " (" + file.getName().split("-")[1] +")", "time (days)", "usage");
			}		
		}

	    }
	    
	    
	    
	    //ALL CPUs AT ONE plot	
	    System.out.println("-----------------ALL CPUs AT ONE plot---------------");
	    File[] files = null;
	    LinkedList<String> filenamesToUse = new LinkedList<String>();
	    LinkedList<String> names = new LinkedList<String>();
	    for (File folder: folders){
		filenamesToUse.clear();
		names.clear();
		files = folder.listFiles();
		for (File file:files){
		    //filter here
		    if( file.isFile() && file.getName().startsWith(prefix) && file.length() > 0 
			    && file.getName().endsWith("_stat.csv")){
			filenamesToUse.add(file.getAbsolutePath());
			names.add(file.getName().split("_")[1]);
		    }
		}
		if ( !filenamesToUse.isEmpty() ){
			CompareFromDifferentFiles.plot(filenamesToUse, names,
				       "busyCPUs", folder.getName(), "Time (days)", "CPU usage", 100);
		}else{
		    System.out.println("FAILED TO FIND FILES");
		}  	    
	    }
	    
	    //Different files	
	    System.out.println("-----------------Different files---------------");
	    String suffix = "_CpuUsage.csv";
	    String field = "TOTAL";
	    
	    files = null;
	    filenamesToUse.clear();
	    names.clear();
	    for (File folder: folders){
		files = folder.listFiles();
		for (File file:files){
		    //filter here
		    if( file.isFile() && file.getName().startsWith(prefix) && file.length() > 0 
			    && file.getName().endsWith(suffix)){
			filenamesToUse.add(file.getAbsolutePath());
			names.add(file.getName().split("-")[1]);
		    }
		}	    
	    }
	    if ( !filenamesToUse.isEmpty() ){
		CompareFromDifferentFiles.plot(filenamesToUse, names,
			field, prefix + "    " +  field, "Time (days)", "CPU usage", 100);
	    }else{
		System.out.println("FAILED TO FIND FILES");
	    }  
	    
	    
	    //planned flows
	    String plannedFlowsFilename = "Planer_planned_net_usage.csv";
	    //plotter.plot(prefix1 + plannedFlowsFilename,"planned network usage (PLANER)", "time (days)", "planned usage");
	    //network usage
	    String networkUsageFilename = "network_usage.csv";
	    //plotter.plot(prefix1 + networkUsageFilename, "network usage (PLANER)", "time (days)", "usage");	    
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
	    //String[] filenames = {  prefixPUSHpar + globalCpuUsageFilename, prefixPUSHseq + globalCpuUsageFilename, prefix1 + globalCpuUsageFilename};//
	    String[] seriesNames = { "PUSH_par", "PUSH_seq", "PLANNER" };//
	    //CompareFromDifferentFiles.plot(filenames, seriesNames,
	     //   "TOTAL", description, "Time (days)", "CPU usage (%)", 100);
	    
	    //compare network load
	    field = "queue";
	    String queueLengthStatFilename = "RCF_router_to_KISTI_router_statistics.csv";
	    String flowsNoFilename = "RCF_KISTI_link_statistics.csv";
	    String[] temp = null;
	    filenamesToUse.clear();
	    names.clear();
	    for (File file: PLANNERFiles){
		if (file.isFile() && file.length() > 0){
		    if (file.getName().endsWith("_router_statistics.csv") ){
			temp = file.getName().split("_");
			//plotter.plot(file.getAbsolutePath(), "transfer queue " + temp[0] + "->" + temp [3] + " (PLANNER) " , "time (days)", "files");			
			filenamesToUse.add(file.getAbsolutePath());
			names.add(temp[0] + "->" + temp [3] + " (PLANNER) " );			
		    }		    
		}
	    }	
	    
	    for (File file: PUSHseqFiles){
		if (file.isFile() && file.length() > 0){
		    if (file.getName().endsWith("_router_statistics.csv") ){
			temp = file.getName().split("_");
			//plotter.plot(file.getAbsolutePath(), "transfer queue " + temp[0] + "->" + temp [3] + " (PUSHseq) " , "time (days)", "files");			
			filenamesToUse.add(file.getAbsolutePath());
			names.add(temp[0] + "->" + temp [3] + " (PUSHseq) " );			
		    }		    
		}
	    }	
	    
	    for (File file: PUSHparFiles){
		if (file.isFile() && file.length() > 0){
		    if (file.getName().endsWith("_link_statistics.csv") && ! file.getName().contains("_link_link_") ){
			temp = file.getName().split("_");
			//plotter.plot(file.getAbsolutePath(), "transfer queue " + temp[0] + "->" + temp [1] + " (PUSHpar) " , "time (days)", "files");			
			filenamesToUse.add(file.getAbsolutePath());
			names.add(temp[0] + "<->" + temp [1] + " (PUSHpar) " );			
		    }		    
		}
	    }	
	    if ( !filenamesToUse.isEmpty() ){
		CompareFromDifferentFiles.plot(filenamesToUse, names,
			field, "Transfer queue", "Time (days)", "files", 1);
	    }else{
		System.out.println("FAILED TO FIND FILES");
	    }

	    //String[] StatFilenames = {prefixPUSHpar + flowsNoFilename, prefixPUSHseq + queueLengthStatFilename, prefix1 + queueLengthStatFilename};//
	    //seriesNames = { "PUSH_par", "PUSH_seq", "PLANNER" };//
	    //CompareFromDifferentFiles.plot(StatFilenames, seriesNames,
		//        "queue", description, "Time (days)", "queued file transfers", 1);
		    

    }

}
