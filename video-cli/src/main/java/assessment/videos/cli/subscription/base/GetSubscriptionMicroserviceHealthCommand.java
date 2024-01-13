package assessment.videos.cli.subscription.base;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;

@Command(name="get-subscription-microservice-health", description="Gets the health of the Subscription Microservice", mixinStandardHelpOptions = true)
public class GetSubscriptionMicroserviceHealthCommand implements Runnable {

	@Inject
	private BaseClient client;

	@Override
	public void run() {
		HttpResponse<Void> response = client.GetSubscriptionMicroserviceHealth();
		System.out.println("Server responded with: " + response.getStatus());
	}

}
