package us.mattgreen;

public class Meal {

    private MealType mealType;
    private String item;
    private int calories;

    public Meal(MealType mealType, String item, int calories) {
        this.mealType = mealType;
        this.item = item;
        this.calories = calories;
    }

    public MealType getMealType() {
        return mealType;
    }

    public String getItem() {
        return item;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Meal meals = (Meal) o;

        if (getCalories() != meals.getCalories()) {
            return false;
        }
        if (getMealType() != meals.getMealType()) {
            return false;
        }
        return getItem().equals(meals.getItem());
    }

    @Override
    public int hashCode() {
        int result = getMealType().hashCode();
        result = 31 * result + getItem().hashCode();
        result = 31 * result + getCalories();
        return result;
    }

    @Override
    public String toString() {
        return "Meal {"
                + "mealType=" + mealType
                + ", item='" + item + '\''
                + ", calories=" + calories
                + '}';
    }

}
