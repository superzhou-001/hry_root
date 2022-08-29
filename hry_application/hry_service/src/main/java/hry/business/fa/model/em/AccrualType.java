package hry.business.fa.model.em;

/**
 * @author zhouming
 * @date 2020/7/20 11:43
 * @Version 1.0
 */
public enum AccrualType{

    REPAY_TYPE_1(1,"等额本金","sameprincipal"),
    REPAY_TYPE_2(2,"等额本息","sameprincipalandInterest"),
    REPAY_TYPE_3(3,"等本等息","sameprincipalsameInterest"),
    REPAY_TYPE_4(4,"按期收息, 到本还期","singleInterest"),
    REPAY_TYPE_5(5,"其他方式","other"),
    ;
    private Integer index;
    private String name;
    private String value;

    private AccrualType(Integer index, String name, String value) {
        this.index = index;
        this.name = name;
        this.value = value;
    }

    public static String getValue(Integer index) {
        String value = "";
        for (AccrualType e : AccrualType.values()) {
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
