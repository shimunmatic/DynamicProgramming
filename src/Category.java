import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Category {

    private String name;
    private List<Item> items;

    public Category(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
                Objects.equals(items, category.items);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, items);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Category: ").append(name).append("\n");
        for (Item item : items) builder.append(item).append("\n");
        return builder.toString();
    }

    public List<Item> getItemsLessOrEqual(Integer integer) {
        return items.stream().filter(item -> item.getCost() <= integer).collect(Collectors.toList());

    }
}
