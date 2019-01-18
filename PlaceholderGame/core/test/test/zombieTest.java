package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.placeholder.game.Zombie;
import com.placeholder.game.Zombie.zombieType;

public class zombieTest {

	/**
	 * HOW TO RUN TESTS
	 * To run tests, right click file in package explorer, go to run configurations and press Junit Test
	 */
	
	/**
	 * CreateZombie
	 *	@Description
	 *		returns a zombie object
	 */	
	public Zombie CreateZombie() {
		
		//create Zombie object
		Zombie ZombieObj = new Zombie(zombieType.Big);
		return ZombieObj;		
	}

	@Test
	//ID U6
	public void ZombieConstuctorTest() {
		
		Zombie testZombie = new Zombie(zombieType.Big);
		
		//Test initial values 
		assertEquals(testZombie.getSight(),5);
		assertEquals(testZombie.attackDelay,1);
		assertEquals(testZombie.lastAttack,0);
		assertTrue(testZombie.melee);
	}

}
