package com.slack.slack_bot_api.controller;

import com.slack.slack_bot_api.dto.BlockMessageRequest;
import com.slack.slack_bot_api.service.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/slack")
@RequiredArgsConstructor
public class SlackController {

    private final SlackService slackService;

    @PostMapping("/send-message")
    public String sendMessage(@RequestParam String text){
        try {
            slackService.sendMessage(text);
            return "Message sent ✅";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
