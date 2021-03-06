package myPlotter;

import java.awt.BasicStroke;
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
public class CompareFromDifferentFiles {
  
  public static void plot(LinkedList<String> filenamesToUse, LinkedList<String> names, String fieldName, String title, String xName, String yName, double mult) throws Exception {
      System.out.println("CompareFromDifferentFiles: starting to process files " + filenamesToUse);
      Chart chart = new ChartBuilder().width(1100).height(500).build();   
  SeriesLineStyle[] stiles= {SeriesLineStyle.SOLID,  SeriesLineStyle.DASH_DASH, SeriesLineStyle.DASH_DOT, SeriesLineStyle.DOT_DOT,
	  SeriesLineStyle.SOLID,  SeriesLineStyle.DASH_DASH, SeriesLineStyle.DASH_DOT, SeriesLineStyle.DOT_DOT,
	  SeriesLineStyle.SOLID,  SeriesLineStyle.DASH_DASH, SeriesLineStyle.DASH_DOT, SeriesLineStyle.DOT_DOT};
  //Color[] color= { Color.DARK_GRAY, Color.GRAY, Color.BLACK};
  Color[] color= {Color.GREEN, Color.BLUE, Color.RED, Color.CYAN, Color.BLACK, Color.BLUE, Color.GRAY, Color.BLUE,  Color.PINK, Color.DARK_GRAY, Color.LIGHT_GRAY, Color.GRAY};
  ArrayList<String> seriesNames = new ArrayList<String>();
  seriesNames.add("PUSHpar");
  seriesNames.add("PUSHseq");
  seriesNames.add("PLANNER"); 
  seriesNames.add("no_network");
  seriesNames.add("PULL");
  seriesNames.add("PLANNER(single)"); 
  seriesNames.add("PULL(single)");
  seriesNames.add("PLANNER(replication)"); 
  seriesNames.add("PULL(replication)");
  
  ArrayList<Double> averages = new ArrayList<Double>();

  
  int stileNo = 0;
  int fileNomber = 0;
  for (String filename : filenamesToUse){
    double average = 0.0;
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
    br.mark(50000000);

    
    while ((line = br.readLine()) != null ) {	
	lines++;
    }
    System.out.println("lines: " + lines);
    
    //br.reset();
    br.close();
    br = new BufferedReader(new FileReader(filename));
    br.readLine();
    
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
	    //update average
	    if (metricsNames.get(i).equals(fieldName)){
		average += data.get(i)[lineIterator]; //calculates sum of all values
	    }
	    
	    
	}
	lineIterator++;
    }
    
    while (lineIterator < lines){
	for(int i = 0; i < metricsNames.size(); i++){
	    data.get(i)[lineIterator] = data.get(i)[lineIterator - 1];
	    //update average
	    if (metricsNames.get(i).equals(fieldName)){
		average += data.get(i)[lineIterator]; //calculates sum of all values
	    }
	}
	lineIterator++;
    }
    
  
   

    System.out.println("plotting...");
    for (int i = 1; i < data.size(); i++ ){
	if (metricsNames.get(i).compareTo(fieldName) != 0){
	    continue;
	}
	//.setLineStyle(stiles[stileNo]).setLineColor(SeriesColor.BLACK)
	Series serie = chart.addSeries(names.get(fileNomber).replaceAll("PUSHpar", "PAR").replaceAll("PUSHseq", "SEQ"), data.get(0), data.get(i));
	serie.setMarker(SeriesMarker.NONE);
	serie.setSeriesType(Series.SeriesType.Line);
	serie.setLineColor(color[stileNo]);
	
	int index = seriesNames.indexOf( names.get(fileNomber) );
	if (index != -1 ){
	    //serie.setLineStyle(stiles[index]);
	    serie.setLineColor(color[index]);
	}
	

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
    average = average / lines;
    averages.add(average);
  }
    int scale = 6;
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
    
    System.out.println("AVERAGES OF " + fieldName);
    int k = 0;
    for (double average: averages){
	System.out.println(average + " " + names.get(k));
	k++;
    }
  }
  
  
}