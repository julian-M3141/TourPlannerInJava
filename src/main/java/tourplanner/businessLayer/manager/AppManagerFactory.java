package tourplanner.businessLayer.manager;

import tourplanner.dataAccess.Status;

public class AppManagerFactory {
    private static IAppManger manager;
    public static IAppManger getManager(){
        if (manager == null){
            manager = new AppManager(Status.DB);
        }
        return manager;
    }
}
