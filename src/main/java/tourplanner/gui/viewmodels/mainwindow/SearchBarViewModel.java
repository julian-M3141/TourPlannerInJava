package tourplanner.gui.viewmodels.mainwindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class SearchBarViewModel {
    private final StringProperty search = new SimpleStringProperty("");

    public interface SearchListener{
        void search(String search);
    }

    private final List<SearchListener> listeners = new ArrayList<>();

    public void addListener(SearchListener listener){
        listeners.add(listener);
    }

    public String getSearch() {
        return search.get();
    }

    public StringProperty searchProperty() {
        return search;
    }

    public void search() {
        for(var listener : listeners){
            listener.search(search.get());
        }
    }
}
