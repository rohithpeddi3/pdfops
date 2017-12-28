package com.experiment.pdfutil.controller;

import com.experiment.pdfutil.service.MergeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("pdfutil/v1/")
public class MergeController {

    @Autowired
    private MergeService mergeService;

    @GetMapping("merge/{inputDirectory}/{outputDirectory}")
    private String mergeFilesInDirectory(@PathVariable(value = "inputDirectory") String inputDirectory,
                                         @PathVariable(value = "outputDirectory") String outputDirectory,
                                         @RequestParam(required = false) Map map){

        return mergeService.mergeFilesInDirectory(inputDirectory, outputDirectory);

    }


}
