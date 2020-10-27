package com.hechfx.project.commands.discord

import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext
import com.hechfx.project.config.Configuration.DISCORD_BLURPLE
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color

class UserinfoCommand: CommandBuilder("userinfo", arrayOf("infouser", "memberinfo"), "discord") {
    override fun onCommand(context: CommandContext) {
        val user = context.message.mentionedUsers.firstOrNull { it.asMention == context.rawArgs.getOrNull(0)?.replace("!", "") } ?: context.rawArgs.getOrNull(0)?.toLong()?.let {
            context.jda.getUserById(it)
        } ?: context.author

        val userAvatarUrl = user.effectiveAvatarUrl + "?size=4096"

        val embed = EmbedBuilder()
            .setTitle("Informações do Usuário")
            .setThumbnail(userAvatarUrl)
            .setFooter("Command executed by ${context.author.asTag}", context.author.effectiveAvatarUrl)
            .addField("Tag do Discord", user.asTag, true)
            .addField("ID do usuário", user.id, true)
            .addField("Conta foi criada", "${user.timeCreated.dayOfMonth}/${user.timeCreated.monthValue}/${user.timeCreated.year} ${user.timeCreated.hour}:${user.timeCreated.minute}", true)
            if (context.guild.isMember(user)) {
                val member = context.guild.getMember(user)
                embed.addField("Entrou aqui", "${member?.timeJoined?.dayOfMonth}/${member?.timeJoined?.monthValue}/${member?.timeJoined?.year} ${member?.timeJoined?.hour}:${member?.timeJoined?.minute}", true)
                embed.setColor(member?.color ?: DISCORD_BLURPLE)
            } else {
                embed.setColor(DISCORD_BLURPLE)
            }

        context.textChannel.sendMessage(embed.build()).queue()
    }
}