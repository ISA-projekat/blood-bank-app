package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.model.Survey;
import com.bloodbank.bloodbankapp.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping("/getByUser/{id}")
    public Survey getByUser(@PathVariable("id") Long id){
        return surveyService.getByUser(id);
    }

    @GetMapping
    public List<Survey> getAll(){
        return surveyService.getAll();
    }

    @CrossOrigin
    @PostMapping
    public Survey add(@RequestBody Survey survey){
        return surveyService.add(survey);
    }

}
