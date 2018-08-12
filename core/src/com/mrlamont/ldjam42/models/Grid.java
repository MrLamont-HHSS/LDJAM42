/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrlamont.ldjam42.models;


/**
 *
 * @author lamon
 */
public class Grid {
    private boolean grid[][];
    
    public Grid(int width, int height){
        grid = new boolean[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                grid[i][j] = false;
            }
        }
    }
    
    public void reset(){
        for (boolean[] grid1 : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                grid1[j] = false;
            }
        }
    }
    
    public void rotateCCW(){
        boolean[][] rGrid = new boolean[grid.length][grid[0].length];
        for(int row = 0; row < grid.length; row++){
            for(int col = 0; col < grid[0].length; col++){
                rGrid[row][col] = grid[grid[0].length - 1 -col][row];
            }
        }
        grid = rGrid;
    }
    
    public void rotateCW(){
        boolean[][] rGrid = new boolean[grid.length][grid[0].length];
        for(int row = 0; row < grid.length; row++){
            for(int col = 0; col < grid[0].length; col++){
                rGrid[grid[0].length - 1 -col][row] = grid[row][col];
            }
        }
        grid = rGrid;
    }
    
    public boolean hasPiece(int row, int col){
        return grid[row][col];
    }
    
    public void setPiece(int row, int col){
        grid[row][col] = true;
    }
    
    public void printGrid(){
        for(int row = 0; row < grid.length; row++){
            for(int col = 0; col < grid[0].length; col++){
                if(grid[row][col]){
                    System.out.printf("%d %d\n", row, col);
                }
            }
        }
    }
}
