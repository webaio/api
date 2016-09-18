package io.weba.api.infrastructure.domain.site.file;

import io.weba.api.domain.site.Site;
import io.weba.api.domain.site.Tracker;
import io.weba.api.domain.site.TrackerRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class TrackerRepositoryImpl implements TrackerRepository {
    private final ClassLoader classLoader;

    @Value("${weba.collector_url}")
    private String collectorUrl;

    @Autowired
    public TrackerRepositoryImpl(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Optional<Tracker> findBy(Site site) {
        try {
            File file = new File(this.classLoader.getResource("tracker.js.tmpl").getFile());
            Scanner scan = new Scanner(file).useDelimiter("\\Z");

            return Optional.of(new Tracker(String.format(scan.next(), site.getId(), this.collectorUrl)));
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }
}
