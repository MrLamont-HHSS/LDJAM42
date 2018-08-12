/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrlamont.ldjam42.screens;

import com.badlogic.gdx.Screen;
import com.mrlamont.ldjam42.LDJAM42Game;

/**
 *
 * @author lamon
 */
public class MenuScreen implements Screen{

    LDJAM42Game game;
    
    public MenuScreen(LDJAM42Game game){
        this.game = game;
    }
    
    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        
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
