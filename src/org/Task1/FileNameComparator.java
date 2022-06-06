package org.Task1;

import java.nio.file.Path;
import java.util.Comparator;

public class FileNameComparator implements Comparator<Path> {
    @Override
    public int compare(Path o1, Path o2) {
        return o1.getFileName().compareTo(o2.getFileName());
    }
}
