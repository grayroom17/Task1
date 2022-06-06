package org.Task1;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TxtEditor {
        private static Path source = null;
    private static Path result = null;
//    private static Path source = Paths.get("D:\\test\\1");
//    private static Path result = Paths.get("D:\\test\\result.txt");

    public static void main(String[] args) {

        List<Path> txtFileList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            source = setSourceDirectory(reader);
            result = setResultFile(reader);

            FileSearcher fileSearcher = new FileSearcher();
            Files.walkFileTree(source, fileSearcher);
            txtFileList.addAll(fileSearcher.getTxtFileList());
            Collections.sort(txtFileList,new FileNameComparator());

            System.out.println("");
            System.out.println("Все txt файлы:");
            for (Path p : txtFileList) {
                System.out.println(p.getFileName().toString());
            }
            System.out.println("");

            writeToTxtFile(txtFileList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Конец!");
    }


    public static Path setSourceDirectory(BufferedReader reader) throws IOException {
        String sourceDirectoryPath = null;
        Path source = null;
        while (true) {
            System.out.println("Укажите полный путь до папки с файлами:");
            sourceDirectoryPath = reader.readLine();
            try {
                source = Paths.get(sourceDirectoryPath);
                if (!source.toFile().isDirectory())
                    throw new FileNotFoundException();
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("Ресурс не существует по указанному адресу. Попробуйте еще раз.");
                continue;
            }
            break;
        }

        return source;
    }

    public static Path setResultFile(BufferedReader reader) throws IOException {
        Path resultFile = null;
        while (true) {
            System.out.println("Укажите полный путь (c расширением txt) где необходимо создать итоговый \".txt\" файл.");
            String resultFilePath = reader.readLine();
            if (!resultFilePath.endsWith(".txt")) {
                System.out.println("Указан неверный путь.\n" +
                        "Убедитесь, что указали имя файла и расширение \".txt\"\n" +
                        "Попробуйте еще раз.");
                continue;
            }
            resultFile = Paths.get(resultFilePath);
            break;
        }
        return resultFile;
    }

    public static void writeToTxtFile(List<Path> txtFiles) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        for (Path file : txtFiles) {
            try (FileInputStream fileInputStream = new FileInputStream(file.toFile())) {
                while (fileInputStream.available() > 0) {
                    byteArrayOutputStream.write(fileInputStream.read());
                }
                byteArrayOutputStream.write("\r\n".getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }

        try (FileOutputStream writer = new FileOutputStream(result.toFile())) {
            byteArrayOutputStream.writeTo(writer);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
