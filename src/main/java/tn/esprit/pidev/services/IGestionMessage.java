package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Message;

public interface IGestionMessage {

    public Message sendMessage (Long senderUser, Long discussion, String message);

    public Message modifyMessage (Long id, String message);

    public boolean deleteMessage (Long id);

    public Message replyMessage(Long userSender, Long discussion, Long message, String reply);


    }
