package com.experiment.pdfutil.service;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MergeService {

    public String mergeFilesInDirectory(String inputDirectory, String outputDirectory) {

        try {

            inputDirectory = inputDirectory.replace('-', '/');
            outputDirectory = outputDirectory.replace('-', '/');

            inputDirectory = "/" + inputDirectory;
            outputDirectory = "/" + outputDirectory + ".pdf";

            List<File> filesInDirectory =
                    Files.walk(Paths.get(inputDirectory))
                            .filter(Files::isRegularFile)
                            .map(Path::toFile)
                            .sorted(Comparator.comparing(File::lastModified))
                            .collect(Collectors.toList());

            //Instantiating PDFMergerUtility class
            PDFMergerUtility PDFmerger = new PDFMergerUtility();

            for (File file : filesInDirectory) {
                PDFmerger.addSource(file);
            }

            //Setting the destination file
            PDFmerger.setDestinationFileName(outputDirectory);

            //Merging the two documents
            PDFmerger.mergeDocuments();

            System.out.println("Documents merged");

            return "OK";

        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED!";
        }

    }

}
