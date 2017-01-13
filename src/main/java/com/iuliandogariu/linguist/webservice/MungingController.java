package com.iuliandogariu.linguist.webservice;

import com.iuliandogariu.linguist.munging.MungingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public final class MungingController {

    private MungingService mungingService;

    @Autowired
    public MungingController(MungingService mungingService) {
        this.mungingService = mungingService;
    }

    @PostMapping(value = "/format")
    MungingResponse munge(@Valid @RequestBody MungingRequest mungingRequest) {
        return new MungingResponse(
                mungingService.unmungedPhraseBook(mungingRequest.getText()).toMungedString());
    }
}
