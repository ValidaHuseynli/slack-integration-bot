package com.slack.slack_bot_api.config;

import com.slack.api.methods.SlackApiException;
import com.slack.slack_bot_api.service.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartupNotifier {
    private final SlackService slackService;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() throws SlackApiException, IOException {
        slackService.sendStartupNotification();
        log.info("Startup notification sent");
    }
}
