package io.weba.api.tests.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourcesContentLoader {
    public static String load(String path) throws IOException {
        ClassLoader classLoader = ResourcesContentLoader.class.getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());

        return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    }
}
