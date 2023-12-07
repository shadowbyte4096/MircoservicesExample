package uk.ac.york.eng2.assessment.videos.cli;

import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import uk.ac.york.eng2.assessment.videos.cli.users.AddUserCommand;
import uk.ac.york.eng2.assessment.videos.cli.users.DeleteUserCommand;
import uk.ac.york.eng2.assessment.videos.cli.users.GetUserCommand;
import uk.ac.york.eng2.assessment.videos.cli.users.GetUsersCommand;
import uk.ac.york.eng2.assessment.videos.cli.users.UpdateUserCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.AddVideoCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.AddVideoWatcherCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.DeleteVideoCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.DeleteVideoWatcherCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.GetVideoCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.GetVideoWatchersCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.GetVideosCommand;
import uk.ac.york.eng2.assessment.videos.cli.videos.UpdateVideoCommand;

@Command(name = "video-cli", description = "...",
        mixinStandardHelpOptions = true,
        subcommands = {
            	GetVideosCommand.class, GetVideoCommand.class, AddVideoCommand.class, UpdateVideoCommand.class, DeleteVideoCommand.class,
            	GetUsersCommand.class, GetUserCommand.class, AddUserCommand.class, UpdateUserCommand.class, DeleteUserCommand.class,
            	AddVideoWatcherCommand.class, GetVideoWatchersCommand.class, DeleteVideoWatcherCommand.class
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
