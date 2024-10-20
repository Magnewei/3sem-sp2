package app.controllers;

import app.exceptions.ApiException;
import app.exceptions.JpaException;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    public static void setExceptionController(Javalin app) {
        app.exception(ApiException.class, ExceptionController::apiExceptionHandler);
        app.exception(JpaException.class, ExceptionController::jpaExceptionHandler);
        app.exception(IllegalStateException.class, ExceptionController::illegalStateExceptionHandler);
        app.exception(Exception.class, ExceptionController::exceptionHandler);

        beforeAndAfter(app);
    }

    private static void beforeAndAfter(Javalin app) {
        app.before(ctx -> {
            String requestBody = ctx.body().isEmpty() ? "No Body" : ctx.body();
            //logger.info("Request: {} {} - Body: {}", ctx.method(), ctx.path(), requestBody);
        });

        app.after(ctx -> {
            String responseBody = ctx.body().isEmpty() ? "No Body" : ctx.body();
            //logger.info("Response: Status {} - Body: {}", ctx.status(), responseBody);
        });
    }

    private static void apiExceptionHandler(ApiException e, Context ctx) {
       // logger.error("API error: {}", e.getMessage(), e);
        ctx.status(e.getStatusCode()).result("API error: " + e.getMessage());
    }

    private static void jpaExceptionHandler(JpaException e, Context ctx) {
       // logger.error("JPA error: {}", e.getMessage(), e);
        ctx.status(404).result("Database error: " + e.getMessage());
    }

    private static void exceptionHandler(Exception e, Context ctx) {
       // logger.error("Internal server error: {}", e.getMessage(), e);
        ctx.status(404).result("Internal server error: " + e.getMessage());
    }

    private static void illegalStateExceptionHandler(IllegalStateException e, Context ctx) {
       // logger.error("Illegal State: {}", e.getMessage(), e);
        ctx.status(400).result("Bad request: Incorrect JSON representation or data format.");
    }
}
