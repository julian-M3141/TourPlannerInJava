package tourplanner.businessLayer.report;

import tourplanner.models.Tour;

import java.io.FileNotFoundException;

public interface IReport {
    public void print(Tour tour)throws FileNotFoundException;
}
