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
import com.badlogic.gdx.math.Vector2;
import com.mrlamont.ldjam42.LDJAM42Game;
import com.mrlamont.ldjam42.models.Grid;
import com.mrlamont.ldjam42.models.Piece;

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
    private Piece piece;
    
    
    
    
    public GameScreen(SpriteBatch batch, OrthographicCamera cam, LDJAM42Game game){
        grid = new Grid(GRID_SIZE,GRID_SIZE);

        this.batch = batch;
        this.cam = cam;
        shapeRender = new ShapeRenderer();
        this.game = game;
        
        piece = new Piece();
                
        piece.set(Piece.L);
        
        for(int i = 3; i < 11; i++){
            grid.setPiece(0, i);
            grid.setPiece(i+1, 2);
        }
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
