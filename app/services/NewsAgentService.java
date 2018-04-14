package services;

import com.fasterxml.jackson.databind.JsonNode;
import data.NewsAgentResponse;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class NewsAgentService
{
    public NewsAgentResponse getNewsAgentResponse(String query, UUID sessionId)
    {
        NewsAgentResponse newsAgentResponse = new NewsAgentResponse();
        try
        {
            //Creating websocket for dialogue flow api
            WSRequest queryRequest = WS.url("https://api.api.ai/api/query");
            //set query parameters that has to be sent to the api
            CompletionStage<WSResponse> responsePromise = queryRequest
                    .setQueryParameter("v", "20150919")
                    .setQueryParameter("query", query)
                    .setQueryParameter("lang", "en")
                    .setQueryParameter("sessionId", sessionId.toString())
                    .setQueryParameter("timezone", "2018-13-04T16:57:23+0530")
                    .setHeader("Authorization", "Bearer 054a388ef08e46c3beb61cd9a12dd13f")
                    .get();
            //collect the response from the api
            //the api will return source, keyword and category
            JsonNode response = responsePromise.thenApply(WSResponse::asJson).toCompletableFuture.get();
            //conditions if the api doesnt return all the data
            newsAgentResponse.query = response.get("result").get("parameters").get("keyword").asText().isEmpty() ?
                    (response.get("result").get("parameters").get("source").asText().isEmpty()
                            ? response.get("result").get("parameters").get("category").asText()
                            : response.get("result").get("parameters").get("source").asText() )
                    : response.get("result").get("parameters").get("keyword").asText() ;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return newsAgentResponse;
    }
}
