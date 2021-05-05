package gui.viewmodels.mainwindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import gui.ButtonListener;
import models.Log;

import java.util.List;
import java.util.function.Consumer;

public class LogDetailsViewModel {
    private final ObservableList<Log> logs = FXCollections.observableArrayList();

    private ButtonListener addLogButton;
    private Consumer<Log> updateLogButton;
    private Consumer<Log> deleteLogButton;


    //setter for buttons
    public void setAddLogButton(ButtonListener addLogButton) {
        this.addLogButton = addLogButton;
    }

    public void setUpdateLogButton(Consumer<Log> updateLogButton) {
        this.updateLogButton = updateLogButton;
    }

    public void setDeleteLogButton(Consumer<Log> deleteLogButton) {
        this.deleteLogButton = deleteLogButton;
    }

    public void setLogs(List<Log> loglist) {
        logs.clear();
        logs.addAll(loglist);
    }

    public ObservableList<Log> getLogs() {
        return logs;
    }

    public void deleteLog(Log selectedItem) {
        if(selectedItem != null){
            deleteLogButton.accept(selectedItem);
        }
    }

    public void addLog() {
        addLogButton.listen();
    }

    public void updateLog(Log selectedItem) {
        if(selectedItem != null){
            updateLogButton.accept(selectedItem);
        }
    }
}
