package com.hechfx.project.api

import com.hechfx.project.commands.CommandContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import java.net.URL
import java.util.*

// This is from MrPowerGamerBR's webserver! :3

class GabrielaImageGenerator(
        val endpoint: String,
        val imageURL: URL,
        val output: String,
        context: CommandContext
) {
    init {
        val url = "https://gabriela-canary.loritta.website"

        val imageConnection = imageURL.openConnection()
        imageConnection.addRequestProperty(
                "User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)"
        )
        val imageInputStream = imageConnection.getInputStream()
        val imageByteArray = imageInputStream.readBytes()
        val client = context.jda.httpClient

        val json = buildJsonObject {
            put("image", Base64.getEncoder().encodeToString(imageByteArray))
        }.toString()

        val requestBody = RequestBody.create(
                MediaType.parse("application/json"),
                json
        )

        val request = Request.Builder()
                .url(url + endpoint)
                .post(requestBody)
                .build()
        val called = client.newCall(request).execute()

        val body = called.body()!!

        val outputByteArray = body.source().readByteArray()

        context.sendFile(outputByteArray, output)
    }
}