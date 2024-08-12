package com.crio.warmup.stock.quotes;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import java.time.LocalDate;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.client.RestTemplate;

public class TingoLimitTest {
    
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TiingoService tiingoService;

    private String aaplQuotes = "{\"Information\": \"The **demo** API key is for demo purposes only. "
    + "Please claim your free API key at (https://www.alphavantage.co/support/#api-key) to "
    + "explore our full API offerings. It takes fewer than 20 seconds, and we are committed to "
    + "making it free forever.\"}";

    @Test
    @MockitoSettings(strictness= Strictness.LENIENT)
    public void tingoLimitTest(){
        Mockito.doReturn(aaplQuotes).when(restTemplate).getForObject(anyString(), eq(String.class));
        // Mockito.when(restTemplate).getForObject(anyString(), eq(String.class)).Return(aaplQuotes);
        try{
        tiingoService.getStockQuote("AAPL", LocalDate.parse("2019-01-01"), 
            LocalDate.parse("2019-01-04"));
        }
        catch(Throwable th){
            if(!(th instanceof StockQuoteServiceException))
                fail("Runtime Exception");
        }
        
    }

}
