package com.rollernigi.game.BasicClass;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rollernigi.game.objects.Rock;
import com.rollernigi.game.screens.MenuScreen;
import com.rollernigi.game.util.Constants;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.rollernigi.game.objects.JumpBuffer;
import com.rollernigi.game.objects.Jumper;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.rollernigi.game.objects.Jumper.JUMP_STATE;
import com.rollernigi.game.objects.Coin;
import com.badlogic.gdx.math.MathUtils;
import com.rollernigi.game.util.CameraHelper;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class WorldController extends InputAdapter {

    public int touchDownX,touchDownY,debugSelectedSpr=1;
    public Level level;
    public int lives;
    public int score;
    public float scoreViual;
    public Touchpad touchPad;
    public float livesVisual;
    public TouchpadStyle style;
    public TextureRegionDrawable background;
    public TextureRegionDrawable knobRegion;

    private float timeLeftGameOverDelay;
    private Game game;

    public boolean isGameOver(){
        return lives<0;
    }

    public boolean isPalyerFallDown(){
        return level.jumper.position.y<-5.5;

    }

    //用于碰撞检测的临时变量
    private Rectangle r1=new Rectangle();
    private Rectangle r2=new Rectangle();

    private void onCollisionJumperWithRock(Rock rock){
        Jumper jumper = level.jumper;
        float heightDifference = Math.abs(jumper.position.y-(rock.position.y+rock.bounds.height));
        if(heightDifference>0.25f) {
            boolean hitRightEdge = jumper.position.x > (rock.position.x + rock.bounds.width / 2.0f);
            if (hitRightEdge) {
                jumper.position.x = rock.position.x + rock.bounds.width;
            } else{
                jumper.position.x = rock.position.x - jumper.bounds.width;
            }
            return;
        }
        switch (jumper.jumpState){
            case GROUNDED:break;
            case FALLING:
            case JUMP_FALLING:
                jumper.position.y= rock.position.y+jumper.bounds.height;
                jumper.jumpState = JUMP_STATE.GROUNDED;
                break;
            case JUMP_RISING:
                jumper.position.y= rock.position.y+jumper.bounds.height;
                break;
        }

    }


    private void onCollisionJumperWithCoin(Coin coin){
        coin.collected=true;
        score+=coin.getScore();
    }

    private void onCollisionJumperWithJumpBuffer(JumpBuffer jumpBuffer){
        jumpBuffer.collected =true;
        score+=jumpBuffer.getScore();
        level.jumper.setJumpBufferPowerup(true);
    }

    private void testCollisions(){
        r1.set(level.jumper.position.x,level.jumper.position.y,level.jumper.bounds.width,level.jumper.bounds.height);

        //碰撞检测 Jumper <- -> Rocks

        for (Rock rock:level.rocks){
            r2.set(rock.position.x,rock.position.y,rock.bounds.width,rock.bounds.height);
            if(!r1.overlaps(r2))continue;
            onCollisionJumperWithRock(rock);
        }

        //碰撞检测 Jumper<- ->Coins

        for(Coin coin:level.coins){
            if(coin.collected)continue;
            r2.set(coin.position.x,coin.position.y,coin.bounds.width,coin.bounds.height);
            if(!r1.overlaps(r2))continue;
            onCollisionJumperWithCoin(coin);
            break;
        }

        //碰撞检测 Jumper<- -> JumpBuffer

        for (JumpBuffer jumpBuffer:level.jumpBuffers){
            if(jumpBuffer.collected)continue;
            r2.set(jumpBuffer.position.x,jumpBuffer.position.y,jumpBuffer.bounds.width,jumpBuffer.bounds.height);
            if(!r1.overlaps(r2))continue;
            onCollisionJumperWithJumpBuffer(jumpBuffer);
            break;
        }
    }

    public CameraHelper cameraHelper;

    public WorldController(Game game){
        this.game = game;
        init();
    }

    private void init(){
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        lives = Constants.LIVES_START;
        livesVisual = lives;
        timeLeftGameOverDelay=0;
        initLevel();
    }

    private void initLevel(){
        score =0;
        scoreViual = score;
        level = new Level(Constants.Level_00);
        cameraHelper.setTarget(level.jumper);
    }


    public void update(float deltaTime){
        handleDebugInput(deltaTime);
        if(isGameOver()){
            timeLeftGameOverDelay -= deltaTime;
            if(timeLeftGameOverDelay<0)init();
        }else{
            handleDebugInput(deltaTime);
        }

        level.update(deltaTime);
        testCollisions();
        cameraHelper.update(deltaTime);

        if(!isGameOver()&&isPalyerFallDown()){
            lives--;
            if (isGameOver()){
                timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
            }else {
                initLevel();
            }
        }
        if(livesVisual>lives){
            livesVisual=Math.max(lives,livesVisual-1*deltaTime);
        }
        if(scoreViual<score)scoreViual=Math.min(score,scoreViual+170*deltaTime);
    }

    private void handleDebugInput(float deltaTime) {
        float sprMoveSpeed = 5*deltaTime;
        if(Gdx.app.getType()== Application.ApplicationType.Desktop) {
            //控制选中的精灵


        }else if(Gdx.app.getType()== Application.ApplicationType.Android){
            level.jumper.velocity.x=level.jumper.terminalVelocity.x;
        }
        if(Gdx.input.isTouched()||Gdx.input.isKeyPressed((Input.Keys.SPACE))){
            level.jumper.setJumping(true);
        }else {
            level.jumper.setJumping(false);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchDownX = screenX;
        touchDownY = screenY;
        //跟新相机的追踪目标
        if(cameraHelper.hasTarget()){

        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        debugSelectedSpr+=1;
        if (debugSelectedSpr>4){
            debugSelectedSpr=1;
        }
        if(screenX - touchDownX >50){

        }
        if(screenX - touchDownX <-300){


        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode){
        //重置世界
        if(keycode == Input.Keys.R){
            init();
        }else if(keycode == Input.Keys.SPACE){

        }
        return false;
    }

    private void backToMenu(){
        game.setScreen(new MenuScreen(game));
    }

}
