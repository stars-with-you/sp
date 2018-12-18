package com.fl.sp.common;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

public class WordUtil {
    private Configuration configuration = null;

    public WordUtil() {
        configuration = Configuration.getDefaultConfiguration();
        configuration.setDefaultEncoding("utf-8");
    }

    public void createDoc(Map<String, Object> dataMap, String fileName) throws UnsupportedEncodingException {
        configuration.setClassForTemplateLoading(this.getClass(), "/");
        Template doct = null;
        try {
            doct = configuration.getTemplate("test.ftl");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        File outfile = new File(fileName);
        Writer out = null;
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outfile);
            OutputStreamWriter oWriter = new OutputStreamWriter(outputStream, "UTF-8");
            out = new BufferedWriter(oWriter);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            doct.process(dataMap, out);
            out.close();
            outputStream.close();
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createDoc(Map<String, Object> dataMap, OutputStream outfil) throws UnsupportedEncodingException {
        configuration.setClassForTemplateLoading(this.getClass(), "/");
        Template doct = null;
        try {
            doct = configuration.getTemplate("mb.ftl");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//		File outfile = new File(fileName);
        Writer out = null;
        //  outputStream = new FileOutputStream(outfile);
        OutputStreamWriter oWriter = new OutputStreamWriter(outfil, "UTF-8");
        out = new BufferedWriter(oWriter);
        try {
            doct.process(dataMap, out);
            out.close();
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
