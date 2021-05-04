package tourplanner.gui.viewmodels.mainwindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class SearchBarViewModel {
    private final StringProperty search = new SimpleStringProperty("");

    public void clearSearch() {
        for(var listener : listeners){
            listener.search("");
        }
        search.set("");
    }

    public interface SearchListener{
        void search(String search);
    }

    private final List<SearchListener> listeners = new ArrayList<>();

    public void addListener(SearchListener listener){
        listeners.add(listener);
    }

    public StringProperty searchProperty() {
        return search;
    }

    public void search() {
        for(var listener : listeners){
            listener.search(search.get());
        }
        search.set("");
    }
}
