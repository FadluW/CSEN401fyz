package engine;

import model.abilities.*;
import model.effects.*;
import model.world.*;

import java.awt.Point;
import java.io.BufferedReader;
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
    private final static int NUMCOVERS=5;
    private final static int MAXCHAMPS= 6;

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
        turnOrder = new PriorityQueue(MAXCHAMPS);
        placeChampions();
        placeCovers();
    }
    
    private void placeChampions() {
    	// Fetch both teams
    	ArrayList<Champion> firstTeam = firstPlayer.getTeam();
    	ArrayList<Champion> secondTeam = secondPlayer.getTeam();
    	
    	for(int i=0; i<MAXCHAMPS/2; i++) {
    		// Place down a champion from the first team if they still have any
    		if (i < firstTeam.size()) { 
    			Point positionFirst = new Point(0, i+1);
    			((Champion) (firstTeam.get(i))).setLocation(positionFirst);
    			board[0][i + 1] = (Champion) (firstTeam.get(i));
    		}
    		// Place down a champion from the second team if they still have any
    		if (i < secondTeam.size()) {
    			Point positionSecond = new Point(4, i+1);
    			((Champion) (secondTeam.get(i))).setLocation(positionSecond);
    			board[4][i + 1] = (Champion) (secondTeam.get(i));
    		}
    	}
    	
    }
    
    private void placeCovers() {
    	Random random = new Random();
    	for(int i=0; i<NUMCOVERS; i++) {
    		int randX = random.nextInt(3) + 1;
    		int randY = random.nextInt(5);
			
    		// Loop until an empty slot on the game board is found
    		while(board[randX][randY] != null) {
    			randX = random.nextInt(3) + 1;
        		randY = random.nextInt(5);
    		}
    		
    		// Place down the cover
    		Cover cover = new Cover(randX, randY);
    		board[randX][randY] = cover;
    	}
    }

    public static void loadAbilities(String filePath) throws Exception {
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
    			case "CC":
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
    			case "V":
    				champion = new Villain(championDetails[1],Integer.parseInt(championDetails[2]),
        					Integer.parseInt(championDetails[3]),Integer.parseInt(championDetails[4]),
        					Integer.parseInt(championDetails[5]),Integer.parseInt(championDetails[6]),Integer.parseInt(championDetails[7]));
    		}
    		
    		// Obtain this champion's abilities reference
    		ArrayList<Ability> champAbilities = champion.getAbilities();
    		// Loop 3 times to load champion's abilities
    		for (int i = 0; i < 3; i++) {
    			// Loop through all abilities until matching name found
    	        for (int j = 0; j < availableAbilities.size(); j++) {
    	        	if (((Ability) availableAbilities.get(j)).getName().equals(championDetails[i + 8])) {
    	        		champAbilities.add((Ability) availableAbilities.get(j));
    	        		break;
    	        	}
    	        }
    		}
    		
    		availableChampions.add(champion);
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


//   public static void main(String[] args) {
//		Player p1 = new Player("Player 1");
//		Player p2 = new Player("Player 2");
//		Champion ch = new Champion("Fadlu", 50, 50, 50, 100,50 , 50);
//		Champion ch1 = new Champion("Joey", 50, 50, 50, 101,50 , 50);
//		Champion ch2 = new Champion("Zeina", 50, 50, 50, 98,50 , 50);
//		Champion ch3 = new Champion("Youssef", 50, 50, 50, 120,50 , 50);
//		ArrayList<Champion> team1 = p1.getTeam();
//	    ArrayList<Champion> team2 = p2.getTeam();
//		team1.add(ch);
//		team1.add(ch1);
//		team2.add(ch2);
//		team2.add(ch3);
//		Game game = new Game(p1,p2);
//		PriorityQueue p = game.getTurnOrder();
//		p.insert(ch1);
//		p.insert(ch2);
//		p.insert(ch3);
//		p.insert(ch);
//		game.placeChampions();
//		while(!(p.isEmpty())) {
//			System.out.println(((Champion)p.remove()).getSpeed());
//		}
//		Object[][] b = game.getBoard();
//		for(int i = 0; i<getBoardheight(); i++){
//			System.out.print("[ ");
//			for (int j = 0; j<getBoardwidth(); j++){
//				System.out.print(b[i][j] + " , ");
//			}
//			System.out.print(" ]");
//			System.out.println();
//		}
//
//
//		try {
//			loadAbilities("Abilities.csv");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//System.out.println(availableAbilities.get(0));
//
//		try {
//			loadChampions("Champions.csv");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//System.out.println(availableChampions.get(0));
//	}
}

