package com.lx.第二季.贪心.src;

import java.util.Arrays;
import java.util.Comparator;

// 贪心策略解决 0-1 背包问题
public class Knapsack {
    // 物品类
    static class Article {
        public Article(int weight, int value) {
            this.weight = weight;
            this.value = value;
            this.density = (value + 0.0) / weight;
        }

        int weight; // 重量
        int value; // 价值
        double density; // 价值密度

        @Override
        public String toString() {
            return "Article{" +
                    "weight=" + weight +
                    ", value=" + value +
                    ", density=" + density +
                    '}';
        }
    }

    static int maxCapacity = 150; // 背包最大承重
    int loadCapacity = 0; // 已装载的重量
    int loadValue = 0; // 已装载物品的总价值

    // 选择物品装满背包,使用背包所装物品总价值尽可能大
    public void select(Comparator<Article> comparator) {
        Article[] articles = new Article[]{
                new Article(35, 10), new Article(30, 40),
                new Article(60, 30), new Article(50, 50),
                new Article(40, 35), new Article(10, 40),
                new Article(25, 30)
        };

        Arrays.sort(articles, comparator); // 根据使用的贪心策略对数据进行排序

        // 由于可能使用的是重量优先以外的贪心策略,因此需要遍历所有的 article
        for (int i = 0; i < articles.length; i++) {
            Article article = articles[i];
            int surplusCapacity = maxCapacity - this.loadCapacity - article.weight;

            if (surplusCapacity >= 0) {
                loadCapacity += article.weight;
                loadValue += article.value;
                System.out.println("挑选了 编号为 " + (i + 1) + " 的 " + article);
            }
        }

        System.out.println("总重量为: " + this.loadCapacity + ", 总价值为: " + this.loadValue);
        System.out.println();
    }

    public static void main(String[] args) {
        // 使用价值主导策略挑选物品
        new Knapsack().select((Article article1, Article article2) -> article2.value - article1.value);

        // 使用重量主导策略挑选物品
        new Knapsack().select((Article article1, Article article2) -> article1.weight - article2.weight);

        // 使用价值密度主导策略挑选物品
        new Knapsack().select((Article article1, Article article2) -> Double.compare(article2.density, article1.density));
    }
}
