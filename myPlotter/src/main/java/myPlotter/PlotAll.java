package myPlotter;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;

public class PlotAll {

    public static void main(String[] args) throws Exception {
	    //String prefix = "ZZ4";
	    String prefix = "T2E";
	    String description = "multiple data sources (test) ";//"1 node, 1 Gbps link, 1000 CPUs, 01 TB disk, 7000 jobs, 0.1 Gbps background (1 thread)";
	    //String prefix1 = "F:/GridSimResults/3nodes/planer/";
	    
	    //File PLANNER = new File("F:/GridSimResults/CHEP2016/GoliasOutput/PLANNERrep3/");
	    //File PUSHseq = new File("F:/GridSimResults/CHEP2016/GoliasOutput/");
	    //File PUSHpar = new File("F:/GridSimResults/CHEP2016/GoliasOutput/");
	    //File PULL = new File("F:/GridSimResults/CHEP2016/GoliasOutput/PULLrep3/");
	    
	    File PLANNER = new File("F:/GridSimResults/backup/flow_model/output/");
            File PUSHseq = new File("F:/GridSimResults/CHEP2016/GoliasOutput/");
            File PUSHpar = new File("F:/GridSimResults/CHEP2016/GoliasOutput/");
            File PULL = new File("F:/GridSimResults/backup/pullModel/output/");
	    
            
	    //F:\GridSimResults\backup\pullModel\
	    
	    //File PLANNER = new File("F:/git/Grid_simulation/grid/src/main/java/flow_model/output/");
	    //File PUSHseq = new File("F:/git/Grid_simulation/grid/src/main/java/push_model/output/");
	    //File PUSHpar = new File("F:/git/Grid_simulation/grid/src/main/java/PUSH_parallel_transfer/output/");
	    //File PULL = new File("F:/git/Grid_simulation/grid/src/main/java/pullModel/output/");
	    
	    //File PUSHseq = new File("F:/GridSimResults/LargeGrid/PUSHseq");
	    //File PLANNER = new File("F:/GridSimResults/LargeGrid/PLANNER_NEW");
	    //File PUSHpar = new File("F:/GridSimResults/LargeGrid/PUSHpar");	    
	    
	    File[] folders = {PUSHpar, PUSHseq, PLANNER, PULL};
	    
	    File[] PUSHseqFiles = PUSHseq.listFiles();
	    File[] PLANNERFiles = PLANNER.listFiles();
	    File[] PUSHparFiles = PUSHpar.listFiles();
	    File[] PULLFiles = PULL.listFiles();
	    
	    File[] allFiles = new File[PUSHseqFiles.length + PLANNERFiles.length + PUSHparFiles.length + PULLFiles.length];
	    
	    System.arraycopy(PUSHparFiles, 0, allFiles, 0, PUSHparFiles.length);
	    System.arraycopy(PUSHseqFiles, 0, allFiles, PUSHparFiles.length, PUSHseqFiles.length);
	    System.arraycopy(PLANNERFiles, 0, allFiles, PUSHparFiles.length + PUSHseqFiles.length, PLANNERFiles.length);
	    System.arraycopy(PULLFiles, 0, allFiles, PUSHparFiles.length + PUSHseqFiles.length + PLANNERFiles.length, PULLFiles.length);
	    
	    //for (File f: PULLFiles){
	       // System.out.println(f.toString());
	    //}
	    
	    //System.arraycopy(PUSHseqFiles, 0, allFiles, 0, PUSHseqFiles.length);
	    //System.arraycopy(PLANNERFiles, 0, allFiles, PUSHseqFiles.length, PLANNERFiles.length);
	    //System.arraycopy(PUSHparFiles, 0, allFiles, PUSHseqFiles.length + PLANNERFiles.length , PUSHparFiles.length);
	    
	    //String prefix1 = "F:/git/Grid_simulation/grid/src/main/java/flow_model/output/";  
	    //String prefixPUSHseq = "F:/git/Grid_simulation/grid/src/main/java/push_model/output/"; 
	    //String prefixPUSHpar = "F:/git/Grid_simulation/grid/src/main/java/PUSH_parallel_transfer/output/";
	    //Utilization plots
	    //String[] UtilizationFilenams = {"STORAGE_statistics.csv", "FAST_statistics.csv", "SLOW_statistics.csv", "MEDIUM_statistics.csv"};
	    for (File file: allFiles){
		//System.out.println(file.getAbsolutePath());
		if (file.isFile() && file.getName().startsWith(prefix) && file.length() > 0){
		        //stat file
			if (file.getName().endsWith("_stat.csv") 
				&& (file.getName().contains("_B10_") || file.getName().contains("_N1_") )
				){
			    //ResourceUtilizationPlotter.plot(file.getAbsolutePath(), 
				//    file.getName().split("_")[1] + (" (" + file.getName().split("-")[1] +")").replaceAll("PUSHpar", "PAR").replaceAll("PUSHseq", "SEQ") );
			}				
			//network usage
			if (file.getName().endsWith("network_usage.csv")){
			    //plotter.plot(file.getAbsolutePath(), "network usage " + (" (" + file.getName().split("-")[1] +")").replaceAll("PUSHpar", "PAR").replaceAll("PUSHseq", "SEQ"), "time (days)", "usage");
			}			
			//planned net usage
			if (file.getName().endsWith("_planned_net_usage.csv")){
			    //plotter.plot(file.getAbsolutePath(), "planned data flows" + " (" + file.getName().split("-")[1] +")", "time (days)", "usage");
			}		
		}

	    }
	    
	    
	    
	    //CPUs of all sites AT ONE plot	
	    System.out.println("-----------------CPUs of all sites AT ONE plot---------------");
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
			    && file.getName().endsWith("_stat.csv")
			    /*&& ( // for L3
				    file.getName().contains("_C14_") 
				 || file.getName().contains("_B10_" )
				 || file.getName().contains("_C5_" )
				 || file.getName().contains("_C6_" )
				 || file.getName().contains("_C20_" ) ) */
			    
			    && ( // for 2L3
			    file.getName().contains("_C11_") 
			 || file.getName().contains("_C7_" )
			 || file.getName().contains("_C5_" )
			 || file.getName().contains("_C3_" )
			 || file.getName().contains("_C20_") 
			 || file.getName().contains("_C14_") 
			 || file.getName().contains("_B10_" ) ) 
			    
			    ){
			filenamesToUse.add(file.getAbsolutePath());
			names.add(file.getName().split("_")[1]);
		    }
		}
		if ( !filenamesToUse.isEmpty() ){
			CompareFromDifferentFiles.plot(filenamesToUse, names,
				"busyCPUs", 
				(filenamesToUse.getFirst().split("-")[1]).substring(0, (filenamesToUse.getFirst().split("-")[1]).length() - 4), 
				"Time (days)", "CPU usage", 100);
				      // "busyCPUs", folder.getParentFile().getName().replace("flow_model", "PLANNER").replace("push_model", "SEQ").replace("PUSH_parallel_transfer", "PAR").replace("pullModel", "PULL"), "Time (days)", "CPU usage", 100);
			//CompareFromDifferentFiles.plot(filenamesToUse, names,
			//	       "jobQueue", folder.getParentFile().getName().replace("flow_model", "PLANNER").replace("push_model", "SEQ").replace("PUSH_parallel_transfer", "PAR").replace("pullModel", "PULL"), "Time (days)", "job queue (% of #CPU)", 100);
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
			    && file.getName().endsWith(suffix)
			    && !file.getName().contains("PUSHpar")
			    ){
			filenamesToUse.add(file.getAbsolutePath());
			names.add(file.getName().split("-")[1].replace("PLANNERrep2", "PLANNER").replace("PULLrep2", "PULL"));
		    }
		}	    
	    }
	    if ( !filenamesToUse.isEmpty() ){
		CompareFromDifferentFiles.plot(filenamesToUse, names,
			field, "", "Time (days)", "Total CPU usage (%)", 100);
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
	    String[] seriesNames = { "PUSH_par", "PUSH_seq", "PLANNER", "PULL" };//
	    //CompareFromDifferentFiles.plot(filenames, seriesNames,
	     //   "TOTAL", description, "Time (days)", "CPU usage (%)", 100);
	    
	    //compare network load
	    field = "queue";
	    String[] temp = null;
	    filenamesToUse.clear();
	    names.clear();
	    String extraPrefix = "S1";
	    
	    for (File file: PUSHparFiles){
		if (file.isFile() && file.length() > 0){
		    if (file.getName().endsWith("_link_statistics.csv") && ! file.getName().contains("_link_link_") 
			    && file.getName().startsWith(extraPrefix)
			    &&file.getName().contains("P2") //extra condition
			    ){
			temp = file.getName().split("_");
			//plotter.plot(file.getAbsolutePath(), "transfer queue " + temp[0] + "->" + temp [1] + " (PUSHpar) " , "time (days)", "files");			
			filenamesToUse.add(file.getAbsolutePath());
			names.add(temp[0] + "<->" + temp [1] + " (PUSHpar) " );			
		    }		    
		}
	    }	
	    
	    for (File file: PUSHseqFiles){
		if (file.isFile() && file.length() > 0){
		    if (file.getName().endsWith("_router_statistics.csv") 
			    && file.getName().startsWith(extraPrefix) 
			    ){
			temp = file.getName().split("_");
			//plotter.plot(file.getAbsolutePath(), "transfer queue " + temp[0] + "->" + temp [3] + " (PUSHseq) " , "time (days)", "files");			
			filenamesToUse.add(file.getAbsolutePath());
			names.add(temp[0] + "->" + temp [3] + " (PUSHseq) " );			
		    }		    
		}
	    }
	    
	    for (File file: PULLFiles){
		if (file.isFile() && file.length() > 0){
		    if (file.getName().endsWith("_router_statistics.csv") 
			    && file.getName().startsWith(extraPrefix) 
			    ){
			temp = file.getName().split("_");
			//plotter.plot(file.getAbsolutePath(), "transfer queue " + temp[0] + "->" + temp [3] + " (PUSHseq) " , "time (days)", "files");			
			filenamesToUse.add(file.getAbsolutePath());
			names.add(temp[0] + "->" + temp [3] + " (PULL) " );			
		    }		    
		}
	    }
	    
	    for (File file: PLANNERFiles){
		if (file.isFile() && file.length() > 0){
		    if (file.getName().endsWith("_router_statistics.csv") 
			    && file.getName().startsWith(extraPrefix)
			    ){
			temp = file.getName().split("_");
			//plotter.plot(file.getAbsolutePath(), "transfer queue " + temp[0] + "->" + temp [3] + " (PLANNER) " , "time (days)", "files");			
			filenamesToUse.add(file.getAbsolutePath());
			names.add(temp[0] + "->" + temp [3] + " (PLANNER) " );			
		    }		    
		}
	    }	
	    
	    if ( !filenamesToUse.isEmpty() ){
		//CompareFromDifferentFiles.plot(filenamesToUse, names,
		//	field, "Transfer queue", "Time (days)", "files", 1);
	    }else{
		System.out.println("FAILED TO FIND FILES");
	    }

	    //String[] StatFilenames = {prefixPUSHpar + flowsNoFilename, prefixPUSHseq + queueLengthStatFilename, prefix1 + queueLengthStatFilename};//
	    //seriesNames = { "PUSH_par", "PUSH_seq", "PLANNER" };//
	    //CompareFromDifferentFiles.plot(StatFilenames, seriesNames,
		//        "queue", description, "Time (days)", "queued file transfers", 1);
		    

    }

}
