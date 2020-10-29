package com.hechfx.project.commands.util

import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext

class InviteCommand: CommandBuilder(
        "invite",
        arrayOf("adicionar", "add"),
        "Shows the link to add me!",
        "util"
) {
    override fun onCommand(context: CommandContext) {
        val message = "here is the link if you want to add me!\n\nhttps://discord.com/api/oauth2/authorize?client_id=758128536908988436&permissions=51264&scope=bot"
        context.reply(message)
    }
}