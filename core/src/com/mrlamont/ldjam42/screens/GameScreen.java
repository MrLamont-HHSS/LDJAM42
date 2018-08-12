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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
public class GameScreen implements Screen {

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
    private Vector2 moved;
    
    private BitmapFont font32;
    private BitmapFont font22;

    private float dropDelay = 1.0f;
    private float holdDelay = 0.1f;
    private float sideDelayTime = 0;
    private float dropDelayTime = 0;

    private boolean collision = false;
    private boolean gameOver = false;
    private boolean pause = true;
    
    private int numLines = 0;

    public GameScreen(SpriteBatch batch, OrthographicCamera cam, LDJAM42Game game) {
        grid = new Grid(GRID_SIZE, GRID_SIZE);

        this.batch = batch;
        this.cam = cam;
        shapeRender = new ShapeRenderer();
        this.game = game;

        moved = new Vector2(0, 0);

        float cornerRow = GRID_SIZE / 2 - 2;
        float cornerCol = GRID_SIZE / 2 - 2;

        pieces = new Piece[7];
        pieces[Piece.I] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{31211101, 30313233});
        pieces[Piece.S] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{11122223, 12212231});
        pieces[Piece.Z] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{12132122, 11212232});
        pieces[Piece.L] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{11122131, 10111222, 12223231, 20212210});
        pieces[Piece.J] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{11122232, 11121321, 11213132, 21222313});
        pieces[Piece.T] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{11121322, 13233322, 31323322, 11213122});
        pieces[Piece.SQ] = new Piece(new Vector2(cornerRow, cornerCol), new int[]{11122122});

        piece = pieces[MathUtils.random(0, 6)];
        nextPiece = MathUtils.random(0, 6);
        
        font32 = new BitmapFont(Gdx.files.internal("Bookman32.fnt"));
        font22 = new BitmapFont(Gdx.files.internal("Bookman22.fnt"));
    }

    public void getPiece() {
        piece = pieces[nextPiece];
        piece.reset();
        nextPiece = MathUtils.random(0, pieces.length - 1);

        if (checkCollision()) {
            // game over baby!
            gameOver = true;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause = !pause;
        }
        if (!gameOver && !pause) {
            dropDelayTime += delta;
            sideDelayTime += delta;

            // level rotation
            if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
                grid.rotateCCW();
                if (checkCollision()) {
                    grid.rotateCW();
                }
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
                grid.rotateCW();
                if (checkCollision()) {
                    grid.rotateCCW();
                }
            }

            // piece rotation
            if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
                piece.rotateCCW();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
                piece.rotateCW();
            }

            // piece movement
            moved.set(0, 0);
            if (Gdx.input.isKeyPressed(Input.Keys.A) && sideDelayTime >= holdDelay) {
                sideDelayTime = 0;
                if (piece.moveLeft()) {
                    moved.y--;
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.D) && sideDelayTime >= holdDelay) {
                sideDelayTime = 0;
                if (piece.moveRight()) {
                    moved.y++;
                }
            }
            // see if it hit sideways
            // check collisions on grid
            collision = checkCollision();
            if (collision) {
                if (moved.y == -1) {
                    piece.moveRight();
                } else {
                    piece.moveLeft();
                }
            }

            boolean stick = false;
            if (Gdx.input.isKeyPressed(Input.Keys.S) && dropDelayTime >= holdDelay || dropDelayTime >= dropDelay) {
                dropDelayTime = 0;
                if (piece.moveDown()) {
                    moved.x--;
                } else {
                    stick = true;
                }
            }
            // check collisions on grid
            collision = checkCollision();
            if (collision || stick) {
                if (collision) {
                    piece.moveUp();
                }
                setPieces();
                piece.reset();
                getPiece();
            }
        }
        
        
        

        shapeRender.setProjectionMatrix(cam.combined);

        shapeRender.begin(ShapeRenderer.ShapeType.Filled);
        // draw Background
        if (collision) {
            shapeRender.setColor(Color.RED);
        } else {
            shapeRender.setColor(Color.GRAY);
        }
        shapeRender.rect(0, 0, cam.viewportWidth, cam.viewportHeight);
        // draw Grid background
        float gridSize = GRID_SIZE * BLOCK_SIZE;
        float gridCornerX = cam.viewportWidth / 2 - gridSize / 2;
        float gridCornerY = cam.viewportHeight / 2 - gridSize / 2;
        
        shapeRender.setColor(Color.LIGHT_GRAY);
        shapeRender.rect(gridCornerX, gridCornerY, gridSize, gridSize);
        
        //draw next piece background
        float size = 6*BLOCK_SIZE;
        float nextCornerX = cam.viewportWidth/8 - size/2;
        float nextCornerY = cam.viewportHeight/2 - size/2;
        shapeRender.rect(nextCornerX, nextCornerY, size, size);
        
        
        // draw placed pieces and next piece
        shapeRender.setColor(Color.ROYAL);
        drawGrid();
        drawNextPiece();
        shapeRender.end();

        // draw the player's piece and outline of placed pieces and starting
        shapeRender.begin(ShapeRenderer.ShapeType.Line);
        drawMiddle();
        shapeRender.setColor(Color.BLUE);
        drawGrid();
        drawPiece();
        shapeRender.end();

        batch.begin();
        font22.setColor(Color.BLACK);
        font32.setColor(Color.BLACK);
        font32.draw(batch, "QUATRIS", nextCornerX, cam.viewportHeight - 2*BLOCK_SIZE);
        font22.draw(batch, "Next Piece:", nextCornerX, nextCornerY + 7*BLOCK_SIZE );
        font22.draw(batch, "Lines:", nextCornerX, nextCornerY - 3*BLOCK_SIZE/2);
        font22.draw(batch, "" + numLines, nextCornerX, nextCornerY - 5*BLOCK_SIZE/2);
        batch.end();
        
    }

    private void drawPiece() {
        float gridCornerX = cam.viewportWidth / 2 - GRID_SIZE * BLOCK_SIZE / 2;
        float gridCornerY = cam.viewportHeight / 2 - GRID_SIZE * BLOCK_SIZE / 2;
        if(gameOver) shapeRender.setColor(Color.RED);
        for (int i = 0; i < 4; i++) {
            shapeRender.rect(gridCornerX + piece.getCol(i) * BLOCK_SIZE, gridCornerY + piece.getRow(i) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }
    }

    private void drawGrid() {
        float gridCornerX = cam.viewportWidth / 2 - GRID_SIZE * BLOCK_SIZE / 2;
        float gridCornerY = cam.viewportHeight / 2 - GRID_SIZE * BLOCK_SIZE / 2;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid.hasPiece(row, col)) {
                    shapeRender.rect(gridCornerX + col * BLOCK_SIZE, gridCornerY + row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }
    
    public void drawNextPiece(){
        float size = 4*BLOCK_SIZE;
        float nextCornerX = cam.viewportWidth/8 - size/2;
        float nextCornerY = cam.viewportHeight/2 - size/2;
        Piece next = pieces[nextPiece];
        for(int i = 0; i < 4; i++){
            shapeRender.rect(next.getCleanCol(i)*BLOCK_SIZE + nextCornerX, next.getCleanRow(i)*BLOCK_SIZE + nextCornerY, BLOCK_SIZE, BLOCK_SIZE);
        }
    }
    
    public void drawMiddle(){
        shapeRender.setColor(Color.CORAL);
        shapeRender.rect(cam.viewportWidth/2 - 2*BLOCK_SIZE, cam.viewportHeight/2 - 2*BLOCK_SIZE, 4*BLOCK_SIZE, 4*BLOCK_SIZE);
    }

    public boolean checkCollision() {
        for (int i = 0; i < 4; i++) {
            if (grid.hasPiece(piece.getRow(i), piece.getCol(i))) {
                return true;
            }
        }
        return false;
    }

    public void setPieces() {
        for (int i = 0; i < 4; i++) {
            grid.setPiece(piece.getRow(i), piece.getCol(i));
        }
        numLines += grid.checkLines();
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
