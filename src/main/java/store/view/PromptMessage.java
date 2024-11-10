package store.view;

import java.math.BigInteger;

public class PromptMessage {

    public static String shopMessage() {
        return "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.";
    }

    public static String orderRequest() {
        return "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    }

    public static String askPromotion(String productName, BigInteger quantity) {
        return "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)".formatted(productName, quantity);
    }

    public static String askNonPromotion(String productName, BigInteger quantity) {
        return "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)".formatted(productName, quantity);
    }

    public static String askMembership() {
        return "멤버십 할인을 받으시겠습니까? (Y/N)";
    }

    public static String askNextTurn() {
        return "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";
    }
}
