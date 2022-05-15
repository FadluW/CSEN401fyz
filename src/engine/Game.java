package engine;

import exceptions.*;
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
	private final static int BOARDHEIGHT = 5;
	private final static int BOARDWIDTH = 5;
	private final static int NUMCOVERS = 5;
	private final static int MAXCHAMPS = 6;

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

		for (int i = 0; i < MAXCHAMPS / 2; i++) {
			// Place down a champion from the first team if they still have any
			if (i < firstTeam.size()) {
				Point positionFirst = new Point(0, i + 1);
				((Champion) (firstTeam.get(i))).setLocation(positionFirst);
				board[0][i + 1] = (Champion) (firstTeam.get(i));
				turnOrder.insert((Champion) (firstTeam.get(i)));
			}
			// Place down a champion from the second team if they still have any
			if (i < secondTeam.size()) {
				Point positionSecond = new Point(4, i + 1);
				((Champion) (secondTeam.get(i))).setLocation(positionSecond);
				board[4][i + 1] = (Champion) (secondTeam.get(i));
				turnOrder.insert((Champion) (secondTeam.get(i)));
			}
		}

	}

	private void placeCovers() {
		Random random = new Random();
		for (int i = 0; i < NUMCOVERS; i++) {
			int randX = random.nextInt(3) + 1;
			int randY = random.nextInt(5);

			// Loop until an empty slot on the game board is found
			while (board[randX][randY] != null) {
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
		Ability ability = null;
		Effect effect = null;
		while ((line = br.readLine()) != null) {
			String[] abilityDetails = line.split(",");
			switch (abilityDetails[0]) {
				case "DMG":
					ability = new DamagingAbility(abilityDetails[1], Integer.parseInt(abilityDetails[2]),
							Integer.parseInt(abilityDetails[4]), Integer.parseInt(abilityDetails[3]),
							AreaOfEffect.valueOf(abilityDetails[5]), Integer.parseInt(abilityDetails[6]), Integer.parseInt(abilityDetails[7]));
					break;
				case "HEL":
					ability = new HealingAbility(abilityDetails[1], Integer.parseInt(abilityDetails[2]),
							Integer.parseInt(abilityDetails[4]), Integer.parseInt(abilityDetails[3]),
							AreaOfEffect.valueOf(abilityDetails[5]), Integer.parseInt(abilityDetails[6]), Integer.parseInt(abilityDetails[7]));
					break;
				case "CC":
					switch (abilityDetails[7]) {
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
					ability = new CrowdControlAbility(abilityDetails[1], Integer.parseInt(abilityDetails[2]),
							Integer.parseInt(abilityDetails[4]), Integer.parseInt(abilityDetails[3]), AreaOfEffect.valueOf(abilityDetails[5]),
							Integer.parseInt(abilityDetails[6]), effect);
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
			switch (championDetails[0]) {
				case "A":
					champion = new AntiHero(championDetails[1], Integer.parseInt(championDetails[2]),
							Integer.parseInt(championDetails[3]), Integer.parseInt(championDetails[4]),
							Integer.parseInt(championDetails[5]), Integer.parseInt(championDetails[6]), Integer.parseInt(championDetails[7]));
					break;
				case "H":
					champion = new Hero(championDetails[1], Integer.parseInt(championDetails[2]),
							Integer.parseInt(championDetails[3]), Integer.parseInt(championDetails[4]),
							Integer.parseInt(championDetails[5]), Integer.parseInt(championDetails[6]), Integer.parseInt(championDetails[7]));
					break;
				case "V":
					champion = new Villain(championDetails[1], Integer.parseInt(championDetails[2]),
							Integer.parseInt(championDetails[3]), Integer.parseInt(championDetails[4]),
							Integer.parseInt(championDetails[5]), Integer.parseInt(championDetails[6]), Integer.parseInt(championDetails[7]));
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

	public Champion getCurrentChampion() {
		return (Champion) getTurnOrder().peekMin();
	}

	public Player checkGameOver() {
		if (firstPlayer.getTeam().isEmpty()) return secondPlayer;
		if (secondPlayer.getTeam().isEmpty()) return firstPlayer;
		return null;
	}

	public void move(Direction d) throws UnallowedMovementException {
		Champion c = getCurrentChampion();
		Point p = calcDirection(c.getLocation(), d, 1);
		
		int vertical = (int) p.getLocation().getX();
		int horizontal = (int) p.getLocation().getY();

		if (vertical < 0 || vertical > 4) throw new UnallowedMovementException("Out of vertical board borders"); 
		if (horizontal < 0 || horizontal > 4) throw new UnallowedMovementException("Out of horizontal board borders"); 

		if (!board[vertical][horizontal].equals(null)) throw new UnallowedMovementException();

		c.setLocation(new Point(vertical, horizontal));
		c.setCurrentActionPoints(c.getCurrentActionPoints() - 1);
	}

	public void attack(Direction d) throws InvalidTargetException, NotEnoughResourcesException, ChampionDisarmedException {
		Champion c = getCurrentChampion();
		int range = c.getAttackRange();
		Boolean flag = false;

		// Ensure not disarmed
		for (Effect e : c.getAppliedEffects()) {
			if (e.getName() == "Disarm") throw new ChampionDisarmedException("Champion disarmed");
		}

		// Ensure enough AP
		if (c.getCurrentActionPoints() < 2) throw new NotEnoughResourcesException("Champion has less than 2 AP");
		else c.setCurrentActionPoints(c.getCurrentActionPoints() - 2);
		
		// Iterate over champion's range until out of range or target found
		for (int i = 1; i <= range && !flag; i++) {
			Point p = calcDirection(c.getLocation(), d, i);
			
			int x = (int) p.getLocation().getX();
			int y = (int) p.getLocation().getY();

			if (x < 0 || x > BOARDHEIGHT) throw new InvalidTargetException("Target out of board bounds");
			if (y < 0 || y > BOARDWIDTH) throw new InvalidTargetException("Target out of board bounds");

			// Check if attacking cover
			if (getBoard()[x][y] instanceof Cover) {
				flag = true;
				Cover cover = (Cover) getBoard()[x][y];
				cover.setCurrentHP(cover.getCurrentHP() - c.getAttackDamage());
			}

			// Check if attacking champion
			if (getBoard()[x][y] instanceof Champion) {
				Champion target = (Champion) getBoard()[x][y];
				// Ensure not of same team
				if (firstPlayer.getTeam().contains(c) == !firstPlayer.getTeam().contains(target)) {
					flag = true;
					double dmgMultiplier = 1;
					if (!(target.getClass().equals(c.getClass())) && !(target instanceof AntiHero))
						dmgMultiplier = 1.5;

					double damageDealt = c.getAttackDamage() * dmgMultiplier;
					Random rand = new Random();

					// Iterate over target's current effects
					Boolean deflected = false;
					ArrayList<Effect> targetEffects = target.getAppliedEffects();
					for (Effect e : targetEffects) {
						if (e.getName().equals("Shield")) {
							// Remove shield from target
							e.remove(target);
							deflected = true;
							break;
						}
					}
					if (!deflected) {
						for (Effect e : targetEffects) {
							if (e.getName().equals("Dodge")){
								int chance = rand.nextInt(100);
								// If dodged
								if (chance < 50) deflected = true;
								break;
							}
						}
					}
					

					// Deal damage on target
					target.setCurrentHP((int) (target.getCurrentHP() - ((deflected) ? 0 : damageDealt)));
				}
			}
		}

		// If no target found throw exception
		if (!flag) {
			throw new InvalidTargetException("No target in direction");
		}		
	}

	private void checkCastAbility(Ability a, Champion c) throws Exception {
		// Ensure not silenced
		for (Effect e : c.getAppliedEffects()) {
			if (e.getName() == "Silence") throw new AbilityUseException("Champion silenced");
		}

		// Ensure off of cooldown
		if (a.getCurrentCooldown() > 0) throw new AbilityUseException("Ability on cooldown");

		// Ensure champ has enough mana
		if (c.getMana() < a.getManaCost()) throw new NotEnoughResourcesException("Not enough mana");

		// Ensure enough AP
		if (c.getCurrentActionPoints() < 1) throw new NotEnoughResourcesException("Not enough AP");

		// Reset cooldown, Decrease mana
		a.setCurrentCooldown(a.getBaseCooldown());
		c.setMana(c.getMana() - a.getManaCost());
		c.setCurrentActionPoints(c.getCurrentActionPoints() - 1);
	}

	public void castAbility(Ability a) throws Exception {
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		Champion c = getCurrentChampion();
		ArrayList<Champion> friendlyTeam;
		ArrayList<Champion> enemyTeam;

		// check for proper conditions and update stats
		checkCastAbility(a, c);

		// If self-cast
		if (a.getCastArea() == AreaOfEffect.SELFTARGET) {
			// Add self to targets
			targets.add(c);
			a.execute(targets);
			return;
		}

		if (firstPlayer.getTeam().contains(c)) {
			friendlyTeam = firstPlayer.getTeam();
			enemyTeam = secondPlayer.getTeam();
		} else {
			friendlyTeam = secondPlayer.getTeam();
			enemyTeam = firstPlayer.getTeam();
		}

		ArrayList<Champion> targetTeam = null;
		if (a instanceof DamagingAbility) {
			a = (DamagingAbility) a;
			targetTeam = enemyTeam;

		} else if (a instanceof HealingAbility) {
			a = (HealingAbility) a;
			targetTeam = friendlyTeam;

		} else if (a instanceof CrowdControlAbility) {
			a = (CrowdControlAbility) a;
			switch (((CrowdControlAbility) a).getEffect().getType()) {
				case BUFF: {
					targetTeam = friendlyTeam;
					break;
				}
				case DEBUFF: {
					targetTeam = enemyTeam;
					break;
				}
				default: break;
			}
		}

		switch (a.getCastArea()) {
			case SURROUND: {
				// Iterate over rows
				for (int i = (int) c.getLocation().getX() - 1; i <= (int) c.getLocation().getX() + 1; i++) {
					if (i < 0 || i > BOARDHEIGHT) continue;

					// Iterate over columns
					for (int j = (int) c.getLocation().getY() - 1; j <= (int) c.getLocation().getY() + 1; j++) {
						if (j < 0 || j > BOARDWIDTH) continue;
						
						if (board[i][j] == null) continue;
						if (board[i][j] instanceof Champion && board[i][j].equals(c)) continue;

						handleAbilityTarget(c, i, j, a, targets);
					}
				}
				break;
			}
			case TEAMTARGET: {
				for (Champion current : targetTeam) {
					int distance = manhattanDist(c.getLocation(), current.getLocation());
					if (distance <= a.getCastRange()) {
						targets.add(current);
					}
				}
				break;
			}
			default: break;
		}

		// Ensure target found
		if (!targets.isEmpty()) a.execute(targets);
	}

	public void castAbility(Ability a, Direction d) throws Exception {
		// Ensure ability is directional
		if (a.getCastArea() != AreaOfEffect.DIRECTIONAL) throw new AbilityUseException("Ability not directional");
		Champion c = getCurrentChampion();
		ArrayList<Damageable> targets = new ArrayList<Damageable>();

		// check for proper conditions and update stats
		checkCastAbility(a, c);

		// Iterate over board in direction through abilities range
		for (int i = 1; i <= a.getCastRange(); i++) {
			Point p = calcDirection(c.getLocation(), d, i);

			int x = (int) p.getX();
			int y = (int) p.getY();

			if (x < 0 || x > board.length) break;
			if (y < 0 || y > board[x].length) break;

			if (board[x][y] == null) continue;
			handleAbilityTarget(c, x, y, a, targets);
		}

		// Ensure target found
		if (!targets.isEmpty()) a.execute(targets);
	}

	public void castAbility(Ability a, int x, int y) throws Exception {
		// Ensure ability is directional
		if (a.getCastArea() != AreaOfEffect.SINGLETARGET) throw new AbilityUseException("Ability not directional");
		Champion c = getCurrentChampion();
		ArrayList<Damageable> targets = new ArrayList<Damageable>();

		// check for proper conditions and update stats
		checkCastAbility(a, c);

		if (x < 0 || x > BOARDHEIGHT) throw new InvalidTargetException("Target out of board bounds");
		if (y < 0 || y > BOARDWIDTH) throw new InvalidTargetException("Target out of board bounds");

		if (board[x][y] == null) throw new InvalidTargetException("Target empty cell");

		handleAbilityTarget(c, x, y, a, targets);

		if (!targets.isEmpty()) a.execute(targets);
	}

	public void useLeaderAbility() throws Exception {
		Champion c = getCurrentChampion();
		ArrayList<Champion> targets = new ArrayList<Champion>();
		ArrayList<Champion> friendlyTeam = null;
		ArrayList<Champion> enemyTeam = null;

		if (firstPlayer.getTeam().contains(c)) {
			if (!firstPlayer.getLeader().equals(c)) throw new LeaderNotCurrentException();
			if (firstLeaderAbilityUsed) throw new LeaderAbilityAlreadyUsedException();

			friendlyTeam = firstPlayer.getTeam();
			enemyTeam = secondPlayer.getTeam();
			firstLeaderAbilityUsed = true;
		} else if (secondPlayer.getTeam().contains(c)) {
			if (!secondPlayer.getLeader().equals(c)) throw new LeaderNotCurrentException();
			if (secondLeaderAbilityUsed) throw new LeaderAbilityAlreadyUsedException();

			friendlyTeam = secondPlayer.getTeam();
			enemyTeam = firstPlayer.getTeam();
			secondLeaderAbilityUsed = true;
		}

		if (c instanceof Hero) {
			targets = friendlyTeam;
		} else if (c instanceof Villain) {
			targets = enemyTeam;
		} else if (c instanceof AntiHero) {
			// Iterate over both teams adding any non-leaders
			for (Champion curr : secondPlayer.getTeam()) {
				if (!secondPlayer.getLeader().equals(curr)) targets.add(curr);
			}
			for (Champion curr : firstPlayer.getTeam()) {
				if (!firstPlayer.getLeader().equals(curr)) targets.add(curr);
			}
		}

		if (!targets.isEmpty()) c.useLeaderAbility(targets);
	}

	public void endTurn() {
		turnOrder.remove();
		if (turnOrder.isEmpty()) prepareChampionTurns(); //prepare turns

		// Loop until first non inactive
		while (((Champion)turnOrder.peekMin()).getCondition() == Condition.INACTIVE) {
			turnOrder.remove();
			if (turnOrder.isEmpty()) prepareChampionTurns();
		}

		Champion current = (Champion) turnOrder.peekMin();

		// Decrement cooldown from all abilities and effect
		for (Ability a : current.getAbilities()) {
			a.setCurrentCooldown(a.getCurrentCooldown() - 1);
		}
		for (Effect e : current.getAppliedEffects()) {
			e.setDuration(e.getDuration() - 1);
			// If duration ran out, remove effect
			if (e.getDuration() < 1) e.remove(current);
		}

		// Reset current AP
		current.setCurrentActionPoints(current.getMaxActionPointsPerTurn());
	}

	private void prepareChampionTurns() {
		ArrayList<Champion> firstTeam = firstPlayer.getTeam();
		ArrayList<Champion> secondTeam = secondPlayer.getTeam();

		for (int i = 0; i < MAXCHAMPS / 2; i++) {

			if (i < firstTeam.size()) {
				if (firstTeam.get(i).getCondition() != Condition.INACTIVE) {
					turnOrder.insert((Champion) (firstTeam.get(i)));
				}
			}
			
			if (i < secondTeam.size()) {
				if (secondTeam.get(i).getCondition() != Condition.INACTIVE) {
					turnOrder.insert((Champion) (secondTeam.get(i)));
				}
			}
		}
	}

	private int manhattanDist(Point a, Point b) {
		int distance = (int) (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()));
		return distance;
	}

	private Point calcDirection(Point p, Direction d, int step) {
		int x = (int) p.getX();
		int y = (int) p.getY();

		switch (d) {
			case RIGHT: {
				y += step;
				break;
			}
			case LEFT: {
				y -= step;
				break;
			}
			case UP: {
				x += step;
				break;
			}
			case DOWN: {
				x -= step;
				break;
			}
		}

		Point p2 = new Point(x, y);
		return p2;
	}

	private void handleAbilityTarget(Champion c, int x, int y, Ability a, ArrayList<Damageable> targets) {
		ArrayList<Champion> friendlyTeam;
		ArrayList<Champion> enemyTeam;

		if (firstPlayer.getTeam().contains(c)) {
			friendlyTeam = firstPlayer.getTeam();
			enemyTeam = secondPlayer.getTeam();
		} else {
			friendlyTeam = secondPlayer.getTeam();
			enemyTeam = firstPlayer.getTeam();
		}

		// Handle covers
		if (board[x][y] instanceof Cover) {
			if (!(a instanceof CrowdControlAbility)) targets.add((Cover) board[x][y]);
			return;
		}

		// Handle champion
		if (board[x][y] instanceof Champion) {
			Champion currChamp = (Champion) board[x][y];
			// Ensure within range
			if (manhattanDist(c.getLocation(), currChamp.getLocation()) > a.getCastRange()) return;
			
			if (a instanceof DamagingAbility) {
				if (enemyTeam.contains(currChamp)) targets.add(currChamp);
	
			} else if (a instanceof HealingAbility) {
				if (friendlyTeam.contains(currChamp)) targets.add(currChamp);
	
			} else if (a instanceof CrowdControlAbility) {
				switch (((CrowdControlAbility) a).getEffect().getType()) {
					case BUFF: {
						if (friendlyTeam.contains(currChamp)) targets.add(currChamp);
						break;
					}
					case DEBUFF: {
						if (enemyTeam.contains(currChamp)) targets.add(currChamp);
						break;
					}
					default: break;
				}
			}
		}
	}


//	public static void main(String[] args) {
//		Player p1 = new Player("Player 1");
//		Player p2 = new Player("Player 2");
//		Champion ch = new Champion("Fadlu", 50, 50, 50, 100, 50, 50);
//		Champion ch1 = new Champion("Joey", 50, 50, 50, 101, 50, 50);
//		Champion ch2 = new Champion("Zeina", 50, 50, 50, 98, 50, 50);
//		Champion ch3 = new Champion("Youssef", 50, 50, 50, 120, 50, 50);
//		ArrayList<Champion> team1 = p1.getTeam();
//		ArrayList<Champion> team2 = p2.getTeam();
//		team1.add(ch);
//		team1.add(ch1);
//		team2.add(ch2);
//		team2.add(ch3);
//		Game game = new Game(p1, p2);
//		PriorityQueue p = game.getTurnOrder();
//		p.insert(ch1);
//		p.insert(ch2);
//		p.insert(ch3);
//		p.insert(ch);
//		game.placeChampions();
//		while (!(p.isEmpty())) {
//			System.out.println(((Champion) p.remove()).getSpeed());
//		}
//		Object[][] b = game.getBoard();
//		for (int i = 0; i < getBoardheight(); i++) {
//			System.out.print("[ ");
//			for (int j = 0; j < getBoardwidth(); j++) {
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
//		System.out.println(availableChampions.get(0));
//	}
}


