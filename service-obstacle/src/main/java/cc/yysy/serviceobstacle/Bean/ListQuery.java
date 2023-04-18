package cc.yysy.serviceobstacle.Bean;

public class ListQuery {
    /**
     * listQuery: {
     *         page: 1,
     *         limit: 20,
     *         area: undefined,
     *         type: undefined,
     *         processingStatus: undefined,
     *         processingStartTime: undefined,
     *         processingEndTime: undefined,
     *         discoveryStartTime: undefined,
     *         discoveryEndTime: undefined,
     *         processorId: undefined
     */
    private Integer page;
    private Integer limit;
    private String area;
    private String type;
    private Integer processingStatus;
    private String processingStartTime;
    private String processingEndTime;
    private String discoveryStartTime;
    private String discoveryEndTime;
    private Integer processorId;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(Integer processingStatus) {
        this.processingStatus = processingStatus;
    }

    public String getProcessingStartTime() {
        return processingStartTime;
    }

    public void setProcessingStartTime(String processingStartTime) {
        this.processingStartTime = processingStartTime;
    }

    public String getProcessingEndTime() {
        return processingEndTime;
    }

    public void setProcessingEndTime(String processingEndTime) {
        this.processingEndTime = processingEndTime;
    }

    public String getDiscoveryStartTime() {
        return discoveryStartTime;
    }

    public void setDiscoveryStartTime(String discoveryStartTime) {
        this.discoveryStartTime = discoveryStartTime;
    }

    public String getDiscoveryEndTime() {
        return discoveryEndTime;
    }

    public void setDiscoveryEndTime(String discoveryEndTime) {
        this.discoveryEndTime = discoveryEndTime;
    }

    public Integer getProcessorId() {
        return processorId;
    }

    public void setProcessorId(Integer processorId) {
        this.processorId = processorId;
    }
}
