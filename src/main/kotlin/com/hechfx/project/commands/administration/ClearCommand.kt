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
    "administration",
    "Bulk delete messages!"
) {
    override fun onCommand(context: CommandContext) {
        if (context.rawArgs.isEmpty()) {
            context.textChannel.sendMessage(
                    Reply(
                            "You didn't provide any arguments!",
                            mention = true
                    ).build(context)
            ).queue()
            return
        }


        try {
            val quantity = parseInt(context.rawArgs[0])

            if (!context.member.hasPermission(Permission.MESSAGE_MANAGE)) {
                context.textChannel.sendMessage(
                        Reply(
                                "You don't have permission to use this command!",
                                mention = true
                        ).build(context)
                ).queue()
                return
            }

            val clientAsMember = context.guild.getMemberById(CLIENT_ID)!!

            if (!clientAsMember.hasPermission(Permission.MESSAGE_MANAGE)) {
                context.textChannel.sendMessage(
                        Reply(
                                "I don't have permission to execute this command!",
                                mention = true
                        ).build(context)
                ).queue()
                return
            }



            if (quantity < 2 || quantity > 100) {
                context.textChannel.sendMessage(
                        Reply(
                                "You need to put a number between 2 and 100!",
                                mention = true
                        ).build(context)
                ).queue()
                return
            }

            try {
                val channel = MessageHistory(context.textChannel)
                val messages: List<Message> = channel.retrievePast(quantity).complete()

                context.textChannel.deleteMessages(messages).queue()
                context.textChannel.sendMessage(
                        Reply(
                                "$quantity messages deleted!",
                                mention = true
                        ).build(context)
                ).queue()
            } catch (e: IllegalArgumentException) {
                context.textChannel.sendMessage(
                        Reply(
                                "$e",
                                mention = true
                        ).build(context)
                ).queue()
            }
        } catch (e: NumberFormatException) {
            context.textChannel.sendMessage(
                    Reply(
                            "You don't have provided a number!",
                            mention = true
                    ).build(context)
            ).queue()
        }
    }
}