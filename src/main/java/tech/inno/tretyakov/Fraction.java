package tech.inno.tretyakov;

public class Fraction implements Fractionable{
    private int num;
    private int denum;

    public Fraction(int num, int denum) {
        this.num = num;
        this.denum = denum;
    }

    @Override
    @Mutator(fieldName = Fields.DIV)
    @Mutator(fieldName = Fields.PLUS)
    public void setNum(int num) {
        this.num = num;
    }

    @Override
    @Mutator(fieldName = Fields.DIV)
    @Mutator(fieldName = Fields.PLUS)
    public void setDenum(int denum) {
        this.denum = denum;
    }

    @Override
    @Cache(fieldName = Fields.DIV)
    public double doubleValue(){
        System.out.println("Расчитываем значение (div)");
        return (double) num/denum;
    }

    @Override
    @Cache(fieldName = Fields.PLUS)
    public double plusValue(){
        System.out.println("Расчитываем значение (plus)");
        return num + denum;
    }

}
