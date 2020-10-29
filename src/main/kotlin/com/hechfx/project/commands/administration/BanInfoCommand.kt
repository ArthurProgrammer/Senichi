package com.hechfx.project.commands.administration

import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext
import com.hechfx.project.config.Configuration.CLIENT_ID
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.exceptions.ErrorResponseException

class BanInfoCommand: CommandBuilder(
        "baninfo",
        arrayOf("infoban", "checkban"),
        "Shows the ban reason of the member!",
        "admin"
) {
    override fun onCommand(context: CommandContext) {
        if (context.rawArgs.isEmpty()) return context.reply("you didn't provide any arguments!")

        try {
            val user = context.rawArgs.getOrNull(0)?.toLong()?.let { context.guild.retrieveBanById(it).complete() } ?: return context.reply("this user doesn't exist!")
            val authorAsMember = context.guild.getMember(context.author)!!
            val clientAsMember = context.guild.getMemberById(CLIENT_ID)!!

            if (!authorAsMember.hasPermission(Permission.BAN_MEMBERS)) return context.reply("you don't have permission to use this command!")
            if (!clientAsMember.hasPermission(Permission.BAN_MEMBERS)) return context.reply("i don't have permission to execute this command!")

            val replies = listOf(
                    "Banned User: `${user.user.asTag}`",
                    "Ban Reason: `${user.reason ?: ""}`"
            )

            context.sendMessage(replies.joinToString("\n") + context.author.asMention)
        } catch (e: ErrorResponseException) {
            if (e.errorCode == 10026) return context.reply("unknown ban!")
        }
    }
}