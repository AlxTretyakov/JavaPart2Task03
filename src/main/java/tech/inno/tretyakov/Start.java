package tech.inno.tretyakov;

public class Start {
    public static void main(String[] args) {
        Fraction fr = new Fraction(2,3);
        double result = fr.doubleValue();
        System.out.println("result = " + result);
        result = fr.plusValue();
        System.out.println("result = " + result);
        System.out.println("-------------------");

        // Создание объекта с кешированием результатов расчета
        Fractionable chachableFr = Utils.cache(fr);

        // Вызов метода для расчета результата деления. Второй вызов возвращает значение из кеша
        System.out.println("Результат: " + chachableFr.doubleValue());
        System.out.println("Результат: " + chachableFr.doubleValue());
        System.out.println("-------------------");

        // Вызов метода для расчета результата сложения. Второй вызов возвращает значение из кеша
        System.out.println("Результат: " + chachableFr.plusValue());
        System.out.println("Результат: " + chachableFr.plusValue());
        System.out.println("-------------------");

        // Установка новых значений - содердимое кеша сбрасывается
        chachableFr.setDenum(10);
        chachableFr.setNum(100);
        System.out.println("-------------------");

        System.out.println("Результат: " + chachableFr.doubleValue());
        System.out.println("Результат: " + chachableFr.doubleValue());
        System.out.println("-------------------");

        System.out.println("Результат: " + chachableFr.plusValue());
        System.out.println("Результат: " + chachableFr.plusValue());
        System.out.println("-------------------");

    }
}
