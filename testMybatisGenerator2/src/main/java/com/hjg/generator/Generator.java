package com.hjg.generator;
/**
 * CopyRight (R) BeiJing MAXIT Co.Ltd
 * All right reserved
 */


import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Generator {
	
	private static final String FILE = "t_user";
	private static final Log log = LogFactory.getLog(Generator.class);
	private static final File[] FILES = new File("dev").listFiles(new FileFilter() {
		public boolean accept(File file) {
			return file.isFile() && file.getName().equalsIgnoreCase("mybator-" + FILE + ".xml");
		}
	});

	public static void main(String[] args) throws Exception {
		log.info("starting ... ");
		for (File file : FILES) {
			log.info(file.getName() + "开始处理");
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(file);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
			log.info(file.getName() + "处理结束");
		}
		log.info("end ... ");
	}
}
