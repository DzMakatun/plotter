package myPlotter;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

import com.xeiam.xchart.*;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;

/**
 * Creates a simple Chart using QuickChart
 */
public class MakespanComparisonPlotter {
 
  public static void plot(String filename, String title, String xName, String yName) throws Exception {
      

    BufferedReader br = new BufferedReader(new FileReader(filename));
    LinkedList<String> metricsNames = new LinkedList<String>();
    LinkedList<Double> time = new LinkedList<Double>();
    LinkedList<Double> freeCPUs = new LinkedList<Double>();
    
    SeriesLineStyle[] stiles= {SeriesLineStyle.SOLID,  SeriesLineStyle.DASH_DASH, SeriesLineStyle.DASH_DOT, SeriesLineStyle.DOT_DOT,
	    SeriesLineStyle.SOLID,  SeriesLineStyle.DASH_DASH};
    int stileNo = 0;

    SeriesMarker[] seriesMarkers =  {SeriesMarker.CIRCLE, SeriesMarker.DIAMOND, SeriesMarker.SQUARE, SeriesMarker.TRIANGLE_DOWN,
	    SeriesMarker.CIRCLE, SeriesMarker.DIAMOND};
    ArrayList<String> seriesNames = new ArrayList<String>();
    seriesNames.add("PLANER"); seriesNames.add("PUSH_par");seriesNames.add("PUSH_seq");seriesNames.add("no_network");
    seriesNames.add("PLANER_vs_PUSH_par");seriesNames.add("PLANER_vs_PUSH_seq");
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
		data.get(i)[lineIterator] = data.get(i)[lineIterator - 1];  //if there is no more data - repeat the last point
	    }
	    
	}
	lineIterator++;
    }
    
  
    Chart chart = new ChartBuilder().width(1500).height(800).build();

    System.out.println("plotting...");
    for (int i = data.size() - 1; i > 0; i-- ){
	if ( data.get(i)[0] == -1){
	    continue;
	}
	//.setLineStyle(stiles[stileNo]).setLineColor(SeriesColor.BLACK)
	Series series = chart.addSeries(metricsNames.get(i).replaceAll("PLANER", "PLANNER").replaceAll("_vs_", " vs "), data.get(0), data.get(i));
	series
	    //.setLineStyle(stiles[stileNo])
	    .setLineColor(SeriesColor.BLACK)
	    .setMarkerColor(SeriesColor.BLACK)
	    
	    .setSeriesType(Series.SeriesType.Line);
	int index = seriesNames.indexOf( metricsNames.get(i) );
	if (index != -1 ){
	    series.setMarker(seriesMarkers[index]);
	    series.setLineStyle(stiles[index]);
	}
	
	
	// Create Chart
	//Chart chart = QuickChart.getChart(filename, "Time (s)", metricsNames.get(i), 
        //metricsNames.get(i),
        //data.get(0), data.get(i));
	// Show it
	stileNo++;
	if (stileNo == stiles.length){stileNo = 0;}
    }
    
    int scale = 6;
    chart.setChartTitle(title);
    chart.setXAxisTitle(xName);
    chart.setYAxisTitle(yName);
    chart.getStyleManager().setChartType(ChartType.Line);    
    chart.getStyleManager().setLegendPosition(LegendPosition.InsideNW);
    chart.getStyleManager().setChartBackgroundColor(Color.WHITE);
    chart.getStyleManager().setChartTitleFont(new Font(Font.DIALOG, Font.PLAIN, 6*scale));
    chart.getStyleManager().setLegendFont(new Font(Font.DIALOG, Font.PLAIN, 7*scale));
    chart.getStyleManager().setLegendSeriesLineLength(scale * 10);
    chart.getStyleManager().setAxisTitleFont(new Font(Font.DIALOG, Font.PLAIN, 8*scale));
    chart.getStyleManager().setAxisTickLabelsFont(new Font(Font.DIALOG, Font.PLAIN, 8*scale));
    chart.getStyleManager().setMarkerSize(3*scale +1);
    chart.getStyleManager().setAxisTickPadding(10);
    chart.getStyleManager().setXAxisTickMarkSpacingHint(100);
    chart.getStyleManager().setYAxisTickMarkSpacingHint(100);
    chart.getStyleManager().setAxisTitlePadding(scale * 5);
    chart.getStyleManager().setChartPadding(scale * 5);
    //chart.getStyleManager().setXAxisMin(0.2);
    //chart.getStyleManager().setXAxisMax(0.4);
    //chart.getStyleManager().setYAxisMin(1);
    //chart.getStyleManager().setYAxisMax(2);
    //chart.getStyleManager().setDecimalPattern("#.#E0");
    new SwingWrapper(chart).displayChart();	

 
  }
}