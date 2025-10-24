package com.slack.slack_bot_api.config;

import com.slack.api.Slack;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackConfig {

    @Bean
    public Slack slackClient() {
        return Slack.getInstance();
    }
}