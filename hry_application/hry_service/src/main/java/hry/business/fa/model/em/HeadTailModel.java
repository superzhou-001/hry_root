package hry.business.fa.model.em;

/**
 * @author zhouming
 * @date 2020/7/20 14:00
 * @Version 1.0
 */
public enum HeadTailModel {
    MODEL_TYPE_1(1,"算头不算尾", 1),
    MODEL_TYPE_2(2,"算头又算尾", 2),
    ;
    private Integer index;
    private String name;
    private Integer value;
    private HeadTailModel(Integer index, String name, Integer value) {
        this.index = index;
        this.name = name;
        this.value = value;
    }
    public static Integer getValue(Integer index) {
        Integer value = null;
        for (HeadTailModel e : HeadTailModel.values()) {
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
