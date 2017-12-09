import java.util.*;

public class Table {
    private List<Category> categories;
    private int basketSize;
    private Map<Integer, List<List<Item>>> table;

    Table(List<Category> categories, int basketSize) {
        this.categories = categories;
        this.basketSize = basketSize;
        initTable();
    }

    private void initTable() {
        table = new HashMap<>();
        for (int i = 0; i <= basketSize; i++) {
            List<List<Item>> row = new ArrayList<>();
            for (int j = 0; j < categories.size(); j++) {
                row.add(new ArrayList<>());
            }
            table.put(i, row);
        }
    }

    public void populateTable() {
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            Set<Integer> costs = table.keySet();
            List<Integer> sortedList = new ArrayList(costs);
            Collections.sort(sortedList);
            for (Integer integer : sortedList) {
                List<Item> itemsLessOrEqual = category.getItemsLessOrEqual(integer);
                List<Item> currentList = table.get(integer).get(i);
                if (integer == 0) {
                    continue;
                }
                // get list of items from previous category
                if (i > 0) {
                    List<Item> bestOption = getBestOptionForThisPosition(itemsLessOrEqual, i, integer);
                    currentList.clear();
                    currentList.addAll(bestOption);
                } else {
                    Item maxFromThisCategory;
                    Optional<Item> optional = itemsLessOrEqual.stream().max(Comparator.comparingInt(Item::getBenefit));
                    if (optional.isPresent()) {
                        maxFromThisCategory = optional.get();
                        if (maxFromThisCategory.getCost() <= basketSize)
                            currentList.add(maxFromThisCategory);
                    }
                }
            }
        }
    }

    private List<Item> getBestOptionForThisPosition(List<Item> sameCategoryItems, int categoryIndex, int costIndex) {
        // get all lists from previous category
        List<Item> maxCombo = new ArrayList<>();
        List<Item> previousList = table.get(costIndex - 1).get(categoryIndex);
        List<Item> leftList = table.get(costIndex).get(categoryIndex - 1);

        int previousBenefit = previousList.stream().mapToInt(Item::getBenefit).sum();
        int leftBenefit = leftList.stream().mapToInt(Item::getBenefit).sum();
        maxCombo = previousBenefit >= leftBenefit ? previousList : leftList;
        for (Item sameCategoryItem : sameCategoryItems) {
            int cost = sameCategoryItem.getCost();
            for (int i = costIndex - cost; i >= 0; i--) {
                List<Item> leftListAtCost = table.get(i).get(categoryIndex - 1);

                int leftListBenefit = leftListAtCost.stream().mapToInt(Item::getBenefit).sum();
                if (leftListBenefit + sameCategoryItem.getBenefit() > maxCombo.stream().mapToInt(Item::getBenefit).sum()) {
                    List<Item> newList = new ArrayList<>(leftListAtCost);
                    newList.add(sameCategoryItem);
                    if (newList.stream().mapToInt(Item::getCost).sum() <= basketSize)
                        maxCombo = newList;
                }
            }
        }
        return maxCombo;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<Integer, List<List<Item>>> integerListEntry : table.entrySet()) {
            stringBuilder.append(integerListEntry.getKey()).append("\t");
            for (List<Item> items : integerListEntry.getValue()) {
                for (Item item : items) {
                    stringBuilder.append(item.getName()).append("|");
                }
                int benefit = items.stream().mapToInt(Item::getBenefit).sum();
                int cost = items.stream().mapToInt(Item::getCost).sum();
                stringBuilder.append(cost).append("|").append(benefit).append("\t\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public List<Item> getBestCombination() {
        return table.get(basketSize).get(categories.size() - 1);
    }
}
