package at.technikum.businessLayer.report;

import at.technikum.businessLayer.EnergyConsumption;
import at.technikum.businessLayer.LogData;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import at.technikum.models.Log;
import at.technikum.models.Tour;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class BasicTourReport implements IReport{

    @Override
    public byte[] print(Tour tour) throws FileNotFoundException {
        Logger logger = LogManager.getLogger(SummarizeReport.class);
        logger.info("Create new Tour Report for '"+tour.getName()+"'");
        //PdfWriter writer = new PdfWriter(tour.getName().replace(' ','_')+".pdf");
        var byteArrayOutputStream = new ByteArrayOutputStream();
        var writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.addNewPage();

        Document document = new Document(pdf);
        document.add(new Paragraph(tour.getName()).setFontSize(24).setBold());
        document.add(new Paragraph("From "+tour.getStart()+" to "+tour.getFinish()));
        document.add(new Paragraph("Distance: "+tour.getDistance()));
        try {
            ImageData imageData = ImageDataFactory.create("pics/" + tour.getImage());
            Image image = new Image(imageData);
            image.setAutoScale(true);
            document.add(image);
        }catch (MalformedURLException e){
            document.add(new List().add("No image available"));
            System.err.println(e);
        }
        document.add(new Paragraph(tour.getDescription()));
        document.add(new Paragraph("Logs: "));
        if(tour.getLogs().size() > 0) {
            float [] pointColumnWidths = {150F, 150F, 150F,150F, 150F, 150F,150F, 150F, 150F};
            Table table = new Table(pointColumnWidths);
            table.setFontSize(10);
            Arrays.asList("Date","Total Time","Rating","Weather","Sport","Steps","Pace","Speed","Energy").forEach(x ->{
                table.addCell(new Cell().add(new Paragraph(x)));
            });

            for (Log log : tour.getLogs()) {
                //do something

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
                String[] data = {log.getTime().format(formatter),log.getTimeinminutes() + " min","*".repeat(log.getRating()),log.getWeather().toString(),log.getSport().toString()
                                    ,String.valueOf(log.getSteps()),String.format("%.2f min/km", LogData.getPace(log)),String.format("%.2f km/h",LogData.getAverageSpeed(log)), String.format("%.2f kcal", EnergyConsumption.getCalories(log))};
                Arrays.asList(data).forEach(x -> {
                    table.addCell(new Cell().add(new Paragraph(x)));
                });
            }
            document.add(table);
        }else {
            document.add(new List().add("No logs available"));
        }
        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
