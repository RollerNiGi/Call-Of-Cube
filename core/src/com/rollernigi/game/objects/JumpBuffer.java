package com.rollernigi.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rollernigi.game.BasicClass.Assets;

public class JumpBuffer extends AbstractGameObject {

    private TextureRegion regJumpBuffer;

    public boolean collected;

    public JumpBuffer(){
        init();
    }

    public void init(){
        dimension.set(1f,1f);

        regJumpBuffer= Assets.instance.assetJumpBuffer1.JumpBuffer1;

        //设置边界矩形的尺寸
        bounds.set(0,0,dimension.x,dimension.y);

        collected=false;
    }
    @Override
    public void render(SpriteBatch batch) {
        if (collected)return;
        TextureRegion reg = null;
        reg=regJumpBuffer;
        batch.draw(reg.getTexture(),position.x,position.y,origin.x,origin.y,dimension.x,dimension.y,scale.x,scale.y,
                rotation,reg.getRegionX(),reg.getRegionY(),reg.getRegionWidth(),reg.getRegionHeight(),false,false);
    }

    public int getScore(){
        return 64;
    }

}
