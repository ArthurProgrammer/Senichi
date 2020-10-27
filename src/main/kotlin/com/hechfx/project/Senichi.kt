package com.hechfx.project

import com.hechfx.project.config.Configuration
import com.hechfx.project.listeners.MessageListener
import com.hechfx.project.listeners.MessageUpdateListener
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.utils.ChunkingFilter
import net.dv8tion.jda.api.utils.MemberCachePolicy
import net.dv8tion.jda.api.utils.cache.CacheFlag

object Senichi {
    @JvmStatic
    fun main(args: Array<String>) {
        val shardManager = DefaultShardManagerBuilder.create(Configuration.TOKEN, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_MESSAGES)
            .disableCache(CacheFlag.values().toList())
            .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.EMOTE)
            .setChunkingFilter(ChunkingFilter.NONE)
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .setActivity(Activity.of(Activity.ActivityType.WATCHING, "you!"))
            .setStatus(OnlineStatus.IDLE)
            .addEventListeners(
                MessageListener(),
                MessageUpdateListener()
            )
        shardManager.build()
    }
}