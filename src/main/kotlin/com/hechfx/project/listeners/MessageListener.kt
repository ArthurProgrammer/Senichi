package com.hechfx.project.listeners

import com.hechfx.project.api.Reply
import com.hechfx.project.commands.CommandContext
import com.hechfx.project.commands.`fun`.ChatbotCommand
import com.hechfx.project.commands.discord.AvatarCommand
import com.hechfx.project.commands.misc.*
import com.hechfx.project.config.Configuration
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageListener : ListenerAdapter() {
    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if (event.author.isBot) return
        if (!event.message.channelType.isGuild) return

        if (event.message.contentRaw.contains(event.jda.selfUser.asMention)) {
            event.message.channel.sendMessage(
                "Hi, i'm Senichi. My prefix is `!`"
            )
            return
        }

        if (!event.message.contentRaw.contains(Configuration.PREFIX)) return

        val argsl = event.message.contentRaw.substring(Configuration.PREFIX.length).trim().split(" ").drop(0)
        val rawArgs = event.message.contentRaw.substring(Configuration.PREFIX.length).trim().split(" ").drop(1)
        val commandArg = argsl.first()
        val commands = listOf(
            // ======/ DISCORD \======
            AvatarCommand(),
            // ======/ FUN \======
            ChatbotCommand(),
            // ======/ MISC \======
            BotinfoCommand(),
            PingCommand(),
            InviteCommand()
        )

        for (command in commands) {
            if ((Configuration.PREFIX + command.name) == (Configuration.PREFIX + commandArg)) {
                command.onCommand(
                    CommandContext(
                        rawArgs,
                        event.guild,
                        event.channel,
                        event.message,
                        event.jda,
                        event.author
                    )
                )
            }
            for (alias in command.aliases) {
                if (Configuration.PREFIX + alias == Configuration.PREFIX + commandArg) {
                    command.onCommand(
                        CommandContext(
                            rawArgs,
                            event.guild,
                            event.channel,
                            event.message,
                            event.jda,
                            event.author
                        )
                    )
                }
            }
        }
    }
}