package com.angadi.tripmanagementa.Model;

public class ChatDetailsPojo
{
    String id;
    String chat_id;
    String user_id;
    String type;
    String message;
    String delivered_at;
    String read_at;
    String created_at;

    public String getDelivered_at() {
        return delivered_at;
    }

    public void setDelivered_at(String delivered_at) {
        this.delivered_at = delivered_at;
    }

    public String getRead_at() {
        return read_at;
    }

    public void setRead_at(String read_at) {
        this.read_at = read_at;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }





    public ChatDetailsPojo( String id, String chat_id, String user_id, String type, String message,String delivered_at,String read_at, String created_at)
    {
        this.id = id;
        this.chat_id = chat_id;
        this.user_id = user_id;
        this.type = type;
        this.message = message;
        this.delivered_at = delivered_at;
        this.read_at = read_at;
        this.created_at = created_at;
    }
}
