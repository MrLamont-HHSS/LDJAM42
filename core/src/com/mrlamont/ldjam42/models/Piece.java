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
        if(isOffScreen()){
            rotateCW();
        }
    }

    public void rotateCW() {
        this.rotation = this.rotation == 0? this.numRotations - 1: this.rotation - 1;
        if(isOffScreen()){
            rotateCCW();
        }
    }
    
    public boolean moveLeft() {
        for (int i = 0; i < 4; i++) {
            if (getCol(i) == 0) {
                return false;
            }
        }
        location.y--;
        return true;
    }
    
    public boolean moveRight() {
        for (int i = 0; i < 4; i++) {
            if (getCol(i) == GameScreen.GRID_SIZE - 1 ) {
                return false;
            }
        }
        location.y++;
        return true;
    }
    
    public boolean moveDown() {
        for (int i = 0; i < 4; i++) {
            if (getRow(i) == 0 ) {
                return false;
            }
        }
        location.x--;
        return true;
    }
    
    private boolean isOffScreen(){
        for(int i = 0; i < 4; i++){
            int row = getRow(i);
            int col = getCol(i);
            if(row >= GameScreen.GRID_SIZE || row < 0 || col >= GameScreen.GRID_SIZE || col < 0){
                return true;
            }
        }
        return false;
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
    
    public int getCleanRow(int spot) {
        return (int)(rotations[0][spot].x);
    }
    
    public int getCleanCol(int spot) {
        return (int)(rotations[0][spot].y);
    }
}
