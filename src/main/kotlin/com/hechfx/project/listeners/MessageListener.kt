package com.hechfx.project.listeners

import com.hechfx.project.commands.CommandContext
import com.hechfx.project.commands.`fun`.*
import com.hechfx.project.commands.administration.*
import com.hechfx.project.commands.discord.*
import com.hechfx.project.commands.misc.*
import com.hechfx.project.commands.util.*
import com.hechfx.project.config.Configuration
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageListener : ListenerAdapter() {
    val commands = listOf(
            // ======/ ADMIN \======
            ClearCommand(),
            // ======/ DISCORD \======
            AvatarCommand(),
            UserinfoCommand(),
            // ======/ MINECRAFT \======
            // ======/ FUN \======
            ChatbotCommand(),
            CatCommand(),
            DogCommand(),
            // ======/ MISC \======
            BotinfoCommand(),
            PingCommand(),
            InviteCommand(),
            // ======/ UTIL \======
            HelpCommand()
    )
    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if (event.author.isBot) return
        if (!event.message.channelType.isGuild) return

        if (event.message.contentRaw.contains(event.jda.selfUser.asMention)) {
            event.message.channel.sendMessage(
                "Hi, i'm Senichi. My prefix is `!`"
            ).queue()
        }

        if (!event.message.contentRaw.contains(Configuration.PREFIX)) return

        val args = event.message.contentRaw.substring(Configuration.PREFIX.length).trim().split(" ").drop(0)
        val rawArgs = event.message.contentRaw.substring(Configuration.PREFIX.length).trim().split(" ").drop(1)
        val commandArg = args.first()

        for (command in commands) {
            if ((Configuration.PREFIX + command.name) == (Configuration.PREFIX + commandArg)) {
                if (command.dev == true) {
                    if (event.author.idLong != Configuration.OWNER_ID) {
                        event.channel.sendMessage("You can't use this command!").queue()
                        return
                    }
                }
                command.onCommand(
                    CommandContext(
                        rawArgs,
                        event.guild,
                        event.channel,
                        event.message,
                        event.jda,
                        event.author,
                        event.member!!,
                        event.jda.httpClient,
                    )
                )
            }
            for (alias in command.aliases) {
                if ((Configuration.PREFIX + alias) == (Configuration.PREFIX + commandArg)) {
                    command.onCommand(
                        CommandContext(
                            rawArgs,
                            event.guild,
                            event.channel,
                            event.message,
                            event.jda,
                            event.author,
                            event.member!!,
                            event.jda.httpClient
                        )
                    )
                }
            }
        }
    }
}