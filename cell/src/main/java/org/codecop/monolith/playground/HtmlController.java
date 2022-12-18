package org.codecop.monolith.playground;

import java.util.Map;

import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

@Controller
public class HtmlController {

    @Get("/hello.html")
    @View("home")
    public HttpResponse<Map<?, ?>> issueView() {
        // see https://micronaut-projects.github.io/micronaut-views/latest/guide/
        return HttpResponse.ok(CollectionUtils.mapOf("loggedIn", true, "username", "sdelamo"));
    }

}
