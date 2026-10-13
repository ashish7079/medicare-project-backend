package com.example.demo.Controllers;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.web.bind.annotation.*;

import com.example.demo.Repositorys.MessageRepos;

import com.example.demo.model.Messsage;

@RestController
@CrossOrigin(origins = {
    "http://localhost:5173",
    "https://medicare-project-frontened.vercel.app"
})
public class MessageController {

    private final
    SimpMessagingTemplate
            messagingTemplate;

    private final
    MessageRepos repo;

    public MessageController(

            SimpMessagingTemplate
                    messagingTemplate,

            MessageRepos repo
    ) {

        this.messagingTemplate =
                messagingTemplate;

        this.repo = repo;
    }

    @MessageMapping(
            "/private-message"
    )

    public void sendprivate(

            @Payload
            Messsage message
    ) {


        message.setTime(

                LocalDateTime.now()
                        .toString()
        );


        repo.save(message);

        messagingTemplate
                .convertAndSend(

                "/topic/messages/" +

                message.getReceiverId(),

                message
        );


        messagingTemplate
                .convertAndSend(

                "/topic/messages/" +

                message.getSenderId(),

                message
        );
    }


 // =========================
 // GET OLD MESSAGES
 // =========================

 @GetMapping("/messages")

 public List<Messsage>
 getMessages(

         @RequestParam
         Long senderId,

         @RequestParam
         Long receiverId
 ) {

     return repo.getChat(
             senderId,
             receiverId
     );
 }
}
