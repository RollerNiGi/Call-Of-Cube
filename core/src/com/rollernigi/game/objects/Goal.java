package com.rollernigi.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rollernigi.game.BasicClass.Assets;

public class Goal extends AbstractGameObject {

    private TextureRegion regGoal;

    public Goal(){
        init();
    }

    private void init() {
        dimension.set(2.0f,3.0f);
        regGoal = Assets.instance.assetLevelDecoration.goal;

        //set bounds of Rectangle
        bounds.set(1,Float.MIN_VALUE,10,Float.MAX_VALUE);
        origin.set(dimension.x/2.0f,0.0f);
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;

        reg=regGoal;
        batch.draw(reg.getTexture(),position.x-origin.x,position.y-origin.y,origin.x,origin.y,dimension.x,dimension.y,
                scale.x,scale.y,rotation,reg.getRegionX(),reg.getRegionY(),reg.getRegionWidth(),reg.getRegionHeight(),false,false);
    }
}
