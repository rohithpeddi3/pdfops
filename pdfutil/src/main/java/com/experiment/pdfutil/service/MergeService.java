package com.experiment.pdfutil.service;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class MergeService {


//    Take the input path then scan through each file
//    In each path if the file is a directory get inside it and merge the available files and write it to the same directory
//    If it contains just files then merge the available pdf files


    @FunctionalInterface
    public interface ThrowingConsumer<T, E extends Exception> {
        void accept(T t) throws E;
    }

    static <T> Consumer<T> throwingConsumerWrapper(ThrowingConsumer<T, Exception> throwingConsumer) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public void mergeFiles(List<File> fileList, String inputPath) throws Exception{

        String outputPath = "/" + inputPath + "/merged.pdf";

        //Instantiating PDFMergerUtility class
        PDFMergerUtility PDFmerger = new PDFMergerUtility();

        Collections.sort(fileList, new SortFileByName());

        fileList.forEach(throwingConsumerWrapper(PDFmerger::addSource));

        //Setting the destination file
        PDFmerger.setDestinationFileName(outputPath);

        //Merging the two documents
        PDFmerger.mergeDocuments();

        System.out.println("Documents merged in the path "+ inputPath);

    }

    public String getModifiedPath(String path) {

        path = path.replace('-','/');

        path = "/" + path;

        return path;

    }

    public void mergeFilesInDirectory(String inputPath) throws Exception{

        inputPath = getModifiedPath(inputPath);

        File inputDirectory = new File(inputPath);

        List<File> filesInDirectory = new ArrayList<>();

        for (File file : inputDirectory.listFiles()) {

            if (file.isDirectory()) {
                mergeFilesInDirectory(file.getPath());
            } else {
                filesInDirectory.add(file);
            }

        }

        mergeFiles(filesInDirectory, inputPath);

    }

    class SortFileByName implements Comparator<File> {

        @Override
        public int compare(File f1, File f2) {

            String f1Name = f1.getName();
            String f2Name = f2.getName();

            f1Name = f1Name.replace(".pdf", "");
            f2Name = f2Name.replace(".pdf", "");

            Long f1Number = Long.parseLong(f1Name);
            Long f2Number = Long.parseLong(f2Name);

            if (f1Number < f2Number) {
                return -1;
            } else if (f1Number > f2Number) {
                return 1;
            } else {
                return 0;
            }

        }

    }


}
