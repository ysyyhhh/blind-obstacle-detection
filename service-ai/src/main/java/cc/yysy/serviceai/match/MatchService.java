package cc.yysy.serviceai.match;

import cc.yysy.serviceai.common.DetectObjectDto;
import cc.yysy.serviceai.common.ImageResult;

import java.util.List;

public interface MatchService {

    public List<DetectObjectDto> match(List<ImageResult> imageResultList);

}
