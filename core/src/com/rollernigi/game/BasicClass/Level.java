package com.rollernigi.game.BasicClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rollernigi.game.objects.AbstractGameObject;
import com.rollernigi.game.objects.Coin;
import com.rollernigi.game.objects.JumpBuffer;
import com.rollernigi.game.objects.FallBreak;
import com.rollernigi.game.objects.Goal;
import com.rollernigi.game.objects.Jumper;
import com.rollernigi.game.objects.Rock;
import com.badlogic.gdx.utils.Array;

public class Level {
    public static final String TAG=Level.class.getName();
    public Jumper jumper;
    public Array<Coin> coins;
    public Array<FallBreak> fallBreaks;
    public Goal goal;
    public Array<JumpBuffer> jumpBuffers;
    public enum BLOCK_TYPE{
        EMPTY(0,0,0),//黑色
        ROCK(0,255,0),//绿色
        GOAL(255,0,0),//红色
        PLAYER_SPAWNPOINT(255,255,255),//白色
        ITEM_JUMPBUFFER(255,0,255),//紫色
        ITEM_COIN(255,255,0);//黄色

        private int color;

        private BLOCK_TYPE(int r,int g,int b){
            color = r<<24|g<<16|b<<8|0xff;
        }

        public boolean sameColor(int color){
            return this.color==color;
        }

        public int getColor(){
            return color;
        }

    }

    //游戏对象
    public Array<Rock> rocks;
    private float rockOffsetHeight=-5;

    public Level(String filename){
        init(filename);
    }

    private void init(String filename){
        //玩家角色
        jumper =null;

        //游戏对象
        rocks = new Array<Rock>();
        coins = new Array<Coin>();
        jumpBuffers = new Array<JumpBuffer>();
        //加载卡关图片
        Pixmap pixmap=new Pixmap(Gdx.files.internal(filename));

        //从图片的左上角逐行扫描直到右下角
        int lastPixel = -1;
        for(int pixelY=0;pixelY<pixmap.getHeight();pixelY++){
            for( int pixelX = 0; pixelX<pixmap.getWidth();pixelX++){
                AbstractGameObject obj =null;
                float offsetHeight  = 0;
                //计算底部高度
                float baseHeight = pixmap.getHeight() - pixelY;
                //获取当前位置的RGB值
                int currentPixel = pixmap.getPixel(pixelX,pixelY);
                //找到与当前位置(x,y)颜色匹配的代码块并创建相应的对象

                //空白空间
                if(BLOCK_TYPE.EMPTY.sameColor(currentPixel)){

                }

                //rock对象
                else if(BLOCK_TYPE.ROCK.sameColor(currentPixel)){
                    if(lastPixel!=currentPixel){
                        obj = new Rock();
                        offsetHeight = rockOffsetHeight;
                        obj.position.set(pixelX,baseHeight*obj.dimension.y+offsetHeight);
                        rocks.add((Rock)obj);
                    }else {
                        rocks.get(rocks.size-1).increaseLength(1);
                    }
                }
                //结束路标
                else if (BLOCK_TYPE.GOAL.sameColor(currentPixel)){
                    obj = new Goal();
                    offsetHeight = -7.0f;
                    obj.position.set(pixelX,baseHeight+offsetHeight);
                    goal = (Goal)obj;
                }

                //玩家出生点
                else if(BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)){
                    obj = new Jumper();
                    offsetHeight = rockOffsetHeight;
                    obj.position.set(pixelX,baseHeight*obj.dimension.y+offsetHeight);
                    jumper = (Jumper)obj;
                }
                //JUMPBUFFER
                else if(BLOCK_TYPE.ITEM_JUMPBUFFER.sameColor(currentPixel)){
                    obj = new JumpBuffer();
                    offsetHeight = rockOffsetHeight;
                    obj.position.set(pixelX,baseHeight*obj.dimension.y+offsetHeight);
                    jumpBuffers.add((JumpBuffer)obj);
                }
                //Coin
                else if(BLOCK_TYPE.ITEM_COIN.sameColor(currentPixel)){
                    obj = new Coin();
                    offsetHeight = rockOffsetHeight;
                    obj.position.set(pixelX,baseHeight*obj.dimension.y+offsetHeight);
                    coins.add((Coin)obj);
                }
                else {
                    int r = 0xff&(currentPixel>>24);    //red通道
                    int g = 0xff&(currentPixel>>16);    //green通道
                    int b = 0xff&(currentPixel>>8);     //blue通道
                    int a = 0xff&(currentPixel);

                    Gdx.app.error(TAG,"Unkown object at x<"+pixelX+">y<"+pixelY+">:"+r+g+b);
                }
                lastPixel = currentPixel;
            }

        }
        pixmap.dispose();
        Gdx.app.debug(TAG,"level'"+filename+"'load");
    }

    public void render(SpriteBatch batch){
        goal.render(batch);
        //渲染游戏对象
        for(Rock rock : rocks){
            rock.render(batch);
        }
        for (Coin coin:coins){
            coin.render(batch);
        }
        for(FallBreak fallBreak:fallBreaks){
            fallBreak.render(batch);
        }
        for(JumpBuffer jumpBuffer:jumpBuffers){
            jumpBuffer.render(batch);
        }
        jumper.render(batch);
    }

    public void update(float deltaTime){
        jumper.update(deltaTime);
        for(Rock rock : rocks){
            rock.update(deltaTime);
        }
        for (Coin coin:coins){
            coin.update(deltaTime);
        }
        for(FallBreak fallBreak:fallBreaks){
            fallBreak.update(deltaTime);
        }
        for(JumpBuffer jumpBuffer:jumpBuffers){
            jumpBuffer.update(deltaTime);
        }
    }

}
