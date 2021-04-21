package tourplanner.dataAccess.db;

import tourplanner.dataAccess.dao.IDataAccessFactory;
import tourplanner.dataAccess.dao.ILogDataAccess;
import tourplanner.dataAccess.dao.ITourDataAccess;

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
