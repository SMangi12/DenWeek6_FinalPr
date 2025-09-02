package com.example.deninternship_week5.Entity;

//package com.example.deninternship_week5.Entity;

public class Message {
    private String sender;
    private String receiver;
    private String message;
    private long timestamp;

    public Message() { }

    public Message(String sender, String receiver, String message, long timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
}
//public class Message {
//    private String senderId;
//    private String senderName;
//    private String message;
//    private long timestamp;
//
//    public Message() { }
//
//    public Message(String senderId, String senderName, String message, long timestamp) {
//        this.senderId = senderId;
//        this.senderName = senderName;
//        this.message = message;
//        this.timestamp = timestamp;
//    }
//
//    public String getSenderId() {
//        return senderId;
//    }
//
//    public String getSenderName() {
//        return senderName;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public long getTimestamp() {
//        return timestamp;
//    }
//}
