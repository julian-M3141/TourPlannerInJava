package at.technikum.businessLayer.manager;

import at.technikum.businessLayer.tourMap.TourMap;
import at.technikum.dataAccess.db.DBAccessFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppManagerFactory {

    private static IAppManger manager;
    public static IAppManger getManager(){
        if (manager == null){
            Logger logger = LogManager.getLogger(AppManagerFactory.class);
            logger.debug("creating new AppManager");
            manager = new AppManager(new DBAccessFactory(),new TourMap());
        }
        return manager;
    }
}
