package dat.routes;

import dat.controllers.HaikuPartController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class HaikuPartRoute {
    private final HaikuPartController haikuPartController = new HaikuPartController();

    protected EndpointGroup getRoutes() {
        return () -> {
            post("/", haikuPartController::create, Role.ANYONE);
            get("/", haikuPartController::readAll, Role.ANYONE);
            get("/{id}", haikuPartController::read, Role.ANYONE);
            put("/{id}", haikuPartController::update, Role.ANYONE);
            delete("/{id}", haikuPartController::delete, Role.ANYONE);
        };
    }
}
