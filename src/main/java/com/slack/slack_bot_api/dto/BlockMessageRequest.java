package com.slack.slack_bot_api.dto;

import lombok.Data;

@Data
public class BlockMessageRequest {

    private String channel;
    private String message;

}
