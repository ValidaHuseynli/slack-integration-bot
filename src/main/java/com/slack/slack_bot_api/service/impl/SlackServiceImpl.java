package com.slack.slack_bot_api.service.impl;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.block.LayoutBlock;
import com.slack.slack_bot_api.service.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SlackServiceImpl implements SlackService {

    private final Slack slack;

    @Value("${slack.bot-token}")
    private String botToken;

    @Value("${slack.default-channel}")
    private String channel;

    @Override
    public void sendMessage(String text) {
        try {
            slack.methods(botToken).chatPostMessage(req -> req
                    .channel(channel)
                    .text(text)
                    .mrkdwn(true));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);

        }
    }

    @Override
    public void sendBlockMessage(List<LayoutBlock> blocks, String fallbackText) {
        try {
            slack.methods(botToken).chatPostMessage(req -> req
                    .channel(channel)
                    .text(fallbackText == null ? "Message" : fallbackText)
                    .blocks(blocks)
            );
        } catch (IOException | SlackApiException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
