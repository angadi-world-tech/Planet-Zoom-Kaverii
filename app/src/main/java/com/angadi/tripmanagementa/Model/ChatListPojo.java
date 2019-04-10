package com.angadi.tripmanagementa.Model;

public class ChatListPojo
{
     String  id;
     String Msg_conversation;
     String createdAt_conversation;
     String Type_conversation;
     String id_recipient ;
     String  created_at;
     String  updated_at;
     String  recipient;
     String  unique_id;
     String  name;
     String  avatar;
    String delivered_at;
    String read_at;


    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getDelivered_at()
    {
        return delivered_at;
    }

    public void setDelivered_at(String delivered_at) {
        this.delivered_at = delivered_at;
    }

    public String getRead_at()
    {
        return read_at;
    }

    public void setRead_at(String read_at) {
        this.read_at = read_at;
    }



    public String getMsg_conversation() {
        return Msg_conversation;
    }

    public void setMsg_conversation(String msg_conversation) {
        Msg_conversation = msg_conversation;
    }

    public String getCreatedAt_conversation() {
        return createdAt_conversation;
    }

    public void setCreatedAt_conversation(String createdAt_conversation) {
        this.createdAt_conversation = createdAt_conversation;
    }

    public String getType_conversation() {
        return Type_conversation;
    }

    public void setType_conversation(String type_conversation) {
        Type_conversation = type_conversation;
    }



    public String getId_recipient() {
        return id_recipient;
    }

    public void setId_recipient(String id_recipient) {
        this.id_recipient = id_recipient;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }





   public ChatListPojo(String  id, String id_recipient,
                       String  created_at, String  updated_at, String  recipient,
                       String  unique_id, String  name, String  avatar,
                       String msg_conversation,String delivery_at,String read_at,String createdAt_conversation,String type_conversation)
   {
    this.id = id;
    this.id_recipient = id_recipient;
    this.created_at = created_at;
    this.updated_at = updated_at;
    this.recipient = recipient;
    this.unique_id = unique_id;
    this.name = name;
    this.avatar = avatar;
    this.Msg_conversation = msg_conversation;
       this.delivered_at = delivery_at;
    this.read_at = read_at;
    this.createdAt_conversation = createdAt_conversation;
    this.Type_conversation = type_conversation;



    }

}
