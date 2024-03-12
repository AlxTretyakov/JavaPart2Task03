package tech.inno.tretyakov;

public class Start {
    public static void main(String[] args) {
        Fraction fr = new Fraction(2,3);
        double result = fr.doubleValue();
        System.out.println("result = " + result);

        Fractionable chachableFr = Utils.cache(fr);

        System.out.println(chachableFr.doubleValue());
        System.out.println(chachableFr.doubleValue());
        System.out.println(chachableFr.plusValue());
        System.out.println(chachableFr.plusValue());
        chachableFr.setDenum(10);
        chachableFr.setNum(100);
        System.out.println(chachableFr.plusValue());
        System.out.println(chachableFr.doubleValue());
        System.out.println(chachableFr.plusValue());
        chachableFr.setDenum(10);
        chachableFr.setNum(100);
        chachableFr.setDenum(10);
        chachableFr.setNum(100);
        chachableFr.setDenum(10);
        chachableFr.setNum(100);

    }
}
