/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrlamont.ldjam42.models;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mrlamont.ldjam42.screens.GameScreen;

/**
 *
 * @author lamon
 */
public class Piece {

    public static final int NUM_PIECES = 7;

    public static final int I = 0;
    public static final int S = 1;
    public static final int Z = 2;
    public static final int L = 3;
    public static final int J = 4;
    public static final int T = 5;
    public static final int SQ = 6;

    private Vector2[] spots = new Vector2[4];
    private Vector2 center = new Vector2(0, 0);
    private int currentPiece = 0;

    public Piece() {
        for (int i = 0; i < 4; i++) {
            spots[i] = new Vector2(0, 0);
        }
    }

    public void set(int type) {
        currentPiece = type;
        int xStart = GameScreen.GRID_SIZE / 2 - 2;
        int yStart = GameScreen.GRID_SIZE / 2 - 2;
        center.set(yStart+1.5f, xStart+2);
        switch (type) {
            case I:
                for (int i = 0; i < 4; i++) {
                    spots[i].set(yStart + i, xStart + 2);
                }
                center.set(yStart+1.5f, xStart+2);
                break;
            case L:
                for (int i = 0; i < 3; i++) {
                    spots[i].set(yStart + i, xStart + 2);
                }
                spots[3].set(yStart, xStart + 3);
                center.set(xStart+3.5f, yStart+1.5f);
                break;
            case J:
                for (int i = 0; i < 3; i++) {
                    spots[i].set(yStart + i, xStart + 2);
                }
                spots[3].set(yStart, xStart + 1);
                center.set(yStart+0.5f, xStart+2.5f);
                break;
            case S:
                for (int i = 0; i < 2; i++) {
                    spots[i].set(yStart + 2, xStart + 3 + i);
                    spots[i + 2].set(yStart + 1, xStart + 3 - i);
                }
                center.set(yStart+1.5f, xStart+3.5f);
                break;
            case Z:
                for (int i = 0; i < 2; i++) {
                    spots[i].set(yStart + 2, xStart + 2 - i);
                    spots[i + 2].set(yStart + 1, xStart + 2 + i);
                }
                center.set(yStart+1.5f, xStart+3.5f);
                break;
            case T:
                for (int i = 0; i < 3; i++) {
                    spots[i].set(yStart + 1, xStart + 1 + i);
                }
                spots[3].set(yStart + 2, xStart + 2);
                center.set(yStart+1.5f, xStart+2.5f);
                break;
            case SQ:
                for (int i = 0; i < 2; i++) {
                    spots[i].set(yStart + 2, xStart + 2 + i);
                    spots[i+2].set(yStart + 3, xStart + 2 + i);
                }
                center.set(yStart+2.5f, xStart+2.5f);
                break;
        }
    }

    public int getRow(int spot) {
        return (int) spots[spot].x;
    }

    public int getCol(int spot) {
        return (int) spots[spot].y;
    }

    public void moveLeft() {
        for (int i = 0; i < 4; i++) {
            if (spots[i].y == 0) {
                return;
            }
        }
        for (int i = 0; i < 4; i++) {
            spots[i].y--;
        }
        center.y--;
    }

    public void moveRight() {
        for (int i = 0; i < 4; i++) {
            if (spots[i].y == GameScreen.GRID_SIZE - 1) {
                return;
            }
        }
        for (int i = 0; i < 4; i++) {
            spots[i].y++;
        }
        center.y++;
    }

    public void moveDown() {
        for (int i = 0; i < 4; i++) {
            spots[i].x--;
        }
        center.x--;
    }

    public void moveUp() {
        for (int i = 0; i < 4; i++) {
            spots[i].x++;
        }
        center.x++;
    }

    public void pickType() {
        set(MathUtils.random(0, NUM_PIECES));
    }

    public void rotateCCW() {
        for (Vector2 v : spots) {
            v.sub(center);
            v.rotate(90);
            v.add(center);
        }
    }

    public void rotateCW() {
        switch (currentPiece) {

        }
    }

}
