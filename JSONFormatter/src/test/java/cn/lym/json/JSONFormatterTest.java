package cn.lym.json;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class JSONFormatterTest {

	@Test
	public void testFormat() throws Exception {
		String pathname = JSONFormatterTest.class.getClassLoader()
				.getResource("test.json").getFile();
		File file = new File(pathname);
		String source = FileUtils.readFileToString(file);

		System.out.println(JSONFormatter.format(source));
	}

}
