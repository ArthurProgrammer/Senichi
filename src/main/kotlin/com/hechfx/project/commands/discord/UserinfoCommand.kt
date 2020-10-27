package com.hechfx.project.commands.discord

import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color

class UserinfoCommand: CommandBuilder("userinfo", arrayOf("infouser"), "discord") {
    override fun onCommand(context: CommandContext) {
        val user = context.message.mentionedUsers.firstOrNull { it.asMention == context.rawArgs.getOrNull(0)?.replace("!", "") } ?: context.rawArgs.getOrNull(0)?.toLong()?.let {
            context.jda.getUserById(it)
        } ?: context.author

        val userAvatarUrl = user.effectiveAvatarUrl + "?size=4096"

        val embed = EmbedBuilder()
            .setTitle("Informações do Usuário")
            .setThumbnail(userAvatarUrl)
            .setFooter("Command executed by ${context.author.asTag}", context.author.effectiveAvatarUrl + "?size=4096")
            .addField("Tag do Discord", user.asTag, true)
            .addField("ID do usuário", user.id, true)
            .addField("Conta foi criada", "há ${user.timeCreated.year} anos, ${user.timeCreated.monthValue} meses e ${user.timeCreated.dayOfMonth} dias", true)
            if (context.guild.isMember(user)) {
                embed.addField("Entrou aqui", "há ${context.guild.getMemberById(user.id)?.timeJoined}", true)
                val member = context.guild.getMember(user)
                embed.setColor(member?.color ?: Color(114, 137, 218))
            } else {
                embed.setColor(Color(114, 137, 218))
            }

        context.textChannel.sendMessage(embed.build()).queue()
    }
}