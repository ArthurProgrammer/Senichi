package com.hechfx.project.commands.discord

import com.hechfx.project.commands.CommandContext
import com.hechfx.project.commands.CommandBuilder
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.awt.Color

class AvatarCommand: CommandBuilder("avatar", "discord") {
    override fun onCommand(event: GuildMessageReceivedEvent, context: CommandContext) {

        val user = context.message.mentionedUsers.firstOrNull { it.asMention == context.rawArgs.getOrNull(0)?.replace("!", "") } ?: context.args.getOrNull(0)?.toLong()?.let {
            event.jda.getUserById(it)
        } ?: event.author

        val avatarUrl = user.effectiveAvatarUrl + "?size=4096"

        val userName = if (user.name.endsWith("s")) {
            user.name + "'"
        } else {
            user.name + "'s"
        }

        val embed = EmbedBuilder()
            .setTitle("$userName avatar")
            .setImage(avatarUrl)
            .setColor(Color(17, 238, 176))
            .setFooter("Command executed by ${event.author.asTag}", event.author.effectiveAvatarUrl)
            .build()
        context.sendMessage(embed)

    }
}