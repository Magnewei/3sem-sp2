package dat.routes;

import dat.controllers.HaikuPartController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class HaikuPartRoute {
    private final HaikuPartController haikuPartController = new HaikuPartController();

    protected EndpointGroup getRoutes() {
        return () -> {
            post("/", haikuPartController::create, Role.USER);
            get("/", haikuPartController::readAll, Role.ANYONE);
            get("/{id}", haikuPartController::read, Role.ANYONE);
            put("/{id}", haikuPartController::update, Role.USER);
            delete("/{id}", haikuPartController::delete, Role.ADMIN);
        };
    }
}
