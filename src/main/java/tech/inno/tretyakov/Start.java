package tech.inno.tretyakov;

import static java.lang.Thread.sleep;

public class Start {
    public static void main(String[] args) throws InterruptedException {
        Fraction fr = new Fraction(2,3);

        // Создание объекта с кэшированием результатов расчета
        Fractionable chachableFr = Utils.cache(fr);

        // Вызов метода для расчета результата деления. Второй вызов возвращает значение из кэша
        System.out.println("");
        System.out.println("===> Первый вызов двух методов расчета для текущего состояния объекта");
        System.out.println("Результат: " + chachableFr.doubleValue());
        System.out.println("Результат: " + chachableFr.plusValue() + "\n");

        System.out.println("===> Второй вызов двух методов расчета для текущего состояния объекта");
        System.out.println("Результат: " + chachableFr.doubleValue());
        System.out.println("Результат: " + chachableFr.plusValue() + "\n");

        chachableFr.setDenum(10);
        chachableFr.setNum(100);
        System.out.println("===> Вызов после изменения состояние объекта");
        System.out.println("Результат: " + chachableFr.doubleValue());
        System.out.println("Результат: " + chachableFr.plusValue() + "\n");

        sleep(2000);
        System.out.println("===> Вызов методода после паузы");
        System.out.println("Результат: " + chachableFr.plusValue() + "\n");

        sleep(5000);

    }
}
