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
            get("/", collectionController::readAll, Role.ANYONE);
            get("/{id}", collectionController::read, Role.ANYONE);
            put("/{id}", collectionController::update, Role.USER);
            delete("/{id}", collectionController::delete, Role.ADMIN);
        };
    }
}