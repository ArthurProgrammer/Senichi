package com.hechfx.project.commands.`fun`

import com.fasterxml.jackson.databind.ObjectMapper
import com.hechfx.project.commands.CommandContext
import com.hechfx.project.api.Reply
import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.config.Configuration
import net.dv8tion.jda.api.EmbedBuilder
import okhttp3.Request
import java.net.SocketTimeoutException
import java.net.URL

class ChatbotCommand : CommandBuilder(
    "chatbot",
    arrayOf("chatwbot"),
    "fun",
    "Chat with chatbot api!"
) {
    override fun onCommand(context: CommandContext) {
        if (context.rawArgs.isEmpty()) {
            context.sendMessage(
                    Reply(
                            "How do you'll start a conversation without any word?",
                            mention = true
                    ).build(context)
            )
            return
        }

        val question = context.rawArgs.joinToString(" ")

        val url = URL("https://some-random-api.ml/chatbot?message=${question.replace(" ", "+")}")

        val client = context.httpClient

        val request = Request.Builder()
                .url(url)
                .get()
                .build()
        val res = client.newCall(request).execute()
        val resBody = res.body()!!.string()

        val mapperAll = ObjectMapper()
        val objData = mapperAll.readTree(resBody)

        val answer = objData["response"].asText()

        try {
            if (res.code() == 200) {
                context.sendMessage(
                        Reply(
                                answer,
                                mention = true
                        ).build(context)
                )
                return
            }
        } catch (e: SocketTimeoutException) {
            context.sendMessage("Something has gone wrong... `${e.message}`")
        }
    }
}