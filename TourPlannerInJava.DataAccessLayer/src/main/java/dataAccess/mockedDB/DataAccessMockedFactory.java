package dataAccess.mockedDB;

import dataAccess.dao.IDataAccessFactory;
import dataAccess.dao.ILogDataAccess;
import dataAccess.dao.ITourDataAccess;

public class DataAccessMockedFactory implements IDataAccessFactory {
    @Override
    public ITourDataAccess getTourAccess() {
        return TourDAMock.Instance();
    }

    @Override
    public ILogDataAccess getLogAccess() {
        return LogDAMock.Instance();
    }
}
