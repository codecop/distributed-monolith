package org.codecop.monolith.playground.events;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.ConfiguredEqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class ClockedPositionTest {

    @Test
    void equalsContract() {
        new ConfiguredEqualsVerifier(). //
                suppress(Warning.NONFINAL_FIELDS). //
                forClass(ClockedPosition.class). //
                verify();
    }

}
