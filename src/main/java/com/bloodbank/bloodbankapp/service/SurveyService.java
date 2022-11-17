package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.exception.NotFoundException;
import com.bloodbank.bloodbankapp.model.Survey;
import com.bloodbank.bloodbankapp.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public Survey getByUser(Long id){
        Survey survey = surveyRepository.findByUser(id);
        if (survey == null) throw new NotFoundException("Survey doesnt exist for specified user");

        return survey;
    }

    public Survey add(Survey survey){
        surveyRepository.save(survey);

        return survey;
    }

    public List<Survey> getAll(){
        return surveyRepository.findAll();
    }
}
