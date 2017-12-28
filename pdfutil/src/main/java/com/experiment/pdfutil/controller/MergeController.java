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

    @GetMapping("merge/{inputDirectory}")
    private String mergeFilesInDirectory(@PathVariable(value = "inputDirectory") String inputDirectory){
        try {

            mergeService.mergeFilesInDirectory(inputDirectory);
            return "OK!";

        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED!";
        }

    }


}
