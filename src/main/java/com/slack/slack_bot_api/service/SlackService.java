package com.slack.slack_bot_api.service;

import com.slack.api.methods.SlackApiException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SlackService {

     String sendBlockMessage(String text) throws SlackApiException, IOException;

     String uploadFile(MultipartFile file, String comment) throws IOException, SlackApiException;

     String updateMessage(String ts, String newText) throws SlackApiException, IOException;

     String deleteMessage(String ts) throws SlackApiException, IOException;

     void sendStartupNotification() throws SlackApiException, IOException;
}
