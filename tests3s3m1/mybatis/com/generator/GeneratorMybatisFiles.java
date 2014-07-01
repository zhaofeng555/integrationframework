package com.generator;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GeneratorMybatisFiles {

	private static final Log log = LogFactory.getLog(GeneratorMybatisFiles.class);

	static {
		PropertyConfigurator.configure("src/main/resources/log4j.properties");
	}
	
	//文件名字
	private static final String FILE = "t_user";
	private static final File[] FILES = new File("dev")
			.listFiles(new FileFilter() {
				public boolean accept(File file) {
					Boolean isFile = file.isFile();
					String filename = file.getName();
					return isFile && filename.equalsIgnoreCase("mybator-" + FILE	+ ".xml");
				}
			});

	// private static final String[] EXCLUDE_FILES = {};
	// private static final File[] FILES_EX = new File("dev").listFiles(new
	// FileFilter() {
	// public boolean accept(File file) {
	// Boolean isFile = file.isFile();
	// String name = file.getName();
	// for (String f : EXCLUDE_FILES)
	// if (name.indexOf(f) != -1)
	// return false;
	// return isFile&& name.endsWith(".xml");
	// }
	// });

	@Test
	public void generatorFiles() throws Exception {

		for (File file : FILES) {
			log.debug(file.getName() + "开始处理");
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(file);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
			log.debug(file.getName() + "处理结束");
		}
	}
}
