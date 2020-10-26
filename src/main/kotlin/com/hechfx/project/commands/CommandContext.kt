package com.hechfx.project.commands

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class CommandContext(var event: GuildMessageReceivedEvent, var args: List<String>, var rawArgs: List<String>, var strippedArgs: List<String>) {
    val message: Message = event.message
    val author: User = event.author

    fun sendMessage(message: String) {
        return event.message.channel.sendMessage(message).queue()
    }
    fun sendMessage(embed: MessageEmbed) {
        return event.message.channel.sendMessage(embed).queue()
    }
}