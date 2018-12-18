package com.fl.sp.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fl.sp.model.AppFileInfo;
import com.swetake.util.Qrcode;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class CommonHelp {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CommonHelp.class.getName());

    /**
     * 将对象转出json字符串
     * @param obj
     * @return
     */
    public static String  MyToJSONString(Object obj){
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        String text = JSON.toJSONString(obj,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteBigDecimalAsPlain,
                SerializerFeature.WriteDateUseDateFormat);
        return text;
    }
    /**
     * 过滤html特殊字符%,<,>,\
     *
     * @param str 待过滤的字符串
     * @return
     */
    public static String htmlspecialchars(String str) {
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quot;");
        return str;
    }

    /**
     * 获取当前时间，格式为yyyy-MM-dd HH:mm:ss
     *
     * @return
     * @throws ParseException
     */
    public static Date getNowTime() throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = fmt.format(new Date());
        return fmt.parse(str);
    }

    /**
     * 获取当前时间
     *
     * @param TimeType 时间格式 例：yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static Date getNowTime(String TimeType) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = fmt.format(new Date());
        return fmt.parse(str);
    }

    /**
     * 字符串转成时间格式：格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param timestr 时间字符串
     * @return
     * @throws ParseException
     */
    public static Date getTime(String timestr) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fmt.parse(timestr);
    }

    /**
     * 字符串转成时间格式：格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param timestr  时间字符串
     * @param TimeType 时间格式
     * @return
     * @throws ParseException
     */
    public static Date getTime(String timestr, String TimeType) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat(TimeType);
        return fmt.parse(timestr);
    }

    /**
     * 获取当前时间字符串，格式为format，如：yyyy-MM-dd HH:mm:ss
     *
     * @param format
     * @return
     */
    public static String getStringDate(String format) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取文件后缀名，带点.
     *
     * @param filename
     * @return
     */
    public static String getHZMDot(String filename) {
        String hzm = filename.substring(filename.lastIndexOf("."));
        return hzm;
    }

    /**
     * 获取文件后缀名，不带点.
     *
     * @param filename
     * @return
     */
    public static String getHZM(String filename) {
        String hzm = filename.substring(filename.lastIndexOf(".") + 1);
        return hzm;
    }

    /**
     * 获取guid
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 读取某个文件夹下的第一级所有文件（夹）
     *
     * @param filepath 文件夹路径
     * @return
     */
    public static List<AppFileInfo> readfile(String filepath) {
        List<AppFileInfo> list = new ArrayList<AppFileInfo>();
        try {
            File file = new File(filepath);
            if (!file.isDirectory()) {
                list.add(new AppFileInfo("0", file.getName(), EncodeUtils.urlEncode(file.getPath(), "utf-8"),
                        file.getAbsolutePath()));
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        list.add(new AppFileInfo("0", readfile.getName(),
                                EncodeUtils.urlEncode(readfile.getPath(), "utf-8"), readfile.getAbsolutePath()));
                    } else if (readfile.isDirectory()) {
                        list.add(new AppFileInfo("1", readfile.getName(),
                                EncodeUtils.urlEncode(readfile.getPath(), "utf-8"), readfile.getAbsolutePath()));
                        // readfile(filepath + "\\" + filelist[i]);
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return list;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    public static String GetIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 根据ip获取ip归属地
     *
     * @param ip
     * @return
     * @throws Exception
     */
    public static JSONObject GetIPPalce(String ip) throws Exception {
        String resp = CommonHelp.GetFromURL("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip, "GET", "", "");
        JSONObject obj = JSONObject.parseObject(resp);
        return obj;
    }

    /**
     * 向URLSTR 发送请求
     *
     * @param URLSTR
     * @param Type     请求类似，GET/POST
     * @param PostData 发送的数据
     * @return 返回字符串
     * @throws Exception
     */
    public static String GetFromURL(String URLSTR, String Type, String PostData, String contentType) throws Exception {
        String UTF8 = "UTF-8";
        URL httpUrl = new URL(URLSTR);
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
        httpURLConnection.setDoOutput(true);
        //设置连接请求方式
        httpURLConnection.setRequestMethod(Type);
        //设置连接超时时间
        httpURLConnection.setConnectTimeout(10 * 1000);
        //设置读取超时时间
        httpURLConnection.setReadTimeout(10 * 1000);
        if (!contentType.isEmpty()) {
            httpURLConnection.setRequestProperty("Content-Type", contentType);
        }
        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        if (PostData != null && PostData != "") {
            outputStream.write(PostData.getBytes(UTF8));
        }
        //获取内容
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        final StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        String resp = stringBuffer.toString();
        if (stringBuffer != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resp;
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key : data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        } catch (Exception ex) {
        }
        return output;
    }

    /**
     * QRCode 方式生成二维码文件
     *
     * @param content  二维码内容
     * @param imgPath  二维码生成路径
     * @param version  二维码版本
     * @param logoPath 是否生成Logo图片    为NULL不生成
     */
    public static void CreateQRCodeFile(String content, String imgPath, int version, String logoPath) {
        BufferedImage bufImg = getEWM(content, version, logoPath);
        if (bufImg != null) {
            try {
                //创建二维码文件
                File imgFile = new File(imgPath);
                if (!imgFile.exists())
                    imgFile.createNewFile();
                //根据生成图片获取图片
                String imgType = imgPath.substring(imgPath.lastIndexOf(".") + 1, imgPath.length());
                // 生成二维码QRCode图片，把bufImg写入imgFile
                ImageIO.write(bufImg, imgType, imgFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * QRCode 方式生成二维码base64字符串
     *
     * @param content  二维码内容
     * @param hzm      二维码图片后缀名
     * @param version  二维码版本
     * @param logoPath 是否生成Logo图片    为NULL不生成
     */
    public static String CreateQRCodeBase64Str(String content, String hzm, int version, String logoPath) {
        BufferedImage bufImg = getEWM(content, version, logoPath);
        return BufferedImageToBase64(bufImg, hzm);
    }

    public static BufferedImage getEWM(String content, int version, String logoPath) {
        try {
            Qrcode qr = new Qrcode();
            //设置二维码排错率，可选L(7%) M(15%) Q(25%) H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
            qr.setQrcodeErrorCorrect('M');
            //N代表数字,A代表字符a-Z,B代表其他字符
            qr.setQrcodeEncodeMode('B');
            //版本1为21*21矩阵，版本每增1，二维码的两个边长都增4；所以版本7为45*45的矩阵；最高版本为是40，是177*177的矩阵
            qr.setQrcodeVersion(version);
            //根据版本计算尺寸
            int imgSize = 67 + 12 * (version - 1);
            byte[] contentBytes = content.getBytes("gb2312");
            //设置二维码宽高
            BufferedImage bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
            Graphics2D gs = bufImg.createGraphics();
            gs.setBackground(Color.WHITE);
            //设置二维码宽高
            gs.clearRect(0, 0, imgSize, imgSize);
            // 设定图像颜色 > BLACK
            gs.setColor(Color.BLACK);
            // 设置偏移量 不设置可能导致解析出错
            int pixoff = 2;
            // 输出内容 > 二维码
            if (contentBytes.length > 0) {//&& contentBytes.length < 130
                boolean[][] codeOut = qr.calQrcode(contentBytes);
                for (int i = 0; i < codeOut.length; i++) {
                    for (int j = 0; j < codeOut.length; j++) {
                        if (codeOut[j][i]) {
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                        }
                    }
                }
            } else {
                System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,130 ]. ");
            }
            /* 判断是否需要添加logo图片 */
            if (!TypeUtils.isEmpty(logoPath)) {
                File icon = new File(logoPath);
                if (icon.exists()) {
                    int width_4 = imgSize / 4;
                    int width_8 = width_4 / 2;
                    int height_4 = imgSize / 4;
                    int height_8 = height_4 / 2;
                    Image img = ImageIO.read(icon);
                    gs.drawImage(img, width_4 + width_8, height_4 + height_8, width_4, height_4, null);
                    gs.dispose();
                    bufImg.flush();
                } else {
                    System.out.println("Error: logo图片不存在！");
                }
            }
            gs.dispose();
            bufImg.flush();
            return bufImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过get请求得到读取器响应数据的数据流
     *
     * @param url
     * @return
     */
    public static InputStream getUrlFile(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url)
                    .openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                return inputStream;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据图片url获取图片base64字符串
     * @param url
     * @param hzm
     * @return
     */
    public static String UrlToBase64(String url, String hzm) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(getUrlFile(url));
        } catch (IOException e) {
            return "";
        }
        String b64 = CommonHelp.BufferedImageToBase64(bi, hzm);
        return b64;
    }

    /**
     * 获得指定文件的byte数组
     *
     * @param filePath 文件路径
     * @return
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * BufferedImage转成Base64字符串
     *
     * @param bufImg
     * @return
     */
    public static String BufferedImageToBase64(BufferedImage bufImg, String hzm) {
        String rst = "";
        if (bufImg != null) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufImg, hzm, outputStream);
                BASE64Encoder encoder = new BASE64Encoder();
                rst = encoder.encode(outputStream.toByteArray());
                rst = "data:image/" + hzm.toLowerCase() + ";base64," + rst;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rst;
    }

    /**
     * MultipartFile 转 BufferedImage
     *
     * @param file 上传的图片
     * @return 错误返回null
     */
    public static BufferedImage MultipartFileToBufferedImage(MultipartFile file) {
        BufferedImage srcImage = null;
        try {
            FileInputStream in = (FileInputStream) file.getInputStream();
            srcImage = ImageIO.read(in);
        } catch (IOException e) {
            System.out.println("读取图片文件出错！" + e.getMessage());
        }
        return srcImage;
    }

    /**
     * 文件路径转BufferedImage
     *
     * @param src 图片路径
     * @return 错误返回null
     */
    public static BufferedImage SrcToBufferedImage(String src) {
        if (TypeUtils.isImage(src)) {
            BufferedImage result = null;
            try {
                File srcfile = new File(src);
                if (!srcfile.exists()) {
                    System.out.println("文件不存在");
                    return null;
                }
                BufferedImage im = ImageIO.read(srcfile);
                /* 原始图像的宽度和高度 */
                int width = im.getWidth();
                int height = im.getHeight();
                //压缩计算
                float resizeTimes = 0.3f;  /*这个参数是要转化成的倍数,如果是1就是转化成1倍*/
                /* 调整后的图片的宽度和高度 */
                int toWidth = (int) (width * resizeTimes);
                int toHeight = (int) (height * resizeTimes);
                /* 新生成结果图片 */
                result = new BufferedImage(toWidth, toHeight,
                        BufferedImage.TYPE_INT_RGB);
                result.getGraphics().drawImage(
                        im.getScaledInstance(toWidth, toHeight,
                                Image.SCALE_SMOOTH), 0, 0, null);
            } catch (Exception e) {
                System.out.println("创建缩略图发生异常" + e.getMessage());
            }
            return result;
        } else {
            return null;
        }
    }

}
