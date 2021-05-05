package gui.viewmodels.mainwindow;

import gui.ButtonListener;
import gui.TourButtonListener;

public class MenuBarViewModel {
    private TourButtonListener tourButtonListener;
    private ButtonListener importFile;
    private ButtonListener exportFile;
    private ButtonListener tourReport;
    private ButtonListener summarizeReport;
    private ButtonListener addLogButton;


    public void addTourButtonListener(TourButtonListener tourButtonListener) {
        this.tourButtonListener = tourButtonListener;
    }

    public void setImportFile(ButtonListener importFile) {
        this.importFile = importFile;
    }

    public void setExportFile(ButtonListener exportFile) {
        this.exportFile = exportFile;
    }

    public void setTourReport(ButtonListener tourReport) {
        this.tourReport = tourReport;
    }

    public void setSummarizeReport(ButtonListener summarizeReport) {
        this.summarizeReport = summarizeReport;
    }

    public void setAddLogButton(ButtonListener addLogButton) {
        this.addLogButton = addLogButton;
    }


    public void importFile() {
        importFile.listen();
    }

    public void export() {
        exportFile.listen();
    }

    public void print() {
        tourReport.listen();
    }

    public void summarizeReport() {
        summarizeReport.listen();
    }

    public void newTour() {
        tourButtonListener.createNewTour();
    }

    public void updateTour() {
        tourButtonListener.updateTour();
    }

    public void deleteTour() {
        tourButtonListener.deleteTour();
    }

    public void addLog() {
        addLogButton.listen();
    }
}
