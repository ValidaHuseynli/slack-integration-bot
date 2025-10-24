package com.slack.slack_bot_api.service;

import com.slack.api.methods.SlackApiException;
import com.slack.api.model.block.LayoutBlock;

import java.io.IOException;
import java.util.List;

public interface SlackService {
     void sendMessage(String text) throws SlackApiException, IOException;

     void sendBlockMessage(List<LayoutBlock> blocks, String fallbackText);
}
