package cc.yysy.serviceai.common;

public class RadarResult {
    Integer num;
    Long time;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public RadarResult(Long time, Integer num) {
        this.time = time;
        this.num = num;
    }
}
