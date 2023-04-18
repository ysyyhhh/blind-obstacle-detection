package cc.yysy.serviceobstacle.filter;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionsResultHandler implements ResultHandler {

    private List<String> obstacleLocations;
    private List<String> obstacleTypes;
    private List<Integer> obstacleProcessorIds;

    @Override
    public void handleResult(ResultContext resultContext) {
        ResultSet rs = (ResultSet) resultContext.getResultObject();
        try {
            if (obstacleLocations == null) {
                obstacleLocations = new ArrayList<>();
                while (rs.next()) {
                    obstacleLocations.add(rs.getString(1));
                }
            } else if (obstacleTypes == null) {
                obstacleTypes = new ArrayList<>();
                while (rs.next()) {
                    obstacleTypes.add(rs.getString(1));
                }
            } else if (obstacleProcessorIds == null) {
                obstacleProcessorIds = new ArrayList<>();
                while (rs.next()) {
                    obstacleProcessorIds.add(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<String> getObstacleLocations() {
        return obstacleLocations;
    }

    public List<String> getObstacleTypes() {
        return obstacleTypes;
    }

    public List<Integer> getObstacleProcessorIds() {
        return obstacleProcessorIds;
    }
}
