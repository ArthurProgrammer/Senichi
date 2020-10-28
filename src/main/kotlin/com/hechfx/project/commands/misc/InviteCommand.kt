package com.hechfx.project.commands.misc

import com.hechfx.project.api.Reply
import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext

class InviteCommand: CommandBuilder(
    "invite",
    arrayOf("adicionar", "add"),
    "misc",
    "Shows the link to add me!"
) {
    override fun onCommand(context: CommandContext) {
        context.sendMessage(
            Reply(
                "Here is the link if you want to add me!\n\nhttps://discord.com/api/oauth2/authorize?client_id=758128536908988436&permissions=51264&scope=bot",
                    mention = true
            ).build(context)
        )
    }
}