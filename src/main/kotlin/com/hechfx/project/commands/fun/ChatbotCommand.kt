package com.hechfx.project.commands.`fun`

import com.fasterxml.jackson.databind.ObjectMapper
import com.hechfx.project.commands.CommandContext
import com.hechfx.project.api.Reply
import com.hechfx.project.commands.CommandBuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import okhttp3.Request

class ChatbotCommand : CommandBuilder("chatbot", arrayOf("chatwbot"),"fun") {
    override fun onCommand(event: GuildMessageReceivedEvent, context: CommandContext) {
        val question: String

        if (context.rawArgs.isEmpty()) {
            return event.channel.sendMessage(
                Reply(
                    "You need to insert your question!",
                ).build(context)
            ).queue()
        } else {
            question = context.rawArgs.joinToString(" ")
        }

        val client = event.jda.httpClient

        val request = Request.Builder()
            .url("https://some-random-api.ml/chatbot?message=${question.replace(" ", "+")}")
            .get()
            .build()

        val res = client.newCall(request).execute()
        val resBody = res.body()!!.string()

        val mapperAll = ObjectMapper()
        val objData = mapperAll.readTree(resBody)

        val answer = objData["response"].asText()

        if (res.code() == 200) {
            context.sendMessage(
                Reply(
                    answer,
                ).build(context)
            )
        }
    }
}