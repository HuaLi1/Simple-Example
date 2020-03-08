package com;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List<String> war=new ArrayList<>();
        Boolean ovr=true;
        File file=new File("E:\\IdeaProject\\Simple-Example\\src\\main\\resources\\generatorConfig.xml");
        ConfigurationParser configurationParser=new ConfigurationParser(war);
        try {
            Configuration config=configurationParser.parseConfiguration(file);
            DefaultShellCallback back=new DefaultShellCallback(ovr);
            MyBatisGenerator myBatisGenerator=new MyBatisGenerator(config, back, war);
            myBatisGenerator.generate(null);
            for(String str:war) {
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println( "Hello World!" );
    }
}
