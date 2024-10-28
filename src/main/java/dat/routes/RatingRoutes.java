package dat.routes;

import dat.controllers.HaikuController;
import dat.controllers.RatingController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class RatingRoutes {
        private final RatingController ratingController = new RatingController();
        private final HaikuController haikuController = new HaikuController();

        protected EndpointGroup getRoutes() {
            return () -> {
                post("/", ratingController::create, Role.USER);
                get("/", ratingController::readAll, Role.ANYONE);
                get("/{id}", ratingController::read, Role.USER);
                put("/{id}", ratingController::update, Role.USER);
                delete("/{id}", ratingController::delete, Role.ADMIN);
                get("/popularity", haikuController::sortByScore, Role.ANYONE);
                get("/originality", haikuController::sortByOriginality, Role.ANYONE);
                get("/spicyness", haikuController::sortBySpicyness, Role.ANYONE);
                get("/lowest", haikuController::getLowestRated, Role.ANYONE);
            };
        }
    }
