package controllers.api;

import play.mvc.Controller;
import play.mvc.Result;

public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a REST API service status message
     */
    public Result index() {
        return ok(views.html.index.render());
    }

}
