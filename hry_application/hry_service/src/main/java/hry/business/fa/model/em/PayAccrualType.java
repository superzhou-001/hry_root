package hry.business.fa.model.em;

/**
 * @author zhouming
 * @date 2020/7/20 13:47
 * @Version 1.0
 */
public enum PayAccrualType {
    REPAY_PERIOD_1(1,"按日", "dayPay"),
    REPAY_PERIOD_2(2,"按月", "monthPay"),
    REPAY_PERIOD_3(3,"按季", "seasonPay"),
    REPAY_PERIOD_4(4,"按半年", "halfYearPay"),
    REPAY_PERIOD_5(5,"按年", "yearPay"),
    REPAY_PERIOD_6(6,"按自定义天数", "owerPay"),
    ;
    private Integer index;
    private String name;
    private String value;

    private PayAccrualType (Integer index, String name, String value) {
        this.index = index;
        this.name = name;
        this.value = value;
    }
    public static String getValue(Integer index) {
        String value = "";
        for (PayAccrualType e : PayAccrualType.values()) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
