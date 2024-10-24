package dat.controllers;

import dat.exceptions.ApiException;
import dat.exceptions.DatabaseException;
import dat.routes.Routes;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionController {
    private final Logger logger = LoggerFactory.getLogger(Routes.class);

    public void apiExceptionHandler(ApiException e, Context ctx) {
        logger.error(ctx.attribute("requestInfo") + " " + ctx.res().getStatus() + " " + e.getMessage());
        ctx.status(e.getStatusCode());
        ctx.json(new ErrorMessage(e.getStatusCode(), e.getMessage()));
    }

    public void generalExceptionHandler(Exception e, Context ctx) {
        logger.warn(ctx.attribute("requestInfo") + " " + ctx.res().getStatus() + " " + e.getMessage());
        ctx.status(500);
        ctx.json(new ErrorMessage(500, e.getMessage()));
    }

    /*
     * This method is used to handle exceptions thrown by DAOs when interacting with the database
     * and before the method call reaches the controller to avoid wrong error codes being sent to the client.
     */
    public void databaseExceptionHandler(DatabaseException e, Context ctx) {
        logger.error(ctx.attribute("requestInfo") + " " + ctx.res().getStatus() + " " + e.getMessage(), e.getCause());
        ctx.status(e.getStatusCode());
        ctx.json(new ErrorMessage(e.getStatusCode(), e.getMessage()));
    }


    /**
     * ErrorMessage is used to wrap and present an error message to the client.
     *
     * @param status  the HTTP status code of the error.
     * @param message the error message being presented to the client.
     */
    private record ErrorMessage(int status, String message) {
    }
}
