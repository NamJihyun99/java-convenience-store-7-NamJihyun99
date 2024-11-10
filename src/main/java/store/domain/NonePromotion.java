package store.domain;

public class NonePromotion extends Promotion {

    private NonePromotion() {
        super("");
    }

    public static NonePromotion getInstance() {
        return new NonePromotion();
    }

    @Override
    public String toString() {
        return "";
    }
}
