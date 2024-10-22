package dat.routes;

import dat.controllers.HaikuPartController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class HaikuPartRoute {

    private final HaikuPartController haikuPartController = new HaikuPartController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/hotel/{id}", haikuPartController::create);
            get("/", haikuPartController::readAll);
            get("/{id}", haikuPartController::read);
            put("/{id}", haikuPartController::update);
            delete("/{id}", haikuPartController::delete);
        };
    }
}
