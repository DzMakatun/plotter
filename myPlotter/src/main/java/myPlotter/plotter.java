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
public class plotter {
 
  public static void plot(String filename, String title, String xName, String yName) throws Exception {
      

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
    br.mark(20000000);

    
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
		    data.get(i)[lineIterator] = Double.valueOf(lineData[i]);
		}
	    }  
	    catch(NumberFormatException nfe)  
	    {  
		data.get(i)[lineIterator] = -1;  
	    }
	    
	}
	lineIterator++;
    }
    
  
    Chart chart = new ChartBuilder().width(1200).height(400).build();

    
    for (int i = 1; i < data.size(); i ++ ){
	if ( data.get(i)[0] == -1){
	    continue;
	}
	chart.addSeries(metricsNames.get(i), data.get(0), data.get(i)).setMarker(SeriesMarker.NONE).setSeriesType(Series.SeriesType.Line);
	// Create Chart
	//Chart chart = QuickChart.getChart(filename, "Time (s)", metricsNames.get(i), 
        //metricsNames.get(i),
        //data.get(0), data.get(i));
	// Show it
	
    }
    chart.getStyleManager().setChartBackgroundColor(Color.WHITE);
    chart.setChartTitle(title);
    chart.setXAxisTitle(xName);
    chart.setYAxisTitle(yName);
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