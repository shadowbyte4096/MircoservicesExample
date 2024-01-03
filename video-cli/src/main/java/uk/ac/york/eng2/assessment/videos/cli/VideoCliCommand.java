package uk.ac.york.eng2.assessment.videos.cli;

import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import uk.ac.york.eng2.assessment.videos.cli.reactions.AddReactionCommand;
import uk.ac.york.eng2.assessment.videos.cli.reactions.GetReactionCommand;
import uk.ac.york.eng2.assessment.videos.cli.reactions.GetReactionsCommand;
import uk.ac.york.eng2.assessment.videos.cli.reactions.UpdateReactionCommand;
import uk.ac.york.eng2.assessment.videos.cli.users.AddUserCommand;
import uk.ac.york.eng2.assessment.videos.cli.users.DeleteUserCommand;
import uk.ac.york.eng2.assessment.videos.cli.users.GetUserCommand;
import uk.ac.york.eng2.assessment.videos.cli.users.GetUsersCommand;
import uk.ac.york.eng2.assessment.videos.cli.users.UpdateUserCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.AddVideoCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.AddVideoHashtagCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.DeleteVideoCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.DeleteVideoHashtagCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.GetVideoCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.GetVideoHashtagsCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.GetVideosCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.UpdateVideoCommand;

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
 