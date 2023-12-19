package a02a.e1;

import java.util.Map;

public class DietFactoryImpl implements DietFactory {

    @Override
    public Diet standard() {
        return new DietImpl() {

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                int totalCalories = this.getTotalCalories(dietMap);
                return totalCalories >= 1500 && totalCalories <= 2000;
            }

        };
    }

    @Override
    public Diet lowCarb() {
        return new DietImpl() {

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                int totalCalories = this.getTotalCalories(dietMap);
                int totalCarbsCalories = this.getNutrientCalories(Nutrient.CARBS, dietMap);
                return totalCalories >= 1000 && totalCalories <= 1500 && totalCarbsCalories <= 300;
            }
        };
    }

    @Override
    public Diet highProtein() {
        return new DietImpl() {

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                int totalCalories = this.getTotalCalories(dietMap);
                int totalCarbsCalories = this.getNutrientCalories(Nutrient.CARBS, dietMap);
                int totalProteinCalories = this.getNutrientCalories(Nutrient.PROTEINS, dietMap);
                return totalCalories >= 2000 && totalCalories <= 2500 && totalCarbsCalories <= 300 && totalProteinCalories >= 1300;
            }
            
        };
    }

    @Override
    public Diet balanced() {
        return new DietImpl() {

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                int totalCalories = this.getTotalCalories(dietMap);
                int totalCarbsCalories = this.getNutrientCalories(Nutrient.CARBS, dietMap);
                int totalProteinCalories = this.getNutrientCalories(Nutrient.PROTEINS, dietMap);
                int totalFatCalories = this.getNutrientCalories(Nutrient.FAT, dietMap);
                return totalCalories > 1600 && totalCalories < 2000
                    && totalCarbsCalories >= 600 && totalProteinCalories >= 600 && totalFatCalories >= 400
                    && totalProteinCalories + totalFatCalories <= 1100;
            }
            
        };
    }
    
}
