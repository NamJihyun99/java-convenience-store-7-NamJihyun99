package store.sale.view;

import camp.nextstep.edu.missionutils.Console;

import java.math.BigInteger;

public class ConsoleInputView {

    public String readOrderRequest() {
        System.out.println(PromptMessage.orderRequest());
        return readInput();
    }

    public String readPromotionYn(ProductAmountDto dto) {
        System.out.println("\n"+PromptMessage.askPromotion(dto.name(), dto.amount()));
        return readInput();
    }

    public String readNonPromotionYn(String productName, BigInteger quantity) {
        System.out.println("\n"+PromptMessage.askNonPromotion(productName, quantity));
        return readInput();
    }

    public String readMembershipYn() {
        System.out.println("\n"+PromptMessage.askMembership());
        return readInput();
    }

    public String readNextTurnYn() {
        System.out.println("\n"+PromptMessage.askNextTurn());
        return readInput();
    }

    private String readInput() {
        return Console.readLine();
    }
}
