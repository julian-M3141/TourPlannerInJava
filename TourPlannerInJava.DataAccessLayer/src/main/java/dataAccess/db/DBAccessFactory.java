package dataAccess.db;

import dataAccess.dao.IDataAccessFactory;
import dataAccess.dao.ILogDataAccess;
import dataAccess.dao.ITourDataAccess;

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
