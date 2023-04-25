package cc.yysy.serviceai.common;


import java.util.List;

public class ImageResult {
    Long time;
    Double longitude;
    Double latitude;
    List<DetectObjectDto> dtoList;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public List<DetectObjectDto> getDtoList() {
        return dtoList;
    }

    public void setDtoList(List<DetectObjectDto> dtoList) {
        this.dtoList = dtoList;
    }
}
