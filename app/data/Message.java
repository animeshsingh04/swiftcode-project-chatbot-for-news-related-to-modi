package data;

public class Message {
    public String title,text,time;
    public enum Sender{BOT,USER};
    public Sender sender;
    public FeedResponse feedResponse;
}
