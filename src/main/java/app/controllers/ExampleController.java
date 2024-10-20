package app.controllers;

import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

public class ExampleController {
    public static EndpointGroup addRoutes() {
        return () -> {
            ApiBuilder.get("/user/{id}", ExampleController::sendSingleUser);
        };
    }

    private static void sendSingleUser(Context ctx) {
        try {
            Object object = new Object();
            ctx.json(object);

        } catch (Exception e) {
            ctx.status(404);
            ctx.json("User not found");
        }
    }
}
