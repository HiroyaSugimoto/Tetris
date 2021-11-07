package com.mygdx.game;

import java.util.HashMap;
import java.util.Map;

public class TetrominoController {

    private TetrominoGrid grid;

    public TetrominoController(TetrominoGrid grid) {
        this.grid = grid;
    }

    enum Keys {
        LEFT, RIGHT, DOWN,UP
    }

    static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();
    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.DOWN, false);
        keys.put(Keys.UP, false);
    }

    //キーを押下している間
    public void leftPressed() {
        keys.get(keys.put(Keys.LEFT, true));
    }
    public void rightPressed() {
        keys.get(keys.put(Keys.RIGHT, true));
    }
    public void downPressed() {
        keys.get(keys.put(Keys.DOWN, true));
    }
    public void upPressed() {
        keys.get(keys.put(Keys.UP, true));
    }

    //キーがリリースされた際
    public void leftReleased() {
        keys.get(keys.put(Keys.LEFT, false));
    }
    public void rightReleased() {
        keys.get(keys.put(Keys.RIGHT, false));
    }
    public void downReleased() {
        keys.get(keys.put(Keys.DOWN, false));
    }
    public void upReleased() {
        keys.get(keys.put(Keys.UP, false));
    }

    /**
     * 更新処理を行うメソッド
     */
    public void update(float delta) {
        processInput();
    }

    /**
     * 入力内容に基づいてブロックの状態とパラメータを変更
     */
    private void processInput() {
        if(keys.get(Keys.LEFT)) {
            grid.moveLeft();
        }
        if(keys.get(Keys.RIGHT)) {
            grid.moveRight();
        }
        if(keys.get(Keys.DOWN)) {
            grid.moveDown();
        }
        if(keys.get(Keys.UP)) {
            grid.rotateClockwise();
            upReleased(); //一回押す毎に回転
        }
    }

}
