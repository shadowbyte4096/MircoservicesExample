package assessment.videos.cli;

import assessment.videos.cli.subscription.base.GetSubscriptionMicroserviceHealthCommand;
import assessment.videos.cli.subscription.users.AddSubscriptionsCommand;
import assessment.videos.cli.subscription.users.DeleteSubscriptionCommand;
import assessment.videos.cli.subscription.users.GetSubscriptionsCommand;
import assessment.videos.cli.subscription.videos.GetSuggestionsCommand;
import assessment.videos.cli.trending.base.GetTrendingHashtagMicroserviceHealthCommand;
import assessment.videos.cli.trending.hashtags.GetTopTenHashtagsCommand;
import assessment.videos.cli.video.base.GetVideoMicroserviceHealthCommand;
import assessment.videos.cli.video.hashtags.AddHashtagCommand;
import assessment.videos.cli.video.hashtags.GetHashtagsCommand;
import assessment.videos.cli.video.reactions.AddReactionCommand;
import assessment.videos.cli.video.reactions.GetReactionCommand;
import assessment.videos.cli.video.reactions.UpdateReactionCommand;
import assessment.videos.cli.video.users.AddUserCommand;
import assessment.videos.cli.video.users.GetUserCommand;
import assessment.videos.cli.video.users.GetUsersCommand;
import assessment.videos.cli.video.videos.AddVideoCommand;
import assessment.videos.cli.video.videos.GetVideoCommand;
import assessment.videos.cli.video.videos.GetVideosByHashtagCommand;
import assessment.videos.cli.video.videos.GetVideosByUserCommand;
import assessment.videos.cli.video.videos.GetVideosCommand;
import assessment.videos.cli.video.videos.UpdateVideoCommand;
import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "client", description = "...",
        mixinStandardHelpOptions = true,
        subcommands = {
        		GetSubscriptionMicroserviceHealthCommand.class, GetTrendingHashtagMicroserviceHealthCommand.class, GetVideoMicroserviceHealthCommand.class,
        		AddHashtagCommand.class, GetHashtagsCommand.class, GetTopTenHashtagsCommand.class,
            	AddReactionCommand.class, GetReactionCommand.class, UpdateReactionCommand.class,
        		AddUserCommand.class, GetUserCommand.class, GetUsersCommand.class,
        		AddSubscriptionsCommand.class, GetSubscriptionsCommand.class, DeleteSubscriptionCommand.class,
        		GetSuggestionsCommand.class,
            	AddVideoCommand.class, GetVideoCommand.class, GetVideosCommand.class, GetVideosByHashtagCommand.class, GetVideosByUserCommand.class,  UpdateVideoCommand.class, 
            })
public class VideoCliCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(VideoCliCommand.class, args);
    }

    public void run() {
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }
    }
}
 