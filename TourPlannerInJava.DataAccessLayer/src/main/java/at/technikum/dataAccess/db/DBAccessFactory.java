package at.technikum.dataAccess.db;

import at.technikum.dataAccess.dao.IDataAccessFactory;
import at.technikum.dataAccess.dao.ILogDataAccess;
import at.technikum.dataAccess.dao.ITourDataAccess;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class DBAccessFactory implements IDataAccessFactory {
    @Override
    public ITourDataAccess getTourAccess() {
        return TourDBAccess.getInstance();
    }

    @Override
    public ILogDataAccess getLogAccess() {
        return LogDBAccess.getInstance();
    }
}
