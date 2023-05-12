package cn.sciuridae564.util;

import cn.sciuridae564.frame.main;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @created 2023/5/10 23:33
 */
public class getUrlImage {
    public static String file_dir = "./al_icon/";
    public static String file_out = "./al_icon_out/";
    public static String web = "https://allb.tqlwsl.moe/Image/CharacterJob/";


    public static void get(main role){
        ExecutorService newCachedThreadPool = Executors.newFixedThreadPool(4);
        try {
            Document lily_document = Jsoup.parse(new URL(web), 10000);
            Elements pre = lily_document.getElementsByTag("img");
            for (Element element : pre) {
                thre t = new thre();
                t.weblink = element.attr("alt");
                newCachedThreadPool.submit(t);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newCachedThreadPool.shutdown();

        Thread thread = new Thread(() -> {
            while (true){
                if(newCachedThreadPool.isTerminated()){
                    role.ready_url = true;
                    role.setWebLabel("爬完了");
                    role.refrashWebImage();
                    break;
                }
            }
        });
        thread.start();
    }


    static class thre implements Runnable{
        public String weblink;
        @Override
        public void run() {
            if (weblink.contains(".png")){
                File file = new File(file_dir+weblink);
                File dir = new File(file.getParent());
                if (!dir.exists()){
                    dir.mkdirs();
                }
                while (true){
                    try {
                        URL imgaeSrcUrl = new URL(web+weblink);
                        if(!file.exists()){
                            FileUtils.copyURLToFile(imgaeSrcUrl, file);
                        }
                        return;
                    } catch (IOException ignored) {}
                }
            }
        }
    }
}
