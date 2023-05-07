package cc.yysy.serviceai.match.impl;

import cc.yysy.serviceai.common.DetectObjectDto;
import cc.yysy.serviceai.common.ImageResult;
import cc.yysy.serviceai.match.MatchService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MatchServiceSimpleImpl implements MatchService {

    private static final Integer FRAME_THRESHOLD = 5;

    /**
     *
     * 根据pre和after的x，y，width，height判断是否相交
     * @param pre
     * @param after
     * @return
     */
    private boolean intersect(DetectObjectDto pre, DetectObjectDto after) {
        //pre的中心点
        Double preX = pre.getX() + pre.getWidth() / 2;
        Double preY = pre.getY() + pre.getHeight() / 2;
        //after的中心点
        Double afterX = after.getX() + after.getWidth() / 2;
        Double afterY = after.getY() + after.getHeight() / 2;

        //pre的对角线长度的一半
        double preDiagonalLength = Math.sqrt(Math.pow(pre.getWidth(), 2) + Math.pow(pre.getHeight(), 2)) / 2;
        //after的对角线长度的一半
        double afterDiagonalLength = Math.sqrt(Math.pow(after.getWidth(), 2) + Math.pow(after.getHeight(), 2)) / 2;

        //两个中心点的距离
        double distance = Math.sqrt(Math.pow(preX - afterX, 2) + Math.pow(preY - afterY, 2));

        if(distance > preDiagonalLength + afterDiagonalLength) {
            return false;
        }
        return true;
    }

    @Override
    public List<DetectObjectDto> match(List<ImageResult> imageResultList) {
        //now 到 end 之间的图像帧进行融合
        List<DetectObjectDto> dtoList = imageResultList.get(0).getDtoList();

        for(int i = 1;i < imageResultList.size();i++){
            List<DetectObjectDto> dtoList1 = imageResultList.get(i).getDtoList();
            for(DetectObjectDto dto : dtoList){
                for(DetectObjectDto dto1 : dtoList1){
                    if(intersect(dto,dto1)){
                        dto.setCount(dto.getCount() + 1);
                        dto1.setCount(dto1.getCount() + 1);
                    }
                }
            }
            for(DetectObjectDto dto : dtoList1){
                if(dto.getCount() == 1){
                    dtoList.add(dto);
                }
            }
        }

        //如果连续5帧都没有出现过，就删除
        for(int i = 0;i < dtoList.size();i++){
            if(dtoList.get(i).getCount() < FRAME_THRESHOLD){
                dtoList.remove(i);
                i--;
            }
        }
        return dtoList;
    }
}
