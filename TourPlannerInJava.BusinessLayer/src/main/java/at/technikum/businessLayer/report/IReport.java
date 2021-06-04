package at.technikum.businessLayer.report;

import at.technikum.models.Tour;

import java.io.FileNotFoundException;

public interface IReport {
    public byte[] print(Tour tour)throws FileNotFoundException;
}
