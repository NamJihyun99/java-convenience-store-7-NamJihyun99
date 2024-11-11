package store.sale.common;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDateTime;

public class SystemDateTime implements DateTime {

    @Override
    public LocalDateTime now() {
        return DateTimes.now();
    }
}
