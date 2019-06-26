package com.yuntian.poeticlife.service;
import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.entity.Dict;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.model.dto.DictDTO;

/**
 * Created by CodeGenerator on 2019/06/17.
 */
public interface DictService extends Service<DictDTO, Dict> {


    PageInfoVo<Dict> queryListByPage(DictDTO dto);


    void saveByDTO(Dict vo);


    void deleteByDTO(Dict vo);


    void updateByDTO(Dict vo);


}
