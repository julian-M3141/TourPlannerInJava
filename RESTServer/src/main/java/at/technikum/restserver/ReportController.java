package at.technikum.restserver;


import at.technikum.businessLayer.manager.AppManagerFactory;
import at.technikum.businessLayer.manager.IAppManger;
import at.technikum.businessLayer.report.BasicTourReport;
import at.technikum.businessLayer.report.IReport;
import at.technikum.businessLayer.report.SummarizeReport;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/resources/tours/{tourid}")
public class ReportController {

    IAppManger manager = AppManagerFactory.getManager();

    private byte[] getBytes(@PathVariable int tourid, IReport report) {
        var tour = manager.get(tourid);
        if(tour.isPresent()){
            try {
                var pdf = report.print(tour.get());
                return pdf;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException("Cannot find tour");
    }

    @GetMapping(value = "/report",produces = "application/pdf")
    public @ResponseBody byte[] getReport(@PathVariable int tourid){
        IReport report = new BasicTourReport();
        return getBytes(tourid, report);
    }

    @GetMapping(value = "/summarizereport",produces = "application/pdf")
    public @ResponseBody byte[] getSummarizeReport(@PathVariable int tourid){
        IReport report = new SummarizeReport();
        return getBytes(tourid, report);
    }
}
