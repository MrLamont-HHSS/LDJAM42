/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrlamont.ldjam42.models;

import com.badlogic.gdx.math.Vector2;
import com.mrlamont.ldjam42.screens.GameScreen;

/**
 *
 * @author lamon
 */
public class Piece {

    public static final int I = 0;
    public static final int S = 1;
    public static final int Z = 2;
    public static final int L = 3;
    public static final int J = 4;
    public static final int T = 5;
    public static final int SQ = 6;
    
    private final int numRotations;
    private int rotation;
    private final Vector2[][] rotations;
    private Vector2 location;
    private final Vector2 startLocation;

    public Piece(Vector2 spot, int[] rotations) {
        location = spot;
        startLocation = spot.cpy();
        
        this.numRotations = rotations.length;
        this.rotation = 0;
        this.rotations = new Vector2[numRotations][4];
        for(int rot = 0; rot < numRotations; rot++){
            for(int i = 0; i < 4; i++){
                int col = rotations[rot]%10;
                rotations[rot]/=10;
                int row = rotations[rot]%10;
                rotations[rot]/=10;
                this.rotations[rot][i] = new Vector2(row, col);
            }
        }
    }

    public void reset(){
        rotation = 0;
        location.set(startLocation);
    }
    
    public void rotateCCW() {
        this.rotation = (this.rotation + 1) % this.numRotations;
    }

    public void rotateCW() {
        this.rotation = this.rotation == 0? this.numRotations - 1: this.rotation - 1;
    }
    
    public boolean moveLeft() {
        for (int i = 0; i < 4; i++) {
            if (rotations[rotation][i].y + location.y == 0) {
                return false;
            }
        }
        location.y--;
        return true;
    }
    
    public boolean moveRight() {
        for (int i = 0; i < 4; i++) {
            if (rotations[rotation][i].y + location.y == GameScreen.GRID_SIZE - 1 ) {
                return false;
            }
        }
        location.y++;
        return true;
    }
    
    public boolean moveDown() {
        for (int i = 0; i < 4; i++) {
            if (rotations[rotation][i].x + location.x == 0 ) {
                return false;
            }
        }
        location.x--;
        return true;
    }
    
    public boolean moveUp() {
        for (int i = 0; i < 4; i++) {
            if (rotations[rotation][i].x + location.x == GameScreen.GRID_SIZE - 1 ) {
                return false;
            }
        }
        location.x++;
        return true;
    }

    public int getRow(int spot) {
        return (int)(location.x + rotations[rotation][spot].x);
    }
    
    public int getCol(int spot) {
        return (int)(location.y + rotations[rotation][spot].y);
    }
}
