package imagebrowser.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import imagebrowser.Model.Image;

public class FileImageListLoader implements ImageListLoader {

    private String path;

    public FileImageListLoader(String path) {
        this.path = path;
    }

    @Override
    public List<Image> load() {
        return linkImages(loadImages());
    }

    private List<Image> loadImages() {
        List<Image> list = new ArrayList<>();
        for (String file : new File(path).list())
            list.add(new ProxyImage(new FileImageLoader(path + "/" + file)));
        return list;
    }

    private List<Image> linkImages(List<Image> images) {
        for (int i = 0; i < images.size(); i++) {
            Image image = images.get(i);
            image.setNext(images.get((i + 1) % images.size()));
            image.setPrev(images.get((i + images.size() - 1) % images.size()));
        }
        return images;
    }
}
