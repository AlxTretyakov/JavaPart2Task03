package tech.inno.tretyakov;
import org.junit.Test;

public class CacheTests {
    // Тест для проверки расчета без кеширования
    @Test
    public void calculateWithNoCache(){
        // Создадим объект, который не подразумевает кеширования результатов расчета
        Fraction fr = new Fraction(10,5);
        double result;

        // Вызов метода расчета должен вернуть ожидаемое значение
        result = fr.doubleValue();
        assert result == 2 : "Результат расчета неверен!";

        // Изменим значение поля методом, который никогда не очищает кеш
        // Повторный вызов метода расчета должен вернуть значение не из кеша, а расчитать с учетом нового значения поля
        fr.changeDenumNoCacheClear(2);
        result = fr.doubleValue();
        assert result == 5 : "Результат расчета неверен!";

    }

    // Тест для проверки расчета с кешированием результата
    @Test
    public void calculateWithCache() {
        double result;

        // Создадим объект, который не подразумевает кеширования результатов расчета
        Fraction fr = new Fraction(16,4);
        // Создание объекта с кешированием результатов расчета
        Fractionable chachableFr = Utils.cache(fr);

        // Вызов метода расчета должен вернуть ожидаемое значение и закешировать результат
        result = chachableFr.doubleValue();
        assert result == 4 : "Результат расчета неверен!";
        // Изменим значение поля denum методом, который не сбрасывает кеш и проверим, что значение поля изменилось.
        // При этом в кеше останется закешироваанное значение результата, расчитанное ранее при прежнем значении поля denum
        chachableFr.changeDenumNoCacheClear(2);
        assert chachableFr.getDenum() == 2 : "Новое значение поля denum неверно!";
        // Второй вызов метода расчета должен вернуть значение из кеша, а не рассчитанное с новым значением поля denum
        result = chachableFr.doubleValue();
        assert result == 4 : "Результат расчета неверен!";

    }

}
