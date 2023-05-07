package cc.yysy.serviceai.match.impl;

import cc.yysy.serviceai.common.DetectObjectDto;
import cc.yysy.serviceai.common.ImageResult;
import cc.yysy.serviceai.match.MatchService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 使用匈牙利算法进行匹配
 */
@Service
public class MatchServiceHungarianImpl implements MatchService {
/*
DetectObjectDto{
    private String className;
    private Double x;
    private Double y;
    private Double width;
    private Double height;
}
ImageResult{
    Long time;
    Double longitude;
    Double latitude;
    List<DetectObjectDto> dtoList;
}
 */

    @Override
    public List<DetectObjectDto> match(List<ImageResult> imageResultList) {
        List<DetectObjectDto> dtoList = imageResultList.get(0).getDtoList();
        for(int i = 1;i < imageResultList.size();i++){
            List<DetectObjectDto> dtoList1 = imageResultList.get(i).getDtoList();
            dtoList = Hungarian(dtoList, dtoList1);
        }
        return dtoList;
    }

    /**
     * 计算两个点之间的距离
     * @param p1
     * @param p2
     * @return
     */
    private Double distance(DetectObjectDto p1, DetectObjectDto p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    /**
     * 使用匈牙利算法进行匹配,返回匹配后的p1,删除p1中不匹配的元素
     * @param p1
     * @param p2
     * @return
     */
    private List<DetectObjectDto> Hungarian(List<DetectObjectDto> p1, List<DetectObjectDto> p2) {

        //1.计算距离矩阵
        Double[][] distanceMatrix = new Double[p1.size()][p2.size()];
        for(int i = 0;i < p1.size();i++) {
            for(int j = 0;j < p2.size();j++) {
                distanceMatrix[i][j] = distance(p1.get(i), p2.get(j));
            }
        }
        //2.计算最小代价匹配
        int[] match = new int[p1.size()];
        for(int i = 0;i < p1.size();i++) {
            match[i] = -1;
        }
        for(int i = 0;i < p1.size();i++) {
            Double min = Double.MAX_VALUE;
            int minIndex = -1;
            for(int j = 0;j < p2.size();j++) {
                if(distanceMatrix[i][j] < min) {
                    min = distanceMatrix[i][j];
                    minIndex = j;
                }
            }
            match[i] = minIndex;
        }
        //3.计算匹配结果
        for(int i = 0;i < p1.size();i++) {
            p1.get(i).setX((p1.get(i).getX() + p2.get(match[i]).getX()) / 2);
            p1.get(i).setY((p1.get(i).getY() + p2.get(match[i]).getY()) / 2);
            p1.get(i).setWidth((p1.get(i).getWidth() + p2.get(match[i]).getWidth()) / 2);
            p1.get(i).setHeight((p1.get(i).getHeight() + p2.get(match[i]).getHeight()) / 2);
        }
        return p1;
    }
}
