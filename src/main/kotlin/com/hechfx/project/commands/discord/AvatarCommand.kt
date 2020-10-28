package com.hechfx.project.commands.discord

import com.hechfx.project.commands.CommandContext
import com.hechfx.project.commands.CommandBuilder
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color

class AvatarCommand: CommandBuilder(
    "avatar",
    arrayOf("userpfp"),
    "Shows user's avatar!"
) {
    override fun onCommand(context: CommandContext) {

        val user = context.user(0) ?: context.author

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
            .setFooter("Command executed by ${context.author.asTag}", context.author.effectiveAvatarUrl)
            .build()
        context.reply(embed)

    }
}