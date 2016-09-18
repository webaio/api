package io.weba.api.domain.site;

import java.util.Optional;

public interface TrackerRepository {
    Optional<Tracker> findBy(Site site);
}
