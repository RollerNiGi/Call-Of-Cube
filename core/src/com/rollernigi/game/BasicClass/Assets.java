package com.rollernigi.game.BasicClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.rollernigi.game.util.Constants;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable,AssetErrorListener {

    public AssetFont fonts;
    public class  AssetFont{
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;

        public AssetFont(){

            //创建三个15磅的位图字体
            defaultSmall = new BitmapFont(Gdx.files.internal("images/RegularFont.fnt"),true);
            defaultNormal = new BitmapFont(Gdx.files.internal("images/RegularFont.fnt"),true);
            defaultBig = new BitmapFont(Gdx.files.internal("images/RegularFont.fnt"),true);

            //设置字体尺寸
            defaultSmall.getData().setScale(0.85f);
            defaultNormal.getData().setScale(1.10f);
            defaultBig.getData().setScale(1.35f);

            //为字体激活线性纹理过滤模式

        }
    }



    public AssetJumpBuffer1 assetJumpBuffer1;
    public AssetCoin1 assetCoin1;
    public AssetBlock1 assetBlock1;
    public AssetJumper1 assetJumper1;
    public AssetGamepad assetGamepad;

    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    //单例类，防止在别的类中实例化
    private Assets(){}

    public void init(AssetManager assetManager){
        this.assetManager=assetManager;
        //设置资源管理器的错误处理对象
        assetManager.setErrorListener(this);
        //预加载纹理集资源
        assetManager.load(Constants.TETURE_ATLAS_OBJECTS, TextureAtlas.class);
        //开始加载资源
        assetManager.finishLoading();
        Gdx.app.debug(TAG,"#of assets loaded"+assetManager.getAssetNames().size);
        for(String a:assetManager.getAssetNames()){
            Gdx.app.debug(TAG,"assets:"+a);
        }
        TextureAtlas atlas = assetManager.get(Constants.TETURE_ATLAS_OBJECTS);

        //激活平滑纹理过滤
        for(Texture t:atlas.getTextures()){
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        fonts = new AssetFont();
        //创建游戏对象
        assetJumpBuffer1 = new AssetJumpBuffer1(atlas);
        assetJumper1 = new AssetJumper1(atlas);
        assetBlock1 = new AssetBlock1(atlas);
        assetCoin1 = new AssetCoin1(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.debug(TAG,"Couldn't load asset'"+asset.fileName+"'",(Exception)throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        fonts.defaultBig.dispose();
        fonts.defaultNormal.dispose();
        fonts.defaultSmall.dispose();
    }

    public class AssetJumper1{
        public final AtlasRegion Jumper1;

        public AssetJumper1(TextureAtlas atlas){
            Jumper1 = atlas.findRegion("Jumper1");
        }
    }

    public class AssetBlock1{
        public final AtlasRegion Block1,Block2;

        public AssetBlock1(TextureAtlas atlas){
            Block1 = atlas.findRegion("Block1");
            Block2 = atlas.findRegion("Block2") ;
        }
    }

    public class AssetCoin1{
        public final AtlasRegion Coin1;

        public AssetCoin1(TextureAtlas atlas){
            Coin1 = atlas.findRegion("Coin1");
        }
    }

    public class AssetJumpBuffer1{
        public final AtlasRegion JumpBuffer1;

        public AssetJumpBuffer1(TextureAtlas atlas){
            JumpBuffer1 = atlas.findRegion("JumpBuffer1");
        }
    }

    public class AssetGamepad{
        public final AtlasRegion GamepadBG;
        public final AtlasRegion GamepadKN;

        public AssetGamepad(TextureAtlas atlas){
            GamepadBG = atlas.findRegion("GamepadBackground");
            GamepadKN = atlas.findRegion("GamepadKnob");
        }
    }

}
