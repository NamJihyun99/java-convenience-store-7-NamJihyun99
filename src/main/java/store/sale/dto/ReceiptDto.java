package store.sale.dto;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.List;

public class ReceiptDto {

    List<ProductAmountPriceDto> buys;
    List<ProductAmountDto> gets;
    BigInteger totalPrice;
    BigInteger promotionDiscount;
    BigInteger membershipDiscount;
    BigInteger result;

    public ReceiptDto(List<ProductAmountPriceDto> buys, List<ProductAmountDto> gets, BigInteger totalPrice, BigInteger promotionDiscount, BigInteger membershipDiscount) {
        this.buys = buys;
        this.gets = gets;
        this.totalPrice = totalPrice;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.result = totalPrice.subtract(promotionDiscount).subtract(membershipDiscount);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###,###");
        StringBuilder builder = new StringBuilder("\n===========W 편의점=============\n");
        builder.append("상품명\t수량\t금액\n");
        buys.forEach(productPriceAmount ->
            builder.append(String.format("%s\t%d\t%s\n",
                    productPriceAmount.name(),
                    productPriceAmount.amount(),
                    df.format(productPriceAmount.totalPrice())))
        );
        builder.append("===========증\t정=============\n");
        gets.forEach(productAmount -> {
            builder.append(String.format("%s\t%d\n", productAmount.name(), productAmount.amount()));
        });
        builder.append("==============================\n");
        builder.append(String.format("총구매액 %d\t%s\n", buys.size(), df.format(totalPrice)));
        builder.append(String.format("행사할인\t\t-%s\n", df.format(promotionDiscount)));
        builder.append(String.format("멤버십할인\t\t-%s\n", df.format(membershipDiscount)));
        builder.append(String.format("내실돈\t\t%s", df.format(result)));
        return builder.toString();
    }
}
