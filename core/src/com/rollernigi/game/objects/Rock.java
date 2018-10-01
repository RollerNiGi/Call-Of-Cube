package com.rollernigi.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rollernigi.game.BasicClass.Assets;

public class Rock extends AbstractGameObject {

    private TextureRegion regEdge;
    private TextureRegion regMiddle;

    private int length;

    public Rock(){
        init();
    }

    private void init() {
        dimension.set(1,1);
        regEdge = Assets.instance.assetBlock1.Block2;
        regMiddle = Assets.instance.assetBlock1.Block1;
        //设置Block初始长度
        setLength(1);

    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg=null;

        float relX = 0;
        float relY = 0;



        //渲染中间部分
        relX=0;
        reg=regMiddle;
        for(int i=0;i<length;i++){
            batch.draw(reg.getTexture(),position.x+relX,position.y+relY,origin.x,origin.y,dimension.x,dimension.y,scale.x,scale.y,rotation,
            reg.getRegionX(),reg.getRegionY(),reg.getRegionWidth(),reg.getRegionHeight(),false,false);
            relX+=dimension.x;
        }


    }

    public void setLength(int length) {
        this.length = length;
        bounds.set(0,0,dimension.x*length,dimension.y);
    }

    public void increaseLength(int amount){
        setLength(length+amount);
    }
}
