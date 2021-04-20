package tourplanner.businessLayer.manager;

public class AppManagerFactory {
    private static IAppManger manager;
    public static IAppManger getManager(){
        if (manager == null){
            manager = new AppManager();
        }
        return manager;
    }
}
