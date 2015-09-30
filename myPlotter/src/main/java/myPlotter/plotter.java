package myPlotter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

import com.xeiam.xchart.*;

/**
 * Creates a simple Chart using QuickChart
 */
public class plotter {
 
  public static void main(String[] args) throws Exception {
      
    String prefix = "F:/git/Grid_simulation/grid/src/main/java/flow_model/output/";  
    String filename = "KISTI_statistics.csv";
    BufferedReader br = new BufferedReader(new FileReader(prefix + filename));
    LinkedList<String> metricsNames = new LinkedList<String>();
    LinkedList<Double> time = new LinkedList<Double>();
    LinkedList<Double> freeCPUs = new LinkedList<Double>();
    
    String line;
    String[] lineData;
    String cvsSplitBy = " ";
    boolean firstLine = true;
    String plotName = "fail";
    
    line = br.readLine();
    lineData = line.split(cvsSplitBy);
    
    //read names of values
    for(int i = 0; i < lineData.length; i++){
	System.out.println(i + " " + lineData[i]);
	metricsNames.add(lineData[i]);
    }
    
    //calculate lines
    int lines = 0;
    br.mark(10000000);

    
    while ((line = br.readLine()) != null ) {	
	lines++;
    }
    System.out.println("lines: " + lines);
    br.reset();
    
    
    //read all data into 2D array
    LinkedList <double[]> data = new LinkedList<double[]>();
    for (int i = 0; i < metricsNames.size(); i++){
	double[] dataSet = new double[lines];	
	data.add(dataSet);
    }

    int lineIterator = 0;
    while ((line = br.readLine()) != null ) {	
	lineData = line.split(cvsSplitBy);
	for(int i = 0; i < lineData.length; i++){
	    try  
	    {  
		data.get(i)[lineIterator] = Double.valueOf(lineData[i]);
	    }  
	    catch(NumberFormatException nfe)  
	    {  
		data.get(i)[lineIterator] = -1;  
	    }
	    
	}
	lineIterator++;
    }
    
  
    for (int i = 1; i < data.size(); i ++ ){
	if ( data.get(i)[0] == -1){
	    continue;
	}	
	// Create Chart
	Chart chart = QuickChart.getChart(filename, "Time (s)", metricsNames.get(i), 
        metricsNames.get(i),
        data.get(0), data.get(i));
	// Show it
	new SwingWrapper(chart).displayChart();	
    }
    

 
  }
}