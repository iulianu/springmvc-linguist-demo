package com.iuliandogariu.linguist.webservice;

import com.iuliandogariu.linguist.munging.block.BlockMungingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.Callable;

@RestController
public final class MungingController {

    private BlockMungingService mungingService;

    @Autowired
    public MungingController(BlockMungingService mungingService) {
        this.mungingService = mungingService;
    }

    @PostMapping(value = "/format")
    public Callable<MungingResponse> munge(@Valid @RequestBody MungingRequest mungingRequest) {
        return () -> new MungingResponse(
                mungingService.mungedPhrasePairs(mungingRequest.getText()));
    }
}
