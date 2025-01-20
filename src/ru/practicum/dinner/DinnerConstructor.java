package ru.practicum.dinner;

import java.util.*;

public class DinnerConstructor {
    private final HashMap<String, ArrayList<String>> menu = new HashMap<>();

    public HashMap<String, ArrayList<String>> getMenu() {
        return menu;
    }

    public boolean checkType(String type){
        return menu.containsKey(type);
    }

    public void addDish(String type, String dish) {
        if (type.isEmpty()) {
            System.out.println("Не заполнена категория");
            return;
        }

        if (dish.isEmpty()) {
            System.out.println("Не заполнено название блюда");
            return;
        }

        if (!menu.containsKey(type))
            menu.put(type, new ArrayList<String>());

        menu.get(type).add(dish);
    }

    public Iterable<ArrayList<String>> getFoodCombinations(int countCombinations, ArrayList<String> types) {
        return new IterFoodCombinations(countCombinations, types);
    }

    private class IterFoodCombinations implements Iterable<ArrayList<String>> {
        private final int countCombinations;
        private int count;
        private final ArrayList<String> types;
        private static final Random random = new Random();

        IterFoodCombinations(int countCombinations, ArrayList<String> types) {
            if (!menu.isEmpty())
                this.countCombinations = countCombinations;
                // если меню пустое нет смысла
            else
                this.countCombinations = 0;
            this.types = types;
        }

        @Override
        public Iterator<ArrayList<String>> iterator() {
            return new Iterator<ArrayList<String>>() {
                @Override
                public boolean hasNext() {
                    return count < countCombinations;
                }

                @Override
                public ArrayList<String> next() {
                    ArrayList<String> result = new ArrayList<>();

                    for (String type : types) {
                        ArrayList<String> dishes = menu.get(type);
                        int randValue = random.nextInt(dishes.size());
                        result.add(dishes.get(randValue));
                    }

                    count++;
                    return result;
                }
            };
        }
    }

    public static void main(String[] args) {
        DinnerConstructor dc1 = new DinnerConstructor();
        for (ArrayList<String> combination : dc1.getFoodCombinations(
                5, new ArrayList<String>(Arrays.asList("Первое", "Второе"))))
            System.out.println(combination);

        DinnerConstructor dc2 = new DinnerConstructor();
        for (String dish : new ArrayList<String>(Arrays.asList("Каша", "Блинчики", "Оладушки", "Яйцо пашот")))
            dc2.addDish("Первое", dish);
        if (dc2.getMenu().get("Первое") == null || dc2.getMenu().get("Первое").size() != 4)
            System.out.println("Некоректно рабодает добавление в категорию: " + "Первое");

        for (String dish : new ArrayList<String>(Arrays.asList("Суп гороховый", "Борщ", "Солянка", "Бестроганов", "Растегай", "Паста")))
            dc2.addDish("Второе", dish);
        if (dc2.getMenu().get("Второе") == null || dc2.getMenu().get("Второе").size() != 6)
            System.out.println("Некоректно рабодает добавление в категорию: " + "Второе");

        for (String dish : new ArrayList<String>(Arrays.asList("Сок", "Компот")))
            dc2.addDish("Напитки", dish);
        if (dc2.getMenu().get("Напитки") == null || dc2.getMenu().get("Напитки").size() != 2)
            System.out.println("Некоректно рабодает добавление в категорию: " + "Напитки");

        int countCombinations = 0;
        ArrayList<ArrayList<String>> combinations = new ArrayList<>();

        for (ArrayList<String> combination : dc2.getFoodCombinations(
                countCombinations, new ArrayList<String>(Arrays.asList("Первое", "Напитки"))))
            Collections.addAll(combinations, combination);
        System.out.println("countCombinations = " + countCombinations + ", result = " + combinations.size());

        countCombinations = 2;
        combinations.clear();
        for (ArrayList<String> combination : dc2.getFoodCombinations(
                countCombinations, new ArrayList<String>(Arrays.asList("Первое", "Напитки"))))
            Collections.addAll(combinations, combination);
        System.out.println("countCombinations = " + countCombinations + ", result = " + combinations.size());
        System.out.println(combinations);

        countCombinations = 10;
        for (ArrayList<String> combination : dc2.getFoodCombinations(
                countCombinations, new ArrayList<String>(Arrays.asList("Первое", "Второе", "Напитки"))))
            //combinations.addAll(combination);
            Collections.addAll(combinations, combination);
        System.out.println("countCombinations = " + countCombinations + ", result = " + combinations.size());
        System.out.println(combinations);

    }
}
