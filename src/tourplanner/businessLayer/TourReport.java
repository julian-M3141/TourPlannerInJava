package tourplanner.businessLayer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class TourReport {
    public void print(Tour tour) throws IOException {
        PDDocument document = new PDDocument();
        PDDocumentInformation info = document.getDocumentInformation();
        //set information
        info.setAuthor("TourPlanner");
        info.setTitle(tour.getTourname());
        info.setCreator("TourPlanner");
        info.setSubject("Tour "+tour.getTourname());
        info.setCreationDate(Calendar.getInstance());
        info.setModificationDate(Calendar.getInstance());

        //create new page
        //default us-letter, set to a4
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        //create new stream
        PDPageContentStream stream = new PDPageContentStream(document,page);

        //write to stream
        stream.beginText();

        //set font size
        stream.setFont(PDType1Font.TIMES_BOLD,24);
        //set position
        stream.newLineAtOffset(25,750);
        //write to stream
        stream.showText(tour.getTourname());


        stream.endText();

        //draw image
        //later tour.getImage
        PDImageXObject image = PDImageXObject.createFromFile("test.jpg",document);
        stream.drawImage(image,25,400,450,300);

        stream.beginText();
        stream.newLineAtOffset(25,350);
        stream.setFont(PDType1Font.TIMES_ROMAN,12);
        stream.setLeading(14.5f);
        stream.showText(tour.getDescription());

        stream.newLine();
        stream.newLine();
        stream.showText("Logs:");
        stream.newLine();
        ArrayList<Log> logs = tour.getLogs();
        for(int i=0;i<logs.size(); ++i){
            stream.showText("Log "+(i+1));
            stream.newLine();
            stream.showText("Datum: "+logs.get(i).getDate());
            stream.newLine();
            stream.showText("Zeit: "+logs.get(i).getTime());
            stream.newLine();
            stream.showText("Rating: "+logs.get(i).getRating());
            stream.newLine();
        }

        stream.endText();
        stream.close();

        document.save("test.pdf");
        document.close();

    }
}
