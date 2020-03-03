package com.dev.QueuePay.user.models.document;

import com.dev.QueuePay.user.models.user.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Document {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String documentName;

    private String documentType;

    @Lob
    private byte[] document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Document(String documentName, String documentType, byte[] document) {
       this.documentName = documentName;
       this.documentType = documentType;
       this.document = document;
    }
}
