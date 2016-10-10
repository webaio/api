package io.weba.api.ui.rest.controller;

import io.weba.api.domain.session.SessionCardinality;
import io.weba.api.domain.session.SessionCardinalityCriteria;
import io.weba.api.domain.session.SessionCardinalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class SessionCardinalityController {
    @Autowired
    private SessionCardinalityRepository sessionCardinalityRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/session/cardinality")
    @ResponseBody
    public ResponseEntity<List<SessionCardinality>> getSessionCardinality(
            @RequestParam(value = "dateFrom", required = true) @DateTimeFormat(pattern = "yyyy-mm-dd") Date from,
            @RequestParam(value = "dateTo", required = true) @DateTimeFormat(pattern = "yyyy-mm-dd") Date to,
            @RequestParam(value = "trackerIdentity", required = true) UUID trackerIdentity

    ) {
        List<SessionCardinality> sessionCardinality = this
                .sessionCardinalityRepository
                .findBy(new SessionCardinalityCriteria(from, to, trackerIdentity));

        return new ResponseEntity<>(sessionCardinality, HttpStatus.OK);
    }
}
