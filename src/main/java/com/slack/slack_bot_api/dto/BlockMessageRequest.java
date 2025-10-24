package com.slack.slack_bot_api.dto;

public record BlockMessageRequest(
        String title,
        String body,
        String buttonText,
        String buttonUrl
) {}