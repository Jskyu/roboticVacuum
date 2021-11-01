package com.example.roboticVacuum.dto;

/**
 * command
 * table : CMD_TABLE
 * query
CREATE TABLE `CMD_TABLE` (
    `INDEX` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `NAME` VARCHAR(50) NOT NULL DEFAULT 'defualt' COLLATE 'utf8mb4_general_ci',
    `MOVE` INT(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`index`) USING BTREE)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB;

 * INDEX : primary key
 * NAME : saved name
 * MOVE : up, down, right, left == 0, 1, 2, 3
 */
public class CommandDTO {
    private Long index;
    private String name;
    private int move;

    public CommandDTO() {
    }

    public CommandDTO(Long index, String id, int move) {
        this.index = index;
        this.name = id;
        this.move = move;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    @Override
    public String toString() {
        return "CommandDTO{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", move=" + move +
                '}';
    }
}
