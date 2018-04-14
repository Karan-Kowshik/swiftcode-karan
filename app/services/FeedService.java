package services;

import data.FeedResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class FeedService
{
    public FeedResponse getFeedByQuery(String query)
    {
        FeedResponse feedResponseObject = new FeedResponse();
        try
        {
            //creating a web socket request for the google news api
            WSRequest feedRequest = WS.url("https://news.google.com/news");
            //set query parameters that has to be sent to google news api
            //query parameters are retrieved from NewsAgentService.java
            CompletionStage<WSResponse> responsePromise = feedRequest
                    .getQueryParameter("q", "ipl")
                    //output:rss gives result in XML
                    .getQueryParameter("output", "rss")
                    .get();
            //JsonNode response = responsePromise.thenApply(WSResponse::asJson).toCompletableFuture.get();
            //Docuument for xml
            Document feedResponse = responsePromise.thenApply(WSResponse::asXml).toCompletableFuture.get();
            //Getting the 10item in the <channel>
            Node item = feedResponse.getFirstChild().getFirstChild().getChildNode().item(10);
            //Getting title, description and pubDate from the 10th item in channel
            feedResposeObject.title = item.getChildNodes().item(0).getFirstChild().getNodeValue();
            feedResposeObject.description = item.getChildNodes().item(4).getFirstChild().getNodeValue();
            feedResposeObject.pubDate = item.getChildNodes().item(3).getFirstChild().getNodeValue();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return feedResponseObject;
    }
}
