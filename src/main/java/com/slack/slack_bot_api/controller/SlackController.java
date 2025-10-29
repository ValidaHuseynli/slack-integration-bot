package com.slack.slack_bot_api.controller;

import com.slack.api.methods.SlackApiException;
import com.slack.slack_bot_api.service.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/slack")
@RequiredArgsConstructor
public class SlackController {

    private final SlackService slackService;


    @PostMapping("/send-message")
    public String sendMessage(@RequestParam String text){
        try {
            return slackService.sendBlockMessage(text);
        } catch (SlackApiException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "comment", required = false) String comment
            ) throws SlackApiException, IOException {

        return slackService.uploadFile(file, comment);
    }




}
