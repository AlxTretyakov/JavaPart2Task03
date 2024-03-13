package tech.inno.tretyakov;

public interface Fractionable {
    double doubleValue();
    double plusValue();
    void setNum(int num);
    void setDenum(int denum);
    int getDenum();
    void changeDenumNoCacheClear(int denum);
}
