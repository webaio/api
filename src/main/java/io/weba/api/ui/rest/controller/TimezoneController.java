package io.weba.api.ui.rest.controller;

import io.weba.api.domain.timezone.TimezoneRepository;
import io.weba.api.domain.timezone.Timezones;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TimezoneController {
    @Autowired
    private TimezoneRepository timezoneRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/timezones")
    public Timezones get() {
        return this.timezoneRepository.getList();
    }
}
