package com.hechfx.project.commands.misc

import com.hechfx.project.api.Reply
import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class InviteCommand: CommandBuilder("invite", arrayOf("adicionar", "add"), "misc") {
    override fun onCommand(event: GuildMessageReceivedEvent, context: CommandContext) {
        context.sendMessage(
            Reply(
                "Obrigado por querer me adicionar, basta apertar no link abaixo!\n\n"
            ).build(context)
        )
    }
}