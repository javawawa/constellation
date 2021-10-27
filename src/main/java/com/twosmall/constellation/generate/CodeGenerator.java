package com.twosmall.constellation.generate;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


public class CodeGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://www.qbjavawa.top:3306/solo?useSSL=false";
        String username = "root";
        String password = "mysql";
        String[] tables = new String[]{"gerp_menu"};
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        String projectDir = "/";
        String packageDir = "com.twosmall.constellation";
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + projectDir + "/src/main/java");
        gc.setAuthor("enzo");
        gc.setOpen(false);
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setServiceImplName("%sService");
        gc.setEntityName("%sDao");
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert());
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageDir);
        pc.setEntity("entity.dao");
        pc.setXml("xmls");
        mpg.setPackageInfo(pc);

        //模板配置
        TemplateConfig tc = new TemplateConfig();
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.fs.framework.common.domain.dao.BaseDao");
        strategy.setEntityLombokModel(true);
//        strategy.setRestControllerStyle(true);
//        strategy.setSuperControllerClass("com.linjiao.platform.common.web.controller.BaseRequestController");
        strategy.setInclude(tables);
        strategy.setSuperEntityColumns("id");
//        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("gerp_");
        //生成实体时，生成字段注解
        strategy.entityTableFieldAnnotationEnable(true);
        //逻辑删除字段
        strategy.setLogicDeleteFieldName("deleted");
        mpg.setStrategy(strategy);
        mpg.execute();

    }
}
