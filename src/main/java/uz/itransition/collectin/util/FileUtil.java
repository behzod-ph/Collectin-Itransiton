package uz.itransition.collectin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static Resource load(String filePath)
    {
        final Path path = Paths.get(filePath);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return resource;
    }

    public static void cleanUp(Resource resource){
            try {
                Files.delete(resource.getFile().toPath());
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
    }

}
