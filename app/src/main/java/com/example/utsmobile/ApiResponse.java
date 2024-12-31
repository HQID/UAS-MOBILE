package com.example.utsmobile;

import org.jsoup.Jsoup;

import java.util.List;

public class ApiResponse {

    private List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public static class Recipe {
        private String title;
        private String summary;
        private String image;
        private Integer healthScore;
        private Integer readyInMinutes;
        private Integer servings;
        private List<Ingredient> extendedIngredients;
        private String instructions;


        public String getTitle() {
            return title;
        }

        public String getSummary() {
            return summary;
        }

        public String getImage() {
            return image;
        }

        // Fungsi untuk menghapus tag HTML menggunakan Jsoup
        public String removeHtmlTagsWithJsoup(String html) {
            if (html == null) {
                return "";
            }
            // Menggunakan Jsoup untuk mengkonversi HTML ke teks biasa
            return Jsoup.parse(html).text();
        }

        // Setter untuk summary yang sudah dibersihkan menggunakan Jsoup
        public void setSummaryWithoutHtml(String html) {
            this.summary = removeHtmlTagsWithJsoup(html);
        }

        public Integer getHealthScore() {
            return healthScore;
        }

        public void setHealthScore(Integer healthScore) {
            this.healthScore = healthScore;
        }

        public Integer getReadyInMinutes() {
            return readyInMinutes;
        }

        public void setReadyInMinutes(Integer readyInMinutes) {
            this.readyInMinutes = readyInMinutes;
        }

        public Integer getServings() {
            return servings;
        }

        public void setServings(Integer servings) {
            this.servings = servings;
        }

        public List<Ingredient> getExtendedIngredients() {
            return extendedIngredients;
        }

        public void setExtendedIngredients(List<Ingredient> extendedIngredients) {
            this.extendedIngredients = extendedIngredients;
        }

        public String getInstructions() {
            return instructions;
        }

        public void setInstructions(String instructions) {
            this.instructions = instructions;
        }
    }
}