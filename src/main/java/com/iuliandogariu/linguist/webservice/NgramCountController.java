package com.iuliandogariu.linguist.webservice;

import com.iuliandogariu.linguist.ngram.NgramCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public final class NgramCountController {

    private NgramCountService ngramCountService;

    @Autowired
    public NgramCountController(NgramCountService ngramCountService) {
        this.ngramCountService = ngramCountService;
    }

    @PostMapping(value = "/ngram")
    List<NgramCountResponse> ngramCount(@Valid @RequestBody NgramCountRequest ngramCountRequest) {
        return ngramCountService.countNgrams(ngramCountRequest.getMaxNGramCount(), ngramCountRequest.getText())
                .stream()
                .map(NgramCountResponse::fromNgramCount)
                .collect(Collectors.toList());
    }
}
