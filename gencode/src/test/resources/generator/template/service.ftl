package ${basePackage}.service;
import ${basePackage}.core.Service;
import ${basePackage}.model.vo.PageInfoVo;
import ${basePackage}.model.dto.${modelNameUpperCamel}DTO;
import ${basePackage}.model.vo.${modelNameUpperCamel}VO;

/**
 * Created by ${author} on ${date}.
 */
public interface ${modelNameUpperCamel}Service extends Service<${modelNameUpperCamel}DTO,${modelNameUpperCamel}VO> {


    PageInfoVo<${modelNameUpperCamel}VO> queryListByPage(${modelNameUpperCamel}DTO dto);


    void saveByDTO(${modelNameUpperCamel}DTO dto);


    void deleteByDTO(${modelNameUpperCamel}DTO dto);


    void updateByDTO(${modelNameUpperCamel}DTO dto);


}
