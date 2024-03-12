package tech.inno.tretyakov;

public class Fraction implements Fractionable{
    private int num;
    private int denum;

    public Fraction(int num, int denum) {
        this.num = num;
        this.denum = denum;
    }

    @Override
    @Mutator(fieldName = "DIV_VALUE")
    @Mutator(fieldName = "PLUS_VALUE")
    public void setNum(int num) {
        this.num = num;
    }

    @Override
    @Mutator(fieldName = "DIV_VALUE")
    @Mutator(fieldName = "PLUS_VALUE")
    public void setDenum(int denum) {
        this.denum = denum;
    }

    @Override
    @Cache(fieldName = "DIV_VALUE")
    public double doubleValue(){
        System.out.println("Расчитываем значение (div), так как нет в кеше.");
        return (double) num/denum;
    }

    @Override
    @Cache(fieldName = "PLUS_VALUE")
    public double plusValue(){
        System.out.println("Расчитываем значение (plus), так как нет в кеше.");
        return (int) num + denum;
    }

}
