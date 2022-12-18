package org.codecop.monolith.playground;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class Model {

    @Inject
    private Life status;
    @Inject
    private Position position;

    private int countNeighbours;
    private int lastClock = -1;

    public void tick(int clock) {
        if (clock > lastClock) {
            lastClock = clock;
            status.withNeighbours(countNeighbours);
            countNeighbours = 0;
        }
    }

    public void recordLivingNeighbour(Position at) {
        if (position.isNext(at)) {
            countNeighbours++;
        }
    }

    public Life getStatus() {
        return status;
    }

    public Position getPosition() {
        return position;
    }

}
