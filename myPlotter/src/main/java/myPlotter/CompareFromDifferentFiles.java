package myPlotter;

import java.awt.BasicStroke;
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
public class CompareFromDifferentFiles {
  
  public static void plot(String[] filenames, String[] seriesNames, String fieldName, String title, String xName, String yName, double mult) throws Exception {
      System.out.println("CompareFromDifferentFiles: starting to process files " + filenames);
      Chart chart = new ChartBuilder().width(1500).height(500).build();   
  SeriesLineStyle[] stiles= {SeriesLineStyle.SOLID,  SeriesLineStyle.DASH_DASH, SeriesLineStyle.DASH_DOT, SeriesLineStyle.DOT_DOT};
  //Color[] color= { Color.DARK_GRAY, Color.GRAY, Color.BLACK};
  Color[] color= {Color.RED, Color.BLUE, Color.BLACK};
  int stileNo = 0;
  int fileNomber = 0;
  for (String filename : filenames){
    System.out.println("CompareFromDifferentFiles: reading file " + filename);
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
	if (lineData.length != metricsNames.size()){
	    System.out.println("corrupted line: " + lineData[0]);
	    break;//if the last line is broken skip it	    
	}
	for(int i = 0; i < lineData.length; i++){
	    try  
	    {  
		if (metricsNames.get(i).equals("time")){
		    data.get(i)[lineIterator] = Double.valueOf(lineData[i]) / ( 24 * 3600.0 ); //convert seconds to hours
		}else{
		    data.get(i)[lineIterator] = Double.valueOf(lineData[i]) * mult; //convert to percentage
		}
	    }  
	    catch(NumberFormatException nfe)  
	    {  
		data.get(i)[lineIterator] = data.get(i)[lineIterator - 1];  //if the data is corripted - repeat the last point
	    }
	    
	}
	lineIterator++;
    }
    
    while (lineIterator < lines){
	for(int i = 0; i < metricsNames.size(); i++){
	    data.get(i)[lineIterator] = data.get(i)[lineIterator - 1];
	}
	lineIterator++;
    }
    
  


    System.out.println("plotting...");
    for (int i = 1; i < data.size(); i+=2 ){
	if (metricsNames.get(i).compareTo(fieldName) != 0 || data.get(i)[0] == -1){
	    continue;
	}
	//.setLineStyle(stiles[stileNo]).setLineColor(SeriesColor.BLACK)
	Series serie = chart.addSeries(seriesNames[fileNomber], data.get(i-1), data.get(i));
	serie.setMarker(SeriesMarker.NONE);
	serie.setSeriesType(Series.SeriesType.Line);
	serie.setLineColor(color[stileNo]);

	//series.setLineColor(SeriesColor.BLUE);
	// Create Chart
	//Chart chart = QuickChart.getChart(filename, "Time (s)", metricsNames.get(i), 
        //metricsNames.get(i),
        //data.get(0), data.get(i));
	// Show it
	System.out.println("Line style: " + stiles[stileNo]);
	stileNo++;
	if (stileNo == color.length){stileNo = 0;}
    }
    fileNomber++;
  }
    int scale = 4;
    chart.setChartTitle(title);
    chart.setXAxisTitle(xName);
    chart.setYAxisTitle(yName);
    chart.getStyleManager().setChartType(ChartType.Line);    
    chart.getStyleManager().setLegendPosition(LegendPosition.OutsideE);
    chart.getStyleManager().setChartBackgroundColor(Color.WHITE);
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