package com.hechfx.project.commands.administration

import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext
import com.hechfx.project.config.Configuration.CLIENT_ID
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.exceptions.HierarchyException

class BanCommand: CommandBuilder(
        "ban",
        arrayOf("banir"),
        "Bans users!",
        "admin"
) {
    override fun onCommand(context: CommandContext) {
        if (context.rawArgs.isEmpty()) return context.reply("you didn't provide any arguments!")

        val user = context.user(0)
        val member = user?.idLong?.let { context.guild.getMemberById(it) }
        val authorAsMember = context.guild.getMember(context.author)!!
        val clientAsMember = context.guild.getMemberById(CLIENT_ID)!!
        var reason = context.rawArgs.drop(1).joinToString(" ")

        if (reason.isBlank()) reason = "The reason was not provided!"
        if (!authorAsMember.hasPermission(Permission.BAN_MEMBERS)) return context.reply("you don't have permission to use this command!")
        if (!clientAsMember.hasPermission(Permission.BAN_MEMBERS)) return context.reply("i don't have permission to execute this command!")

        try {
            if (member == null) {
                if (user == null) {
                    context.reply("this user doesn't exist!")
                } else {
                    context.reply("`${user.asTag}` has been punished!")
                    context.guild.ban(user, 0, "Banned by ${context.author.asTag}: " + reason).queue()
                }
            } else {
                context.reply("`${member.user.asTag}` has been punished!")
                context.guild.ban(member, 0, "Banned by ${context.author.asTag}: " + reason).queue()
            }
        } catch (e: HierarchyException) {
            context.reply("something has gone wrong... `${e.message}`")
        }
    }
}