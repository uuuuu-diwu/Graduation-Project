package Parser;

public class Vocab {
    public String vocab;
    public Node    parent;
    public Vocab(String v,Node p) {
        vocab = new String(v);
        parent = p;
    }
}
