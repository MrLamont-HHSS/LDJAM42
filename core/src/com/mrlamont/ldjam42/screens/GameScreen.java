/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrlamont.ldjam42.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mrlamont.ldjam42.LDJAM42Game;
import com.mrlamont.ldjam42.models.Grid;
import com.mrlamont.ldjam42.models.Piece;
import com.mrlamont.ldjam42.models.PieceOld;

/**
 *
 * @author lamon
 */
public class GameScreen implements Screen{
    
    public static final int GRID_SIZE = 22;
    private static int BLOCK_SIZE = 24;
    
    
    private SpriteBatch batch;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRender;
    private Grid grid;
    private LDJAM42Game game;
    private Piece[] pieces;
    private Piece piece;
    private int nextPiece;
    
    
    
    
    public GameScreen(SpriteBatch batch, OrthographicCamera cam, LDJAM42Game game){
        grid = new Grid(GRID_SIZE,GRID_SIZE);

        this.batch = batch;
        this.cam = cam;
        shapeRender = new ShapeRenderer();
        this.game = game;
        
        float cornerRow = GRID_SIZE/2 - 2;
        float cornerCol = GRID_SIZE/2 - 2;
        
        pieces = new Piece[7];
        pieces[Piece.I] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{31211101, 30313233});
        pieces[Piece.S] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{11122223,12212231});
        pieces[Piece.Z] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{12132122,11212232});
        pieces[Piece.L] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{11122131,10111222,12223231,20212210});
        pieces[Piece.J] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{11122232,11121321,11213132,21222313});
        pieces[Piece.T] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{11121322,13233322,31323322,11213122});
        pieces[Piece.SQ] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{11122122});
        
        piece = pieces[MathUtils.random(0, 6)];
        nextPiece = MathUtils.random(0, 6);
        
    }
    
    
    public void getPiece(){
        piece = pieces[nextPiece];
        piece.reset();
        nextPiece = MathUtils.random(0, pieces.length - 1);
    }
    
    
    
    
    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        // level rotation
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
            grid.rotateCCW();
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.O)){
            grid.rotateCW();
        }
        
        // piece rotation
        if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
            piece.rotateCCW();
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.L)){
            piece.rotateCW();
        }
        
        // piece movement
        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            piece.moveLeft();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
            piece.moveRight();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
            piece.moveDown();
        }
        
        boolean collision = checkCollision();
        
        float gridCornerX = cam.viewportWidth/2 - GRID_SIZE*BLOCK_SIZE/2;
        float gridCornerY = cam.viewportHeight/2 - GRID_SIZE*BLOCK_SIZE/2;
        float gridSize = GRID_SIZE*BLOCK_SIZE;
        shapeRender.setProjectionMatrix(cam.combined);
        
        shapeRender.begin(ShapeRenderer.ShapeType.Filled);
        if(collision){
            shapeRender.setColor(Color.RED);
        }else{
            shapeRender.setColor(Color.GRAY);
        }
        shapeRender.rect(0, 0, cam.viewportWidth, cam.viewportHeight);
        shapeRender.setColor(Color.LIGHT_GRAY);
        shapeRender.rect(gridCornerX, gridCornerY, gridSize, gridSize);
        shapeRender.setColor(Color.ROYAL);
        drawGrid();
        shapeRender.end();
        
        shapeRender.begin(ShapeRenderer.ShapeType.Line);
        shapeRender.setColor(Color.BLUE);
        drawGrid();
        drawPiece();
        shapeRender.end();
    }
    
    private void drawPiece(){
        float gridCornerX = cam.viewportWidth/2 - GRID_SIZE*BLOCK_SIZE/2;
        float gridCornerY = cam.viewportHeight/2 - GRID_SIZE*BLOCK_SIZE/2;
        for(int i = 0; i < 4; i++){
            shapeRender.rect(gridCornerX+piece.getCol(i)*BLOCK_SIZE, gridCornerY+piece.getRow(i)*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }
    }
    
    private void drawGrid(){
        float gridCornerX = cam.viewportWidth/2 - GRID_SIZE*BLOCK_SIZE/2;
        float gridCornerY = cam.viewportHeight/2 - GRID_SIZE*BLOCK_SIZE/2;
        for(int row = 0; row < GRID_SIZE; row++){
            for(int col = 0; col < GRID_SIZE; col++){
                if(grid.hasPiece(row, col)){
                    shapeRender.rect(gridCornerX+col*BLOCK_SIZE, gridCornerY+row*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }
    
    public boolean checkCollision(){
        for(int i = 0; i < 4; i++){
            if(grid.hasPiece(piece.getRow(i),piece.getCol(i))){
                return true;
            }
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        
    }
    
}
