import java.io.IOException;
import java.util.List;

public class Main {

    private static final String INPUT_FILE_LOCATION = "C:\\Users\\Shimun\\IdeaProjects\\NASP\\src\\categories2.data";

    public static void main(String[] args) {

        // load data from file
        try {
            String data = FileLoader.readDataFromFile(INPUT_FILE_LOCATION);
            int basketSize = DataParser.getBasketSize(data);
            List<Category> categories = DataParser.parseData(data);
            categories.forEach(System.out::println);
            // create table
            Table table = new Table(categories, basketSize);
            // add items to the table

            table.populateTable();


            // get optimal items


            // print table
            System.out.print("$");
            categories.forEach(category -> System.out.print(" " + category.getName()));
            System.out.println();
            System.out.println(table);

            System.out.println("Optimal solution is: " + table.getBestCombination());

        } catch (IOException e) {
            System.err.println("File not found: " + INPUT_FILE_LOCATION);
        } catch (Exception e) {
            System.err.println("Basket size not found");
        }


    }
}
