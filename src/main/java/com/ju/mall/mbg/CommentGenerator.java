package com.ju.mall.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 * @projectName: mall
 * @package: com.ju.mall.mbg
 * @className: CommentGenerator
 * @author: Eric
 * @description: TODO 逆向工程自定义的代码注释
 * @date: 2023/5/28 11:38
 * @version: 1.0
 */
public class CommentGenerator extends DefaultCommentGenerator {
    private boolean addRemarkComments = false;

    private static final String EXAMPLE_SUFFIX="Example";
    private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME="io.swagger.annotations.ApiModelProperty";
    private static final String API_MAPPER_MAPPER_CLASS_NAME="org.apache.ibatis.annotations.Mapper";

    /**
     * @param properties:
     * @return void
     * @author jcm
     * @description TODO 设置用户配置的参数
     * @date 2023/5/28 11:41
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        //父类的变量要先初始化
        super.addConfigurationProperties(properties);
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    }

    /**
     * @param field:
     * @param introspectedTable:
     * @return void
     * @author jcm
     * @description TODO 重写字段的注释
     * @date 2023/5/28 11:54
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        //获取数据库的备注信息
        String remarks = introspectedColumn.getRemarks();
        super.addFieldComment(field, introspectedTable);
        //根据参数和备注信息判断是否为字段横撑备注信息
        if(addRemarkComments && StringUtility.stringHasValue(remarks)){
//            this.addFieldJavaDoc(field,remarks);
            //数据库中特殊的字段需要转义
            if(remarks.contains("\"")){
                remarks.replaceAll("\"","'");
            }
            //增加swagger注释
            field.addJavaDocLine("@ApiModelProperty(value = \""+remarks+"\")");
        }
    }

    /**
     * @param field:
     * @param remarks:
     * @return void
     * @author jcm
     * @description TODO 字段的备注信息
     * @date 2023/5/28 11:46
     */
    public void addFieldJavaDoc(Field field, String remarks){
        //字段增加文档注释功能
        field.addJavaDocLine("/**");
        //根据数据库字段获取备注信息
        //System.getProperty("line.separator") 获取当前操作系统的换行符
        String[] remarksLines = remarks.split(System.getProperty("line.separator"));
        for(String remark : remarksLines){
            field.addJavaDocLine("*" + remark);
        }
        //不生成doc标签
        super.addJavadocTag(field,false);
        field.addJavaDocLine("*/");
    }

    /**
     * @param compilationUnit:
     * @return void
     * @author jcm
     * @description TODO 给类,接口增加导入
     * @date 2023/5/28 20:40
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
        //只给model类import 注解类
        if(!compilationUnit.isJavaInterface() && !compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)){
            compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_FULL_CLASS_NAME));
        }
        //给mapper层导入@mapper包,增加@mapper注解
        if(compilationUnit.isJavaInterface()){
            compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MAPPER_MAPPER_CLASS_NAME));
            ((Interface)compilationUnit).addAnnotation("@Mapper");
        }
    }
}
