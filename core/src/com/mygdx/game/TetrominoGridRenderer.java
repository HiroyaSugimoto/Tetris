package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.Tetromino.colors;

public class TetrominoGridRenderer {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Rectangle[][] gridRectangles;
    private ArrayMap<colors, Texture> textures;
    private TetrominoGrid grid;

    public TetrominoGridRenderer(TetrominoGrid grid) {
        camera = new OrthographicCamera();
        //画面サイズの基本単位は1tetrominoと等しい
        //プレイフィールドは10×20
        //プレイフィールドは画面中央に配置し、1ユニットが空白
        //カメラの描画範囲は12×22
        camera.setToOrtho(false, 12, 22);

        batch = new SpriteBatch();
        this.grid = grid;
        gridRectangles = createGridRectangles();

        textures = new ArrayMap<colors, Texture>();
        textures.put(colors.RED, new Texture(Gdx.files.internal("red.png")));
        textures.put(colors.GREEN, new Texture(Gdx.files.internal("green.png")));
        textures.put(colors.BLUE, new Texture(Gdx.files.internal("blue.png")));
    }

    public void render(float delta) {
        camera.update();

        //カメラによる描画
        batch.setProjectionMatrix(camera.combined);

        //現在のgridを描画
        batch.begin();
        for(BlockDrawable block : grid.getGridBlocksToDraw()) {
            Rectangle rect = gridRectangles[(int)block.y][(int)block.x];
            rect.width = 1;
            rect.height = 1;
            rect.x = block.x + 1;
            rect.y = block.y + 1;
            batch.draw(textures.get(block.color), rect.x, rect.y, rect.width, rect.height);
        }
        batch.end();

    }

    private Rectangle[][] createGridRectangles(){
        Rectangle[][] rects = new Rectangle[20][10];

        //10×20のrectangleを作成
        for(int row = 0; row < 20; row++) {
            for(int col = 0; col < 10; col++) {
                Rectangle rect = new Rectangle();
                rect.width = 1;
                rect.height = 1;
                rect.x = col + 1;
                rect.y = row + 1;
                rects[row][col] = rect;
            }
        }
        return rects;
    }

}
