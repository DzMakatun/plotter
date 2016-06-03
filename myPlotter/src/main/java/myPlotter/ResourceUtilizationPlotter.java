package myPlotter;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.xeiam.xchart.*;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;

/**
 * Creates a simple Chart using QuickChart
 */
public class ResourceUtilizationPlotter {
 
  public static void plot(String filename, String title) throws Exception {
     Map<String, Color> colorMap = new HashMap<String, Color>();
     colorMap.put("busyCPUs", Color.BLACK);
     colorMap.put("submittedInputSize", Color.GRAY) ;
     colorMap.put("reservedOutputSize", Color.lightGray);
     colorMap.put("waitingInputSize", Color.BLUE);
     colorMap.put("readyOutputSize", Color.ORANGE);     
     colorMap.put("pendingOutputSize", Color.RED);
     
     colorMap.put("jobSubmissionFailureFlag", Color.PINK);
     colorMap.put("incommingTransferFailureFlag", Color.MAGENTA);     
     colorMap.put("outgoingTransferFailureFlag", Color.CYAN);

     Map<String, String> nameMap = new HashMap<String, String>();
     nameMap.put("busyCPUs", "CPU usage");
     nameMap.put("submittedInputSize", "used input") ;
     nameMap.put("reservedOutputSize", "reserved for output");
     nameMap.put("waitingInputSize", "input queue");
     nameMap.put("readyOutputSize", "processed output");
     nameMap.put("pendingOutputSize", "pending");
     
     nameMap.put("jobSubmissionFailureFlag", "job submition failure");
     nameMap.put("incommingTransferFailureFlag", "incoming transfer failure");
     nameMap.put("outgoingTransferFailureFlag", "outgoing transfer failure ");
      
      
      
     System.out.println(filename); 
    //String prefix = "F:/git/Grid_simulation/grid/src/main/java/flow_model/output/";  
    //String filename = "RCF_statistics.csv";
    BufferedReader br = new BufferedReader(new FileReader(filename));
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
    br.mark(100000000);

    
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
		if (metricsNames.get(i).equals("time")){
		    data.get(i)[lineIterator] = Double.valueOf(lineData[i]) / (24 * 3600.0);
		}else{
		data.get(i)[lineIterator] = Double.valueOf(lineData[i]) * 100;
		}
	    }  
	    catch(NumberFormatException nfe)  
	    {  
		data.get(i)[lineIterator] = -1;  
	    }
	    
	}
	lineIterator++;
    }
    
    //renormalize plots
    for (int i = 7; i < data.size(); i++){
	for (int j = 0; j < lines; j++){
	    data.get(i)[j] += data.get(i - 1)[j];
	}
    }
    
  
    Chart chart = new ChartBuilder().width(1500).height(500).build();

    
    for (int i = data.size()-1; i >0 ; i-- ){
	if ( data.get(i)[0] == -1){
	    continue;
	}
	//filter values
	if (!colorMap.containsKey(metricsNames.get(i)) ){
	    continue;
	}
	
	//Series serie = chart.addSeries(metricsNames.get(i), data.get(0), data.get(i)).setMarker(SeriesMarker.NONE);
	Series serie = chart.addSeries(nameMap.get(metricsNames.get(i) ), data.get(0), data.get(i)).setMarker(SeriesMarker.NONE);
	
	
	if (metricsNames.get(i).equals("busyCPUs")){	    //System.out.println("cache");
	    serie   
	    .setSeriesType(Series.SeriesType.Line);
	    serie.setLineColor(colorMap.get(  metricsNames.get(i)  )   );
	}else
	if (metricsNames.get(i).endsWith("Flag")){
	    serie.setSeriesType(Series.SeriesType.Line);
	    serie.setLineStyle(SeriesLineStyle.DOT_DOT);
	    serie.setLineColor(colorMap.get(  metricsNames.get(i)  )   );
	}else
	{    serie
	    .setMarker(SeriesMarker.NONE)
	    .setSeriesType(Series.SeriesType.Area);
	    if (colorMap.containsKey(metricsNames.get(i)) ){
	        serie.setFillColor(colorMap.get(  metricsNames.get(i)  )   );
	        serie.setLineColor(colorMap.get(  metricsNames.get(i)  )   );
	    }

	}
	if (i ==data.size()-1){
	    System.out.println("MISC: " + metricsNames.get(i));
	}
	
	// Create Chart
	//Chart chart = QuickChart.getChart(filename, "Time (s)", metricsNames.get(i), 
        //metricsNames.get(i),
        //data.get(0), data.get(i));
	// Show it
	
    }
    int scale = 4;
    chart.setChartTitle(title);
    chart.setXAxisTitle("Time (days)");
    chart.setYAxisTitle("usage (%)");
    chart.getStyleManager().setChartType(ChartType.Line);    
    chart.getStyleManager().setLegendPosition(LegendPosition.OutsideE);
    chart.getStyleManager().setChartBackgroundColor(Color.WHITE);
    //chart.getStyleManager().setDecimalPattern("#.#E0");
    

    chart.getStyleManager().setChartTitleFont(new Font(Font.DIALOG, Font.PLAIN, 6*scale));
    chart.getStyleManager().setLegendFont(new Font(Font.DIALOG, Font.PLAIN, 7*scale));
    chart.getStyleManager().setLegendSeriesLineLength(scale * 10);
    chart.getStyleManager().setAxisTitleFont(new Font(Font.DIALOG, Font.PLAIN, 8*scale));
    chart.getStyleManager().setAxisTickLabelsFont(new Font(Font.DIALOG, Font.PLAIN, 8*scale));
    chart.getStyleManager().setAxisTickPadding(10);
    chart.getStyleManager().setAxisTitlePadding(scale * 5);
    chart.getStyleManager().setChartPadding(scale * 5);
    chart.getStyleManager().setXAxisTickMarkSpacingHint(100);
    chart.getStyleManager().setYAxisTickMarkSpacingHint(100);
    new SwingWrapper(chart).displayChart();	

 
  }
}