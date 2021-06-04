package at.technikum.dataAccess.mockedDB;

import at.technikum.dataAccess.dao.IDataAccessFactory;
import at.technikum.dataAccess.dao.ILogDataAccess;
import at.technikum.dataAccess.dao.ITourDataAccess;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
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
