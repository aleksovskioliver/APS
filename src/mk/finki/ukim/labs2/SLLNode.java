package mk.finki.ukim.labs2;

public class SLLNode<E> {
    public E element;
    public SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}