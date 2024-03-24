package tech.inno.tretyakov;

public class Fraction implements Fractionable{
    private int num;
    private int denum;
    private String stateKey;

    public Fraction(int num, int denum) {
        this.num = num;
        this.denum = denum;
        setStateKey();
    }

    @Override
    public void setNum(int num) {
        this.num = num;
        setStateKey();
    }

    @Override
    public void setDenum(int denum) {
        this.denum = denum;
        setStateKey();
    }

    @Override
    @Cache(lifeTime = 1000)
    public double doubleValue(){
        return (double) num/denum;
    }

    @Override
    @Cache(lifeTime = 2000)
    public double plusValue(){
        return num + denum;
    }

    @Override
    public void changeDenumNoCacheClear(int denum){
        this.denum = denum;
    }

    public int getNum() {
        return num;
    }

    @Override
    public int getDenum() {
        return denum;
    }

    private void setStateKey(){
        stateKey = String.valueOf(this.num) + '#' + this.denum;
    }

    public String getStateKey() {
        return stateKey;
    }
}
