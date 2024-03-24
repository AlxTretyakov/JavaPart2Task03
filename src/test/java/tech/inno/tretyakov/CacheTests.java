package tech.inno.tretyakov;

import org.junit.Test;

import static java.lang.Thread.sleep;

public class CacheTests {

    // Тест для проверки расчета с кэшированием результата
    @Test
    public void calculateWithCache() throws InterruptedException {
        double result;

        // Создадим объект, который не подразумевает кэширования результатов расчета
        Fraction fr = new Fraction(16,4);
        // Создание объекта с кэшированием результатов расчета
        Fractionable chachableFr = Utils.cache(fr);

        // Вызов метода расчета должен вернуть ожидаемое значение и закэшировать результат
        result = chachableFr.doubleValue();
        assert result == 4 : "Результат расчета неверен!";
        // Изменим значение поля denum методом, который не сбрасывает кэш и проверим, что значение поля изменилось.
        // При этом в кэше останется закэшироваанное значение результата, расчитанное ранее при прежнем значении поля denum
        chachableFr.changeDenumNoCacheClear(2);
        assert chachableFr.getDenum() == 2 : "Новое значение поля denum неверно!";
        // Второй вызов метода расчета должен вернуть значение из кэша, а не рассчитанное с новым значением поля denum
        result = chachableFr.doubleValue();
        assert result == 4 : "Результат расчета неверен!";
        sleep(5000);
    }

    // Тест для проверки времени жизни закэшированного значения
    @Test
    public void calculateWithCacheAndTime() throws InterruptedException {
        double result;

        // Создадим объект, который не подразумевает кэширования результатов расчета
        Fraction fr = new Fraction(10,2);
        // Создание объекта с кэшированием результатов расчета
        Fractionable chachableFr = Utils.cache(fr);

        // Вызов метода расчета должен вернуть ожидаемое значение и закэшировать результат на заданное аннотацией время = 2000
        result = chachableFr.plusValue();
        assert result == 12 : "Результат расчета неверен!";
        // Изменим значение поля denum методом, который не сбрасывает кэш и проверим, что значение поля изменилось.
        // При этом в кэше останется закэшироваанное значение результата, расчитанное ранее при прежнем значении поля denum
        chachableFr.changeDenumNoCacheClear(5);
        assert chachableFr.getDenum() == 5 : "Новое значение поля denum неверно!";

        // Состояние объекта изменилось, но из кэша должно быть прочитано старое значение, так как время жизни еще не истекло
        sleep (1000);
        result = chachableFr.plusValue();
        assert result == 12 : "Результат расчета неверен!";

        sleep(5000);

    }

    // Тест для проверки удаления утсаревшего значения из кэша
    @Test
    public void calculateEndOfLife() throws InterruptedException {
        double result;

        // Создадим объект, который не подразумевает кэширования результатов расчета
        Fraction fr = new Fraction(10,2);
        // Создание объекта с кэшированием результатов расчета
        Fractionable chachableFr = Utils.cache(fr);

        // Вызов метода расчета должен вернуть ожидаемое значение и закэшировать результат на заданное аннотацией время = 2000
        result = chachableFr.plusValue();
        assert result == 12 : "Результат расчета неверен!";
        // Изменим значение поля denum методом, который не сбрасывает кэш и проверим, что значение поля изменилось.
        // При этом в кэше останется закэшироваанное значение результата, расчитанное ранее при прежнем значении поля denum
        chachableFr.changeDenumNoCacheClear(5);
        assert chachableFr.getDenum() == 5 : "Новое значение поля denum неверно!";

        // Проверим, что после изменения состояния объекта значение всё еще читается из кэша, так как время еще не вышло
        result = chachableFr.plusValue();
        assert result == 12 : "Результат расчета неверен!";

        // Состояние объекта изменилось, но в кэше находится старое значение, которое должно удалиться по истечении времени жизни
        // Вызов метода расчета должен вернуть значение, рассчитанное для нового состояния объекта
        sleep (5000);
        result = chachableFr.plusValue();
        assert result == 15 : "Результат расчета неверен!";

        sleep(5000);

    }

}
