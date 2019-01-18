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

	/**
	 * Draws the main menu screen
	 * @implementation Add all of the widgets that should be on the main menu screen here
	 */
	static void drawMainMenu() {
		table.clear();
		table.center();
		selectDrawn = false;
		
		//Add all of the widgets onto the table
		table.add(logo).padBottom(20).height(200).width(200);
		table.row();
		table.add(start).padBottom(20).height(30).width(100);
		table.row();
		table.add(quit).height(30).width(100);
		
		mainDrawn = true;
	}
	
	/**
	 * Draws the character selection screen
	 * @implementation Add all of the widgets that should be on the character selection screen here
	 */
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
	
	/**
	 * Takes the UI to the Character selection screen
	 */
	static void charSelection() {
		menuPosition = 1;
	}
	
	/**
	 * Start the game after a character has been selected
	 */
	static void startGame() {
		inMenu = false;
	}
	
	/**
	 * Return to main menu from the character selection screen
	 */
	static void returnToMain() {
		menuPosition = 0;
	}
	
	/**
	 * Sets the players desired character to Nerd
	 */
	static void chooseNerd() {
		desiredType = playerType.Nerd;
	}
	
	/**
	 * Sets the players desired character to Jock
	 */
	static void chooseJock() {
		desiredType = playerType.Jock;
	}
	
	/**
	 * Creation function of the UI. Creates all of the widgets for use in the menus
	 */
	public static void create() {
		//UI Create
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		//Create the UI skin
		skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
		
		//Create the table
		table = new Table();
		
		//Start creating widgets
		title = new Label("Generic: Zombie Game", skin);
		
		logo = new Image(new Texture("logo.png"));
		
		start = new TextButton("Start", skin);
		start.addListener(new ClickListener() { 
			public void clicked(InputEvent event, float x, float y) {
				charSelection();
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
				startGame();
			}
		});
		
		prevPage = new TextButton("Return", skin);
		prevPage.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				returnToMain();
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
		//End of creating widgets
		
		table.setFillParent(true);
		
		drawMainMenu(); //Draw the main menu
		
		stage.addActor(table);
	}
	
	/**
	 * Render function for the UI </br>
	 * displays the buttons and art for character selection and to start the game
	 * @implementation (F10) Used to display the buttons to select which character the user wishes to play
	 */
	public static void render() {
		if (inMenu) {
			if(menuPosition == 1) {
				if(desiredType == playerType.Nerd) { //If the character is supposed to be the nerd
					currText.setText("The Nerd"); //Change the text to show it is the nerd
					currChar.setDrawable(new SpriteDrawable(new Sprite(new Texture("player1_updown.png")))); //Change the image to be the nerd
					currStats.setText("Stats:\nStrength: 1\nSpeed: 3\nHP: 3\nStealth: 3"); //Show the nerds stats TODO not have this hardcoded
				}
				else if (desiredType == playerType.Jock) { //If the character is supposed to be the jock
					currText.setText("The Jock"); //Change the text to show it is the jock
					currChar.setDrawable(new SpriteDrawable(new Sprite(new Texture("player2_updown.png")))); //Change the image to be the jock
					currStats.setText("Stats:\nStrength: 3\nSpeed: 2\nHP: 5\nStealth: 0"); //Show the jocks stats TODO Not have this hardcoded
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
	
	/**
	 * Disposes of the current UI in order to free up memory
	 */
	public static void dispose() { 
		stage.dispose();
	}
}