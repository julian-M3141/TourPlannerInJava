package tourplanner.businessLayer.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tourplanner.dataAccess.Status;

public class AppManagerFactory {
    private static Logger logger;
    private static IAppManger manager;
    public static IAppManger getManager(){
        if (manager == null){
            logger = LogManager.getLogger(AppManagerFactory.class);
            logger.debug("creating new AppManager");
            manager = new AppManager(Status.DB);
        }
        return manager;
    }
}
