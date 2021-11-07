package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen implements Screen, InputProcessor {

    private TetrominoGrid grid;
    private TetrominoGridRenderer renderer;
    private TetrominoController controller;

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        controller.update(delta);
        grid.update(delta);
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

        grid = new TetrominoGrid();
        renderer = new TetrominoGridRenderer(grid);
        controller = new TetrominoController(grid);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Keys.LEFT)
            controller.leftPressed();
        if(keycode == Keys.RIGHT)
            controller.rightPressed();
        if(keycode == Keys.DOWN)
            controller.downPressed();
        if(keycode == Keys.UP)
            controller.upPressed();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        if(keycode == Keys.LEFT)
            controller.leftReleased();
        if(keycode == Keys.RIGHT)
            controller.rightReleased();
        if(keycode == Keys.DOWN)
            controller.downReleased();
        if(keycode == Keys.UP)
            controller.upReleased();
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
