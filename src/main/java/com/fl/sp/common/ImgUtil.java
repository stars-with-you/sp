package com.fl.sp.common;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImgUtil {
    //int baseSize = 1280;//1280,1080,400
    /**
     * 当图片大于500，进行质量压缩
     * 压缩质量到0.7
     */
    public static int fileSize = 500 * 1024;//500k
    public static double quality = 0.7;

    /**
     * @param baseSize，图片压缩尺寸标准，400，1080，1280
     * @param picfile，图片源
     * @param toPic，压缩图片保存路径
     */
    public boolean ThumbnailsImgMultipartFile(int baseSize, MultipartFile picfile, String toPic) {
        try {
            FileInputStream in = (FileInputStream) picfile.getInputStream();
            return ThumbnailsImg(baseSize, in, toPic, picfile.getSize());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param baseSize，图片压缩尺寸标准，400，1080，1280
     * @param file，图片源
     * @param toPic，压缩图片保存路径
     */
    public boolean ThumbnailsImgFile(int baseSize, File file, String toPic) {
        try {
            FileInputStream in = new FileInputStream(file);
            return ThumbnailsImg(baseSize, in, toPic, file.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param baseSize，图片压缩尺寸标准，400，1080，1280
     * @param fromPic，图片源
     * @param toPic，压缩图片保存路径
     */
    public boolean ThumbnailsImgURL(int baseSize, String fromPic, String toPic) {
        File file = new File(fromPic);
        return ThumbnailsImgFile(baseSize, file, toPic);
    }

    /**
     * @param baseSize，图片压缩尺寸标准，400，1080，1280
     * @param in，图片源
     * @param toPic，压缩图片保存路径
     */
    public boolean ThumbnailsImg(int baseSize, FileInputStream in, String toPic, long length) {
        try {
            BufferedImage srcImage = ImageIO.read(in);
            Thumbnails.Builder<BufferedImage> fBuilder = Thumbnails.of(srcImage);
            BufferedImage bi = Thumbnails.of(srcImage).scale(1f).asBufferedImage();
            int height = bi.getHeight();
            int width = bi.getWidth();
            //(1)图片宽高均≤baseSizepx时，图片尺寸保持不变，（这里增加一个判断，如果图片大小＞500k）图片压缩quality=70%
            if (width <= baseSize && height <= baseSize) {
                if (length > fileSize) {
                    fBuilder.scale(1f).outputQuality(quality).toFile(toPic);
                } else {
                    fBuilder.scale(1f).toFile(toPic);
                }
            } else {
                //(2)宽或高均＞baseSizepx
                if (width > baseSize && height > baseSize) {
                    //2.1:宽高比大于2  既W > 2H,取 H = 1280, W等比缩放;
                    if (width > 2 * height) {
                        fBuilder.height(baseSize).toFile(toPic);
                    }
                    //2.2:高宽比大于2 既W < 0.5H, 取 W = 1280, H等比缩放;
                    if (width < 0.5 * height) {
                        fBuilder.width(baseSize).toFile(toPic);
                    }
                    //2.3:宽高比介于 0.5到2之间,取宽或者高 为1280, 另一边等比缩放;
                    if ((0.5 * height) <= width && width <= (2 * height)) {
                        fBuilder.width(baseSize).toFile(toPic);
                    }
                } else {
                    //(3)宽或者高大于1280
                    //(3.1)宽高比大与2(宽图) 或者小于0.5(长图)  大小不变
                    if (width > (2 * height) || width < (0.5 * height)) {
                        fBuilder.scale(1f).toFile(toPic);
                    } else {
                        //(3.2)宽大于高, 取较大值W = 1280, H等比压缩;
                        if (width > height) {
                            fBuilder.width(baseSize).toFile(toPic);
                        }
                        //(3.3)高大于宽, 去较大值H = 1280. W等比压缩;
                        else {
                            fBuilder.height(baseSize).toFile(toPic);
                        }
                    }
                }
            }
            File ftopic = new File(toPic);
            if (ftopic.length() > fileSize) {
                fBuilder.outputQuality(quality).toFile(toPic);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
