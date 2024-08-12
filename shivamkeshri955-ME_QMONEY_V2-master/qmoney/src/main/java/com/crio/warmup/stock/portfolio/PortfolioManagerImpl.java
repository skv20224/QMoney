
package com.crio.warmup.stock.portfolio;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.crio.warmup.stock.quotes.StockQuoteServiceFactory;
import com.crio.warmup.stock.quotes.StockQuotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class PortfolioManagerImpl implements PortfolioManager {

  private RestTemplate restTemplate;
  private StockQuotesService stockQuotesService;


  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
  // protected PortfolioManagerImpl(RestTemplate restTemplate, StockQuotesService stockQuotesService) {
  //   this.restTemplate = restTemplate;
  //   this.stockQuotesService = stockQuotesService;
  // }
  
  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    // this.stockQuotesService = stockQuotesService;
  }
  
  protected PortfolioManagerImpl(StockQuotesService stockQuotesService) {
    // this.restTemplate = restTemplate;
    this.stockQuotesService = stockQuotesService;

  //   protected PortfolioManagerImpl(RestTemplate restTemplate) {
  //   this.restTemplate = restTemplate;
  }








  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }

  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo third-party APIs to a separate function.
  //  Remember to fill out the buildUri function and use that.



  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws StockQuoteServiceException{
        
        // if(stockQuotesService==null){
        //   stockQuotesService = StockQuoteServiceFactory.INSTANCE.getService("alphavantage", restTemplate);
        //   return stockQuotesService.getStockQuote(symbol, from, to);
        // }
        List<Candle> candlesList = new ArrayList<>(); //= stockQuotesService.getStockQuote(symbol, from, to);
        try{
          candlesList = stockQuotesService.getStockQuote(symbol, from, to);
          // System.out.println(candlesList);
        }
        catch(JsonProcessingException e){
          e.getStackTrace();
        }   
        return candlesList;

        // String Uri = buildUri(symbol, from, to);
        
        // Candle[] candles = restTemplate.getForObject(Uri, TiingoCandle[].class);
        
        
        // return Arrays.asList(candles);
     
  }

  // public static String getToken() {
  //   return "46c9237105728b9f97ec8b54259c694a4a66ab88";
  // }

  // protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
  //     //  String uriTemplate = "https://api.tiingo.com/tiingo/daily/$SYMBOL/prices?"
  //     //       + "startDate=$STARTDATE&endDate=$ENDDATE&token=$APIKEY";
          
  //     //   return uriTemplate;
  //     return "https://api.tiingo.com/tiingo/daily/" + symbol + "/prices?startDate=" + startDate
  //     + "&endDate=" + endDate + "&token=" + getToken();
  // }

  public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
      PortfolioTrade trade, Double buyPrice, Double sellPrice) {

        LocalDate startDate = trade.getPurchaseDate();
        Period period = Period.between(startDate, endDate);
        
        int totalYears = period.getYears();
        int totalMonths = period.getMonths();
        int totalDays = period.getDays();


        
        // System.out.println(totalDays);
        double totalNumYears = (double) (totalYears/1.0) + (totalMonths/12.0) + (totalDays/365.24);
        // System.out.println(totalNumYears);
        double totalReturn =  (sellPrice-buyPrice)/buyPrice;
        // System.out.println(totalNumYears);
        double annualizedReturn =  Math.pow((1+totalReturn), (1/totalNumYears)) - 1;
        // annualizedReturn = annualizedReturn*100;  

      return new AnnualizedReturn(trade.getSymbol(), annualizedReturn, totalReturn);
  }


  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades,
      LocalDate endDate) throws StockQuoteServiceException {
    // TODO Auto-generated method stub
      
    List<AnnualizedReturn> lAnnualizedReturns = new ArrayList<>();

    // try{
      for(PortfolioTrade obj : portfolioTrades){
        String symbol = obj.getSymbol();
        LocalDate startDate = obj.getPurchaseDate();
        // String Uri = buildUri(symbol, startDate, endDate);
        // Candle[] candles = restTemplate.getForObject(Uri, TiingoCandle[].class);
        // List<Candle> candles;
        
          List<Candle> candles = getStockQuote(symbol, startDate, endDate);
          
          double buyPrice = candles.get(0).getOpen();
          // System.out.println(buyPrice);
          double sellPrice = candles.get(candles.size()-1).getClose();
          // System.out.println(sellPrice);
          AnnualizedReturn annualizedReturn = calculateAnnualizedReturns(endDate, obj, buyPrice, sellPrice);
        //  System.out.println(annualizedReturn.getAnnualizedReturn());
          lAnnualizedReturns.add(annualizedReturn);
        }
        lAnnualizedReturns.sort(getComparator());
        return lAnnualizedReturns;
      // } 
        // catch (RuntimeException | JsonProcessingException  e) {
          // TODO Auto-generated catch block
          // e.printStackTrace();
          // throw new StockQuoteServiceException("abc", e); 
        // }
      
  }

  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturnParallel(
      List<PortfolioTrade> portfolioTrades, LocalDate endDate, int numThreads)
      throws InterruptedException, StockQuoteServiceException {
    // TODO Auto-generated method stub

    List<AnnualizedReturn> answer = new ArrayList<>();
    ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
    
    for(PortfolioTrade trade : portfolioTrades){
      threadPool.execute( () -> {
        String symbol = trade.getSymbol();
        LocalDate startDate = trade.getPurchaseDate();
        // List<Candle> candles; //= new ArrayList<>();
        try {
          List<Candle> candles = getStockQuote(symbol, startDate, endDate);
          Double buyPrice = candles.get(0).getOpen();
          Double sellPrice = candles.get(candles.size()-1).getClose();
          AnnualizedReturn annualizedReturn = calculateAnnualizedReturns(endDate, trade, buyPrice, sellPrice);
          answer.add(annualizedReturn);    
        } 
        catch (StockQuoteServiceException e) {
          // TODO Auto-generated catch block
          e.getMessage();
        }
        
      });
      Thread.sleep(100);
    }
    // System.out.println(answer);
    
    Thread.sleep(5500);
    // threadPool.
    if(answer.size()!=portfolioTrades.size())
      throw new StockQuoteServiceException("Error while processing..");
    answer.sort(getComparator());
    // System.out.println(answer);
    threadPool.shutdown();
    return answer;

    // portfolioTrades.stream().map(trade -> {
    //   String symbol = trade.getSymbol();
    //   LocalDate startDate = trade.getPurchaseDate();
    //   List<Candle> candles = getStockQuote(symbol, startDate, endDate);

    //   Double buyPrice = candles.get(0).getOpen();
    //   Double sellPrice = candles.get(candles.size()-1).getClose();
    //   AnnualizedReturn annualizedReturn = calculateAnnualizedReturns(endDate, trade, buyPrice, sellPrice);
    //   answer.add(annualizedReturn);
    // });

  } 




  // ¶TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Modify the function #getStockQuote and start delegating to calls to
  //  stockQuoteService provided via newly added constructor of the class.
  //  You also have a liberty to completely get rid of that function itself, however, make sure
  //  that you do not delete the #getStockQuote function.

}
