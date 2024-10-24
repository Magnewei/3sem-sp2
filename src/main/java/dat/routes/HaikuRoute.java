package dat.routes;

import dat.controllers.HaikuController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class HaikuRoute {

    private final HaikuController haikuController = new HaikuController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/", haikuController::create, Role.ANYONE);
            get("/", haikuController::readAll);
            get("/{id}", haikuController::read);
            put("/{id}", haikuController::update);
            delete("/{id}", haikuController::delete);
        };
    }
}
