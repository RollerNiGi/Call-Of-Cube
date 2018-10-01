package com.rollernigi.game.BasicClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;
import com.rollernigi.game.util.Constants;

public class WorldRenderer implements Disposable {

    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private SpriteBatch batch;
    private WorldController worldController;

    public WorldRenderer(WorldController worldController){
        this.worldController = worldController;
        init();
    }

    private void init(){
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        camera.position.set(0,0,0);
        camera.update();
        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        cameraGUI.position.set(0,0,0);
        cameraGUI.setToOrtho(true);
        cameraGUI.update();
    }

    public void render(){
        renderWrold(batch);
        renderGUI(batch);
    }


    public void resize(int width,int height){
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT/height)*width;
        camera.update();
        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HTIGHT;
        cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HTIGHT/(float)height)*(float)width;
        cameraGUI.position.set(cameraGUI.viewportWidth/2,cameraGUI.viewportHeight/2,0);
        cameraGUI.update();
}

    private void renderWrold(SpriteBatch batch){
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.level.render(batch);
        batch.end();
    }

    private void renderGuiGameOverMessage(SpriteBatch batch){
        float x = cameraGUI.viewportWidth/2;
        float y = cameraGUI.viewportHeight/2;
        if(worldController.isGameOver()){
            BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
            fontGameOver.setColor(1,0.25f,0.25f,1);
            fontGameOver.draw(batch,"Game Over",x-95,y);
            fontGameOver.setColor(1,1,1,1);
        }
    }

    private void renderGuiJumpBufferPowerUp(SpriteBatch batch){
        float x =-15;
        float y = 30f;
        float timeLeftJumpBufferPowerUp = worldController.level.jumper.timeLeftJumpBufferPowerup;
        if(timeLeftJumpBufferPowerUp>0){
            if(timeLeftJumpBufferPowerUp<4){
                if(((int)(timeLeftJumpBufferPowerUp*5)%2)!=0){
                    batch.setColor(1,1,1,0.5f);
                }
            }
            batch.draw(Assets.instance.assetJumpBuffer1.JumpBuffer1,x,y,50,50,100,100,0.35f,-0.35f,0);
            batch.setColor(1,1,1,1);
            BitmapFont fontTimeJumpPowerUp = Assets.instance.fonts.defaultBig;
            fontTimeJumpPowerUp.setColor(0.55f,0.55f,0.65f,1);
            fontTimeJumpPowerUp.draw(batch,""+(int)timeLeftJumpBufferPowerUp,x+75,y+40);
            fontTimeJumpPowerUp.setColor(1,1,1,1);
        }
    }

    private void renderGuiScore(SpriteBatch batch){
        float x = -15;
        float y =-15;

        batch.draw(Assets.instance.assetCoin1.Coin1,x,y,50,50,100,100,0.35f,-0.35f,0);
        BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
        fontGameOver.setColor(0.75f,0.75f,0.45f,1);
        fontGameOver.draw(batch,""+worldController.score,x+75,y+40);
        fontGameOver.setColor(1,1,1,1);
    }

    private void renderLeftLives(SpriteBatch batch){
        float x = cameraGUI.viewportWidth-50-Constants.LIVES_START*50;
        float y =-15;
        for (int i=0;i<Constants.LIVES_START;i++){
            if(i>=worldController.lives) batch.setColor(0.5f,0.5f,0.5f,0.5f);
            batch.draw(Assets.instance.assetJumper1.Jumper1,x+i*50,y,50,50,100,100,0.35f,-0.35f,0);
            batch.setColor(1,1,1,1);
        }
    }

    private void renderGUI(SpriteBatch batch){
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        renderGuiJumpBufferPowerUp(batch);
        renderGuiScore(batch);
        renderLeftLives(batch);

        renderGuiGameOverMessage(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
