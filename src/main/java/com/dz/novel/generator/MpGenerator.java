package com.dz.novel.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Deng Zhou
 * @Description:
 * @Date: 23:25 2020/1/13
 */
public class MpGenerator {

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Velocity
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("Deng Zhou");
        gc.setOutputDir("D:\\JAVA\\ideaspace\\novel\\src\\main\\java");
        gc.setFileOverride(true);// 是否覆盖同名文件，默认是false
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setUrl("jdbc:mysql://localhost:3306/novel?useUnicode=true&serverTimezone=UTC&characterEncoding=utf8");
        mpg.setDataSource(dsc);


        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.dz.novel");
        // pc.setModuleName("test");
        mpg.setPackageInfo(pc);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //设置命名格式
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude(new String[]{"spider_error"});
        //实体是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(true);
        //生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        //设置自定义继承的Entity类全称，带包名
        //strategy.setSuperEntityClass("com.jiangfeixiang.mpdemo.BaseEntity");
        //设置自定义继承的Controller类全称，带包名
        //strategy.setSuperControllerClass("com.jiangfeixiang.mpdemo.BaseController");
        //设置自定义基础的Entity类，公共字段
        //strategy.setSuperEntityColumns("id");
        //驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        //表名前缀
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);

        //mapper目录修改
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return "D:\\JAVA\\ideaspace\\novel\\src\\main\\resources\\mapper\\" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        //mpg.setCfg(cfg);

        TemplateConfig tc = new TemplateConfig();
        tc.setController(null);
        tc.setService(null);
        tc.setServiceImpl(null);
        tc.setMapper(null);
        tc.setXml(null);
        mpg.setTemplate(tc);
        // 执行生成
        mpg.execute();
    }

}
