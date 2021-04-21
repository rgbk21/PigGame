package com.rgbk21.model;

public enum DiceRoll {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);

    private Integer value;

    private DiceRoll(Integer value) {
        this.value = value;
    }

    public static DiceRoll getEnum(Integer roll) {
        switch (roll) {
            case 1: return DiceRoll.ONE;
            case 2: return DiceRoll.TWO;
            case 3: return DiceRoll.THREE;
            case 4: return DiceRoll.FOUR;
            case 5: return DiceRoll.FIVE;
            case 6: return DiceRoll.SIX;
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
