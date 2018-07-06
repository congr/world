import java.util.HashMap;
import java.util.Map;

public class MapTest {


    public static void main(String[] args) {
        Map<String, Integer> prices = new HashMap<>();

        prices.put("veggies", null);
        System.out.println("Prices map: " + prices);

        // No need to handle initial null value:
        prices.merge("veggies", 42, Integer::sum);

        prices.merge("fruits", 20, Integer::sum);


        System.out.println("Prices map: " + prices);
        System.out.println(prices.keySet());
    }

}
