package assessment.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/VideoMicroservice")
public class BaseControllerExt extends BaseController{

	@Override
	@Get("/")
	public HttpResponse<Void> GetHealth() {
		return HttpResponse.ok();
	}
}