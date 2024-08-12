
package com.crio.warmup.stock.quotes;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;
import com.crio.warmup.stock.dto.AlphavantageCandle;
import com.crio.warmup.stock.dto.AlphavantageDailyResponse;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class AlphavantageService implements StockQuotesService {

  private RestTemplate restTemplate;

  protected AlphavantageService(RestTemplate restTemplate){
    this.restTemplate = restTemplate;
  }

  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException, StockQuoteServiceException
      {
    // TODO Auto-generated method stub
    // System.out.println("calling alphavantage service class");
    String Uri = buildUri(symbol, from, to);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    
    String response = restTemplate.getForObject(Uri, String.class);  
    // System.out.println(response);
    List<Candle> candles = new ArrayList<>();
    
    try{
      AlphavantageDailyResponse alphavantageDailyResponse = objectMapper.readValue(response, AlphavantageDailyResponse.class);
      // System.out.println(alphavantageDailyResponse);
      Map<LocalDate, AlphavantageCandle> map = alphavantageDailyResponse.getCandles();
      
      // Run a loop from "from" to "to" and check that localdate present in map response
      for(LocalDate date=from; date.isBefore(to.plusDays(1)); date=date.plusDays(1)){
          if(map.containsKey(date)){
              AlphavantageCandle obj =  map.get(date);
              obj.setDate(date);
              candles.add(map.get(date));
          }
      }
      return candles; 
    }
    catch(RuntimeException e){
      throw new StockQuoteServiceException("Runtime exception", e);
    }
    // catch(JsonProcessingException e){
    //   throw new StockQuoteServiceException("error while processing response", e);
    // }
    // catch(NullPointerException e){
    //   // System.out.println(e.getClass().getName());
    //   throw new StockQuoteServiceException("error while processing response", e);
    // }

    // System.out.println(alphavantageDailyResponse.getCandles().values());
    
        // return alphavantageDailyResponse.getCandles().values().stream().collect(Collectors.toList());
        
        // return Arrays.asList(candles);
    // return null;
  }



  // private static String getToken() {
  //   return "46c9237105728b9f97ec8b54259c694a4a66ab88";
  // }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
      //  String uriTemplate = "https://api.tiingo.com/tiingo/daily/$SYMBOL/prices?"
      //       + "startDate=$STARTDATE&endDate=$ENDDATE&token=$APIKEY";
    
      // String uri = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=" + "IBM" + "&outputsize=full&apikey=H6CVHA0DGTZ5IDK5";
      String uri = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&outputsize=full&apikey=H6CVHA0DGTZ5IDK5";
      
      return uri;

        // return "https://api.tiingo.com/tiingo/daily/" + symbol + "/prices?startDate=" + startDate
      // + "&endDate=" + endDate + "&token=" + getToken();
  }
  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Implement the StockQuoteService interface as per the contracts. Call Alphavantage service
  //  to fetch daily adjusted data for last 20 years.
  //  Refer to documentation here: https://www.alphavantage.co/documentation/
  //  --
  //  The implementation of this functions will be doing following tasks:
  //    1. Build the appropriate url to communicate with third-party.
  //       The url should consider startDate and endDate if it is supported by the provider.
  //    2. Perform third-party communication with the url prepared in step#1
  //    3. Map the response and convert the same to List<Candle>
  //    4. If the provider does not support startDate and endDate, then the implementation
  //       should also filter the dates based on startDate and endDate. Make sure that
  //       result contains the records for for startDate and endDate after filtering.
  //    5. Return a sorted List<Candle> sorted ascending based on Candle#getDate
  //  IMP: Do remember to write readable and maintainable code, There will be few functions like
  //    Checking if given date falls within provided date range, etc.
  //    Make sure that you write Unit tests for all such functions.
  //  Note:
  //  1. Make sure you use {RestTemplate#getForObject(URI, String)} else the test will fail.
  //  2. Run the tests using command below and make sure it passes:
  //    ./gradlew test --tests AlphavantageServiceTest
  //CHECKSTYLE:OFF
    //CHECKSTYLE:ON
  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  1. Write a method to create appropriate url to call Alphavantage service. The method should
  //     be using configurations provided in the {@link @application.properties}.
  //  2. Use this method in #getStockQuote.
  // TODO: CRIO_TASK_MODULE_EXCEPTIONS
  //   1. Update the method signature to match the signature change in the interface.
  //   2. Start throwing new StockQuoteServiceException when you get some invalid response from
  //      Alphavantage, or you encounter a runtime exception during Json parsing.
  //   3. Make sure that the exception propagates all the way from PortfolioManager, so that the
  //      external user's of our API are able to explicitly handle this exception upfront.
  //CHECKSTYLE:OFF

}

