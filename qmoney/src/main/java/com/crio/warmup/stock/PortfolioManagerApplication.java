
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.*;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
// import java.nio.file.Files;
import java.nio.file.Paths;
// import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
// import java.util.Collections;
// import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
// import org.springframework.web.client.RestTemplate;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication {

  final static String Token = "46c9237105728b9f97ec8b54259c694a4a66ab88";

  //  Task:
  //       - Read the json file provided in the argument[0], The file is available in the classpath.
  //       - Go through all of the trades in the given file,
  //       - Prepare the list of all symbols a portfolio has.
  //       - if "trades.json" has trades like
  //         [{ "symbol": "MSFT"}, { "symbol": "AAPL"}, { "symbol": "GOOGL"}]
  //         Then you should return ["MSFT", "AAPL", "GOOGL"]
  //  Hints:
  //    1. Go through two functions provided - #resolveFileFromResources() and #getObjectMapper
  //       Check if they are of any help to you.
  //    2. Return the list of all symbols in the same order as provided in json.

  //  Note:
  //  1. There can be few unused imports, you will need to fix them to make the build pass.
  //  2. You can use "./gradlew build" to check if your code builds successfully.

  public static String getToken(){
    return Token;
  }

  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {
    ObjectMapper objMapper =  getObjectMapper();
    List<String> list = new ArrayList<>();
    File inputFile = resolveFileFromResources(args[0]);

    // File inputFile = new File


    // System.out.println(inputFile);
    PortfolioTrade[] portfolioTrades =  objMapper.readValue(inputFile,PortfolioTrade[].class);
    for(PortfolioTrade pt : portfolioTrades){
      list.add(pt.getSymbol());
    }
     return list;
  }

  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.









  // TODO: CRIO_TASK_MODULE_REST_API
  //  Find out the closing price of each stock on the end_date and return the list
  //  of all symbols in ascending order by its close value on end date.

  // Note:
  // 1. You may have to register on Tiingo to get the api_token.
  // 2. Look at args parameter and the module instructions carefully.
  // 2. You can copy relevant code from #mainReadFile to parse the Json.
  // 3. Use RestTemplate#getForObject in order to call the API,
  //    and deserialize the results in List<Candle>



  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    return Paths.get(
        Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).toFile();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }


  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Follow the instructions provided in the task documentation and fill up the correct values for
  //  the variables provided. First value is provided for your reference.
  //  A. Put a breakpoint on the first line inside mainReadFile() which says
  //    return Collections.emptyList();
  //  B. Then Debug the test #mainReadFile provided in PortfoliomanagerApplicationTest.java
  //  following the instructions to run the test.
  //  Once you are able to run the test, perform following tasks and record the output as a
  //  String in the function below.
  //  Use this link to see how to evaluate expressions -
  //  https://code.visualstudio.com/docs/editor/debugging#_data-inspection
  //  1. evaluate the value of "args[0]" and set the value
  //     to the variable named valueOfArgument0 (This is implemented for your reference.)
  //  2. In the same window, evaluate the value of expression below and set it
  //  to resultOfResolveFilePathArgs0
  //     expression ==> resolveFileFromResources(args[0])
  //  3. In the same window, evaluate the value of expression below and set it
  //  to toStringOfObjectMapper.
  //  You might see some garbage numbers in the output. Dont worry, its expected.
  //    expression ==> getObjectMapper().toString()
  //  4. Now Go to the debug window and open stack trace. Put the name of the function you see at
  //  second place from top to variable functionNameFromTestFileInStackTrace
  //  5. In the same window, you will see the line number of the function in the stack trace window.
  //  assign the same to lineNumberFromTestFileInStackTrace
  //  Once you are done with above, just run the corresponding test and
  //  make sure its working as expected. use below command to do the same.
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testDebugValues

  public static List<String> debugOutputs() {

     String valueOfArgument0 = "trades.json";
     String resultOfResolveFilePathArgs0 = "/home/crio-user/workspace/shivamkeshri955-ME_QMONEY_V2/qmoney/bin/main/trades.json";
     String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@29d89d5d";
     String functionNameFromTestFileInStackTrace = "PortfolioManagerApplication.mainReadFile()";
     String lineNumberFromTestFileInStackTrace = "29:1";


    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
        toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
        lineNumberFromTestFileInStackTrace});
  }
  


  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.
  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
    
    String fileName = args[0];
    List<PortfolioTrade> list = readTradesFromJson(fileName);
    
    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
    LocalDate endDate =  LocalDate.parse(args[1]); //args[1];
    
    List<String> answer = new ArrayList<>();

    RestTemplate restTemplate = new RestTemplate();
    
    List<TotalReturnsDto> candleArr = new ArrayList<>(); 

    for(PortfolioTrade obj : list){
      String url = prepareUrl(obj, endDate, Token);
      // System.out.println(url);
      Candle[] tiingoCandle =  restTemplate.getForObject(url, TiingoCandle[].class);
      if(tiingoCandle==null)
        continue;
      // System.out.println("for "+obj.getSymbol());
      // System.out.println(tiingoCandle[0]);
      // System.out.println(tiingoCandle[1]);
      candleArr.add(new TotalReturnsDto(obj.getSymbol(), tiingoCandle[tiingoCandle.length-1].getClose()));

    }

    Collections.sort(candleArr, (a,b)-> a.getClosingPrice().compareTo(b.getClosingPrice()));
    for(TotalReturnsDto obj : candleArr)
      answer.add(obj.getSymbol());
    return answer;
    //  return Collections.emptyList();
  }

  // TODO:
  //  After refactor, make sure that the tests pass by using these two commands
  //  ./gradlew test --tests PortfolioManagerApplicationTest.readTradesFromJson
  //  ./gradlew test --tests PortfolioManagerApplicationTest.mainReadFile
  public static List<PortfolioTrade> readTradesFromJson(String filename) throws IOException, URISyntaxException {

    List<PortfolioTrade> list = new ArrayList<>();
    
    File inpFile =  resolveFileFromResources(filename);

    ObjectMapper mapper = getObjectMapper();
    PortfolioTrade[] arr = mapper.readValue(inpFile, PortfolioTrade[].class);
    for(PortfolioTrade obj : arr){
        list.add(obj);
    }
    return list;
    // return Collections.emptyList();
  }


  // TODO:
  //  Build the Url using given parameters and use this function in your code to cann the API.
  public static String prepareUrl(PortfolioTrade trade, LocalDate endDate, String token) {
    String Url = "https://api.tiingo.com/tiingo/daily/"+trade.getSymbol()+"/prices?startDate="+trade.getPurchaseDate()+ "&endDate=" + String.valueOf(endDate) +"&token="+token;
    //  return Collections.emptyList();
    return Url;
  }


  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Now that you have the list of PortfolioTrade and their data, calculate annualized returns
  //  for the stocks provided in the Json.
  //  Use the function you just wrote #calculateAnnualizedReturns.
  //  Return the list of AnnualizedReturns sorted by annualizedReturns in descending order.

  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.















  // TODO:
  //  Ensure all tests are passing using below command
  //  ./gradlew test --tests ModuleThreeRefactorTest
  static Double getOpeningPriceOnStartDate(List<Candle> candles) {

    if(candles!=null){
      Candle startDateCandle = candles.get(0);
      return startDateCandle.getOpen();
    }
    
    return 0.0;
  }
  
  
  public static Double getClosingPriceOnEndDate(List<Candle> candles) {
    
    if(candles!=null){
      Candle endDateCandle = candles.get(candles.size()-1);
      return endDateCandle.getClose();
    }
    return 0.0;
  }


  public static List<Candle> fetchCandles(PortfolioTrade trade, LocalDate endDate, String token) {
    String url = prepareUrl(trade, endDate, token);
    RestTemplate restTemplate = new RestTemplate();

    Candle[] candles =  restTemplate.getForObject(url, TiingoCandle[].class);
    return Arrays.asList(candles);
    // return Collections.emptyList();
  }

  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args)
      throws IOException, URISyntaxException {

        File srcFile = resolveFileFromResources(args[0]);

        ObjectMapper objectMapper = getObjectMapper();
        
        PortfolioTrade[] portfolioTrades =  objectMapper.readValue(srcFile, PortfolioTrade[].class);
        
        List<AnnualizedReturn> listOfAnnualizedReturns = new ArrayList<>();
        
        for(PortfolioTrade obj : portfolioTrades){
            List<Candle> candles =  fetchCandles(obj, LocalDate.parse(args[1]), getToken());
            double buyPrice = getOpeningPriceOnStartDate(candles);
            double sellPrice = getClosingPriceOnEndDate(candles);
            AnnualizedReturn annualizedReturnObject = calculateAnnualizedReturns(LocalDate.parse(args[1]), obj, buyPrice, sellPrice);
            listOfAnnualizedReturns.add(annualizedReturnObject);
        }

        Collections.sort(listOfAnnualizedReturns, (o1, o2)-> o1.getAnnualizedReturn()>o2.getAnnualizedReturn() ? -1 : 1);       

     return listOfAnnualizedReturns;
  }

  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Return the populated list of AnnualizedReturn for all stocks.
  //  Annualized returns should be calculated in two steps:
  //   1. Calculate totalReturn = (sell_value - buy_value) / buy_value.
  //      1.1 Store the same as totalReturns
  //   2. Calculate extrapolated annualized returns by scaling the same in years span.
  //      The formula is:
  //      annualized_returns = (1 + total_returns) ^ (1 / total_num_years) - 1
  //      2.1 Store the same as annualized_returns
  //  Test the same using below specified command. The build should be successful.
  //     ./gradlew test --tests PortfolioManagerApplicationTest.testCalculateAnnualizedReturn

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





















  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Once you are done with the implementation inside PortfolioManagerImpl and
  //  PortfolioManagerFactory, create PortfolioManager using PortfolioManagerFactory.
  //  Refer to the code from previous modules to get the List<PortfolioTrades> and endDate, and
  //  call the newly implemented method in PortfolioManager to calculate the annualized returns.

  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.

  public static List<AnnualizedReturn> mainCalculateReturnsAfterRefactor(String[] args)
      throws Exception {
       String file = args[0];
       LocalDate endDate = LocalDate.parse(args[1]);
      //  String contents = readFileAsString(file);
      List<PortfolioTrade> portfolioTrades =  readTradesFromJson(file);
      //  ObjectMapper objectMapper = getObjectMapper();
      PortfolioManager portfolioManager = PortfolioManagerFactory.getPortfolioManager("tiingo",new RestTemplate());
      //  return portfolioManager.calculateAnnualizedReturn(Arrays.asList(portfolioTrades), endDate);
       return portfolioManager.calculateAnnualizedReturn(portfolioTrades, endDate);
  }


  // private static String readFileAsString(String file) {
  //   return null;
  // }











  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());
    // Object ob;
    // System.out.println(ob.toString());

    printJsonObject(mainReadFile(args));


    printJsonObject(mainReadQuotes(args));



   printJsonObject(mainCalculateSingleReturn(args));




    printJsonObject(mainCalculateReturnsAfterRefactor(args));



    // PortfolioManager portfolioManager = PortfolioManagerFactory.getPortfolioManager(new RestTemplate());
    // portfolioManager.calculateAnnualizedReturn(, LocalDate.parse("2023-12-12"));

  }
}

