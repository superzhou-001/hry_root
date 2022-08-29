package hry.business.fa.model.em;

/**
 * @author zhouming
 * @date 2020/7/20 14:12
 * @Version 1.0
 */
public enum YearModel {
    YEAR_MODEL_TYPE_1 (1,"360天",360),
    YEAR_MODEL_TYPE_2 (2,"365天",365),
    ;
    private Integer index;
    private String name;
    private Integer value;

    private YearModel (Integer index, String name, Integer value) {
        this.index = index;
        this.name = name;
        this.value = value;
    }
    public static Integer getValue(Integer index) {
        Integer value = null;
        for (YearModel e : YearModel.values()) {
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
