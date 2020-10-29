package com.hechfx.project.commands.administration

import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext
import com.hechfx.project.config.Configuration.CLIENT_ID
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.exceptions.HierarchyException

class KickCommand: CommandBuilder(
        "kick",
        arrayOf("expulsar"),
        "Kicks a member",
        "admin"
) {
    override fun onCommand(context: CommandContext) {
        if (context.rawArgs.isEmpty()) return context.reply("you didn't provide any arguments!")

        val member = context.user(0)?.let { context.guild.getMember(it) }
        var reason = context.rawArgs.drop(1).joinToString(" ")
        val authorAsMember = context.guild.getMember(context.author)!!
        val clientAsMember = context.guild.getMemberById(CLIENT_ID)!!

        if (!authorAsMember.hasPermission(Permission.KICK_MEMBERS)) return context.reply("you don't have permission to use this command!")
        if (!clientAsMember.hasPermission(Permission.KICK_MEMBERS)) return context.reply("i don't have permission to execute this command!")
        if (member == null) return context.reply("member isn't in the server!")
        if (reason.isBlank()) reason = "The reason was not provided"

        try {
            context.guild.kick(member, "Kicked by ${context.author.asTag}: ${reason}").queue()
            context.reply("`${member.user.asTag}` has been punished!")
        } catch (e: HierarchyException) {
            context.reply("something has gone wrong... `${e.message}`")
        }
    }
}