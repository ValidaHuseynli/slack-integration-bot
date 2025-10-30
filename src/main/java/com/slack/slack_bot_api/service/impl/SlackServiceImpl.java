package com.slack.slack_bot_api.service.impl;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.files.FilesUploadV2Request;
import com.slack.api.model.File;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.composition.BlockCompositions;
import com.slack.slack_bot_api.service.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public String sendBlockMessage(String text) throws SlackApiException, IOException {
        var message = ChatPostMessageRequest
                .builder()
                .channel(channel)
                .blocks(List.of(
                        Blocks.section(section -> section.text(
                                BlockCompositions.markdownText(text)
                        ))
                ))
                .build();
        var response = slack.methods(botToken).chatPostMessage(message);
        return response.isOk() ? "Message Sent" : "Error: " + response.getMessage();
    }

    @Override
    public String uploadFile(MultipartFile file, String comment) throws IOException, SlackApiException {

        var request = FilesUploadV2Request
                .builder()
                .channel(channel)
                .filename(file.getOriginalFilename())
                .initialComment(comment)
                .fileData(file.getInputStream().readAllBytes())
                .build();
        var response = slack.methods(botToken).filesUploadV2(request);
        if (response.isOk()) {
            File uploaded = response.getFiles().get(0);
            return "OK: " + uploaded.getName() + " → " + uploaded.getPermalink();
        }

        throw new RuntimeException("Slack upload error: " + response.getError());
    }

    @Override
    public String updateMessage(String ts, String newText) throws SlackApiException, IOException {


        var response = slack.methods(botToken).chatUpdate(req ->
                req.channel(channel)
                        .ts(ts)
                        .text(newText)
        );

        return response.isOk() ? "Message Edited" : "Error: " + response.getError();
    }

    @Override
    public String deleteMessage(String ts) throws SlackApiException, IOException {
        var response = slack.methods(botToken)
                .chatDelete(req ->
                        req.channel(channel)
                                .ts(ts)
                );
        return response.isOk() ? "Successfully Deleted" : "Something went wrong" + response.getError();
    }

    @Override
    public void sendStartupNotification() throws SlackApiException, IOException {
        var response = slack.methods(botToken)
                .chatPostMessage(req ->
                        req.channel(channel)
                                .text("Service started successfully")
                );
        if(!response.isOk())
            System.out.println(response.getError());

    }


}
