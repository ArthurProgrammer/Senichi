package com.hechfx.project.listeners

import com.hechfx.project.commands.CommandContext
import com.hechfx.project.commands.`fun`.*
import com.hechfx.project.commands.administration.*
import com.hechfx.project.commands.discord.*
import com.hechfx.project.commands.info.*
import com.hechfx.project.commands.util.*
import com.hechfx.project.config.Configuration
import com.hechfx.project.config.Configuration.CLIENT_ID
import com.hechfx.project.config.Configuration.COMMAND_LOG
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.Webhook
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.managers.WebhookManager
import java.net.URL

class MessageListener : ListenerAdapter() {
    val commands = listOf(
            // ======/ ADMIN \======
            BanCommand(),
            BanInfoCommand(),
            ClearCommand(),
            KickCommand(),
            // ======/ DISCORD \======
            AvatarCommand(),
            UserinfoCommand(),
            ServerInfoCommand(),
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
            NotifyCommand(),
            HelpCommand()
    )
    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if (event.author.isBot) return
        if (!event.message.channelType.isGuild) return

        if (!event.message.contentRaw.contains(Configuration.PREFIX)) return

        val args = event.message.contentRaw.substring(Configuration.PREFIX.length).trim().split(" ").drop(0)
        val rawArgs = event.message.contentRaw.substring(Configuration.PREFIX.length).trim().split(" ").drop(1)
        val commandArg = args.first()

        for (command in commands) {
            if ((Configuration.PREFIX + command.name) == (Configuration.PREFIX + commandArg)) {
                val webhook = event.jda.retrieveWebhookById(COMMAND_LOG).complete()
                webhook.channel.sendMessage("`${event.author.asTag} (${event.author.id})` used the command `${Configuration.PREFIX}${command.name}` in `#${event.channel.name} (${event.channel.id})` channel on `${event.guild.name} (${event.guild.id})` guild!").queue()
                if (command.dev == true) {
                    if (event.author.idLong != Configuration.OWNER_ID) {
                        event.channel.sendMessage("You can't use this command!").queue()
                        return
                    }
                }
                if (command.onlyOnMainGuild == true) {
                    if (event.guild.idLong != Configuration.MAIN_GUILD_ID) return
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
                        event.jda.getUserById(CLIENT_ID)!!
                    )
                )
            }
            for (alias in command.aliases) {
                if ((Configuration.PREFIX + alias) == (Configuration.PREFIX + commandArg)) {
                    val webhook = event.jda.retrieveWebhookById(COMMAND_LOG).complete()
                    webhook.channel.sendMessage("`${event.author.asTag} (${event.author.id})` used the command `${Configuration.PREFIX}${alias}` in `#${event.channel.name} (${event.channel.id})` channel on `${event.guild.name} (${event.guild.id})` guild!").queue()
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
                            event.jda.getUserById(CLIENT_ID)!!
                        )
                    )
                }
            }
        }
    }
}