package a02a.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class DietImpl implements Diet {

    private Map<String, Map<Nutrient,Integer>> foodsNutrientMap = new HashMap<>();

    @Override
    public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
        this.foodsNutrientMap.put(name, nutritionMap);
    }

    protected int getNutrientCalories(Nutrient nutrient, Map<String, Double> dietMap)
    {
        int totalNutrientCalories = 0;
        for (Entry<String, Double> food : dietMap.entrySet()) {
            totalNutrientCalories += this.foodsNutrientMap.get(food.getKey()).get(nutrient) * (food.getValue().intValue() / 100);
        }
        return totalNutrientCalories;
    }

    protected int getTotalCalories(Map<String, Double> dietMap) {
        return getNutrientCalories(Nutrient.CARBS, dietMap)
                + getNutrientCalories(Nutrient.FAT, dietMap)
                + getNutrientCalories(Nutrient.PROTEINS, dietMap);
    }

    @Override
    public abstract boolean isValid(Map<String, Double> dietMap);
    
}
