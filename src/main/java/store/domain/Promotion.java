package store.domain;

public interface Promotion {

    static Promotion of() {
        return new NonePromotion();
    }
}
