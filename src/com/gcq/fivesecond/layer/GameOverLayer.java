package com.gcq.fivesecond.layer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gcq.fivesecond.core.AppStats;
import com.gcq.fivesecond.database.ScoreRecord;
import com.gcq.fivesecond.definition.AppEvents;
import com.gcq.fivesecond.game.FiveSecondGame;
import com.netthreads.libgdx.director.AppInjector;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.scene.Layer;

public class GameOverLayer extends Layer {

	private Button replay;
	private Button backToMenu;
	private Table table;
	private Skin replay_skin;
	private Skin backtomenu_skin;
	private Label scores;
	private Label highest_sc;
	
	private Director director;
	private AppStats appStats;
	
	private static final String UI_REPLAY_FILE = "data/five-second-ui-replay.json";
	private static final String UI_BACK_TO_MENU_FILE = "data/five-second-ui-backtomenu.json";
	
	public GameOverLayer(float width, float height) {
		setWidth(width);
		setHeight(height);
		replay_skin = new Skin(Gdx.files.internal(UI_REPLAY_FILE));
		backtomenu_skin=new Skin(Gdx.files.internal(UI_BACK_TO_MENU_FILE));
		director = AppInjector.getInjector().getInstance(Director.class);
		appStats = AppInjector.getInjector().getInstance(AppStats.class);
		buildElements();
	}
	
	@Override
	public void enter() {
		int sc=appStats.getScore();
		int dif=FiveSecondGame.dbm.getProperties().getDifficulty();
		ScoreRecord sr=FiveSecondGame.dbm.getScoreRecord(dif);
		int hsc=sr.getHighest_score();
		if(hsc<sc){
			sr=FiveSecondGame.dbm.updateScore(dif,sc);
			hsc=sc;
		}
		highest_sc.setText("最高分:"+hsc+" ("+sr.getScore_record_data()+")");
		scores.setText(sc+"");
	}
	
	private void buildElements()
	{
		replay=new TextButton("  重新开始", replay_skin);
		replay.padBottom(5f);
		backToMenu=new TextButton("  返回菜单", backtomenu_skin);
		backToMenu.padBottom(5f);
		table = new Table();
		scores= new Label("",replay_skin,"over-score-font",Color.WHITE);
		highest_sc = new Label(" ", replay_skin,"highest-score",Color.WHITE);
		table.setSize(getWidth(), getHeight());
		table.add(scores).height(200).padBottom(30f);
		table.row();
		table.add(highest_sc).padBottom(20f);
		table.row();
		table.add(replay).padBottom(20f);
		table.row();
		table.add(backToMenu);
		table.setTransform(true);
		table.setOrigin(table.getPrefWidth() / 2, table.getPrefHeight() / 2);
		
		replay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				scores.setText("");
				director.sendEvent(AppEvents.EVENT_TRANSITION_TO_GAME_SCENE, event.getRelatedActor());
			}
		});
		
		backToMenu.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				scores.setText("");
				director.sendEvent(AppEvents.EVENT_TRANSITION_TO_MENU_SCENE, event.getRelatedActor());
			}
		});
		addActor(table);
	}
}
