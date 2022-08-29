package hry.business.fa.model.em;

/**
 * @author zhouming
 * @date 2020/7/20 14:07
 * @Version 1.0
 */
public enum MonthModel {
    PERIOD_TYPE_1 (1,"按月化利率直接计算", 1),
    PERIOD_TYPE_2 (2,"按日化利率乘以实际天数计算", 2),
    PERIOD_TYPE_3 (3,"按日化利率乘以固定30天计算", 3),
    ;
    private Integer index;
    private String name;
    private Integer value;

    private MonthModel(Integer index,String name, Integer value) {
        this.index = index;
        this.name = name;
        this.value = value;
    }

    public static Integer getValue(Integer index) {
        Integer value = null;
        for (MonthModel e : MonthModel.values()) {
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
