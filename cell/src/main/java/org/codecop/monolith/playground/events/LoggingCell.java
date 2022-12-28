package org.codecop.monolith.playground.events;

import org.codecop.monolith.playground.gol.Cell;
import org.codecop.monolith.playground.gol.Life;
import org.codecop.monolith.playground.gol.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class LoggingCell {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private Cell cell;

    @Value("${micronaut.server.port}")
    private int port;

    @Inject
    private Time time;

    public boolean seed(Position at) {
        logStateBefore("seed", at);
        boolean value = cell.seed(at);
        logStateAfter("seed", at);
        return value;
    }

    private void logStateBefore(String action, Position at) {
        if (at != null) {
            logger.warn("starting action {} with ({}x{})", action, at.getX(), at.getY());
        } else {
            logger.warn("starting action {}", action);
        }
        logState();
    }

    private void logStateAfter(String action, @SuppressWarnings("unused") Position ignored) {
        logState();
        logger.warn("ending action {} ----------------------------------------", action);
    }

    private void logState() {
        logger.warn("Cell {} at time {} at {}x{} is {} with {} recorded neighbours", // 
                port, time.getCurrent(), getPosition().getX(), getPosition().getY(), isAlive(),
                cell.getCountNeighbours());
    }

    public void recordLivingNeighbour(Position at) {
        logStateBefore("recordLivingNeighbour", at);
        cell.recordLivingNeighbour(at);
        logStateAfter("recordLivingNeighbour", at);
    }

    public void tick() {
        logStateBefore("tick", null);
        cell.tick();
        logStateAfter("tick", null);
    }

    public boolean isAlive() {
        return cell.isAlive();
    }

    public Life getLife() {
        return cell.getLife();
    }

    public Position getPosition() {
        return cell.getPosition();
    }

}
