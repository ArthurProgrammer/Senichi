package com.hechfx.project.commands.administration

import com.hechfx.project.api.Reply
import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext
import com.hechfx.project.config.Configuration.CLIENT_ID
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageHistory
import java.lang.IllegalArgumentException
import java.lang.Integer.parseInt
import java.lang.NumberFormatException

class ClearCommand: CommandBuilder(
        "clear",
        arrayOf("clean", "limpar"),
        "Bulk delete messages!",
        "admin"
) {
    override fun onCommand(context: CommandContext) {
        if (context.rawArgs.isEmpty()) {
            context.reply("you didn't provide any arguments!")
            return
        }

        try {
            val quantity = parseInt(context.rawArgs[0])

            if (!context.member.hasPermission(Permission.MESSAGE_MANAGE)) {
                context.reply("you don't have permission to use this command!")
                return
            }

            val clientAsMember = context.guild.getMemberById(CLIENT_ID)!!

            if (!clientAsMember.hasPermission(Permission.MESSAGE_MANAGE)) {
                context.reply("i don't have permission to execute this command!")
                return
            }

            if (quantity < 2 || quantity > 100) {
                context.reply("you need to put a number between 2 and 100!")
                return
            }

            try {
                val channel = MessageHistory(context.textChannel)
                val messages: List<Message> = channel.retrievePast(quantity).complete()

                context.textChannel.deleteMessages(messages).queue()
                context.reply("$quantity messages have been deleted!")
            } catch (e: IllegalArgumentException) {
                context.reply("something has gone wrong... `$e`")
            }
        } catch (e: NumberFormatException) {
            context.reply("you don't have provided a number!")
        }
    }
}