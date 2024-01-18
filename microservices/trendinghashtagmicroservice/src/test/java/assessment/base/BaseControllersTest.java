package assessment.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

/*
 * We have to disable transactions here, as otherwise the controller will not be
 * able to see changes made by the test.
 */
@MicronautTest(transactional = false, environments = "no_streams")
public class BaseControllersTest {

	private static Logger logger = LoggerFactory.getLogger("testLogger");
	
	@Inject
	BaseClient client;

	/*
	 * We mock the Kafka producer here, so we can just test that it's called,
	 * rather than actually checking if the event fully went through.
	 */

	@BeforeEach
	public void clean() {
	}

	@Test
	public void healthCheck() {
		HttpResponse<Void> response = client.GetTrendingHashtagMicroserviceHealth();
		assertEquals(HttpStatus.OK, response.getStatus());
	}
}
