package com.chatgpt.open.ia.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

@RestController
@RequestMapping("imagem")
public class GeradorDeImagensService {

    private final ImageModel imageModel;

    public GeradorDeImagensService(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    //http://localhost:8080/imagem?prompt=Escova+de+dente
    @GetMapping
    public String gerarImagem(String prompt){
        var options = ImageOptionsBuilder
                .builder()
                .withHeight(1024)
                .withWidth(1024)
                .build();

        var response = imageModel.call(new ImagePrompt(prompt, options));
        return response.getResult().getOutput().getUrl();



    }


}
