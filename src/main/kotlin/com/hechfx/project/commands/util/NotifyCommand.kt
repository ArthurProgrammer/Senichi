package com.hechfx.project.commands.util

import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext

class NotifyCommand: CommandBuilder(
        "notify",
        arrayOf("notificar"),
        "???",
        onlyOnMainGuild = true
) {
    override fun onCommand(context: CommandContext) {
        val member = context.guild.getMember(context.author)!!
        val notifyRole = context.guild.getRoleById(771108676475551766L)!!

        if (member.roles.contains(notifyRole)) {
            context.reply("now, you'll not receive my notifications updates!")
            context.guild.removeRoleFromMember(member, notifyRole).queue()
            return
        } else {
            context.reply("now, you'll receive my notifications updates!")
            context.guild.addRoleToMember(member, notifyRole).queue()
            return
        }
    }
}