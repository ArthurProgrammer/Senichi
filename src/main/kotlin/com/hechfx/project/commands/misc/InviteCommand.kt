package com.hechfx.project.commands.misc

import com.hechfx.project.api.Reply
import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext

class InviteCommand: CommandBuilder("invite", arrayOf("adicionar", "add"), "misc") {
    override fun onCommand(context: CommandContext) {
        context.textChannel.sendMessage(
            Reply(
                "Obrigado por querer me adicionar, basta apertar no link abaixo!\n\nhttps://discord.com/api/oauth2/authorize?client_id=758128536908988436&permissions=51264&scope=bot"
            ).build(context)
        ).queue()
    }
}