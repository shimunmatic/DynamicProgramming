import java.util.Objects;

public class Item {
    private String name;
    private int cost;
    private int benefit;

    public Item(String name, int cost, int benefit) {
        this.name = name;
        this.cost = cost;
        this.benefit = benefit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getBenefit() {
        return benefit;
    }

    public void setBenefit(int benefit) {
        this.benefit = benefit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return cost == item.cost &&
                benefit == item.benefit &&
                Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, cost, benefit);
    }

    @Override
    public String toString() {
        return "(" + name + "," + cost + "," + benefit + ")";
    }
}
