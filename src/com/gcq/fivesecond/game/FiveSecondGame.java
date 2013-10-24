package com.gcq.fivesecond.game;

import aurelienribon.tweenengine.equations.Bounce;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputAdapter;
import com.gcq.fivesecond.FiveSecondApplication;
import com.gcq.fivesecond.core.AppStats;
import com.gcq.fivesecond.database.DBManager;
import com.gcq.fivesecond.definition.AppEvents;
import com.gcq.fivesecond.definition.AppMusicDefinitions;
import com.gcq.fivesecond.definition.AppSoundDefinitions;
import com.gcq.fivesecond.definition.AppTextureDefinitions;
import com.gcq.fivesecond.music.MusicCache;
import com.gcq.fivesecond.scene.GameOverScene;
import com.gcq.fivesecond.scene.GameScene;
import com.gcq.fivesecond.scene.MenuScene;
import com.gcq.fivesecond.scene.SettingsScene;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.event.ActorEvent;
import com.netthreads.libgdx.event.ActorEventObserver;
import com.netthreads.libgdx.scene.Scene;
import com.netthreads.libgdx.scene.transition.MoveInBTransitionScene;
import com.netthreads.libgdx.scene.transition.MoveInLTransitionScene;
import com.netthreads.libgdx.scene.transition.MoveInRTransitionScene;
import com.netthreads.libgdx.scene.transition.MoveInTTransitionScene;
import com.netthreads.libgdx.scene.transition.TransitionScene;
import com.netthreads.libgdx.sound.SoundCache;
import com.netthreads.libgdx.texture.TextureCache;

public class FiveSecondGame extends InputAdapter implements
		ApplicationListener, ActorEventObserver {

	FiveSecondApplication app;
	public static final String VERSION_TEXT = "1.0.0";
	private static final int DURATION_SETTINGS_TRANSITION = 700;
	private static final int DURATION_ABOUT_TRANSITION = 700;
	private static final int DURATION_GAME_TRANSITION = 900;
	private static final int DURATION_GAME_OVER_TRANSITION = 700;
	private static final int DURATION_MENU_TRANSITION = 900;

	private GameScene gameScene;
	private GameOverScene gameOver;
	private MenuScene menuScene;
	private SettingsScene settingsScene;

	private Director director;

	private TextureCache textureCache;
	private SoundCache soundCache;
	private MusicCache musicCache;
	private AppStats appStats;
	
	public static DBManager dbm;

	public FiveSecondGame() {
	}

	public FiveSecondGame(FiveSecondApplication app) {
		this.app = app;
		dbm=new DBManager(app);
	}

	@Override
	public void resize(int arg0, int arg1) {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		// Graphics.
		textureCache.dispose();

		// Sounds.
		soundCache.dispose();

		// Graphics
		director.dispose();
		
		musicCache.dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void create() {
		director = AppInjector.getInjector().getInstance(Director.class);
		textureCache = AppInjector.getInjector()
				.getInstance(TextureCache.class);
		soundCache = AppInjector.getInjector().getInstance(SoundCache.class);
		musicCache= AppInjector.getInjector().getInstance(MusicCache.class);
		appStats = AppInjector.getInjector().getInstance(AppStats.class);

		// Set initial width and height.
		director.setWidth(this.app.getScreenWidth());
		director.setHeight(this.app.getScreenHeight());

		// Add this as an event observer.
		director.registerEventHandler(this);

		// Load/Re-load textures
		textureCache.load(AppTextureDefinitions.TEXTURES);

		// Load/Re-load sounds.
		soundCache.load(AppSoundDefinitions.SOUNDS);
		
		musicCache.load(AppMusicDefinitions.MUSICS);

		menuScene = getMenuScene();

		director.setScene(menuScene);
	}
	
	@Override
	public void render() {
		director.update();
	}

	@Override
	public boolean handleEvent(ActorEvent event) {
		boolean handled = false;
		switch (event.getId()) {
		// case AppEvents.EVENT_DISPLAY_SPLASH_SCREEN:
		// displaySplashScene();
		// handled = true;
		// break;
		case AppEvents.EVENT_TRANSITION_TO_MENU_SCENE:
			transitionToMenuScene();
			handled = true;
			break;
		case AppEvents.EVENT_TRANSITION_TO_SETTINGS_SCENE:
			transitionToSettingsScene();
			handled = true;
			break;
		case AppEvents.EVENT_TRANSITION_TO_GAME_SCENE:
			transitionToGameScene();
			handled = true;
			break;
		case AppEvents.EVENT_TRANSITION_TO_GAME_OVER_SCENE:
			transitionToGameOverScene();
			handled = true;
			break;
		// case AppEvents.EVENT_TRANSITION_TO_ABOUT_SCENE:
		// transitionToAboutScene();
		// handled = true;
		// break;
		default:
			break;
		}
		return handled;
	}

	/**
	 * Run transition.
	 * 
	 */
	private synchronized void transitionToGameScene() {
		// Reset scores.
		appStats.resetScore();

		Scene inScene = getGameScene();
		Scene outScene = director.getScene();
		if(outScene instanceof MoveInBTransitionScene){
			System.out.println(inScene+" "+outScene+"　to game");
			return;
		}
		TransitionScene transitionScene = MoveInBTransitionScene.$(inScene,
				outScene, DURATION_GAME_TRANSITION, Linear.INOUT);

		director.setScene(transitionScene);
	}

	private synchronized void transitionToGameOverScene() {
		Scene inScene = getGameOverScene();
		Scene outScene = director.getScene();
		if(outScene instanceof MoveInLTransitionScene){
			return;
		}
		TransitionScene transitionScene = MoveInLTransitionScene.$(inScene,
				outScene, DURATION_GAME_OVER_TRANSITION, Bounce.OUT);

		director.setScene(transitionScene);
	}

	/**
	 * Run transition to menu.
	 * 
	 */
	private synchronized void transitionToMenuScene() {
		Scene inScene = getMenuScene();
		Scene outScene = director.getScene();
		if(outScene instanceof MoveInRTransitionScene|| outScene instanceof MoveInTTransitionScene){
			return;
		}
		TransitionScene transitionScene = null;
		if (outScene instanceof GameScene) {
			transitionScene = MoveInTTransitionScene.$(inScene, outScene,
					DURATION_MENU_TRANSITION, Linear.INOUT);
		} else {
			if (outScene instanceof SettingsScene) {
				transitionScene = MoveInRTransitionScene.$(inScene, outScene,
						DURATION_SETTINGS_TRANSITION, Bounce.OUT);
			} else {
				transitionScene = MoveInLTransitionScene.$(inScene, outScene,
						DURATION_ABOUT_TRANSITION, Bounce.OUT);
			}
		}

		director.setScene(transitionScene);
	}

	/**
	 * Run transition.
	 * 
	 */
	private synchronized void transitionToSettingsScene() {
		Scene inScene = getSettingsScene();
		Scene outScene = director.getScene();
		if(outScene instanceof MoveInLTransitionScene){
			return;
		}
		TransitionScene transitionScene = MoveInLTransitionScene.$(inScene,
				outScene, DURATION_SETTINGS_TRANSITION, Bounce.OUT);

		director.setScene(transitionScene);
	}

	public GameScene getGameScene() {
		if (gameScene == null) {
			gameScene = new GameScene();
		}

		return gameScene;
	}

	public GameOverScene getGameOverScene() {
		if (gameOver == null) {
			gameOver = new GameOverScene();
		}
		return gameOver;
	}

	public MenuScene getMenuScene() {
		if (menuScene == null) {
			menuScene = new MenuScene();
		}

		return menuScene;
	}

	public SettingsScene getSettingsScene() {
		if (settingsScene == null) {
			settingsScene = new SettingsScene();
		}

		return settingsScene;
	}

}
