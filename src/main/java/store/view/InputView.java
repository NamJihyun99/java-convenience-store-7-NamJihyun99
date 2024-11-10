package store.view;

import camp.nextstep.edu.missionutils.Console;

import java.math.BigInteger;

public class InputView {

    public String readOrderRequest() {
        System.out.println(PromptMessage.orderRequest());
        return readInput();
    }

    public String readPromotionYn(String productName, BigInteger quantity) {
        System.out.println(PromptMessage.askPromotion(productName, quantity));
        return readInput();
    }

    public String readNonPromotionYn(String productName, BigInteger quantity) {
        System.out.println(PromptMessage.askNonPromotion(productName, quantity));
        return readInput();
    }

    public String readMembershipYn() {
        System.out.println(PromptMessage.askMembership());
        return readInput();
    }

    private String readInput() {
        return Console.readLine();
    }
}
