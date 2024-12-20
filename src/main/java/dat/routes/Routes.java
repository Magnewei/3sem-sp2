package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {
    private final HaikuRoute haikuRoute = new HaikuRoute();
    private final HaikuPartRoute haikuPartRoute = new HaikuPartRoute();
    private final CollectionRoute collectionRoute = new CollectionRoute();
    private final RatingRoutes ratingRoutes = new RatingRoutes();
    private final SortingRoute sortingRoutes = new SortingRoute();

    public EndpointGroup getRoutes() {
        return () -> {
                path("/haikus", haikuRoute.getRoutes());
                path("/haikuparts", haikuPartRoute.getRoutes());
                path("/collections", collectionRoute.getRoutes());
                path("/ratings", ratingRoutes.getRoutes());
                path("/sort", sortingRoutes.getRoutes());
        };
    }
}
