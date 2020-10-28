package com.hechfx.project.commands.discord

import com.hechfx.project.commands.CommandContext
import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.config.Configuration
import com.hechfx.project.config.Configuration.DISCORD_BLURPLE
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
            .setTitle("\uD83D\uDDBC $userName avatar")
            .setImage(avatarUrl)
            if (!context.guild.isMember(user)) {
                embed.setColor(DISCORD_BLURPLE)
            } else {
                val member = context.guild.getMember(user)
                embed.setColor(member?.color ?: DISCORD_BLURPLE)
            }
            embed.setFooter("Command executed by ${context.author.asTag}", context.author.effectiveAvatarUrl)
            embed.setDescription("**Click [here]($avatarUrl) to download the avatar!**")
        if (user.idLong == Configuration.CLIENT_ID) {
            embed.appendDescription("*Yes! My avatar is so cute like me.*")
        }
        context.sendMessage(embed.build())
    }
}
