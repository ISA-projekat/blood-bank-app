package com.bloodbank.bloodbankapp.hospitalintegration.news;

import java.nio.charset.StandardCharsets;

public class NewsService {

    private static String[] GetImageParts(News news) {
        String[] imageParts = news.reqImage.split(",");
        return imageParts;
    }

    private static String GetRequestImage(String[] imageParts) {
        return imageParts[1];
    }

    private static String GetImageExtension(String[] imageParts) {
        String extension = imageParts[0].split("/")[1].split(";")[0];
        return extension;
    }

    private static byte[] CreateMessage(News news, String extension, String reqImage) {
        String delimiter = '\n' + "-".repeat(20);
        String upperDelimiter = extension + delimiter;

        String message = upperDelimiter + reqImage + delimiter + news.reqTitle + delimiter + news.reqBody;

        byte[] body = message.getBytes(StandardCharsets.UTF_8);

        return body;
    }

    public static byte[] CreateNewsMessage(News news) {
        String[] imageParts = NewsService.GetImageParts(news);
        String reqImage = NewsService.GetRequestImage(imageParts);
        String extension = NewsService.GetImageExtension(imageParts);

        byte[] message = NewsService.CreateMessage(news, extension, reqImage);
        return message;
    }
}
