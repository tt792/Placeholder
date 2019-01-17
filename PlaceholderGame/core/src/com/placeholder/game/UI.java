package com.placeholder.game;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.placeholder.game.Player.playerType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.*;

public class UI {
	//UI Stuff
	static Stage stage;
	static Table table;
	static Skin skin;
	
	//Main Menu Widgets
	static Label title;
	static Image logo;
	static TextButton start;
	static TextButton quit;
	
	//Character Select widgets
	static Label csTitle;
	static TextButton nextPage;
	static TextButton prevPage;
	static Image currChar;
	static Label currText;
	static Label currStats;
	static TextButton nerd;
	static TextButton jock;
	
	
	
	static int menuPosition = 0; //What page of the menu you are
	static boolean mainDrawn = false; //Is the main page drawn? 
	static boolean selectDrawn = false; //Is the character select page drawn?
	static boolean inMenu = true; //Have we left the menu yet
	
	static Player.playerType desiredType = playerType.Nerd;

	static void drawMainMenu() {
		table.clear();
		table.center();
		selectDrawn = false;
		
		//Add all of the widgets onto the table
		//table.add(title).height(100);
		table.add(logo);
		table.row();
		table.add(start).padBottom(20).height(30).width(100);
		table.row();
		table.add(quit).height(30).width(100);
		
		mainDrawn = true;
	}
	
	static void drawSelect() {
		table.clear();
		mainDrawn = false;
		
		table.left();
		
		//Add all of the widgets onto the table
		table.add(csTitle);
		table.row();
		table.add(currText).padTop(40).padBottom(10).colspan(3);
		table.row();
		table.add(currChar).left().padLeft(50).width(250).height(250).colspan(2);
		table.add(currStats).right().padRight(120);
		table.row();
		table.add(nerd).left().padLeft(70).padTop(10);
		table.add(jock).left().padLeft(10).padTop(10);
		table.row();
		table.add(prevPage).width(100).height(40).left().bottom();
		table.add().expand();
		table.add(nextPage).width(100).height(40).right().bottom();
		
		selectDrawn = true;
	}
	
	static void startGame() { //Go to the char selection screen
		menuPosition = 1;
	}
	
	static void next() { //Start the game after char selection
		inMenu = false;
	}
	
	static void prev() { //Return to main menu from char selection
		menuPosition = 0;
	}
	
	static void chooseNerd() { //Set desired character to nerd
		desiredType = playerType.Nerd;
	}
	
	static void chooseJock() { //Set desired character to art
		desiredType = playerType.Jock;
	}
	
	public static void create() {
		//UI Create
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
		
		table = new Table();
		
		title = new Label("Generic: Zombie Game", skin);
		
		logo = new Image(new Texture("logo.png"));
		
		start = new TextButton("Start", skin);
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
		
		nextPage = new TextButton("Start", skin);
		nextPage.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				next();
			}
		});
		
		prevPage = new TextButton("Return", skin);
		prevPage.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				prev();
			}
		});
		
		csTitle = new Label("Choose Your Character:", skin);
		
		currText = new Label("",skin);
		
		currChar = new Image();
		
		currStats = new Label("", skin);
		
		nerd = new TextButton("The Nerd", skin);
		nerd.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				chooseNerd();
			}
		});
		
		jock = new TextButton("The Jock", skin);
		jock.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				chooseJock();
			}
		});
		
		table.setFillParent(true);
		
		drawMainMenu();
		
		stage.addActor(table);
	}
	
	public static void render() {
		if (inMenu) {
			if(menuPosition == 1) {
				if(desiredType == playerType.Nerd) {
					currText.setText("The Nerd");
					currChar.setDrawable(new SpriteDrawable(new Sprite(new Texture("player1_updown.png"))));
					currStats.setText("Stats:\nStrength: 1\nSpeed: 3\nHP: 3\nStealth: 3");
				}
				else if (desiredType == playerType.Jock) {
					currText.setText("The Jock");
					currChar.setDrawable(new SpriteDrawable(new Sprite(new Texture("player2_updown.png"))));
					currStats.setText("Stats:\nStrength: 3\nSpeed: 2\nHP: 5\nStealth: 0");
				}
			}
			if (menuPosition == 0 && !mainDrawn) { //If we are supposed to be on the main menu and it isn't drawn yet
				drawMainMenu(); //Draw the main menu
			}
			if (menuPosition == 1 && !selectDrawn) { //If we are supposed to be on the char selection screen and it isn't drawn yet
				drawSelect(); //Draw the char selection screen
			}
			
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
		}
		if (!inMenu) { //If we are no longer in the menu (i.e. we have started the game)
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