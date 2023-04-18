package cc.yysy.servicearea.Bean;

import javax.annotation.Generated;

public class MyAreaSubscription {
    private Integer id;

    private String fullName;

    private String level;

    private Integer obstacleCount;

    private Integer unprocessedObstacleCount;

    private Integer isSubscribed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getObstacleCount() {
        return obstacleCount;
    }

    public void setObstacleCount(Integer obstacleCount) {
        this.obstacleCount = obstacleCount;
    }

    public Integer getUnprocessedObstacleCount() {
        return unprocessedObstacleCount;
    }

    public void setUnprocessedObstacleCount(Integer unprocessedObstacleCount) {
        this.unprocessedObstacleCount = unprocessedObstacleCount;
    }

    public Integer getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(Integer isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    @Override
    public String toString() {
        return "MyAreaSubscription{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", level='" + level + '\'' +
                ", obstacleCount=" + obstacleCount +
                ", unprocessedObstacleCount=" + unprocessedObstacleCount +
                ", isSubscribed=" + isSubscribed +
                '}';
    }
}
