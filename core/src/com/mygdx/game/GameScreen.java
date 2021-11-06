package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen, InputProcessor {

    private TetrominoGrid grid;
    private TetrominoGridRenderer renderer;
    private TetrominoController controller;

    @Override
    public boolean keyDown(int keycode) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public void show() {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void render(float delta) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void resize(int width, int height) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void pause() {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void resume() {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void hide() {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void dispose() {
        // TODO 自動生成されたメソッド・スタブ

    }

}
