package com.suchorab.entity;

public class Move {
    boolean left = false, right = false, up = false, down = false;

    public Move(int direction) {
        switch (direction) {
            case 0:
                right = true;
                break;
            case 1:
                left = true;
                break;
            case 2:
                up = true;
                break;
            case 3:
                down = true;
                break;
        }
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean haveSpaceToMove() {
        return this.down && this.up && this.left && this.right;
    }
}
