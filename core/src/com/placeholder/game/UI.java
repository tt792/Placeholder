package com.placeholder.game;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.*;

public class UI {
	//UI Stuff
	static Stage stage;
	static Table table;
	static Skin skin;
	
	//Main Menu Widgets
	static Label title;
	static TextButton start;
	static TextButton quit;
	
	//Character Select widgets (These are temporary)
	static Label explain;
	static TextButton normal;
	static TextButton sneaky;
	static TextButton stronk;
	
	static int menuPosition = 0;
	static boolean mainDrawn = false;
	static boolean selectDrawn = false;
	static boolean inMenu = true;
	
	static Player.playerType desiredType;

	static void drawMainMenu() {
		table.clear();
		selectDrawn = false;
		
		table.add(title).height(100);
		table.row();

		table.add(start).padBottom(20);
		table.row();
		table.add(quit);
		
		mainDrawn = true;
	}
	
	static void drawSelect() {
		table.clear();
		mainDrawn = false;
		
		table.add(explain).height(100);
		table.row();
		table.add(normal);
		table.row();
		table.add(sneaky);
		table.row();
		table.add(stronk);
		
		selectDrawn = true;
	}
	
	static void startGame() {
		menuPosition = 1;
	}
	
	public static void create() {
		//UI Create
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
		
		table = new Table();
		
		title = new Label("I forgot what our game is called!!", skin);
		
		start = new TextButton("Start Game", skin);
		start.addListener(new ClickListener() { 
			public void clicked(InputEvent event, float x, float y) {
				startGame();
			}
		});
		
		quit = new TextButton("Quit", skin);
		quit.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		explain = new Label("This is temporary. I will make it look better", skin);
		
		normal = new TextButton("Normal Character", skin);
		normal.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				desiredType = Player.playerType.Nerd;
				inMenu = false;
			}
		});
		
		sneaky = new TextButton("Sneaky Character", skin);
		sneaky.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				desiredType = Player.playerType.Art;
				inMenu = false;
			}
		});
		
		stronk = new TextButton("Stronkg Character", skin);
		stronk.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				desiredType = Player.playerType.Jock;
				inMenu = false;
			}
		});
		
		table.setFillParent(true);
		
		drawMainMenu();
		
		stage.addActor(table);
	}
	
	public static void render() {
		if (inMenu) {
			if (menuPosition == 0 && !mainDrawn) {
				drawMainMenu();
			}
			if (menuPosition == 1 && !selectDrawn) {
				drawSelect();
			}
			
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
		}
		if (!inMenu) {
			table.clear();
			menuPosition = 0;
			mainDrawn = false;
			selectDrawn = false;
		}
	}
	
	public static void dispose() {
		stage.dispose();
	}
}