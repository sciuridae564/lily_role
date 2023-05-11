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
                try {
                    URL imgaeSrcUrl = new URL(web+weblink);
                    File file = new File(file_dir+weblink);
                    File dir = new File(file.getParent());
                    if (!dir.exists()){
                        dir.mkdirs();
                    }
                    if(!file.exists()){
                        FileUtils.copyURLToFile(imgaeSrcUrl, file);
                    }
                    return;
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            File dir = new File(file_dir+weblink);
            if (!dir.exists()){
                dir.mkdirs();
            }
            try {
                Document lily_document = Jsoup.parse(new URL(web+weblink), 10000);
                Elements box = lily_document.getElementsByClass("box");
                if (box.size() == 0)
                    return;
                Elements a = box.get(0).getElementsByTag("a");
                for (Element element : a) {
                    URL imgaeSrcUrl = new URL(web+weblink+element.attr("href"));
                    File file = new File(file_dir+weblink+element.attr("href"));
                    if(file.exists())//跳过已有的
                        continue;
                    FileUtils.copyURLToFile(imgaeSrcUrl, file);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
