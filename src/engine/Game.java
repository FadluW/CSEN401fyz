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
    private static int BOARDHEIGHT;
    private static int BOARDWIDTH;

    public static int getBOARDWIDTH() {
        return BOARDWIDTH;
    }

    public static int getBOARDHEIGHT() {
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
        board = new Object[5][5];
        BOARDHEIGHT = 1;
        BOARDWIDTH = 1;
        availableChampions = new ArrayList<Champion>();
        availableAbilities = new ArrayList<Ability>();
        firstLeaderAbilityUsed = false;
        secondLeaderAbilityUsed = false;
        turnOrder = new PriorityQueue(6); 
        placeChampions();
        placeCovers();
    }
    
    private void placeChampions() {
    	for(int i=0; i<3; i++) {
    		Point positionFirst = new Point(4,i+1);
    		((firstPlayer.getTeam()).get(i)).setLocation(positionFirst);
    		Point positionSecond = new Point(0,i+1);
    		((secondPlayer.getTeam()).get(i)).setLocation(positionSecond);
    	}
    }
    
    private void placeCovers() {
    	Random random = new Random();
    	for(int i=0; i<5; i++) {
    		Cover cover = new Cover(random.nextInt(5),random.nextInt(3) + 1);
    	}
    }
    
    public static void loadAbilities(String filePath) throws Exception {
    	BufferedReader br = new BufferedReader(new FileReader("Abilities.csv"));
    	String Line = br.readLine();
    	String[] abilityDetails = Line.split(","); 
    	Ability ability=null;
    	Effect effect= null;
    	while (Line != null) {
    		switch(abilityDetails[0]){
    			case "DMG":
    				ability = new DamagingAbility(abilityDetails[1],Integer.parseInt(abilityDetails[2]),
        					Integer.parseInt(abilityDetails[3]),Integer.parseInt(abilityDetails[4]),
        					AreaOfEffect.valueOf(abilityDetails[5]),Integer.parseInt(abilityDetails[6]),Integer.parseInt(abilityDetails[7]));
    				break;
    			case "HEL":
    				ability = new HealingAbility(abilityDetails[1],Integer.parseInt(abilityDetails[2]),
        					Integer.parseInt(abilityDetails[3]),Integer.parseInt(abilityDetails[4]),
        					AreaOfEffect.valueOf(abilityDetails[5]),Integer.parseInt(abilityDetails[6]),Integer.parseInt(abilityDetails[7]));
    				break;
    			default:
    				switch(abilityDetails[7]){
    					case "Disarm":
    						effect = new Disarm(abilityDetails[7],Integer.parseInt(abilityDetails[8]),EffectType.DEBUFF);
    						break;
    					case "PowerUp":
    						effect = new PowerUp(abilityDetails[7],Integer.parseInt(abilityDetails[8]),EffectType.BUFF);
    						break;
    					case "Shield":
    						effect = new Shield(abilityDetails[7],Integer.parseInt(abilityDetails[8]),EffectType.BUFF);
    						break;
    					case "Silence":
    						effect = new Silence(abilityDetails[7],Integer.parseInt(abilityDetails[8]),EffectType.DEBUFF);
    						break;
    					case "SpeedUp":
    						effect = new SpeedUp(abilityDetails[7],Integer.parseInt(abilityDetails[8]),EffectType.BUFF);
    						break;
    					case "Embrace":
    						effect = new Embrace(abilityDetails[7],Integer.parseInt(abilityDetails[8]),EffectType.BUFF);
    						break;
    					case "Root":
    						effect = new Root(abilityDetails[7],Integer.parseInt(abilityDetails[8]),EffectType.DEBUFF);
    						break;
    					case "Shock":
    						effect = new Shock(abilityDetails[7],Integer.parseInt(abilityDetails[8]),EffectType.DEBUFF);
    						break;
    					case "Dodge":
    						effect = new Dodge(abilityDetails[7],Integer.parseInt(abilityDetails[8]),EffectType.BUFF);
    						break;
    					case "Stun":
    						effect = new Stun(abilityDetails[7],Integer.parseInt(abilityDetails[8]),EffectType.DEBUFF);
    						break;
    				}
        			ability = new CrowdControlAbility(abilityDetails[1],Integer.parseInt(abilityDetails[2]),
        					Integer.parseInt(abilityDetails[3]),Integer.parseInt(abilityDetails[4]),AreaOfEffect.valueOf(abilityDetails[5]),
        					Integer.parseInt(abilityDetails[6]),effect);
    		}	
    		availableAbilities.add(ability);
    		Line = br.readLine();
    		abilityDetails = Line.split(","); 
    		
    	}
    }
    
    public static void loadChampions(String filePath) throws Exception {
    	BufferedReader br = new BufferedReader(new FileReader("Abilities.csv"));
    	String Line = br.readLine();
    	String[] championDetails = Line.split(","); 
		Champion champion = null;
		while (Line != null) {
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
    		Line = br.readLine();
    		championDetails = Line.split(",");
    	}
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