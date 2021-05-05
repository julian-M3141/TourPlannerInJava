package dataAccess.dao;

public interface IDataAccessFactory {
    public ITourDataAccess getTourAccess();
    public ILogDataAccess getLogAccess();
}
