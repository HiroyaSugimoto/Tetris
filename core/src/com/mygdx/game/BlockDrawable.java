package com.mygdx.game;

import com.mygdx.game.Tetromino.colors;

public class BlockDrawable {

    public int x;
    public int y;
    public colors color;

    //引数ありのコンストラクタ
    public BlockDrawable(int x, int y, colors color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
