package com.rollernigi.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rollernigi.game.BasicClass.Assets;

public class FallBreak extends AbstractGameObject {

    private TextureRegion regFallBreak;

    public FallBreak(){
        init();
    }

    private void init (){
        dimension.set(0.25f,0.5f);

        regFallBreak= Assets.instance.assetLevelDecoration.smallPart;

        bounds.set(0,0,dimension.x,dimension.y);
        origin.set(dimension.x/2,dimension.y/2);
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg=null;

        reg=regFallBreak;
        batch.draw(reg.getTexture(),position.x=origin.x,position.y-origin.y,origin.x,origin.y,
                dimension.x,dimension.y,scale.x,scale.y,rotation,reg.getRegionX(),reg.getRegionY(),reg.getRegionWidth(),reg.getRegionHeight(),false,false);

    }
}
