package tourplanner.dataAccess.mockedDB;

import tourplanner.dataAccess.dao.IDataAccessFactory;
import tourplanner.dataAccess.dao.ILogDataAccess;
import tourplanner.dataAccess.dao.ITourDataAccess;

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
