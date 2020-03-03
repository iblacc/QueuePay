package com.dev.QueuePay.user.dto;

import com.dev.QueuePay.user.models.document.Document;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

public class InfoRequest {
    @NotBlank
    private String description;

    @NotBlank
    @Lob
    private byte[] logo;

    @NotBlank
    private String documentName;

    @NotBlank
    private String documentType;

    @NotBlank
    @Lob
    private byte[] document;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }
}
