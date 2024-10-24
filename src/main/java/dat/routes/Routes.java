package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final HaikuRoute haikuRoute = new HaikuRoute();
    private final HaikuPartRoute haikuPartRoute = new HaikuPartRoute();
    private final CollectionRoute collectionRoute = new CollectionRoute();

    public EndpointGroup getRoutes() {
        return () -> {
                path("/haikus", haikuRoute.getRoutes());
                path("/haikuparts", haikuPartRoute.getRoutes());
                path("/collections", collectionRoute.getRoutes());
        };
    }
}
