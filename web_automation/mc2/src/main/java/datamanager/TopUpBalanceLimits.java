package datamanager;


import java.util.Arrays;
import java.util.List;

public enum TopUpBalanceLimits {

    MAX_USD("USD", 40000),
    MAX_EUR("EUR", 35000),
    MAX_ZAR("EUR", 500000),

    MIN_USD("USD", 10),
    MIN_EUR("EUR", 10),
    MIN_ZAR("EUR", 100),
    ;

    String currency;
    int maxValue;


   TopUpBalanceLimits(String currency, int maxValue) {
        this.currency = currency;
        this.maxValue = maxValue;
   }

   public int getMaxValue() {
       return this.maxValue;
   }

   public String getCurrency(){
       return this.currency;
   }

   public static int getMaxValueByCurrency(String currency){
       TopUpBalanceLimits[] currencyArray = TopUpBalanceLimits.values();
       List<TopUpBalanceLimits> currencyList = Arrays.asList(currencyArray);
       return currencyList.stream().filter(e -> e.getCurrency().equalsIgnoreCase(currency) &&
                                                e.toString().toLowerCase().contains("max"))
               .findFirst().orElseThrow(() -> new AssertionError(
               "Unsupported '"+currency+"' curency type"))
               .getMaxValue();
    }

    public static int getMinValueByCurrency(String currency){
        TopUpBalanceLimits[] currencyArray = TopUpBalanceLimits.values();
        List<TopUpBalanceLimits> currencyList = Arrays.asList(currencyArray);
        return currencyList.stream().filter(e -> e.getCurrency().equalsIgnoreCase(currency) &&
                e.toString().toLowerCase().contains("min"))
                .findFirst().orElseThrow(() -> new AssertionError(
                "Unsupported '"+currency+"' curency type"))
                .getMaxValue();
    }
}
