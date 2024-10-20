package config;

import app.controllers.ExampleController;
import app.controllers.ExceptionController;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.config.JavalinConfig;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AppConfig {
    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    private static void configuration(JavalinConfig config) {
        config.router.contextPath = "/api/v1";
        config.bundledPlugins.enableRouteOverview("/routes");
        config.bundledPlugins.enableDevLogging();
        config.router.apiBuilder(() -> {
            ApiBuilder.path("", () -> {
               // TODO: Add routes here
                ExampleController.addRoutes().addEndpoints();
            });
        });
    }

    public static void startServer(int port) {
        var app = Javalin.create(AppConfig::configuration);
        ExceptionController.setExceptionController(app);
        app.start(port);
        log.info("Server started on port: " + port);
    }
}

