package me.zebbzz.brotatobot.functionality;

import me.zebbzz.brotatobot.functionality.hosting.Secrets;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Main {


    private Main() {

        CommandManager commandManager = new CommandManager();
        Listener listener = new Listener(commandManager);
        Logger logger = LoggerFactory.getLogger(Main.class);

        try {
            logger.info("Booting");
            new JDABuilder(AccountType.BOT) //Sets account type as bot
                    .setToken(Secrets.TOKEN) // Token stored in Secrets class, logs in to the bot
                    .setAudioEnabled(false) // <-- Read
                    .setGame(Game.streaming("Join Our Discord!", "https://discord.gg/CXksb8u")) // <- Discord invite link stored as a "Playing..."
                    .addEventListener(listener) //Read listener class and execute code inside
                    .build().awaitReady(); //
            logger.info("Running");
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Main();
    }

}