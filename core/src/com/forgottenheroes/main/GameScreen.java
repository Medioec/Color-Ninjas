package com.forgottenheroes.main;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.audio.Music;
//import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.forgottenheroes.main.objects.Leaderboard;
import com.forgottenheroes.main.objects.Map;
import com.forgottenheroes.main.objects.ObjectManager;
import com.forgottenheroes.main.objects.Scoreboard;

public class GameScreen extends DisplayScreen {
	
	/*Texture dropImage;
	Texture bucketImage;
	//Sound dropSound;
	//Music rainMusic;
	
	Rectangle bucket;
	Array<Rectangle> raindrops;
	long lastDropTime;
	int dropsGathered;*/

	public GameScreen(final FHeroes game, Viewport viewport, OrthographicCamera camera) {
		super(game, viewport, camera);
		game.setShapeRenderer(new ShapeRenderer());
		game.getShapeRenderer().setAutoShapeType(true);
		FHeroes.getObjectManager().setMap(new Map());
		FHeroes.getObjectManager().setLeaderboard(new Leaderboard());
		FHeroes.getObjectManager().setScoreboard(new Scoreboard());
		FHeroes.getObjectManager().getMap().generateMap1();

		// load the images for the droplet and the bucket, 64x64 pixels each
		//dropImage = new Texture(Gdx.files.internal("droplet.png"));
		//bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		// load the drop sound effect and the rain background "music"
		//dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		//rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		//rainMusic.setLooping(true);

		// create the camera and the SpriteBatch
		//camera = new OrthographicCamera();
		//camera.setToOrtho(false, 800, 480);

		// create a Rectangle to logically represent the bucket
		//bucket = new Rectangle();
		//bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
		//bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
						// the bottom screen edge
		//bucket.width = 64;
		//bucket.height = 64;

		// create the raindrops array and spawn the first raindrop
		//raindrops = new Array<Rectangle>();
		//spawnRaindrop();

	}

	//private void spawnRaindrop() {
		//Rectangle raindrop = new Rectangle();
		//raindrop.x = MathUtils.random(0, 800 - 64);
		//raindrop.y = 480;
		//raindrop.width = 64;
		//raindrop.height = 64;
		//raindrops.add(raindrop);
		//lastDropTime = TimeUtils.nanoTime();
	//}

	@Override
	public void render(float delta) {
		// clear the screen with a dark blue color. The
		// arguments to clear are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		ScreenUtils.clear(0, 0, 0.5f, 0);
		getViewport().apply();
		//ScreenUtils.clear(0.4f, 0.6f, 1f, 1);
		// tell the camera to update its matrices.
		getOrthographicCamera().update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.getSpriteBatch().setProjectionMatrix(getOrthographicCamera().combined);
		// begin a new batch and draw the bucket and
		// all drops
		//game.getSpriteBatch().begin();
		//FHeroes.font.draw(FHeroes.batch, "Drops Collected: ", 0, 480);
		/*game.batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle raindrop : raindrops) {
			game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}*/
		//game.getSpriteBatch().end();
		Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		game.getShapeRenderer().setProjectionMatrix(getOrthographicCamera().combined);
		game.getShapeRenderer().begin();
		game.getShapeRenderer().set(ShapeType.Filled);
		game.getShapeRenderer().setColor(0.4f, 0.6f, 1, 1);
		game.getShapeRenderer().rect(0, 0, FHeroes.INIT_WIDTH, FHeroes.INIT_HEIGHT);
		game.getShapeRenderer().end();
		FHeroes.getObjectManager().render(game);
		// process user input
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			getOrthographicCamera().unproject(touchPos);
			//bucket.x = touchPos.x - 64 / 2;
		}
		//if (Gdx.input.isKeyPressed(Keys.LEFT)){}
			//bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		//if (Gdx.input.isKeyPressed(Keys.RIGHT)){}
			//bucket.x += 200 * Gdx.graphics.getDeltaTime();
		/*
		// make sure the bucket stays within the screen bounds
		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > 800 - 64)
			bucket.x = 800 - 64;

		// check if we need to create a new raindrop
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnRaindrop();

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.
		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0)
				iter.remove();
			if (raindrop.overlaps(bucket)) {
				dropsGathered++;
				//dropSound.play();
				iter.remove();
			}
		}*/
	}

	@Override
	public void resize(int width, int height) {
		getViewport().update(width, height);
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		//rainMusic.play();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		//dropImage.dispose();
		//bucketImage.dispose();
		//dropSound.dispose();
		//rainMusic.dispose();
	}

}