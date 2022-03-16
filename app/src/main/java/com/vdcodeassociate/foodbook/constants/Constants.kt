package com.vdcodeassociate.foodbook.constants

class Constants {

    companion object {

        // API KEY
        const val API_KEY = "036653e14b4440969c09962ff5675bda"

        // BASE URL
        const val BASE_URL = "https://api.spoonacular.com"

        // Image BASE URL
        const val BASE_IMAGE_URL = "https://api.spoonacular.com/cdn/ingredients_100x100/"

        // API Query Keys
        const val QUERY_SEARCH = "query"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_NUMBER = "number"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        // Room Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"

        // BottomSheet and Preferences
        const val PREFERENCES_NAME = "food_recipes_preferences"
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"

    }

}