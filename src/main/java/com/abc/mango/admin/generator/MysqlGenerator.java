package com.abc.mango.admin.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MysqlGenerator {

	public final static String AUTHOR = "wanglei";

	public final static String OUTPUT_DIR = "/src/main/java";

	public final static String URL = "jdbc:mysql://localhost:3306/mango?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8";
	public final static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	public final static String USERNAME = "root";
	public final static String PASSWORD = "123456";

	public final static String PACKAGE_CONFIG_PARENT = "com.abc.mango.admin";

	public final static String MAPPER_XML_FTL = "/templates/mapper.xml.ftl";
	public final static String MAPPER_XML_OUTPUT_DIR = "/src/main/resources/mapper/";

	public final static String BASE_ENTITY = "com.abc.mango.admin.common.BaseEntity";
	public final static String BASE_CONTROLLER = "com.abc.mango.admin.common.BaseController";
	
	public final static String MAPPER_TEST_JAVA_FTL = "/templates/mapperTest.java.ftl";
	public final static String MAPPER_TEST_JAVA_OUTPUT_DIR = "/src/test/java/com/abc/mango/admin/";

	// scanner("模块名")
	// scanner("表名")

	/**
	 * <p>
	 * 读取控制台内容
	 * </p>
	 */
	public static String scanner(String tip) {
		Scanner scanner = new Scanner(System.in);
		StringBuilder help = new StringBuilder();
		help.append("请输入" + tip + "：");
		System.out.println(help.toString());
		if (scanner.hasNext()) {
			String ipt = scanner.next();
			if (StringUtils.isNotEmpty(ipt)) {
				return ipt;
			}
		}
		throw new MybatisPlusException("请输入正确的" + tip + "！");
	}

	/**
	 * RUN THIS
	 */
	public static void main(String[] args) {
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		String projectPath = System.getProperty("user.dir");
		gc.setOutputDir(projectPath + OUTPUT_DIR);
		gc.setAuthor(AUTHOR);
		gc.setOpen(false);
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setUrl(URL);
		// dsc.setSchemaName("public");
		dsc.setDriverName(DRIVER_NAME);
		dsc.setUsername(USERNAME);
		dsc.setPassword(PASSWORD);
		mpg.setDataSource(dsc);

		// 包配置
		PackageConfig pc = new PackageConfig();
		// pc.setModuleName(scanner("模块名"));
		// pc.setModuleName(null);
		pc.setParent(PACKAGE_CONFIG_PARENT);
		mpg.setPackageInfo(pc);

		// 自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};
		List<FileOutConfig> focList = new ArrayList<>();
		focList.add(new FileOutConfig(MAPPER_XML_FTL) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输入文件名称
//				return projectPath + MAPPER_XML_OUTPUT_DIR + pc.getModuleName()
//						+ "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
				return projectPath + MAPPER_XML_OUTPUT_DIR + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
			}
		});
		
		//测试类
		focList.add(new FileOutConfig(MAPPER_TEST_JAVA_FTL) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return projectPath + MAPPER_TEST_JAVA_OUTPUT_DIR + "mapper" + "/" + tableInfo.getEntityName() + "Test"
						+ StringPool.DOT_JAVA;
			}
		});
		
		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);
		mpg.setTemplate(new TemplateConfig().setXml(null));

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		// strategy.setSuperEntityClass(BASE_ENTITY);
		strategy.setEntityLombokModel(true);
		// strategy.setSuperControllerClass(BASE_CONTROLLER);
		strategy.setInclude(scanner("表名"));
		//strategy.setSuperEntityColumns("id");
		strategy.setControllerMappingHyphenStyle(true);
		strategy.setTablePrefix(pc.getModuleName() + "_");
		mpg.setStrategy(strategy);
		// 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		mpg.execute();
	}
}
