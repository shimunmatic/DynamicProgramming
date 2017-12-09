import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataParser {
    private static final String REGEX_BASKET = "Basket Size: ([0-9]+)[\\n\\r]+(.*)";
    private static final String REGEX_CATEGORY = "([a-zA-Z0-9]+):(.*)";
    private static final String REGEX_ITEM = "\\[(.*?),([0-9]+),([0-9]+)\\]";


    public static List<Category> parseData(String data) {
        List<Category> categories = new ArrayList<>(1);
        Pattern pattern = Pattern.compile(REGEX_BASKET, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            String body = matcher.group(2);
            Pattern pattern1 = Pattern.compile(REGEX_CATEGORY);
            Matcher matcher1 = pattern1.matcher(body);
            Pattern pattern2 = Pattern.compile(REGEX_ITEM);
            while (matcher1.find()) {
                String categoryName = matcher1.group(1);
                String itemsBody = matcher1.group(2);
                List<Item> categoryItems = new ArrayList<>(1);
                Matcher matcher2 = pattern2.matcher(itemsBody);
                while (matcher2.find()) {
                    String itemName = matcher2.group(1);
                    int cost = Integer.parseInt(matcher2.group(2));
                    int benefit = Integer.parseInt(matcher2.group(3));
                    categoryItems.add(new Item(itemName, cost, benefit));
                }
                categories.add(new Category(categoryName, categoryItems));
            }
        }
        return categories;
    }

    public static int getBasketSize(String data) throws Exception {
        Pattern pattern = Pattern.compile(REGEX_BASKET, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(data);
        if (matcher.find())
            return Integer.parseInt(matcher.group(1));
        throw new Exception("Basket size not found");
    }
}
