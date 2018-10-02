package com.rollernigi.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rollernigi.game.BasicClass.Assets;
import com.rollernigi.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;


public class Jumper extends AbstractGameObject{

    public static final String TAG = Jumper.class.getName();

    private final float JUMP_TIME_MAX = 0.3f;
    private final float JUMP_TIME_MIN = 0.1f;
    private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;

    public ParticleEffect dustParticles = new ParticleEffect();

    public enum  VIEW_DIRECTION{
        LEFT,RIGHT;
    }

    public enum JUMP_STATE{
        GROUNDED,FALLING,JUMP_RISING,JUMP_FALLING
    }

    private TextureRegion regJumper;

    public VIEW_DIRECTION viewDirection;
    public float timeJumping;
    public JUMP_STATE jumpState;
    public boolean hasJumpBufferPowerup;
    public float timeLeftJumpBufferPowerup;

    public Jumper(){
        init();
    }

    public void init(){
        dimension.set(1f,1f);
        regJumper = Assets.instance.assetJumper1.Jumper1;
        origin.set(dimension.x/2,dimension.y/2);
        bounds.set(0,0,dimension.x,dimension.y);
        terminalVelocity.set(3.0f,4.0f);
        friciton.set(8.0f,0.0f);
        acceleration.set(0.0f,-10f);
        viewDirection = VIEW_DIRECTION.RIGHT;
        jumpState =JUMP_STATE.FALLING;
        timeJumping=0;
        hasJumpBufferPowerup = false;
        timeLeftJumpBufferPowerup =0;

        //粒子特效
        dustParticles.load(Gdx.files.internal("particles/dust.pfx"),Gdx.files.internal("particles"));

    }
    public void setJumping(boolean jumpKeyPressed){
        switch (jumpState){
            case GROUNDED:
                if(jumpKeyPressed){
                    timeJumping = 0;
                    jumpState = JUMP_STATE.JUMP_RISING;
                }
                break;
            case JUMP_RISING:
                if(!jumpKeyPressed){
                    jumpState = JUMP_STATE.JUMP_FALLING;
                }
                break;
            case FALLING:
            case JUMP_FALLING:
                if (jumpKeyPressed&&hasJumpBufferPowerup){
                    timeJumping = JUMP_TIME_OFFSET_FLYING;
                    jumpState=JUMP_STATE.JUMP_RISING;
                }
                break;
        }
    }
    public void setJumpBufferPowerup(boolean pickedUp){
        hasJumpBufferPowerup = pickedUp;
        if(pickedUp){
            timeLeftJumpBufferPowerup = Constants.ITEM_JUMPBUFFER_POWERUP_DURATION;
        }
    }
    public boolean hasJumpBufferPowerup(){
        return hasJumpBufferPowerup&&timeLeftJumpBufferPowerup>0;
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        if(velocity.x!=0){
            viewDirection=velocity.x<0?VIEW_DIRECTION.LEFT:VIEW_DIRECTION.RIGHT;
        }
        if(timeLeftJumpBufferPowerup>0){
            timeLeftJumpBufferPowerup -= deltaTime;
            if(timeLeftJumpBufferPowerup<0){
                timeLeftJumpBufferPowerup=0;
                setJumpBufferPowerup(false);
            }
        }
        dustParticles.update(deltaTime);
    }

    @Override
    protected void updateMotionY(float deltaTime){
        switch (jumpState){
            case GROUNDED:
                jumpState = JUMP_STATE.FALLING;
                if(velocity.x!=0){
                    dustParticles.setPosition(position.x+dimension.x/2,position.y);
                }
                break;
            case JUMP_RISING:
                timeJumping+=deltaTime;
                if(timeJumping<=JUMP_TIME_MAX){
                    velocity.y=terminalVelocity.y;
                }
                break;
            case FALLING:
                break;
            case JUMP_FALLING:
                timeJumping +=deltaTime;
                if(timeJumping>0&&timeJumping<=JUMP_TIME_MIN){
                    velocity.y = terminalVelocity.y;
                }
        }
        if(jumpState!=JUMP_STATE.GROUNDED){
            dustParticles.allowCompletion();
            super.updateMotionY(deltaTime);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;

        dustParticles.draw(batch);

        if(hasJumpBufferPowerup){
            batch.setColor(0.4f,0.4f,1.0f,1.0f);
        }
        reg = regJumper;
        batch.draw(reg.getTexture(),position.x,position.y,origin.x,origin.y,dimension.x,dimension.y,scale.x,scale.y,rotation,
                reg.getRegionX(),reg.getRegionY(),reg.getRegionWidth(),reg.getRegionHeight(),viewDirection==VIEW_DIRECTION.LEFT,false);
        batch.setColor(1,1,1,1);
    }

}
