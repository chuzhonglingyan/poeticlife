package ${basePackage}.service;
import ${basePackage}.core.Service;
import ${basePackage}.model.vo.PageInfoVo;
import ${basePackage}.model.dto.${modelNameUpperCamel}DTO;
import ${basePackage}.model.entity.${modelNameUpperCamel};

/**
 * Created by ${author} on ${date}.
 */
public interface ${modelNameUpperCamel}Service extends Service<${modelNameUpperCamel}DTO,${modelNameUpperCamel}> {


    PageInfoVo<${modelNameUpperCamel}> queryListByPage(${modelNameUpperCamel}DTO dto);


    void saveByDTO(${modelNameUpperCamel} vo);


    void deleteByDTO(${modelNameUpperCamel} vo);


    void updateByDTO(${modelNameUpperCamel} vo);


}
