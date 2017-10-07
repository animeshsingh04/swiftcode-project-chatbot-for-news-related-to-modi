package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FeedResponse;
import data.Message;
import services.FeedService;
import services.NewsAgentService;

import java.util.Objects;
import java.util.UUID;

public class MessageActor extends UntypedActor {

    public static Props props(ActorRef out) {
        return Props.create(MessageActor.class, out);
    }
    private final ActorRef out;

    private FeedService feedService= new FeedService();
    private NewsAgentService newsAgentService= new NewsAgentService();
    public MessageActor(ActorRef out){
        this.out=out;
    }

    public void onReceive(Object message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Message messageObject = new Message();
        if (message instanceof String) {
            messageObject.text = (String) message;
            messageObject.sender = Message.Sender.USER;
            out.tell(mapper.writeValueAsString(messageObject),
                    self());
            String keyword = newsAgentService
                    .getNewsAgentResponse((String) message,
                            UUID.randomUUID()).keyword;
            if(!Objects.equals(keyword, "NOT_FOUND")){
                FeedResponse feedResponse = feedService.getFeedResponse(keyword);
                messageObject.text = (feedResponse.title == null) ? "No results found" : "Showing results for: " + keyword;
                messageObject.feedResponse = feedResponse;
                messageObject.sender = Message.Sender.BOT;
                out.tell(mapper.writeValueAsString(messageObject), self());
            }
        }
    }
}