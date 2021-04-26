package tourplanner.businessLayer.report;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.orsonpdf.PDFDocument;
import com.orsonpdf.PDFGraphics2D;
import com.orsonpdf.Page;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import tourplanner.businessLayer.EnergyConsumption;
import tourplanner.models.Sport;
import tourplanner.models.Tour;
import tourplanner.models.Weather;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SummarizeReport implements IReport{

    @Override
    public void print(Tour tour) throws FileNotFoundException {
        Logger logger = LogManager.getLogger(SummarizeReport.class);
        logger.info("Create new Summarize Report for '"+tour.getName()+"'");
        PdfWriter writer = new PdfWriter((tour.getName()+"_summary").replace(' ','_')+".pdf");
        PdfDocument pdf = new PdfDocument(writer);
        pdf.addNewPage();

        Document document = new Document(pdf);
        document.add(new Paragraph(tour.getName()).setFontSize(24).setBold());
        document.add(new Paragraph("From "+tour.getStart()+" to "+tour.getFinish()));
        if(tour.getLogs().size() == 0){
            document.add(new Paragraph("No logs available"));
            document.close();
            return;
        }

        //time
        int time = 0;
        //rating
        int rating = 0;
        int[] ratinghisto = {0,0,0,0,0};
        //distance
        int distance = 0;
        //weather
        Map<Weather,Integer> weather = new HashMap<>();
        Arrays.asList(Weather.class.getEnumConstants()).forEach(x -> weather.put(x,0));
        //sport
        Map<Sport,Integer> sports = new HashMap<>();
        Arrays.asList(Sport.class.getEnumConstants()).forEach(x -> sports.put(x,0));
        //steps
        int steps = 0;
        //avg calories
        double calories = 0;
        for(var log : tour.getLogs()){
            time += log.getTimeinminutes();
            rating += log.getRating();
            distance += log.getDistance();
            ratinghisto[log.getRating()-1]+=1;
            weather.put(log.getWeather(),weather.get(log.getWeather())+1);
            sports.put(log.getSport(),sports.get(log.getSport())+1);
            steps += log.getSteps();
            calories += EnergyConsumption.getCalories(log);
        }
        //System.out.println(ratinghisto[0]+ " " + ratinghisto[1]+ " " +ratinghisto[2]+ " " +ratinghisto[3]+ " " +ratinghisto[4]);
        List list = new List();
        int sum = tour.getLogs().size();
        list.add("#Logs: "+sum);
        list.add(String.format("Total time: %d min",time));
        list.add(String.format("Average time: %d min",(time/sum)));
        list.add(String.format("Total distance: %d km",distance));
        list.add(String.format("Average distance: %d km",(distance/sum)));
        list.add(String.format("Average speed: %.2f km/h",((double)distance/time*60)));
        list.add(String.format("Average pace: %.2f min/km",((double)time/distance)));
        list.add(String.format("Average steps: %.1f",((double)steps/sum)));
        list.add(String.format("Average energy consumption: %.2f kcal",(calories/sum)));
        list.add(String.format("Average Rating: %.2f/5,00",((double)rating/sum)));
        document.add(list);
        try {
            document.add(new Paragraph().add(getImage(createDataSet(ratinghisto,"Rating"),"Rating",pdf)));
        } catch (IOException e) {
            logger.error(e);
        }
        list = new List();
        List weatherlist = new List();
        weather.forEach((key,value) -> weatherlist.add(key.toString() + ": "+value+" times"));
        ListItem item = new ListItem();
        item.add(new Paragraph().add("Weather: "));
        item.add(weatherlist);
        list.add(item);
        document.add(list);
        try {
            document.add(new Paragraph().add(getImage(createDataSet(weather,"Weather"),"Weather",pdf)));
        } catch (IOException e) {
            logger.error(e);
        }
        list = new List();

        List sportslist = new List();
        sports.forEach((key,value) -> sportslist.add(key.toString() + ": "+value+" times"));
        item = new ListItem();
        item.add(new Paragraph().add("Sport: "));
        item.add(sportslist);
        list.add(item);
        document.add(list);
        try {
            document.add(new Paragraph().add(getImage(createDataSet(sports,"Sports"),"Sports",pdf)));
        } catch (IOException e) {
            logger.error(e);
        }
        document.close();

    }

    private static byte[] generateChartPDF(JFreeChart chart) {
        // here we use OrsonPDF to generate PDF in a byte array
        PDFDocument doc = new PDFDocument();
        Rectangle bounds = new Rectangle(500, 335);
        Page page = doc.createPage(bounds);
        PDFGraphics2D g2 = page.getGraphics2D();
        chart.draw(g2, bounds);
        return doc.getPDFBytes();
    }

    private static Image getImage(DefaultCategoryDataset dataset,String title,PdfDocument pdf) throws IOException {
        JFreeChart chart = ChartFactory.createBarChart(title,"","",dataset);
        PdfReader reader = new PdfReader(new ByteArrayInputStream(generateChartPDF(chart)));
        PdfDocument chartDoc = new PdfDocument(reader);
        PdfFormXObject chart1 = chartDoc.getFirstPage().copyAsFormXObject(pdf);
        return new Image(chart1);
    }
    private static <T extends Enum<T>> DefaultCategoryDataset createDataSet(Map<T,Integer> map,String title){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        map.forEach((key,value) -> dataset.addValue(value,key.toString(),title));
        return dataset;
    }
    private static DefaultCategoryDataset createDataSet(int[] data,String title){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i=0;i<data.length;++i){
            dataset.addValue(data[i],"*".repeat(i+1),title);
        }
        return dataset;
    }
}
