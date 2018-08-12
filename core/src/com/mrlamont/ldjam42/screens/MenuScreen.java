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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mrlamont.ldjam42.LDJAM42Game;

/**
 *
 * @author lamon
 */
public class MenuScreen implements Screen {

    private LDJAM42Game game;
    private SpriteBatch batch;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRender;
    private Texture title;
    private Texture play;
    private boolean first = true;

    public MenuScreen(LDJAM42Game game) {
        this.game = game;
        batch = game.getBatch();
        cam = game.getCamera();
        shapeRender = new ShapeRenderer();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (first) {
            title = new Texture(Gdx.files.internal("title.png"));
            play = new Texture(Gdx.files.internal("playBtn.png"));
            first = false;
        }
        shapeRender.begin(ShapeRenderer.ShapeType.Filled);
        shapeRender.setColor(Color.GRAY);
        shapeRender.rect(0, 0, cam.viewportWidth, cam.viewportHeight);
        shapeRender.end();
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
