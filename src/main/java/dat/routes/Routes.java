package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final HaikuRoute haikuRoute = new HaikuRoute();
    private final HaikuPartRoute haikuPartRoute = new HaikuPartRoute();

    public EndpointGroup getRoutes() {
        return () -> {
                path("/haikus", haikuRoute.getRoutes());
                path("/haikuparts", haikuPartRoute.getRoutes());
        };
    }
}
