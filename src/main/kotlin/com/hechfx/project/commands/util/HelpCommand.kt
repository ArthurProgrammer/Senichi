package com.hechfx.project.commands.util

import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext
import net.dv8tion.jda.api.EmbedBuilder
import com.hechfx.project.config.Configuration
import com.hechfx.project.config.Configuration.CLIENT_ID
import com.hechfx.project.config.Configuration.DISCORD_BLURPLE
import com.hechfx.project.listeners.MessageListener

class HelpCommand: CommandBuilder(
        "help",
        arrayOf("ajuda", "comandos"),
        "Show the help embed",
        "util"
) {
    override fun onCommand(context: CommandContext) {
        val commands = MessageListener().commands

        val embed = EmbedBuilder()
                .setTitle("My commands!")
                .setColor(DISCORD_BLURPLE)
                .setThumbnail(context.clientUser.effectiveAvatarUrl)
                .setFooter("Command executed by ${context.author.asTag}", context.author.effectiveAvatarUrl)
        for (command in commands) {
            embed.addField(Configuration.PREFIX + command.name, command.description, false)
        }
        context.sendMessage(embed.build())
    }
}