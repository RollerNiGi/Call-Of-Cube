package com.rollernigi.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rollernigi.game.BasicClass.Assets;

public class Coin extends AbstractGameObject {

    private TextureRegion regCoin;

    public boolean collected;

    public Coin(){
        init();
    }

    private void init(){
        dimension.set(1f,1f);

        regCoin = Assets.instance.assetCoin1.Coin1;

        //设置碰撞检测的矩形边界

        bounds.set(0,0,dimension.x,dimension.y);

        collected = false;
    }

    @Override
    public void render(SpriteBatch batch) {
        if(collected) return;

        TextureRegion reg = null;
        reg = regCoin;
        batch.draw(reg.getTexture(),position.x,position.y,origin.x,origin.y,dimension.x,dimension.y,scale.x,scale.y,
                rotation,reg.getRegionX(),reg.getRegionY(),reg.getRegionWidth(),reg.getRegionHeight(),false,false);
    }

    public int getScore(){
        return 24;
    }
}
