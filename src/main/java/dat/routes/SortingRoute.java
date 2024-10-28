package dat.routes;

import dat.controllers.HaikuController;
import dat.controllers.RatingController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

/**
 * Purpose:
 *
 * @Author: Anton Friis Stengaard
 */
public class SortingRoute {
    private final HaikuController haikuController = new HaikuController();

    protected EndpointGroup getRoutes() {
        return () -> {
            get("/popularity", haikuController::sortByScore, Role.ANYONE);
            get("/originality", haikuController::sortByOriginality, Role.ANYONE);
            get("/spicyness", haikuController::sortBySpicyness, Role.ANYONE);
            get("/lowest", haikuController::getLowestRated, Role.ANYONE);
        };
    }
}
