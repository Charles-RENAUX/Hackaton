package Prheidator.hackaton_prheidatorapp;

public class Session {
    private String id;
    private String sendMessage;
    private String response;

    public Session(String _id){
        this.id=_id;
    }
    public Session(){this.id="id";}

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public String getResponse() {
        return response;
    }
}
