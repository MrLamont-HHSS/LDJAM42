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
public class OutputPieceRotations {
    public static void main(String[] args) {
        Grid g = new Grid(4,4);
        
        g.setPiece(3, 2);
        g.setPiece(2, 2);
        g.setPiece(1, 2);
        g.setPiece(1, 1);
        
        g.printGrid();
        g.rotateCCW();
        g.printGrid();
        g.rotateCCW();
        g.printGrid();
        g.rotateCCW();
        g.printGrid();
    }
}
