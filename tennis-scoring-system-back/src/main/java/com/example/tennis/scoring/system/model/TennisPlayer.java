package com.example.tennis.scoring.system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Player attributes
 */
@Accessors(chain = true)
@AllArgsConstructor
@Getter
@Setter
public class TennisPlayer implements Serializable {

    private String name;

    private int points;

    private boolean isWinner;

}
