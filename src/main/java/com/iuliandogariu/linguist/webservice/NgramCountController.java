package com.iuliandogariu.linguist.webservice;

import com.iuliandogariu.linguist.ngram.block.BlockNgramCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@RestController
public final class NgramCountController {

    private BlockNgramCountService ngramCountService;

    @Autowired
    public NgramCountController(BlockNgramCountService ngramCountService) {
        this.ngramCountService = ngramCountService;
    }

    @PostMapping(value = "/ngram")
    public Callable<List<NgramCountResponse>> ngramCount(@Valid @RequestBody NgramCountRequest ngramCountRequest) {
        return () -> ngramCountService.countNgrams(ngramCountRequest.getMaxNGramCount(), ngramCountRequest.getText())
                .stream()
                .map(NgramCountResponse::fromNgramCount)
                .collect(Collectors.toList());
    }
}
