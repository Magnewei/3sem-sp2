package dat.routes;

import dat.controllers.CollectionController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class CollectionRoute {

    private final CollectionController collectionController = new CollectionController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/", collectionController::create, Role.USER);
            get("/", collectionController::readAll);
            get("/{id}", collectionController::read);
            put("/{id}", collectionController::update);
            delete("/{id}", collectionController::delete);
        };
    }
}