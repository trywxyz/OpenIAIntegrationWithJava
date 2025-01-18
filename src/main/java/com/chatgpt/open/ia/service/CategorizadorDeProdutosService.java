package com.chatgpt.open.ia.service;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.ModelType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorizar")
public class CategorizadorDeProdutosService {

    private final ChatClient chatClient;

    public CategorizadorDeProdutosService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultOptions(ChatOptionsBuilder.builder()
                        .withModel("gpt-4o-mini")
                        .build())
                .build();
    }

    @GetMapping
    public String categorizar(String produto) {

        //http://localhost:8080/categorizador?produto=Escova+de+dente
        var system = """
                Você é um categorizador de produtos e deve responder apenas o nome da categoria do produto informado
                       \s
                    Escolha uma categoria dentro da lista abaixo:
                    1. Higiene pessoal
                    2. Eletrônicos
                    3. Esportes
                    4. Outros
                       \s
                    ###### exemplo de uso:
                       \s
                    Pergunta: Bola de futebol
                    Resposta: Esportes
                """;

        var tokens = contarTokens(system, produto);
        System.out.println("QTD de tokens " + tokens);


        return this.chatClient.prompt()
                .system(system)
                .user(produto)
                .options(ChatOptionsBuilder.builder()
                        .withTemperature(0.85)
                        .build())
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .content();
    }

    private int contarTokens(String system, String user){
        var registry = Encodings.newDefaultEncodingRegistry();
        var enc = registry.getEncodingForModel(ModelType.GPT_4O_MINI);

        return enc.countTokens(system+user);


    }
}
