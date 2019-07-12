package com.kuraki.concurrency.chapter16;

public class FightSecurity {

    private int count = 0;
    // 登机牌
    private String boardingPass = "null";
    // 身份证
    private String idCard = "null";

    public void pass(String boardingPass, String idCard) {
        this.boardingPass = boardingPass;
        this.idCard = idCard;
        this.count++;
        check();
    }

    private void check() {
        // 当登机牌和身份证瘦子不相同时则表示检查不会通过
        if (boardingPass.charAt(0) != idCard.charAt(0)) {
            throw new RuntimeException("------Exception------" + toString());
        }
    }

    @Override
    public String toString() {
        return "The " + count + " passengers, boardingPass [" + boardingPass + "], idCard [" + idCard + "]";
    }
}
