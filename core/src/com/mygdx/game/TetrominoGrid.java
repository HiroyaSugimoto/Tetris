package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * 画面上の全てのブロックがどこにあるのかと、現在落下中のブロックを取得
 * キー入力を受け取り、移動操作が有効かどうか判断
 * ゲーム画面の更新
 */

public class TetrominoGrid {
    private Tetromino fallingBlock;
    private Vector2 fallingBlockLocation;
    private boolean[][] grid;

    private int gridHeight = 20;
    private int gridWidth = 10;
    private long autoDropRate = 500000000;
    private long lastAutoDrop;
    private long moveRate = 100000000;
    private long lastMove;

    public TetrominoGrid() {
        grid = new boolean[gridHeight][gridWidth];

        newFallingBlock();
    }

    //新しい落下ブロックを作成
    private void newFallingBlock() {
        fallingBlock = new Tetromino();
        fallingBlockLocation = new Vector2(gridWidth / 2, gridHeight - 1); //作成位置を画面外の上部中央に指定
    }

    /**
     * 現在のブロックの座標を取得
     */
    public static Array<Vector2> translateBlockCoords(Tetromino block, Vector2 gridLocation){
        Array<Vector2> gridCoords = new Array<Vector2>();
        Array<Vector2> shape = block.getCurrentShape();
        for (Vector2 shapeCoords : shape) {
            shapeCoords = shapeCoords.cpy();
            gridCoords.add(shapeCoords.add(gridLocation));
        }
        return gridCoords;
    }

    private boolean moveTooFast(long tooFast) {
        return TimeUtils.nanoTime() - lastMove < tooFast;
    }

    private boolean moveTooFast() {
        return moveTooFast(moveRate);
    }

    private boolean timeForAutoDrop() {
        return TimeUtils.nanoTime() - lastAutoDrop > autoDropRate;
    }

    /**
     * 可能な場合、落下中のブロックを左に移動
     */
    public void moveLeft() {
        if(!moveTooFast() && canMoveLeft()) {
            fallingBlockLocation.add(new Vector2(-1, 0));
            lastMove = TimeUtils.nanoTime();
        }
    }

    /**
     * 可能な場合、落下中のブロックを右に移動
     */
    public void moveRight() {
        if (!moveTooFast() && canMoveRight()) {
            fallingBlockLocation.add(new Vector2(1, 0));
            lastMove = TimeUtils.nanoTime();
        }
    }

    /**
     * 可能な場合、落下中のブロックを下に移動
     */
    public void moveDown() {
        if(moveTooFast()) {
            return;
        }
        if(canFallMore()) {
            fallingBlockLocation.add(new Vector2(0, -1));
            lastMove = TimeUtils.nanoTime();
            if(timeForAutoDrop()) {
                lastAutoDrop = TimeUtils.nanoTime();
            }
        } else { //これ以上下に移動できない場合
            //落下中のブロックをgridに追加
            for(Vector2 coord : translateBlockCoords(fallingBlock, fallingBlockLocation)) {
                if(coord.y < gridHeight && coord.x < gridWidth)
                    grid[(int)coord.y][(int)coord.x] = true;
            }
            //行をチェック
            removeCompletedLines();
            //新しい落下ブロックを作成
            newFallingBlock();
        }
    }

    /**
     * ブロックで埋まった行を削除し、削除した数を返却
     * @return
     */
    private int removeCompletedLines() {
        int numCompleted = 0;
        for(int rowNum = 0; rowNum < gridHeight; rowNum++) {
            boolean complete = true;
            for(int colNum = 0; colNum < gridWidth; colNum++) {
                if(!grid[rowNum][colNum]) {
                    complete = false;
                    break;
                }
            }
            if(complete) {
                numCompleted++;
                //削除した行より上の行を一段下に移動する
                for(int aboveRowNum = rowNum + 1; aboveRowNum < gridHeight; aboveRowNum++) {
                    for(int colNum = 0; colNum < gridWidth; colNum++) {
                        grid[aboveRowNum - 1][colNum] = grid[aboveRowNum][colNum];
                    }
                }
                //現在の行を再チェック
                rowNum--;
            }
        }
        return numCompleted;
    }

    /**
     * 可能な場合、落下中のブロックを時計回りに回転させる
     */
    public void rotateClockwise() {
        if(canRotateClockwise()) {
            fallingBlock.rotateClockwise();
            lastMove = TimeUtils.nanoTime();
        }
    }

    /**
     * 可能な場合、落下中のブロックを反時計回りに回転させる
     */
    public void rotateCounterClockwise() {
        if(canRotateCounterClockwise()) {
            fallingBlock.rotateCounterClockwise();
            lastMove = TimeUtils.nanoTime();
        }
    }

    /**
     * 落下中のブロックが既存のブロックに衝突するかどうか、
     *
     * @param translation
     * @return
     */
    private boolean doseBlockCollide(Vector2 translation) {
        Array<Vector2> blockCoords = translateBlockCoords(fallingBlock, fallingBlockLocation);
        for(Vector2 coord : blockCoords) {
            coord = coord.cpy();
            coord.add(translation);
            //画面の制約を確認
            if(coord.y < 0 || coord.x < 0 || coord.x > gridWidth - 1) {
                return true;
            }
            //既存のブロックとの衝突をチェック
            if(coord.y < gridHeight && grid[Math.round(coord.y)][Math.round(coord.x)]) {
                return true;
            }
        }
        return false;
    }

    /**
     * 落下中のブロックがまだ落下できるかどうか
     * @return
     */
    private boolean canFallMore() {
        return !doseBlockCollide(new Vector2(0, -1));
    }

    /**
     * 落下中のブロックが左に移動できるかどうか
     * @return
     */
    private boolean canMoveLeft() {
        return !doseBlockCollide(new Vector2(-1, 0));
    }

    /**
     * 落下中のブロックが右に移動できるかどうか
     * @return
     */
    private boolean canMoveRight() {
        return !doseBlockCollide(new Vector2(1, 0));
    }

    /**
     * 落下中のブロックが時計回りに回転できるかどうか
     * @return
     */
    private boolean canRotateClockwise() {
        //ブロックを回転させて衝突するか確認し、回転させて返却
        fallingBlock.rotateCounterClockwise();
        boolean check = doseBlockCollide(new Vector2(0, 0));
        fallingBlock.rotateClockwise();
        return !check;
    }

    /**
     * 落下中のブロックが反時計回りに回転できるかどうか
     * @return
     */
    private boolean canRotateCounterClockwise() {
        //ブロックを回転させて衝突するか確認し、回転させて返却
        fallingBlock.rotateClockwise();
        boolean check = doseBlockCollide(new Vector2(0, 0));
        fallingBlock.rotateCounterClockwise();
        return !check;
    }

    public Array<BlockDrawable> getGridBlocksToDraw(){
        Array<BlockDrawable> blocks = new Array<BlockDrawable>();
        int rowNum = 0;
        for(boolean[] row : grid) {
            int colNum = 0;
            for(boolean block : row) {
                if(block) {
                    blocks.add(new BlockDrawable(colNum, rowNum, Tetromino.colors.GREEN));
                }
                colNum++;
            }
            rowNum++;
        }
        if(fallingBlock != null) {
            for(Vector2 block : translateBlockCoords(fallingBlock, fallingBlockLocation)) {
                if(block.y <= 19 && block.x <= 9) {
                    blocks.add(new BlockDrawable(Math.round(block.x), Math.round(block.y), Tetromino.colors.BLUE));
                }
            }
        }
        return blocks;
    }

    /**
     * grid更新の繰り返し処理
     * @param delta
     */
    public void update(float delta) {
        if(timeForAutoDrop()) {
            lastMove = 0;
            moveDown();
        }
    }

}

