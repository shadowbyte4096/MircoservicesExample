package assessment.videos.cli;

import assessment.videos.cli.reactions.AddReactionCommand;
import assessment.videos.cli.reactions.GetReactionCommand;
import assessment.videos.cli.reactions.GetReactionsCommand;
import assessment.videos.cli.reactions.UpdateReactionCommand;
import assessment.videos.cli.users.AddUserCommand;
import assessment.videos.cli.users.DeleteUserCommand;
import assessment.videos.cli.users.GetUserCommand;
import assessment.videos.cli.users.GetUsersCommand;
import assessment.videos.cli.users.UpdateUserCommand;
import assessment.videos.cli.videos.AddVideoCommand;
import assessment.videos.cli.videos.AddVideoHashtagCommand;
import assessment.videos.cli.videos.DeleteVideoCommand;
import assessment.videos.cli.videos.DeleteVideoHashtagCommand;
import assessment.videos.cli.videos.GetVideoCommand;
import assessment.videos.cli.videos.GetVideoHashtagsCommand;
import assessment.videos.cli.videos.GetVideosCommand;
import assessment.videos.cli.videos.UpdateVideoCommand;
import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "video-cli", description = "...",
        mixinStandardHelpOptions = true,
        subcommands = {
        		AddVideoCommand.class, AddVideoHashtagCommand.class, DeleteVideoCommand.class, DeleteVideoHashtagCommand.class,
        		GetVideoCommand.class, GetVideoHashtagsCommand.class, GetVideosCommand.class,  UpdateVideoCommand.class, 
        		AddUserCommand.class, DeleteUserCommand.class, GetUserCommand.class, GetUsersCommand.class, UpdateUserCommand.class, 
            	AddReactionCommand.class, GetReactionCommand.class, GetReactionsCommand.class, UpdateReactionCommand.class,
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
 