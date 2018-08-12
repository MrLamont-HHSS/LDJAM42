package com.mrlamont.ldjam42;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mrlamont.ldjam42.screens.GameScreen;
import com.mrlamont.ldjam42.screens.MenuScreen;

public class LDJAM42Game extends Game {
        private OrthographicCamera cam;
	private SpriteBatch batch;
        private FitViewport viewport;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
                cam = new OrthographicCamera();
                viewport = new FitViewport(1024, 576, cam);
                cam.position.set(1024/2, 576/2, 0);
                cam.update();
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
        
        @Override
        public void resize(int width, int height){
            viewport.update(width, height);
        }
        
        public SpriteBatch getBatch(){
            return batch;
        }
        
        public OrthographicCamera getCamera(){
            return cam;
        }
}
