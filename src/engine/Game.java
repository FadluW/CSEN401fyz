package engine;

import model.abilities.*;
import model.effects.*;
import model.world.*;

import java.awt.Point;
import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileDescriptor;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;


public class Game {
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean firstLeaderAbilityUsed;
    private boolean secondLeaderAbilityUsed;
    private Object[][] board;
    private static ArrayList<Champion> availableChampions;
    private static ArrayList<Ability> availableAbilities;
    private PriorityQueue turnOrder;
    private final static int BOARDHEIGHT= 5;
    private final static int BOARDWIDTH= 5;

    public static int getBoardwidth() {
        return BOARDWIDTH;
    }

    public static int getBoardheight() {
        return BOARDHEIGHT;
    }

    public PriorityQueue getTurnOrder() {
        return turnOrder;
    }

    public static ArrayList<Ability> getAvailableAbilities() {
        return availableAbilities;
    }

    public static ArrayList<Champion> getAvailableChampions() {
        return availableChampions;
    }

    public Object[][] getBoard() {
        return board;
    }

    public Game(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        board = new Object[BOARDHEIGHT][BOARDWIDTH];
        availableChampions = new ArrayList<Champion>();
        availableAbilities = new ArrayList<Ability>();
        firstLeaderAbilityUsed = false;
        secondLeaderAbilityUsed = false;
        turnOrder = new PriorityQueue(6); 
        placeChampions();
		placeCovers();
    }
    
    private void placeChampions() {
    	ArrayList<Champion> firstTeam = firstPlayer.getTeam();
    	ArrayList<Champion> secondTeam = secondPlayer.getTeam();

    	for(int i=0; i<3; i++) {
			Point positionFirst = new Point(0, i + 1);
			if (i < firstTeam.size()) {
				((Champion) (firstTeam.get(i))).setLocation(positionFirst);
				board[0][i + 1] = (Champion) (firstTeam.get(i));
			}
			Point positionSecond = new Point(4, i + 1);
			if (i < secondTeam.size()) {
				((Champion) (secondTeam.get(i))).setLocation(positionSecond);
				board[4][i + 1] = (Champion) (secondTeam.get(i));
			}
		}
    	for(int i=1; i<4; i++) {
    		Point positionFirst = new Point(4,i);
    		(firstPlayer.getTeam().get(i-1)).setLocation(positionFirst);
    		Point positionSecond = new Point(0,i);
    		(secondPlayer.getTeam().get(i-1)).setLocation(positionSecond);
    	}
    }
    
//    public static void main(String[] args) {
//    	Player p1 = new Player("ya3");
//    	Player p2 = new Player("Joey Boy");
//    	Game game = new Game(p1,p2);
//
//    	try {
//			loadAbilities("Abilities.csv");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//    	try {
//			loadChampions("Champions.csv");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//    }
    
    private void placeCovers() {
    	Random random = new Random();
    	for(int i=0; i<5; i++) {
    		int randX = random.nextInt(3) + 1;
    		int randY = random.nextInt(5);

    		while(board[randX][randY] != null) {
    			randX = random.nextInt(3) + 1;
        		randY = random.nextInt(5);
    		}
    		Cover cover = new Cover(randX, randY);
    		board[randX][randY] = cover;
    	}
    }
    
    public static void loadAbilities(String filePath )throws Exception {
    	BufferedReader br = new BufferedReader(new FileReader(filePath));
    	String line;
    	Ability ability=null;
    	Effect effect= null;
    	while ((line = br.readLine()) != null) {
    		String[] abilityDetails = line.split(",");
    		switch(abilityDetails[0]){
    			case "DMG":
    				ability = new DamagingAbility(abilityDetails[1],Integer.parseInt(abilityDetails[2]),
        					Integer.parseInt(abilityDetails[4]),Integer.parseInt(abilityDetails[3]),
        					AreaOfEffect.valueOf(abilityDetails[5]),Integer.parseInt(abilityDetails[6]),Integer.parseInt(abilityDetails[7]));
    				break;
    			case "HEL":
    				ability = new HealingAbility(abilityDetails[1],Integer.parseInt(abilityDetails[2]),
        					Integer.parseInt(abilityDetails[4]),Integer.parseInt(abilityDetails[3]),
        					AreaOfEffect.valueOf(abilityDetails[5]),Integer.parseInt(abilityDetails[6]),Integer.parseInt(abilityDetails[7]));
    				break;
    			default:
    				switch(abilityDetails[7]){
    					case "Disarm":
    						effect = new Disarm(Integer.parseInt(abilityDetails[8]));
    						break;
    					case "PowerUp":
    						effect = new PowerUp(Integer.parseInt(abilityDetails[8]));
    						break;
    					case "Shield":
    						effect = new Shield(Integer.parseInt(abilityDetails[8]));
    						break;
    					case "Silence":
    						effect = new Silence(Integer.parseInt(abilityDetails[8]));
    						break;
    					case "SpeedUp":
    						effect = new SpeedUp(Integer.parseInt(abilityDetails[8]));
    						break;
    					case "Embrace":
    						effect = new Embrace(Integer.parseInt(abilityDetails[8]));
    						break;
    					case "Root":
    						effect = new Root(Integer.parseInt(abilityDetails[8]));
    						break;
    					case "Shock":
    						effect = new Shock(Integer.parseInt(abilityDetails[8]));
    						break;
    					case "Dodge":
    						effect = new Dodge(Integer.parseInt(abilityDetails[8]));
    						break;
    					case "Stun":
    						effect = new Stun(Integer.parseInt(abilityDetails[8]));
    						break;
    				}
        			ability = new CrowdControlAbility(abilityDetails[1],Integer.parseInt(abilityDetails[2]),
        					Integer.parseInt(abilityDetails[4]),Integer.parseInt(abilityDetails[3]),AreaOfEffect.valueOf(abilityDetails[5]),
        					Integer.parseInt(abilityDetails[6]),effect);
    		}	
    		availableAbilities.add(ability);
    	}
    	br.close();
    }
    
    public static void loadChampions(String filePath) throws Exception {
    	BufferedReader br = new BufferedReader(new FileReader(filePath));
    	String line;
		Champion champion = null;
		while ((line = br.readLine()) != null) {
			String[] championDetails = line.split(",");
    		switch(championDetails[0]){
    			case "A":
    				champion = new AntiHero(championDetails[1],Integer.parseInt(championDetails[2]),
        					Integer.parseInt(championDetails[3]),Integer.parseInt(championDetails[4]),
        					Integer.parseInt(championDetails[5]),Integer.parseInt(championDetails[6]),Integer.parseInt(championDetails[7]));
    				break;
    			case "H":
    				champion = new Hero(championDetails[1],Integer.parseInt(championDetails[2]),
        					Integer.parseInt(championDetails[3]),Integer.parseInt(championDetails[4]),
        					Integer.parseInt(championDetails[5]),Integer.parseInt(championDetails[6]),Integer.parseInt(championDetails[7]));
    				break;
    			default:
    				champion = new Villain(championDetails[1],Integer.parseInt(championDetails[2]),
        					Integer.parseInt(championDetails[3]),Integer.parseInt(championDetails[4]),
        					Integer.parseInt(championDetails[5]),Integer.parseInt(championDetails[6]),Integer.parseInt(championDetails[7]));
    		}
//    		loadAbilities("./Abilities.csv");
//    		for(int i=0; i<3; i++) {
//    			availableAbilities
//    		}
    		availableChampions.add(champion);
//    		line = br.readLine();
//    		championDetails = line.split(",");
    	}
		br.close();
    }

    public boolean isSecondLeaderAbilityUsed() {
        return secondLeaderAbilityUsed;
    }

    public boolean isFirstLeaderAbilityUsed() {
        return firstLeaderAbilityUsed;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }
}