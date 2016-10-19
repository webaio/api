package io.weba.api.ui.rest.controller;

import io.weba.api.domain.session.Interval;
import io.weba.api.domain.session.SessionCardinality;
import io.weba.api.domain.session.SessionCardinalityCriteria;
import io.weba.api.domain.session.SessionCardinalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class SessionCardinalityController {
    @Autowired
    private SessionCardinalityRepository sessionCardinalityRepository;

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET, value = "/session/cardinality")
    @ResponseBody
    public ResponseEntity<List<SessionCardinality>> getSessionCardinality(SessionCardinalityCriteria sessionCardinalityCriteria) {
        List<SessionCardinality> sessionCardinality = this
                .sessionCardinalityRepository
                .findBy(sessionCardinalityCriteria);

        return new ResponseEntity<>(sessionCardinality, HttpStatus.OK);
    }
}
