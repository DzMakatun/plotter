package myPlotter;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

import com.xeiam.xchart.*;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;

/**
 * Creates a simple Chart using QuickChart
 */
public class ResourceUtilizationPlotter {
 
  public static void plot(String filename, String title) throws Exception {
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
		data.get(i)[lineIterator] = Double.valueOf(lineData[i]);
	    }  
	    catch(NumberFormatException nfe)  
	    {  
		data.get(i)[lineIterator] = -1;  
	    }
	    
	}
	lineIterator++;
    }
    
    //renormalize plots
    for (int i = 6; i < data.size(); i++){
	for (int j = 0; j < lines; j++){
	    data.get(i)[j] += data.get(i - 1)[j];
	}
    }
    
  
    Chart chart = new ChartBuilder().width(1200).height(400).build();

    
    for (int i = data.size()-1; i >0 ; i-- ){
	if ( data.get(i)[0] == -1){
	    continue;
	}
	if (i <= 4){
	    //System.out.println("cache");
	    chart.addSeries(metricsNames.get(i), data.get(0), data.get(i)).setMarker(SeriesMarker.NONE).setSeriesType(Series.SeriesType.Line);
	}else{
	    chart.addSeries(metricsNames.get(i), data.get(0), data.get(i)).setMarker(SeriesMarker.NONE).setSeriesType(Series.SeriesType.Area);

	}
	// Create Chart
	//Chart chart = QuickChart.getChart(filename, "Time (s)", metricsNames.get(i), 
        //metricsNames.get(i),
        //data.get(0), data.get(i));
	// Show it
	
    }
    chart.setChartTitle(title);
    chart.setXAxisTitle("Time (s)");
    chart.setYAxisTitle("usage");
    chart.getStyleManager().setChartType(ChartType.Line);    
    chart.getStyleManager().setLegendPosition(LegendPosition.OutsideE);
    chart.getStyleManager().setChartBackgroundColor(Color.WHITE);
    chart.getStyleManager().setChartTitleFont(new Font(Font.DIALOG, Font.PLAIN, 24));
    chart.getStyleManager().setLegendFont(new Font(Font.DIALOG, Font.PLAIN, 18));
    chart.getStyleManager().setAxisTitleFont(new Font(Font.DIALOG, Font.PLAIN, 30));
    chart.getStyleManager().setAxisTickLabelsFont(new Font(Font.DIALOG, Font.PLAIN, 18));
    //chart.getStyleManager().setDecimalPattern("#.#E0");
    new SwingWrapper(chart).displayChart();	

 
  }
}