package dat;

import dat.config.ApplicationConfig;
import dat.config.Populate;

public class Main {

    public static void main(String[] args) {
        Populate.main(args);
        ApplicationConfig.startServer(7070);
        //HEJ
    }
}