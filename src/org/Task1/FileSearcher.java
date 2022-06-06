package org.Task1;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class FileSearcher extends SimpleFileVisitor<Path> {

    private ArrayList<Path> txtFileList = new ArrayList<>();

    public ArrayList<Path> getTxtFileList() {
        return txtFileList;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().endsWith(".txt")) {
            txtFileList.add(file);
        }
        return FileVisitResult.CONTINUE;
    }
}
