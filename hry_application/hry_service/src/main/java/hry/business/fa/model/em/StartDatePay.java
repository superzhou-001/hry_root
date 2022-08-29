package hry.business.fa.model.em;

/**
 * @author zhouming
 * @date 2020/7/20 16:52
 * @Version 1.0
 */
public enum StartDatePay {
    REPAY_DAY_TYPE_1(1,"按还款日对日还款", 2),
    REPAY_DAY_TYPE_2(2,"按固定还款", 1),
    ;
    private Integer index;
    private String name;
    private Integer value;

    private StartDatePay (Integer index, String name, Integer value) {
        this.index = index;
        this.name = name;
        this.value = value;
    }

    public static Integer getValue(Integer index) {
        Integer value = null;
        for (StartDatePay e : StartDatePay.values()) {
            if (index.equals(e.index)) {
                value = e.value;
            }
        }
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
