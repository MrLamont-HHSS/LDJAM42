/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrlamont.ldjam42.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author lamon
 */
public class Grid {

    private boolean grid[][];

    public Grid(int width, int height) {
        grid = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = false;
            }
        }
    }

    public void reset() {
        for (boolean[] grid1 : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                grid1[j] = false;
            }
        }
    }

    public void rotateCCW() {
        boolean[][] rGrid = new boolean[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                rGrid[row][col] = grid[grid[0].length - 1 - col][row];
            }
        }
        grid = rGrid;
    }

    public void rotateCW() {
        boolean[][] rGrid = new boolean[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                rGrid[grid[0].length - 1 - col][row] = grid[row][col];
            }
        }
        grid = rGrid;
    }

    public boolean hasPiece(int row, int col) {
        return grid[row][col];
    }

    public void setPiece(int row, int col) {
        grid[row][col] = true;
    }

    public void printGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col]) {
                    System.out.printf("%d %d\n", row, col);
                }
            }
        }
    }

    public int checkLines() {
        int lines = 0;
        boolean[] remove = new boolean[grid.length];
        for (int i = 0; i < grid.length / 2; i++) {
            boolean countR = true;
            for (int j = 0; j < grid[i].length; j++) {
                if (!grid[i][j]) {
                    countR = false;
                }

            }
            if (countR) {
                remove[i] = true;
                lines++;
            }
        }

        
        for (int i = grid.length - 1; i >= 0; i--) {
            if (remove[i]) {
                boolean[][] rGrid = new boolean[grid.length][grid[0].length];
                genGrid(i,0,rGrid);
                
                for(int col = 0; col < grid.length; col++){
                    grid[i][col] = false;
                    rGrid[i][col] = false;
                }
                
                for(int row = i+1; row < rGrid.length; row++){
                    for(int col = 0; col < rGrid.length; col++){
                        if(rGrid[row][col]){
                            grid[row-1][col] = grid[row][col];
                        }
                        if(row == grid.length-1){
                            grid[row][col] = false;
                        }
                    }
                }
            }
        }

        return lines;
    }

    private void genGrid(int row, int col, boolean[][] rGrid) {
        if (row >= rGrid.length || col >= rGrid.length || col < 0 || rGrid[row][col] || !grid[row][col]) {
            return;
        }
        rGrid[row][col] = true;
        genGrid(row+1,col, rGrid);
        genGrid(row,col+1, rGrid);
        genGrid(row,col-1, rGrid);
    }
}
