package ioopm.calculator;
public class Tuple <E,K> {
    private E a;
    private K b;

    public Tuple(E a, K b) {
        this.a = a;
        this.b = b;
    }
    public E fst() {
        return a;
    }
    public K snd() {
        return b;
    }
}
